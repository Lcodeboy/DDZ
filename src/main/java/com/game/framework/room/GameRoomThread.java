package com.game.framework.room;

import java.util.ArrayList;

import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.SingleConsumerDoubleBufferList;

/**
 * 
 */
public class GameRoomThread extends Thread {
	//	
	private int threadIndex = 0;
	//	
	private GameRoomThreadPool threadPool = null;
	//
	private long tickTime = 0;
	
	public long getTickTime() { 
		return tickTime;
	}
	
	public GameRoomThread(int threadIndex, GameRoomThreadPool threadPool ) {
		this.threadIndex = threadIndex;
		this.threadPool = threadPool;
	}
	
	public void run() {
		
		ThreadLocalInit threadLocalInit = (ThreadLocalInit)ProcessGlobalData.gameProcess.getSubSystem("ThreadLocalInit");
		
		threadLocalInit.initData();
		
		SingleConsumerDoubleBufferList<GameRoomRunnable> doubleBufferList = threadPool.getConsumerQueue( threadIndex );
		ArrayList<DelayGameRoomRunnable> delayRunnableList = threadPool.getDelayGameRoomList( threadIndex );
		ArrayList<DelayGameRoomRunnable> removeDelayList = new ArrayList<DelayGameRoomRunnable>();
		
		GameRoomRunnable roomRunnable = null;
		DelayGameRoomRunnable delayRunnable = null;
		
		long startTime = 0;
		long stopTime = 0;
		long totalTime = 0;
		long waringUseTime = threadPool.getWaringUseTime();
		ArrayList<GameRoomRunnable> roomRunnableList = null;
		
		long startTime1 = 0;
		long stopTime1 = 0;
		
		long startTime2 = 0;
		long stopTime2 = 0;
		
		long startTime3 = 0;
		long stopTime3 = 0;
		
		while( true ) {
			
			tickTime = System.currentTimeMillis();
			startTime = tickTime;
			
			if( roomRunnableList != null ) {
				roomRunnableList.clear();
			}
			
			roomRunnableList = doubleBufferList.getAll();
			
			GameRoom[] allGameRoom = ProcessGlobalData.getAllGameRoom(threadIndex);
			
			try {
				
				//	/////////////////////////////
				//	不同于MMO, 这种游戏场景是无状态的
				//	不使用状态机就可以满足需求
				//	1.	遍历Delay执行操作表
				//	2. 	遍历执行操作表
				//	3.	遍历执行房间表
				//	/////////////////////////////
				
				//	执行Delay操作表
				startTime3 = System.currentTimeMillis();
				for( int i = 0, size = delayRunnableList.size(); i < size; i++ ) {
					
					if( (delayRunnable = delayRunnableList.get(i)) == null ) {
						continue;
					}
					if( (delayRunnable.getDelayTime() + delayRunnable.getStartTime()) <= tickTime ) {
						removeDelayList.add(delayRunnable);
						delayRunnable.run();
					}
				}
				delayRunnableList.removeAll(removeDelayList);
				
				startTime1 = stopTime3 = System.currentTimeMillis();
				
				int runnableSize = roomRunnableList.size();
				//	执行操作表
				for( int i = 0, size = roomRunnableList.size(); i < size; i++ ) {
					if( (roomRunnable = roomRunnableList.get(i)) == null ) {
						continue;
					}
					roomRunnable.run();
				}
				stopTime1 = System.currentTimeMillis();
				startTime2 = System.currentTimeMillis();
				//	执行房间表
				for( int i = 0, size = allGameRoom.length; i < size; i++ ) {
					
					try {
						allGameRoom[i].execute(tickTime);
					} catch (Exception e) {
						ProcessGlobalData.gameLog.basicErr("", e);
					}
					
					if( allGameRoom[i].getStopTime() > 0 ) {
						ProcessGlobalData.removeGameRoom(allGameRoom[i].getGameUUID(), threadIndex);
					}
				}
				
				stopTime = stopTime2 = System.currentTimeMillis();
				
				totalTime = stopTime - startTime;
				
				//	超过报警时间
				if( totalTime >= waringUseTime ) {
					ProcessGlobalData.gameLog.waring("GameRoom Tick TotalTime Waring "
							+ totalTime + " " + (stopTime2 - startTime2) + " " + 
							(stopTime1 - startTime1) + " " + (stopTime3 - startTime3) + " runnableSize " + runnableSize);
				}
				
				//	休眠TICK线程
				long tickTimeOut = ProcessGlobalData.roomTickTimeOut;
				
				if( (tickTimeOut = tickTimeOut - totalTime) > 10 ) {
					Thread.sleep( tickTimeOut );	
				}
				
			} catch ( Exception e ) {
				ProcessGlobalData.gameLog.basicErr("", e);
			} 
			
		}
		
	}
		
}
