package com.game.framework.concurrent.threadpool;

/**
 * 
 */
public interface UnchangedBindCreateThreadCallBack {
	
	public void leftThreadNotify(int index, Thread thread);
	
	public void rightThreadNotify(int index, Thread thread);
	
}
