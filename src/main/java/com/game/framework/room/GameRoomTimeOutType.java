package com.game.framework.room;
public enum GameRoomTimeOutType {
	
	//	操作超时
	PLAYER(1),
	//	等待用户加入
	WAITJOIN(2),
	//	等待用户准备时间
	WAITZREADY(3),
	//	一局超时
	GAME(4);
	
	private GameRoomTimeOutType( int value ) {
		this.value = value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
	
}
