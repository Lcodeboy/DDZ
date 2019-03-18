package com.game.framework.network;

/**
 * @Description: GameProcessState的状态和TCP状态无关
 */
public enum GameProcessState {
	
	NONE(0), 		//	
	INITING(1),		//
	INITED(2),		//	
	STARTING(3), 	//	
	RUNNING(4), 		//	
	STOPING(5), 		//	
	STOPED(6);		//
	
	private int state;

	private GameProcessState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
	
	
}

