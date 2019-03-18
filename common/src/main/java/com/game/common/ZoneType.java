package com.game.common;

/**
 * 
 * @author suchen
 * @date 2018年10月8日下午2:42:01
 */
public enum ZoneType {

	NONE(-1), SMS(0), WX(1), QQ(2);
	
	private int value;

	private ZoneType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
