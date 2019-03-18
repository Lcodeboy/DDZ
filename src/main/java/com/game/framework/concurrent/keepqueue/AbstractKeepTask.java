package com.game.framework.concurrent.keepqueue;

import com.game.framework.ProcessGlobalData;

/**
 * 
 */
public abstract class AbstractKeepTask {
	//
	private KeepTaskRunnableBuilder builder = null;
	//
	private KeepTaskManager keepTaskManager = null;
	//
	protected long actionTime = 0;
	//
	private String keepTaskUUID = null;
	
	public abstract Runnable createKeepTask();

	public AbstractKeepTask(KeepTaskManager keepTaskManager, KeepTaskRunnableBuilder builder) {
		this.builder = builder;
		this.keepTaskManager = keepTaskManager;
	}

	public boolean run( long nowTime ) {
		
		boolean running = keepTaskManager.canRunning(nowTime, this);
		
		try {
			if( running ) {
				ProcessGlobalData.appLVSKeepTaskExecutor.execute(createKeepTask());
				AbstractKeepTask keepTask = builder.createKeepTask();
				keepTaskManager.addKeepTask(keepTask);
			}
		} catch (Exception e) {
			ProcessGlobalData.gameLog.basicErr("AbstractKeepTask Run", e);
		} 
		
		return running;
		
	}

	public String getKeepTaskUUID() {
		return keepTaskUUID;
	}
	
	public abstract long getNextActionTime();
	
	public long getActionTime() {
		return actionTime;
	}
}
