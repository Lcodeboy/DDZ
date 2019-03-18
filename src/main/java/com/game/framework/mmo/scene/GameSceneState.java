package com.game.framework.mmo.scene;

public enum GameSceneState {
	//	没有被激活的状态
	STATE_NONE(0),
	//	初始状态 表示一个干净的房间用户可以随时进入
	STATE_INIT(1),
	//	表示等待开始阶段, 人数没有达到可以开始的数量
	STATE_WAIT_NOT_OPEN(2),
	//	表示等待开始阶段, 人数已经达到可以开始的数量
	STATE_WAIT_OPEN(3),
	//	表示等待开始阶段, 人数已经达到上限
	STATE_WAIT_FULL(4),
	//	副本已经开始
	STATE_RUNNING(5),
	//	副本结束正在准备清空副本的阶段, 处于这个阶段时 房间逻辑状态无意义
	STATE_CLEAR(6),
	//	副本正在激活
	STATE_ACTIVATION_ENTRY(7),
	//	副本激活失败
	STATE_ACTIVATION_FAIL(8),
	//	副本激活, 在这个状态表示副本刚被激活, 还没有被使用
	STATE_ACTIVATION_SUCCESS(9),
	//	副本异常
	STATE_EXCEPTION(10);
	
	private GameSceneState( int value ) {
		this.value = value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
}
