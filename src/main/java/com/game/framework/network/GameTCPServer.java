package com.game.framework.network;

import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.DefaultThreadFactoryAdapter;
import com.game.framework.concurrent.FastThreadIDLocalThread;
import com.game.framework.network.GameTCPIOClient.NetIOState;
import com.game.framework.network.context.ChannelAttributeContext;
import com.game.framework.network.handler.GameTCPTLVDecoder;
import com.game.framework.network.handler.GameTCPTLVEncoder;
import com.game.framework.process.GameProcess;
import com.game.framework.util.IniFileReader;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * @Description: 
 */
public abstract class GameTCPServer extends GameTCP {
	
	/** Netty IO线程池 */
	protected int bossThreadCount = 0;
	/** Netty 工作线程池 */
	protected int workerThreadCount = 0;
	
	protected GameTCPIOClientGroup[] tcpIOClientGroup = null;
	
	public GameTCPServer(String subSystemName ) {
		this.subSystemName = subSystemName;
	}
	
	public GameTCPIOClientGroup[] getTCPIOClientGroup() {
		return tcpIOClientGroup;
	}
	
	protected int keepalivetry = MessageContext.KEEPALIVE_TRYCOUNT;
	
	@Override
	public void init( GameProcess gameProcess ) throws Exception {
		IniFileReader configReader = gameProcess.getConfigReader();
		String section = gameProcess.getSection();
		
		bindAddr = configReader.getParam(section, "bindAddr");
		bindPort = Integer.valueOf(configReader.getParam(section, "bindPort"));
		externalAddr = configReader.getParam(section, "externalAddr", bindAddr);
		
		bossThreadCount = Integer.valueOf(configReader.getParam(section, "bossThreadCount"));
		workerThreadCount = Integer.valueOf(configReader.getParam(section, "workerThreadCount")); 
	}
	
	@Override
	public void start( GameProcess gameProcess ) throws Exception {
		
		String name = gameProcess.getName();
		
		//	/////////////////////////////////////
		//	启动Netty
		//	/////////////////////////////////////
		
		NioEventLoopGroup bossGroup = new NioEventLoopGroup(bossThreadCount,
				new DefaultThreadFactoryAdapter("NettyBossThread"));
		NioEventLoopGroup workerGroup = new NioEventLoopGroup(workerThreadCount,
				new DefaultThreadFactoryAdapter("NettyWorkerThread"));
		
		int ioRatio = 100;

		// 分配处理IO的时间
		workerGroup.setIoRatio(ioRatio);
		
		ProcessGlobalData.gameLog.basic( "==================================");
		ProcessGlobalData.gameLog.basic( "== Start " + name + " Begin" );
		ProcessGlobalData.gameLog.basic( "==================================");
		ProcessGlobalData.gameLog.basic( "BindAddr " + bindAddr + " BindPort " + bindPort);
		ProcessGlobalData.gameLog.basic( "BossThreadCount " + bossGroup.executorCount() + " WorkerThreadCount " + workerGroup.executorCount());
		ProcessGlobalData.gameLog.basic( "IoRatio " + ioRatio );
		ProcessGlobalData.gameLog.basic( "Io.netty.eventLoop.maxPendingTasks " +
				SystemPropertyUtil.getInt("io.netty.eventexecutor.maxPendingTasks", Integer.MAX_VALUE) );
		
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			
			bootstrap.group(bossGroup, workerGroup);
			
			Class<? extends ServerChannel> channelClass =
					useEpoll( gameProcess ) ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
//			SelectorProvider.provider();
			
			bootstrap.channel( channelClass );
			ProcessGlobalData.gameLog.basic( "Channel " + channelClass.getSimpleName() );
			// ///////////////////////
			// 向子类要TCP选项参数
			// ///////////////////////

			bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
			
			//	应用层处理保活定时器逻辑关闭保活定时器
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, false);
			//	关闭延迟发送小包的算法
			bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
			//	堆外池分配
			bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
			//	高低水位线
			bootstrap.childOption(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, MessageContext.WRITE_BUFFER_WATER_MARK_HIGH);
			bootstrap.childOption(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, MessageContext.WRITE_BUFFER_WATER_MARK_LOW);
			
			//	采用 Netty 默认的方式处理半关闭, 下面这行的BOOLEAN将false改为true就是手动处理半关闭
			//	bootstrap.childOption(ChannelOption.ALLOW_HALF_CLOSURE, false);
			
