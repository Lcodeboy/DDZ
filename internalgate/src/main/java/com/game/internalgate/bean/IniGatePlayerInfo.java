package com.game.internalgate.bean;

import java.util.concurrent.atomic.AtomicBoolean;

import com.game.common.staticdata.bean.RobatStaticData;
import com.game.framework.network.node.ServerNode;
import com.game.internalgate.InternalGateConn;
import com.game.message.proto.ProtoContext_Common.PlayerState;

public class IniGatePlayerInfo {
	// 如果是-1
	private volatile int roomServer = -1;
	//
	private AtomicBoolean isGame = new AtomicBoolean(false);
	//
	private volatile long playerId = 0;

	//	GameServer节点
	private ServerNode<InternalGateConn> serverNode = null;
	//
	private volatile int gameSceneId = 0;
	//
	private volatile boolean applyGameScene = false;
	
	public PlayerState getPlayerState() {
		if (roomServer == -1) {
			return PlayerState.OFFLINE;
		} else if (isGame.get()) {
			return PlayerState.GAMEING;
		}
		return PlayerState.ONLINE;

	}
	
	public ServerNode<InternalGateConn> getServerNode() {
		return serverNode;
	}
	
	public void setServerNode(ServerNode<InternalGateConn> serverNode) {
		this.serverNode = serverNode;
	}

	public static IniGatePlayerInfo valueOf(RobatStaticData robatStaticData) {
		IniGatePlayerInfo info = new IniGatePlayerInfo();

		info.playerId = robatStaticData.getId();
		return info;
	}
	
	public IniGatePlayerInfo() {
		
	}
	
	public int getRoomServer() {
		return roomServer;
	}

	public void setRoomServer(int roomServer) {
		this.roomServer = roomServer;
	}

	public boolean isGame() {
		return isGame.get();
	}

	public void setGame(boolean b) {
		isGame.set(b);
	}
	
	public boolean casGame( boolean expect, boolean update ) {
		return isGame.compareAndSet(expect, update);
	}
	
	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public int getGameSceneId() {
		return gameSceneId;
	}

	public void setGameSceneId(int gameSceneId) {
		this.gameSceneId = gameSceneId;
	}
	
	public boolean isApplyGameScene() {
		return applyGameScene;
	}

	public void setApplyGameScene(boolean applyGameScene) {
		this.applyGameScene = applyGameScene;
	}

	
}
