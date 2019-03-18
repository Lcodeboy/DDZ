package com.game.internalgate.service.multiplayergame.statehandler.graffiti;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.mmo.scene.GameSceneStateHandler;
import com.game.framework.mmo.scene.state.GameSceneState_WaitNotOpen;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;

public class GateServer_GameSceneStateHandler_RunningClear implements GameSceneStateHandler {
	@Override
	public int getLogicType() {
		return MPGRoomType.GRAFFITI_VALUE;
	}

	@Override
	public void enter(long time, GameScene entity) throws FSMException {

	}

	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		if( gameScene.getLogicType() == MPGRoomType.GRAFFITI_VALUE ) {
			
			GameSceneSeat[] seatArray = (GameSceneSeat[]) gameScene.getPlayerArray();
			GameSceneSeat seat = null;
			
			for( int i = 0, size = seatArray.length; i < size; i++ ) {
				if( (seat = seatArray[i]).getPlayerId() != -1 ) {
					seat.setReady(false);
				}
			}
			
			gameScene.setEntryWaitStartTime(0);
			
			for( int i = 0, size = seatArray.length; i < size; i++ ) {
				if( (seat = seatArray[i]).getPlayerId() != -1 ) {
//					gameScene.setOpenDoorPlayerId(seat.getPlayerId());
					gameScene.changeState(time, GameSceneState_WaitNotOpen.INSTANCE);
					break;
				}
			}
		}
	}

	@Override
	public void exit(long time, GameScene entity) throws FSMException {

	}
}
