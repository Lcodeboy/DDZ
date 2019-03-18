package com.game.game.processor.mpg.graffiti;

import org.springframework.stereotype.Controller;

import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.message.MessageConstant;
import com.game.message.processor.netcg.NetCGGraffitiLineControllerLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiLineController;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiLineController;

@Controller
@SocketMessage(NetCGGraffitiLineController.class)
public class NetCGGraffitiLineControllerLogicProcessor extends NetCGGraffitiLineControllerLogicProcessor_Decoder {
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, GameScene gameScene, long nowTime,
			Object message) throws Exception {
		NetCGGraffitiLineController requestMSG = (NetCGGraffitiLineController) message;
		
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
		
		NetGCGraffitiLineController.Builder responseBuilder = 
				(NetGCGraffitiLineController.Builder) ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCGraffitiLineController.ordinal());
		
		responseBuilder.setType(requestMSG.getType());
		responseBuilder.setResult(MessageConstant.TFRESULT_TRUE);
		
		graffitiGameScene.broadCastOther(responseBuilder.build(), 
				ioClient.get32StubID());
	}
	
}
