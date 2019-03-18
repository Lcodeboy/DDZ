package com.game.game.scene;

import java.util.HashMap;

import com.game.common.staticdata.GroupGameItemStaticDataProvider;
import com.game.common.staticdata.bean.GroupGameItemStaticData;
import com.game.framework.ProcessGlobalData;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneManager;
import com.game.framework.process.GameProcess;
import com.game.framework.room.ThreadLocalInit;
import com.game.game.GameServer;
import com.game.game.scene.MPRConfig.ClosedIntervalConfigLimit;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.message.proto.ProtoContext_Common.GameStaticDataType;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;

/**
 * 
 * @author suchen
 * @date 2018年11月26日上午10:30:04
 */
public class GameServerGameSceneManager extends GameSceneManager {

	private static MPRConfig mprConfig = null;

	public static MPRConfig getMPRConfig() {
		return mprConfig;
	}

	public static final GameServerGameSceneManager[][] MANAGER_ARRAY = new GameServerGameSceneManager[MPGRoomType
			.values().length][];
	//
	private MPGRoomType roomType = null;
	//
	private ClosedIntervalConfigLimit sceneIdLimit = null;

	public GameServerGameSceneManager(String name) {
		super(name);
	}

	public static void buildMPRConfig(GameProcess gameProcess) {
		String section = gameProcess.getSection();

		GameServer gameServer = (GameServer) gameProcess.getSubSystem("GameServer");

		HashMap<String, String> hashMap = gameProcess.getConfigReader().getParamMap(section);

		mprConfig = new MPRConfig();
		ClosedIntervalConfigLimit mprStartAndStop = new ClosedIntervalConfigLimit(
				Integer.valueOf(hashMap.get("MPRStartIndex")), Integer.valueOf(hashMap.get("MPRStopIndex")));
		ClosedIntervalConfigLimit mprStartAndStop1 = null;
		String[] MPRTypeStartIndex = hashMap.get("MPRTypeStartIndex").split(";");
		String[] MPRTypeStopIndex = hashMap.get("MPRTypeStopIndex").split(";");
		String[] mprTypeLineCount = hashMap.get("MPRTypeLineCount").split(";");

		String[] roomTypeStartArray = null;
		String[] roomTypeStopArray = null;
		String[] mprTypeLineCount1 = null;
		int length = MPGRoomType.values().length;

		int[] lineCount = new int[mprTypeLineCount.length + 1];
		ClosedIntervalConfigLimit[] mprTypeStartAndStopMap = new ClosedIntervalConfigLimit[length];

		int[] openPeopleCountArray = new int[length];
		int[] fullPeopleCountArray = new int[length];
		int[] waitOpenTimeOutArray = new int[length];
		int[] useReadyArray = new int[length];
		int[] openNeedReadyArray = new int[length];
		int[] waitStartTimeArray = new int[length];
		GroupGameItemStaticDataProvider staticDataProvider = (GroupGameItemStaticDataProvider) gameServer
				.getStaticData(GameStaticDataType.GROUPGAME_ITEM);
		for (int i = 0; i < length - 1; i++) {
			roomTypeStartArray = MPRTypeStartIndex[i].split(":");
			roomTypeStopArray = MPRTypeStopIndex[i].split(":");

			int index_1 = Integer.valueOf(roomTypeStartArray[0]);

			mprStartAndStop1 = new ClosedIntervalConfigLimit(Integer.valueOf(roomTypeStartArray[1]),
					Integer.valueOf(roomTypeStopArray[1]));

			mprTypeStartAndStopMap[index_1] = mprStartAndStop1;

			GroupGameItemStaticData staticData = (GroupGameItemStaticData) staticDataProvider.getStaticData(i + 1);
			openPeopleCountArray[index_1] = staticData.getOpenPeopleCount();
			fullPeopleCountArray[index_1] = staticData.getFullPeopleCount();
			waitOpenTimeOutArray[index_1] = staticData.getWaitOpenTimeOut();
			useReadyArray[index_1] = staticData.getUseReady();
			openNeedReadyArray[index_1] = staticData.getOpenNeedReady();
			waitStartTimeArray[index_1] = staticData.getWaitStartTime() * 1000;
			
			
			openPeopleCountArray[index_1] = 4;
			fullPeopleCountArray[index_1] = 6;
			waitOpenTimeOutArray[index_1] = -1;
			useReadyArray[index_1] = 1;
			openNeedReadyArray[index_1] = 1;
			waitStartTimeArray[index_1] = 10 * 1000;

		}

		for (int i = 0, size = mprTypeLineCount.length; i < size; i++) {
			mprTypeLineCount1 = mprTypeLineCount[i].split(":");
			lineCount[Integer.valueOf(mprTypeLineCount1[0])] = Integer.valueOf(mprTypeLineCount1[1]);
		}

		mprConfig.setMprTypeStartAndStopMap(mprTypeStartAndStopMap);
		mprConfig.setMprTypeLineCount(lineCount);
		mprConfig.setOpenMPR(Boolean.valueOf(hashMap.get("openMPR")));
		mprConfig.setMprStartAndStop(mprStartAndStop);
		mprConfig.setFullPeopleCountArray(fullPeopleCountArray);
		mprConfig.setOpenPeopleCountArray(openPeopleCountArray);
		mprConfig.setWaitOpenTimeOutArray(waitOpenTimeOutArray);
		mprConfig.setUseReadyArray(useReadyArray);
		mprConfig.setOpenNeedReadyArray(openNeedReadyArray);
		mprConfig.setWaitStartTimeArray(waitStartTimeArray);
		// mprConfig.setWaitStartTimeStopEntryArray(waitStartTimeStopEntryArray);
	}

