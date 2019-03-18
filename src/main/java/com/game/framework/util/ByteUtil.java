package com.game.framework.util;

import com.google.common.primitives.Ints;
import io.netty.buffer.ByteBuf;

import java.io.*;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
public final class ByteUtil {

	private ByteUtil() {

	}

	public static String toByteBuf(ByteBuf byteBuf) {

		int wIndex = byteBuf.writerIndex();
		int rIndex = byteBuf.readerIndex();

		int index = byteBuf.readerIndex();

		byte[] array = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(array);
		byteBuf.readerIndex(index);

		return "wIndex " + wIndex + " rIndex " + rIndex + " array " + (Arrays.toString(array)) + " length "
				+ array.length + " hashCode " + byteBuf.hashCode();
	}

	/**
	 * 从转换成可序列化的对象转换成ByteArray
	 * 
	 * @param obj
	 */
	public static byte[] toByteArray(Serializable obj) throws IOException {
		ByteArrayOutputStream rawOut = new ByteArrayOutputStream();

		ObjectOutputStream objOut = null;

		try {
			objOut = new ObjectOutputStream(rawOut);
			objOut.writeObject(obj);
			objOut.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			objOut.close();
		}

		return rawOut.toByteArray();
	}

	/**
	 * 将对象写到指定输出去, 输出成功后但不关闭流
	 * 
	 * @param rawOut
	 * @param obj
	 * @throws IOException
	 */
	public static void writeObject(OutputStream rawOut, Serializable obj) throws IOException {

		ObjectOutputStream objOut = null;

		try {
			objOut = new ObjectOutputStream(rawOut);
			objOut.writeObject(obj);
			objOut.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			objOut.close();
		}

	}

	/**
	 * 从输入中的ByteArray转换成可序列化的对象, 读取成功后但不关闭流
	 * 
	 * @param rawIn @return @throws
	 */
	public static Serializable readObject(InputStream rawIn) throws IOException {
		Serializable result = null;

		ObjectInputStream objIn = new ObjectInputStream(rawIn);

		try {
			result = (Serializable) objIn.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException("指定的类型无法找到", e);
		} catch (IOException e) {
			throw e;
		} finally {
			objIn.close();
		}

		return result;
	}

