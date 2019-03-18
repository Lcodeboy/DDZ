package com.game.internalgate.service.multiplayergame.statehandler.graffiti;

import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_Common.FieldValue;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
import com.game.message.proto.ProtoContext_Common.MPRDataSyncEventStruct;
import com.game.message.proto.ProtoContext_Common.MPRDataSysEventType;
import com.game.message.proto.ProtoContext_Common.MPRDataSysEventValue_Global;
import com.game.message.proto.ProtoContext_GAIG.NetIGGASyncMPRData;

public final class GateGSSHandlerFunTable {
	
	private GateGSSHandlerFunTable() {
		
	}
	
	public static void notifyGate_Global_PlayerOffline(int playerId, GameScene gameScene ) {
		
		NetIGGASyncMPRData.Builder syncDataBuilder = NetIGGASyncMPRData.newBuilder();
		MPRDataSyncEventStruct.Builder dataBuilder = MPRDataSyncEventStruct.newBuilder();
		FieldValue.Builder fieldValueBuilder = FieldValue.newBuilder();
		
		
		dataBuilder.setRoomType(MPGRoomType.valueOf(gameScene.getLogicType()));
		dataBuilder.setDseType(MPRDataSysEventType.MPR_DSE_TYPE_Global);
		
		dataBuilder.setEventValue(MPRDataSysEventValue_Global.MPR_DSE_VALUE_Global_Player_Offline_VALUE);
		dataBuilder.addFieldValueArray(fieldValueBuilder.setIntValue(playerId));
		
		syncDataBuilder.setDseStruct(dataBuilder.build());
		syncDataBuilder.setRoomId(gameScene.getSceneId());
		
		
		MessageWriteUtil.writeAndFlush(gameScene.getIoClient(), gameScene.getSceneId(), syncDataBuilder.build());
	}
	
	public static void notifyGameServerPlayerApply( long time, GameScene gameScene ) {
		
		NetIGGASyncMPRData.Builder syncDataBuilder = null;
		MPRDataSyncEventStruct.Builder dataBuilder = null;
		FieldValue.Builder fieldValue = null;
		GameSceneSeat[] playerArray = gameScene.getPlayerArray();
		GameSceneSeat seat = null;
		
		int counter = 0;
		
		for( int i = 0, size = playerArray.length; i < size; i++ ) {
			seat = playerArray[i];
			
			if( seat.getPlayerId() == -1 || seat.getApplyTime() == 0 || seat.isSendApplyTime() ) {
				//	没有人或在之间申请加入过则匹配
				continue;
			}
			
			seat.setSendApplyTime(true);
			
			if( counter++ == 0 ) {
				syncDataBuilder = NetIGGASyncMPRData.newBuilder();
				dataBuilder = MPRDataSyncEventStruct.newBuilder();
				fieldValue = FieldValue.newBuilder();
			}
			
			fieldValue.clear();
			dataBuilder.addFieldValueArray(fieldValue.setIntValue( (int)seat.getPlayerId() ).build());
			fieldValue.clear();
			dataBuilder.addFieldValueArray(fieldValue.setIntValue( i ).build());
			
		}
		
		if( counter > 0 ) {
			fieldValue.clear();
			dataBuilder.addFieldValueArray(0, fieldValue.setIntValue(counter).build());
			dataBuilder.setRoomType(MPGRoomType.valueOf(gameScene.getLogicType()));
			dataBuilder.setDseType(MPRDataSysEventType.MPR_DSE_TYPE_Global);
			dataBuilder.setEventValue(MPRDataSysEventValue_Global.MPR_DSE_VALUE_Global_Player_Apply_VALUE);
			
			syncDataBuilder.setDseStruct(dataBuilder.build());
			syncDataBuilder.setRoomId(gameScene.getSceneId());
			
			MessageWriteUtil.writeAndFlush( gameScene.getIoClient(), gameScene.getSceneId(), syncDataBuilder.build());
		}
	}
}
