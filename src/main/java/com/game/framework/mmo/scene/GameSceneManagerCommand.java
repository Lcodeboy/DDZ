package com.game.framework.mmo.scene;

import com.game.framework.network.GameTCPIOClient;

public abstract class GameSceneManagerCommand {

	//
	protected Object param = null;
	//
	protected GameSceneManager gameSceneManager = null;
	//
	protected GameTCPIOClient ioClient = null;

	public GameSceneManagerCommand(GameSceneManager gameSceneManager ) {
		this(gameSceneManager, null, null);
	}
	
	public GameSceneManagerCommand(GameSceneManager gameSceneManager, Object param ) {
		this(gameSceneManager, null, param);
	}

	public GameSceneManagerCommand(GameSceneManager gameSceneManager, GameTCPIOClient ioClient) {
		this(gameSceneManager, ioClient, null);
	}

	public GameSceneManagerCommand(GameSceneManager gameSceneManager, GameTCPIOClient ioClient, Object param) {
		super();
		this.param = param;
		this.gameSceneManager = gameSceneManager;
	}

	public abstract void processCommand(long nowTime) throws Exception;

	public Object getParam() {
		return param;
	}

	public GameSceneManager getGameSceneManager() {
		return gameSceneManager;
	}

}
