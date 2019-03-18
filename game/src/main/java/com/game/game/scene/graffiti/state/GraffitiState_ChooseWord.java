package com.game.game.scene.graffiti.state;

import com.game.common.staticdata.GraffitiStaticDataProvider;
import com.game.common.staticdata.bean.GraffitiStaticData;
import com.game.framework.ProcessGlobalData;
import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.mmo.scene.state.AbstractGameSceneState;
import com.game.framework.util.RandomUtil;
import com.game.game.GameServer;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.game.scene.graffiti.GraffitiPlayerData;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiNotifyWords;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiSelectWords;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiUpdateWord;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiWordsStage;
import com.game.message.proto.ProtoContext_Common.GameStaticDataType;

/**
 * 选词阶段
 * 
 * @author suchen
 * @date 2018年12月4日上午11:25:39
 */
public class GraffitiState_ChooseWord extends AbstractGameSceneState {
	public static final GraffitiState_ChooseWord INSTANCE = new GraffitiState_ChooseWord();
	
	private GraffitiState_ChooseWord() {
		this.stateId = GraffitiGameScene.LOGIC_FSM_CHOOSEWORD;
	}
	
	public void enter(long time, GameScene gameScene) throws FSMException {
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
		graffitiGameScene.incrChooseWordIndex();
		graffitiGameScene.setWordId(-1);
		graffitiGameScene.setChooseWordTime( time );
		graffitiGameScene.setNotifyRoundMSG(false);
		graffitiGameScene.clearGuessRightCount();
		
		if( graffitiGameScene.getChooseWordIndex() >= gameScene.getFullPeopleCount() ) {
			return;
		}
		
		GameSceneSeat[] seatArray = graffitiGameScene.getPlayerArray();
		
		GameSceneSeat seat = seatArray[graffitiGameScene.getChooseWordIndex()];
		
		for( int i = 0, size = graffitiGameScene.getPlayerArray().length; i < size; i++ ) {
			((GraffitiPlayerData) seat.getPlayerData()).setRoundScore(0);
		}
		
		if( seat.isJoin() && seat.isReady() && seat.isOnline() ) {
			//	通知其他人
			NetGCGraffitiWordsStage.Builder otherBuilder = (NetGCGraffitiWordsStage.Builder)ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCGraffitiWordsStage.ordinal());
			otherBuilder.setPlayerId((int)seat.getPlayerId());
			otherBuilder.setRoomUUID(gameScene.getSceneId());
			
			graffitiGameScene.broadCast(otherBuilder.build());
			
			//	通知自己
			GameServer gameServer = (GameServer)ProcessGlobalData.gameProcess.getSubSystem("GameServer");
			
			NetGCGraffitiUpdateWord msg = GraffitiGameScene.createUpdateWord(gameServer, (GraffitiPlayerData)seat.getPlayerData(), gameScene.getSceneId());
			graffitiGameScene.send(seat, msg);
		}
	}
	
	public void execute(long time, GameScene gameScene) throws FSMException {
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
		
		int chooseWordIndex = graffitiGameScene.getChooseWordIndex();
		
		GameSceneSeat[] seatArray = graffitiGameScene.getPlayerArray();
		
		//	最后一个用户已经处理完毕了
		//	graffitiGameScene.getStartReadyAndJoinPeopleCount()
		if( chooseWordIndex >= gameScene.getFullPeopleCount() ) {
			ProcessGlobalData.gameLog.basic("chooseWordIndex SHOW_ANSWER_ENTRY_1 " + chooseWordIndex + " " + graffitiGameScene.getStartReadyAndJoinPeopleCount());
			graffitiGameScene.setShowAnswerEntry(GraffitiGameScene.SHOW_ANSWER_ENTRY_1);
			graffitiGameScene.getLogicStateMachine().changeState(time, GraffitiState_ShowAnswer.INSTANCE);
			return;
		}
		
		GameSceneSeat gameSceneSeat = seatArray[chooseWordIndex];
		
		
		//	当前用户已经不在游戏中
		if( !gameSceneSeat.isOnline() || gameSceneSeat.getPlayerId() <= 0) {
			ProcessGlobalData.gameLog.basic("chooseWordIndex SHOW_ANSWER_ENTRY_2 " + chooseWordIndex + " " + graffitiGameScene.getStartReadyAndJoinPeopleCount());
				graffitiGameScene.setShowAnswerEntry(GraffitiGameScene.SHOW_ANSWER_ENTRY_2);
			graffitiGameScene.getLogicStateMachine().changeState(time, GraffitiState_ShowAnswer.INSTANCE);
			return;
		}
		
		if( graffitiGameScene.getWordId() > 0 ) {
			//	已经选好词了
			graffitiGameScene.getLogicStateMachine().changeState(time, GraffitiState_GuessWord.INSTANCE);
			return;
		}
		
		//	FIXME @SuChen 你画我猜 选词超时时间
		long chooseWordTimeOut = 15 * 1000;
		int playerId = (int)gameSceneSeat.getPlayerId();
		if( (time - graffitiGameScene.getChooseWordTime()) >= chooseWordTimeOut ) {
			GameServer gameServer = (GameServer)ProcessGlobalData.gameProcess.getSubSystem("GameServer");
			
			GraffitiPlayerData playerData = (GraffitiPlayerData)gameSceneSeat.getPlayerData();
			
			
			int wordId = playerData.getWordList().get(RandomUtil.random(4));
			GraffitiStaticDataProvider staticDataProvider = (GraffitiStaticDataProvider) gameServer
					.getStaticData(GameStaticDataType.Lexicon);
			
			GraffitiStaticData staticData = (GraffitiStaticData)staticDataProvider.getStaticData(wordId);
			
			graffitiGameScene.setWordId(wordId);
			System.out.println("Word " + staticData.getTerms());
			graffitiGameScene.getLogicStateMachine().changeState(time, GraffitiState_GuessWord.INSTANCE);
		
			NetGCGraffitiSelectWords.Builder builder = (NetGCGraffitiSelectWords.Builder)ProcessGlobalData.getThreadLocalMSG( ThreadLocalEnum.NetGCGraffitiSelectWords.ordinal() );
			
			
			builder.setResult( MessageConstant.TFRESULT_TRUE );
			
			builder.setRoomUUID(gameScene.getSceneId());
			builder.setWordId(wordId);
			//	告知选词成功
			MessageWriteUtil.writeAndFlush(
					gameSceneSeat.getGameScenePlayerUnit().getTCPIOClient(), builder.build());
			NetGCGraffitiNotifyWords.Builder broadCastBuilder = (NetGCGraffitiNotifyWords.Builder)ProcessGlobalData.getThreadLocalMSG( ThreadLocalEnum.NetGCGraffitiNotifyWords.ordinal() );

			//	广播选词的类型	
			broadCastBuilder.setRoomUUID(gameScene.getSceneId());
			broadCastBuilder.setPlayerId( playerId );
			broadCastBuilder.setWordId(wordId);
			
			graffitiGameScene.broadcastOther(broadCastBuilder.build(), playerId);
		}
		
	}
	
	public void exit(long time, GameScene gameScene) throws FSMException {
		
	}
}
