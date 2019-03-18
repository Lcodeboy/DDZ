package com.game.game.processor.mpg.graffiti;

import org.springframework.stereotype.Controller;

import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameScenePlayerUnit;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.game.scene.graffiti.GraffitiPlayerData;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcg.NetCGGraffitiSelectWordLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiSelectWord;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiNotifyWords;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiSelectWords;

/**
 *
 *
 */
@Controller
@SocketMessage(NetCGGraffitiSelectWord.class)
public class NetCGGraffitiSelectWordLogicProcessor extends NetCGGraffitiSelectWordLogicProcessor_Decoder{

	@Override
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, GameScene gameScene, long nowTime,
			Object message) throws Exception {
		
		NetCGGraffitiSelectWord requestMSG = (NetCGGraffitiSelectWord)message;
		
		GraffitiGameScene graffitiScene = (GraffitiGameScene) gameScene;
		
		GameScenePlayerUnit playerUnit = (GameScenePlayerUnit) ioClient.getGamePlayer();
		
		GameSceneSeat seat = gameScene.getPlayerByIndex(playerUnit.getSeatIndex());

		GraffitiPlayerData playerData = (GraffitiPlayerData) seat.getPlayerData();
		
		NetGCGraffitiSelectWords.Builder builder = (NetGCGraffitiSelectWords.Builder)ProcessGlobalData.getThreadLocalMSG( ThreadLocalEnum.NetGCGraffitiSelectWords.ordinal() );
		
		int roomUUID = requestMSG.getRoomUUID();
		
		int wordId = requestMSG.getWordId();
		
		if( !playerData.getWordList().contains(wordId) ) {
			//	选词不在范围之内
			builder.setResult( MessageConstant.TFRESULT_FALSE );
			
			MessageWriteUtil.writeAndFlush(ioClient, builder.build());
			
			return;
		}
		graffitiScene.setWordId(wordId);
		
		builder.setResult( MessageConstant.TFRESULT_TRUE );
		builder.setRoomUUID(roomUUID);
		builder.setWordId(wordId);
		//	告知选词成功
		MessageWriteUtil.writeAndFlush(ioClient, builder.build());
		NetGCGraffitiNotifyWords.Builder broadCastBuilder = (NetGCGraffitiNotifyWords.Builder)ProcessGlobalData.getThreadLocalMSG( ThreadLocalEnum.NetGCGraffitiNotifyWords.ordinal() );

		//	广播选词的类型	
		broadCastBuilder.setRoomUUID(roomUUID);
		broadCastBuilder.setPlayerId( playerUnit.get32PlayerID() );
		broadCastBuilder.setWordId(wordId);
		
		graffitiScene.broadcastOther(broadCastBuilder.build(), playerUnit.get32PlayerID());
		
	}
}
