package com.game.framework.network.handler;

import com.game.framework.network.MessageContext;
import com.google.protobuf.GeneratedMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Description: 输出编码类
 */
public class GameTCPTLVEncoder extends ChannelOutboundHandlerAdapter {

	private ByteBufOutputStreamAdapter out = new ByteBufOutputStreamAdapter();

	public void write(final ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
	
		GameTCPTLVMessage message = (GameTCPTLVMessage) msg;

		GeneratedMessage generatedMessage = message.msg;
		
		int code1 = message.code1;
		int code2 = message.code2;

		int arraylength = (generatedMessage == null ? 0 : generatedMessage.getSerializedSize()) + MessageContext.HEAD_LENGTH;
		
		long stubID = message.stubID;
		
		//	/////////////////////////
		//	只进行一次COPY
		//	/////////////////////////
		
		ByteBuf byteBuf = ctx.alloc().directBuffer(MessageContext.HEAD_LENGTH + arraylength);

		byteBuf.writeByte(code1);
		byteBuf.writeShort(code2);
		byteBuf.writeInt(arraylength);
		byteBuf.writeInt(MessageContext.MAGIC_NUM);
		byteBuf.writeLong(MessageContext.buildCheckNum( code1, code2, arraylength, stubID ));
		byteBuf.writeLong(stubID);
		
		out.setByteBuf(byteBuf);
//		byte[] array = generatedMessage.toByteArray();
//		System.out.println("Arrays code1 " + code1 + " code2 " + code2 + "  " + Arrays.toString(array));
		generatedMessage.writeTo(out);
		
		out.clear();		
//		try {
//			System.out.println(ByteUtil.toByteBuf(byteBuf));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		ctx.writeAndFlush(byteBuf, promise);
	}

}

class ByteBufOutputStreamAdapter extends OutputStream {

	private ByteBuf byteBuf = null;

	public ByteBufOutputStreamAdapter() {

	}

	public ByteBuf getByteBuf() {
		return byteBuf;
	}

	public void setByteBuf(ByteBuf byteBuf) {
		this.byteBuf = byteBuf;
	}

	public void clear() {
		byteBuf = null;
	}

	@Override
	public void write(int i) throws IOException {
		byteBuf.writeByte(i);
	}

	@Override
	public void write(byte[] data, int off, int len) throws IOException {
		byteBuf.writeBytes(data, off, len);
	}
}