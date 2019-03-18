package com.game.game.scene.graffiti.state;

import com.game.framework.ProcessGlobalData;
import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.mmo.scene.state.AbstractGameSceneState;
import com.game.framework.support.fsm.StateMachine;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.game.scene.graffiti.GraffitiPlayerData;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiRound;

/**
 * 
 * @author suchen
 * @date 2018年12月4日上午11:02:51
 */
public class GraffitiState_ShowAnswer extends AbstractGameSceneState {
	public static final GraffitiState_ShowAnswer INSTANCE = new GraffitiState_ShowAnswer();

	private GraffitiState_ShowAnswer() {
		this.stateId = GraffitiGameScene.LOGIC_FSM_SHOWANSWER;
	}

	public void enter(long time, GameScene gameScene) throws FSMException {
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
		StateMachine fsm = graffitiGameScene.getLogicStateMachine();

		if (fsm.getPreviousState().getStateId() == GraffitiGameScene.LOGIC_FSM_CHOOSEWORD) {
			// 选词阶段进入
			switch (graffitiGameScene.getShowAnswerEntry()) {
			case GraffitiGameScene.SHOW_ANSWER_ENTRY_1:
				// 最后一个用户已经处理完毕了, 进入最后一轮的状态
				fsm.changeState(time, GraffitiState_ShowOver.INSTANCE);
				break;
			case GraffitiGameScene.SHOW_ANSWER_ENTRY_2:
				// 当前用户已经不在游戏中
				if( graffitiGameScene.getChooseWordIndex() >= gameScene.getFullPeopleCount() - 1 ) {
					fsm.changeState(time, GraffitiState_ShowOver.INSTANCE);
				} else {
					fsm.changeState(time, GraffitiState_ChooseWord.INSTANCE);
				}
				
				break;
			}
		} else if (fsm.getPreviousState().getStateId() == GraffitiGameScene.LOGIC_FSM_GUESSWORD) {
			graffitiGameScene.setShowAnswerTime(time);
		}

	}

	public void execute(long time, GameScene gameScene) throws FSMException {
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
		
		StateMachine fsm = graffitiGameScene.getLogicStateMachine();
		
		if (fsm.getPreviousState().getStateId() == GraffitiGameScene.LOGIC_FSM_GUESSWORD) {
			// 	猜词阶段进入
			
			if( !graffitiGameScene.isNotifyRoundMSG() ) {
				NetGCGraffitiRound.Builder builder = (NetGCGraffitiRound.Builder) 
						ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCGraffitiRound.ordinal());
				
				GameSceneSeat playerData = 
						graffitiGameScene.getPlayerByIndex(graffitiGameScene.getChooseWordIndex());
				
				builder.setPlayerId((int)(playerData.getPlayerId() == -1 ? playerData.getQuitPlayerId() : playerData.getPlayerId()));
				builder.setRoomUUID(gameScene.getSceneId());
				builder.setScore(
						((GraffitiPlayerData)playerData.getPlayerData()).getRoundScore()
						);
				
				graffitiGameScene.broadCast(builder.build());
				graffitiGameScene.setNotifyRoundMSG(true);
			}
			
			
			//	FIXME @SuChen 每一轮次公告的时间
			if( time - graffitiGameScene.getShowAnswerTime() >= 10 * 1000 ) {
				// 是否是最后一轮
				boolean lastRound = graffitiGameScene.getChooseWordIndex() >= graffitiGameScene.getFullPeopleCount();
				
				ProcessGlobalData.gameLog.basic("GraffitiState_ShowAnswer lastRound " + graffitiGameScene.getChooseWordIndex() + " " + graffitiGameScene.getStartReadyAndJoinPeopleCount());
				
				if (lastRound) {
					//	最后一轮
					fsm.changeState(time, GraffitiState_ShowOver.INSTANCE);
				} else {
					//	下一轮次
					fsm.changeState(time, GraffitiState_ChooseWord.INSTANCE);
				}	
			}
			
		}
		

	}

	public void exit(long time, GameScene gameScene) throws FSMException {

	}
}
