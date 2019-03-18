package com.game.game.scene.graffiti.state;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.mmo.scene.state.AbstractGameSceneState;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.game.scene.graffiti.GraffitiPlayerData;

/**
 * 猜词
 * @author suchen
 * @date 2018年12月4日上午11:56:20
 */
public class GraffitiState_GuessWord extends AbstractGameSceneState {
	
	public static final GraffitiState_GuessWord INSTANCE = new GraffitiState_GuessWord();
	
	private GraffitiState_GuessWord() {
		this.stateId = GraffitiGameScene.LOGIC_FSM_GUESSWORD;
	}
		
	public void enter(long time, GameScene gameScene) throws FSMException {
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
		GameSceneSeat[] seatArray = graffitiGameScene.getPlayerArray();
		GraffitiPlayerData data = null;
		GameSceneSeat seat = null;
		
		for( int i = 0, size = seatArray.length; i < size; i++ ) {
			
			seat = seatArray[i];
			
			if( (data = (GraffitiPlayerData)seat.getPlayerData()) != null ) {
				data.setGuessRight(false);
			}
			
		}
		graffitiGameScene.setGuessWordTime( time );
	}
	
	public void execute(long time, GameScene gameScene) throws FSMException {
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
		
		long guessWordTimeOut = 70 * 1000;
		
		if( (time - graffitiGameScene.getGuessWordTime()) >= guessWordTimeOut ) {
			//	超时直接跳转到
			graffitiGameScene.setShowAnswerEntry(GraffitiGameScene.SHOW_ANSWER_ENTRY_3);
			graffitiGameScene.getLogicStateMachine().changeState(time, GraffitiState_ShowAnswer.INSTANCE);
			return;
		}
		
//		int onlineCount = graffitiGameScene.getOnlinePeopleCount();
//		
//		if( onlineCount == 1 ) {
//			return;
//		}
		
		GameSceneSeat[] seatArray = graffitiGameScene.getPlayerArray();
		GraffitiPlayerData data = null;
		GameSceneSeat seat = null;
		
		for( int i = 0, size = seatArray.length; i < size; i++ ) {
			
			seat = seatArray[i];
			
			if( (data = (GraffitiPlayerData)seat.getPlayerData()) != null ) {
				if( seat.getPlayerId() > 0 ) {
					if( i == graffitiGameScene.getChooseWordIndex() ) {
						data.setRoundGuess( false );
					} else {
						data.setRoundGuess( true );
					}	
				}
			}
		}
		
		int count = graffitiGameScene.getRoundGuessWordPlayerCount();
				
		int roundGuessCount = graffitiGameScene.getRoundGuessCount(count);
		int roundRightCount = graffitiGameScene.getRoundRightCount(count);
		
		if( roundGuessCount <= roundRightCount ) {
			//	都猜对了
			graffitiGameScene.setShowAnswerEntry(GraffitiGameScene.SHOW_ANSWER_ENTRY_4);
			graffitiGameScene.getLogicStateMachine().changeState(time, GraffitiState_ShowAnswer.INSTANCE);
			return;
		}
	}
	
	public void exit(long time, GameScene gameScene) throws FSMException {
		
	}
}
