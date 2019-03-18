package com.game.game;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.game.common.cache.redis.DefaultRedisContextFacade;
import com.game.common.staticdata.GameExpressionStaticDataProvider;
import com.game.common.staticdata.GameTimeConfStaticDataProvider;
import com.game.common.staticdata.GlobalConfStaticDataProvider;
import com.game.common.staticdata.GraffitiStaticDataProvider;
import com.game.common.staticdata.GroupGameItemStaticDataProvider;
import com.game.common.staticdata.KeyWordStaticDataProvider;
import com.game.common.staticdata.PininsertionStaticDataProvider;
import com.game.common.staticdata.PlaneRandomStaticDataProvider;
//import com.game.common.staticdata.PlaneRandomStaticDataProvider;
import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.threadpool.UnchangedBindThreadPool;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneCommand;
import com.game.framework.mmo.scene.GameScenePlayerUnit;
import com.game.framework.network.GameLogicProcessor;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.network.GameTCPIOClientGroup;
import com.game.framework.network.GameTCPServer;
import com.game.framework.network.context.ChannelAttributeContext;
import com.game.framework.process.GameProcess;
import com.game.framework.room.DelayGameRoomRunnable;
import com.game.framework.room.GameRoom;
import com.game.framework.room.GameRoomPlayerUnit;
import com.game.framework.room.GameRoomRunnable;
import com.game.framework.room.GameRoomThreadPool;
import com.game.game.concurrent.GameUnchangedBindCreateThreadCallBack;
import com.game.game.concurrent.GameUnchangedBindRunnable;
import com.game.game.concurrent.NetReConnUnchangedBindRunnable;
import com.game.game.processor.mpg.NetCGMPGPlayerOfflineLogicProcessor;
import com.game.game.scene.GameServerGameSceneManager;
import com.game.message.MessageCode1Enum;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_BASIC.NetKeepLivePing;
import com.game.message.proto.ProtoContext_BASIC.NetKeepLivePong;
import com.game.message.proto.ProtoContext_BASIC.NetReConn;
import com.game.message.proto.ProtoContext_BASIC.NetRobotConnTest;
import com.game.message.proto.ProtoContext_BASIC.NetServerStoping;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiGoodOrBad;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiGuessWords;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiLineController;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiPenLine;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiPenUpdate;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiReady;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiSelectWord;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiUpdateWord;
import com.game.message.proto.ProtoContext_CG.NetCGJoinGame;
import com.game.message.proto.ProtoContext_CG.NetCGJoinMPGRoom;
import com.game.message.proto.ProtoContext_CG.NetCGMPGRoomMSG;
import com.game.message.proto.ProtoContext_CG.NetCGMPGThrowExpression;
import com.game.message.proto.ProtoContext_CG.NetCGQuitMPGRoom;
import com.game.message.proto.ProtoContext_Common.GameStaticDataType;
import com.game.message.proto.ProtoContext_Common.GameType;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
import com.game.message.proto.ProtoContext_GAIG.NetIGGAActivationMPRRoom;
import com.game.message.proto.ProtoContext_GAIG.NetIGGACreateGame;
import com.game.message.proto.ProtoContext_GAIG.NetIGGASyncMPRData;
import com.game.message.proto.ProtoContext_GAIG.NetIGGATiPlayer;
import com.game.message.proto.ProtoContext_GAIG.NetIGGATryConnGame;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

/**
 * 房间服
 */
@SuppressWarnings("rawtypes")
public class GameServer extends GameTCPServer {

	private GameRoomThreadPool roomThreadPool = null;

	private AbstractStaticDataProvider[] dataProviderArray = null;

	private ConcurrentHashMap<Integer, GameRoomPlayerUnit> gamePlayerMap = new ConcurrentHashMap<>();

//	private GameServerMonitorAndControllContext monitorAndControll = new GameServerMonitorAndControllContext();

	private ConcurrentHashMap<Integer, GameScenePlayerUnit> scenePlayerMap = new ConcurrentHashMap<>();
	
	public void pushPlayerUnit( int playerId, GameScenePlayerUnit playerUnit ) {
		scenePlayerMap.put(playerId, playerUnit);
	}
	
	public void removePlayerUnit( GameScenePlayerUnit playerUnit ) {
		scenePlayerMap.remove(playerUnit.get32PlayerID(), playerUnit);
	}
	
