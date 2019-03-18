package com.game.game.scene.graffiti.statehandler.global;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneStateHandler;
import com.game.framework.mmo.scene.state.GameSceneState_WaitFull;
import com.game.framework.mmo.scene.state.GameSceneState_WaitOpen;
import com.game.game.scene.graffiti.GraffitiGameScene;
import com.game.message.proto.ProtoContext_Common.FieldValue;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
import com.game.message.proto.ProtoContext_Common.MPRDataSyncEventStruct;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGSyncMPRData;

/**
 * 
 * @author suchen
 * @date 2018年11月26日下午8:41:02
 */
public class GameServer_GameSceneStateHandler_WaitNotOpen implements GameSceneStateHandler {
	
	private NetGAIGSyncMPRData.Builder syncDataBuilder = NetGAIGSyncMPRData.newBuilder();
	
	private MPRDataSyncEventStruct.Builder dataBuilder = MPRDataSyncEventStruct.newBuilder();
	
	private FieldValue.Builder fieldValueBuilder = FieldValue.newBuilder();
	
	@Override
	public int getLogicType() {
		return MPGRoomType.GRAFFITI_VALUE;
	}

	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {
		int stateId = gameScene.getStateMachine().getPreviousState().getStateId();
		
		if( gameScene instanceof GraffitiGameScene ) {
			GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
			
			if( stateId == GameSceneState_WaitFull.INSTANCE.getStateId() || stateId == GameSceneState_WaitOpen.INSTANCE.getStateId() ) {
				graffitiGameScene.setEntryWaitStartTime(0);
			}
		}
		
	}
	
	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		GameGSSHandlerFunTable.notifyGate_Global_PlayerJoin( syncDataBuilder, dataBuilder, fieldValueBuilder, time, gameScene );
		GameGSSHandlerFunTable.notifyGate_Logic_Ready( syncDataBuilder, dataBuilder, fieldValueBuilder, time, gameScene );
	}

	@Override
	public void exit(long time, GameScene gameScene) throws FSMException {

	}

}
