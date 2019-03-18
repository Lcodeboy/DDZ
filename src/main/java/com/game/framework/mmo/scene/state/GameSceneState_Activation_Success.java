package com.game.framework.mmo.scene.state;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;

public class GameSceneState_Activation_Success extends AbstractGameSceneState {
	
	private GameSceneState_Activation_Success() {
		this.stateId = GameScene.FSM_ACTIVATION_SUCCESS;
	}
	
	public static final GameSceneState_Activation_Success INSTANCE = new GameSceneState_Activation_Success();
	
	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {
		gameScene.getActivationSuccessStateHandler().enter(time, gameScene);
		gameScene.closeDoor();
	}
	
	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		gameScene.getActivationSuccessStateHandler().execute(time, gameScene);
		
		if( gameScene.getOpenDoor() == GameScene.OPEN_DOOR_OPEN ) {
			gameScene.getStateMachine().changeState(time, GameSceneState_Init.INSTANCE);
		}
	}
	
}
