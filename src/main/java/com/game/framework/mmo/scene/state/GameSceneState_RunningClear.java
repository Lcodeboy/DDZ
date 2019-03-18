package com.game.framework.mmo.scene.state;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
public class GameSceneState_RunningClear extends AbstractGameSceneState {
	public static final GameSceneState_RunningClear INSTANCE = new GameSceneState_RunningClear();
	
	private GameSceneState_RunningClear() {
		this.stateId = GameScene.FSM_RUNNING_CLEAR;
	}
	
	public void enter(long time, GameScene gameScene) throws FSMException {
		gameScene.getRunningClearStateHandler().enter(time, gameScene);
	}
	
	public void execute(long time, GameScene gameScene) throws FSMException {
		if( gameScene.getOnlinePeopleCount() <= 0 ) {
			gameScene.changeState(time, GameSceneState_Clear.INSTANCE);
			return;
		}
		
		gameScene.getRunningClearStateHandler().execute(time, gameScene);
	}
	
	public void exit(long time, GameScene gameScene) throws FSMException {
		gameScene.getRunningClearStateHandler().exit(time, gameScene);
	}
}
