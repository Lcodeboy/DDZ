package com.game.room.processor.client.game;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Controller;

import com.game.common.cache.redis.DefaultRedisContextFacade;
import com.game.common.cache.redis.bean.CBeanMatchLog;
import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcr.NetCRBattleMatchLogListLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CR.NetCRBattleMatchLogList;
import com.game.message.proto.ProtoContext_CR.NetRCBattleMatchLogList;
import com.game.message.proto.ProtoContext_Common.PlayerMatchBattleData;
import com.game.room.unit.RoomPlayerUnit;

@Controller
@SocketMessage(NetCRBattleMatchLogList.class)
public class NetCRBattleMatchLogListLogicProcessor extends NetCRBattleMatchLogListLogicProcessor_Decoder {
	
	@Override
	public void handler(SubSystem gameProcess, GameTCPIOClient ioClient, Object message) throws Exception {
		
		RoomPlayerUnit playerUnit = (RoomPlayerUnit) ioClient.getGamePlayer();
		
		int playerId = playerUnit.get32PlayerID();
		
		DefaultRedisContextFacade redisContext = (DefaultRedisContextFacade)ProcessGlobalData.redisContextFacade;
		ArrayList<CBeanMatchLog> matchLogList = null;
		
		if( (matchLogList = playerUnit.getRoomPlayerAllData().getMatchLog()) == null ) {
			matchLogList = redisContext.getMatchLog(playerId);
		}
		
		if( matchLogList == null ) {
			return;
		}
		
		PlayerMatchBattleData.Builder dataBuilder = PlayerMatchBattleData.newBuilder();
		NetRCBattleMatchLogList.Builder resultBuilder = NetRCBattleMatchLogList.newBuilder();
		CBeanMatchLog beanMatchLog = null;
		int[] array = null;
		
		
		ArrayList<CBeanMatchLog> result = new ArrayList<CBeanMatchLog>();
        for (Object s : matchLogList) {        	
            if (Collections.frequency(result, s) < 1)
                result.add((CBeanMatchLog) s);
        }
        
		for( int i = 0; i < result.size(); i++ ) {
			
			beanMatchLog = result.get(i);
			
			array = redisContext.getGFPCount(playerId, beanMatchLog.getPlayerId());
			
			dataBuilder.setWin(array[0]);
			dataBuilder.setFail(array[1]);
			
			resultBuilder.addData(beanMatchLog.copyTo(dataBuilder).build());
			dataBuilder.clear();
		}
		
		MessageWriteUtil.writeAndFlush(ioClient, resultBuilder.build());
	}
	
}
