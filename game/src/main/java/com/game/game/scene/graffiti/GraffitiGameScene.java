package com.game.game.scene.graffiti;

import java.util.ArrayList;

import com.game.common.CommonConstantContext;
import com.game.common.staticdata.GraffitiStaticDataProvider;
import com.game.common.staticdata.bean.GraffitiStaticData;
import com.game.framework.ProcessGlobalData;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.support.fsm.StateMachine;
import com.game.framework.util.RandomUtil;
import com.game.game.GameServer;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.state.GraffitiState_None;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiCountDown;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiGameStart;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiGoodOrBad;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiGuessWords;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiLineController;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiNotifyWords;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiPenLine;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiPenUpdate;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiRankList;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiReady;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiRound;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiUpdateWord;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiWordsStage;
import com.game.message.proto.ProtoContext_CG.NetGCMPGDuadMSG;
import com.game.message.proto.ProtoContext_CG.NetGCMPGThrowExpression;
import com.game.message.proto.ProtoContext_CG.NetGCOtherJoinMPGRoom;
import com.game.message.proto.ProtoContext_CG.NetGCQuitMPGRoom;
import com.game.message.proto.ProtoContext_Common.GameStaticDataType;

public class GraffitiGameScene extends GameScene {

	// 当前轮次选词的人, 此变量和当前第几轮变量意义一致
	private int chooseWordIndex = -1;
	// 当前轮次选的词
	private int wordId = -1;
	// 当前轮次猜对的人数
	private int guessRightCount = 0;
	//
	private StateMachine logicStateMachine = null;
	//
	public static final int LOGIC_FSM_NONE = 1;
	//
	public static final int LOGIC_FSM_GUESSWORD = 2;
	//
	public static final int LOGIC_FSM_CHOOSEWORD = 3;
	//
	public static final int LOGIC_FSM_SHOWANSWER = 4;
	//
	public static final int LOGIC_FSM_SHOWOVER = 5;
	//
	private long chooseWordTime = 0;
	//
	private long guessWordTime = 0;
	//
	private long showAnswerTime = 0;
	//
	private long showOverTime = 0;

	// 进入到 ShowAnswer 的入口
	private int showAnswerEntry = SHOW_ANSWER_ENTRY_NONE;
	//
	public static final int SHOW_ANSWER_ENTRY_NONE = 0;
	// 执行时到达最后一个
	public static final int SHOW_ANSWER_ENTRY_1 = 1;
	// 当前用户已经退出或掉线
	public static final int SHOW_ANSWER_ENTRY_2 = 2;
	// 猜词超时
	public static final int SHOW_ANSWER_ENTRY_3 = 3;
	// 所有人都猜对词
	public static final int SHOW_ANSWER_ENTRY_4 = 4;
	
	private boolean notifyRoundMSG = false;
	
	public GraffitiGameScene(int sceneId) {
		super(sceneId);
		
		logicStateMachine = new StateMachine(this, true);
		logicStateMachine.setCurrentState(GraffitiState_None.INSTANCE);
	}

	public void clear() {
		super.clear();

		clearLogicData();
	}

	public int getRoundGuessCount( int count ) {
		return count >> 8 & 0xFF;
	}
	
	public int getRoundRightCount( int count ) {
		return count & 0xFF;
	}
	
	public int getRoundGuessWordPlayerCount() {
	
		int guesscount = 0;
		int rightcount = 0;
		GraffitiPlayerData playerData = null;
		
		for( int i = 0; i < playerArray.length; i++ ) {
			
			if( playerArray[i].getPlayerId() > 0 ) {
				
				playerData = (GraffitiPlayerData)playerArray[i].getPlayerData();
				
				if( playerData.isRoundGuess() ) {
					guesscount++;
					if( playerData.isGuessRight() ) {
						rightcount++;
					}
				}
			}		
		}
		
		return (guesscount << 8) + rightcount;
	}
	
	public void clearLogicData() {
		guessRightCount = 0;
		chooseWordIndex = -1;
		wordId = -1;
		logicStateMachine.setCurrentState(GraffitiState_None.INSTANCE);
		
		for( int i = 0; i < playerArray.length; i++ ) {
			if( playerArray[i].getPlayerId() > 0 ) {
				GraffitiPlayerData playerData = (GraffitiPlayerData)playerArray[i].getPlayerData();
				
				playerData.setUpdatedWord(false);
				playerData.setTotalScore(0);
				playerData.setTotalBad(0);
				playerData.setTotalGood(0);
				playerData.setRoundScore(0);
			}		
		}
	}
	
