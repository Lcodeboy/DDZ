package com.game.framework.mmo.scene.state;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;

public class GameSceneState_Running extends AbstractGameSceneState {
	private GameSceneState_Running() {
		this.stateId = GameScene.FSM_RUNNING;
	}
	
	public static final GameSceneState_Running INSTANCE =
			new GameSceneState_Running();
	
	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {
		gameScene.closeDoor();
		
		gameScene.getRunningStateHandler().enter(time, gameScene);
	}
	
	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		gameScene.getRunningStateHandler().execute(time, gameScene);
		
		if( gameScene.getOnlinePeopleCount() <= 0 ) {
			gameScene.changeState(time, GameSceneState_Clear.INSTANCE);
		}
	}
	
}
