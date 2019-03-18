package com.game.framework.mmo.scene.state;

import com.game.framework.ProcessGlobalData;
import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneStateHandler;
public class GameSceneState_Exception extends AbstractGameSceneState {
	private GameSceneState_Exception() {
		this.stateId = GameScene.FSM_EXCEPTION;
	}
	public static final GameSceneState_Exception INSTANCE = new GameSceneState_Exception();

	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {
		ProcessGlobalData.gameLog.basic(gameScene.getClass().getSimpleName() + " " + gameScene.getSceneId() + " Enter Exception");
		GameSceneStateHandler gameSceneStateHandler = gameScene.getExcetpionStateHandler();
		gameSceneStateHandler.enter(time, gameScene);
	}

	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		GameSceneStateHandler gameSceneStateHandler = gameScene.getExcetpionStateHandler();
		gameSceneStateHandler.execute(time, gameScene);
		gameScene.getStateMachine().changeState(time, GameSceneState_Clear.INSTANCE);
	}

	@Override
	public void exit(long time, GameScene gameScene) throws FSMException {
		ProcessGlobalData.gameLog.basic(gameScene.getClass().getSimpleName() + " " + gameScene.getSceneId() + " Exit Exception");
		GameSceneStateHandler gameSceneStateHandler = gameScene.getExcetpionStateHandler();
		gameSceneStateHandler.exit(time, gameScene);
	}
}
