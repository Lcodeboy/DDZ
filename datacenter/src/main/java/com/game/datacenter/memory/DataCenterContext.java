package com.game.datacenter.memory;

import com.game.framework.concurrent.AtomicLongIDBuilder;

public final class DataCenterContext {

	private DataCenterContext() {
		
	}
	
	//	
	public static AtomicLongIDBuilder orderIDBuilder = new AtomicLongIDBuilder(0);
	//	最大好友个数
	public static final int MAX_PLAYER_COUNT = 4096;
	//	初始好友个数
	public static final int INIT_PLAYER_COUNT = 512;
	
}