	public GameScenePlayerUnit getPlayerUnit( int playerId ) {
		return scenePlayerMap.get(playerId);
	}
	public GameRoomPlayerUnit getGameRoomPlayerUnit(int playerId) {
		return gamePlayerMap.get(playerId);
	}

//	public GameServerMonitorAndControllContext getMonitorAndControll() {
//		return monitorAndControll;
//	}

	public void putGameRoomPlayerUnit(GameRoomPlayerUnit playerUnit) {
		gamePlayerMap.put(playerUnit.get32PlayerID(), playerUnit);
	}

	public void removeGameRoomPlayerUnit(int playerId) {
		gamePlayerMap.remove(playerId);
	}

	public int getPlayerCounter() {
		return gamePlayerMap.size();
	}

	//
	private volatile boolean stopBeforeAction = false;
	//
	private volatile boolean closeEntryAction = false;
	//
	private volatile boolean recvMSGLog = true;

	public GameServer() {
		super("GameServer");
		
//		keepalivetry = Integer.MAX_VALUE;
	}

	private volatile GameTCPIOClient iniGateConn = null;

	public GameTCPIOClient getIniGateConn() {
		return iniGateConn;
	}

	public void setIniGateConn(GameTCPIOClient iniGateConn) {
		this.iniGateConn = iniGateConn;
	}

	@SuppressWarnings("unchecked")
	public void init(GameProcess gameProcess) throws Exception {

		super.init(gameProcess);
		String section = gameProcess.getSection();

		ProcessGlobalData.excelExportTXTDir = new File(
				System.getenv(ProcessGlobalData.ImportantEnv.GAME_EXCEL_EXPORTTXT));

		dataProviderArray = new AbstractStaticDataProvider[GameStaticDataType.values().length + 1];

		ProcessGlobalData.redisContextFacade = new DefaultRedisContextFacade(ProcessGlobalData.redisContext);

		loadAllStaticData();

		// 开多少个房间线程
		int roomThreadCount = Integer.valueOf(ProcessGlobalData.configReader.getParam(section, "RoomThreadCount"));
		// 每个房间线程最多支持多少人
		int roomThreadMaxPeople = Integer
				.valueOf(ProcessGlobalData.configReader.getParam(section, "RoomThreadMaxPeople"));
		// 房间报警耗时
		int roomWaringUseTime = Integer.valueOf(ProcessGlobalData.configReader.getParam(section, "RoomWaringUseTime"));
		// 节点ID
		nodeId = Integer.valueOf(ProcessGlobalData.configReader.getParam(section, "nodeId"));

		tcpIOClientGroup = new GameTCPIOClientGroup[roomThreadCount];

		for (int i = 0; i < tcpIOClientGroup.length; i++) {
			tcpIOClientGroup[i] = new GameTCPIOClientGroup();
		}

		// ProcessGlobalData.gameRoomThreadLocalInit = this;

		//
		roomThreadPool = new GameRoomThreadPool(roomThreadCount, roomThreadMaxPeople * 2, roomWaringUseTime,
				"RoomThread");

		ProcessGlobalData.gameRoomCache = new HashMap[roomThreadPool.getLength()];
		ProcessGlobalData.gameRoomCounter = new AtomicInteger[GameType.values().length];

		for (int i = 0; i < ProcessGlobalData.gameRoomCache.length; i++) {
			ProcessGlobalData.gameRoomCache[i] = new HashMap<String, GameRoom>();
		}

		for (int i = 0; i < ProcessGlobalData.gameRoomCounter.length; i++) {
			ProcessGlobalData.gameRoomCounter[i] = new AtomicInteger();
		}

		ProcessGlobalData.appLVRoomTickProcessorExecutor = roomThreadPool;

		int count1 = Integer.valueOf(ProcessGlobalData.configReader.getParam(section, "logicThreadPoolCount1"));
		int count2 = Integer.valueOf(ProcessGlobalData.configReader.getParam(section, "logicThreadPoolCount2"));

		ProcessGlobalData.appLVSLogicProcessorExecutor = new UnchangedBindThreadPool(count1, count2, "Logic",
				new GameUnchangedBindCreateThreadCallBack());

		boolean openMPR = gameProcess.getConfigReader().getBooleanParam(section, "openMPR", false);

		if (openMPR) {
			GameServerGameSceneManager.buildMPRConfig(gameProcess);
			GameServerGameSceneManager.createAllGameSceneManager(MPGRoomType.GRAFFITI);
			GameServerGameSceneManager[] managerArray = GameServerGameSceneManager.MANAGER_ARRAY[MPGRoomType.GRAFFITI_VALUE];

			for (int i = 0; i < managerArray.length; i++) {
				managerArray[i].start();
			}
		}
	}

