package com.game.game.scene.graffiti.statehandler.global;

import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.network.GameTCPIOClient;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_Common.FieldValue;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
import com.game.message.proto.ProtoContext_Common.MPRDataSyncEventStruct;
import com.game.message.proto.ProtoContext_Common.MPRDataSysEventType;
import com.game.message.proto.ProtoContext_Common.MPRDataSysEventValue_Global;
import com.game.message.proto.ProtoContext_Common.MPRDataSysEventValue_Logic_Graffiti;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGSyncMPRData;

public final class GameGSSHandlerFunTable {
	
	private GameGSSHandlerFunTable() {
		
	}
	
	
	public static void notifyGate_Global_RunningClear(NetGAIGSyncMPRData.Builder syncDataBuilder,
			MPRDataSyncEventStruct.Builder dataBuilder, long time, GameScene gameScene ) {
		syncDataBuilder.clear();
		dataBuilder.clear();
		
		dataBuilder.setRoomType(MPGRoomType.valueOf(gameScene.getLogicType()));
		dataBuilder.setDseType(MPRDataSysEventType.MPR_DSE_TYPE_Global);
		
		dataBuilder.setEventValue(MPRDataSysEventValue_Global.MPR_DSE_VALUE_Global_Running_Clear_VALUE);
		
		syncDataBuilder.setDseStruct(dataBuilder.build());
		syncDataBuilder.setRoomId(gameScene.getSceneId());
		
		MessageWriteUtil.writeAndFlush(gameScene.getIoClient(), gameScene.getSceneId(), syncDataBuilder.build());
	}
	
	public static void notifyGate_Global_PlayerTiRen(NetGAIGSyncMPRData.Builder syncDataBuilder,
			MPRDataSyncEventStruct.Builder dataBuilder,
			FieldValue.Builder fieldValueBuilder, int playerId, long time, GameScene gameScene ) {
		syncDataBuilder.clear();
		dataBuilder.clear();
		fieldValueBuilder.clear();
		
		dataBuilder.setRoomType(MPGRoomType.valueOf(gameScene.getLogicType()));
		dataBuilder.setDseType(MPRDataSysEventType.MPR_DSE_TYPE_Global);
		
		dataBuilder.setEventValue(MPRDataSysEventValue_Global.MPR_DES_VALUE_Global_Player_TiRen_VALUE);
		dataBuilder.addFieldValueArray(fieldValueBuilder.setIntValue(playerId));
		
		syncDataBuilder.setDseStruct(dataBuilder.build());
		syncDataBuilder.setRoomId(gameScene.getSceneId());
		
		MessageWriteUtil.writeAndFlush(gameScene.getIoClient(), gameScene.getSceneId(), syncDataBuilder.build());
	}
	
	public static void notifyGate_Global_PlayerQuit(NetGAIGSyncMPRData.Builder syncDataBuilder,
			MPRDataSyncEventStruct.Builder dataBuilder,
			FieldValue.Builder fieldValueBuilder, int playerId, long time, GameScene gameScene ) {
		syncDataBuilder.clear();
		dataBuilder.clear();
		fieldValueBuilder.clear();
		
		dataBuilder.setRoomType(MPGRoomType.valueOf(gameScene.getLogicType()));
		dataBuilder.setDseType(MPRDataSysEventType.MPR_DSE_TYPE_Global);
		
		dataBuilder.setEventValue(MPRDataSysEventValue_Global.MPR_DES_VALUE_Global_Player_Quit_VALUE);
		dataBuilder.addFieldValueArray(fieldValueBuilder.setIntValue(playerId));
		
		syncDataBuilder.setDseStruct(dataBuilder.build());
		syncDataBuilder.setRoomId(gameScene.getSceneId());
		
		MessageWriteUtil.writeAndFlush(gameScene.getIoClient(), gameScene.getSceneId(), syncDataBuilder.build());
	}
	
	public static void notifyGate_Global_PlayerOffline(NetGAIGSyncMPRData.Builder syncDataBuilder,
			MPRDataSyncEventStruct.Builder dataBuilder,
			FieldValue.Builder fieldValueBuilder, int playerId, long time, GameScene gameScene ) {
		syncDataBuilder.clear();
		dataBuilder.clear();
		fieldValueBuilder.clear();
		
		dataBuilder.setRoomType(MPGRoomType.valueOf(gameScene.getLogicType()));
		dataBuilder.setDseType(MPRDataSysEventType.MPR_DSE_TYPE_Global);
		
		dataBuilder.setEventValue(MPRDataSysEventValue_Global.MPR_DSE_VALUE_Global_Player_Offline_VALUE);
		dataBuilder.addFieldValueArray(fieldValueBuilder.setIntValue(playerId));
		
		syncDataBuilder.setDseStruct(dataBuilder.build());
		syncDataBuilder.setRoomId(gameScene.getSceneId());
		
		
		MessageWriteUtil.writeAndFlush(gameScene.getIoClient(), gameScene.getSceneId(), syncDataBuilder.build());
	}
	
