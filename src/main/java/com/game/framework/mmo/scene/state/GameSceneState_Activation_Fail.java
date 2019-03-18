package com.game.framework.mmo.scene.state;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;

public class GameSceneState_Activation_Fail extends AbstractGameSceneState {

	private GameSceneState_Activation_Fail() {
		this.stateId = GameScene.FSM_ACTIVATION_FAIL;
	}
	
	public static GameSceneState_Activation_Fail INSTANCE = new GameSceneState_Activation_Fail();
	
	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		gameScene.getActivationFailStateHandler().execute(time, gameScene);
		gameScene.clear();
		gameScene.getStateMachine().changeState(time, GameSceneState_None.INSTANCE);
	}
	
}
