package com.game.common.staticdata.bean;

import com.game.framework.dataprovier.StaticData;

/**
 * GameRoom 配置使用
 * 
 *
 */
public class GameTimeConfStaticData implements StaticData {

	private int id = 0;
	
	private String gameType = null;
	
	private int waitAllJoinTimeOut = 0;

	private int waitAllReadyTimeOut = 0;
	
	private int gameDurationTime = 0;
	
	private int playerOperationTimeOut = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public int getWaitAllJoinTimeOut() {
		return waitAllJoinTimeOut;
	}

	public void setWaitAllJoinTimeOut(int waitAllJoinTimeOut) {
		this.waitAllJoinTimeOut = waitAllJoinTimeOut;
	}

	public int getWaitAllReadyTimeOut() {
		return waitAllReadyTimeOut;
	}

	public void setWaitAllReadyTimeOut(int waitAllReadyTimeOut) {
		this.waitAllReadyTimeOut = waitAllReadyTimeOut;
	}

	public int getGameDurationTime() {
		return gameDurationTime;
	}

	public void setGameDurationTime(int gameDurationTime) {
		this.gameDurationTime = gameDurationTime;
	}

	public int getPlayerOperationTimeOut() {
		return playerOperationTimeOut;
	}

	public void setPlayerOperationTimeOut(int playerOperationTimeOut) {
		this.playerOperationTimeOut = playerOperationTimeOut;
	}
	
	
	
}
