package com.game.game.processor.mpg;

import org.springframework.stereotype.Controller;

import com.game.common.staticdata.bean.GameExpressionStaticData;
import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameScenePlayerUnit;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.framework.room.GameRoomPlayerUnit;
import com.game.game.GameServer;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.message.processor.netcg.NetCGMPGThrowExpressionLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CG.NetCGMPGThrowExpression;
import com.game.message.proto.ProtoContext_CG.NetGCMPGThrowExpression;
import com.game.message.proto.ProtoContext_Common.GameStaticDataType;

@Controller
@SocketMessage(NetCGMPGThrowExpression.class)
public class NetCGMPGThrowExpressionLogicProcessor extends NetCGMPGThrowExpressionLogicProcessor_Decoder{

	@Override
	public void handler( SubSystem subSystem, GameTCPIOClient ioClient, GameScene gameScene, long nowTime, Object message ) throws Exception {
			GameServer gameServer = (GameServer) subSystem;
		NetCGMPGThrowExpression requestMSG = (NetCGMPGThrowExpression) message;
		GameScenePlayerUnit gamePlayer = (GameScenePlayerUnit) ioClient.getGamePlayer();
		// 发送表情方id
		long playerID = gamePlayer.getPlayerID();
		int roomUUID = requestMSG.getRoomUUID();
		int key = requestMSG.getKey();
		
		GameExpressionStaticData gameExpressionStaticData = (GameExpressionStaticData) gameServer
				.getStaticData(GameStaticDataType.GAME_EXPRESSION, key);

		if (gameExpressionStaticData == null) {
			return;
		}
		
		NetGCMPGThrowExpression.Builder builder = (NetGCMPGThrowExpression.Builder)ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCMPGThrowExpression.ordinal());
		
		builder.setKey(key);
		builder.setPlayerId((int)playerID);
		builder.setRoomUUID(roomUUID);
		
		((GraffitiGameScene) gameScene).broadcastOther(builder.build(), playerID);
	}
	
}
