package com.game.common;

/**
 * 
 * @author suchen
 * @date 2018年8月13日上午11:57:13
 */
public final class CommonConstantContext {

	private CommonConstantContext() {
		
	}
	
	public static final int PLAYERID_OFFSET = 10000;

	public static final int HOUSEKEEPER = 9999;
	
	public static final int MAX_AGE = 100;
	
	public static final int MAX_AGE_INDEX = MAX_AGE - 1;
	
	public static final int GRAFFITISTATICDATA_ID_START = 1001;

	public static final int GRAFFITI_SELECT_COUNT = 4;
	
	public static final int DRAW_BIRD_TIMES = 40;
	
	public static boolean hasRobat( int playerId ) {
		return playerId >= 0 && playerId < PLAYERID_OFFSET && playerId != HOUSEKEEPER;
	}
		
	public static boolean hasPlayer( int playerId ) {
		return playerId >= PLAYERID_OFFSET;
	}
	
}
