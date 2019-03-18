package com.game.framework.mmo.scene.state;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.support.fsm.State;


public abstract class AbstractGameSceneState implements State<GameScene> {
	
	protected int stateId = 0;
	
	public AbstractGameSceneState() {

	}
	
	public int getStateId() {
		return stateId;
	}
	
	@Override
	public void enter(long time, GameScene entity) throws FSMException {
		
	}

	@Override
	public void execute(long time, GameScene entity) throws FSMException {
		
	}

	@Override
	public void exit(long time, GameScene entity) throws FSMException {
		
	}
}
