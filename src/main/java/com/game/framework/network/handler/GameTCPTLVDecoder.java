package com.game.framework.network.handler;

import com.game.framework.ProcessGlobalData;
import com.game.framework.network.GameLogicProcessor;
import com.game.framework.network.GameTCP;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.network.MessageContext;
import com.game.framework.network.context.ChannelAttributeContext;
import com.game.framework.util.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.*;

import static com.game.framework.network.MessageContext.*;

/**
 * 
 * 
 * @Description: TLV的解码方式, 解码后调用GameTCP的处理消息的函数进行处理
 */
public class GameTCPTLVDecoder extends LengthFieldBasedFrameDecoder {

	private GameTCP gameTCP = null;
	
	public GameTCPTLVDecoder(GameTCP gameTCP) {
		super(MAXPACKAGELENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP);

		this.gameTCP = gameTCP;
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		
		ByteBuf byteBuf = null;

		try {
			if ((byteBuf = (ByteBuf) super.decode(ctx, in)) == null) {
				return null;
			}
		} catch (Throwable e) {
			ProcessGlobalData.gameLog.basicErr("GameTCPTLVDecoder Err-1", e);

			if (byteBuf != null) {
				byteBuf.release();
			}
			return null;
		}

		try {
			Channel remoteChannel = ctx.channel();

			// 头部数据缓冲区
			byte[] headBuffer = remoteChannel.attr(ChannelAttributeContext.ATTR_TLV_HEAD_BUFFER).get();

			// 读头部数据
			byteBuf.readBytes(headBuffer);
			
			
			// /////////////////////
			// 1 2 4 4 8 8
			// /////////////////////
			// 0
			// 1
			// 3
			// 7
			// 11
			// 19
			// /////////////////////
			
			//	///////////////////////////////////////////////////////////////////////////////////////////
			//	+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
			//  +Byte Length+ 1	  		+ 2			+ 4	     	+ 4			+ 8		 	+ 8			+
			//	+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
			//	+Field Name + CODE1 	+ CODE2 	+ Length 	+ MagicNum  + CheckNum  + StubID	+
			//	+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
			//	
			//	CODE1 		通信方向类型
			//	CODE2 		业务逻辑类型
			//	Length 		长度
			//	MagicNum	魔法数
			//	CheckNum	验证结果
			//	StubID		带外ID
			//	///////////////////////////////////////////////////////////////////////////////////////////
			
			// 消息号
			int code1 = ByteUtil.readUnsignedByte(headBuffer, 0);
			// 消息号
			int code2 = ByteUtil.readUnsignedShort(headBuffer, 1);
			// 消息长度
			int length = ByteUtil.readInt(headBuffer, 3);
			// MagicNum
			int magicNum = ByteUtil.readInt(headBuffer, 7);
			// CheckNum
			long checkNum = ByteUtil.readLong(headBuffer, 11);
			// StubID
			long stubID = ByteUtil.readLong(headBuffer, 19);

			if (magicNum != MessageContext.MAGIC_NUM) {
				// MagicNUM 魔数验证不通过
				ProcessGlobalData.gameLog.basic("GameTCPTLVDecoder MagicNum Fail");
				return null;
			}

			if (checkNum != MessageContext.buildCheckNum(code1, code2, length, stubID)) {
				// CheckNUM 验证不通过
				ProcessGlobalData.gameLog.basic("GameTCPTLVDecoder CheckNum Fail");
				return null;
			}

			GameLogicProcessor logicProcessor = MessageContext.LOGIC_SUBROUTINE_GROUP[code1][code2];
			
			if (logicProcessor == null) {
				// 消息号不被支持, 直接忽略并释放直接内存.
				ProcessGlobalData.gameLog.basic("GameTCPTLVDecoder Not Found Processor CODE1 " + code1 + " CODE2 " + code2);
				return null;
			}
			
			Object value = null;

			int contentLength = (int) (length - MessageContext.HEAD_LENGTH);

			final byte[] array;

			Channel channel = ctx.channel();

			if (contentLength == 0) {
				try {
					value = logicProcessor.decoder();
				} catch (Exception e) {
					ProcessGlobalData.gameLog.basicErr("GameTCPTLVDecoder Err-2", e);
					return null;
				}
			} else {
				
				//	参见 Netty ProtobufDecoder 源代码 4.0.56.Final
				//	在 ProtobufDecoder 中会重复NEW, 所以没有采用ProtobufDecoder
				//	为每个通道分配一个定长的缓冲区, 重复利用. 超大包则重复NEW因为超大包毕竟是少数.
				if( byteBuf.hasArray() ) {
					//	堆内缓冲区使用这种方案
					array = byteBuf.array();
					value = logicProcessor.decoder(array, byteBuf.arrayOffset() + contentLength, contentLength);
				} else {
					//	堆外缓冲区使用这种方案
					if (contentLength > MessageContext.DECODER_BUFFER_SIZE) {
						array = new byte[contentLength];
					} else {
						array = channel.attr(ChannelAttributeContext.ATTR_TLV_DECODER_BUFFER).get();
					}

					byteBuf.getBytes(byteBuf.readerIndex(), array, 0, contentLength);
					value = logicProcessor.decoder(array, 0, contentLength);
				}
				
				
			}
			
			GameTCPIOClient client = channel.attr(ChannelAttributeContext.ATTR_IO_CLIENT).get();
			
			client.setReceiveDataTime(System.currentTimeMillis());
			client.setStubID(stubID);
			
			gameTCP.executeMSG(client, code1, code2, value, logicProcessor);

		} catch (Throwable e) {
			ProcessGlobalData.gameLog.basicErr("GameTCPTLVDecoder Err-3", e);
		} finally {
			byteBuf.release();
		}

		return null;
	}

}

