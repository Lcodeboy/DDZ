package com.game.game.processor.mpg;

import org.springframework.stereotype.Controller;

import com.game.common.cache.redis.DefaultRedisContextFacade;
import com.game.common.mpg.MPGTypeZhuanHuan;
import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameScenePlayerUnit;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.game.GameServer;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.game.scene.graffiti.GraffitiPlayerData;
import com.game.game.scene.graffiti.statehandler.global.GameGSSHandlerFunTable;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcg.NetCGJoinMPGRoomLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CG.NetCGJoinMPGRoom;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiCountDown;
import com.game.message.proto.ProtoContext_CG.NetGCJoinMPGRoom;
import com.game.message.proto.ProtoContext_CG.NetGCOtherJoinMPGRoom;
import com.game.message.proto.ProtoContext_Common.FieldValue;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
import com.game.message.proto.ProtoContext_Common.MPRDataSyncEventStruct;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGSyncMPRData;

@Controller
@SocketMessage(NetCGJoinMPGRoom.class)
public class NetCGJoinMPGRoomLogicProcessor extends NetCGJoinMPGRoomLogicProcessor_Decoder {
	
	public void handler( SubSystem subSystem, GameTCPIOClient ioClient, 
			GameScene gameScene, long nowTime, Object message ) throws Exception {
		
//		GameServer gameServer = (GameServer) subSystem;
		NetCGJoinMPGRoom requestMSG = (NetCGJoinMPGRoom) message;
		
		long playerId = requestMSG.getPlayerCardData().getPlayerID();
		
//		if( gameServer.getGameRoomPlayerUnit((int)playerId) != null ) {
//			
//			NetGCJoinMPGRoom.Builder builder = (NetGCJoinMPGRoom.Builder)
//					ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCJoinMPGRoom.ordinal());
//			builder.setRoomUUID( gameScene.getSceneId() );
//			builder.setResult( MessageConstant.FAIL_5 );
//			MessageWriteUtil.writeAndFlush(ioClient, builder.build());
//			return;
//		}
		
		ProcessGlobalData.gameLog.basic("NetCGJoinMPGRoom " + requestMSG.getPlayerCardData().getPlayerID());
		
		DefaultRedisContextFacade redisContextFacade = (DefaultRedisContextFacade)ProcessGlobalData.redisContextFacade;
		
		String token = redisContextFacade.getPlayerToken((int)playerId);
		
		if( !token.equals(requestMSG.getToken() ) ) {
			NetGCJoinMPGRoom.Builder builder = (NetGCJoinMPGRoom.Builder)
					ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCJoinMPGRoom.ordinal());
			builder.setRoomUUID( gameScene.getSceneId() );
			builder.setResult(MessageConstant.FAIL_1);
			MessageWriteUtil.writeAndFlush(ioClient, builder.build());
			return;
		}
		
		int position = gameScene.playerJoin(playerId, nowTime);
		