			// ////////////////////////
			// 添加处理器
			// ////////////////////////
			
			ChannelInitializer<SocketChannel> initializer = createChannelInitializer();
			
			if( initializer == null ) {
				initializer = new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();

						ch.attr(ChannelAttributeContext.ATTR_TLV_HEAD_BUFFER).set(new byte[MessageContext.HEAD_LENGTH]);
						ch.attr(ChannelAttributeContext.ATTR_TLV_DECODER_BUFFER).set(new byte[MessageContext.DECODER_BUFFER_SIZE]);
						
						pipeline.addLast(new GameTCPTLVEncoder());
						pipeline.addLast(new GameTCPChannelInboundHandlerProxy(GameTCPServer.this));
						pipeline.addLast(new GameTCPTLVDecoder(GameTCPServer.this));
					}
				};
			}
			
			bootstrap.childHandler(initializer);

			ChannelFuture f = bootstrap.bind(bindAddr, bindPort).sync();
			
			f.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					
					
					if( ProcessGlobalData.openKeenalive ) {
						GameTCPServerKeepAliveThread gameTCPServerTickThread = 
								new GameTCPServerKeepAliveThread( GameTCPServer.this, keepalivetry );
						gameTCPServerTickThread.start();
					}
					
					bindCompleteCallBack(future);
				}
			});
			
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	
	@Override
	public void stop( GameProcess gameProcess ) {
		
	}
	
	protected abstract void bindCompleteCallBack( ChannelFuture future );
	
	@Override
	protected void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		GameTCPIOClient ioClient = ctx.channel().attr(ChannelAttributeContext.ATTR_IO_CLIENT).get();
		ioClient.setNetIOState(NetIOState.NETTY_UNREGISTERED);
		int index = ioClient.threadFactoryId - 1;
		if( index >= 0 && index < tcpIOClientGroup.length) {
			tcpIOClientGroup[index].add(ioClient);
		}
		ProcessGlobalData.gameLog.basic("channelUnregistered " + channelCTXToAddressString(ctx));
	}

	@Override
	protected void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		GameTCPIOClient ioClient = ctx.channel().attr(ChannelAttributeContext.ATTR_IO_CLIENT).get();
		ioClient.setNetIOState(NetIOState.NETTY_UNREGISTERED);
		ProcessGlobalData.gameLog.basicErr( "exceptionCaught " + channelCTXToAddressString(ctx), e );
	}

	@Override
	protected void channelActive(ChannelHandlerContext ctx) throws Exception {
		GameTCPIOClient ioClient = ctx.channel().attr(ChannelAttributeContext.ATTR_IO_CLIENT).get();
		ioClient.setNetIOState(NetIOState.NETTY_ACTIVE);
		ProcessGlobalData.gameLog.basic("channelActive " + channelCTXToAddressString(ctx));
	}

	@Override
	protected void channelInactive(ChannelHandlerContext ctx) throws Exception {
		GameTCPIOClient ioClient = ctx.channel().attr(ChannelAttributeContext.ATTR_IO_CLIENT).get();
		ioClient.setNetIOState(NetIOState.NETTY_INACTIVE);
		ProcessGlobalData.gameLog.basic("channelInactive " + channelCTXToAddressString(ctx));
	}

	@Override
	protected void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		GameTCPIOClient ioClient = new GameTCPIOClient(ctx);
		ioClient.setReceiveDataTime(System.currentTimeMillis());
		FastThreadIDLocalThread currentThread = (FastThreadIDLocalThread) Thread.currentThread();
		ioClient.setNetIOState(NetIOState.NETTY_REGISTER);
		ioClient.threadFactoryId = currentThread.getFactoryId();
		tcpIOClientGroup[ioClient.threadFactoryId - 1].add(ioClient);
		
		ctx.channel().attr(ChannelAttributeContext.ATTR_IO_CLIENT).set(ioClient);
		
		ProcessGlobalData.gameLog.basic("channelRegistered " + channelCTXToAddressString(ctx));
	}
	
	
	
	@Override
	protected void userEventTriggered(final ChannelHandlerContext ctx, Object evt ) throws Exception {
		ProcessGlobalData.gameLog.basic("userEventTriggered " + channelCTXToAddressString(ctx) + " " + evt.getClass().getSimpleName());
	}
	
	protected ChannelInitializer<SocketChannel> createChannelInitializer() {
		return null;
	}
	
	public abstract void sendKeeplive( GameTCPIOClient ioClient ) throws Exception;

	
	
}

