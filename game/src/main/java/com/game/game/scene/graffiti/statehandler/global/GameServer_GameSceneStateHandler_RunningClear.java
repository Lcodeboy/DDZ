package com.game.game.scene.graffiti.statehandler.global;

import com.game.framework.ProcessGlobalData;
import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.mmo.scene.GameSceneStateHandler;
import com.game.framework.mmo.scene.state.GameSceneState_WaitNotOpen;
import com.game.game.ThreadLocalEnum;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
import com.game.message.proto.ProtoContext_Common.MPRDataSyncEventStruct;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGSyncMPRData;

/**
 * 
 * @author suchen
 * @date 2018年11月26日下午8:41:02
 */
public class GameServer_GameSceneStateHandler_RunningClear implements GameSceneStateHandler {
	
	@Override
	public int getLogicType() {
		return MPGRoomType.GRAFFITI_VALUE;
	}
	
	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {
		
	}

	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		if( gameScene.getLogicType() == MPGRoomType.GRAFFITI_VALUE ) {
			GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
			
			graffitiGameScene.clearLogicData();
			GameSceneSeat[] seatArray = (GameSceneSeat[]) graffitiGameScene.getPlayerArray();
			GameSceneSeat seat = null;
			
			for( int i = 0, size = seatArray.length; i < size; i++ ) {
				if( (seat = seatArray[i]).getPlayerId() != -1 ) {
					seat.setReady(false);
				}
			}
			
			gameScene.setEntryWaitStartTime(0);
			
			for( int i = 0, size = seatArray.length; i < size; i++ ) {
				if( (seat = seatArray[i]).getPlayerId() != -1 ) {
					gameScene.changeState(time, GameSceneState_WaitNotOpen.INSTANCE);
					break;
				}
			}
			
			NetGAIGSyncMPRData.Builder syncDataBuilder = (NetGAIGSyncMPRData.Builder) ProcessGlobalData
					.getThreadLocalMSG(ThreadLocalEnum.NetGAIGSyncMPRData.ordinal());
			MPRDataSyncEventStruct.Builder dataBuilder = (MPRDataSyncEventStruct.Builder) ProcessGlobalData
					.getThreadLocalMSG(ThreadLocalEnum.MPRDataSyncEventStruct.ordinal());
			
			GameGSSHandlerFunTable.notifyGate_Global_RunningClear(syncDataBuilder, dataBuilder, time, graffitiGameScene);
		}
		
		
	}

	@Override
	public void exit(long time, GameScene gameScene) throws FSMException {
		
	}

}
