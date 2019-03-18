package com.game.framework.network;

import com.game.framework.ProcessGlobalData;
import com.game.framework.network.GameTCPIOClient.NetIOState;
import com.game.framework.network.context.ChannelAttributeContext;
import com.game.framework.network.handler.GameTCPTLVDecoder;
import com.game.framework.network.handler.GameTCPTLVEncoder;
import com.game.framework.process.GameProcess;
import com.game.framework.util.IniFileReader;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.SystemPropertyUtil;

public abstract class GameTCPClient extends GameTCP {

	/** 绑定远程端口 */
	protected int bindRemotePort = 0;
	/** 绑定远程地址 */
	protected String bindRemoteAddr = null;

	protected int nettyThreadCount = 0;
	
	protected GameTCPIOClient ioClient = null;
	
	public GameTCPClient( String subSystemName ) {
		this.subSystemName = subSystemName;
	}

	@Override
	public void init(GameProcess gameProcess) throws Exception {
		IniFileReader configReader = gameProcess.getConfigReader();
		String section = gameProcess.getSection();
		
		bindAddr = configReader.getParam(section, subSystemName + ".bindAddr");
		bindPort = -1;
		externalAddr = configReader.getParam(section, "externalAddr", bindAddr);
		
		bindRemoteAddr = configReader.getParam(section, subSystemName + ".bindRemoteAddr");
		bindRemotePort = Integer.valueOf(configReader.getParam(section, subSystemName + ".bindRemotePort"));

		nettyThreadCount = Integer.valueOf(configReader.getParam(section, subSystemName + ".nettyThreadCount"));
	}

	@Override
	public void start(GameProcess gameProcess) throws Exception {
		
		Thread thread = new Thread() {
			public void run() {
				try {
					NioEventLoopGroup group = new NioEventLoopGroup(nettyThreadCount, new DefaultThreadFactory("NettyWorkThread"));

					int ioRatio = 100;

					// 分配处理IO的时间
					group.setIoRatio(ioRatio);

					String name = gameProcess.getName();
					ProcessGlobalData.gameLog.basic("Running " + name);
					ProcessGlobalData.gameLog.basic("IoRatio " + ioRatio);
					ProcessGlobalData.gameLog.basic("io.netty.eventLoop.maxPendingTasks "
							+ SystemPropertyUtil.getInt("io.netty.eventLoop.maxPendingTasks", Integer.MAX_VALUE));
					ProcessGlobalData.gameLog.basic("io.netty.recycler.maxCapacity "
							+ SystemPropertyUtil.getInt("io.netty.recycler.maxCapacity", Integer.MAX_VALUE));

					final Bootstrap bootstrap = new Bootstrap();

					// 禁用此nagle算法
					bootstrap.option(ChannelOption.TCP_NODELAY, true);
					// 分配器
					bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
					// 输出的流量控制参数
					// bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK, MessageContext.MARK);
					// 包活定时器
					bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

					bootstrap.group(group);
					bootstrap.channel(NioSocketChannel.class);

					bootstrap.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {

							ch.attr(ChannelAttributeContext.ATTR_TLV_HEAD_BUFFER)
									.set(new byte[MessageContext.HEAD_LENGTH]);
							ch.attr(ChannelAttributeContext.ATTR_TLV_DECODER_BUFFER)
									.set(new byte[MessageContext.DECODER_BUFFER_SIZE]);

							ChannelPipeline pipeline = ch.pipeline();
							
							pipeline.addFirst(new GameTCPTLVEncoder());
							pipeline.addLast(new GameTCPChannelInboundHandlerProxy(GameTCPClient.this));
							pipeline.addLast(new GameTCPTLVDecoder(GameTCPClient.this));
						}
					});

					ChannelFuture f = null;
					
					// 没有配置绑定地址
					f = bootstrap.connect(bindRemoteAddr, bindRemotePort).sync();

					f.addListener(new ChannelFutureListener() {
						public void operationComplete(ChannelFuture future) throws Exception {
							connectCompleteCallBack(future);
						}
					});

					f.channel().closeFuture().sync();
				} catch (Exception e) {
					ProcessGlobalData.gameLog.basicErr("BoostrapErr", e);
				}
			}
		};
		thread.setName(subSystemName + "-BootstrapThread");
		thread.start();
		
	}

	@Override
	public void stop(GameProcess gameProcess) throws Exception {}
	
	@Override
	protected void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		GameTCPIOClient ioClient = ctx.channel().attr(ChannelAttributeContext.ATTR_IO_CLIENT).get();
		ioClient.setNetIOState(NetIOState.NETTY_UNREGISTERED);
		ProcessGlobalData.gameLog.basic("channelUnregistered " );
	}

	@Override
	protected void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		GameTCPIOClient ioClient = ctx.channel().attr(ChannelAttributeContext.ATTR_IO_CLIENT).get();
		ioClient.setNetIOState(NetIOState.NETTY_UNREGISTERED);
		ProcessGlobalData.gameLog.basicErr( "exceptionCaught " , e );
	}

	@Override
	protected void channelActive(ChannelHandlerContext ctx) throws Exception {
		GameTCPIOClient ioClient = ctx.channel().attr(ChannelAttributeContext.ATTR_IO_CLIENT).get();
		ioClient.setNetIOState(NetIOState.NETTY_ACTIVE);
		ProcessGlobalData.gameLog.basic("channelActive " );
	}

	@Override
	protected void channelInactive(ChannelHandlerContext ctx) throws Exception {
		GameTCPIOClient ioClient = ctx.channel().attr(ChannelAttributeContext.ATTR_IO_CLIENT).get();
		ioClient.setNetIOState(NetIOState.NETTY_INACTIVE);
		ProcessGlobalData.gameLog.basic("channelInactive ");
	}

	@Override
	protected void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		GameTCPIOClient ioClient = new GameTCPIOClient(ctx);

		ioClient.setNetIOState(NetIOState.NETTY_REGISTER);
		
		ctx.channel().attr(ChannelAttributeContext.ATTR_IO_CLIENT).set(ioClient);
		
		ProcessGlobalData.gameLog.basic("channelRegistered " );
	}

	@Override
	protected void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		ProcessGlobalData.gameLog.basic("userEventTriggered " + channelCTXToAddressString(ctx) + " " + evt.getClass().getSimpleName());
	}
	
	protected void connectCompleteCallBack(ChannelFuture f) throws Exception {
		ioClient = f.channel().attr(ChannelAttributeContext.ATTR_IO_CLIENT).get();
	}

	public GameTCPIOClient getIoClient() {
		return ioClient;
	}
}
