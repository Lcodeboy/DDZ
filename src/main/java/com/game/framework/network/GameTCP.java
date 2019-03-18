package com.game.framework.network;

import com.game.framework.ProcessGlobalData;
import com.game.framework.ProcessGlobalData.OSPlatform;
import com.game.framework.process.GameProcess;
import com.game.framework.process.SubSystem;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.epoll.Epoll;

import java.net.InetSocketAddress;

/**
 * @Description: TCP接口的基类
 */
public abstract class GameTCP implements SubSystem {
	
	protected int nodeId = -1;
	/** 绑定到的IP地址			*/
	protected String bindAddr = null;
	/** 绑定到的端口号			*/
	protected int bindPort = 0;
	/* */
	protected String externalAddr = null;
	
	protected String subSystemName = null;
	
	protected abstract void channelUnregistered( final ChannelHandlerContext ctx ) throws Exception;
	
	protected abstract void exceptionCaught(final ChannelHandlerContext ctx, Throwable e ) throws Exception;
	
	protected abstract void channelActive( final ChannelHandlerContext ctx ) throws Exception;
	
	protected abstract void channelInactive( final ChannelHandlerContext ctx ) throws Exception;
	
	protected abstract void channelRegistered( final ChannelHandlerContext ctx ) throws Exception;
	
	protected abstract void userEventTriggered(final ChannelHandlerContext ctx, Object evt ) throws Exception;
	
	//	参见RocketMQ -> NettyRemotingServer 类的同名函数的修改
	protected boolean useEpoll(GameProcess gameProcess) {
        return ProcessGlobalData.osPlatform == OSPlatform.Linux
            && ProcessGlobalData.configReader.getBooleanParam(gameProcess.getSection(), "UseEpoll", false)
            && Epoll.isAvailable();
    }
    
	protected String channelToAddressString( final Channel channel ) {
		
		InetSocketAddress remoteAddress = (InetSocketAddress)channel.remoteAddress();
		InetSocketAddress localAddress = (InetSocketAddress)channel.localAddress();
		
		return "Remote " + remoteAddress.getHostString() + " " + remoteAddress.getPort() + 
				" Local " + localAddress.getHostString() + " " + localAddress.getPort();
		
	}

	protected String channelCTXToAddressString( final ChannelHandlerContext ctx ) {
		
		InetSocketAddress remoteAddress = (InetSocketAddress)ctx.channel().remoteAddress();
		InetSocketAddress localAddress = (InetSocketAddress)ctx.channel().localAddress();
		
		return "Remote " + remoteAddress.getHostString() + " " + remoteAddress.getPort() + 
				" Local " + localAddress.getHostString() + " " + localAddress.getPort();
		
	}
	
	public static String toChannelState(Channel channel) {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("R " + channel.remoteAddress() + " L " + channel.localAddress());
		stringBuilder.append(" isWritable ").append(channel.isWritable());
		stringBuilder.append(" isActive ").append(channel.isActive());
		stringBuilder.append(" isRegistered ").append(channel.isRegistered());
		stringBuilder.append(" isOpen ").append(channel.isOpen());
		
		return stringBuilder.toString();
	}
	
	public abstract void executeMSG(GameTCPIOClient ioClient, int code1, int code2, Object message, GameLogicProcessor gameLogicProcessor );
	
	public String getSubSystemName() {
		return subSystemName;
	}
	
	public int getNodeId() {
		return nodeId;
	}
	
	public String getBindArr() {
		return bindAddr;
	}
	
	public int getBindPort() {
		return bindPort;
	}
	
	public String getExternalArr() {
		return externalAddr;
	}
	
}
