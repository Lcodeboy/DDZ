package com.game.framework.room;

import com.game.framework.network.GameLogicProcessor;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;

public class GameRoomRunnable implements Runnable {
	//
	private GameRoomPlayerUnit playerUnit = null;
	//
	public GameLogicProcessor gameLogicProcessor = null;
	//
	private Object message = null;
	//
	private SubSystem subSystem = null;
	//
	private int threadIndex = 0;
	//
	private GameTCPIOClient ioClient = null;

	public GameRoomRunnable( SubSystem subSystem, int threadIndex, GameLogicProcessor gameLogicProcessor, Object message ) {
		this.gameLogicProcessor = gameLogicProcessor;
		this.message = message;
		this.threadIndex = threadIndex;
		this.subSystem = subSystem;
	}

	public GameRoomRunnable( SubSystem subSystem, GameTCPIOClient ioClient, int threadIndex, GameLogicProcessor gameLogicProcessor, Object message ) {
		this.gameLogicProcessor = gameLogicProcessor;
		this.message = message;
		this.threadIndex = threadIndex;
		this.subSystem = subSystem;
		this.ioClient = ioClient;
	}

	public GameRoomRunnable( SubSystem subSystem, GameRoomPlayerUnit playerUnit, GameLogicProcessor gameLogicProcessor, Object message ) {
		this.gameLogicProcessor = gameLogicProcessor;
		this.message = message;
		this.threadIndex = playerUnit.getThreadIndex();
		this.subSystem = subSystem;
		this.playerUnit = playerUnit;
	}

	@Override
	public void run() {
		try {
			if( playerUnit == null ) {
				gameLogicProcessor.handler(subSystem, ioClient, message);
			} else {
				if( playerUnit.getGameRoom().getStopGameType() == null ) {
					gameLogicProcessor.handler(subSystem, playerUnit.getTCPIOClient(), message);
				} else {
					System.out.println("Game Stop Room");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getThreadIndex() {
		return threadIndex;
	}

	public Object getMessage() {
		return message;
	}

	public GameTCPIOClient getIOClient() {
		return ioClient;
	}
}
