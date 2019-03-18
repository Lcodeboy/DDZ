package com.game.framework.room;

import com.game.framework.network.AbstractGamePlayer;
import com.game.framework.network.GameTCPIOClient;

public class GameRoomPlayerUnit extends AbstractGamePlayer {
	//
	private int threadIndex = -1;
	//
	private GameRoom gameRoom = null;
	//
	private long prevUseTime = 0;
	//	上一次操作的时间，用作超时判断，这个超时与对手无关
	private long preExcuteTime = 0;
	
	public GameRoomPlayerUnit(long playerID, GameRoom gameRoom, int threadIndex, GameTCPIOClient ioClient) {
		this.playerID = playerID;
		this.gameRoom = gameRoom;
		this.gameTCPIOClient = ioClient;
		this.threadIndex = threadIndex;
		this.joinProcessTime = System.currentTimeMillis();
		this.preExcuteTime = System.currentTimeMillis();
	}

	public int getThreadIndex() {
		return threadIndex;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public long getPrevUseTime() {
		return prevUseTime;
	}

	public void setPrevUseTime(long prevUseTime) {
		this.prevUseTime = prevUseTime;
	}

	public long getPreExcuteTime() {
		return preExcuteTime;
	}

	public void setPreExcuteTime(long preExcuteTime) {
		this.preExcuteTime = preExcuteTime;
	}
	
}
