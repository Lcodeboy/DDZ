package com.game.internalgate.service.multiplayergame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.game.common.mpg.MPGTypeZhuanHuan;
import com.game.framework.ProcessGlobalData;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneManager;
import com.game.framework.mmo.scene.GameSceneManagerCommand;
import com.game.framework.mmo.scene.state.GameSceneState_Activation_Success;
import com.game.framework.mmo.scene.state.GameSceneState_Init;
import com.game.framework.mmo.scene.state.GameSceneState_WaitNotOpen;
import com.game.framework.mmo.scene.state.GameSceneState_WaitOpen;
import com.game.framework.network.GameTCPClient;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.network.node.ServerNode;
import com.game.framework.process.SubSystem;
import com.game.internalgate.InternalGateConn;
import com.game.internalgate.InternalGateServer;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_Common.GameSceneGlobalData;
import com.game.message.proto.ProtoContext_Common.GameType;
import com.game.message.proto.ProtoContext_Common.MPGRoomLifeState;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
import com.game.message.proto.ProtoContext_Common.TFResult;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGGameJoinGate;
import com.game.message.proto.ProtoContext_RIG.NetIGRApplyMPGRoom;
import com.game.message.proto.ProtoContext_WEBIG.NetIGWEBGameSceneGlobalData;

public class GateServerGameSceneManager extends GameSceneManager {

	private static final ConcurrentHashMap<Integer, GameScene> SCENE_MAP = new ConcurrentHashMap<>();
	private static final GameSceneManagerStruct[] STRUCT_ARRAY;

	static {

		GameSceneManagerStruct[] structArray = new GameSceneManagerStruct[MPGRoomType.values().length];

		for (int i = 0, size = structArray.length; i < size; i++) {
			structArray[i] = new GameSceneManagerStruct(MPGRoomType.valueOf(i));
		}

		STRUCT_ARRAY = structArray;
	}

	public static GameScene getGameSceneFromMap(int sceneId) {
		return SCENE_MAP.get(sceneId);
	}

	public static Object getSceneMap(MPGRoomType roomType) {
		GameSceneManagerStruct struct = STRUCT_ARRAY[roomType.getNumber()];
		ConcurrentHashMap<Integer, GameScene> SCENE_MAP = struct.getGameSceneManager().SCENE_MAP;
		NetIGWEBGameSceneGlobalData.Builder builder = NetIGWEBGameSceneGlobalData.newBuilder();
		GameSceneGlobalData.Builder dataBuilder = GameSceneGlobalData.newBuilder();

		for (GameScene gameScene : SCENE_MAP.values()) {
			if (gameScene == null) {
				continue;
			}
			// dataBuilder.setOpenDoorPlayerId((int)gameScene.getOpenDoorPlayerId());
			dataBuilder.setGameSceneId(gameScene.getSceneId());
			dataBuilder.setPlayerCount(gameScene.getPeopleCount());
			dataBuilder.setLifeState(MPGRoomLifeState.valueOf(gameScene.getCurrentStateId()));

			builder.addGlobalData(dataBuilder);
			dataBuilder.clear();
		}
		return builder;
	}
	// public static void quit( IniGatePlayerInfo playerInfo, MPGRoomType roomType )
	// {
	// GameSceneManagerStruct struct = STRUCT_ARRAY[roomType.getNumber()];
	//
	// GateServerGameSceneManager manager = struct.getGameSceneManager();
	//
	// QuitGameSceneCommand command = new QuitGameSceneCommand(manager, playerInfo);
	//
	// manager.addGameSceneManagerCommand(command);
	// }

	public static void match(int playerId, GameTCPIOClient roomServerIoClient, MPGRoomType roomType) {
		GameSceneManagerStruct struct = STRUCT_ARRAY[roomType.getNumber()];

		GateServerGameSceneManager manager = struct.getGameSceneManager();

		// GameServer的通道
		GameTCPIOClient ioClient = ((GameTCPClient) manager.serverNode.getNode()).getIoClient();

		MatchParam matchParam = new MatchParam(struct, playerId, manager.getId(), roomServerIoClient);

		MatchGameSceneCommand command = new MatchGameSceneCommand(manager, ioClient, matchParam);

		ProcessGlobalData.gameLog
				.basic("MatchGameSceneCommand PUSH " + command.hashCode() + " " + matchParam.getPlayerId());

		manager.addGameSceneManagerCommand(command);
	}

