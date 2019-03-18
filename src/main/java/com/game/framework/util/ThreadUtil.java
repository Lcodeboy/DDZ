package com.game.framework.util;

import java.util.Set;
public final class ThreadUtil {

	private ThreadUtil() {
		
	}
	
	public static String getAllThreadString() {
		StringBuilder builder = new StringBuilder();
		
		Thread[] allThread = ThreadUtil.getAllThread();
		
		for( int i = 0; i < allThread.length; i++ ) {
			builder.append(allThread[i].getClass().getSimpleName() + " [" + allThread[i].getName() + "] " + allThread[i].getId()).append("\n");
		}
		
		return builder.toString();
	}
	
	/**
	 * 高消耗调用不要随便调用
	 * 
	 * @return
	 */
	public static Thread[] getAllThread() {
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		
		Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
		
		return threadArray;
	}
	
	public static String toString( Thread thread ) {
		return thread.toString();
	}
	
	public static String toString1( Thread thread ) {
		return thread.getName() + "-" + thread.getId();
	}
	
}
