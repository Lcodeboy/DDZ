package com.game.game.scene.graffiti.statehandler.global;

import com.game.framework.ProcessGlobalData;
import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.mmo.scene.GameSceneStateHandler;
import com.game.framework.process.GameProcess;
import com.game.game.GameServer;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.game.scene.graffiti.state.GraffitiState_ChooseWord;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_CG.NetGCGraffitiGameStart;
import com.game.message.proto.ProtoContext_CG.NetGCQuitMPGRoom;
import com.game.message.proto.ProtoContext_Common.FieldValue;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
import com.game.message.proto.ProtoContext_Common.MPRDataSyncEventStruct;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGSyncMPRData;
import com.game.message.proto.ProtoContext_RG.NetGRNotifyQuitGame;

/**
 * 
 * @author suchen
 * @date 2018年11月26日下午8:41:02
 */
public class GameServer_GameSceneStateHandler_Running implements GameSceneStateHandler {

	@Override
	public int getLogicType() {
		return MPGRoomType.GRAFFITI_VALUE;
	}

	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {

		if (gameScene.getLogicType() == MPGRoomType.GRAFFITI_VALUE) {
			

			GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
			GameSceneSeat[] playerArray = graffitiGameScene.getPlayerArray();
			for( int i = 0; i < playerArray.length; i++ ) {
				if( playerArray[i].getPlayerId() > 0 && !playerArray[i].isJoin() ) {
					//	加入太慢了 但是房间已经开了 直接清理掉
					ProcessGlobalData.gameLog.basic("Clear JoinRoom TimeOut Player " + playerArray[i].toString());
					playerArray[i].clear(false);
				}
			}
			
			graffitiGameScene.saveStartReadyAndJoinPeopleCount();
			
			GameSceneSeat seat = null;
			
			for( int i = 0, size = playerArray.length; i < size; i++ ) {
				seat = playerArray[i];
				seat.clearQuitPlayerId();
			}
			
			NetGCGraffitiGameStart.Builder build = (NetGCGraffitiGameStart.Builder) ProcessGlobalData
					.getThreadLocalMSG(ThreadLocalEnum.NetGCGraffitiGameStart.ordinal());

			build.setRoomUUID(gameScene.getSceneId());


			
			for (int i = 0; i < playerArray.length; i++) {
				if (playerArray[i].isJoin() && !playerArray[i].isReady()) {
					// 进入但没有准备, 踢出
					build.addPlayerId((int) playerArray[i].getPlayerId());
				}
			}
			graffitiGameScene.broadCast(build.build());

			for (int i = 0; i < playerArray.length; i++) {
				if (playerArray[i].isJoin() && !playerArray[i].isReady()) {
					// 进入但没有准备, 踢出
					NetGCQuitMPGRoom.Builder roomBuild = (NetGCQuitMPGRoom.Builder) ProcessGlobalData
							.getThreadLocalMSG(ThreadLocalEnum.NetGCQuitMPGRoom.ordinal());
					
					roomBuild.setRoomUUID(gameScene.getSceneId());
					roomBuild.setPlayerId((int) playerArray[i].getPlayerId());
					roomBuild.setResult(MessageConstant.TFRESULT_TRUE);
					graffitiGameScene.broadCast(roomBuild.build());
					
					NetGAIGSyncMPRData.Builder syncDataBuilder = (NetGAIGSyncMPRData.Builder) ProcessGlobalData
							.getThreadLocalMSG(ThreadLocalEnum.NetGAIGSyncMPRData.ordinal());
					MPRDataSyncEventStruct.Builder dataBuilder = (MPRDataSyncEventStruct.Builder) ProcessGlobalData
							.getThreadLocalMSG(ThreadLocalEnum.MPRDataSyncEventStruct.ordinal());
					FieldValue.Builder fieldValueBuilder = (FieldValue.Builder) ProcessGlobalData
							.getThreadLocalMSG(ThreadLocalEnum.FieldValue.ordinal());

					int quitPlayerId = (int) playerArray[i].getPlayerId();
					
					GameGSSHandlerFunTable.notifyGate_Global_PlayerQuit(syncDataBuilder, dataBuilder, fieldValueBuilder,
							quitPlayerId, time, graffitiGameScene);
					
					graffitiGameScene.playerQuit(quitPlayerId, false);
					
					NetGRNotifyQuitGame.Builder quitMPGGameBuilder = NetGRNotifyQuitGame.newBuilder();
					
					quitMPGGameBuilder.setPlayerId(quitPlayerId);
					
					MessageWriteUtil.writeAndFlush(((GameServer)ProcessGlobalData.gameProcess.getSubSystem("GameServer")).getIniGateConn(), 1, quitMPGGameBuilder.build());
				}
			}
			graffitiGameScene.getLogicStateMachine().changeState(time, GraffitiState_ChooseWord.INSTANCE);
		}
	}

	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		if (gameScene.getLogicType() == MPGRoomType.GRAFFITI_VALUE) {
			GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
			graffitiGameScene.getLogicStateMachine().update(time);
		}
	}

	@Override
	public void exit(long time, GameScene gameScene) throws FSMException {
		
	}

}