	/**
	 * 约战申请新房间
	 * 
	 * @param activePlayerId
	 * @param passivePlayerId
	 */
	public static void gameRequestRoom(MPGRoomType roomType, int activePlayerId, int passivePlayerId,
			String gameRequestKey) {
		//
		GameSceneManagerStruct struct = STRUCT_ARRAY[roomType.getNumber()];
		//
		GateServerGameSceneManager manager = struct.getGameSceneManager();
		//
		GameRequestParam gameRequestParam = new GameRequestParam(struct, activePlayerId, passivePlayerId,
				gameRequestKey);

		GameRequestGameSceneCommand command = new GameRequestGameSceneCommand(gameRequestParam, manager);

		manager.addGameSceneManagerCommand(command);
	}

	// 游戏服服务器节点
	private ServerNode<? extends SubSystem> serverNode = null;
	//
	private int startIndex = 0;
	//
	private int stopIndex = 0;
	//
	private int[] startIndexArray = null;
	//
	private int[] stopIndexArray = null;
	// 不同游戏类型有不同的初始化参数
	private int[] openPeopleCountArray = null;
	//
	private int[] fullPeopleCountArray = null;
	//
	private int[] waitOpenTimeOutArray = null;
	//
	private int[] useReadyArray = null;
	//
	private int[] openNeedReadyArray = null;
	//
	private MPGRoomType[] roomTypeArray = null;
	//
	private int[] waitStartTimeArray = null;
	//
	private int[] waitStartTimeStopEntryArray = null;

	public GateServerGameSceneManager(ServerNode<? extends SubSystem> serverNode) {
		super("Node-" + serverNode.getId());
		this.serverNode = serverNode;
	}

	public boolean isSupportType(MPGRoomType roomType) {
		for (int i = 0; i < roomTypeArray.length; i++) {

			if (roomTypeArray[i].equals(roomType)) {
				if (startIndexArray[i] != -1) {
					return true;
				} else {
					return false;
				}
			}

		}

		return false;
	}

	public ArrayList<GameScene> init(NetGAIGGameJoinGate joinGate) {
		startIndex = joinGate.getStartIndexLimit();
		stopIndex = joinGate.getStopIndexLimit();

		this.gameSceneArray = new GameScene[stopIndex - startIndex + 1];

		int arraysize = MPGRoomType.values().length;

		roomTypeArray = new MPGRoomType[arraysize];
		openPeopleCountArray = new int[arraysize];
		fullPeopleCountArray = new int[arraysize];
		waitOpenTimeOutArray = new int[arraysize];
		useReadyArray = new int[arraysize];
		openNeedReadyArray = new int[arraysize];
		startIndexArray = new int[arraysize];
		stopIndexArray = new int[arraysize];
		waitStartTimeArray = new int[arraysize];
		waitStartTimeStopEntryArray = new int[arraysize];

		ArrayList<GameScene> arrayList = new ArrayList<>();

		for (int i = 0, size = joinGate.getRoomTypeCount(); i < size; i++) {
			roomTypeArray[i] = joinGate.getRoomType(i);
			startIndexArray[i] = joinGate.getStartIndex(i);
			stopIndexArray[i] = joinGate.getStopIndex(i);
			openPeopleCountArray[i] = joinGate.getOpenPeopleCount(i);
			fullPeopleCountArray[i] = joinGate.getFullPeopleCount(i);
			waitOpenTimeOutArray[i] = joinGate.getWaitOpenTimeOut(i);
			useReadyArray[i] = joinGate.getUseReady(i);
			openNeedReadyArray[i] = joinGate.getOpenNeedReady(i);
			waitStartTimeArray[i] = joinGate.getWaitStartTime(i);
			waitStartTimeStopEntryArray[i] = joinGate.getWaitOpenTimeOut(i);
		}

		GameScene gameScene = null;

		for (int i = 0, size = arraysize; i < size; i++) {
			if (startIndexArray[i] == -1) {
				continue;
			}

			STRUCT_ARRAY[roomTypeArray[i].getNumber()].addGameSceneManager(this);

			for (int startIndex = startIndexArray[i], stopIndex = stopIndexArray[i]; startIndex <= stopIndex; startIndex++) {

				gameScene = gameSceneArray[startIndex - 1] = new GameScene(startIndex);
				gameScene.init(openPeopleCountArray[i], fullPeopleCountArray[i], waitOpenTimeOutArray[i],
						roomTypeArray[i].getNumber(), useReadyArray[i], openNeedReadyArray[i], waitStartTimeArray[i]
				// , waitStartTimeStopEntryArray[i]
				);

				arrayList.add(gameScene);
				SCENE_MAP.put(gameSceneArray[startIndex - 1].getSceneId(), gameSceneArray[startIndex - 1]);
			}
		}

		return arrayList;
	}

