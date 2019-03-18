package com.game.framework.network;

import com.game.framework.mmo.scene.GameScene;
import com.game.framework.process.SubSystem;

import java.io.InputStream;
import java.net.SocketAddress;

/**
 * @Description: 游戏逻辑处理器
 */
public interface GameLogicProcessor {
	
	//	从 off 开始 length 长的范围内解码 array
	public Object decoder(byte[] array, int off, int length) throws Exception;
	//	Empty数据包
	public Object decoder() throws Exception;
	//	
	public Object decoder(InputStream in) throws Exception;
	
	/**
	 * TCP通道收到消息后的处理器
	 * 
	 * @param ioClient
	 * @param message
	 * @throws Exception
	 */
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, Object message) throws Exception;
	
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, GameScene gameScene, long nowTime, Object message) throws Exception;
	
	public void udpHandler(SubSystem subSystem, SocketAddress remoteAddress, Object message, long stubId) throws Exception;
	
}
