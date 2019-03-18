package com.game.framework.mmo.scene;

import com.game.framework.ProcessGlobalData;
import com.game.framework.mmo.scene.state.GameSceneState_Exception;
import com.game.framework.mmo.scene.state.GameSceneState_None;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.support.fsm.State;
import com.game.framework.support.fsm.StateMachine;

import java.util.concurrent.LinkedBlockingQueue;

public class GameScene {

	//
	private int sceneId = 0;
	//
	private String uuid = null;
	//
	private StateMachine stateMachine = null;
	//
	private String name = null;
	//
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

	// 每一个位置是副本当中的人
	// 采用数组保持数据结构的简单; 副本对用户操作通常是遍历而不是查找, 当然在加入和退出时例外.
	protected GameSceneSeat[] playerArray = null;

	//
	private boolean activationEntry = false;
	// 激活状态
	// 0 初值
	// 1 表示等待结果
	// 2 表示成功
	// 3 表示失败
	private int activationState = ACTIVATION_STATE_NONE;

	public static final int ACTIVATION_STATE_NONE = 0;
	public static final int ACTIVATION_STATE_WAIT = 1;
	public static final int ACTIVATION_STATE_SUCCESS = 2;
	public static final int ACTIVATION_STATE_FAIL = 3;

	// 开门状态
	// 0 初值
	// 1 关门
	// 2 开门
	private int openDoor = OPEN_DOOR_NONE;

	public static final int OPEN_DOOR_NONE = 0;
	public static final int OPEN_DOOR_CLOSE = 1;
	public static final int OPEN_DOOR_OPEN = 2;

	// // 进入等待开始的时间后的提前关门时间(提前关门指的是不允许匹配)
	// private int waitStartTimeStopEntry = WAITSTARTTIMESTOPENTRY_NONE;
	//
	// public static final int WAITSTARTTIMESTOPENTRY_NONE = 0;

	// private long openDoorPlayerId = 0;

	// 是否有人已经进来了, Init 之后是否有人进来了
	private boolean firstJoined = false;
	// 第一个人进来的时间
	private long firstJoinedTime = 0;

	private LinkedBlockingQueue<GameSceneCommand> commandQueue = new LinkedBlockingQueue<>();
	// 对于Game节点 这个通道是网管服
	// 对于Gate节点 这个通道是对应的游戏服
	private GameTCPIOClient ioClient = null;

	private GameSceneManager gameSceneManager = null;

	private int readyAndJoinCount = Integer.MIN_VALUE;

	public GameScene(int sceneId) {
		this.sceneId = sceneId;
		stateMachine = new StateMachine(this, true);
		stateMachine.setCurrentState(GameSceneState_None.INSTANCE);
		name = getClass().getSimpleName() + " " + this.getSceneId();
	}

	// public int getWaitStartTimeStopEntry() {
	// return waitStartTimeStopEntry;
	// }

	public GameSceneManager getGameSceneManager() {
		return gameSceneManager;
	}

	public void setGameSceneManager(GameSceneManager gameSceneManager) {
		this.gameSceneManager = gameSceneManager;
	}

	public GameTCPIOClient getIoClient() {
		return ioClient;
	}

	public void setIoClient(GameTCPIOClient ioClient) {
		this.ioClient = ioClient;
	}

	public void init(int openPeopleCount, int fullPeopleCount, int waitOpenTimeOut, int logicType, int useReady,
			int openNeedReady, int waitStartTime
	// , int waitStartTimeStopEntry
	) {

		this.openPeopleCount = openPeopleCount;
		this.fullPeopleCount = fullPeopleCount;
		this.waitOpenTimeOut = waitOpenTimeOut;
		this.logicType = logicType;
		this.useReady = useReady;
		this.openNeedReady = openNeedReady;
		this.waitStartTime = waitStartTime;
		// this.waitStartTimeStopEntry = waitStartTimeStopEntry;
		playerArray = new GameSceneSeat[fullPeopleCount];

		for (int i = 0; i < playerArray.length; i++) {
			playerArray[i] = new GameSceneSeat(i);

			if (openNeedReady == OPEN_NEED_READY_TRUE) {
				playerArray[i].setReady(useReady == USE_READY_TRUE);
				playerArray[i].setPrevTickReady(false);
			}
		}
	}

