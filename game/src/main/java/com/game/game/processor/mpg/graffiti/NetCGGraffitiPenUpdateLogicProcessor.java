package com.game.game.processor.mpg.graffiti;

import org.springframework.stereotype.Controller;

import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.message.processor.netcg.NetCGGraffitiPenUpdateLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiPenUpdate;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiPenUpdate;

@Controller
@SocketMessage(NetCGGraffitiPenUpdate.class)
public class NetCGGraffitiPenUpdateLogicProcessor extends NetCGGraffitiPenUpdateLogicProcessor_Decoder {
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, GameScene gameScene, long nowTime,
			Object message) throws Exception {
		
		NetCGGraffitiPenUpdate requestMSG = (NetCGGraffitiPenUpdate)message;
		NetGCGraffitiPenUpdate.Builder builder = (NetGCGraffitiPenUpdate.Builder)ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCGraffitiPenUpdate.ordinal());
		
		//	
		builder.setColor(requestMSG.getColor());
		//	
		builder.setShape(requestMSG.getShape());
		
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
		
		graffitiGameScene.broadCast(builder.build());
		
	}
}
