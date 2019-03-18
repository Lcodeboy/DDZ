package com.game.room.processor.client.game;

import org.springframework.stereotype.Controller;

import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcr.NetCRCancelSeachPlayerByLimitLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CR.NetCRCancelSeachPlayerByLimit;
import com.game.message.proto.ProtoContext_CR.NetRCCancelSeachPlayerByLimit;
import com.game.message.proto.ProtoContext_Common.TFResult;
import com.game.message.proto.ProtoContext_RD.NetRDCancelSeachPlayerByLimit;
import com.game.room.RoomServer;
import com.game.room.unit.RoomPlayerUnit;

/**
 * 取消模糊匹配
 * @author wuyang
 * @date 2018年8月29日
 *
 */
@Controller
@SocketMessage(NetCRCancelSeachPlayerByLimit.class)
public class NetCRCancelSeachPlayerByLimitLogicProcessor extends NetCRCancelSeachPlayerByLimitLogicProcessor_Decoder{

	@Override
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, Object message) throws Exception {
		
		int get32StubID = ioClient.get32StubID();
		
		NetRCCancelSeachPlayerByLimit.Builder builder = NetRCCancelSeachPlayerByLimit.newBuilder();
		
		NetRDCancelSeachPlayerByLimit.Builder _builder = NetRDCancelSeachPlayerByLimit.newBuilder();
		
		RoomServer roomServer = (RoomServer)subSystem;

		RoomPlayerUnit roomPlayerUnit = (RoomPlayerUnit) ioClient.getGamePlayer();
		
		_builder.setPlayerID(get32StubID);
		
		MessageWriteUtil.writeAndFlush( roomServer.getDataCenterConn(), get32StubID,  _builder.build() );
		
		builder.setResult(TFResult.newBuilder().setResult(true));
		MessageWriteUtil.writeAndFlush(ioClient, builder.build());
		
		ProcessGlobalData.gameLog
		.logic(" NetRDCancelSeachPlayerByLimit " + roomPlayerUnit.getPlayerID() + " " );

	}
}
