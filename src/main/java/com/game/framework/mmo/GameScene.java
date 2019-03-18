package com.game.framework.mmo;

import com.game.framework.fsm.StateMachine;

public class GameScene {

	private int stateid = 0;
	//
	private String uuid = null;
	//
	private StateMachine stateMachine = null;
	//
	private String name = null;
	//@TODO
	private int precleaning = PRECLEANING_INIT;
	//
	private boolean activation = false;

	public int getPrecleaning() {
		return precleaning;
	}

	public void setPrecleaning(int precleaning) {
		this.precleaning = precleaning;
	}

	public boolean isActivation() {
		return activation;
	}

	public void setActivation(boolean activation) {
		this.activation = activation;
	}

	public static final int PRECLEANING_INIT = 5000;


	public static final int FSM_ACTIVATION_ENTRY = 1;
	public static final int FSM_ACTIVATION_FAIL = 2;
	public static final int FSM_ACTIVATION_SUCCESS = 3;
	public static final int FSM_CLEAR = 4;
	public static final int FSM_EXCEPTION = 5;
	public static final int FSM_INIT = 6;
	public static final int FSM_NONE = 7;
	public static final int FSM_RUNNING = 8;
	public static final int FSM_WAIT_FULL = 9;
	public static final int FSM_WAIT_NOT_OPEN = 10;
	public static final int FSM_WAIT_OPEN = 11;
	public static final int FSM_RUNNING_CLEAR = 12;

	private GameSceneStateHandler activationEntryStateHandler = null;
	private GameSceneStateHandler activationFailStateHandler = null;
	private GameSceneStateHandler activationSuccessStateHandler = null;
	private GameSceneStateHandler clearStateHandler = null;
	private GameSceneStateHandler excetpionStateHandler = null;
	private GameSceneStateHandler initStateHandler = null;
	private GameSceneStateHandler noneStateHandler = null;
	private GameSceneStateHandler runningStateHandler = null;
	private GameSceneStateHandler waitFullStateHandler = null;
	private GameSceneStateHandler waitNotOpenStateHandler = null;
	private GameSceneStateHandler waitOpenStateHandler = null;
	private GameSceneStateHandler runningClearStateHandler = null;

	// 业务逻辑类型, 由于扩展的需要在框架层无法定义枚举, 业务逻辑需要定义
	private int logicType = 0;
	// 准备的默认值, 当openNeedReady为 OPEN_NEED_READY_TRUE 时才有意义
	// 说明用户进入场景后的默认准备状态
	// 0 初值
	// 1 true
	// 2 false
	private int useReady = USE_READY_NONE;

	public static final int USE_READY_NONE = 0;

	public static final int USE_READY_TRUE = 1;

	public static final int USE_READY_FALSE = 2;

	// 开启副本的人数
	private int openPeopleCount = 0;
	// 副本满员的人数
	private int fullPeopleCount = 0;
	// 开启副本的人数是否需要准备
	// 0 初值
	// 1 true
	// 2 false
	private int openNeedReady = OPEN_NEED_READY_NONE;

	public static final int OPEN_NEED_READY_NONE = 0;

	public static final int OPEN_NEED_READY_TRUE = 1;

	public static final int OPEN_NEED_READY_FALSE = 2;

	// 等待副本开启的超时时间
	// 0 初值
	// -1 一直等待
	// >0 等待时间
	private int waitOpenTimeOut = WAIT_OPEN_TIMEOUT_NONE;

	// 初值
	public static final int WAIT_OPEN_TIMEOUT_NONE = 0;
	// 永远
	public static final int WAIT_OPEN_TIMEOUT_FOREVER = -1;

	// 等待全员准备的时间
	private int waitStartTime = WAIT_START_TIME_NONE;
	//
	public static final int WAIT_START_TIME_NONE = 0;
	// 进入等待开始的时间
	private long entryWaitStartTime = 0;
}
