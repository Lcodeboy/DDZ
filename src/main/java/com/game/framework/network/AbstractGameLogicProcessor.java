package com.game.framework.network;

import com.game.framework.ProcessGlobalData;
import com.game.framework.annotation.SocketMessage;
import com.game.framework.mmo.scene.GameScene;
import com.game.framework.process.SubSystem;
import com.google.protobuf.AbstractMessage;
import org.springframework.beans.factory.InitializingBean;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.SocketAddress;

/**
 * 
 * @Description: 业务逻辑处理器的骨架实现. 其实现必须提供两个消息号. 通过反射实现了decoder方法.
 */
public abstract class AbstractGameLogicProcessor implements GameLogicProcessor, InitializingBean {

	public Object decoder() throws Exception {
		SocketMessage socketMessage = getClass().getAnnotation(SocketMessage.class);
		// Proto Buf 类
		Class<?> messageClass = socketMessage.value();

		Method method = messageClass.getMethod("parseFrom", byte[].class);

		Object obj = method.invoke(messageClass, MessageContext.EMPTY_ARRAY);

		return obj;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object decoder(byte[] array, int off, int length) throws Exception {
		SocketMessage socketMessage = getClass().getAnnotation(SocketMessage.class);
		// Proto Buf 类
		Class<?> messageClass = socketMessage.value();

		Method newBuilderMethod = messageClass.getMethod("newBuilder");

		AbstractMessage.Builder builder = (AbstractMessage.Builder) newBuilderMethod.invoke(messageClass);

		Object result = builder.mergeFrom(array, off, length);

		Class<?> resultClass = result.getClass();

		Method buildParsedMethod = resultClass.getDeclaredMethod("buildParsed");
		buildParsedMethod.setAccessible(true);
		return buildParsedMethod.invoke(result);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object decoder(InputStream in) throws Exception {
		SocketMessage socketMessage = getClass().getAnnotation(SocketMessage.class);
		// Proto Buf 类
		Class<?> messageClass = socketMessage.value();

		Method newBuilderMethod = messageClass.getMethod("newBuilder");

		AbstractMessage.Builder builder = (AbstractMessage.Builder) newBuilderMethod.invoke(messageClass);

		Object result = builder.mergeFrom(in);

		Class<?> resultClass = result.getClass();

		Method buildParsedMethod = resultClass.getDeclaredMethod("buildParsed");
		buildParsedMethod.setAccessible(true);
		return buildParsedMethod.invoke(result);
	}

	@SuppressWarnings("rawtypes")
	public void afterPropertiesSet() throws Exception {
		// //////////////////////////////////
		// 绑定消息的处理器和对应的线程
		// //////////////////////////////////

		SocketMessage socketMessage = getClass().getAnnotation(SocketMessage.class);
		
		if (socketMessage == null) {
			ProcessGlobalData.gameLog.basicErr("Loading Processor " + getClass().getName() + " Not SocketMessage");
			return;
		}
		
		Class<?> messageClass = socketMessage.value();

		Class[] array = messageClass.getDeclaredClasses();
		Class enumClazz = null;
		int CODE1 = 0;
		int CODE2 = 0;
		boolean constains = false;
		
		for (int i = 0, size = array.length; i < size; i++) {
			if ((enumClazz = array[i]).isEnum() && enumClazz.getName().endsWith("$ID")) {

				CODE1 = (Integer) enumClazz.getField(MessageContext.CODE1_VALUE).get(enumClazz);
				CODE2 = (Integer) enumClazz.getField(MessageContext.CODE2_VALUE).get(enumClazz);
				
				ProcessGlobalData.gameLog.basic("Loading Processor CODE1 " + CODE1 + " CODE2 " + CODE2 + " = " + getClass().getName());
				MessageContext.LOGIC_SUBROUTINE_GROUP[CODE1][CODE2] = this;
				constains = true;
				break;
			}
		}
		
		if( !constains ) {
			ProcessGlobalData.gameLog.basicErr("Loading Processor " + getClass().getName() + " SocketMessage Not ID");
		}
		
	}
	
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, Object message ) throws Exception {
		throw new UnsupportedOperationException(getClass().getSimpleName() + "");	
	}
	
	public void udpHandler( SubSystem subSystem, SocketAddress remoteAddress, Object message, long stubId ) throws Exception {
		throw new UnsupportedOperationException(getClass().getSimpleName() + "");
	}
	
	public void handler(SubSystem subSystem, GameTCPIOClient ioClient, GameScene gameScene, long nowTime, Object message ) throws Exception {
		throw new UnsupportedOperationException(getClass().getSimpleName() + "");		
	}
}
