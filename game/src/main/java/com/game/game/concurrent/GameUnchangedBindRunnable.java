package com.game.game.concurrent;

import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.threadpool.UnchangedBindRunnable;
import com.game.framework.network.GameLogicProcessor;
import com.game.framework.network.GameTCPIOClient;
import com.game.game.GameServer;

public class GameUnchangedBindRunnable extends UnchangedBindRunnable {

	//
	private GameTCPIOClient ioClient = null;
	//
	private Object message = null;
	//
	private GameServer gameServer = null;
	//
	private GameLogicProcessor processor = null;
	
	public GameUnchangedBindRunnable(GameServer gameServer, GameTCPIOClient ioClient, Object message,  GameLogicProcessor processor) {
		super( ioClient.get32StubID() );
		this.gameServer = gameServer;
		this.message = message;
		this.processor = processor;
		this.ioClient = ioClient;
	}

	@Override
	public void run() {
		try {
			processor.handler(gameServer, ioClient, message);
		} catch (Exception e) {
			ProcessGlobalData.gameLog.basicErr( "", e);
		}
	}

}
