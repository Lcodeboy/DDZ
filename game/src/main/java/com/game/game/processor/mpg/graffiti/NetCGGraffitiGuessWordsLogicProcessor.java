package com.game.game.processor.mpg.graffiti;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.game.common.staticdata.GraffitiStaticDataProvider;
import com.game.common.staticdata.bean.GraffitiStaticData;
import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameScenePlayerUnit;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.framework.util.filterword.FilterwordImpl;
import com.game.framework.util.filterword.SensitiveWordTree;
import com.game.game.GameServer;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.game.scene.graffiti.GraffitiPlayerData;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcg.NetCGGraffitiGuessWordsLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiGuessWords;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiGuessWords;
import com.game.message.proto.ProtoContext_Common.GameStaticDataType;

@Controller
@SocketMessage(NetCGGraffitiGuessWords.class)
public class NetCGGraffitiGuessWordsLogicProcessor extends NetCGGraffitiGuessWordsLogicProcessor_Decoder {

	@Override
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, GameScene gameScene, long nowTime,
			Object message) throws Exception {

		NetCGGraffitiGuessWords requestMSG = (NetCGGraffitiGuessWords) message;

		GameServer gameServer = (GameServer) subSystem;
		
		GraffitiGameScene graffitiScene = (GraffitiGameScene) gameScene;

		int wordId = graffitiScene.getWordId();
		
		if( wordId <= 0 ) {
			return;
		}
		
		GameScenePlayerUnit playerUnit = (GameScenePlayerUnit) ioClient.getGamePlayer();

		GameSceneSeat seat = graffitiScene.getPlayerByIndex(playerUnit.getSeatIndex());
		//	猜词人
		GraffitiPlayerData playerData = (GraffitiPlayerData) seat.getPlayerData();
		// --------获取画词人----------
		GameSceneSeat choosePlayerSeat = graffitiScene.getPlayerByIndex(graffitiScene.getChooseWordIndex());

		GraffitiPlayerData choosePlayerData = (GraffitiPlayerData) choosePlayerSeat.getPlayerData();

		ProcessGlobalData.gameLog.basic("Before " +  playerData );
		ProcessGlobalData.gameLog.basic("Before " +   choosePlayerData );
		
		NetGCGraffitiGuessWords.Builder builder = (NetGCGraffitiGuessWords.Builder) ProcessGlobalData
				.getThreadLocalMSG(ThreadLocalEnum.NetGCGraffitiGuessWords.ordinal() );

		int roomUUID = requestMSG.getRoomUUID();
		builder.setRoomUUID(roomUUID);
		builder.setPlayerId(playerUnit.get32PlayerID());

		String word = requestMSG.getWord();
		// TODO @WuYang 你画我猜是否过滤屏蔽字
		boolean isCheck = true;
		if (isCheck) {

			SensitiveWordTree tree = (SensitiveWordTree) gameServer.getAllStaticData(GameStaticDataType.KEYWORD);

			FilterwordImpl filter = new FilterwordImpl(tree);

			List<String> matchAll = filter.matchAll(word, -1, true, true);

			if (!matchAll.isEmpty()) {
				// word 为屏蔽字
				builder.setResult(MessageConstant.FAIL_2);
				MessageWriteUtil.writeAndFlush(ioClient, builder.build());
				return;
			}
		}
		
		GraffitiStaticDataProvider staticDataProvider = (GraffitiStaticDataProvider) gameServer
				.getStaticData(GameStaticDataType.Lexicon);
		
		GraffitiStaticData staticData = (GraffitiStaticData) staticDataProvider.getStaticData(wordId);

		ProcessGlobalData.gameLog.basic("" + playerData.isGuessRight());
		
		if (staticData.getTerms().equals(word) && !playerData.isGuessRight() ) {
			// 猜对后的得分
			int calculateScore = calculateScore(graffitiScene.getStartReadyAndJoinPeopleCount(),
					graffitiScene.getGuessRightCount());
			//
			playerData.setGuessRight(true);
			playerData.setTotalScore(calculateScore + playerData.getTotalScore());
			playerData.setRoundScore(calculateScore);

			builder.setResult(MessageConstant.TFRESULT_TRUE);
			builder.setScore(calculateScore);
			builder.setRank(graffitiScene.getGuessRightCount() + 1);
			graffitiScene.broadCast(builder.build());
			ProcessGlobalData.gameLog.basic(playerUnit.get32PlayerID() + " calculateScore " + calculateScore);
			// 将猜对人数加一
			graffitiScene.incrGuessRightCount();
			
			// ----------添加画词人的分数------------
			
			int readAndJoinCount = graffitiScene.getReadyAndJoinPeopleCount();
			
			if( readAndJoinCount != 4 ) {
				if (graffitiScene.getGuessRightCount() == readAndJoinCount - 1) {
					//	都答对了
					choosePlayerData.setTotalScore(choosePlayerData.getTotalScore() - (graffitiScene.getGuessRightCount() - 1) * 2);
					choosePlayerData.setRoundScore(choosePlayerData.getRoundScore() - (graffitiScene.getGuessRightCount() - 1) * 2);
				} else {
					//	每猜对一个人，添加画词人的轮次分和总分
					choosePlayerData.setTotalScore(choosePlayerData.getTotalScore() + 2);
					choosePlayerData.setRoundScore(choosePlayerData.getRoundScore() + 2);
				}
			}
		} else {
			builder.setWord(word);
			builder.setScore(0);
			builder.setResult(MessageConstant.FAIL_1);
			graffitiScene.broadCast(builder.build());
		}
		ProcessGlobalData.gameLog.basic("After " +  playerData );
		ProcessGlobalData.gameLog.basic("After " +  choosePlayerData );
	}

	/**
	 * e.g. OpenScenePeopleCount  
	 * 
	 * @param openScenePeopleCount
	 *            开局时的人数
	 * @param guessRightCount
	 *            已经猜对的人数
	 * @return
	 */
	private int calculateScore(int openScenePeopleCount, int guessRightCount) {
		// 6 4 3 2 1 (6人)
		// 4 3 2 1   (5人)
		// 3 2 1     (4人)
		// 2 1       (3人)
		// 1         (2人)
		if( openScenePeopleCount <= guessRightCount + 1 ) {
			// 2 2     2 3
			throw new IllegalArgumentException("OpenScenePeopleCount " + openScenePeopleCount + " Already GuessRightCount " + guessRightCount );
		}
		return (openScenePeopleCount == 6 && guessRightCount == 0)? 6 : (openScenePeopleCount - guessRightCount - 1); 
	}
}