	public void start(GameProcess gameProcess) throws Exception {
		roomThreadPool.start();

		super.start(gameProcess);
	}

	public Object getAllStaticData(GameStaticDataType dataType) {
		return (dataProviderArray[dataType.getNumber()]).getAllStaticDataObject();
	}

	public Object getStaticData(GameStaticDataType dataType) {
		return (dataProviderArray[dataType.getNumber()]);
	}

	public Object getStaticData(GameStaticDataType dataType, Object key) {
		return (dataProviderArray[dataType.getNumber()]).getStaticData(key);
	}

	public Object getStaticData(GameStaticDataType dataType, Object key1, Object key2) {
		return (dataProviderArray[dataType.getNumber()]).getStaticData(key1, key2);
	}

	public Object getStaticData(GameStaticDataType dataType, Object key1, Object key2, Object key3) {
		return (dataProviderArray[dataType.getNumber()]).getStaticData(key1, key2, key3);
	}

	private void loadAllStaticData() {

		dataProviderArray[GameStaticDataType.GAME_EXPRESSION_VALUE] = new GameExpressionStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "gameexpression.txt")).loader();

		dataProviderArray[GameStaticDataType.PlaneRandom_VALUE] = new PlaneRandomStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "PlaneRandom.txt")).loader();

		dataProviderArray[GameStaticDataType.PinInsertion_VALUE] = new PininsertionStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "pininsertion.txt")).loader();

