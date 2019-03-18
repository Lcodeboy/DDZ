package com.game.game.concurrent;

import java.net.SocketAddress;

import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.threadpool.UnchangedBindRunnable;
import com.game.framework.mmo.scene.GameScenePlayerUnit;
import com.game.framework.network.GameLogicProcessor;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.room.GameRoomPlayerUnit;
import com.game.game.GameServer;
import com.game.message.MessageConstant;
import com.game.message.proto.ProtoContext_BASIC.NetReConn;
import com.game.message.proto.ProtoContext_BASIC.NetReConnResult;
import com.game.message.proto.ProtoContext_Common.TFResult;


public class NetReConnUnchangedBindRunnable extends UnchangedBindRunnable {
	//	
	private GameTCPIOClient ioClient = null;
	//	
	private Object message = null;
	//
	private GameServer gameServer = null;
	//
	private GameLogicProcessor processor = null;
	
	//	
	private NetReConnResult ERROR_TOKENERR = null;
	//	
	private NetReConnResult ERROR_NOTFOUNDPLAYER = null;
	//	
	private NetReConnResult ERROR_OLDCONN = null;

	{
		ERROR_TOKENERR = NetReConnResult.newBuilder()
				.setResult(TFResult.newBuilder().setResult(false).
						setCode(NetReConnResult.ErrorCode.TOKEN_ERR_VALUE))
				.build();

		ERROR_NOTFOUNDPLAYER = NetReConnResult.newBuilder()
				.setResult(TFResult.newBuilder().setResult(false).
						setCode(NetReConnResult.ErrorCode.NOT_FOUND_VALUE))
				.build();
		
		ERROR_OLDCONN = NetReConnResult.newBuilder()
				.setResult(TFResult.newBuilder().setResult(false).
						setCode(NetReConnResult.ErrorCode.OLD_CONN_VALUE))
				.build();
		
	}
	
	public NetReConnUnchangedBindRunnable(GameServer gameServer, GameTCPIOClient gameTCPIOClient,
			GameLogicProcessor processor, Object message ) {
		super((int)(gameTCPIOClient.getStubID() & 0xFFFFFFFF));
		
		this.ioClient = gameTCPIOClient;
		this.message = message;
		this.gameServer = gameServer;
		this.processor = processor;
	}

	@Override
	public void run() {
		int playerId = (int)ioClient.getStubID();
		NetReConn rawMsg = (NetReConn) message;
		String requestToken = null;
		
		SocketAddress newAddress = ioClient.getChannelContext().channel().remoteAddress();
		
		if( ( requestToken = rawMsg.getToken()) == null ) {
			//	远程ToKen不一致, 证明非法链接, 直接关闭
			ProcessGlobalData.gameLog.basic( "PlayerId " + playerId + " ReConn-A 远程ToKen不一致, 证明非法链接, 直接关闭." );
			ioClient.writeAndFlush(NetReConnResult.ID.CODE1_VALUE, NetReConnResult.ID.CODE2_VALUE, ERROR_TOKENERR);
			ioClient.closeAndClear();
			return;
		}
		
		GameRoomPlayerUnit localPlayerUnit = gameServer.getGameRoomPlayerUnit( playerId );
		
		if( localPlayerUnit == null ) {
			
			GameScenePlayerUnit scenePlayerUnit = gameServer.getPlayerUnit( playerId );
			
			if( scenePlayerUnit == null ) {
				//	数据已经被清空掉无法重连了
				ProcessGlobalData.gameLog.basic( "PlayerId " + playerId + " ReConn-B 数据已经清空掉直接关闭. NewConn " + 
						newAddress );
				ioClient.writeAndFlush(NetReConnResult.ID.CODE1_VALUE, 
						NetReConnResult.ID.CODE2_VALUE, ERROR_NOTFOUNDPLAYER);
				ioClient.closeAndClear();
				
				return;
			}
			
			//	关闭以往链接
			scenePlayerUnit.getTCPIOClient().closeAndClear();
			//	数据没有被清空可以继续
			scenePlayerUnit.setReconnclose(true);
			scenePlayerUnit.setGameTCPIOClient( ioClient );
			ioClient.setGamePlayer( scenePlayerUnit );
			
			NetReConnResult.Builder resultBuilder = NetReConnResult.newBuilder();
			
			resultBuilder.setResult(MessageConstant.TFRESULT_TRUE);
			
			ioClient.writeAndFlush(NetReConnResult.ID.CODE1_VALUE, NetReConnResult.ID.CODE2_VALUE, resultBuilder.build());
		} else if(!requestToken.equals(localPlayerUnit.getToKen())) {
			SocketAddress oldAddress = null;
			
			if( localPlayerUnit.getTCPIOClient() != null && localPlayerUnit.getTCPIOClient().getChannelContext() != null ) {
				oldAddress = localPlayerUnit.getTCPIOClient().getChannelContext().channel().remoteAddress();
			}
			
			ProcessGlobalData.gameLog.basic( "PlayerId " + playerId + " ReConn-C 远程ToKen不一致, 证明非法链接, 直接关闭. NewConn " + 
					newAddress + " OldConn " + oldAddress );
			ioClient.writeAndFlush(NetReConnResult.ID.CODE1_VALUE, 
					NetReConnResult.ID.CODE2_VALUE, ERROR_TOKENERR);
			ioClient.closeAndClear();
			return;
		} else if(localPlayerUnit.getTCPIOClient().getChannelContext().channel().equals(ioClient.getChannelContext().channel())) {
			SocketAddress oldAddress = null;
			
			if( localPlayerUnit.getTCPIOClient() != null && localPlayerUnit.getTCPIOClient().getChannelContext() != null ) {
				oldAddress = localPlayerUnit.getTCPIOClient().getChannelContext().channel().remoteAddress();
			}
			
			ProcessGlobalData.gameLog.basic( "PlayerId " + playerId + " ReConn-D 在旧的连接上发起重连  NewConn " + 
					newAddress + " OldConn " + oldAddress);
			ioClient.writeAndFlush(NetReConnResult.ID.CODE1_VALUE, 
					NetReConnResult.ID.CODE2_VALUE, ERROR_OLDCONN);
			return;
		} else {
			
			try {
				processor.handler(gameServer, ioClient, localPlayerUnit);
			} catch (Exception e) {
				ProcessGlobalData.gameLog.basicErr("GameLogicProcessorUBR Error", e);
			}
		}
		
	}

}