	public void init(int openPeopleCount, int fullPeopleCount, int waitOpenTimeOut, int logicType, int useReady,
			int openNeedReady, int waitStartTime) {
		super.init(openPeopleCount, fullPeopleCount, waitOpenTimeOut, logicType, useReady, openNeedReady, waitStartTime);

		GameSceneSeat[] gameSceneSeatArray = this.getPlayerArray();

		for (int i = 0, size = gameSceneSeatArray.length; i < size; i++) {
			gameSceneSeatArray[i].setPlayerData(new GraffitiPlayerData());
		}
	}

	private GameTCPIOClient getGameTCPIOClient(GameSceneSeat seat) {
		boolean result = seat != null && (seat.getGameScenePlayerUnit()) != null && seat.getPlayerId() != -1;

		if (result) {
			return seat.getGameScenePlayerUnit().getTCPIOClient();
		}

		return null;
	}

	private GameTCPIOClient getGameTCPIOClient(GameSceneSeat seat, long playerId) {
		boolean result = seat != null && (seat.getGameScenePlayerUnit()) != null && seat.getPlayerId() != playerId
				&& seat.getPlayerId() != -1;

		if (result) {
			return seat.getGameScenePlayerUnit().getTCPIOClient();
		}

		return null;
	}

	public void send( GameSceneSeat seat, NetGCGraffitiUpdateWord msg ) {
		
		GameTCPIOClient ioClient = getGameTCPIOClient( seat );
		
		if( ioClient != null ) {
			MessageWriteUtil.writeAndFlush(ioClient, msg);
		}
	}
	
	

