package com.game.common;


public enum ClientOSType {

	NONE(-1), 
	
	ANDROID(1),
	
	IOS(2);
	
	private int value;
	
	private ClientOSType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
