
package com.game.room;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.game.common.CommonConstantContext;
import com.game.common.cache.redis.DefaultRedisContextFacade;
import com.game.common.staticdata.GiftShopStaticDataProvider;
import com.game.common.staticdata.KeyWordStaticDataProvider;
import com.game.common.staticdata.PunlishmentStaticDataProvider;
import com.game.common.staticdata.RobatStaticDataProvider;
import com.game.common.staticdata.bean.RobatStaticData;
import com.game.common.unit.RobotPlayerUnit;
import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.threadpool.UnchangedBindThreadPool;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.network.GameLogicProcessor;
import com.game.framework.network.GameLogicProcessorUnchangedBindRunnable;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.network.GameTCPIOClientGroup;
import com.game.framework.network.GameTCPServer;
import com.game.framework.network.context.ChannelAttributeContext;
import com.game.framework.process.GameProcess;
import com.game.message.MessageCode1Enum;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_BASIC.NetKeepLivePing;
import com.game.message.proto.ProtoContext_BASIC.NetReConn;
import com.game.message.proto.ProtoContext_BASIC.NetServerStoping;
import com.game.message.proto.ProtoContext_CR.NetCRJoinRoom;
import com.game.message.proto.ProtoContext_CR.NetCRQuitWaitJoinGame;
import com.game.message.proto.ProtoContext_CR.NetRCDuadMSG;
import com.game.message.proto.ProtoContext_CR.NetRCStateNotify;
import com.game.message.proto.ProtoContext_Common.ChatMSGType;
import com.game.message.proto.ProtoContext_Common.DuadMSG;
import com.game.message.proto.ProtoContext_Common.GameStaticDataType;
import com.game.message.proto.ProtoContext_Common.PlayerState;
import com.game.message.proto.ProtoContext_Common.PlayerType;
import com.game.message.proto.ProtoContext_RD.NetRDPlayerQuitRoom;
import com.game.message.proto.ProtoContext_RIG.NetRIGPlayerQuitRoom;
import com.game.room.concurrency.NetCRJoinRoomUnchangedBindRunnable;
import com.game.room.concurrency.NetReConnUnchangedBindRunnable;
import com.game.room.concurrency.RoomUnchangedBindCreateThreadCallBack;
import com.game.room.concurrency.StopBeforeActionUnchangedBindRunnable;
import com.game.room.unit.RoomPlayerUnit;
import com.game.room.unit.data.RoomPlayerRelation;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

/**
 * 房间服
 */
public class RoomServer extends GameTCPServer {

	@SuppressWarnings("rawtypes")
	private AbstractStaticDataProvider[] dataProviderArray = null;
	//
	private ConcurrentHashMap<Integer, RoomPlayerUnit> roomPlayerMap = new ConcurrentHashMap<>();
	//
	private ConcurrentHashMap<Integer, RobotPlayerUnit> roomRobotMap = new ConcurrentHashMap<>();
	//

	private volatile GameTCPIOClient iniGateConn = null;

	private volatile GameTCPIOClient dataCenterConn = null;

	private int maxPeople = 0;
	//

	public static final String SUB_SYSTEM_NAME = "RoomServer";
	//
	private volatile boolean stopBeforeAction = false;
	//
	private volatile boolean closeEntryAction = false;
	//
	private volatile boolean recvMSGLog = true;
	

	public RoomServer() {
		super("RoomServer");
	}

	public String getSubSystemName() {
		return SUB_SYSTEM_NAME;
	}


	public RobotPlayerUnit getRobotPlayerUnit(int playerId) {
		return roomRobotMap.get(playerId);
	}




	public GameTCPIOClient getIniGateConn() {
		return iniGateConn;
	}

	public void setIniGateConn(GameTCPIOClient iniGateConn) {
		this.iniGateConn = iniGateConn;
	}

	public GameTCPIOClient getDataCenterConn() {
		return dataCenterConn;
	}

	public void setDataCenterConn(GameTCPIOClient dataCenterConn) {
		this.dataCenterConn = dataCenterConn;
	}

	public int getMaxPeople() {
		return maxPeople;
	}

	public int getPlayerCounter() {
		return roomPlayerMap.size();
	}
	/**
	 * 返回room里的在线玩家
	 * 
	 * @param playerId
	 * @return
	 */
	public RoomPlayerUnit getInServerRoomPlayerUnit(int playerId) {
		RoomPlayerUnit playerUnit = roomPlayerMap.get(playerId);

		if (playerUnit == null) {
			return null;
		}

		return playerUnit.getPlayerState() == PlayerState.OFFLINE ? null : playerUnit;
	}

