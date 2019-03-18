package com.game.framework.mmo.scene.state;

import com.game.framework.ProcessGlobalData;
import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneStateHandler;

public class GameSceneState_WaitNotOpen extends AbstractGameSceneState {
	
	public static final GameSceneState_WaitNotOpen INSTANCE = new GameSceneState_WaitNotOpen();
	private GameSceneState_WaitNotOpen() {
		this.stateId = GameScene.FSM_WAIT_NOT_OPEN;
	}
	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {
		
		int stateId = gameScene.getStateMachine().getPreviousState().getStateId();
		GameSceneStateHandler gameSceneHandler = gameScene.getWaitNotOpenStateHandler();
		
		if( stateId == GameSceneState_Init.INSTANCE.getStateId() ) {
			gameSceneHandler.enter(time, gameScene);
		} else if( stateId == GameSceneState_WaitFull.INSTANCE.getStateId() ) {
			gameSceneHandler.enter(time, gameScene);
		} else if( stateId == GameSceneState_WaitOpen.INSTANCE.getStateId() ) {
			gameSceneHandler.enter(time, gameScene);
		}
		
	}
	
	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		
		if( gameScene.getWaitOpenTimeOut() > GameScene.WAIT_OPEN_TIMEOUT_NONE ) {
			if( time - gameScene.getFirstJoinedTime() >= gameScene.getWaitOpenTimeOut() ) {
				//	超时了退出
				ProcessGlobalData.gameLog.basic("GameSceneState WaitNotOpen > Clear A " + gameScene.getSceneId());
				gameScene.getStateMachine().changeState(time, GameSceneState_Clear.INSTANCE);
				return;
			}
		}
		
		gameScene.getWaitNotOpenStateHandler().execute(time, gameScene);
		
		int peopleCount = gameScene.getPeopleCount();
		
		if( peopleCount == gameScene.getFullPeopleCount() ) {
			gameScene.getStateMachine().changeState(time, GameSceneState_WaitFull.INSTANCE);
		} else if( peopleCount >= gameScene.getOpenPeopleCount() ) {
			gameScene.getStateMachine().changeState(time, GameSceneState_WaitOpen.INSTANCE);
		}
		
		if( gameScene.getPlayerCount() <= 0 ) {
			if( gameScene.getPrecleaning() <= 0 ) {
				gameScene.setPrecleaning(GameScene.PRECLEANING_INIT);
				ProcessGlobalData.gameLog.basic("GameSceneState WaitNotOpen > Clear B " + gameScene.getSceneId());
				gameScene.changeState(time, GameSceneState_Clear.INSTANCE);
			} else {
				ProcessGlobalData.gameLog.basic("GameSceneState WaitNotOpen Join Precleaning " + gameScene.getSceneId());
				gameScene.setPrecleaning(gameScene.getPrecleaning() - ProcessGlobalData.sceneTickTimeOut);
			}
		} else {
			gameScene.setPrecleaning(GameScene.PRECLEANING_INIT);
		}
	}
}
