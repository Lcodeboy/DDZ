package com.game.internalgate.service.multiplayergame.statehandler.graffiti;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneStateHandler;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;

public class GateServer_GameSceneStateHandler_WaitOpen implements GameSceneStateHandler {
	
	@Override
	public int getLogicType() {
		return MPGRoomType.GRAFFITI_VALUE;
	}
	
	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {
		
	}

	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		GateGSSHandlerFunTable.notifyGameServerPlayerApply( time, gameScene );
	}

	@Override
	public void exit(long time, GameScene gameScene) throws FSMException {
		
	}

}
