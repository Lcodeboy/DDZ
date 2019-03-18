package com.game.game.processor.mpg.graffiti;

import org.springframework.stereotype.Controller;

import com.game.framework.annotation.SocketMessage;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameScenePlayerUnit;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.game.GameServer;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.game.scene.graffiti.GraffitiPlayerData;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcg.NetCGGraffitiUpdateWordLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiUpdateWord;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiUpdateWord;

/**
 *  客户端请求刷新一次牌库
 *
 */
@Controller
@SocketMessage(NetCGGraffitiUpdateWord.class)
public class NetCGGraffitiUpdateWordLogicProcessor extends NetCGGraffitiUpdateWordLogicProcessor_Decoder {

	private NetGCGraffitiUpdateWord FAIL_0 = NetGCGraffitiUpdateWord.newBuilder().
			setResult(MessageConstant.TFRESULT_FALSE).build();
	
	@Override
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, GameScene gameScene, long nowTime,
			Object message) throws Exception {

		GameServer gameServer = (GameServer) subSystem;
		
		GameScenePlayerUnit playerUnit = (GameScenePlayerUnit) ioClient.getGamePlayer();

		GameSceneSeat seat = gameScene.getPlayerByIndex(playerUnit.getSeatIndex());

		GraffitiPlayerData playerData = (GraffitiPlayerData) seat.getPlayerData();
		
		//	判断该用户是否是否更新过词
		if (playerData.isUpdatedWord()) {
			MessageWriteUtil.writeAndFlush(ioClient, FAIL_0);
		} else {
			MessageWriteUtil.writeAndFlush(ioClient, GraffitiGameScene.createUpdateWord(gameServer, playerData, gameScene.getSceneId()));
			playerData.setUpdatedWord(true);
		}

	}

}
