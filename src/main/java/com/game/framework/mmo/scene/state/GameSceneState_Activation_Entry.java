package com.game.framework.mmo.scene.state;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;

public class GameSceneState_Activation_Entry extends AbstractGameSceneState {
	
	private GameSceneState_Activation_Entry() {
		this.stateId = GameScene.FSM_ACTIVATION_ENTRY;
	}
	
	public static GameSceneState_Activation_Entry INSATNCE = new GameSceneState_Activation_Entry();
	
	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		int activationState = gameScene.getActivationState();
		
		switch( activationState ) {
		case GameScene.ACTIVATION_STATE_WAIT :
			gameScene.getActivationEntryStateHandler().execute(time, gameScene);
			break;
		case GameScene.ACTIVATION_STATE_SUCCESS : 
			gameScene.getStateMachine().changeState(time, GameSceneState_Activation_Success.INSTANCE);
			break;
		case GameScene.ACTIVATION_STATE_FAIL : 
			gameScene.getStateMachine().changeState(time, GameSceneState_Activation_Fail.INSTANCE);
			break;
		}
	}
}
