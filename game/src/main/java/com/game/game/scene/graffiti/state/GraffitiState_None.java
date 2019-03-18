package com.game.game.scene.graffiti.state;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.state.AbstractGameSceneState;
import com.game.game.scene.graffiti.GraffitiGameScene;

public class GraffitiState_None extends AbstractGameSceneState {
	
	public static final GraffitiState_None INSTANCE = new GraffitiState_None();
	
	private GraffitiState_None() {
		this.stateId = GraffitiGameScene.LOGIC_FSM_NONE;
	}
	
	public void enter(long time, GameScene gameScene) throws FSMException {
		
	}
	
	public void execute(long time, GameScene gameScene) throws FSMException {

	}
	
	public void exit(long time, GameScene gameScene) throws FSMException {
		
	}
}
