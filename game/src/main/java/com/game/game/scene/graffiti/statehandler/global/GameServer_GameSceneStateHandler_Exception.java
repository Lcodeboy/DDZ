package com.game.game.scene.graffiti.statehandler.global;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameScenePlayerUnit;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.mmo.scene.GameSceneStateHandler;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_CG.NetGCQuitMPGRoom;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
import com.game.message.proto.ProtoContext_Common.MPRDataSyncEventStruct;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGSyncMPRData;

/**
 * 
 * @author suchen
 * @date 2018年11月26日下午8:41:02
 */
public class GameServer_GameSceneStateHandler_Exception implements GameSceneStateHandler {

	private NetGAIGSyncMPRData.Builder syncDataBuilder = NetGAIGSyncMPRData.newBuilder();

	private MPRDataSyncEventStruct.Builder dataBuilder = MPRDataSyncEventStruct.newBuilder();

	private NetGCQuitMPGRoom.Builder quitMPGRoomBuilder = NetGCQuitMPGRoom.newBuilder();
	
	@Override
	public int getLogicType() {
		return MPGRoomType.GRAFFITI_VALUE;
	}

	@Override
	public void enter(long time, GameScene entity) throws FSMException {

	}

	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		
		int playerId = 0;
		
		GameSceneSeat[] array = gameScene.getPlayerArray();
		GameScenePlayerUnit gameScenePlayerUnit = null;
		GameSceneSeat seat = null;
		
		for( int i = 0, size = array.length; i < size; i++ ) {
			seat = array[i];
			
			if( seat.getPlayerId() != - 1 && (gameScenePlayerUnit = seat.getGameScenePlayerUnit()) != null ) {
				quitMPGRoomBuilder.clear();
				
				quitMPGRoomBuilder.setRoomUUID(gameScene.getSceneId());
				quitMPGRoomBuilder.setPlayerId(playerId);
				quitMPGRoomBuilder.setResult(MessageConstant.TRUE_1);
				
				//	踢掉用户无需广播给其他人
				MessageWriteUtil.writeAndFlush(gameScenePlayerUnit.getTCPIOClient(), quitMPGRoomBuilder.build());
				
			}
			
		}
		
		GameGSSHandlerFunTable.notifyGate_Global_Exception(syncDataBuilder, dataBuilder, time, gameScene);
	}

	@Override
	public void exit(long time, GameScene entity) throws FSMException {

	}

}
