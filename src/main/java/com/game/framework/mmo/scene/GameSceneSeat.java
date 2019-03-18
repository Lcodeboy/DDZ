package com.game.framework.mmo.scene;

import com.game.framework.util.Clear;

/**
 * 表示副本的一个座位, 表示进到副本的人
 */
public class GameSceneSeat {
	//	
	private int position = 0;
	//	
	private long playerId = -1;
	//	
	private long joinTime = 0;
	//	
	private long readyTime = 0;
	//	
	private boolean ready = false;
	//	申请占座的时间
	//	为0则表示没有占座
	private long applyTime = 0;
	//
	private boolean prevReady = false;
	//	
	private boolean prevTickReady = false;
	//	
	private boolean prevTickJoin = false;
	//	
	private Clear playerData = null;
	//	
	private GameScenePlayerUnit gameScenePlayerUnit = null;
	//
	private boolean online = false;
	//
	private boolean sendApplyTime = false;
	//
	private boolean sendJoinTime = false;
	
	private boolean sendApplyMPGRoom = false;
	
	private long quitPlayerId = 0;
	
	public GameSceneSeat( int position ) {
		this.position = position;
	}
	
	public void restart() {
		
	}
	
	public void clear() {
		clear(false);
	}
	
	public long getQuitPlayerId() {
		return quitPlayerId;
	}
	
	public void clearQuitPlayerId() {
		quitPlayerId = -1;
	}
	
	public void clear(boolean savePlayerId) {
		if( savePlayerId ) {
			quitPlayerId = playerId;
		} else {
			quitPlayerId = -1;
		}
		
		sendApplyMPGRoom = false;
//		position = -1;
		playerId = -1;
		joinTime = 0;
		readyTime = 0;
		applyTime = 0;
		ready = true;
		prevReady = false;
		prevTickReady = false;
		prevTickJoin = false;
		online = false;
		gameScenePlayerUnit = null;
		sendApplyTime = false;
		sendJoinTime = false;
		
		if( playerData != null ) {
			playerData.clearData();
		}
	}
	
	public boolean isPrevTickReady() {
		return prevTickReady;
	}

	public void setPrevTickReady(boolean prevTickReady) {
		this.prevTickReady = prevTickReady;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

	public long getReadyTime() {
		return readyTime;
	}

	public void setReadyTime(long readyTime) {
		this.readyTime = readyTime;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(long applyTime) {
		this.applyTime = applyTime;
	}

	public boolean isPrevTickJoin() {
		return prevTickJoin;
	}

	public void setPrevTickJoin(boolean prevTickJoin) {
		this.prevTickJoin = prevTickJoin;
	}

	public Object getPlayerData() {
		return playerData;
	}

	public void setPlayerData(Clear playerData) {
		this.playerData = playerData;
	}

	public GameScenePlayerUnit getGameScenePlayerUnit() {
		return gameScenePlayerUnit;
	}

	public void setGameScenePlayerUnit(GameScenePlayerUnit gameScenePlayerUnit) {
		this.gameScenePlayerUnit = gameScenePlayerUnit;
	}
	
	public int getPosition() {
		return position;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public boolean isJoin() {
		return joinTime != 0;
	}

	public boolean isPrevReady() {
		return prevReady;
	}

	public void setPrevReady(boolean prevReady) {
		this.prevReady = prevReady;
	}

	public boolean isSendApplyTime() {
		return sendApplyTime;
	}

	public void setSendApplyTime(boolean sendApplyTime) {
		this.sendApplyTime = sendApplyTime;
	}

	public boolean isSendJoinTime() {
		return sendJoinTime;
	}

	public void setSendJoinTime(boolean sendJoinTime) {
		this.sendJoinTime = sendJoinTime;
	}

	public boolean isSendApplyMPGRoom() {
		return sendApplyMPGRoom;
	}

	public void setSendApplyMPGRoom(boolean sendApplyMPGRoom) {
		this.sendApplyMPGRoom = sendApplyMPGRoom;
	}

	@Override
	public String toString() {
		return "GameSceneSeat [position=" + position + ", playerId=" + playerId + ", joinTime=" + joinTime
				+ ", readyTime=" + readyTime + ", ready=" + ready + ", applyTime=" + applyTime + ", prevReady="
				+ prevReady + ", prevTickReady=" + prevTickReady + ", prevTickJoin=" + prevTickJoin + ", online="
				+ online + ", sendApplyTime=" + sendApplyTime + ", sendJoinTime=" + sendJoinTime + ", sendApplyMPGRoom="
				+ sendApplyMPGRoom + "]";
	}

	
	
}