	public void addGameSceneCommand(GameSceneCommand command) {
		commandQueue.add(command);
	}

	public int getWaitStartTime() {
		return waitStartTime;
	}

	public boolean isFirstJoined() {
		return firstJoined;
	}

	public long getFirstJoinedTime() {
		return firstJoinedTime;
	}

	public long getEntryWaitStartTime() {
		return entryWaitStartTime;
	}

	public void setEntryWaitStartTime(long entryWaitStartTime) {
		this.entryWaitStartTime = entryWaitStartTime;
	}

	public int getOpenDoor() {
		return openDoor;
	}

	public void openDoor() {
		if (openDoor == OPEN_DOOR_CLOSE || openDoor == OPEN_DOOR_NONE) {
			openDoor = OPEN_DOOR_OPEN;
		}

		ProcessGlobalData.gameLog.basic("Scene " + sceneId);
	}

	public void closeDoor() {
		openDoor = OPEN_DOOR_CLOSE;
	}

	public int getActivationState() {
		return activationState;
	}

	public void waitActivationResult() {
		if (activationState != ACTIVATION_STATE_NONE) {
			throw new IllegalStateException();
		}
		activationState = ACTIVATION_STATE_WAIT;
	}

	public void activationSuccess() {
		if (activationState != ACTIVATION_STATE_WAIT) {
			throw new IllegalStateException();
		}
		activationState = ACTIVATION_STATE_SUCCESS;
	}

	public void activationFail() {
		if (activationState != ACTIVATION_STATE_WAIT) {
			throw new IllegalStateException();
		}
		activationState = ACTIVATION_STATE_FAIL;
	}

	public void clear() {
		ProcessGlobalData.gameLog.basic("Scene " + getSceneId() + " Clear");

		uuid = null;
		activationEntry = false;
		activationState = ACTIVATION_STATE_NONE;
		openDoor = OPEN_DOOR_NONE;
		// openDoorPlayerId = 0;
		firstJoined = false;
		entryWaitStartTime = 0;
		readyAndJoinCount = Integer.MIN_VALUE;

		for (int i = 0; i < playerArray.length; i++) {
			playerArray[i].clear();
		}

		commandQueue.clear();
	}

	public boolean isActivationEntry() {
		return activationEntry;
	}

	public void activation_Entry() {
		this.activationEntry = true;
	}

	public int playerJoin(long playerId, int index, long nowTime) {

		StringBuilder builder = new StringBuilder();
		
		builder.append("SceneID "+ getSceneId() + " playerJoin " + playerId + " Assign Index " + index + "\n");
		
		for( int i = 0, size = playerArray.length; i < size; i++ ) {

			builder.append(playerArray[i]).append("\n");
		}

		ProcessGlobalData.gameLog.basic(builder.toString());

		if (playerArray[index].getPlayerId() != playerId) {
			throw new IllegalStateException();
		}

		playerArray[index].setJoinTime(nowTime);
		if (!firstJoined) {
			firstJoined = true;
			firstJoinedTime = nowTime;
		}
		return index;
	}

	public int playerApply(long playerId, int index, long nowTime) {

		StringBuilder builder = new StringBuilder();

		
		builder.append("SceneID "+ getSceneId() + " playerApply " + playerId + " Assign Index " + index + "\n");
		
		for( int i = 0, size = playerArray.length; i < size; i++ ) {

			builder.append(playerArray[i]).append("\n");
		}

		ProcessGlobalData.gameLog.basic(builder.toString());

		if (playerArray[index].getPlayerId() != -1) {
			if (playerArray[index].getPlayerId() == playerId) {
				builder.append("Apply Result " + index);
				ProcessGlobalData.gameLog.basic(builder.toString());
				return index;
			}

			throw new IllegalStateException("Player " + builder.toString() + " Not Apply");
		}

		playerArray[index].setPlayerId(playerId);
		playerArray[index].setApplyTime(nowTime);

		return index;
	}

	public boolean checkPlayerSeat(long playerId, int index) {
		return playerArray[index].getPlayerId() == playerId;
	}

