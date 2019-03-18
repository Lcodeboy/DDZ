package com.game.game.processor.mpg;

import com.game.framework.ProcessGlobalData;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameScenePlayerUnit;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.game.GameServer;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.game.scene.graffiti.statehandler.global.GameGSSHandlerFunTable;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcg.NetCGMPGPlayerOfflineLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CG.NetGCQuitMPGRoom;
import com.game.message.proto.ProtoContext_Common.FieldValue;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
import com.game.message.proto.ProtoContext_Common.MPRDataSyncEventStruct;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGSyncMPRData;
import com.game.message.proto.ProtoContext_RG.NetGRNotifyQuitGame;

public class NetCGMPGPlayerOfflineLogicProcessor extends NetCGMPGPlayerOfflineLogicProcessor_Decoder {

	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, GameScene gameScene, long nowTime,
			Object message) throws Exception {
//		if( true ) {
//			return;
//		}
		//
		GameScenePlayerUnit playerUnit = (GameScenePlayerUnit) ioClient.getGamePlayer();
		//
		boolean quitSuccess = gameScene.playerQuit(playerUnit.get32PlayerID());

		if (quitSuccess) {
			if (gameScene.getLogicType() == MPGRoomType.GRAFFITI_VALUE) {
				GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
				NetGCQuitMPGRoom.Builder roomBuild = (NetGCQuitMPGRoom.Builder) ProcessGlobalData
						.getThreadLocalMSG(ThreadLocalEnum.NetGCQuitMPGRoom.ordinal());
				roomBuild.setRoomUUID(gameScene.getSceneId());
				roomBuild.setPlayerId(playerUnit.get32PlayerID());
				roomBuild.setResult(MessageConstant.TFRESULT_TRUE);
				graffitiGameScene.broadCast(roomBuild.build());
				
				NetGAIGSyncMPRData.Builder syncDataBuilder = (NetGAIGSyncMPRData.Builder) ProcessGlobalData
						.getThreadLocalMSG(ThreadLocalEnum.NetGAIGSyncMPRData.ordinal());
				MPRDataSyncEventStruct.Builder dataBuilder = (MPRDataSyncEventStruct.Builder) ProcessGlobalData
						.getThreadLocalMSG(ThreadLocalEnum.MPRDataSyncEventStruct.ordinal());
				FieldValue.Builder fieldValueBuilder = (FieldValue.Builder) ProcessGlobalData
						.getThreadLocalMSG(ThreadLocalEnum.FieldValue.ordinal());

				GameGSSHandlerFunTable.notifyGate_Global_PlayerOffline(syncDataBuilder, dataBuilder, fieldValueBuilder,
						playerUnit.get32PlayerID(), nowTime, graffitiGameScene);
				
				NetGRNotifyQuitGame.Builder quitMPGGameBuilder = NetGRNotifyQuitGame.newBuilder();
				
				quitMPGGameBuilder.setPlayerId(playerUnit.get32PlayerID());
				
				MessageWriteUtil.writeAndFlush(((GameServer)subSystem).getIniGateConn(), 1, quitMPGGameBuilder.build());

			}
		}
	}

}