	public static void notifyGate_Global_PlayerJoin(NetGAIGSyncMPRData.Builder syncDataBuilder, 
			MPRDataSyncEventStruct.Builder dataBuilder, 
			FieldValue.Builder fieldValueBuilder, long time, GameScene gameScene ) {
		
		GameSceneSeat[] playerArray = gameScene.getPlayerArray();
		
		int counter = 0;
		for( int i = 0, size = playerArray.length; i < size; i++ ) {
			if( playerArray[i].getJoinTime() > 0 && !playerArray[i].isPrevTickJoin() ) {
				if( counter++ == 0 ) {
					syncDataBuilder.clear();
					dataBuilder.clear();
					fieldValueBuilder.clear();
				}
				
				playerArray[i].setPrevTickJoin(true);
				
				fieldValueBuilder.setIntValue((int)playerArray[i].getPlayerId());
				dataBuilder.addFieldValueArray(fieldValueBuilder.build());
				fieldValueBuilder.setIntValue(i);
				dataBuilder.addFieldValueArray(fieldValueBuilder.build());
			}
		}
		
		if( counter > 0 ) {
			
			
			dataBuilder.setRoomType(MPGRoomType.valueOf(gameScene.getLogicType()));
			dataBuilder.setDseType(MPRDataSysEventType.MPR_DSE_TYPE_Global);
			
			dataBuilder.setEventValue(MPRDataSysEventValue_Global.MPR_DSE_VALUE_Global_Player_Join_VALUE);
			dataBuilder.addFieldValueArray(0, fieldValueBuilder.setIntValue(counter));
			
			syncDataBuilder.setDseStruct(dataBuilder.build());
			syncDataBuilder.setRoomId(gameScene.getSceneId());
			
			//	///////////////////////////////////////
			//	还需要场景内的其他用户转发, 有新用户加入
			//	///////////////////////////////////////
			
			MessageWriteUtil.writeAndFlush(gameScene.getIoClient(), gameScene.getSceneId(), syncDataBuilder.build());
		}
	}
	
	public static void notifyGate_Logic_Ready(NetGAIGSyncMPRData.Builder syncDataBuilder, 
			MPRDataSyncEventStruct.Builder dataBuilder, 
			FieldValue.Builder fieldValueBuilder, long time, GameScene gameScene ) {
		
		GameSceneSeat[] seatArray = gameScene.getPlayerArray();
		
		int count = 0;
		
		//  有人更新准备状态            <<导致副本状态变迁>>
		//  FieldValue[0]  更新准备状态玩家个数
		//      playerCount:int32
		//  int index = 1; 有几个用户更新准备状态就有几个这样的结构体
		//  FieldValue[index++]
		//      playerID:int32;
		//  FieldValue[index++]
		//      ready:boolean;
		
		GameSceneSeat seat = null;
		
		for (int i = 0, size = seatArray.length; i < size; i++) {
			
			if( !(seat = seatArray[i]).isPrevTickReady() ) {
				continue;
			}
			
			if (seat.isReady() != seat.isPrevReady()) {
				if( count++ == 0 ) {
					dataBuilder.clear();
				}
				
				fieldValueBuilder.clear();
				fieldValueBuilder.setIntValue((int)seat.getPlayerId());
				dataBuilder.addFieldValueArray(fieldValueBuilder);
				
				fieldValueBuilder.clear();
				fieldValueBuilder.setBoolValue(seat.isReady());
				dataBuilder.addFieldValueArray(fieldValueBuilder);
				
				seat.setPrevTickReady( false );
			}
		}
		
		if( count > 0 ) {
			fieldValueBuilder.setIntValue(count);
			dataBuilder.addFieldValueArray(0, fieldValueBuilder);
			
			dataBuilder.setRoomType(MPGRoomType.valueOf(gameScene.getLogicType()));
			dataBuilder.setDseType(MPRDataSysEventType.MPR_DSE_TYPE_Logic);
			dataBuilder.setEventValue(MPRDataSysEventValue_Logic_Graffiti.MPR_DSE_VALUE_Logic_Ready_VALUE);

			syncDataBuilder.clear();
			syncDataBuilder.setDseStruct(dataBuilder.build());
			syncDataBuilder.setRoomId(gameScene.getSceneId());
			GameTCPIOClient ioClient = gameScene.getIoClient();
			MessageWriteUtil.writeAndFlush(ioClient, gameScene.getSceneId(), syncDataBuilder.build());
		}
		
	}

	public static void notifyGate_Global_Exception( NetGAIGSyncMPRData.Builder syncDataBuilder,
			MPRDataSyncEventStruct.Builder dataBuilder, long time, GameScene gameScene ) {
		syncDataBuilder.clear();
		dataBuilder.clear();
		
		dataBuilder.setRoomType(MPGRoomType.valueOf(gameScene.getLogicType()));
		dataBuilder.setDseType(MPRDataSysEventType.MPR_DSE_TYPE_Global);
		
		dataBuilder.setEventValue(MPRDataSysEventValue_Global.MPR_DES_VALUE_Global_Copy_Error_VALUE);
		
		syncDataBuilder.setDseStruct(dataBuilder.build());
		syncDataBuilder.setRoomId(gameScene.getSceneId());
		
		MessageWriteUtil.writeAndFlush(gameScene.getIoClient(), gameScene.getSceneId(), syncDataBuilder.build());
	}
	
}