	public void broadCast(NetGCGraffitiPenUpdate msg ) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if ((ioClient = getGameTCPIOClient(playerArray[i])) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}
		}
	}
	
	public void broadCast(NetGCQuitMPGRoom msg) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {

			if ((ioClient = getGameTCPIOClient(playerArray[i])) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}

		}
	}
	
	
	public void broadCast(NetGCGraffitiReady msg) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {

			if ((ioClient = getGameTCPIOClient(playerArray[i])) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}

		}
	}

	public void broadcast(NetGCMPGDuadMSG msg, long playerId) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {

			if ((ioClient = getGameTCPIOClient(playerArray[i])) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}

		}
	}
	
	public void broadCastOther(NetGCGraffitiLineController msg, long playerId ) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {

			if ((ioClient = getGameTCPIOClient(playerArray[i], playerId)) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}
		}
	}
	
	public void broadcastOther(NetGCGraffitiPenLine msg, long playerId) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {

			if ((ioClient = getGameTCPIOClient(playerArray[i], playerId)) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}
		}
	}	

	public void broadcastOther(NetGCOtherJoinMPGRoom msg, long playerId) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {

			if ((ioClient = getGameTCPIOClient(playerArray[i], playerId)) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}
		}
	}

	public void broadcastOther(NetGCGraffitiNotifyWords msg, long playerId) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {

			if ((ioClient = getGameTCPIOClient(playerArray[i], playerId)) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}
		}
	}

	public void broadcastOther(NetGCMPGThrowExpression msg, long playerId) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if ((ioClient = getGameTCPIOClient(playerArray[i], playerId)) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}
		}
	}
	
	
	
	public void broadCast(NetGCGraffitiGoodOrBad msg) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if ((ioClient = getGameTCPIOClient(playerArray[i])) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}
		}
	}
	
	public void broadCast(NetGCGraffitiWordsStage msg) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if ((ioClient = getGameTCPIOClient(playerArray[i])) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}
		}
	}
	

	
	public void broadCast(NetGCGraffitiCountDown msg) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if ((ioClient = getGameTCPIOClient(playerArray[i])) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}
		}
	}
	
	public void broadCast(NetGCGraffitiRound msg) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if ((ioClient = getGameTCPIOClient(playerArray[i])) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}
		}
	}
	
	public void broadCast(NetGCGraffitiGuessWords msg) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if ((ioClient = getGameTCPIOClient(playerArray[i])) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}
		}
	}
	
	public void broadCast(NetGCGraffitiRankList msg) {
		GameTCPIOClient ioClient = null;
//		System.out.println("NetGCGraffitiRankList " + msg);
		for (int i = 0, size = playerArray.length; i < size; i++) {
			if ((ioClient = getGameTCPIOClient(playerArray[i])) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}
		}
	}
	public void broadCast(NetGCGraffitiGameStart msg) {
		GameTCPIOClient ioClient = null;

		for (int i = 0, size = playerArray.length; i < size; i++) {
			if ((ioClient = getGameTCPIOClient(playerArray[i])) != null) {
				MessageWriteUtil.writeAndFlush(ioClient, msg);
			}
		}
	}
	public int getWordId() {
		return wordId;
	}

	public void setWordId(int wordId) {
		this.wordId = wordId;
	}

	public int getChooseWordIndex() {
		return chooseWordIndex;
	}

	public void incrChooseWordIndex() {
		chooseWordIndex++;
	}

	public void setChooseWordIndex(int chooseWordIndex) {
		this.chooseWordIndex = chooseWordIndex;
	}

	public StateMachine getLogicStateMachine() {
		return logicStateMachine;
	}

	public void setLogicStateMachine(StateMachine logicStateMachine) {
		this.logicStateMachine = logicStateMachine;
	}

	public void setChooseWordTime(long nowTime) {
		chooseWordTime = nowTime;
	}

	public long getChooseWordTime() {
		return chooseWordTime;
	}

	public int getShowAnswerEntry() {
		return showAnswerEntry;
	}

	public void setShowAnswerEntry(int showAnswerEntry) {
		this.showAnswerEntry = showAnswerEntry;
	}

	public int getGuessRightCount() {
		return guessRightCount;
	}

	public int incrGuessRightCount() {
		return ++guessRightCount;
	}

	public void clearGuessRightCount() {
		guessRightCount = 0;
	}

	public long getGuessWordTime() {
		return guessWordTime;
	}

	public void setGuessWordTime(long guessWordTime) {
		this.guessWordTime = guessWordTime;
	}

	public long getShowAnswerTime() {
		return showAnswerTime;
	}

	public void setShowAnswerTime(long showAnswerTime) {
		this.showAnswerTime = showAnswerTime;
	}

	public long getShowOverTime() {
		return showOverTime;
	}

	public void setShowOverTime(long showOverTime) {
		this.showOverTime = showOverTime;
	}

	public static NetGCGraffitiUpdateWord createUpdateWord( GameServer gameServer, GraffitiPlayerData playerData, int roomUUID ) {
		GraffitiStaticData staticData = null;

		ArrayList<Integer> wordList = playerData.getWordList();
		wordList.clear();
		
		NetGCGraffitiUpdateWord.Builder builder = (NetGCGraffitiUpdateWord.Builder) ProcessGlobalData
				.getThreadLocalMSG(ThreadLocalEnum.NetGCGraffitiUpdateWord.ordinal());
		
		
		while (wordList.size() != 4) {

			staticData = GraffitiGameScene.randomGraffitiStaticData( gameServer );
			
			if (wordList.contains(staticData.getId())) {
				continue;
			}

			wordList.add(staticData.getId());
			builder.addWordId(staticData.getId());
		}

		builder.setRoomUUID(roomUUID);
		
		builder.setResult(MessageConstant.TFRESULT_TRUE);
		
		return builder.build();
	}
	
	public static final GraffitiStaticData randomGraffitiStaticData(GameServer gameServer) {

		GraffitiStaticDataProvider staticDataProvider = (GraffitiStaticDataProvider) gameServer
				.getStaticData(GameStaticDataType.Lexicon);

		GraffitiStaticData staticData = (GraffitiStaticData) gameServer.getStaticData(GameStaticDataType.Lexicon,
				RandomUtil.randomLimit(CommonConstantContext.GRAFFITISTATICDATA_ID_START,
						staticDataProvider.getDataMap().size() + CommonConstantContext.GRAFFITISTATICDATA_ID_START));

		return staticData;
	}
	
	public void setEntryWaitStartTime( long entryWaitStartTime ) {
		super.setEntryWaitStartTime(entryWaitStartTime);
		NetGCGraffitiCountDown.Builder builder = (NetGCGraffitiCountDown.Builder)
				ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCGraffitiCountDown.ordinal());
		builder.setRoomUUID(getSceneId());
	
		if( entryWaitStartTime == 0 ) {
			builder.setStart(false);
		} else {
			builder.setStart(true);
			builder.setConsumeTime(0);
		}
		
		broadCast(builder.build());
	}

	public boolean isNotifyRoundMSG() {
		return notifyRoundMSG;
	}

	public void setNotifyRoundMSG(boolean notifyRoundMSG) {
		this.notifyRoundMSG = notifyRoundMSG;
	}
	
	
}
