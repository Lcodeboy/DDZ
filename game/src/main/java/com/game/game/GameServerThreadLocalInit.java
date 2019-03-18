package com.game.game;

import com.game.framework.ProcessGlobalData;
import com.game.framework.process.GameProcess;
import com.game.framework.process.SubSystem;
import com.game.framework.room.ThreadLocalInit;
import com.game.message.proto.ProtoContext_BASIC.NetKeepLivePing;
import com.game.message.proto.ProtoContext_BASIC.NetKeepLivePong;
import com.game.message.proto.ProtoContext_BASIC.NetReConnResult;
import com.game.message.proto.ProtoContext_CG.NetCGAircraftHitPush;
import com.game.message.proto.ProtoContext_CG.NetCGAnimalChessMove;
import com.game.message.proto.ProtoContext_CG.NetCGAnimalChessOpen;
import com.game.message.proto.ProtoContext_CG.NetCGCoreBallFire;
import com.game.message.proto.ProtoContext_CG.NetCGFeed;
import com.game.message.proto.ProtoContext_CG.NetCGGolangPush;
import com.game.message.proto.ProtoContext_CG.NetCGLinkChessSelect;
import com.game.message.proto.ProtoContext_CG.NetCGOthelloPush;
import com.game.message.proto.ProtoContext_CG.NetCGPopStarClick;
import com.game.message.proto.ProtoContext_CG.NetCGQuitMPGRoom;
import com.game.message.proto.ProtoContext_CG.NetCGRemoveBrick;
import com.game.message.proto.ProtoContext_CG.NetCGTest;
import com.game.message.proto.ProtoContext_CG.NetGCAircraftHitPush;
import com.game.message.proto.ProtoContext_CG.NetGCAircraftInit;
import com.game.message.proto.ProtoContext_CG.NetGCAllJoin;
import com.game.message.proto.ProtoContext_CG.NetGCAnimalChessMove;
import com.game.message.proto.ProtoContext_CG.NetGCAnimalChessOpen;
import com.game.message.proto.ProtoContext_CG.NetGCAnimalChessResultWithDarkPiece;
import com.game.message.proto.ProtoContext_CG.NetGCCoreBallFire;
import com.game.message.proto.ProtoContext_CG.NetGCFeed;
import com.game.message.proto.ProtoContext_CG.NetGCFlappyBirdClick;
import com.game.message.proto.ProtoContext_CG.NetGCFlappyBirdMap;
import com.game.message.proto.ProtoContext_CG.NetGCGameResult;
import com.game.message.proto.ProtoContext_CG.NetGCGolangPush;
import com.game.message.proto.ProtoContext_CG.NetGCGolangSuccess;
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
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiSelectWords;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiUpdateWord;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiWordsStage;
import com.game.message.proto.ProtoContext_CG.NetGCInitCoreBall;
import com.game.message.proto.ProtoContext_CG.NetGCJoinGame;
import com.game.message.proto.ProtoContext_CG.NetGCJoinMPGRoom;
import com.game.message.proto.ProtoContext_CG.NetGCLinkChessPanel;
import com.game.message.proto.ProtoContext_CG.NetGCLinkChessSelect;
import com.game.message.proto.ProtoContext_CG.NetGCMPGDuadMSG;
import com.game.message.proto.ProtoContext_CG.NetGCMPGThrowExpression;
import com.game.message.proto.ProtoContext_CG.NetGCOthelloPush;
import com.game.message.proto.ProtoContext_CG.NetGCOthelloSuccess;
import com.game.message.proto.ProtoContext_CG.NetGCOtherJoinMPGRoom;
import com.game.message.proto.ProtoContext_CG.NetGCPeaceNotify;
import com.game.message.proto.ProtoContext_CG.NetGCPeaceResult;
import com.game.message.proto.ProtoContext_CG.NetGCPopStarClick;
import com.game.message.proto.ProtoContext_CG.NetGCQuitMPGRoom;
import com.game.message.proto.ProtoContext_CG.NetGCRemoveBrick;
import com.game.message.proto.ProtoContext_CG.NetGCThrowExpression;
import com.game.message.proto.ProtoContext_CG.NetGCThrowItem;
import com.game.message.proto.ProtoContext_Common.AnimalChessPieceAllInfo;
import com.game.message.proto.ProtoContext_Common.FieldValue;
import com.game.message.proto.ProtoContext_Common.FlappyBirdBarrier;
import com.game.message.proto.ProtoContext_Common.GameBattleResult;
import com.game.message.proto.ProtoContext_Common.GolangData;
import com.game.message.proto.ProtoContext_Common.MPRDataSyncEventStruct;
import com.game.message.proto.ProtoContext_Common.MPRGraffitiRank;
import com.game.message.proto.ProtoContext_Common.MPRMsgStruct;
import com.game.message.proto.ProtoContext_Common.OthelloData;
import com.game.message.proto.ProtoContext_Common.TFResult;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGActivationMPRRoom;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGCreateGame;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGGameJoinGate;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGSyncMPRData;
import com.game.message.proto.ProtoContext_RG.NetGRCreateGame;
import com.game.message.proto.ProtoContext_RG.NetGRNotifyBattleResult;
import com.game.message.proto.ProtoContext_RG.NetGRNotifyQuitGame;

