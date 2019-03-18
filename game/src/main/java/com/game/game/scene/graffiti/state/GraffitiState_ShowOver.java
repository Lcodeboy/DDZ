package com.game.game.scene.graffiti.state;

import com.game.framework.ProcessGlobalData;
import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.mmo.scene.state.AbstractGameSceneState;
import com.game.framework.mmo.scene.state.GameSceneState_RunningClear;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.game.scene.graffiti.GraffitiPlayerData;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiRankList;
import com.game.message.proto.ProtoContext_Common.MPRGraffitiRank;

/**
 * 
 * @author suchen
 * @date 2018年12月4日上午10:38:52
 */
public class GraffitiState_ShowOver extends AbstractGameSceneState {
	
	public static final GraffitiState_ShowOver INSTANCE = new GraffitiState_ShowOver();
	
	private GraffitiState_ShowOver() {
		this.stateId = GraffitiGameScene.LOGIC_FSM_SHOWOVER;
	}
	
	public void enter(long time, GameScene gameScene) throws FSMException {
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
		
		graffitiGameScene.setShowOverTime(time);
		
		NetGCGraffitiRankList.Builder builder = (NetGCGraffitiRankList.Builder)
				ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCGraffitiRankList.ordinal());
		
		MPRGraffitiRank.Builder rankBuild = (MPRGraffitiRank.Builder) 
				ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.MPRGraffitiRank.ordinal());
		
		GameSceneSeat[] gameSceneSeatArray = graffitiGameScene.getPlayerArray();
		GraffitiPlayerData playerData = null;
		
		for( int i = 0; i < gameSceneSeatArray.length; i++ ) {
			ProcessGlobalData.gameLog.basic("Send Rank " + i + " " + gameSceneSeatArray[i].getPlayerId() + " " + gameSceneSeatArray[i].getQuitPlayerId());
			if( gameSceneSeatArray[i].getPlayerId() == -1 ) {
				
				if( gameSceneSeatArray[i].getQuitPlayerId() == -1 ) {
					rankBuild.setGood(Integer.MIN_VALUE);
					rankBuild.setScore(Integer.MIN_VALUE);
					rankBuild.setPlayerId(-1);
				} else {
					playerData = (GraffitiPlayerData)gameSceneSeatArray[i].getPlayerData();
					rankBuild.setPlayerId((int)gameSceneSeatArray[i].getQuitPlayerId());
					rankBuild.setGood(playerData.getTotalGood());
					rankBuild.setScore(playerData.getTotalScore());
				}
				
			} else {
				playerData = (GraffitiPlayerData)gameSceneSeatArray[i].getPlayerData();
				
				rankBuild.setGood(playerData.getTotalGood());
				rankBuild.setScore(playerData.getTotalScore());
				rankBuild.setPlayerId((int)gameSceneSeatArray[i].getPlayerId());
			}
			
			builder.addRank(rankBuild.build());
		}
		
		graffitiGameScene.broadCast(builder.build());
	}
	
	public void execute(long time, GameScene gameScene) throws FSMException {
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
		
		//	FIXME @SuChen 最后轮次公告的时间
		int maxShowAnswerTime = 10 * 1000;
		
		if( (time - graffitiGameScene.getShowAnswerTime()) >= maxShowAnswerTime ) {
			graffitiGameScene.changeState(time, GameSceneState_RunningClear.INSTANCE);
		}
	}
	
	public void exit(long time, GameScene gameScene) throws FSMException {
		
	}
}
