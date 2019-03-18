package com.game.framework.mmo.scene.state;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;

public class GameSceneState_Init extends AbstractGameSceneState {
	private GameSceneState_Init() {
		this.stateId = GameScene.FSM_INIT;
	}
	
	public static final GameSceneState_Init INSTANCE =
			new GameSceneState_Init();
	
	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {
		gameScene.getInitStateHandler().enter(time, gameScene);
	}
	
	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		gameScene.getInitStateHandler().execute(time, gameScene);
		
		if( gameScene.isFirstJoined() ) {
			gameScene.getStateMachine().changeState(time, GameSceneState_WaitNotOpen.INSTANCE);
		}
		
	}
	
}
