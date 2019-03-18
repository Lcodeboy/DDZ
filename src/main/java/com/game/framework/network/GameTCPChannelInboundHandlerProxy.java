package com.game.framework.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class GameTCPChannelInboundHandlerProxy extends ChannelInboundHandlerAdapter {

	private GameTCP gameTCP = null;

	public GameTCPChannelInboundHandlerProxy(GameTCP gameTCP) {
		this.gameTCP = gameTCP;
	}

	public void exceptionCaught(final ChannelHandlerContext ctx, Throwable e) throws Exception {
		gameTCP.exceptionCaught(ctx, e);
	}

	public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
		gameTCP.channelUnregistered(ctx);
		ctx.fireChannelUnregistered();
	}

	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		gameTCP.channelActive(ctx);
		ctx.fireChannelActive();
	}

	public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
		gameTCP.channelInactive(ctx);
		ctx.fireChannelInactive();
	}

	public void channelRegistered(final ChannelHandlerContext ctx) throws Exception {
		gameTCP.channelRegistered(ctx);
		ctx.fireChannelRegistered();
	}
	
	public void userEventTriggered(final ChannelHandlerContext ctx, Object evt ) throws Exception {
		gameTCP.userEventTriggered( ctx, evt );
		ctx.fireUserEventTriggered( evt );
	}
	
}
