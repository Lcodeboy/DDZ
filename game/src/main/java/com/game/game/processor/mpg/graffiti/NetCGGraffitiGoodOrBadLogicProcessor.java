package com.game.game.processor.mpg.graffiti;

import org.springframework.stereotype.Controller;

import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.game.scene.graffiti.GraffitiPlayerData;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcg.NetCGGraffitiGoodOrBadLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiGoodOrBad;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiGoodOrBad;


@Controller
@SocketMessage(NetCGGraffitiGoodOrBad.class)
public class NetCGGraffitiGoodOrBadLogicProcessor extends NetCGGraffitiGoodOrBadLogicProcessor_Decoder {



	@Override
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, GameScene gameScene, long nowTime,
			Object message) throws Exception {
		NetCGGraffitiGoodOrBad requestMSG = (NetCGGraffitiGoodOrBad) message;
	
		int playerId = requestMSG.getPlayerId();
		
		boolean good = requestMSG.getResult();
		
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
		
		GameSceneSeat seat = graffitiGameScene.getPlayerById(playerId);
		
		if( seat == null ) {
			seat = graffitiGameScene.getQuitPlayerById(playerId);
		}
		if( seat == null ) {
			NetGCGraffitiGoodOrBad.Builder builder = (NetGCGraffitiGoodOrBad.Builder)ProcessGlobalData.getThreadLocalMSG(
					ThreadLocalEnum.NetGCGraffitiGoodOrBad.ordinal() );
			
			builder.setResult(MessageConstant.TFRESULT_FALSE);
			MessageWriteUtil.writeAndFlush(ioClient, builder.build());
			return;
		}
		
		GraffitiPlayerData playerData = (GraffitiPlayerData)seat.getPlayerData();
		
		if( good ) {
			playerData.incrTotalGood();
		} else {
			playerData.incrTotalBad();
		}
		
		NetGCGraffitiGoodOrBad.Builder builder = (NetGCGraffitiGoodOrBad.Builder)ProcessGlobalData.getThreadLocalMSG(
				ThreadLocalEnum.NetGCGraffitiGoodOrBad.ordinal());
		builder.setGood(good);
		builder.setSendPlayerId(ioClient.get32StubID());
		builder.setReceivePlayerId(playerId);
		builder.setResult(MessageConstant.TFRESULT_TRUE);
		graffitiGameScene.broadCast(builder.build());
	}


}