//		dataProviderArray[GameStaticDataType.FLAPPYBIRD_VALUE] = new FlappyBirdStaticDataProvider(
//				new File(ProcessGlobalData.excelExportTXTDir, "flappybird.txt")).loader();

		dataProviderArray[GameStaticDataType.Lexicon_VALUE] = new GraffitiStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "lexicon.txt")).loader();

		dataProviderArray[GameStaticDataType.KEYWORD_VALUE] = new KeyWordStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "keyword.txt")).loader();

		dataProviderArray[GameStaticDataType.GLOBALCONF_VALUE] = new GlobalConfStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "global_conf.txt")).loader();

		dataProviderArray[GameStaticDataType.GAMETIMECONF_VALUE] = new GameTimeConfStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "gametime_conf.txt")).loader();

		dataProviderArray[GameStaticDataType.GROUPGAME_ITEM_VALUE] = new GroupGameItemStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "groupgameitem_config.txt")).loader();

	}

	@Override
	protected void bindCompleteCallBack(ChannelFuture future) {
		ProcessGlobalData.gameLog.basic("bindCompleteCallBack " + System.currentTimeMillis());
	}

	private boolean checkSupport(int code1, int code2) {
		if (code1 == MessageCode1Enum.CG.getValue() || code1 == MessageCode1Enum.RG.getValue()
				|| code1 == MessageCode1Enum.KeepLive.getValue() || code1 == MessageCode1Enum.ReConn.getValue()
				|| code1 == MessageCode1Enum.IGGA.getValue() || code1 == MessageCode1Enum.Manager.getValue()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param ioClient
	 * @param code1
	 * @param code2
	 * @param message
	 * @param gameLogicProcessor
	 * @return
	 */
	private boolean messagePretreatment(GameTCPIOClient ioClient, int code1, int code2, Object message,
			GameLogicProcessor gameLogicProcessor) {
		
		ProcessGlobalData.gameLog.basic("messagePretreatment " + code1 + " " + code2 + " " + message);
		
		if (NetCGJoinGame.ID.CODE1.getNumber() == code1 && NetCGJoinGame.ID.CODE2.getNumber() == code2) {

			if (ioClient.getGamePlayer() != null) {
				return false;
			}

			NetCGJoinGame cgJoinGame = (NetCGJoinGame) message;
			String gameUUID = null;

			if ((gameUUID = cgJoinGame.getGameUUID()) == null) {
				return false;
			}

			ProcessGlobalData.appLVRoomTickProcessorExecutor.execute(
					new GameRoomRunnable(this, ioClient, getThreadIndex(gameUUID), gameLogicProcessor, message));

			return false;
		} else if (NetIGGACreateGame.ID.CODE1.getNumber() == code1 && NetIGGACreateGame.ID.CODE2.getNumber() == code2) {
			NetIGGACreateGame createGameMSG = (NetIGGACreateGame) message;

			String gameUUID = null;

			if ((gameUUID = createGameMSG.getGameUUID()) == null) {
				return false;
			}

			ProcessGlobalData.appLVRoomTickProcessorExecutor.execute(
					new GameRoomRunnable(this, ioClient, getThreadIndex(gameUUID), gameLogicProcessor, message));

			return false;
		} else if (NetIGGATryConnGame.ID.CODE1.getNumber() == code1
				&& NetIGGATryConnGame.ID.CODE2.getNumber() == code2) {
			GameUnchangedBindRunnable runnable = new GameUnchangedBindRunnable(this, ioClient, message,
					gameLogicProcessor);

			ProcessGlobalData.appLVSLogicProcessorExecutor.execute(runnable);
			return false;
		} else if (ioClient.getStubID() < 0 && NetKeepLivePong.ID.CODE1_VALUE == code1
				&& NetKeepLivePong.ID.CODE2_VALUE == code2) {
			GameUnchangedBindRunnable runnable = new GameUnchangedBindRunnable(this, ioClient, message,
					gameLogicProcessor);

			ProcessGlobalData.appLVSLogicProcessorExecutor.execute(runnable);
			return false;
		} else if (code1 == NetReConn.ID.CODE1_VALUE && code2 == NetReConn.ID.CODE2_VALUE) {
			// 重连
			ProcessGlobalData.appLVSLogicProcessorExecutor
					.execute(new NetReConnUnchangedBindRunnable(this, ioClient, gameLogicProcessor, message));
			return false;
		}

		else if (code1 == NetIGGAActivationMPRRoom.ID.CODE1_VALUE && code2 == NetIGGAActivationMPRRoom.ID.CODE2_VALUE) {

			// 激活数据
			NetIGGAActivationMPRRoom requestMSG = (NetIGGAActivationMPRRoom) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getGameSceneId());

			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);
			return false;
		} else if (code1 == NetIGGASyncMPRData.ID.CODE1_VALUE && code2 == NetIGGASyncMPRData.ID.CODE2_VALUE) {
			// 同步数据
			NetIGGASyncMPRData requestMSG = (NetIGGASyncMPRData) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getRoomId());

			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);

			return false;
		}else if (code1 == NetIGGATiPlayer.ID.CODE1_VALUE && code2 == NetIGGATiPlayer.ID.CODE2_VALUE) {
			// 同步数据
			GameUnchangedBindRunnable runnable = new GameUnchangedBindRunnable(this, ioClient, message,
					gameLogicProcessor);

			ProcessGlobalData.appLVSLogicProcessorExecutor.execute(runnable);
			return false;
		}

		else if (code1 == NetCGJoinMPGRoom.ID.CODE1_VALUE && code2 == NetCGJoinMPGRoom.ID.CODE2_VALUE) {

			// 加入房间
			NetCGJoinMPGRoom requestMSG = (NetCGJoinMPGRoom) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getRoomUUID());

			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);
			return false;
		} else if (code1 == NetCGQuitMPGRoom.ID.CODE1_VALUE && code2 == NetCGQuitMPGRoom.ID.CODE2_VALUE) {
			// 退出房间
			NetCGQuitMPGRoom requestMSG = (NetCGQuitMPGRoom) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getRoomUUID());
			ProcessGlobalData.gameLog.basic("NetCGQuitMPGRoom " + (gameScene == null ? "null" : gameScene.getSceneId()));
			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);
			return false;
		} else if (code1 == NetCGGraffitiReady.ID.CODE1_VALUE && code2 == NetCGGraffitiReady.ID.CODE2_VALUE) {
			// 准备
			NetCGGraffitiReady requestMSG = (NetCGGraffitiReady) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getRoomUUID());

			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);

			return false;
		} else if (code1 == NetCGGraffitiSelectWord.ID.CODE1_VALUE && code2 == NetCGGraffitiSelectWord.ID.CODE2_VALUE) {
			// 选词
			NetCGGraffitiSelectWord requestMSG = (NetCGGraffitiSelectWord) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getRoomUUID());

			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);

			return false;
		} else if (code1 == NetCGGraffitiUpdateWord.ID.CODE1_VALUE && code2 == NetCGGraffitiUpdateWord.ID.CODE2_VALUE) {
			// 刷新词表
			NetCGGraffitiUpdateWord requestMSG = (NetCGGraffitiUpdateWord) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getRoomUUID());

			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);

			return false;
		} else if (code1 == NetCGGraffitiGuessWords.ID.CODE1_VALUE && code2 == NetCGGraffitiGuessWords.ID.CODE2_VALUE) {
			// 猜词
			NetCGGraffitiGuessWords requestMSG = (NetCGGraffitiGuessWords) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getRoomUUID());

			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);

			return false;
		} else if (code1 == NetCGGraffitiGoodOrBad.ID.CODE1_VALUE && code2 == NetCGGraffitiGoodOrBad.ID.CODE2_VALUE) {
			// 点评
			NetCGGraffitiGoodOrBad requestMSG = (NetCGGraffitiGoodOrBad) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getRoomUUID());

			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);

			return false;
		} else if (code1 == NetCGMPGRoomMSG.ID.CODE1_VALUE && code2 == NetCGMPGRoomMSG.ID.CODE2_VALUE) {
			// 聊天信息
			NetCGMPGRoomMSG requestMSG = (NetCGMPGRoomMSG) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getRoomUUID());

			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);

			return false;
		} else if (code1 == NetCGMPGThrowExpression.ID.CODE1_VALUE && code2 == NetCGMPGThrowExpression.ID.CODE2_VALUE) {
			// 发表情
			NetCGMPGThrowExpression requestMSG = (NetCGMPGThrowExpression) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getRoomUUID());

			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);

			return false;

		} else if (code1 == NetRobotConnTest.ID.CODE1_VALUE && code2 == NetRobotConnTest.ID.CODE2_VALUE) {

			// TODO @WuYang 测试完删除
			ProcessGlobalData.appLVSLogicProcessorExecutor
					.execute(new GameUnchangedBindRunnable(this, ioClient, message, gameLogicProcessor));

			return false;
		} else if (code1 == NetCGGraffitiPenLine.ID.CODE1_VALUE && code2 == NetCGGraffitiPenLine.ID.CODE2_VALUE) {
			// 画画
			NetCGGraffitiPenLine requestMSG = (NetCGGraffitiPenLine) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getRoomUUID());

			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);

			return false;
		} else if (code1 == NetCGGraffitiPenUpdate.ID.CODE1_VALUE && code2 == NetCGGraffitiPenUpdate.ID.CODE2_VALUE) {
			// 更新画笔
			NetCGGraffitiPenUpdate requestMSG = (NetCGGraffitiPenUpdate) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getRoomUUID());

			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);

			return false;
		} else if (code1 == NetCGGraffitiLineController.ID.CODE1_VALUE
				&& code2 == NetCGGraffitiLineController.ID.CODE2_VALUE) {
			// 更新画笔
			NetCGGraffitiLineController requestMSG = (NetCGGraffitiLineController) message;

			GameScene gameScene = GameServerGameSceneManager.getGameSceneById(requestMSG.getRoomUUID());

			addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);

			return false;
		} else if (code1 == NetKeepLivePing.ID.CODE1_VALUE && code2 == NetKeepLivePing.ID.CODE2_VALUE) {

			if (ioClient.getGamePlayer() instanceof GameScenePlayerUnit) {

				NetKeepLivePing requestMSG = (NetKeepLivePing) message;

				ProcessGlobalData.gameLog
						.basic("NetKeepLivePing Scene " + requestMSG.getNum() + " " + ioClient.toString());
				NetKeepLivePong.Builder builder = NetKeepLivePong.newBuilder();
				builder.setNum(requestMSG.getNum());

				ioClient.writeAndFlush(NetKeepLivePong.ID.CODE1_VALUE, NetKeepLivePong.ID.CODE2_VALUE, builder.build());
				return false;
			}

		} else if (code1 == NetKeepLivePong.ID.CODE1_VALUE && code2 == NetKeepLivePong.ID.CODE2_VALUE) {

			if (ioClient.getGamePlayer() instanceof GameScenePlayerUnit) {
				NetKeepLivePong requestMSG = (NetKeepLivePong) message;
				ioClient.resetKeepAlive();
				ProcessGlobalData.gameLog
						.basic("NetKeepLivePong Scene " + requestMSG.getNum() + " " + ioClient.toString());
				return false;
			}
		}

		return true;
	}

	private void addGameSceneCommand(GameScene gameScene, GameLogicProcessor gameLogicProcessor,
			GameTCPIOClient ioClient, Object message) {
		GameSceneCommand command = new GameSceneCommand(gameLogicProcessor, message, ioClient, this, gameScene);

		gameScene.addGameSceneCommand(command);
	}

	public int getThreadIndex(String gameUUID) {
		return Math.abs(gameUUID.hashCode()) % roomThreadPool.getLength();
	}

	//
	private static final NetServerStoping STOPING_STOPSERVER = NetServerStoping.newBuilder().build();

	private static final NetServerStoping STOPING_CLOSEENTRY = NetServerStoping.newBuilder().build();

	private void sendStopServerBeforeErrorMessage(GameTCPIOClient ioClient) {
		MessageWriteUtil.writeAndFlush(ioClient, STOPING_STOPSERVER);
	}

	private void sendCloseEntryServerBeforeErrorMessage(GameTCPIOClient ioClient) {
		MessageWriteUtil.writeAndFlush(ioClient, STOPING_CLOSEENTRY);
	}

	private static final String RECVLOG_1 = "RECV MSG ";

	private static final String RECVLOG_2 = " ";

	private static final String RECVLOG_3 = " MSG IS ";

	private static final String RECVLOG_4 = " NULL";

	private static final String RECVLOG_5 = " STUBID ";

	@Override
	public void executeMSG(GameTCPIOClient ioClient, int code1, int code2, Object message,
			GameLogicProcessor gameLogicProcessor) {

		// 不支持的类型直接返回
		if (!checkSupport(code1, code2)) {
			ProcessGlobalData.gameLog.basic("NOT Support Execute MSG " + code1 + " " + code2 + " MSG IS "
					+ (message == null ? " NULL" : message.getClass().getSimpleName()) + " " + message.toString());
			return;
		}

		if (isRecvMSGLog()) {
			StringBuilder builder = (StringBuilder) ProcessGlobalData
					.getThreadLocalValue(ThreadLocalEnum.RECV_MSG_StringBuilder.ordinal());
			builder.setLength(0);

			builder.append(RECVLOG_1).append(code1).append(RECVLOG_2).append(code2).append(RECVLOG_3);

			if (message == null) {
				builder.append(RECVLOG_4);
			} else {
				builder.append(message.getClass().getSimpleName());
			}

			if (ioClient != null) {
				builder.append(RECVLOG_5).append(ioClient.getStubID());
			}

			builder.append(RECVLOG_2).append(message.toString());
			ProcessGlobalData.gameLog.basic(builder.toString());
		}

		if (stopBeforeAction) {
			if (code1 == MessageCode1Enum.CG.getValue()) {
				sendStopServerBeforeErrorMessage(ioClient);
				ProcessGlobalData.gameLog.basic("Server StopBeforeAction " + code1 + " " + code2 + " Return");
				return;
			}
		}

		if (closeEntryAction) {
			if (code1 == MessageCode1Enum.CG.getValue() /* && code2 != NetCGQuitRoom.ID.CODE2_VALUE */) {
				sendCloseEntryServerBeforeErrorMessage(ioClient);
				ProcessGlobalData.gameLog.basic("Server CloseEntryAction " + code1 + " " + code2 + " Return");
				return;
			}
		}

		// 预处理
		if (messagePretreatment(ioClient, code1, code2, message, gameLogicProcessor)) {
			GameRoomPlayerUnit playerUnit = null;

			if ((playerUnit = (GameRoomPlayerUnit) ioClient.getGamePlayer()) == null) {
				ProcessGlobalData.gameLog.basic("NOT FOUND playerUnit Code1 " + code1 + " Code2 " + code2 + " "
						+ ioClient.getStubID() + " " + message.toString());
				return;
			}

			ProcessGlobalData.appLVRoomTickProcessorExecutor
					.execute(new GameRoomRunnable(this, playerUnit, gameLogicProcessor, message));
		}
	}

	public void asyncSendRobotCommand(GameRoomPlayerUnit robotPlayerUnit, GameLogicProcessor gameLogicProcessor,
			Object message, long startTime, int delay) {
		DelayGameRoomRunnable delayRunnable = new DelayGameRoomRunnable(startTime, delay,
				new GameRoomRunnable(this, robotPlayerUnit, gameLogicProcessor, message));

		ProcessGlobalData.appLVRoomTickProcessorExecutor.execute(delayRunnable);
	}

	public void sendKeeplive(GameTCPIOClient ioClient) throws Exception {
		NetKeepLivePing.Builder builder = NetKeepLivePing.newBuilder();
		builder.setNum(ioClient.getTryCount());

		ProcessGlobalData.gameLog
				.basic("sendKeeplive " + ioClient.isServerIni() + " " + ioClient.getChannelContext().channel());

		if (ioClient.isServerIni()) {
			ioClient.writeAndFlush(NetKeepLivePing.ID.CODE1_VALUE, NetKeepLivePing.ID.CODE2_VALUE, -1, builder.build());
		} else {
			ioClient.writeAndFlush(NetKeepLivePing.ID.CODE1_VALUE, NetKeepLivePing.ID.CODE2_VALUE, builder.build());
		}
	}

	private static final NetCGMPGPlayerOfflineLogicProcessor playerOfflineLogicProcessor = new NetCGMPGPlayerOfflineLogicProcessor();

	@Override
	protected void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		ProcessGlobalData.gameLog.basic("channelUnregistered " + channelCTXToAddressString(ctx));

		GameTCPIOClient ioClient = ctx.channel().attr(ChannelAttributeContext.ATTR_IO_CLIENT).get();

		if (ioClient == null) {
			ProcessGlobalData.gameLog.basic("channelUnregistered " + channelCTXToAddressString(ctx) + " ioClient == null");
			return;
		}

		ProcessGlobalData.gameLog.basic("channelUnregistered " + channelCTXToAddressString(ctx) + " StubID " + ioClient.get32StubID());

		if (ioClient.getGamePlayer() instanceof GameScenePlayerUnit) {
			
			GameScenePlayerUnit gamePlayer = (GameScenePlayerUnit) ioClient.getGamePlayer();
			
			if( gamePlayer.isReconnclose() ) {
				gamePlayer.setReconnclose(false);
				return;
			}
			GameScene gameScene = gamePlayer.getGameScene();
			Object message = NetCGQuitMPGRoom.newBuilder().setRoomUUID(gameScene.getSceneId()).build();
			GameSceneCommand command = new GameSceneCommand(playerOfflineLogicProcessor, message, ioClient, this,
					gameScene);
			gameScene.addGameSceneCommand(command);
			return;
		}

		GameRoomPlayerUnit gamePlayer = (GameRoomPlayerUnit) ioClient.getGamePlayer();

		if (gamePlayer == null) {
			ProcessGlobalData.gameLog.basic("channelUnregistered " + channelCTXToAddressString(ctx) + " playerUnit == null");
			return;
		}
		
		ProcessGlobalData.gameLog.basic("channelUnregistered " + channelCTXToAddressString(ctx) + " PlayerID " + gamePlayer.get32PlayerID());

		GameRoom gameRoom = gamePlayer.getGameRoom();

		if (gameRoom == null) {
			ProcessGlobalData.gameLog.basic("channelUnregistered " + channelCTXToAddressString(ctx) + " gameRoom == null");
			return;
		}

		gameRoom.quitGame(gamePlayer.getPlayerID());

		removeGameRoomPlayerUnit((int) gamePlayer.getPlayerID());
		
	}
	public void openRecvMSGLog() {
		recvMSGLog = true;
	}

	public void closeRecvMSGLog() {
		recvMSGLog = false;
	}

	public boolean isRecvMSGLog() {
		return recvMSGLog;
	}

	public void closeEntry() {
		closeEntryAction = true;
	}

	public void openEntry() {
		closeEntryAction = false;
	}

	public boolean isOpenEntry() {
		return closeEntryAction;
	}

	public void stopServer() {
		stopBeforeAction = true;
	}

	public void openServer() {
		stopBeforeAction = false;
	}

	public boolean isStopServer() {
		return stopBeforeAction;
	}
}
