package com.game.internalgate.service.multiplayergame.statehandler.graffiti;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneStateHandler;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
public class GateServer_GameSceneStateHandler_Activation_Entry implements GameSceneStateHandler {
	
	@Override
	public int getLogicType() {
		return MPGRoomType.GRAFFITI_VALUE;
	}

	@Override
	public void enter(long time, GameScene entity) throws FSMException {
		
	}

	@Override
	public void execute(long time, GameScene entity) throws FSMException {
		
	}

	@Override
	public void exit(long time, GameScene entity) throws FSMException {
		
	}

}
