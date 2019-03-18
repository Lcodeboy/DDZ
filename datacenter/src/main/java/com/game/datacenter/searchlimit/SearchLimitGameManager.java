package com.game.datacenter.searchlimit;

import java.util.ArrayList;
import java.util.LinkedList;

import com.game.common.CommonConstantContext;
import com.game.datacenter.searchlimit.operation.SearchLimitOperation_Entry;
import com.game.datacenter.searchlimit.operation.SearchLimitOperation_Quit;
import com.game.framework.concurrent.SingleConsumerDoubleBufferList;
import com.game.framework.util.queue.SwapQueue;

/**
 * 人找游戏(模糊匹配)
 */
public class SearchLimitGameManager implements Runnable {

	private static final int matchResultSize = 1024;
	//
	private SingleConsumerDoubleBufferList<SearchLimitOperation> operationDoubleBuffer = new SingleConsumerDoubleBufferList<>(
			matchResultSize);
	//
	private Thread matchingThread = null;
	//
	private volatile boolean running = false;
	//
	private SearchLimitNotify searchLimitNotify = null;
	//
	private int tickTime = 0;
	//
	private SwapQueue<SearchLimitOperation_Entry>[] manQueueGroup = null;
	//
	private SwapQueue<SearchLimitOperation_Entry>[] womanQueueGroup = null;
	//
	private LinkedList<SearchLimitOperation_Entry> activeQueue = null;
	//
	private LinkedList<SearchLimitOperation_Entry> passiveQueue = null;
	//
	private ArrayList<SearchLimitOperation_Entry> timeoutEntryList = null;
	
	@SuppressWarnings("unchecked")
	public SearchLimitGameManager(SearchLimitNotify searchLimitNotify, int tickTime) {
		this.searchLimitNotify = searchLimitNotify;

		timeoutEntryList = new ArrayList<SearchLimitOperation_Entry>();
		
		activeQueue = new LinkedList<SearchLimitOperation_Entry>();
		passiveQueue = new LinkedList<SearchLimitOperation_Entry>();

		manQueueGroup = new SwapQueue[CommonConstantContext.MAX_AGE];
		womanQueueGroup = new SwapQueue[CommonConstantContext.MAX_AGE];

		for (int i = 0, size = CommonConstantContext.MAX_AGE; i < size; i++) {
			manQueueGroup[i] = new SwapQueue<SearchLimitOperation_Entry>();
			womanQueueGroup[i] = new SwapQueue<SearchLimitOperation_Entry>();
		}

		this.tickTime = tickTime;

		matchingThread = new Thread(this);
		matchingThread.setName("SearchLimitGameManagerThread");
	}

