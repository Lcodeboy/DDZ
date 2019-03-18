package com.game.room.processor.client.home;

import java.util.Arrays;

import org.springframework.stereotype.Controller;

import com.game.common.cache.redis.DefaultRedisContextFacade;
import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.network.process.SubSystem;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcr.NetCROftenPlayGameLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CR.NetCROftenPlayGame;
import com.game.message.proto.ProtoContext_CR.NetRCOftenPlayGame;
import com.game.message.proto.ProtoContext_Common.GameType;
import com.game.room.unit.RoomPlayerUnit;

@Controller
@SocketMessage(NetCROftenPlayGame.class)
public class NetCROftenPlayGameLogicProcessor extends NetCROftenPlayGameLogicProcessor_Decoder{
	
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, Object message) throws Exception {

		RoomPlayerUnit playerUnit = (RoomPlayerUnit) ioClient.getGamePlayer();
		
//		RoomServer roomServer = (RoomServer)subSystem;
		
		int playerId = playerUnit.get32PlayerID();
		
		DefaultRedisContextFacade redisContext = (DefaultRedisContextFacade)ProcessGlobalData.redisContextFacade;
		
//		GameConfigStaticData gameSort = (GameConfigStaticData)roomServer.getStaticData(GameStaticDataType.PlaneRandom,0);
//		GameConfigStaticData gameSort1 = (GameConfigStaticData)roomServer.getStaticData(GameStaticDataType.PlaneRandom,1);
//		GameConfigStaticData gameSort2 = (GameConfigStaticData)roomServer.getStaticData(GameStaticDataType.PlaneRandom,2);
//
//		int gameTypeNumber = gameSort.getId();
//		GameType gameType0 = GameType.valueOf(gameTypeNumber);
//		
//		int gameTypeNumber1 = gameSort1.getId();
//		GameType gameType1 = GameType.valueOf(gameTypeNumber1);
//		
//		int gameTypeNumber2 = gameSort2.getId();
//		GameType gameType2 = GameType.valueOf(gameTypeNumber2);
		
		int[] playerGamecount = redisContext.getPlayerGameCount(playerId,GameType.values());
		int[] totalGameCount = redisContext.getTotalGameCount(GameType.values());
		
		int playerCounter = 0;
		OftenPlayGameCounter[] array = new OftenPlayGameCounter[GameType.values().length];
		
		for(int i = 0, size = playerGamecount.length; i < size; i++) {
			if( playerGamecount[i] > 0 ) {
				playerCounter++;
			}
			
			array[i] = OftenPlayGameCounter.valueOf(playerGamecount[i], GameType.valueOf(i));
//			System.out.println(array[i]);
		}
	
//		System.out.println("playerCounter " + playerCounter);
		
		Arrays.sort(array);
		
		NetRCOftenPlayGame.Builder builder = NetRCOftenPlayGame.newBuilder();
		
		int length = GameType.values().length;		
		
		GameType gameType = null;
	
			
		for(int i = 0; i < length; i++) {						
			gameType = array[i].getGameType();
			
			if( gameType == GameType.Graffiti ) {
				continue;
			}
			
//			if( gameType.getNumber() >= 9 ) {
//			continue;
//			}
			
			builder.addGametype(gameType);
			builder.addCount(totalGameCount[gameType.getNumber()]);		
		}		
		
		//	TODO @ZXF 策划配表 这个2要读表, 说明主页显示用户几个常玩游戏
		
		int maxPlayerCount = 2;
		
		builder.setPlayerCount(Math.min(playerCounter, maxPlayerCount));
		
		MessageWriteUtil.writeAndFlush(ioClient, builder.build());
	}

}
