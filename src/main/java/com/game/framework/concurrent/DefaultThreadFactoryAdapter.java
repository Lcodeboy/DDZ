package com.game.framework.concurrent;

import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * 
 */
public class DefaultThreadFactoryAdapter extends DefaultThreadFactory {

	public DefaultThreadFactoryAdapter(String poolName) {
		super(poolName);
	}
	
    protected Thread newThread(Runnable r, String name) {
        return new FastThreadIDLocalThread(threadGroup, r, name);
    }
    
}
