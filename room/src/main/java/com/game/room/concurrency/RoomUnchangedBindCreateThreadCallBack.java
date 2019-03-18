package com.game.room.concurrency;

import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.threadpool.UnchangedBindCreateThreadCallBack;
import com.game.framework.room.ThreadLocalInit;

public class RoomUnchangedBindCreateThreadCallBack implements UnchangedBindCreateThreadCallBack {

	public RoomUnchangedBindCreateThreadCallBack() {
		
	}
	
	@Override
	public void leftThreadNotify(int index, Thread thread) {
		init();
	}

	@Override
	public void rightThreadNotify(int index, Thread thread) {
		init();
	}

	private void init() {
		ThreadLocalInit threadLocalInit = (ThreadLocalInit) ProcessGlobalData.gameProcess.getSubSystem("ThreadLocalInit");
		threadLocalInit.initData();		
	}
	
}
