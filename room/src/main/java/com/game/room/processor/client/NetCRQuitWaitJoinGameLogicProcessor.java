package com.game.room.processor.client;

import org.springframework.stereotype.Controller;

import com.game.framework.annotation.SocketMessage;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcr.NetCRQuitWaitJoinGameLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CR.NetCRQuitWaitJoinGame;
import com.game.message.proto.ProtoContext_RD.NetRDQuitWaitJoinGame;
import com.game.room.RoomServer;

/**
 * 点击  loading 界面的退出按钮
 *
 */
@Controller
@SocketMessage(NetCRQuitWaitJoinGame.class)
public class NetCRQuitWaitJoinGameLogicProcessor extends NetCRQuitWaitJoinGameLogicProcessor_Decoder {

	@Override
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, Object message) throws Exception {

		RoomServer roomServer = (RoomServer) subSystem;

		int playerID = ioClient.get32StubID();
		
		//  清除  DataCenter 中 GameType 的状态
		MessageWriteUtil.writeAndFlush(roomServer.getDataCenterConn(), playerID,
				NetRDQuitWaitJoinGame.newBuilder().setPlayerId(playerID).build());
	}
}
