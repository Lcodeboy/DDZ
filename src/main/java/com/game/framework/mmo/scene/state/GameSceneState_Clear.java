package com.game.framework.mmo.scene.state;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneStateHandler;

public class GameSceneState_Clear extends AbstractGameSceneState {
	public static final GameSceneState_Clear INSTANCE = new GameSceneState_Clear();

	private GameSceneState_Clear() {
		this.stateId = GameScene.FSM_CLEAR;
	}
	
	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {
		GameSceneStateHandler gameSceneStateHandler = gameScene.getClearStateHandler();
		gameSceneStateHandler.enter(time, gameScene);
		gameScene.clear();
	}

	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		GameSceneStateHandler gameSceneStateHandler = gameScene.getClearStateHandler();
		gameSceneStateHandler.execute(time, gameScene);
		
		gameScene.changeState(time, GameSceneState_Activation_Success.INSTANCE);
	}

	@Override
	public void exit(long time, GameScene gameScene) throws FSMException {
		GameSceneStateHandler gameSceneStateHandler = gameScene.getClearStateHandler();
		
		gameSceneStateHandler.exit(time, gameScene);
	}
}
