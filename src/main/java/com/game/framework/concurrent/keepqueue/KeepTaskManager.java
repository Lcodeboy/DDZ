package com.game.framework.concurrent.keepqueue;

import java.util.ArrayList;

import com.game.framework.ProcessGlobalData;

public class KeepTaskManager implements Runnable {
	
	private Thread keepTaskThread = null;
	
	private ArrayList<AbstractKeepTask> keepTaskList = new ArrayList<AbstractKeepTask>();
	
	private volatile boolean running = true;
	
	private long timeOut = 0;
	
	private Object lock = new Object();
	
	public KeepTaskManager( long timeOut  ) {
		this.timeOut = timeOut;
		
		keepTaskThread = new Thread( this );
		keepTaskThread.setName("KeepTaskThread");
		keepTaskThread.start();
	}
	
	public boolean canRunning( long nowTime, AbstractKeepTask keepTask ) {
		return nowTime >= keepTask.getActionTime();
	}
	
	public void addKeepTask( AbstractKeepTask keepTask ) {
		synchronized( lock ) {
			keepTaskList.add( keepTask );
		}
	}
	@Override
	public void run() {
		
		long nowTime = 0;
		ArrayList<AbstractKeepTask> removeList = new ArrayList<AbstractKeepTask>();
		
		while( running ) {
			nowTime = System.currentTimeMillis();
			
			synchronized (lock) {
				AbstractKeepTask keepTask = null;
				
				for( int i = 0, size = keepTaskList.size(); i < size; i++ ) {
					
					if( (keepTask = keepTaskList.get(i)) == null ) {
						continue;
					}
					
					if( keepTask.run( nowTime ) ) {
						removeList.add( keepTask );
					}
				}
				
				keepTaskList.removeAll(removeList);
			}
			removeList.clear();
			try {
				Thread.sleep(timeOut);
			} catch (Exception e) {
				ProcessGlobalData.gameLog.basicErr("KeepTaskManager", e);
			}	
		}	
	}
}
