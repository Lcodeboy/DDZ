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
import com.game.message.MessageConstant;
import com.game.message.processor.netcg.NetCGGraffitiReadyLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiReady;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiReady;

@Controller
@SocketMessage(NetCGGraffitiReady.class)
public class NetCGGraffitiReadyLogicProcessor extends NetCGGraffitiReadyLogicProcessor_Decoder {
	
	public void handler( SubSystem subSystem, GameTCPIOClient ioClient, 
			GameScene gameScene, long nowTime, Object message ) throws Exception {
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene)gameScene;
		GameScenePlayerUnit playerUnit = (GameScenePlayerUnit)ioClient.getGamePlayer();
		
		long playerId = playerUnit.getPlayerID();
		
		GameSceneSeat seat = gameScene.getPlayerByIndex( playerUnit.getSeatIndex() );
		
		boolean updateReady = !seat.isReady();
		
		//	FIXME @SuChen 这里需要返回自己的出错结果	你画我猜准备部分
		
		gameScene.playerUpdateReady(playerId, updateReady);
		
		NetGCGraffitiReady.Builder roomBuilder = (NetGCGraffitiReady.Builder)
				ProcessGlobalData.getThreadLocalValue(ThreadLocalEnum.NetGCGraffitiReady.ordinal());
		roomBuilder.clear();
		
		roomBuilder.setPlayerId(playerUnit.get32PlayerID());
		roomBuilder.setReady( seat.isReady() );
		roomBuilder.setRoomUUID( gameScene.getSceneId() );
		roomBuilder.setResult(MessageConstant.TFRESULT_TRUE);
		
		//	广播给其他用户
		graffitiGameScene.broadCast(roomBuilder.build());
	}
	
}
