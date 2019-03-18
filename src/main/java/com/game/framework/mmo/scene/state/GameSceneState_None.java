package com.game.framework.mmo.scene.state;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;

public class GameSceneState_None extends AbstractGameSceneState {
	
	private GameSceneState_None() {
		this.stateId = GameScene.FSM_NONE;
	}
	
	public static final GameSceneState_None INSTANCE = new GameSceneState_None();
		
	public void execute(long time, GameScene entity) throws FSMException {
		if( entity.isActivationEntry() ) {
			entity.getStateMachine().changeState(time, GameSceneState_Activation_Entry.INSATNCE);
		}
	}
	
	public void exit(long time, GameScene entity) throws FSMException {
		entity.waitActivationResult();
	}
}