public class GameServerThreadLocalInit implements ThreadLocalInit, SubSystem {

	@Override
	public void init(GameProcess gameProcess) throws Exception {
		
		
	}

	@Override
	public void start(GameProcess gameProcess) throws Exception {
		
		
	}

	@Override
	public void stop(GameProcess gameProcess) throws Exception {
		
		
	}

	@Override
	public String getSubSystemName() {
		return "ThreadLocalInit";
	}

	@Override
	public void initData() {
		
		ProcessGlobalData.createThreadLocal(ThreadLocalEnum.values().length);

		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiLineController.ordinal(),
				NetGCGraffitiLineController.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiPenLine.ordinal(),
				NetGCGraffitiPenLine.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiPenUpdate.ordinal(),
				NetGCGraffitiPenUpdate.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.AnimalChessPieceAllInfo.ordinal(),
				AnimalChessPieceAllInfo.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.FieldValue.ordinal(), FieldValue.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.FlappyBirdBarrier.ordinal(),
				FlappyBirdBarrier.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.GameBattleResultPassive.ordinal(),
				GameBattleResult.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.GameBattleResultActive.ordinal(),
				GameBattleResult.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.GolangData.ordinal(), GolangData.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.MPRDataSyncEventStruct.ordinal(),
				MPRDataSyncEventStruct.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.MPRGraffitiRank.ordinal(), MPRGraffitiRank.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.MPRMsgStruct.ordinal(), MPRMsgStruct.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetCGAircraftHitPush.ordinal(),
				NetCGAircraftHitPush.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetCGAnimalChessMove.ordinal(),
				NetCGAnimalChessMove.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetCGAnimalChessOpen.ordinal(),
				NetCGAnimalChessOpen.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetCGCoreBallFire.ordinal(),
				NetCGCoreBallFire.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetCGFeed.ordinal(), NetCGFeed.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetCGGolangPush.ordinal(), NetCGGolangPush.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetCGLinkChessSelect.ordinal(),
				NetCGLinkChessSelect.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetCGOthelloPush.ordinal(),
				NetCGOthelloPush.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetCGPopStarClick.ordinal(),
				NetCGPopStarClick.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetCGQuitMPGRoom.ordinal(),
				NetCGQuitMPGRoom.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetCGRemoveBrick.ordinal(),
				NetCGRemoveBrick.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetCGTest.ordinal(), NetCGTest.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGAIGActivationMPRRoom.ordinal(),
				NetGAIGActivationMPRRoom.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGAIGCreateGame.ordinal(),
				NetGAIGCreateGame.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGAIGGameJoinGate.ordinal(),
				NetGAIGGameJoinGate.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGAIGSyncMPRData.ordinal(),
				NetGAIGSyncMPRData.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCAircraftHitPush.ordinal(),
				NetGCAircraftHitPush.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCAircraftInit.ordinal(),
				NetGCAircraftInit.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCAllJoin.ordinal(), NetGCAllJoin.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCAnimalChessMove.ordinal(),
				NetGCAnimalChessMove.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCAnimalChessOpen.ordinal(),
				NetGCAnimalChessOpen.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCAnimalChessResultWithDarkPiece.ordinal(),
				NetGCAnimalChessResultWithDarkPiece.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCCoreBallFire.ordinal(),
				NetGCCoreBallFire.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCFeed.ordinal(), NetGCFeed.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCFlappyBirdClick.ordinal(),
				NetGCFlappyBirdClick.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCFlappyBirdMap.ordinal(),
				NetGCFlappyBirdMap.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGameResult.ordinal(), NetGCGameResult.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGolangPush.ordinal(), NetGCGolangPush.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGolangSuccess.ordinal(),
				NetGCGolangSuccess.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiCountDown.ordinal(),
				NetGCGraffitiCountDown.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiGameStart.ordinal(),
				NetGCGraffitiGameStart.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiGoodOrBad.ordinal(),
				NetGCGraffitiGoodOrBad.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiGuessWords.ordinal(),
				NetGCGraffitiGuessWords.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiNotifyWords.ordinal(),
				NetGCGraffitiNotifyWords.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiRankList.ordinal(),
				NetGCGraffitiRankList.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiReady.ordinal(),
				NetGCGraffitiReady.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiRound.ordinal(),
				NetGCGraffitiRound.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiSelectWords.ordinal(),
				NetGCGraffitiSelectWords.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiUpdateWord.ordinal(),
				NetGCGraffitiUpdateWord.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCGraffitiWordsStage.ordinal(),
				NetGCGraffitiWordsStage.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCInitCoreBall.ordinal(),
				NetGCInitCoreBall.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCJoinGame.ordinal(), NetGCJoinGame.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCJoinMPGRoom.ordinal(),
				NetGCJoinMPGRoom.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCLinkChessPanel.ordinal(),
				NetGCLinkChessPanel.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCLinkChessSelect.ordinal(),
				NetGCLinkChessSelect.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCMPGDuadMSG.ordinal(), NetGCMPGDuadMSG.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCMPGThrowExpression.ordinal(),
				NetGCMPGThrowExpression.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCOthelloPush.ordinal(),
				NetGCOthelloPush.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCOthelloSuccess.ordinal(),
				NetGCOthelloSuccess.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCOtherJoinMPGRoom.ordinal(),
				NetGCOtherJoinMPGRoom.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCPeaceNotify.ordinal(),
				NetGCPeaceNotify.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCPeaceResult.ordinal(),
				NetGCPeaceResult.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCPopStarClick.ordinal(),
				NetGCPopStarClick.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCQuitMPGRoom.ordinal(),
				NetGCQuitMPGRoom.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCRemoveBrick.ordinal(),
				NetGCRemoveBrick.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCThrowExpression.ordinal(),
				NetGCThrowExpression.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGCThrowItem.ordinal(), NetGCThrowItem.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGRCreateGame.ordinal(), NetGRCreateGame.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGRNotifyBattleResult.ordinal(),
				NetGRNotifyBattleResult.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetGRNotifyQuitGame.ordinal(),
				NetGRNotifyQuitGame.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetKeepLivePing.ordinal(), NetKeepLivePing.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetKeepLivePong.ordinal(), NetKeepLivePong.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.NetReConnResult.ordinal(), NetReConnResult.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.OthelloData.ordinal(), OthelloData.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.TFResult.ordinal(), TFResult.newBuilder());
		ProcessGlobalData.setThreadLocalValue(ThreadLocalEnum.RECV_MSG_StringBuilder.ordinal(), new StringBuilder());

	}

}