	/**
	 * 
	 * @Description 解码无压缩
	 * @author chen.su
	 * @date 2014-8-15 下午04:22:42
	 * @param array
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 *             Serializable
	 */
	public static Serializable deserializationnotzip(byte[] array) throws IOException, ClassNotFoundException {

		try {
			ByteArrayInputStream rawIn = new ByteArrayInputStream(array);
			ObjectInputStream objIn = new ObjectInputStream(rawIn);

			Serializable serializable = (Serializable) objIn.readObject();

			objIn.close();

			return serializable;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 
	 * @Description 编码无压缩
	 * @author chen.su
	 * @date 2014-8-15 下午04:22:54
	 * @param serializable
	 * @return
	 * @throws IOException
	 *             byte[]
	 */
	public static byte[] serializationnotzip(Serializable serializable) throws IOException {
		ByteArrayOutputStream rawOut = new ByteArrayOutputStream();

		ObjectOutputStream objOut = new ObjectOutputStream(rawOut);
		objOut.writeObject(serializable);
		objOut.flush();
		objOut.close();

		return rawOut.toByteArray();
	}

	/**
	 * 
	 * @Description 序列化
	 * @author chen.su
	 * @date 2014-3-15 下午01:42:42
	 * @param serializable
	 * @return byte[]
	 */
	public static byte[] serialization(Serializable serializable) throws IOException {
		ByteArrayOutputStream rawOut = new ByteArrayOutputStream();
		GZIPOutputStream gzipOut = new GZIPOutputStream(rawOut);
		ObjectOutputStream objOut = new ObjectOutputStream(gzipOut);
		objOut.writeObject(serializable);
		objOut.flush();
		objOut.close();

		return rawOut.toByteArray();
	}

	/**
	 * 
	 * @Description 反序列化
	 * @author chen.su
	 * @date 2014-3-15 下午01:42:50
	 * @param array
	 * @return Serializable
	 * @throws ClassNotFoundException
	 */
	public static Serializable deserialization(byte[] array) throws IOException, ClassNotFoundException {

		try {
			ByteArrayInputStream rawIn = new ByteArrayInputStream(array);
			GZIPInputStream gzipIn = new GZIPInputStream(rawIn);
			ObjectInputStream objIn = new ObjectInputStream(gzipIn);

			Serializable serializable = (Serializable) objIn.readObject();

			objIn.close();

			return serializable;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 
	 * @Description
	 * @author chen.su
	 * @date 2015-11-9 下午05:43:09
	 * @param str
	 * @return
	 * @throws Exception
	 *             byte[]
	 */
	public static byte[] writeStringToGZIPArray(String str, String encoding) throws Exception {
		ByteArrayOutputStream rawOut = new ByteArrayOutputStream();
		GZIPOutputStream zipOut = new GZIPOutputStream(rawOut);

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(zipOut, encoding));

		writer.write(str);
		writer.flush();
		writer.close();

		return rawOut.toByteArray();
	}

	public static String readStringFromGZIPInputStream(InputStream rawIn, String encoding) throws IOException {
		GZIPInputStream gzipIn = new GZIPInputStream(rawIn);
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(1024);

		byte[] buf = new byte[1024];
		int size = 0;

		while ((size = gzipIn.read(buf)) > 0) {
			byteArrayOut.write(buf, 0, size);
		}

		buf = byteArrayOut.toByteArray();

		return new String(buf, encoding);
	}

	public static byte[] readGZIPInputStream(InputStream rawIn) throws IOException {
		GZIPInputStream gzipIn = new GZIPInputStream(rawIn);
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(1024);

		byte[] buf = new byte[1024];
		int size = 0;

		while ((size = gzipIn.read(buf)) > 0) {
			byteArrayOut.write(buf, 0, size);
		}

		return byteArrayOut.toByteArray();
	}

	public static byte[] getRandomByteArray(int length) {
		byte[] array = new byte[length];

		for (int i = 0; i < length; i++) {
			array[i] = (byte) RandomUtil.random(255);
		}

		return array;
	}

	public static byte readByte(byte[] array, int offset) {
		return array[offset];
	}

	public static int readUnsignedByte(byte[] array, int offset) {
		return array[offset] & 0xFF;
	}

	public static int readUnsignedShort(byte[] array, int offset) {
		int ch1 = array[offset];
		int ch2 = array[offset + 1];

		return ((ch1 & 0xFF) << 8) + (ch2 & 0xFF);
	}

	public static final void writeLong(byte[] array, long v, int offset ) {
		array[offset] = (byte)(v >>> 56);
		array[offset+1] = (byte)(v >>> 48);
		array[offset+2] = (byte)(v >>> 40);
		array[offset+3] = (byte)(v >>> 32);
		array[offset+4] = (byte)(v >>> 24);
        array[offset+5] = (byte)(v >>> 16);
        array[offset+6] = (byte)(v >>>  8);
        array[offset+7] = (byte)(v >>>  0);
	}
	
    public static final void writeInt(byte[] array, int v, int offset) {
    	array[offset] = (byte)((v >>> 24) & 0xFF);
    	array[offset + 1] = (byte)((v >>> 16) & 0xFF);
    	array[offset + 2] = (byte)((v >>>  8) & 0xFF);
    	array[offset + 3] = (byte)((v >>>  0) & 0xFF);
    }
    
    public static final void write(byte[] array, byte v, int offset) {
    	array[offset] = v;
    }
    
	public static int readInt(byte[] array, int offset) {
		int ch1 = array[offset];
		int ch2 = array[offset + 1];
		int ch3 = array[offset + 2];
		int ch4 = array[offset + 3];

		return ((ch1 & 0xFF) << 24) + ((ch2 & 0xFF) << 16) + ((ch3 & 0xFF) << 8) + ((ch4 & 0xFF));
	}

	public static long readLong(byte[] array, int offset) {
		return ((long) array[offset] << 56) + ((long) (array[offset + 1] & 255) << 48)
				+ ((long) (array[offset + 2] & 255) << 40) + ((long) (array[offset + 3] & 255) << 32)
				+ ((long) (array[offset + 4] & 255) << 24) + ((array[offset + 5] & 255) << 16)
				+ ((array[offset + 6] & 255) << 8) + ((array[offset + 7] & 255) << 0);
	}

	public static String toString( byte[] data, int col ) {
		StringBuilder builder = new StringBuilder();
		
		for( int i = 0; i < data.length; i++ ) {
			builder.append(data[i]).append(" ");
			if( i % col == 0 && i != 0 ) {
				builder.append("\n");	
			}
		}
		
		return builder.toString();
	}
	
	public static int readLittleEndianInt( RandomAccessFile rawIn ) throws IOException {
		byte b1 = rawIn.readByte();
		byte b2 = rawIn.readByte();
		byte b3 = rawIn.readByte();
		byte b4 = rawIn.readByte();
		return Ints.fromBytes(b4, b3, b2, b1);
	}
}
