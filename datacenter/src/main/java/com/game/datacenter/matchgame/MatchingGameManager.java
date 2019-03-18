package com.game.datacenter.matchgame;

import java.util.ArrayList;
import java.util.LinkedList;

import com.game.common.CommonConstantContext;
import com.game.datacenter.matchgame.operation.MatchOperation_Entry;
import com.game.datacenter.matchgame.operation.MatchOperation_Quit;
import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.SingleConsumerDoubleBufferList;
import com.game.framework.util.queue.SwapQueue;
import com.game.message.proto.ProtoContext_Common.GameType;

/**
 * 游戏找人(非模糊匹配)
 */

@SuppressWarnings("unchecked")
public class MatchingGameManager implements Runnable {

	private static final int matchResultSize = 4096;
	
	private SingleConsumerDoubleBufferList<MatchOperation> operationDoubleBuffer = 
			new SingleConsumerDoubleBufferList<>(1024);
	//	新存储结构
	private SwapQueue<MatchOperation_Entry>[] queueGroup = new SwapQueue[GameType.values().length];
	//	存储每次匹配的结果
	private ArrayList<int[]>[] matchResultArray = new ArrayList[GameType.values().length];
	//	存储匹配失败结果
	private ArrayList<MatchOperation_Entry> timeOutEntryList = new ArrayList<MatchOperation_Entry>();
	//	
	private Thread matchingThread = null;
	//	
	private volatile boolean running = false;
	//	
	private MatchResultNotify matchResultNotify = null; 
	//
	private int tickTime = 0;
	
	public MatchingGameManager( MatchResultNotify matchResultNotify, int tickTime ) {
		
		if( matchResultNotify == null ) {
			throw new IllegalArgumentException();
		}
		
		for (int i = 0; i < matchResultArray.length; i++) {
			queueGroup[i] = new SwapQueue<MatchOperation_Entry>();
		}
		
		for( int i = 0; i < matchResultArray.length; i++ ) {
			matchResultArray[i] = new ArrayList<int[]>(matchResultSize);
		}
		
		this.matchResultNotify = matchResultNotify;
		this.tickTime = tickTime;
		
		matchingThread = new Thread( this );
		matchingThread.setName("MatchingGameManagerThread");
	}
	
	public void start() {
		running = true;
		matchingThread.start();
	}
	
	private volatile long prevTickTime = 0;
	
	private volatile int prevCounter = 0;
	
	private volatile int matchUseTime = 0;
	
	private volatile int sendUseTime = 0;
	
	public int getMatchUseTime() {
		return matchUseTime;
	}

	public long getPrevTickTime() {
		return prevTickTime;
	}

	public int getPrevCounter() {
		return prevCounter;
	}

	public int getSendUseTime() {
		return sendUseTime;
	}

