package com.game.framework.room;

public abstract class AbstractGameRoom implements GameRoom {
	
	//	房间的唯一ID	
	protected String gameUUID = null;
	//	所在房间线程
	protected GameRoomThread roomThread = null;
	//	等待这个玩家的操作
	protected long waitPlayerId = 0;
	//	等待这个玩家操作的开始时间
	protected long waitPlayerStartTime = 0;
	//	创建的时间
	protected long createTime = 0;
	//	启动游戏的时间(所有人员都加入的时间)
	protected long startTime = 0;
	//	结束的时间
	protected long stopTime = 0;
	//	准备的时间
	protected long readyTime = 0;
	
	//	等待用户进入的超时时间
	protected int waitAllJoinTimeOut = 0;
	//	等待所有人准备的超时时间
	protected int waitAllReadyTimeOut = 0;
	//	游戏的持续时间
	protected int gameDurationTime = 0;
	//	用户操作的超时时间
	protected int playerOperationTimeOut = 0;
	//	重开一局的时间
	protected int waitResetTimeOut = 0;
	//	
	protected StopGameType stopGameType = null;
	//	计算分数来结束比赛的
	protected boolean fixedTime = false;
	//
	protected long losePlayerId = 0;
	//
	protected volatile long quitPlayerId = 0;
	
	public AbstractGameRoom( String gameUUID, boolean fixedTime ) {
		this.gameUUID = gameUUID;
		this.createTime = System.currentTimeMillis();
		this.fixedTime = fixedTime;
	}
	
	@Override
	public long getWaitPlayerStartTime() {
		return waitPlayerStartTime;
	}
	
	@Override
	public String getGameUUID() {
		return gameUUID;
	}
	
	@Override
	public long getWaitPlayerId() {
		return waitPlayerId;
	}

	@Override
	public void setWaitPlayerId(long playerId) {
		this.waitPlayerId = playerId;
		waitPlayerStartTime = System.currentTimeMillis();
	}
	
	@Override
	public void timeOutNotifyPlayer(long playerID) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void timeOutNotifyGame() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void timeOutNotifyAllJoin() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void timeOutNotifyAllReady() {
//		throw new UnsupportedOperationException();
	}
	
	@Override
	public void loseNotifyPlayer( long playerId ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void quitNotifyPlayer( long playerId ) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void peaceNotify() {
		throw new UnsupportedOperationException();
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public long getStopTime() {
		return stopTime;
	}
	
	public void stopGame( StopGameType stopGameEnum ) {
		stopTime = System.currentTimeMillis();
		stopGameType = stopGameEnum;
	}
	
	public StopGameType getStopGameType() {
		return stopGameType;
	}
	
	public boolean isJoinAll() {
		return startTime != 0;
	}
	
	public void setJoinAll() {
		startTime = System.currentTimeMillis();
	}
	
	//	等待所有人全部加入的时间
	public int getWaitAllJoinTimeOut() {
		return waitAllJoinTimeOut;
	}
	
	//	等待所有人准备的超时时间
	public int getWaitAllReadyTimeOut() {
		return waitAllReadyTimeOut;
	}
	
	//	游戏的持续时间
	public int getGameDurationTime() {
		return gameDurationTime;
	}
	
	//	用户操作的超时时间
	public int getPlayerOperationTimeOut() {
		return playerOperationTimeOut;
	}
	
	public int getGameTime() {
		return (int)(stopTime - startTime);
	}

	@Override
	public long getCreateTime() {
		return createTime;
	}
	
	//	
	public boolean isGameOver() {
		return stopTime != 0;
	}
	
	//		
	public boolean isReadyAll() {
		return readyTime != 0;
	}
	
	//	
	public void setReadyAll() {
		readyTime = System.currentTimeMillis();
	}
	
	public void quitGame( long playerId ) {
		quitPlayerId = playerId;
	}
	
	public void loseGame( long playerId ) {
		losePlayerId = playerId;
	}
	
}
