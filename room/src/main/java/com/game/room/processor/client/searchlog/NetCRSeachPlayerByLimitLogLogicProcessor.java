package com.game.room.processor.client.searchlog;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.game.common.cache.redis.DefaultRedisContextFacade;
import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcr.NetCRSeachPlayerByLimitLogLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CR.NetCRSeachPlayerByLimitLog;
import com.game.message.proto.ProtoContext_Common.PlayerLeastData;
import com.game.message.proto.ProtoContext_RIG.NetRIGGetPlayerState;
import com.game.room.RoomServer;
import com.game.room.unit.RoomPlayerUnit;

@Controller
@SocketMessage(NetCRSeachPlayerByLimitLog.class)
public class NetCRSeachPlayerByLimitLogLogicProcessor extends NetCRSeachPlayerByLimitLogLogicProcessor_Decoder {

	@Override
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, Object message) throws Exception {
		RoomPlayerUnit roomPlayerUnit = (RoomPlayerUnit) ioClient.getGamePlayer();
		int requsetId = roomPlayerUnit.get32PlayerID();
		RoomServer roomServer = (RoomServer) subSystem;
		DefaultRedisContextFacade redisContext = (DefaultRedisContextFacade) ProcessGlobalData.redisContextFacade;
		List<PlayerLeastData> searchList = redisContext.getFuzzyMatch(requsetId);
		if(searchList == null || searchList.isEmpty()) {
			return;
		}
		NetRIGGetPlayerState.Builder builder = NetRIGGetPlayerState.newBuilder();
		builder.setRequestId(requsetId);
		for (int i = 0; i < searchList.size(); i++) {
			builder.addPlayerId(searchList.get(i).getPlayerid());
		}
		MessageWriteUtil.writeAndFlush(roomServer.getIniGateConn(), roomPlayerUnit.get32PlayerID(), builder.build());

	}

}
