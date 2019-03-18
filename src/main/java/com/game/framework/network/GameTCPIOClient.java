package com.game.framework.network;

import com.game.framework.ProcessGlobalData;
import com.game.framework.network.handler.GameTCPTLVMessage;
import com.google.protobuf.GeneratedMessage;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 封装IO操作, 给业务逻辑使用.
 */
public final class GameTCPIOClient {
    // NETTY 中的通道
    protected ChannelHandlerContext channelContext = null;
    //	用户的数据
    protected volatile GamePlayer gamePlayer = null;
    //	描述网络IO通道的状态
    protected volatile NetIOState netIOState = NetIOState.NONE;
    //	带外ID
    protected volatile long stubID = 0;
    //	KeepAlive 上次接收到数据的时间
    protected volatile long receiveDataTime = 0;
    //	KeepAlive 心跳尝试次数
    protected AtomicInteger tryCount = new AtomicInteger(0);
    //	所属的线程工厂的线程ID
    protected volatile int threadFactoryId = -1;
    //	主动关闭
    protected volatile boolean activeClose = false;
    //
    protected volatile boolean serverIni = false;

    public void setServerIni( boolean serverIni ) {
        this.serverIni = serverIni;
    }

    public boolean isServerIni() {
        return serverIni;
    }

    public GameTCPIOClient( ChannelHandlerContext ctx ) {
        channelContext = ctx;
    }

    public boolean isActiveClose() {
        return activeClose;
    }

    public void resetKeepAlive() {
        tryCount.set(0);
        receiveDataTime = System.currentTimeMillis();
    }

    public NetIOState getNetIOState() {
        return netIOState;
    }

    public void setNetIOState(NetIOState netIOState) {
        this.netIOState = netIOState;
    }

    public void writeAndFlush( int pcode1, int pcode2, long pstubID, GeneratedMessage pmsg ) {
        ProcessGlobalData.gameLog.basic("Send MSG " + pcode1 + " " + pcode2 + " " + pstubID + " " + pmsg.toString());
        channelContext.writeAndFlush( new GameTCPTLVMessage( pcode1, pcode2, pstubID, pmsg) );
    }

    public void writeAndFlush( int pcode1, int pcode2, GeneratedMessage pmsg ) {
        ProcessGlobalData.gameLog.basic("Send MSG " + pcode1 + " " + pcode2 + " " + stubID + " " + pmsg.toString());
        channelContext.writeAndFlush( new GameTCPTLVMessage( pcode1, pcode2, stubID, pmsg) );
    }

    public static enum NetIOState {

        NONE(0),
        NETTY_REGISTER(1),		//	注册初阶段
        NETTY_ACTIVE(2),		//	激活阶段
        NETTY_INACTIVE(3),		//	活跃阶段
        NETTY_UNREGISTERED(4),	//	销毁阶段
        NETTY_EXCEPTION(5);		//	发生异常阶段

        private int value;

        public int getValue() {
            return value;
        }

        private NetIOState( int pvalue ) {
            value = pvalue;
        }

    }

    public ChannelHandlerContext getChannelContext() {
        return channelContext;
    }

    public void setChannelContext(ChannelHandlerContext channelContext) {
        this.channelContext = channelContext;
    }

    public long getStubID() {
        return stubID;
    }

    public void setStubID(long stubID) {
        this.stubID = stubID;
    }

    public int get32StubID() {
        return (int)(stubID & 0xFFFFFFFF);
    }

    public int getHighStubID() {
        return (int)(stubID >>> 32);
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public void closeAndClear() {
        activeClose = true;
        channelContext.close();
        ProcessGlobalData.gameLog.basic("CloseAndClear " + channelContext.channel().remoteAddress());
    }

    public long getReceiveDataTime() {
        return receiveDataTime;
    }

    public void setReceiveDataTime(long receiveDataTime) {
        this.receiveDataTime = receiveDataTime;
    }

    public int getTryCount() {
        return tryCount.get();
    }

    public String toString() {
        return stubID + " " + netIOState + " " + channelContext.channel();
    }
}
