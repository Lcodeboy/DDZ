package com.game.room.processor.client.game;

import org.springframework.stereotype.Controller;

import com.game.common.CommonConstantContext;
import com.game.common.cache.redis.DefaultRedisContextFacade;
import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;
import com.game.framework.util.ToKenUtil;
import com.game.message.MessageWriteUtil;
import com.game.message.processor.netcr.NetCRSettlementThreeStateLogicProcessor_Decoder;
import com.game.message.proto.ProtoContext_CR.NetCRSettlementThreeState;
import com.game.message.proto.ProtoContext_CR.NetRCSettlementThreeState;
import com.game.message.proto.ProtoContext_Common.GameType;
import com.game.message.proto.ProtoContext_Common.PlayerType;
import com.game.message.proto.ProtoContext_Common.SettlementThreeState;
import com.game.message.proto.ProtoContext_RIG.NetRIGCreateGame;
import com.game.room.RoomServer;
import com.game.room.unit.RoomPlayerUnit;

/**
 * 
 * @author suchen
 * @date 2018年8月31日下午12:29:41
 */
@Controller
@SocketMessage(NetCRSettlementThreeState.class)
public class NetCRSettlementThreeStateLogicProcessor extends NetCRSettlementThreeStateLogicProcessor_Decoder {
	@Override
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, Object message) throws Exception {
		NetCRSettlementThreeState requestMSG = (NetCRSettlementThreeState) message;
		
		RoomServer roomServer = (RoomServer) subSystem;
		
		if( CommonConstantContext.hasRobat(requestMSG.getPassivePlayerId() ) ) {
			return;
		}
		
		int activePlayerId = requestMSG.getActivePlayerId();
		int passivePlayerId = requestMSG.getPassivePlayerId();
		GameType gameType = requestMSG.getGameType();
		String prevGameUUID = requestMSG.getGameUUID();
		//	
		RoomPlayerUnit activePlayerUnit = roomServer.getInServerRoomPlayerUnit( activePlayerId );
		//	
		RoomPlayerUnit passivePlayerUnit = roomServer.getInServerRoomPlayerUnit( passivePlayerId );
		
		boolean activeNotNull = activePlayerUnit != null;
		boolean passiveNotNull = passivePlayerUnit != null;
		
		//	
		DefaultRedisContextFacade redisContextFacade = 
				(DefaultRedisContextFacade) ProcessGlobalData.redisContextFacade;
		
		SettlementThreeState threeState = requestMSG.getState();
		
		//	转发消息
		
		boolean sendIsActive = activePlayerId == ioClient.get32StubID();
		
		NetRCSettlementThreeState.Builder builder = NetRCSettlementThreeState.newBuilder();
		builder.setGameType(gameType);
		builder.setPassivePlayerId(passivePlayerId);
		builder.setActivePlayerId(activePlayerId);
		builder.setState(threeState);
		builder.setGameUUID(prevGameUUID);
		
		if( activeNotNull && passiveNotNull ) {
			//	共同节点
			
			if( sendIsActive ) {
				MessageWriteUtil.writeAndFlush(passivePlayerUnit.getTCPIOClient(), builder.build());
			} else {
				MessageWriteUtil.writeAndFlush(activePlayerUnit.getTCPIOClient(), builder.build());
			}
		} else {
			//	不同节点
			
			if( sendIsActive ) {
				MessageWriteUtil.writeAndFlush(roomServer.getIniGateConn(), passivePlayerId, builder.build());
			} else {
				MessageWriteUtil.writeAndFlush(roomServer.getIniGateConn(), activePlayerId, builder.build());
			}
			
		}
		
		//	/////////////////////////////////
		//
		//	/////////////////////////////////
		
		switch( threeState ) {
		case PAGE:
		case CGAME:
			redisContextFacade.delSettlementgame(activePlayerId, passivePlayerId, prevGameUUID);
			break;
		case READEY:
		
			if( activeNotNull ) {
				boolean result = redisContextFacade.setSettlementGame(activePlayerId, passivePlayerId, prevGameUUID);
				
				if( result ) {
					//	启动游戏
					
					//	以主动为主动
					
					NetRIGCreateGame.Builder createGameBuilder = NetRIGCreateGame.newBuilder();

					createGameBuilder.setActivePlayerID(activePlayerId);
					createGameBuilder.setActivePlayerType(PlayerType.NORMAL);

					createGameBuilder.setPassivePlayerID(passivePlayerId);
					createGameBuilder.setPassivePlayerType(PlayerType.NORMAL);

					createGameBuilder.setGameType(gameType);
					createGameBuilder.setGameUUID(ToKenUtil.tokentmp(96));

					MessageWriteUtil.writeAndFlush(roomServer.getIniGateConn(), activePlayerId,
							createGameBuilder.build());
				}
				
				return;
			} else {
				if( passiveNotNull ) {
					boolean result = redisContextFacade.setSettlementGame(activePlayerId, passivePlayerId, prevGameUUID);
					
					if( result ) {
						//	启动游戏
						
						//	以被动为主动
						
						NetRIGCreateGame.Builder createGameBuilder = NetRIGCreateGame.newBuilder();

						createGameBuilder.setActivePlayerID(passivePlayerId);
						createGameBuilder.setActivePlayerType(PlayerType.NORMAL);

						createGameBuilder.setPassivePlayerID(activePlayerId);
						createGameBuilder.setPassivePlayerType(PlayerType.NORMAL);

						createGameBuilder.setGameType(gameType);
						createGameBuilder.setGameUUID(ToKenUtil.tokentmp(96));

						MessageWriteUtil.writeAndFlush(roomServer.getIniGateConn(), passivePlayerId, createGameBuilder.build());
					}
					return;
				}
			}
		
			break;
		}
			
	}
}
