package com.game.framework.room;

public class DelayGameRoomRunnable implements Runnable {

	private int delayTime = 0;
	
	private long startTime = 0;
	
	private GameRoomRunnable runnable = null;

	public DelayGameRoomRunnable(long startTime, int delayTime, GameRoomRunnable runnable) {
		this.delayTime = delayTime;
		this.runnable = runnable;
		this.startTime = startTime;
	}
	
	public int getDelayTime() {
		return delayTime;
	}

	public GameRoomRunnable getRunnable() {
		return runnable;
	}

	public long getStartTime() {
		return startTime;
	}

	@Override
	public void run() {
		runnable.run();
	}
	
	
}