	public void playerUpdateReady(long playerId, boolean ready) {
		GameSceneSeat seat = null;

		StringBuilder builder = new StringBuilder();

		builder.append("SceneID " + getSceneId() + " playerUpdateReady  ").append(playerId).append(" ").append(ready)
				.append("\n");

		for (int i = 0, size = playerArray.length; i < size; i++) {
			builder.append(playerArray[i]).append("\n");
		}

		ProcessGlobalData.gameLog.basic(builder.toString());

		for (int i = 0, size = playerArray.length; i < size; i++) {

			if ((seat = playerArray[i]).getPlayerId() == playerId) {

				if (!seat.isPrevTickReady()) {
					seat.setPrevReady(seat.isReady());
					seat.setPrevTickReady(true);
				}
				seat.setReady(ready);
				break;
			}
		}
	}

	public int playerJoin(long playerId, long nowTime) {

		StringBuilder builder = new StringBuilder();
		
		builder.append("SceneID "+ getSceneId() + " PlayerJoin Not Assign Index ").append(playerId).append("\n");		
		
		
		for( int i = 0, size = playerArray.length; i < size; i++ ) {

			builder.append(playerArray[i]).append("\n");
		}
		
		if( getStateMachine().getCurrentState().getStateId() == GameScene.FSM_RUNNING ) {
			ProcessGlobalData.gameLog.basic("GameScene CurrentState is " + getStateMachine().getCurrentState().getStateId() + " Not Join");
			return -2;
		}
		
		
		boolean success = false;
		int i = 0;

		for (int size = playerArray.length; i < size; i++) {
			if (playerArray[i].getPlayerId() == playerId) {
				if (playerArray[i].getJoinTime() > 0) {
					builder.append("PlayerJoin " + -1);
					ProcessGlobalData.gameLog.basic(builder.toString());
					return -1;
				}
				playerArray[i].setPlayerId(playerId);
				playerArray[i].setJoinTime(nowTime);
				success = true;
				break;
			}
		}

		if (success) {
			if (!firstJoined) {
				firstJoined = true;
				firstJoinedTime = nowTime;
			}
			builder.append("PlayerJoin " + i);
			ProcessGlobalData.gameLog.basic(builder.toString());
			return i;
		}
		builder.append("PlayerJoin " + -1);
		ProcessGlobalData.gameLog.basic(builder.toString());
		return -1;

	}
	
	public boolean playerQuit( long playerId, boolean quitPlayerId ) {
		boolean success = false;

		StringBuilder builder = new StringBuilder();
		
		builder.append("SceneID "+ getSceneId() + " playerQuit Not Assign Index " + playerId + "\n");
		
		for( int i = 0, size = playerArray.length; i < size; i++ ) {

			builder.append(playerArray[i]).append("\n");
		}

		ProcessGlobalData.gameLog.basic(builder.toString());

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if (playerArray[i].getPlayerId() == playerId) {
				playerArray[i].clear(quitPlayerId);
				// if( openDoorPlayerId == playerId ) {
				// openDoorPlayerId = 0;
				// }
				success = true;
				break;
			}
		}

