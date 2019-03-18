package com.game.framework.mmo.scene;

import com.game.framework.network.GameLogicProcessor;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
public class GameSceneCommand {

	private int gameSceneId = 0;
	//
	private GameLogicProcessor gameLogicProcessor = null;
	//
	private Object message = null;
	//
	private GameTCPIOClient ioClient = null;
	//
	private SubSystem subSystem = null;
	//
	private GameScene gameScene = null;
	
	public GameSceneCommand(GameLogicProcessor gameLogicProcessor, Object message,
                            GameTCPIOClient ioClient, SubSystem subSystem, GameScene gameScene) {
		super();
		this.gameSceneId = gameScene.getSceneId();
		this.gameLogicProcessor = gameLogicProcessor;
		this.message = message;
		this.ioClient = ioClient;
		this.subSystem = subSystem;
		this.gameScene = gameScene;
	}


	public void processCommand( long nowTime ) throws Exception {
		gameLogicProcessor.handler(subSystem, ioClient, gameScene, nowTime, message);
	}

	public GameScene getGameScene() {
		return gameScene;
	}

	public int getGameSceneId() {
		return gameSceneId;
	}

	public GameLogicProcessor getGameLogicProcessor() {
		return gameLogicProcessor;
	}

	public Object getMessage() {
		return message;
	}


	public GameTCPIOClient getIoClient() {
		return ioClient;
	}


	public SubSystem getSubSystem() {
		return subSystem;
	}

}