	public void start() {
		running = true;
		matchingThread.start();
	}
	@Override
	public void run() {
		ArrayList<SearchLimitOperation> operationList = null;

		SearchLimitOperation operation = null;
		SearchLimitOperation_Quit quit = null;
		SearchLimitOperation_Entry entry = null;

		SwapQueue<SearchLimitOperation_Entry>[] group = null;
		SwapQueue<SearchLimitOperation_Entry> tree = null;

		SearchLimitOperation_Entry removeItem = new SearchLimitOperation_Entry();
		long nowTime = 0;

		while (running) {

			try {
				nowTime = System.currentTimeMillis();

				operationList = operationDoubleBuffer.getAll();

				for (int i = 0, size = CommonConstantContext.MAX_AGE; i < size; i++) {
					manQueueGroup[i].swap();
					womanQueueGroup[i].swap();
				}
				// //////////////////////////////
				// 1       放入&取消操作
				// 2       匹配用户
				// 3/4 通知匹配结果
				// //////////////////////////////

				// //////////////////////////////
				// 1 放入&取消操作
				// //////////////////////////////
				for (int i = 0, size = operationList.size(); i < size; i++) {
					
					if ((operation = operationList.get(i)) == null) {
						continue;
					}
					if (operation instanceof SearchLimitOperation_Quit) {
						quit = (SearchLimitOperation_Quit) operation;

						if (quit.isSex()) {
							group = manQueueGroup;
							System.out.println( " MAN QUIT "  + quit.getPlayerId());
						} else {
							group = womanQueueGroup;
							System.out.println( " WOMAN QUIT "  + quit.getPlayerId());
						}
						tree = group[quit.getAge()];
						removeItem.setPlayerId(quit.getPlayerId());
						tree.getReadQueue().remove(removeItem);
						
					} else {
						entry = (SearchLimitOperation_Entry) operation;

						if (entry.isSex()) {
							group = manQueueGroup;
							System.out.println( " MAN ENTRY "  + entry.getPlayerId());
						} else {
							group = womanQueueGroup;
							System.out.println( " WOMAN ENTRY "  + entry.getPlayerId());
						}
						group[entry.getAge()].getReadQueue().add(entry);
					}

					quit = null;
					entry = null;
				}
				operationList.clear();

				// //////////////////////////////
				// 2 匹配用户
				// //////////////////////////////

				// 处理所有男人的匹配请求
				match(manQueueGroup, nowTime);
				// 处理所有女人的匹配请求
				match(womanQueueGroup, nowTime);

				// //////////////////////////////
				// 3. 通知匹配成功结果
				// //////////////////////////////
				searchLimitNotify.matchResultNotify(activeQueue, passiveQueue);
				
				// //////////////////////////////
				// 4. 通知匹配失败结果
				// //////////////////////////////
				searchLimitNotify.matchResultFailNotify(timeoutEntryList);
				
			} catch (Exception e) {
				// 保存未处理的操作
				for (int i = 0, size = CommonConstantContext.MAX_AGE; i < size; i++) {
					manQueueGroup[i].copy();
					womanQueueGroup[i].copy();
				}
				e.printStackTrace();
			}
			try {
				// TODO @SuChen TickTime 的补帧逻辑
				Thread.sleep(tickTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param queueGroup
	 *            索引为年龄的数组
	 * @param nowTime
	 */
	private void match(SwapQueue<SearchLimitOperation_Entry>[] queueGroup, long nowTime) {

		SearchLimitOperation_Entry tempEntry = null;
		SearchLimitOperation_Entry resultEntry = null;

		LinkedList<SearchLimitOperation_Entry> readQueue = null;
		LinkedList<SearchLimitOperation_Entry> writeQueue = null;

		// TODO @SuChen 策划配表 模糊匹配超时时间
		int timea = 30000;
		// TODO @SuChen 策划配表 模糊匹配超时时间, 选择机器人的时间
		int timeb = 5000;

		for (int i = 0, size = queueGroup.length; i < size; i++) {
			if ((readQueue = queueGroup[i].getReadQueue()).isEmpty()) {
				continue;
			}
			while( !readQueue.isEmpty() ) {

				tempEntry = readQueue.poll();
				
				//	resultEntry与tempEntry即互相符合要求, 且互为陌生人
				resultEntry = matchPlayer(tempEntry);
				
				if (resultEntry == null) {

					if (nowTime - tempEntry.getTime() <= timeb) {
						// 还没达到5s时, 等待匹配机器人
						writeQueue = queueGroup[i].getWriteQueue();
						writeQueue.addLast(tempEntry);

					} else if (nowTime - tempEntry.getTime() <= timea) {
						// 没有到达超时时间, 但到达机器人匹配时间
						SearchLimitOperation_Entry robatEntry = searchLimitNotify.matchRobat(tempEntry, nowTime);
						// 机器人只有是陌生人时才能匹配上
						boolean matchRobatSuccess = robatEntry != null;
						if (matchRobatSuccess && checkRelation(tempEntry, robatEntry)) {
							activeQueue.add(tempEntry);
							passiveQueue.add(robatEntry);
						} else {
							// 此次匹配没有合适的机器人再次等待执行
							writeQueue = queueGroup[i].getWriteQueue();
							writeQueue.addLast(tempEntry);
						}
					} else {
						//	沒匹配上机器人超时, 将tempEntry放进队列
						timeoutEntryList.add(tempEntry);
					}
				} else {
					if (nowTime - tempEntry.getTime() <= timeb) {

						// 未超时, 将俩人匹配
						// 成功匹配
						activeQueue.add(tempEntry);
						passiveQueue.add(resultEntry);

					} else if (nowTime - tempEntry.getTime() <= timea) {

						// 匹配队列有超过2人, 超时后仍然匹配机器人
						SearchLimitOperation_Entry robatEntry = searchLimitNotify.matchRobat(tempEntry, nowTime);
						// 机器人只有是陌生人时才能匹配上
						boolean matchRobatSuccess = robatEntry != null;

						if (matchRobatSuccess && checkRelation(tempEntry, robatEntry)) {

							activeQueue.add(tempEntry);
							passiveQueue.add(robatEntry);

						} else {
							// 此次匹配没有合适的机器人再次等待执行
							queueGroup[i].getWriteQueue().addLast(tempEntry);

						}
						if (resultEntry.isSex()) {
							manQueueGroup[resultEntry.getAge()].getWriteQueue().addLast(resultEntry);
						} else {
							womanQueueGroup[resultEntry.getAge()].getWriteQueue().addLast(resultEntry);
						}
					} else {
						//	没匹配上机器人超时, 添加进队列
						timeoutEntryList.add(tempEntry);
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
	private boolean checkRelation(SearchLimitOperation_Entry entry, SearchLimitOperation_Entry resultEntry) {

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

	/**
	 * 检验entry是否符合resultEntry的要求
	 * 
	 * @param entry
	 * @param resultEntry
	 * @return true : 说明反向匹配成功
	 */
	private boolean check(SearchLimitOperation_Entry entry, SearchLimitOperation_Entry resultEntry) {

		// 0 和我一样 1比我大 2比我小 3不限
		// optional int32 age = 1;
		// 0 不限 1 男 2 女
		// optional int32 sex = 2;

		int age = entry.getAge();
		boolean sex = entry.isSex();

		byte searchAge = resultEntry.getSearchAge();
		byte searchSex = resultEntry.getSearchSex();

		boolean flag = false;
		switch (searchAge) {
		case 0: {
			if (age == resultEntry.getAge()) {
				// 年龄一样
				switch (searchSex) {
				case 0:
					flag = true;
					break;
				case 1:
					if (sex) {
						flag = true;
					}
					break;
				case 2:
					if (!sex) {
						flag = true;
					}
					break;
				default:
					break;
				}
			}
		}
			break;

		case 1: {
			if (resultEntry.getAge() <= age) {
				//
				switch (searchSex) {
				case 0:
					flag = true;
					break;
				case 1:
					if (sex) {
						flag = true;
					}
					break;
				case 2:
					if (!sex) {
						flag = true;
					}
					break;
				default:
					break;
				}
			}
		}
			break;

		case 2: {
			if (resultEntry.getAge() >= age) {
				switch (searchSex) {
				case 0:
					flag = true;
					break;
				case 1:
					if (sex) {
						flag = true;
					}
					break;
				case 2:
					if (!sex) {
						flag = true;
					}
					break;
				default:
					break;
				}
			}
		}
			break;

		case 3: {
			switch (searchSex) {
			case 0:
				flag = true;
				break;
			case 1:
				if (sex) {
					flag = true;
				}
				break;
			case 2:
				if (!sex) {
					flag = true;
				}
				break;
			default:
				break;
			}

		}
			break;

		default:
			break;
		}
		return flag;
	}

	/**
	 * 
	 * @param entry
	 *            尝试匹配的
	 * @param result
	 * @return
	 */
	private SearchLimitOperation_Entry matchPlayer(SearchLimitOperation_Entry entry) {

		// 0 和我一样 1比我大 2比我小 3不限
		// optional int32 age = 1;
		// 0 不限 1 男 2 女
		// optional int32 sex = 2;

		int age = entry.getAge();

		byte searchAge = entry.getSearchAge();
		byte searchSex = entry.getSearchSex();

		int ageStartIndex = 0;
		int ageStopIndex = 0;

		switch (searchAge) {
		case 0: {
			// 0 和我一样
			ageStartIndex = age;
			ageStopIndex = age;
		}
			;
			break;
		case 1: {
			// 1 比我大
			if (age == CommonConstantContext.MAX_AGE) {
				ageStartIndex = CommonConstantContext.MAX_AGE_INDEX;
				ageStopIndex = CommonConstantContext.MAX_AGE_INDEX;
			} else {
				ageStartIndex = age;
				ageStopIndex = CommonConstantContext.MAX_AGE_INDEX;
			}
		}
			;
			break;
		case 2: {
			// 2 比我小
			ageStartIndex = Math.max(0, age - 1);
			ageStopIndex = 0;
		}
			;
			break;
		case 3: {
			// 3 不限
			ageStartIndex = 0;
			ageStopIndex = CommonConstantContext.MAX_AGE_INDEX;
		}
			;
			break;
		default:
			break;
		}

		switch (searchSex) {
		case 0: {
			// 0 不限
			for (int i = ageStartIndex; i <= ageStopIndex; i++) {

				if (!womanQueueGroup[i].getReadQueue().isEmpty()) {
					
					SearchLimitOperation_Entry entryResult = (SearchLimitOperation_Entry) womanQueueGroup[i].getReadQueue().poll();
					
					if( check(entry, entryResult) && checkRelation(entry, entryResult) ) {
						
						return entryResult;
					}else {
						//	处理poll出来的resultEntry
						if (entryResult.isSex()) {
							manQueueGroup[entryResult.getAge()].getWriteQueue().addLast(entryResult);
						} else {
							womanQueueGroup[entryResult.getAge()].getWriteQueue().addLast(entryResult);
						}
						continue;
					}
				}				
				if (!manQueueGroup[i].getReadQueue().isEmpty()) {

					SearchLimitOperation_Entry entryResult = (SearchLimitOperation_Entry) manQueueGroup[i].getReadQueue().poll();
					if( check(entry, entryResult) && checkRelation(entry, entryResult) ) {
						
						return entryResult;
					}else {
						//	处理poll出来的resultEntry
						if (entryResult.isSex()) {
							manQueueGroup[entryResult.getAge()].getWriteQueue().addLast(entryResult);
						} else {
							womanQueueGroup[entryResult.getAge()].getWriteQueue().addLast(entryResult);
						}
						continue;
					}
				}
			}
		}
			;
			break;
		case 1: {
			// 1 男
			for (int i = ageStartIndex; i <= ageStopIndex; i++) {
				if (!manQueueGroup[i].getReadQueue().isEmpty()) {

					SearchLimitOperation_Entry entryResult = (SearchLimitOperation_Entry) manQueueGroup[i].getReadQueue().poll();
					if( check(entry, entryResult) && checkRelation(entry, entryResult) ) {
						
						return entryResult;
					}else {
						//	处理poll出来的resultEntry
						if (entryResult.isSex()) {
							manQueueGroup[entryResult.getAge()].getWriteQueue().addLast(entryResult);
						} else {
							womanQueueGroup[entryResult.getAge()].getWriteQueue().addLast(entryResult);
						}
						continue;
					}
				}
			}
		}
			;
			break;
		case 2: {
			// 2 女
			for (int i = ageStartIndex; i <= ageStopIndex; i++) {
				if (!womanQueueGroup[i].getReadQueue().isEmpty()) {
					
					SearchLimitOperation_Entry entryResult = (SearchLimitOperation_Entry) womanQueueGroup[i].getReadQueue().poll();
					
					if( check(entry, entryResult) && checkRelation(entry, entryResult) ) {
						
						return entryResult;
					} else {
						//	处理poll出来的resultEntry
						if (entryResult.isSex()) {
							manQueueGroup[entryResult.getAge()].getWriteQueue().addLast(entryResult);
						} else {
							womanQueueGroup[entryResult.getAge()].getWriteQueue().addLast(entryResult);
						}
						continue;
					}
				}
			}
		}
			;
			break;
		default:
			break;
		}

		return null;
	}

	public void entry(int playerId, boolean sex, int age, byte searchSex, byte searchAge, MEMPlayerRelation relation) {
		operationDoubleBuffer.add(new SearchLimitOperation_Entry(playerId, sex, age, searchSex, searchAge,
				System.currentTimeMillis(), relation));
		
		System.out.println( " Search Limit Entry " );
	}

	public void quit(int playerId, boolean sex, int age) {
		operationDoubleBuffer.add(new SearchLimitOperation_Quit(playerId, sex, age));
	}

}
