package com.game.room.processor.client.game;

import org.springframework.stereotype.Controller;

import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcr.NetCRMatchPlayerLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CR.NetCRMatchPlayer;
import com.game.message.proto.ProtoContext_CR.NetRCMatchPlayer;
import com.game.message.proto.ProtoContext_RD.NetRDSearchPlayerByGame;
import com.game.room.RoomServer;
import com.game.room.unit.RoomPlayerUnit;

/**
 * 游戏找人的接口
 * 
 * @author suchen
 * @date 2018年6月28日上午9:53:09
 */
@Controller
@SocketMessage(NetCRMatchPlayer.class)
public class NetCRMatchPlayerLogicProcessor extends NetCRMatchPlayerLogicProcessor_Decoder {
	
	public static final NetRCMatchPlayer TRUE_1 = NetRCMatchPlayer.newBuilder().
			setResult(MessageConstant.TRUE_1).build();
	
	@Override
	public void handler(SubSystem gameProcess, GameTCPIOClient ioClient, Object message) throws Exception {
		
		NetCRMatchPlayer requestMSG = (NetCRMatchPlayer) message;
		
		RoomServer roomServer = (RoomServer)gameProcess;
		
		RoomPlayerUnit roomPlayerUnit = (RoomPlayerUnit) ioClient.getGamePlayer();

		long now = System.currentTimeMillis();
		
		ProcessGlobalData.gameLog.basic(Thread.currentThread() + "NetCRMatchPlayer " + (roomPlayerUnit.getPushGameSceneTime() - System.currentTimeMillis()));
		if( System.currentTimeMillis() - roomPlayerUnit.getPushGameSceneTime() <= 5000 ) {
			return;
		}
		
		roomPlayerUnit.setPushDoubleTime(now);
		
		MessageWriteUtil.writeAndFlush(ioClient, TRUE_1);
		
		MessageWriteUtil.writeAndFlush(roomServer.getDataCenterConn(), roomPlayerUnit.getPlayerID(), 
				NetRDSearchPlayerByGame.newBuilder().
				setGameType(requestMSG.getGameType()).
				setPlayerId(roomPlayerUnit.get32PlayerID()).build());
		
		ProcessGlobalData.gameLog.basic("NetCRMatchPlayer " + roomPlayerUnit.getPlayerID() + " " + requestMSG.getGameType() );
	}
}
