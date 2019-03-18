/**
 * Copyright: Copyright (c) 2016
 * Company:CYOU
 *
 * @Title: AsyncLog.java
 * @Package com.game.framework.log.async 
 * @author chen.su
 * @date 2017年11月15日 下午4:11:52
 * @version 
 */
package com.game.framework.log;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AsyncLog implements Runnable, GameLog {
	//
	private ConcurrentLinkedQueue<AsyncLogStruct>[] queueArray = null;
	//
	private Thread asyncLogThread = null;
	//
	private volatile boolean running = false;
	//
	private int asyncLogThreadCount = 0;
	
	public AsyncLog() {
		
	}
	
	@SuppressWarnings("unchecked")
	public void start() {
		
		asyncLogThreadCount = 10;

		queueArray = new ConcurrentLinkedQueue[asyncLogThreadCount];

		for (int i = 0, size = queueArray.length; i < size; i++) {
			queueArray[i] = new ConcurrentLinkedQueue<AsyncLogStruct>();
		}

		asyncLogThread = new Thread(this);
		asyncLogThread.setName("AsyncLog-" + asyncLogThread.getId());
		asyncLogThread.setDaemon(true);
		
		synchronized (this) {
			if (!running) {
				running = true;
				asyncLogThread.start();
			}
		}

	}

	public void stop() {
		synchronized (this) {
			if (!running) {
				running = false;
				asyncLogThread.interrupt();
			}
		}
	}

	@Override
	public void run() {

		AsyncLogStruct logStruct = null;
		int size = queueArray.length;

		while (running) {
			for (int i = 0; i < size; i++) {
				while ((logStruct = queueArray[i].poll()) != null) {
					logStruct.saveLog();
				}
			}

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void putLogStruct(AsyncLogStruct logStruct) {
		Thread thread = logStruct.saveLogThread;

		int index = (int) (thread.getId() % asyncLogThreadCount);

		queueArray[index].add(logStruct);
	}

	public void logicErr(String message, Throwable e) {
		putLogStruct(
				new AsyncLogExceptionStruct_LogicErr(Thread.currentThread(), System.currentTimeMillis(), message, e));
	}

	public void basicErr(String message, Throwable e) {
		putLogStruct(
				new AsyncLogExceptionStruct_BasicErr(Thread.currentThread(), System.currentTimeMillis(), message, e));
	}

	public void basicErr(String message) {
		putLogStruct(new AsyncLogStruct_BasicErr(Thread.currentThread(), System.currentTimeMillis(), message));
	}

	public void logicErr(String message) {
		putLogStruct(new AsyncLogStruct_LogicErr(Thread.currentThread(), System.currentTimeMillis(), message));
	}

	public void basic(String message) {
		putLogStruct(new AsyncLogStruct_Basic(Thread.currentThread(), System.currentTimeMillis(), message));
	}

	public void logic(String message) {
		putLogStruct(new AsyncLogStruct_Logic(Thread.currentThread(), System.currentTimeMillis(), message));
	}

	public void waring(String message) {
		putLogStruct(new AsyncLogStruct_Waring(Thread.currentThread(), System.currentTimeMillis(), message));
	}

	public void debug(String message) {
		putLogStruct(new AsyncLogStruct_Logic(Thread.currentThread(), System.currentTimeMillis(), message));
	}
}
