/**
 * Copyright: Copyright (c) 2016
 * Company:CYOU
 *
 * @Title: KeepTask.java
 * @Package com.skylocus.framework.support.multithreading.keeptask 
 * @author chen.su
 * @date 2016年9月8日 上午9:54:03
 * @version 
 */
package com.game.framework.concurrent.keepqueue;

/**
 * @Description: 心跳任务
 */
public interface KeepTask {
	
	/**
	 * 
	 * @Description: 初始延迟的毫秒数
	 * @throws
	 * @return
	 */
	public int delay();
	
	/**
	 * 
	 * @Description: 每次执行的间隔时间, 当初始延迟到达后立即执行
	 * @throws
	 * @return
	 */
	public int interval();
	
	/**
	 * 
	 * @Description: 
	 * @throws
	 * @return
	 */
	public Runnable getLongTimeRunnable();
	
	/**
	 * 
	 * @Description: 
	 * @throws
	 */
	public void shortTimeRunnable();
	
	/**
	 * 
	 * @Description: 
	 * @throws
	 * @return
	 */
	public boolean isShort();
}
