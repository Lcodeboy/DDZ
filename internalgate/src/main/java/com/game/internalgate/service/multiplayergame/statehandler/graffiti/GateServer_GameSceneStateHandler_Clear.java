package com.game.internalgate.service.multiplayergame.statehandler.graffiti;

import com.game.framework.ProcessGlobalData;
import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneSeat;
import com.game.framework.mmo.scene.GameSceneStateHandler;
import com.game.internalgate.InternalGateServer;
import com.game.internalgate.bean.IniGatePlayerInfo;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;

public class GateServer_GameSceneStateHandler_Clear implements GameSceneStateHandler {
	
	@Override
	public int getLogicType() {
		return MPGRoomType.GRAFFITI_VALUE;
	}
	
	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {
		GameSceneSeat[] playerArray = gameScene.getPlayerArray();
		long playerId = 0;
		InternalGateServer gateServer = (InternalGateServer) ProcessGlobalData.gameProcess;
		IniGatePlayerInfo playerInfo = null;
		
		for( GameSceneSeat seat : playerArray ) {
			
			if( (playerId = seat.getPlayerId()) != -1 ) {
				playerInfo = gateServer.getPlayerInfo(playerId);
				
				if( playerInfo == null ) {
					continue;
				}
				
				playerInfo.setApplyGameScene(false);
				playerInfo.setGame(false);
				playerInfo.setGameSceneId(-1);
			}
		}
	}

	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		
	}

	@Override
	public void exit(long time, GameScene entity) throws FSMException {
		
	}

}
