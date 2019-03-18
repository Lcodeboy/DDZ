package com.game.framework.mmo.scene.state;

import com.game.framework.ProcessGlobalData;
import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;

public class GameSceneState_WaitOpen extends AbstractGameSceneState {
	
	public static final GameSceneState_WaitOpen INSTANCE = new GameSceneState_WaitOpen();

	private GameSceneState_WaitOpen() {
		this.stateId = GameScene.FSM_WAIT_OPEN;
	}
	
	public void enter(long time, GameScene gameScene) throws FSMException {
		gameScene.getWaitOpenStateHandler().enter(time, gameScene);
	}
	
	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		
		if( gameScene.getWaitOpenTimeOut() > GameScene.WAIT_OPEN_TIMEOUT_NONE ) {
			if( time - gameScene.getFirstJoinedTime() >= gameScene.getWaitOpenTimeOut() ) {
				//	超时了退出
				ProcessGlobalData.gameLog.basic("GameSceneState WaitOpen > Clear A " + gameScene.getSceneId());
				gameScene.getStateMachine().changeState(time, GameSceneState_Clear.INSTANCE);
				return;
			}
		}

		gameScene.getWaitOpenStateHandler().execute(time, gameScene);
		
		int peopleCount = gameScene.getPeopleCount();
		
		if( peopleCount < gameScene.getOpenPeopleCount() ) {
			//	人数不足了直接返回 WaitNotOpen 状态
			gameScene.getStateMachine().changeState(time, GameSceneState_WaitNotOpen.INSTANCE);
			return;
		}
		
		if( gameScene.getOpenNeedReady() == GameScene.OPEN_NEED_READY_TRUE ) {
			//	需要判断准备
			int readyPeopleCount = gameScene.getReadyAndJoinPeopleCount();
			
			if( readyPeopleCount < gameScene.getOpenPeopleCount() ) {
				//	准备人数不足, 继续等待用户准备
				if( gameScene.getEntryWaitStartTime() != 0 ) {
					gameScene.setEntryWaitStartTime(0);
				}
				return;
			}
			
			if( gameScene.getEntryWaitStartTime() == 0 ) {
				gameScene.setEntryWaitStartTime(time);
			}
		}
		
		if( gameScene.getWaitStartTime() == GameScene.WAIT_START_TIME_NONE ) {
			//	没有等待开始的时间
			gameScene.getStateMachine().changeState(time, GameSceneState_Running.INSTANCE);
			return;
		}
		
		if( time - gameScene.getEntryWaitStartTime() >= gameScene.getWaitStartTime() ) {
			//	大于开始等待的时间
			gameScene.getStateMachine().changeState(time, GameSceneState_Running.INSTANCE);
		}
		
		if( gameScene.getPlayerCount() <= 0 ) {
			
			if( gameScene.getPrecleaning() <= 0 ) {
				gameScene.setPrecleaning(GameScene.PRECLEANING_INIT);
				ProcessGlobalData.gameLog.basic("GameSceneState WaitOpen > Clear B " + gameScene.getSceneId());
				gameScene.changeState(time, GameSceneState_Clear.INSTANCE);
			} else {
				ProcessGlobalData.gameLog.basic("GameSceneState WaitOpen Join Precleaning " + gameScene.getSceneId());
				gameScene.setPrecleaning(gameScene.getPrecleaning() - ProcessGlobalData.sceneTickTimeOut);
			}
		} else {
			gameScene.setPrecleaning(GameScene.PRECLEANING_INIT);
		}
	}
}
