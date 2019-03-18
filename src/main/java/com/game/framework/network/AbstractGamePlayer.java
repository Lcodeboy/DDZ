package com.game.framework.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 */
public class AbstractGamePlayer implements GamePlayer {

	//	
	protected long playerID = 0;
	//
	protected String toKen = null;
	//
	protected GameTCPIOClient gameTCPIOClient = null;
	//	
	protected volatile long joinProcessTime = 0;
	//		
	protected volatile long quitProcessTime = 0;
	
	@Override
	public long getQuitProcessTime() {
		return quitProcessTime;
	}
	
	@Override
	public long getJoinProcessTime() {
		return joinProcessTime;
	}
	
	@Override
	public long getPlayerID() {
		return playerID;
	}

	@Override
	public void setPlayerID(long playerID) {
		this.playerID = playerID;
	}
	
	@Override
	public GameTCPIOClient getTCPIOClient() {
		return gameTCPIOClient;
	}

	@Override
	public int get32PlayerID() {
		return (int)(playerID & 0xFFFFFFFF);
	}
	
	public String getToKen() {
		return toKen;
	}

	public void setToKen(String toKen) {
		this.toKen = toKen;
	}
	
	public void setGameTCPIOClient( GameTCPIOClient gameTCPIOClient ) {
		this.gameTCPIOClient = gameTCPIOClient;
	}
	
	public ChannelHandlerContext remoteChannelHandlerContext() {
		return gameTCPIOClient.getChannelContext();
	}
	
	public Channel remoteChannel() {
		return gameTCPIOClient.getChannelContext().channel();
	}	
}