	public GameScene getGameScene(int sceneId) {
		if (startIndex > sceneId || stopIndex < sceneId) {
			return null;
		}

		return gameSceneArray[sceneId - startIndex];
	}

	public ServerNode<? extends SubSystem> getServerNode() {
		return serverNode;
	}

	public void setServerNode(ServerNode<SubSystem> serverNode) {
		this.serverNode = serverNode;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getStopIndex() {
		return stopIndex;
	}

	public void setStopIndex(int stopIndex) {
		this.stopIndex = stopIndex;
	}

	public int[] getStartIndexArray() {
		return startIndexArray;
	}

	public void setStartIndexArray(int[] startIndexArray) {
		this.startIndexArray = startIndexArray;
	}

	public int[] getStopIndexArray() {
		return stopIndexArray;
	}

	public void setStopIndexArray(int[] stopIndexArray) {
		this.stopIndexArray = stopIndexArray;
	}

}

class GameRequestParam {
	// 游戏类型
	private GameSceneManagerStruct struct = null;
	//
	private int activePlayerId = 0;
	//
	private int passivePlayerId = 0;
	//
	private String gameRequestKey = null;

	public GameSceneManagerStruct getStruct() {
		return struct;
	}

	public void setStruct(GameSceneManagerStruct struct) {
		this.struct = struct;
	}

	public int getActivePlayerId() {
		return activePlayerId;
	}

	public void setActivePlayerId(int activePlayerId) {
		this.activePlayerId = activePlayerId;
	}

	public int getPassivePlayerId() {
		return passivePlayerId;
	}

	public void setPassivePlayerId(int passivePlayerId) {
		this.passivePlayerId = passivePlayerId;
	}

	public String getGameRequestKey() {
		return gameRequestKey;
	}

	public void setGameRequestKey(String gameRequestKey) {
		this.gameRequestKey = gameRequestKey;
	}

	public GameRequestParam(GameSceneManagerStruct struct, int activePlayerId, int passivePlayerId,
			String gameRequestKey) {
		super();
		this.struct = struct;
		this.activePlayerId = activePlayerId;
		this.passivePlayerId = passivePlayerId;
		this.gameRequestKey = gameRequestKey;
	}

}

class MatchParam {

	private int playerId = 0;

	private int startSceneManagerId = 0;

	private GameSceneManagerStruct struct = null;

	private GameTCPIOClient roomServerIoClient = null;

	public MatchParam(GameSceneManagerStruct struct, int playerId, int startSceneManagerId,
			GameTCPIOClient roomServerIoClient) {
		super();
		this.playerId = playerId;
		this.startSceneManagerId = startSceneManagerId;
		this.struct = struct;
		this.roomServerIoClient = roomServerIoClient;
	}

	public GameTCPIOClient getRoomServerIoClient() {
		return roomServerIoClient;
	}

	public void setRoomServerIoClient(GameTCPIOClient roomServerIoClient) {
		this.roomServerIoClient = roomServerIoClient;
	}

	public int getPlayerId() {
		return playerId;
	}

	public int getStartSceneManagerId() {
		return startSceneManagerId;
	}

	public GameSceneManagerStruct getStruct() {
		return struct;
	}

}

class GameSceneManagerStruct {
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

	private ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();

	private ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

	private LinkedList<GateServerGameSceneManager> managerList = new LinkedList<>();

	private MPGRoomType roomType = null;

	public GameSceneManagerStruct(MPGRoomType roomType) {
		this.roomType = roomType;
	}

	public void addGameSceneManager(GateServerGameSceneManager manager) {
		writeLock.lock();

		try {
			managerList.add(manager);
		} finally {
			writeLock.unlock();
		}
	}

	public void removeGameSceneManager(GateServerGameSceneManager manager) {
		writeLock.lock();

		try {

			managerList.remove(manager);
		} finally {
			writeLock.unlock();
		}
	}

	public GateServerGameSceneManager moveGameSceneManager() {

		writeLock.lock();

		try {
			if (managerList.isEmpty() && managerList.size() == 1) {
				return null;
			}

			managerList.addLast(managerList.removeFirst());
			return managerList.getFirst();
		} finally {
			writeLock.unlock();
		}

	}