	public RoomPlayerUnit getRoomPlayerUnit(int playerId) {
		return roomPlayerMap.get(playerId);
	}

	public RoomPlayerUnit putRoomPlayerUnit(RoomPlayerUnit playerUnit) {
		return roomPlayerMap.put(playerUnit.get32PlayerID(), playerUnit);
	}

	// 轻易不要调用
	public ConcurrentHashMap<Integer, RoomPlayerUnit> getAllRoomPlayerUnit() {
		return roomPlayerMap;
	}

	public void init(GameProcess gameProcess) throws Exception {
		super.init(gameProcess);
		String section = gameProcess.getSection();

		ProcessGlobalData.excelExportTXTDir = new File(
				System.getenv(ProcessGlobalData.ImportantEnv.GAME_EXCEL_EXPORTTXT));

		dataProviderArray = new AbstractStaticDataProvider[GameStaticDataType.values().length + 1];

		int count1 = Integer.valueOf(ProcessGlobalData.configReader.getParam(section, "logicThreadPoolCount1"));
		int count2 = Integer.valueOf(ProcessGlobalData.configReader.getParam(section, "logicThreadPoolCount2"));

		tcpIOClientGroup = new GameTCPIOClientGroup[count2];

		for (int i = 0; i < tcpIOClientGroup.length; i++) {
			tcpIOClientGroup[i] = new GameTCPIOClientGroup();
		}

		// 节点ID
		nodeId = Integer.valueOf(ProcessGlobalData.configReader.getParam(section, "nodeId"));
		// 最大人数
		maxPeople = Integer.valueOf(ProcessGlobalData.configReader.getParam(section, "maxPeople"));

		if (maxPeople <= 0 || nodeId < 0) {
			throw new IllegalArgumentException();
		}

		ProcessGlobalData.appLVSLogicProcessorExecutor = new UnchangedBindThreadPool(count1, count2, "Logic",
				new RoomUnchangedBindCreateThreadCallBack());

		ProcessGlobalData.redisContextFacade = new DefaultRedisContextFacade(ProcessGlobalData.redisContext);

		// 加载全部的静态数据
		loadAllStaticData();
		// 加载机器人
		loadAllRobat();
		//

		String chatLogStr = ProcessGlobalData.configReader.getParam(section, "chatLogDir");

		if (chatLogStr == null) {
			throw new IllegalArgumentException();
		}

		File chatLogDir = new File(chatLogStr);

		chatLogDir.mkdirs();


	}

	private void loadAllRobat() {
		RobotPlayerUnit playerUnit = null;

		List<Object> allList = getAllStaticData(GameStaticDataType.ROBATDATA);

		for (Object obj : allList) {
			playerUnit = RobotPlayerUnit.valueOf((RobatStaticData) obj);
			roomRobotMap.put(playerUnit.getPlayerId(), playerUnit);
		}
	}

