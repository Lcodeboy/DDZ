package com.game.game.processor.mpg;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.framework.util.filterword.FilterwordImpl;
import com.game.framework.util.filterword.SensitiveWordTree;
import com.game.game.GameServer;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcg.NetCGMPGRoomMSGLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CG.NetCGMPGRoomMSG;
import com.game.message.proto.ProtoContext_CG.NetGCMPGDuadMSG;
import com.game.message.proto.ProtoContext_Common.GameStaticDataType;
import com.game.message.proto.ProtoContext_Common.MPRMsgStruct;

@Controller
@SocketMessage(NetCGMPGRoomMSG.class)
public class NetCGMPGRoomMSGLogicProcessor extends NetCGMPGRoomMSGLogicProcessor_Decoder {
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, GameScene gameScene, long nowTime,
			Object message) throws Exception {
		
		GameServer gameServer = (GameServer) subSystem;
		
		NetCGMPGRoomMSG requestMSG = (NetCGMPGRoomMSG) message;
		
		MPRMsgStruct mprMsgStruct = requestMSG.getMsgs(0);
		
		String msg = mprMsgStruct.getMsgValue(0).getStringValue();
		
		int playerID = mprMsgStruct.getPlayerId();

		NetGCMPGDuadMSG.Builder builder = (NetGCMPGDuadMSG.Builder) ProcessGlobalData
				.getThreadLocalMSG(ThreadLocalEnum.NetGCMPGDuadMSG.ordinal());
		
		boolean isCheck = true;

		if (isCheck) {

			SensitiveWordTree tree = (SensitiveWordTree) gameServer.getAllStaticData(GameStaticDataType.KEYWORD);

			FilterwordImpl filter = new FilterwordImpl(tree);

			List<String> matchAll = filter.matchAll(msg, -1, true, true);

			if (!matchAll.isEmpty()) {
				// msg 含有屏蔽字
				builder.setResult(MessageConstant.TFRESULT_FALSE);
				builder.setRoomUUID( requestMSG.getRoomUUID() );
				for( int i = 0, size = requestMSG.getMsgsCount(); i < size; i++ ) {
					builder.addMsgs(requestMSG.getMsgs(i));
				}
				MessageWriteUtil.writeAndFlush(ioClient, builder.build());
				return;
			}
		}
		
		for( int i = 0, size = requestMSG.getMsgsCount(); i < size; i++ ) {
			builder.addMsgs(mprMsgStruct);
		}
		builder.setResult(MessageConstant.TFRESULT_TRUE);
		((GraffitiGameScene) gameScene).broadcast(builder.build(), playerID);

	}
}