		return success;
	}
	
	public boolean playerQuit(long playerId) {
		return playerQuit( playerId, true );
	}

	public int playerApply(long playerId, long nowTime) {

		StringBuilder builder = new StringBuilder();

		builder.append("SceneID "+ getSceneId() + " playerApply Not Assign Index ").append(playerId).append("\n");
		
		for( int i = 0, size = playerArray.length; i < size; i++ ) {

			builder.append(playerArray[i]).append("\n");
		}

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if (playerArray[i].getPlayerId() == -1 && playerArray[i].getApplyTime() == 0) {
				playerArray[i].setPlayerId(playerId);
				playerArray[i].setApplyTime(nowTime);

				builder.append("Apply Result " + i);
				ProcessGlobalData.gameLog.basic(builder.toString());
				return i;
			}
		}

		throw new IllegalStateException("Player " + builder.toString() + " Not Apply");
	}

	public boolean cotains(long playerId) {

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if (playerArray[i].getPlayerId() == playerId) {
				return true;
			}
		}

		return false;

	}

	public StateMachine getStateMachine() {
		return stateMachine;
	}

	public State<?> getCurrentState() {
		return stateMachine.getCurrentState();
	}

	public int getCurrentStateId() {
		return stateMachine.getCurrentState().getStateId();
	}

	public void changeState(long time, State<?> newState) {
		stateMachine.changeState(time, newState);
	}

	public String getUuid() {
		return uuid == null ? "Empty_UUID" : uuid;
	}

	public int getLogicType() {
		return logicType;
	}

	public int getOpenPeopleCount() {
		return openPeopleCount;
	}

	public int getFullPeopleCount() {
		return fullPeopleCount;
	}

	public int getWaitOpenTimeOut() {
		return waitOpenTimeOut;
	}

	public GameSceneSeat[] getPlayerArray() {
		return playerArray;
	}

	public int getPlayerCount() {
		int counter = 0;
		
		for (int i = 0; i < playerArray.length; i++) {
			if (playerArray[i].getPlayerId() > 0) {
				counter++;
			}
		}
		
		return counter;
	}
	public GameSceneSeat getPlayerById(long playerId) {
		for (int i = 0; i < playerArray.length; i++) {
			if (playerArray[i].getPlayerId() == playerId) {
				return playerArray[i];
			}
		}

		return null;
	}

	public GameSceneSeat getQuitPlayerById(long playerId) {
		for (int i = 0; i < playerArray.length; i++) {
			if (playerArray[i].getQuitPlayerId() == playerId) {
				return playerArray[i];
			}
		}

		return null;
	}

	public GameSceneSeat getPlayerByIndex(int index) {
		return playerArray[index];
	}

	public int getApplyCount() {

		int counter = 0;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if (playerArray[i].getApplyTime() > 0) {
				counter++;
			}
		}

		return counter;
	}

	public void setPlayerArray(GameSceneSeat[] playerArray) {
		this.playerArray = playerArray;
	}

	public int getPeopleCount() {
		int counter = 0;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if (playerArray[i].getPlayerId() != -1) {
				counter++;
			}
		}
		return counter;
	}

	public int getJoinPeopleCount() {
		if (openNeedReady != OPEN_NEED_READY_TRUE) {
			return Integer.MIN_VALUE;
		}

		int counter = 0;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if (playerArray[i].isReady() && playerArray[i].isJoin()) {
				counter++;
			}
		}

		return counter;
	}

	public int getStartReadyAndJoinPeopleCount() {
		return readyAndJoinCount;
	}

	public void saveStartReadyAndJoinPeopleCount() {
		ProcessGlobalData.gameLog.basic("saveStartReadyAndJoinPeopleCount A");
		readyAndJoinCount = getReadyAndJoinPeopleCount();
		ProcessGlobalData.gameLog.basic("saveStartReadyAndJoinPeopleCount B");
	}

	public int getReadyAndJoinPeopleCount() {
		
		if (openNeedReady != OPEN_NEED_READY_TRUE) {
			return Integer.MIN_VALUE;
		}

		int counter = 0;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			// ProcessGlobalData.gameLog.basic("getReadyAndJoinPeopleCount SceneID " +
			// sceneId + " isReady " +
			// playerArray[i].isReady() + " getPlayerId " +
			// playerArray[i].getPlayerId() + " isJoin " +
			// playerArray[i].isJoin() + " isOnline " +
			// playerArray[i].isOnline());

			if (playerArray[i].isReady() && playerArray[i].getPlayerId() != -1 && playerArray[i].isJoin()
					&& playerArray[i].isOnline()) {
				counter++;
			}
		}
		ProcessGlobalData.gameLog.basic("getReadyAndJoinPeopleCount Counter " + counter);
		return counter;
	}

	public int getReadyPeopleCount() {
		if (openNeedReady != OPEN_NEED_READY_TRUE) {
			return -1;
		}

		int counter = 0;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if (playerArray[i].isReady() && playerArray[i].getPlayerId() != -1) {
				counter++;
			}
		}
		return counter;
	}

	// 返回当前在线的人数
	public int getOnlinePeopleCount() {
		int count = 0;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if (playerArray[i].isOnline()) {
				count++;
			}
		}
		return count;
	}

	public int getSceneId() {
		return sceneId;
	}

	public int getUseReady() {
		return useReady;
	}

	public void setUseReady(int useReady) {
		this.useReady = useReady;
	}

	public int getOpenNeedReady() {
		return openNeedReady;
	}

	public void setOpenNeedReady(int openNeedReady) {
		this.openNeedReady = openNeedReady;
	}

	public void update(long nowTime) {
		// 更新状态机

		GameSceneCommand command = null;

		while (!commandQueue.isEmpty()) {

			if ((command = commandQueue.poll()) == null) {
				break;
			}

			try {
				command.processCommand(nowTime);
			} catch (Exception e) {
				ProcessGlobalData.gameLog.basicErr("GameScene Command Exception", e);
				e.printStackTrace();
				stateMachine.changeState(nowTime, GameSceneState_Exception.INSTANCE);
			}
		}

		try {
			stateMachine.update(nowTime);
		} catch (Exception e) {
			ProcessGlobalData.gameLog.basicErr("GameScene FSM Exception", e);
			e.printStackTrace();
			stateMachine.changeState(nowTime, GameSceneState_Exception.INSTANCE);
		}
	}

	public GameSceneStateHandler getActivationEntryStateHandler() {
		return activationEntryStateHandler;
	}

	public void setActivationEntryStateHandler(GameSceneStateHandler activationEntryStateHandler) {
		this.activationEntryStateHandler = activationEntryStateHandler;
	}

	public GameSceneStateHandler getActivationFailStateHandler() {
		return activationFailStateHandler;
	}

	public void setActivationFailStateHandler(GameSceneStateHandler activationFailStateHandler) {
		this.activationFailStateHandler = activationFailStateHandler;
	}

	public GameSceneStateHandler getActivationSuccessStateHandler() {
		return activationSuccessStateHandler;
	}

	public void setActivationSuccessStateHandler(GameSceneStateHandler activationSuccessStateHandler) {
		this.activationSuccessStateHandler = activationSuccessStateHandler;
	}

	public GameSceneStateHandler getClearStateHandler() {
		return clearStateHandler;
	}

	public void setClearStateHandler(GameSceneStateHandler clearStateHandler) {
		this.clearStateHandler = clearStateHandler;
	}

	public GameSceneStateHandler getExcetpionStateHandler() {
		return excetpionStateHandler;
	}

	public void setExcetpionStateHandler(GameSceneStateHandler excetpionStateHandler) {
		this.excetpionStateHandler = excetpionStateHandler;
	}

	public GameSceneStateHandler getInitStateHandler() {
		return initStateHandler;
	}

	public void setInitStateHandler(GameSceneStateHandler initStateHandler) {
		this.initStateHandler = initStateHandler;
	}

	public GameSceneStateHandler getNoneStateHandler() {
		return noneStateHandler;
	}

	public void setNoneStateHandler(GameSceneStateHandler noneStateHandler) {
		this.noneStateHandler = noneStateHandler;
	}

	public GameSceneStateHandler getRunningStateHandler() {
		return runningStateHandler;
	}

	public void setRunningStateHandler(GameSceneStateHandler runningStateHandler) {
		this.runningStateHandler = runningStateHandler;
	}

	public GameSceneStateHandler getWaitFullStateHandler() {
		return waitFullStateHandler;
	}

	public void setWaitFullStateHandler(GameSceneStateHandler waitFullStateHandler) {
		this.waitFullStateHandler = waitFullStateHandler;
	}

	public GameSceneStateHandler getWaitNotOpenStateHandler() {
		return waitNotOpenStateHandler;
	}

	public void setWaitNotOpenStateHandler(GameSceneStateHandler waitNotOpenStateHandler) {
		this.waitNotOpenStateHandler = waitNotOpenStateHandler;
	}

	public GameSceneStateHandler getWaitOpenStateHandler() {
		return waitOpenStateHandler;
	}

	public void setWaitOpenStateHandler(GameSceneStateHandler waitOpenStateHandler) {
		this.waitOpenStateHandler = waitOpenStateHandler;
	}

	public GameSceneStateHandler getRunningClearStateHandler() {
		return runningClearStateHandler;
	}

	public void setRunningClearStateHandler(GameSceneStateHandler runningClearStateHandler) {
		this.runningClearStateHandler = runningClearStateHandler;
	}

	// public void setOpenDoorPlayerId( long openDoorPlayerId ) {
	// this.openDoorPlayerId = openDoorPlayerId;
	// }

	public String toString() {
		return name;
	}
}
