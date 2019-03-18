package com.game.game.scene.graffiti.statehandler.global;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneStateHandler;
import com.game.message.MessageConstant;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
import com.game.message.proto.ProtoContext_GAIG.NetGAIGActivationMPRRoom;

/**
 * 
 * @author suchen
 * @date 2018年11月26日下午8:41:02
 */
public class GameServer_GameSceneStateHandler_Activation_Success implements GameSceneStateHandler {
	
	@Override
	public int getLogicType() {
		return MPGRoomType.GRAFFITI_VALUE;
	}
	
	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {
		
		if( !gameScene.isActivation() ) {
			//	发送数据
			NetGAIGActivationMPRRoom.Builder builder = NetGAIGActivationMPRRoom.newBuilder();
			//	
			builder.setGameSceneId(gameScene.getSceneId());
			//	
			builder.setResult(MessageConstant.TFRESULT_TRUE);
			//	
			MessageWriteUtil.writeAndFlush(gameScene.getIoClient(), gameScene.getSceneId(), builder.build());
			
			gameScene.setActivation(true);
		}

	}

	@Override
	public void execute(long time, GameScene entity) throws FSMException {
		
	}

	@Override
	public void exit(long time, GameScene entity) throws FSMException {
		
	}

}
