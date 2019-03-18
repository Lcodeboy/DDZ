package com.game.internalgate.service.multiplayergame.statehandler.graffiti;

import com.game.framework.exception.FSMException;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.mmo.scene.GameSceneStateHandler;
import com.game.framework.network.GameTCPIOClient;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_Common.MPGRoomType;
import com.game.message.proto.ProtoContext_Common.MPRDataSyncEventStruct;
import com.game.message.proto.ProtoContext_Common.MPRDataSysEventType;
import com.game.message.proto.ProtoContext_Common.MPRDataSysEventValue_Global;
import com.game.message.proto.ProtoContext_GAIG.NetIGGASyncMPRData;

public class GateServer_GameSceneStateHandler_Init implements GameSceneStateHandler {
	
	@Override
	public int getLogicType() {
		return MPGRoomType.GRAFFITI_VALUE;
	}
	
	@Override
	public void enter(long time, GameScene gameScene) throws FSMException {
		GameTCPIOClient ioClient = gameScene.getIoClient();
		
		//	网关服通知游戏服打开门
		NetIGGASyncMPRData.Builder syncDataBuilder = NetIGGASyncMPRData.newBuilder();
		MPRDataSyncEventStruct.Builder dataBuilder = MPRDataSyncEventStruct.newBuilder();
		
		dataBuilder.setRoomType(MPGRoomType.valueOf(gameScene.getLogicType()));
		dataBuilder.setDseType(MPRDataSysEventType.MPR_DSE_TYPE_Global);
		dataBuilder.setEventValue(MPRDataSysEventValue_Global.MPR_DSE_VALUE_Global_Open_Door_VALUE);
		
		syncDataBuilder.setDseStruct(dataBuilder.build());
		syncDataBuilder.setRoomId(gameScene.getSceneId());
		
		MessageWriteUtil.writeAndFlush(ioClient, gameScene.getSceneId(), syncDataBuilder.build());
	}

	
	@Override
	public void execute(long time, GameScene gameScene) throws FSMException {
		GateGSSHandlerFunTable.notifyGameServerPlayerApply( time, gameScene );
	}

	@Override
	public void exit(long time, GameScene gameScene) throws FSMException {
		
	}

}