	public GateServerGameSceneManager getGameSceneManager() {

		readLock.lock();

		try {
			return managerList.getFirst();
		} finally {
			readLock.unlock();
		}

	}

	public MPGRoomType getRoomType() {
		return roomType;
	}

}

class GameRequestGameSceneCommand extends GameSceneManagerCommand {

	private GameRequestParam gameRequestParam = null;

	public GameRequestGameSceneCommand(GameRequestParam gameRequestParam, GameSceneManager gameSceneManager) {
		super(gameSceneManager);

		this.gameRequestParam = gameRequestParam;
	}

	@Override
	public void processCommand(long nowTime) throws Exception {
		GameScene[] gameSceneArray = gameSceneManager.getGameSceneArray();

		GameScene joinGameScene = null;

		for (GameScene gameScene : gameSceneArray) {

			if (gameScene.getCurrentStateId() == GameSceneState_Activation_Success.INSTANCE.getStateId()
					&& gameScene.getPeopleCount() == 0) {
				joinGameScene = gameScene;
				break;
			}

		}

		if (joinGameScene == null) {
			ProcessGlobalData.gameLog.basic("GameRequestGameSceneCommand joinGameScene == null");
		} else {
			int activeIndex = joinGameScene.playerApply(gameRequestParam.getActivePlayerId(), nowTime);
			joinGameScene.getPlayerByIndex(activeIndex).setOnline(true);

			int passiveIndex = joinGameScene.playerApply(gameRequestParam.getPassivePlayerId(), nowTime);
			joinGameScene.getPlayerByIndex(passiveIndex).setOnline(true);
			joinGameScene.openDoor();
		}

	}

}

/**
 * 
 * @author suchen
 * @date 2018年11月29日下午3:41:49
 */
class MatchGameSceneCommand extends GameSceneManagerCommand {
	private static final TFResult FAIL_0 = TFResult.newBuilder().setResult(false).setCode(0).build();

	public MatchGameSceneCommand(GameSceneManager gameSceneManager, GameTCPIOClient ioClient, Object param) {
		super(gameSceneManager, ioClient, param);
	}

	public MatchGameSceneCommand(GameSceneManager gameSceneManager, GameTCPIOClient ioClient) {
		super(gameSceneManager, ioClient);
	}