	private void loadAllStaticData() {

		dataProviderArray[GameStaticDataType.GIFTSHOP_VALUE] = new GiftShopStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "giftshop.txt")).loader();

		dataProviderArray[GameStaticDataType.ROBATDATA_VALUE] = new RobatStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "robot.txt")).loader();

		dataProviderArray[GameStaticDataType.KEYWORD_VALUE] = new KeyWordStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "keyword.txt")).loader();

		dataProviderArray[GameStaticDataType.PUBLISTMENT_VALUE] = new PunlishmentStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "punishment.txt")).loader();
	}

	//
	public Object getStaticData(GameStaticDataType dataType) {
		return (dataProviderArray[dataType.getNumber()]).getAllStaticDataObject();
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

	@SuppressWarnings("unchecked")
	public List<Object> getAllStaticData(GameStaticDataType dataType) {
		return (dataProviderArray[dataType.getNumber()]).getAllStaticData();
	}

	@Override
	protected void bindCompleteCallBack(ChannelFuture future) {
		ProcessGlobalData.gameLog.basic("bindCompleteCallBack " + System.currentTimeMillis());
	}

	private boolean checkSupport(int code1, int code2) {
		if (code1 == MessageCode1Enum.CR.getValue() || code1 == MessageCode1Enum.RR.getValue()
				|| code1 == MessageCode1Enum.DR.getValue() || code1 == MessageCode1Enum.KeepLive.getValue()
				|| code1 == MessageCode1Enum.ReConn.getValue() || code1 == MessageCode1Enum.IGR.getValue()
				|| code1 == MessageCode1Enum.GR.getValue() || code1 == MessageCode1Enum.WEBR.getValue()
				|| code1 == MessageCode1Enum.Manager.getValue()) {
			return true;
		}

		return false;
	}

	public boolean checkToKen(int playerId, String requestToken) {
		try {
			DefaultRedisContextFacade redisContextFacade = (DefaultRedisContextFacade) ProcessGlobalData.redisContextFacade;

			String localToKen = redisContextFacade.getPlayerToken(playerId);

			if (localToKen == null) {
				return false;
			}
			return requestToken.equals(localToKen);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
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

		// 消息入栈的日志记录

		if ( isRecvMSGLog() ) {
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
			if (code1 == MessageCode1Enum.CR.getValue()) {
				sendStopServerBeforeErrorMessage(ioClient);
				ProcessGlobalData.gameLog.basic("Server StopBeforeAction " + code1 + " " + code2 + " Return");
				return;
			}
		}

		if (closeEntryAction) {
			if (code1 == MessageCode1Enum.CR.getValue()) {
				if( code2 != NetCRQuitWaitJoinGame.ID.CODE2_VALUE ) {
					sendCloseEntryServerBeforeErrorMessage(ioClient);
					ProcessGlobalData.gameLog.basic("Server CloseEntryAction " + code1 + " " + code2 + " Return");
					return;
				}
			}
		}

		if (code1 == NetCRJoinRoom.ID.CODE1_VALUE && code2 == NetCRJoinRoom.ID.CODE2_VALUE) {
			// 预处理加入房间
			ProcessGlobalData.appLVSLogicProcessorExecutor
					.execute(new NetCRJoinRoomUnchangedBindRunnable(this, ioClient, gameLogicProcessor, message));
			return;
		} else if (code1 == NetReConn.ID.CODE1_VALUE && code2 == NetReConn.ID.CODE2_VALUE) {
			// 重连
			ProcessGlobalData.appLVSLogicProcessorExecutor
					.execute(new NetReConnUnchangedBindRunnable(this, ioClient, gameLogicProcessor, message));
			return;
		}

		ProcessGlobalData.appLVSLogicProcessorExecutor
				.execute(new GameLogicProcessorUnchangedBindRunnable(this, ioClient, gameLogicProcessor, message));
	}

	public void stopBeforeAction() throws Exception {
		stopBeforeAction = true;

		UnchangedBindThreadPool threadPool = (UnchangedBindThreadPool) ProcessGlobalData.appLVSLogicProcessorExecutor;
		StopBeforeActionUnchangedBindRunnable runnable = null;
		for (int i = 1; i <= threadPool.getCount2(); i++) {
			runnable = new StopBeforeActionUnchangedBindRunnable(i, this, threadPool);
			threadPool.execute(runnable);
		}
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

	@Override
	protected void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		// 停服时不做数据的改动
		if (stopBeforeAction) {
			return;
		}
		GameTCPIOClient ioClient = ctx.channel().attr(ChannelAttributeContext.ATTR_IO_CLIENT).get();
		ProcessGlobalData.gameLog.basic("channelUnregistered " + channelCTXToAddressString(ctx));

		if (ioClient == null) {
			ProcessGlobalData.gameLog
					.basic("channelUnregistered " + channelCTXToAddressString(ctx) + " ioClient == null");

			return;
		}
		RoomPlayerUnit playerUnit = (RoomPlayerUnit) ioClient.getGamePlayer();

		if (playerUnit == null) {
			ProcessGlobalData.gameLog
					.basic("channelUnregistered " + channelCTXToAddressString(ctx) + " playerUnit == null");
			return;
		} else {
			int playerId = playerUnit.get32PlayerID();
			if (!ioClient.isActiveClose()) {
				// 主动关闭由主动关闭放处理后续逻辑
				if (playerUnit.getPlayerState() == PlayerState.OFFLINE) {
					ProcessGlobalData.gameLog.basic("channelUnregistered " + channelCTXToAddressString(ctx)
							+ " playerUnit " + playerUnit.get32PlayerID() + " is OffLine");
					return;
				}
				playerOfflineAction(playerUnit, false);
			} else {
				ProcessGlobalData.gameLog.basic("channelUnregistered playerId " + playerId + " "
						+ channelCTXToAddressString(ctx) + " isActiveClose == true");
			}
		}
	}

	public void playerOfflineAction(RoomPlayerUnit playerUnit, boolean stopServer) {
		DefaultRedisContextFacade redisContextFacade = (DefaultRedisContextFacade) ProcessGlobalData.redisContextFacade;
		int playerId = playerUnit.get32PlayerID();

		ProcessGlobalData.gameLog.basic("playerOfflineAction playerId " + playerId + " "
				+ channelCTXToAddressString(playerUnit.remoteChannelHandlerContext()));

		playerUnit.offline();
		// ///////////////////////////
		// 发送退出聊天
		// ///////////////////////////

		int targetPlayerId = playerUnit.getChatTargetPlayerId();

		if (CommonConstantContext.hasPlayer(targetPlayerId)) {
			RoomPlayerUnit targetPlayerUnit = getRoomPlayerUnit(targetPlayerId);
			NetRCDuadMSG.Builder rcDuadBuilder = NetRCDuadMSG.newBuilder();

			DuadMSG.Builder duadMSGBuilder = DuadMSG.newBuilder();
			duadMSGBuilder.setSendPlayerID(playerId);
			duadMSGBuilder.setReceivePlayerID(targetPlayerId);
			duadMSGBuilder.setMsgType(ChatMSGType.QUIT_CHAT);
			duadMSGBuilder.setMsgkey("");

			if (targetPlayerUnit == null) {
				MessageWriteUtil.writeAndFlush(getIniGateConn(), rcDuadBuilder.build());
			} else {
				MessageWriteUtil.writeAndFlush(targetPlayerUnit.getTCPIOClient(), rcDuadBuilder.build());
			}
		}

		// //////////////////////////
		// 向数据中心和网关同步状态-用户离线通知
		// //////////////////////////

		NetRDPlayerQuitRoom.Builder quitDataCenterNotify = NetRDPlayerQuitRoom.newBuilder();
		NetRIGPlayerQuitRoom.Builder quitGateNotify = NetRIGPlayerQuitRoom.newBuilder();
		// NetRIGPlayerQuitGame
		quitDataCenterNotify.setPlayerId(playerId);
		quitGateNotify.setPlayerId(playerId);

		MessageWriteUtil.writeAndFlush(getDataCenterConn(), playerUnit.getPlayerID(), quitDataCenterNotify.build());
		MessageWriteUtil.writeAndFlush(getIniGateConn(), playerUnit.getPlayerID(), quitGateNotify.build());

		HashMap<Integer, RoomPlayerRelation> friendMap = playerUnit.getRoomPlayerAllData().getFriendMap();
		HashMap<Integer, RoomPlayerRelation> tempFriendMap = playerUnit.getRoomPlayerAllData().getTempFriendMap();
		RoomPlayerUnit roomPlayerUnit = null;

		// 向用一节点的好友和临时好友同步状态-用户离线通知
		NetRCStateNotify.Builder builder = NetRCStateNotify.newBuilder();

		builder.setPlayerState(PlayerState.OFFLINE);
		builder.setPlayerID(playerId);
		NetRCStateNotify result = builder.build();
		if (friendMap != null) {
			for (RoomPlayerRelation friend : friendMap.values()) {

				if (friend.getPlayerType() == PlayerType.ROBAT) {
					continue;
				}

				if ((roomPlayerUnit = roomPlayerMap.get(friend.getPlayerid())) == null
						|| roomPlayerUnit.getPlayerState() == PlayerState.OFFLINE) {
					continue;
				}

				MessageWriteUtil.writeAndFlush(roomPlayerUnit.getTCPIOClient(), result);
			}
		}

		if (tempFriendMap != null) {
			for (RoomPlayerRelation friend : tempFriendMap.values()) {

				if (friend.getPlayerType() == PlayerType.ROBAT) {
					continue;
				}

				if ((roomPlayerUnit = roomPlayerMap.get(friend.getPlayerid())) == null
						|| roomPlayerUnit.getPlayerState() == PlayerState.OFFLINE) {
					continue;
				}

				MessageWriteUtil.writeAndFlush(roomPlayerUnit.getTCPIOClient(), result);
			}
		}

		redisContextFacade.setRoomPeopleCounter(nodeId, -1);

		if (!stopServer) {
			// TODO @SuChen 需要异步删除从缓存中删除
			roomPlayerMap.remove(playerId);
		}

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