	public MPGRoomType getRoomType() {
		return roomType;
	}

	public boolean inManager(int gameSceneId) {
		return gameSceneId >= sceneIdLimit.getStartIndex() && gameSceneId <= sceneIdLimit.getStopIndex();
	}

	public GameScene getGameScene(int gameSceneId) {
		return gameSceneArray[gameSceneId - sceneIdLimit.getStartIndex()];
	}

	public static GameScene getGameSceneById(int gameSceneId) {
		GameServerGameSceneManager manager = null;

		for (int i = 0; i < MANAGER_ARRAY.length; i++) {
			if (MANAGER_ARRAY[i] == null) {
				continue;
			}

			for (int j = 0, size = MANAGER_ARRAY[i].length; j < size; j++) {
				manager = MANAGER_ARRAY[i][j];
				if (manager.inManager(gameSceneId)) {
					return manager.getGameScene(gameSceneId);
				}
			}

		}

		return null;
	}

	public static int getGameSceneCount(MPGRoomType roomType) {
		if (roomType == null) {
			return 0;
		}

		int count = 0;
		GameServerGameSceneManager[] array = MANAGER_ARRAY[roomType.getNumber()];

		if (array == null) {
			return 0;
		}

		GameServerGameSceneManager manager = null;
		GameScene[] sceneArray = null;

		for (int i = 0; i < array.length; i++) {
			if ((manager = array[i]) == null) {
				continue;
			}

			if ((sceneArray = manager.getGameSceneArray()) == null) {
				continue;
			}

			count = count + sceneArray.length;
		}

		return count;
	}

	public static int getGameScenePlayerCount(MPGRoomType roomType) {
		if (roomType == null) {
			return 0;
		}

		int count = 0;
		GameServerGameSceneManager[] array = MANAGER_ARRAY[roomType.getNumber()];

		if (array == null) {
			return 0;
		}

		GameServerGameSceneManager manager = null;
		GameScene[] sceneArray = null;
		GameScene gameScene = null;

		for (int i = 0; i < array.length; i++) {
			if ((manager = array[i]) == null) {
				continue;
			}

			if ((sceneArray = manager.getGameSceneArray()) == null) {
				continue;
			}

			for (int j = 0; j < sceneArray.length; j++) {
				if ((gameScene = sceneArray[j]) == null) {
					continue;
				}

				count = count + gameScene.getPeopleCount();
			}

		}

		return count;
	}

	public static GameServerGameSceneManager[] createAllGameSceneManager(MPGRoomType roomType) {

		// 返回这个游戏类型的场景个数
		int sceneTotalCount = mprConfig.getSceneCount(roomType);
		// 返回这个游戏类型的每条线有多少个人的数量
		int sceneLineCount = mprConfig.getMprTypeLineCount()[roomType.getNumber()];

		GameServerGameSceneManager[] array = new GameServerGameSceneManager[mprConfig
				.getMprTypeTotalLineCount(roomType)];

		int startIndex = mprConfig.getStartIndexByType(roomType);
		int sceneId = startIndex;

		GameScene gameScene = null;

		int managerIndex = 0;

		GameScene[] gameSceneArray = null;
		int gameSceneIndex = 0;
		GameServerGameSceneManager manager = null;

		for (int i = 0; i < sceneTotalCount; i++, sceneId++) {
			switch (roomType) {
			case GRAFFITI:
				gameScene = new GraffitiGameScene(sceneId);
				break;
			default:
				break;
			}

			gameScene.init(mprConfig.getOpenPeopleCountArray()[roomType.getNumber()],
					mprConfig.getFullPeopleCountArray()[roomType.getNumber()],
					mprConfig.getWaitOpenTimeOutArray()[roomType.getNumber()], roomType.getNumber(),
					mprConfig.getUseReadyArray()[roomType.getNumber()],
					mprConfig.getOpenNeedReadyArray()[roomType.getNumber()],
					mprConfig.getWaitStartTimeArray()[roomType.getNumber()]
			// ,
			// mprConfig.getWaitStartTimeStopEntryArray()[roomType.getNumber()]
			);

			if (gameSceneArray == null) {
				gameSceneIndex = 0;
				gameSceneArray = new GameScene[sceneLineCount];
			}

			gameSceneArray[gameSceneIndex++] = gameScene;

			if (gameSceneIndex == sceneLineCount) {
				manager = new GameServerGameSceneManager(roomType.getNumber() + "_" + managerIndex);
				manager.roomType = roomType;
				manager.sceneIdLimit = new ClosedIntervalConfigLimit(sceneId - sceneLineCount + 1, sceneId);

				manager.setGameSceneArray(gameSceneArray);
				array[managerIndex++] = manager;
				gameSceneArray = null;
			}

		}

		if (gameSceneArray != null) {
			manager = new GameServerGameSceneManager(roomType.getNumber() + "_" + managerIndex);
			manager.setGameSceneArray(gameSceneArray);
			array[managerIndex++] = manager;
			gameSceneArray = null;
		}
		MANAGER_ARRAY[roomType.getNumber()] = array;
		return array;
	}

	public void run() {
		ThreadLocalInit threadLocalInit = (ThreadLocalInit) ProcessGlobalData.gameProcess
				.getSubSystem("ThreadLocalInit");
		threadLocalInit.initData();

		super.run();
	}

	public ClosedIntervalConfigLimit getConfigLimit() {
		return sceneIdLimit;
	}

}
