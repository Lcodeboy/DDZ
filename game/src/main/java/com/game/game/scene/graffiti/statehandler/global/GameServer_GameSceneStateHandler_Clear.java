package com.game.game.scene.graffiti.statehandler.global;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneStateHandler;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;

/**
 * 
 * @author suchen
 * @date 2018年11月26日下午8:41:02
 */
public class GameServer_GameSceneStateHandler_Clear implements GameSceneStateHandler {
	
	@Override
	public int getLogicType() {
		return MPGRoomType.GRAFFITI_VALUE;
	}

	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {
//		GraffitiGameScene graffitiGameScene = (GraffitiGameScene) gameScene;
//		
//		graffitiGameScene.getLogicStateMachine().changeState(time, GraffitiState_None.INSTANCE);
	}

	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		
	}

	@Override
	public void exit(long time, GameScene gameScene) throws FSMException {
		
	}

}
