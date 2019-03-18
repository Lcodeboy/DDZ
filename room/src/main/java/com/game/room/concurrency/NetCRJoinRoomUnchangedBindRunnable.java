package com.game.room.concurrency;

import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.threadpool.UnchangedBindRunnable;
import com.game.framework.network.GameLogicProcessor;
import com.game.framework.network.GameTCPIOClient;
import com.game.message.MessageWriteUtil;
import com.game.message.proto.ProtoContext_CR.NetCRJoinRoom;
import com.game.message.proto.ProtoContext_CR.NetRCJoinRoom;
import com.game.message.proto.ProtoContext_CR.NetRCTiPlayer;
import com.game.message.proto.ProtoContext_Common.TFResult;
import com.game.message.proto.ProtoContext_Common.TiPlayer;
import com.game.room.RoomServer;
import com.game.room.unit.RoomPlayerUnit;
import com.game.room.unit.data.RoomPlayerAllData;

public class NetCRJoinRoomUnchangedBindRunnable extends UnchangedBindRunnable {
	//	
	private GameTCPIOClient ioClient = null;
	//	
	private Object message = null;
	//
	private RoomServer roomServer = null;
	//
	private GameLogicProcessor processor = null;
	
	private static final NetRCJoinRoom JOIN_TOKEN_ERROR = 
			NetRCJoinRoom.newBuilder().setResult(TFResult.newBuilder().setCode(NetRCJoinRoom.ErrorCode.ERROR_TOKEN_VALUE)).build();

	private void sendToKenErrorMessage( GameTCPIOClient ioClient  ) {
		ioClient.writeAndFlush(
						NetRCJoinRoom.ID.CODE1_VALUE, 
						NetRCJoinRoom.ID.CODE2_VALUE, 
						ioClient.getStubID(), JOIN_TOKEN_ERROR );
	}
	
	public NetCRJoinRoomUnchangedBindRunnable(RoomServer roomServer, GameTCPIOClient gameTCPIOClient,
			GameLogicProcessor processor, Object message ) {
		super((int)(gameTCPIOClient.getStubID() & 0xFFFFFFFF));
		
		this.ioClient = gameTCPIOClient;
		this.message = message;
		this.roomServer = roomServer;
		this.processor = processor;
	}
	
	@Override
	public void run() {
		int playerId = (int)ioClient.getStubID();
		NetCRJoinRoom rawMsg = (NetCRJoinRoom) message;
		String requestToken = null;
		
		if( ( requestToken = rawMsg.getToken()) == null ) {
			//	远程ToKen不一致, 证明非法链接, 直接关闭
			ProcessGlobalData.gameLog.basic( "远程ToKen不一致-A, 证明非法链接, 直接关闭. INSERVER" );
			sendToKenErrorMessage( ioClient );
			ioClient.closeAndClear();
			return;
		}
		
		RoomPlayerUnit localPlayerUnit = roomServer.getRoomPlayerUnit( playerId );
		
		if( localPlayerUnit == null ) {
			//	验证 ToKen
			boolean checkRemoteToken1 = roomServer.checkToKen( playerId, requestToken );
			
			if( checkRemoteToken1 ) {
				RoomPlayerUnit roomPlayerUnit = new RoomPlayerUnit();
				
				roomPlayerUnit.setPlayerID(playerId);
				roomPlayerUnit.setToKen(requestToken);
				roomPlayerUnit.setRoomPlayerAllData(new RoomPlayerAllData());
				roomPlayerUnit.setGameTCPIOClient(ioClient);
				ioClient.setGamePlayer(roomPlayerUnit);
				roomPlayerUnit.getRoomPlayerAllData().setPlayerid(playerId);
				
				roomServer.putRoomPlayerUnit(roomPlayerUnit);
				try {
					processor.handler(roomServer, ioClient, message);
				} catch (Exception e) {
					ProcessGlobalData.gameLog.basicErr("GameLogicProcessorUBR Error", e);
				}
				
			} else {
				//	远程ToKen不一致, 证明非法链接, 直接关闭
				ProcessGlobalData.gameLog.basic( "远程ToKen不一致-B, 证明非法链接, 直接关闭. INSERVER" );
				sendToKenErrorMessage( ioClient );
				ioClient.closeAndClear();
			}
			
			return;
		} 
		
		if( localPlayerUnit.remoteChannel().equals(ioClient.getChannelContext().channel()) ) {
			//	TODO 错误发送, 我已经在服务器中为啥还要给我发送这个
			return;
		} else {
			//	验证TOKen
			boolean checkRemoteToken = roomServer.checkToKen( playerId, requestToken );
				
			if( checkRemoteToken ) {
				//	远程ToKen一致, 证明没有启动keeplive时客户端重新登录.
				MessageWriteUtil.writeAndFlush(localPlayerUnit.getTCPIOClient(), NetRCTiPlayer.newBuilder().setType(TiPlayer.Player_TI).build());
				localPlayerUnit.getTCPIOClient().closeAndClear();
					
				localPlayerUnit.setGameTCPIOClient(ioClient);
				ioClient.setGamePlayer(localPlayerUnit);
					
				localPlayerUnit.setToKen(requestToken);
				
				try {
					processor.handler(roomServer, ioClient, message);
				} catch (Exception e) {
					ProcessGlobalData.gameLog.basicErr("GameLogicProcessorUBR Error", e);
				}
					
			} else {
				//	远程ToKen不一致, 证明非法链接, 直接关闭
				ProcessGlobalData.gameLog.basic( "远程ToKen不一致-C, 证明非法链接, 直接关闭. INSERVER" );
				sendToKenErrorMessage( ioClient );
				ioClient.closeAndClear();
				return;
			}
				
		}
			
	}
}