		if( position == -1 ) {
			NetGCJoinMPGRoom.Builder builder = (NetGCJoinMPGRoom.Builder)
					ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCJoinMPGRoom.ordinal());
			builder.setRoomUUID( gameScene.getSceneId() );
			builder.setResult(MessageConstant.FAIL_4);
			MessageWriteUtil.writeAndFlush(ioClient, builder.build());
			return;
		}
		
		if( position == -2 ) {
			
			NetGAIGSyncMPRData.Builder syncDataBuilder = (NetGAIGSyncMPRData.Builder) ProcessGlobalData
					.getThreadLocalMSG(ThreadLocalEnum.NetGAIGSyncMPRData.ordinal());
			MPRDataSyncEventStruct.Builder dataBuilder = (MPRDataSyncEventStruct.Builder) ProcessGlobalData
					.getThreadLocalMSG(ThreadLocalEnum.MPRDataSyncEventStruct.ordinal());
			FieldValue.Builder fieldValueBuilder = (FieldValue.Builder) ProcessGlobalData
					.getThreadLocalMSG(ThreadLocalEnum.FieldValue.ordinal());

			
			GameGSSHandlerFunTable.notifyGate_Global_PlayerTiRen(syncDataBuilder, dataBuilder, fieldValueBuilder, 
					(int)playerId, nowTime, gameScene);
			
			
			NetGCJoinMPGRoom.Builder builder = (NetGCJoinMPGRoom.Builder)
					ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCJoinMPGRoom.ordinal());
			builder.setRoomUUID( gameScene.getSceneId() );
			builder.setResult(MessageConstant.FAIL_4);
			MessageWriteUtil.writeAndFlush(ioClient, builder.build());
			return;
		}
		
		GameScenePlayerUnit playerUnit = new GameScenePlayerUnit();
		ioClient.setGamePlayer(playerUnit);
		playerUnit.setGameTCPIOClient(ioClient);
		playerUnit.setPlayerID(playerId);
		
		GameServer gameServer = (GameServer) subSystem;
		gameServer.pushPlayerUnit(playerUnit.get32PlayerID(), playerUnit);
		
		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
		
		GameSceneSeat seat = graffitiGameScene.getPlayerByIndex(position);
		seat.setGameScenePlayerUnit(playerUnit);
		seat.setOnline(true);
		
		GraffitiPlayerData playerData = (GraffitiPlayerData)seat.getPlayerData();
		playerData.setPlayerCardData(requestMSG.getPlayerCardData());
		playerUnit.setGameScene(graffitiGameScene);
		playerUnit.setSeatIndex(position);
		
		//	//////////////////////////////////////////
		//	NetGCJoinMPGRoom			给自己返回数据
		//	//////////////////////////////////////////
		NetGCJoinMPGRoom.Builder joinBuilder = (NetGCJoinMPGRoom.Builder)
				ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCJoinMPGRoom.ordinal());
		joinBuilder.setPosition(position);
		joinBuilder.setResult(MessageConstant.TFRESULT_TRUE);
		joinBuilder.setGameType(MPGTypeZhuanHuan.roomTypeToGameType(MPGRoomType.valueOf(gameScene.getLogicType())));
		joinBuilder.setReady(true);
		joinBuilder.setRoomUUID(gameScene.getSceneId());
		
		GameSceneSeat[] playerArray = graffitiGameScene.getPlayerArray();
		GraffitiPlayerData tempSeatData = null;
		
		for( int i = 0, size = playerArray.length; i < size; i++ ) {

			if( playerArray[i].getPlayerId() == -1 || !playerArray[i].isJoin() || playerArray[i].getPlayerId() == playerId) {
				continue;
			}
			
			
			tempSeatData = (GraffitiPlayerData) playerArray[i].getPlayerData();
			joinBuilder.addOtherPosition(i);
			joinBuilder.addOtherPlayerCardData( tempSeatData.getPlayerCardData() );
			joinBuilder.addPlayerReady(playerArray[i].isReady());
		}
		
		if( gameScene.getEntryWaitStartTime() > 0 ) {
			
			NetGCGraffitiCountDown.Builder builder = (NetGCGraffitiCountDown.Builder)
					ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCGraffitiCountDown.ordinal());
			builder.setRoomUUID(gameScene.getSceneId());
			
			builder.setStart(true);
			builder.setConsumeTime((int)(nowTime - gameScene.getEntryWaitStartTime()));
			
			((GraffitiGameScene)gameScene).broadCast(builder.build());
		}
		
		MessageWriteUtil.writeAndFlush(playerUnit.getTCPIOClient(), joinBuilder.build());
		
		//	///////////////////////////////////////////
		//	NetGCOtherJoinMPGRoom		给房间里其他人数据
		//	///////////////////////////////////////////
		
		NetGCOtherJoinMPGRoom.Builder otherJoinBuilder = (NetGCOtherJoinMPGRoom.Builder)
				ProcessGlobalData.getThreadLocalMSG(ThreadLocalEnum.NetGCOtherJoinMPGRoom.ordinal());
		
		otherJoinBuilder.setPosition(position);
		otherJoinBuilder.setReady(gameScene.getPlayerByIndex(position).isReady());
		otherJoinBuilder.setPlayerCardData(requestMSG.getPlayerCardData());
		otherJoinBuilder.setRoomUUID(gameScene.getSceneId());
		
		graffitiGameScene.broadcastOther(otherJoinBuilder.build(), playerId);
		
	}
}