/**
 * 
 * 从 Netty 中复制 io/netty/buffer/ByteBufInputStream
 * 
 * 修改 private final ByteBuf buffer 为非fin  al. 提供其读写接口。
 * 
 * 设计原因 在Decoder中 减少拷贝次数&减少New ByteBufOutputStream 的次数. 线程为单线程无需同步可以重用内存.
 * 
 */
class ByteBufInputStreamAdapter extends InputStream implements DataInput {

	private ByteBuf buffer = null;
	private int startIndex = 0;
	private int endIndex = 0;

	public void clearCache() {
		// buffer 的释放交给外层.
		buffer = null;
		startIndex = 0;
		endIndex = 0;
	}

	public void setByteBuf(ByteBuf buffer) {
		setByteBuf(buffer, buffer.readableBytes());
	}

	public void setByteBuf(ByteBuf buffer, int length) {
		if (buffer == null) {
			throw new NullPointerException("buffer");
		}
		if (length < 0) {
			throw new IllegalArgumentException("length: " + length);
		}
		if (length > buffer.readableBytes()) {
			throw new IndexOutOfBoundsException(
					"Too many bytes to be read - Needs " + length + ", maximum is " + buffer.readableBytes());
		}

		this.buffer = buffer;
		startIndex = buffer.readerIndex();
		endIndex = startIndex + length;
		buffer.markReaderIndex();
	}

	/**
	 * Returns the number of read bytes by this stream so far.
	 */
	public int readBytes() {
		return buffer.readerIndex() - startIndex;
	}

	@Override
	public int available() throws IOException {
		return endIndex - buffer.readerIndex();
	}

	@Override
	public void mark(int readlimit) {
		buffer.markReaderIndex();
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public int read() throws IOException {
		if (!buffer.isReadable()) {
			return -1;
		}
		return buffer.readByte() & 0xff;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int available = available();
		if (available == 0) {
			return -1;
		}

		len = Math.min(available, len);
		buffer.readBytes(b, off, len);
		return len;
	}

	@Override
	public void reset() throws IOException {
		buffer.resetReaderIndex();
	}

	@Override
	public long skip(long n) throws IOException {
		if (n > Integer.MAX_VALUE) {
			return skipBytes(Integer.MAX_VALUE);
		} else {
			return skipBytes((int) n);
		}
	}

	@Override
	public boolean readBoolean() throws IOException {
		checkAvailable(1);
		return read() != 0;
	}

	@Override
	public byte readByte() throws IOException {
		if (!buffer.isReadable()) {
			throw new EOFException();
		}
		return buffer.readByte();
	}

	@Override
	public char readChar() throws IOException {
		return (char) readShort();
	}

	@Override
	public double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}

	@Override
	public float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	@Override
	public void readFully(byte[] b) throws IOException {
		readFully(b, 0, b.length);
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		checkAvailable(len);
		buffer.readBytes(b, off, len);
	}

	@Override
	public int readInt() throws IOException {
		checkAvailable(4);
		return buffer.readInt();
	}

	private final StringBuilder lineBuf = new StringBuilder();

	@Override
	public String readLine() throws IOException {
		lineBuf.setLength(0);

		loop: while (true) {
			if (!buffer.isReadable()) {
				return lineBuf.length() > 0 ? lineBuf.toString() : null;
			}

			int c = buffer.readUnsignedByte();
			switch (c) {
			case '\n':
				break loop;

			case '\r':
				if (buffer.isReadable() && (char) buffer.getUnsignedByte(buffer.readerIndex()) == '\n') {
					buffer.skipBytes(1);
				}
				break loop;

			default:
				lineBuf.append((char) c);
			}
		}

		return lineBuf.toString();
	}

	@Override
	public long readLong() throws IOException {
		checkAvailable(8);
		return buffer.readLong();
	}

	@Override
	public short readShort() throws IOException {
		checkAvailable(2);
		return buffer.readShort();
	}

	@Override
	public String readUTF() throws IOException {
		return DataInputStream.readUTF(this);
	}

	@Override
	public int readUnsignedByte() throws IOException {
		return readByte() & 0xff;
	}

	@Override
	public int readUnsignedShort() throws IOException {
		return readShort() & 0xffff;
	}

	@Override
	public int skipBytes(int n) throws IOException {
		int nBytes = Math.min(available(), n);
		buffer.skipBytes(nBytes);
		return nBytes;
	}

	private void checkAvailable(int fieldSize) throws IOException {
		if (fieldSize < 0) {
			throw new IndexOutOfBoundsException("fieldSize cannot be a negative number");
		}
		if (fieldSize > available()) {
			throw new EOFException("fieldSize is too long! Length is " + fieldSize + ", but maximum is " + available());
		}
	}
}
