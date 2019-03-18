package com.game.framework.concurrent.threadpool;

/**
 * 
 * @Description 
 */
public abstract class UnchangedBindRunnable implements Runnable {
	
	protected int bindId = 0;
	
	public UnchangedBindRunnable( int bindId ) {
		this.bindId = bindId;
	}
	
	/**
	 * 
	 * @Description 返回绑定ID, 在一个ThreadPool中 ID是唯一的
	 * @return int
	 */
	public int getBindId() {
		return bindId;
	}
	
}
