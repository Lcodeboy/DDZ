package com.game.room;

import org.apache.commons.collections.map.MultiKeyMap;

import com.game.framework.ProcessGlobalData;
import com.game.framework.process.GameProcess;
import com.game.framework.process.SubSystem;
import com.game.framework.room.ThreadLocalInit;

/**
 * 
 */
public class RoomServerThreadLocalInit implements ThreadLocalInit, SubSystem {

	@Override
	public void initData() {
		ProcessGlobalData.createThreadLocal(ThreadLocalEnum.values().length);
		
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.GAME_REQUEST.ordinal(), new MultiKeyMap());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.RECV_MSG_StringBuilder.ordinal(), new StringBuilder());
	}
	
	@Override
	public String getSubSystemName() {
		return "ThreadLocalInit";
	}
	
	
	@Override
	public void init(GameProcess gameProcess) throws Exception {
		
	}

	@Override
	public void start(GameProcess gameProcess) throws Exception {
		
	}

	@Override
	public void stop(GameProcess gameProcess) throws Exception {
		
	}

}
