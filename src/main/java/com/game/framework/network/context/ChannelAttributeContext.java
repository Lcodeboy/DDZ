package com.game.framework.network.context;

import com.game.framework.network.GameTCPIOClient;
import io.netty.util.AttributeKey;

/**
 * @Description: 通道属性上下文
 */
public final class ChannelAttributeContext {

	private ChannelAttributeContext() {
		
	}
	
	/** 一个通道在解码后当前时间只有一个Head数据, 因此创建一个重用的内存空间避免重复NEW 			*/
	public static final AttributeKey<byte[]> ATTR_TLV_HEAD_BUFFER = AttributeKey.valueOf("tlv_head_buffer");
	/** 缓冲区																	*/
	public static final AttributeKey<byte[]> ATTR_TLV_DECODER_BUFFER = AttributeKey.valueOf("TLVDB");
	/** IO的口 */
	public static final AttributeKey<GameTCPIOClient> ATTR_IO_CLIENT = AttributeKey.valueOf("io_client");
	
}
