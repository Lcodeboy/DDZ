package com.game.framework.network.node;

/**
 * 
 */
public enum ServerNodeState {
	
	NONE(0), 
	//	
	CONNING(1),
	//	
	CONNED(2),
	//	
	CONNFAIL(3);
	
	private ServerNodeState( int value ) {
		this.value = value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
	
}
