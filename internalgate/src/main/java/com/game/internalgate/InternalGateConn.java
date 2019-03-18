package com.game.internalgate;

import com.game.framework.ProcessGlobalData;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneCommand;
import com.game.framework.network.GameLogicProcessor;
import com.game.framework.network.GameTCPClient;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.network.MessageContext;
import com.game.framework.network.node.ServerNode;
import com.game.internalgate.bean.IniGatePlayerInfo;
import com.game.internalgate.service.multiplayergame.GateServerGameSceneManager;
import com.game.message.MessageCode1Enum;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_Common.ServerNodeType;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGActivationMPRRoom;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGSyncMPRData;
import com.game.message.proto.ProtoContext_GAIG.NetIGGATryConnGame;
import com.game.message.proto.ProtoContext_RIG.NetIGRTryConnRoom;
import com.google.protobuf.GeneratedMessage;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

public class InternalGateConn extends GameTCPClient {

	private ServerNode<InternalGateConn> serverNode = null;

	private static final NetIGRTryConnRoom NOTIFY_ROOM;
	private static final NetIGGATryConnGame NOTIFY_GAME;

	static {
		NOTIFY_ROOM = NetIGRTryConnRoom.newBuilder().build();
		NOTIFY_GAME = NetIGGATryConnGame.newBuilder().build();
	}

	public InternalGateConn(ServerNode<InternalGateConn> serverNode, String subSystemName, int nodeId) {
		super(subSystemName);
		this.serverNode = serverNode;
		this.nodeId = nodeId;
	}

	public ServerNode<InternalGateConn> getServerNode() {
		return serverNode;
	}

	@Override
	protected void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		ProcessGlobalData.gameLog.basic(subSystemName + " userEventTriggered " + channelCTXToAddressString(ctx) + " "
				+ evt.getClass().getSimpleName());
	}

	@Override
	protected void connectCompleteCallBack(ChannelFuture f) throws Exception {
		super.connectCompleteCallBack(f);
		ProcessGlobalData.gameLog.basic("Node-" + subSystemName + " Connect Complete");

		int nodeType = getServerNode().getNodeType();

		if (nodeType == ServerNodeType.GAME_VALUE) {
			MessageWriteUtil.writeAndFlush(ioClient, MessageContext.INI_GATE_STUBID, NOTIFY_GAME);
		} else if (nodeType == ServerNodeType.ROOM_VALUE) {
			MessageWriteUtil.writeAndFlush(ioClient, MessageContext.INI_GATE_STUBID, NOTIFY_ROOM);
		}

	}

	private boolean messagePretreatment(GameTCPIOClient ioClient, int code1, int code2, Object message,
			GameLogicProcessor gameLogicProcessor) {

		if (code1 == MessageCode1Enum.GR.getValue()) {
			int roomServerID = ioClient.get32StubID();

			InternalGateServer gateServer = (InternalGateServer) ProcessGlobalData.gameProcess;

			ServerNode<InternalGateConn> serverNode = gateServer.getServerNode(roomServerID);

			if (serverNode.getNodeType() == ServerNodeType.ROOM_VALUE) {
				serverNode.getNode().getIoClient().writeAndFlush(code1, code2, ioClient.getHighStubID(),
						(GeneratedMessage) message);
			}

			return false;
		} else if (code1 == MessageCode1Enum.RG.getValue()) {
			int gameServerID = ioClient.get32StubID();

			InternalGateServer gateServer = (InternalGateServer) ProcessGlobalData.gameProcess;

			ServerNode<InternalGateConn> serverNode = gateServer.getServerNode(gameServerID);

			if (serverNode.getNodeType() == ServerNodeType.GAME_VALUE) {
				serverNode.getNode().getIoClient().writeAndFlush(code1, code2, ioClient.getHighStubID(),
						(GeneratedMessage) message);
			}

			return false;
		} else if (code1 == MessageCode1Enum.RC.getValue()) {
			int targetPlayerID = ioClient.get32StubID();

			InternalGateServer gateServer = (InternalGateServer) ProcessGlobalData.gameProcess;

			IniGatePlayerInfo playerInfo = gateServer.getPlayerInfo(targetPlayerID);

			int roomServer = playerInfo.getRoomServer();

			if (roomServer == -1) {
				// TODO @SuChen 用户不在线是否需要转发
			} else {
				ServerNode<InternalGateConn> serverNode = gateServer.getServerNode(roomServer);

				if (serverNode.getNodeType() == ServerNodeType.GAME_VALUE) {
					serverNode.getNode().getIoClient().writeAndFlush(code1, code2, ioClient.getHighStubID(),
							(GeneratedMessage) message);
				}
			}

			return false;
		}

		return true;
	}

	@Override
	public void executeMSG(GameTCPIOClient ioClient, int code1, int code2, Object message,
			GameLogicProcessor gameLogicProcessor) {

		if (ioClient != null) {
			ProcessGlobalData.gameLog.basic("RECV MSG " + code1 + " " + code2 + " MSG IS "
					+ (message == null ? " NULL" : message.getClass().getSimpleName()) + " STUBID "
					+ ioClient.getStubID() + " " + message.toString());
		} else {
			ProcessGlobalData.gameLog.basic("RECV MSG " + code1 + " " + code2 + " MSG IS "
					+ (message == null ? " NULL" : message.getClass().getSimpleName()) + " " + message.toString());
		}

		if (messagePretreatment(ioClient, code1, code2, message, gameLogicProcessor)) {

			if (code1 == NetGAIGSyncMPRData.ID.CODE1_VALUE && code2 == NetGAIGSyncMPRData.ID.CODE2_VALUE) {
				NetGAIGSyncMPRData requestMSG = (NetGAIGSyncMPRData) message;
				GameScene gameScene = GateServerGameSceneManager.getGameSceneFromMap(requestMSG.getRoomId());
				addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);
				return;
			} else if (code1 == NetGAIGActivationMPRRoom.ID.CODE1_VALUE
					&& code2 == NetGAIGActivationMPRRoom.ID.CODE2_VALUE) {
				NetGAIGActivationMPRRoom requestMSG = (NetGAIGActivationMPRRoom) message;
				GameScene gameScene = GateServerGameSceneManager.getGameSceneFromMap(requestMSG.getGameSceneId());
				addGameSceneCommand(gameScene, gameLogicProcessor, ioClient, message);
				return;
			}

			InternalGateUnchangedBindRunnable runnable = new InternalGateUnchangedBindRunnable(this, ioClient,
					gameLogicProcessor, message);

			ProcessGlobalData.appLVSLogicProcessorExecutor.execute(runnable);
		}

	}

	private void addGameSceneCommand(GameScene gameScene, GameLogicProcessor gameLogicProcessor,
			GameTCPIOClient ioClient, Object message) {
		GameSceneCommand command = new GameSceneCommand(gameLogicProcessor, message, ioClient, this, gameScene);

		gameScene.addGameSceneCommand(command);
	}

}