	@Override
	public void run() {
		//	
		ArrayList<MatchOperation> operationList = null;
		
		MatchOperation operation = null;
		MatchOperation_Quit quit = null;
		MatchOperation_Entry entry = null;
		
		long nowTime = 0;
		
		SwapQueue<MatchOperation_Entry>[] group = null;
		SwapQueue<MatchOperation_Entry> entryList = null;
		
		MatchOperation_Entry removeItem = new MatchOperation_Entry();
		
		GameType[] gameTypeArray = GameType.values();
		
		long starttime = 0;
		long stoptime = 0;
		
		while (running) {

			try {
				operationList = operationDoubleBuffer.getAll();
				nowTime = System.currentTimeMillis();
				
				prevCounter = operationList.size();
				prevTickTime = nowTime;
				
				for (int i = 0, size = gameTypeArray.length; i < size; i++) {
					queueGroup[i].swap();
				}
				
//				if( !operationList.isEmpty() ) {
					//	/////////////////////////
					//	1. 处理 entry or quit 操作
					//	2. 匹配用户
					//	3. 通知匹配结果
					//	/////////////////////////
				
					//	1. 处理  entry or quit 操作
					for( int i = 0, size = operationList.size(); i < size; i++ ) {
							
						if( (operation = operationList.get(i)) == null ) {
							continue;
						}
						if( operation instanceof MatchOperation_Quit ) {
							try {
								quit = (MatchOperation_Quit)operation;
								
//								ProcessGlobalData.gameLog.logic("MGM-QUIT " + quit.getPlayerId());
								group = queueGroup;
								
								entryList = group[quit.getGameType().getNumber()];
								removeItem.setPlayerId(quit.getPlayerId());
								entryList.getReadQueue().remove(removeItem);
							} catch (Exception e) {
								ProcessGlobalData.gameLog.basicErr("group " + group + " quit " + quit, e);
							}
							
						} else {
							entry = (MatchOperation_Entry)operation;
//							ProcessGlobalData.gameLog.logic("MGM-ENTRY " + entry.getPlayerId());
							group = queueGroup;
							group[entry.getGameType().getNumber()].getReadQueue().add(entry);
						}
						quit = null;
						entry = null;
					}
					operationList.clear();
					
					//	2. 匹配用户
					starttime = System.currentTimeMillis();
					match(queueGroup, nowTime );
					stoptime = System.currentTimeMillis();
					matchUseTime = (int)(stoptime - starttime);
					
					starttime = System.currentTimeMillis();
					//	3. 匹配成功通知
					matchResultNotify.matchResultNotify(matchResultArray);
					//	4. 匹配失败的通知
					matchResultNotify.matchFailNotify(timeOutEntryList);
					stoptime = System.currentTimeMillis();
					sendUseTime = (int)(stoptime - starttime);
					
//					ProcessGlobalData.gameLog.logic("MGM-STATISTICS ptt " + 
//							prevTickTime + " pc " + prevCounter + " mut " + matchUseTime + " sut " + sendUseTime );
					
					//	清理
					for( int i = 0; i < matchResultArray.length; i++ ) {
						matchResultArray[i].clear();
					}
//				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(tickTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void match(SwapQueue<MatchOperation_Entry>[] queueGroup, long nowTime) throws Exception {
		
		LinkedList<MatchOperation_Entry> readQueue = null;
		LinkedList<MatchOperation_Entry> writeQueue = null;
		
		MatchOperation_Entry tempEntry = null;
		MatchOperation_Entry resultEntry = null;
		
		//	TODO 超时时间
		int timeOut = 30000;
		int robotTimeOut = 5000;

		for (int i = 0, size = queueGroup.length; i < size; i++) {
			if ((readQueue = queueGroup[i].getReadQueue()).isEmpty()) {
				continue;
			}
			int[] result = null;
			
			while( !readQueue.isEmpty() ) {
				
				tempEntry = readQueue.poll();
				resultEntry = readQueue.poll();
				
				if (resultEntry == null) {
					if (nowTime - tempEntry.getTime() <= robotTimeOut) {
						// 还没达到5s时, 等待匹配机器人
						writeQueue = queueGroup[i].getWriteQueue();
						writeQueue.addLast(tempEntry);

					} else if (nowTime - tempEntry.getTime() <= timeOut) {
						// 没有到达超时时间, 但到达机器人匹配时间
						//	匹配机器人
						boolean matchSuccess = matchResultNotify.matchRobatResultNotify(tempEntry, tempEntry.getGameType() );
						if( !matchSuccess ) {
							queueGroup[i].getWriteQueue().addLast(tempEntry);
						}
					} else {
						//	超时
						timeOutEntryList.add(tempEntry);
					}
				} else if( checkRelation(tempEntry, resultEntry) ){
					//	二人无关系
					if (nowTime - tempEntry.getTime() <= robotTimeOut){
						// 未超时, 将俩人匹配
						// 成功匹配
						result = new int[2];
						result[0] = tempEntry.getPlayerId();
						result[1] = resultEntry.getPlayerId();
						matchResultArray[i].add( result );
					} else if (nowTime - tempEntry.getTime() <= timeOut) {
						// 匹配队列有超过2人, 超时后将两人都匹配机器人
						matchResultNotify.matchRobatResultNotify(tempEntry, tempEntry.getGameType() );
//						queueGroup[i].getWriteQueue().addLast(resultEntry);
						//
						matchResultNotify.matchRobatResultNotify(resultEntry, resultEntry.getGameType() );
					} else {
						//	完全超时
						timeOutEntryList.add(resultEntry);
						timeOutEntryList.add(tempEntry);
					}
				} else {
					
					if( nowTime - tempEntry.getTime() <= robotTimeOut ) {
						//	二人有关系, 且未超时
						writeQueue = queueGroup[i].getWriteQueue();
						writeQueue.addLast(tempEntry);
						writeQueue.addLast(resultEntry);

					}else if( nowTime - tempEntry.getTime() <= timeOut ) {
						System.out.println( " match robot "  );
						boolean matchSuccess1 = matchResultNotify.matchRobatResultNotify(resultEntry, resultEntry.getGameType() );
						if( !matchSuccess1 ) {
							queueGroup[i].getWriteQueue().addLast(resultEntry);
						}
						boolean matchSuccess2 = matchResultNotify.matchRobatResultNotify(tempEntry, tempEntry.getGameType() );
						if( !matchSuccess2 ) {
							queueGroup[i].getWriteQueue().addLast(tempEntry);
						}
					} else {
						timeOutEntryList.add(resultEntry);
						timeOutEntryList.add(tempEntry);
					}
				}
			}
		}
	}
	
	/**
	 * 判断 entry 与 resultEntry 之间是否有关系
	 * 
	 * @param entry
	 * @param resultEntry
	 *            或者为机器人entry
	 * @return true: 互为陌生人关系
	 */
	private boolean checkRelation(MatchOperation_Entry entry, MatchOperation_Entry resultEntry) {

		
		MEMPlayerRelation relation = entry.getRelation();
		ArrayList<MEMPlayerRelationItem> list = relation.getRelationList();

		if (CommonConstantContext.hasRobat(resultEntry.getPlayerId())) {

			// 如果一方为机器人, 检查机器人是否为entry的好友关系
			for (MEMPlayerRelationItem item : list) {
				if (item.getPlayerId() == resultEntry.getPlayerId()) {
					return false;
				}
			}
			return true;
		} else {
			MEMPlayerRelation resutltRelation = resultEntry.getRelation();
			ArrayList<MEMPlayerRelationItem> resultList = resutltRelation.getRelationList();

			boolean resulta = true;
			boolean resultb = true;

			for (MEMPlayerRelationItem item : list) {
				if (item.getPlayerId() == resultEntry.getPlayerId()) {
					resulta = false;
					break;
				}
			}

			for (MEMPlayerRelationItem resultItem : resultList) {
				if (resultItem.getPlayerId() == entry.getPlayerId()) {
					resultb = false;
					break;
				}
			}
			return resulta && resultb;
		}
	}
	
	public void entry( int playerId, GameType gameType, MEMPlayerRelation relation ) {
		operationDoubleBuffer.add( new MatchOperation_Entry( playerId, gameType, System.currentTimeMillis(), relation ));
	}
	
	public void quit( int playerId, GameType gameType ) {
		operationDoubleBuffer.add( new MatchOperation_Quit( playerId, gameType ));
	}
	
}
