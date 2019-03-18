package com.game.room.processor.client.game;

import org.springframework.stereotype.Controller;

import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcr.NetCRCancelMatchPlayerLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CR.NetCRCancelMatchPlayer;
import com.game.message.proto.ProtoContext_CR.NetRCCancelMatchPlayer;
import com.game.message.proto.ProtoContext_RD.NetRDCancelMatch;
import com.game.room.RoomServer;
import com.game.room.unit.RoomPlayerUnit;

@Controller
@SocketMessage(NetCRCancelMatchPlayer.class)
public class NetCRCancelMatchPlayerLogicProcessor extends NetCRCancelMatchPlayerLogicProcessor_Decoder {

	@Override
	public void handler(SubSystem gameProcess, GameTCPIOClient ioClient, Object message) throws Exception {
		NetCRCancelMatchPlayer requestMSG = (NetCRCancelMatchPlayer) message;

		RoomPlayerUnit roomPlayerUnit = (RoomPlayerUnit) ioClient.getGamePlayer();
		
		RoomServer roomServer = (RoomServer)gameProcess;
		
		NetRDCancelMatch.Builder builder = NetRDCancelMatch.newBuilder();
		
		builder.setGameType(requestMSG.getGameType());
		builder.setPlayerId(roomPlayerUnit.get32PlayerID());
		
		MessageWriteUtil.writeAndFlush(
				roomServer.getDataCenterConn(), 
				roomPlayerUnit.get32PlayerID(), 
				builder.build());

		NetRCCancelMatchPlayer.Builder resultBuilder = NetRCCancelMatchPlayer.newBuilder();
		
		resultBuilder.setResult(MessageConstant.TFRESULT_TRUE);
		
		ioClient.writeAndFlush(NetRCCancelMatchPlayer.ID.CODE1_VALUE, NetRCCancelMatchPlayer.ID.CODE2_VALUE, resultBuilder.build());
		
		ProcessGlobalData.gameLog
				.logic("NetRDCancelMatch " + roomPlayerUnit.getPlayerID() + " " + requestMSG.getGameType());

	}

}
