package com.game.game.processor.mpg.graffiti;

import org.springframework.stereotype.Controller;

import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.message.processor.netcg.NetCGGraffitiPenLineLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CG.NetCGGraffitiPenLine;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiPenLine;

@Controller
@SocketMessage(NetCGGraffitiPenLine.class)
public class NetCGGraffitiPenLineLogicProcessor extends NetCGGraffitiPenLineLogicProcessor_Decoder {
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, GameScene gameScene, long nowTime,
			Object message) throws Exception {
		NetCGGraffitiPenLine requestMSG = (NetCGGraffitiPenLine) message;
		
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
		
		NetGCGraffitiPenLine.Builder builder = (NetGCGraffitiPenLine.Builder) ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCGraffitiPenLine.ordinal());
		
		builder.addAllX(requestMSG.getXList());
		builder.addAllY(requestMSG.getYList());
		builder.addAllType(requestMSG.getTypeList());
		
		graffitiGameScene.broadcastOther(builder.build(), ioClient.getGamePlayer().get32PlayerID());
	}
}
