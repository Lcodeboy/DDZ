package com.game.framework.concurrent;

import com.game.framework.ProcessGlobalData;
import com.game.framework.room.ThreadLocalInit;
import io.netty.util.concurrent.FastThreadLocalThread;

/**
 */
public class FastThreadIDLocalThread extends FastThreadLocalThread {
    
	private int factoryId = 0;
	
	public FastThreadIDLocalThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
        factoryId = Integer.valueOf(name.substring(name.lastIndexOf("-") + 1));
    }
	
	public int getFactoryId() {
		return factoryId;
	}
	
	public void run() {
		ThreadLocalInit threadLocalInit = (ThreadLocalInit) ProcessGlobalData.gameProcess.getSubSystem("ThreadLocalInit");
		
		if( threadLocalInit != null ) {
			threadLocalInit.initData();
		}
		
		super.run();
	}
}
