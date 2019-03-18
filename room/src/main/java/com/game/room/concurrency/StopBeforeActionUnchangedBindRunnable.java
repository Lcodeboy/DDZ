package com.game.room.concurrency;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.threadpool.UnchangedBindRunnable;
import com.game.framework.concurrent.threadpool.UnchangedBindThreadPool;
import com.game.message.proto.ProtoContext_Common.PlayerState;
import com.game.room.RoomServer;
import com.game.room.unit.RoomPlayerUnit;

/**
 * 这里面存在一个特殊用法
 */
public class StopBeforeActionUnchangedBindRunnable extends UnchangedBindRunnable {

	private RoomServer roomServer = null;

	private UnchangedBindThreadPool threadPool = null;
	
	public static final AtomicInteger COUNTER = new AtomicInteger(0);
	
	public StopBeforeActionUnchangedBindRunnable(int threadIndex, 
			RoomServer roomServer, UnchangedBindThreadPool threadPool ) {
		super(threadIndex);
		
		this.roomServer = roomServer;
		this.threadPool = threadPool;
	}

	@Override
	public void run() {
		//	此处的特殊用法
		try {
			int threadIndex = getBindId();
			
			ProcessGlobalData.gameLog.basic("StopBeforeAction threadIndex " + threadIndex + " Start" );
			
			ConcurrentHashMap<Integer, RoomPlayerUnit> allPlayerUnit = roomServer.getAllRoomPlayerUnit();

			Collection<RoomPlayerUnit> collection = allPlayerUnit.values();
			
			int playerId = 0;
			
			for( RoomPlayerUnit playerUnit : collection ) {
				if( playerUnit.getPlayerState() == PlayerState.OFFLINE ) {
					continue;
				}
				
				playerId = playerUnit.get32PlayerID();
				
				if( threadIndex != threadPool.getThreadIndex(playerId) ) {
					continue;
				}
				
				try {
					roomServer.playerOfflineAction(playerUnit, true);
				} catch (Exception e) {
					ProcessGlobalData.gameLog.basicErr("StopBeforeAction Exception PlayerUnit " + playerId, e);
				}
			}
			
			ProcessGlobalData.gameLog.basic("StopBeforeAction threadIndex " + threadIndex + " Stop" );
		} finally {
			if( COUNTER.incrementAndGet() == threadPool.getCount2() ) {
				ProcessGlobalData.gameLog.basic("StopBeforeAction Over");
			}
		}
		
	}

}