	@Override
	public void processCommand(long nowTime) throws Exception {

		GameScene[] gameSceneArray = gameSceneManager.getGameSceneArray();

		GameScene joinGameScene = null;

		boolean join = false;

		for (GameScene gameScene : gameSceneArray) {

			if (gameScene.getCurrentStateId() == GameSceneState_WaitNotOpen.INSTANCE.getStateId() || 
					gameScene.getCurrentStateId() == GameSceneState_WaitOpen.INSTANCE.getStateId() ) {
				
				
				
				if(gameScene.getEntryWaitStartTime() > 0 && nowTime - gameScene.getEntryWaitStartTime() >= gameScene.getWaitStartTime() - 500) {
					continue;
				}
				
				join = true;
				joinGameScene = gameScene;
				break;
			}
		}

		ProcessGlobalData.gameLog.basic(
				"Join>>>>> " + join + "GameScence>>>>> " + ((joinGameScene != null) ? joinGameScene.getSceneId() : -2));
		if (!join) {
			int currentStateId = 0;

			for (GameScene gameScene : gameSceneArray) {

				currentStateId = gameScene.getCurrentStateId();
				ProcessGlobalData.gameLog.basic("=====-------=====");

				ProcessGlobalData.gameLog.basic("SceneID " + gameScene.getSceneId() + " ApplyCount "
						+ gameScene.getApplyCount() + " FullPeopleCount " + gameScene.getFullPeopleCount());

				for (int i = 0, size = gameScene.getPlayerArray().length; i < size; i++) {

					ProcessGlobalData.gameLog.basic(gameScene.getPlayerArray()[i].toString() + "\n");
				}
				ProcessGlobalData.gameLog.basic("=====-------=====");

				if (currentStateId == GameSceneState_Activation_Success.INSTANCE.getStateId()
						|| currentStateId == GameSceneState_Init.INSTANCE.getStateId()
						|| currentStateId == GameSceneState_WaitOpen.INSTANCE.getStateId() ||
						currentStateId == GameSceneState_WaitNotOpen.INSTANCE.getStateId()) {

					if( gameScene.getEntryWaitStartTime() > 0 && nowTime - gameScene.getEntryWaitStartTime() >= gameScene.getWaitStartTime() - 500) {
						continue;
					}
					
					if (gameScene.getApplyCount() == gameScene.getFullPeopleCount()) {
						continue;
					}
					joinGameScene = gameScene;
					break;
				}
			}
		}

		MatchParam matchParam = (MatchParam) param;

		if (joinGameScene == null) {

			GameSceneManagerStruct gsmStruct = matchParam.getStruct();

			GateServerGameSceneManager newManager = gsmStruct.moveGameSceneManager();

			if (newManager == null || newManager.getId() == matchParam.getStartSceneManagerId()) {
				// 如果等于进入的Match则返回出错通知客户端无法匹配到房间
				// newManager == null 说明只有一个游戏服节点

				NetIGRApplyMPGRoom.Builder builder = NetIGRApplyMPGRoom.newBuilder();

				GameType gameType = MPGTypeZhuanHuan.roomTypeToGameType(gsmStruct.getRoomType());
				int playerId = matchParam.getPlayerId();

				builder.setGameType(gameType);
				builder.setPlayerId(playerId);
				builder.setResult(FAIL_0);
				InternalGateServer gateServer = (InternalGateServer) ProcessGlobalData.gameProcess;
				InternalGateConn roomConn = gateServer.getServerNode(gateServer.getPlayerInfo(playerId).getRoomServer())
						.getNode();

				MessageWriteUtil.writeAndFlush(roomConn.getIoClient(), playerId, builder.build());
			} else {
				// 换一个房间来
				newManager.addGameSceneManagerCommand(this);
				ProcessGlobalData.gameLog
						.basic("MatchGameSceneCommand " + MatchGameSceneCommand.this.hashCode() + " >>> A");
			}

		} else {

			int gameSceneStateId = joinGameScene.getCurrentStateId();
			int index = 0;
			if (GameSceneState_Activation_Success.INSTANCE.getStateId() == gameSceneStateId) {
				// 此时房间刚刚被激活还没有openDoor

				joinGameScene.openDoor();

				index = joinGameScene.playerApply(matchParam.getPlayerId(), nowTime);
				joinGameScene.getPlayerByIndex(index).setOnline(true);
				ProcessGlobalData.gameLog.basic("MatchGameSceneCommand " + MatchGameSceneCommand.this.hashCode()
						+ " playerid " + matchParam.getPlayerId() + " index " + index + " >>> B");
			} else if (GameSceneState_Init.INSTANCE.getStateId() == gameSceneStateId) {
				// 此时房间已经被打开, 但是还有没任何人JOIN进来过, 但是可能会有多个apply
				index = joinGameScene.playerApply(matchParam.getPlayerId(), nowTime);
				joinGameScene.getPlayerByIndex(index).setOnline(true);
				ProcessGlobalData.gameLog.basic("MatchGameSceneCommand " + MatchGameSceneCommand.this.hashCode()
						+ " playerid " + matchParam.getPlayerId() + " index " + index + " >>> C");
			} else if (GameSceneState_WaitNotOpen.INSTANCE.getStateId() == joinGameScene.getCurrentStateId()) {
				// 此时房间已经被打开, 已经有其他用户JOIN进来过, 此时会有多个join,和多个apply同时存在的情况也有可能没有任何用户, 由用户都退出时产生
				index = joinGameScene.playerApply(matchParam.getPlayerId(), nowTime);
				joinGameScene.getPlayerByIndex(index).setOnline(true);
				ProcessGlobalData.gameLog.basic("MatchGameSceneCommand " + MatchGameSceneCommand.this.hashCode()
						+ " playerid " + matchParam.getPlayerId() + " index " + index + " >>> D");
			} else if (GameSceneState_WaitOpen.INSTANCE.getStateId() == joinGameScene.getCurrentStateId()) {
				// 此时房间已经被打开, 已经有其他用户JOIN进来过, 此时会有多个join,和多个apply同时存在的情况也有可能没有任何用户, 由用户都退出时产生
				index = joinGameScene.playerApply(matchParam.getPlayerId(), nowTime);
				joinGameScene.getPlayerByIndex(index).setOnline(true);
				ProcessGlobalData.gameLog.basic("MatchGameSceneCommand " + MatchGameSceneCommand.this.hashCode()
						+ " playerid " + matchParam.getPlayerId() + " index " + index + " >>> E");
			}
		}
	}
}
