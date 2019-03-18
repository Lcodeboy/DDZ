package com.game.framework.network;

/**
 * TLV L包含消息头的数据和带外数据的长度
 * 
 * @Description: 消息类型的常量, 数据类型为 uint8.
 */
public final class MessageContext {

	private MessageContext() {

	}

	public static final int[] NOT_FORWARD = new int[0];

	public static final int INI_GATE_STUBID = -1;

	public static final int INI_DATACENTER_STUBID = -2;

	public static final int ETHERNET_MAX_UDP = 1500;

	public static long buildCheckNum(int code1, int code2, int length, long stubID) {
		return code1 + code2 + length + MAGIC_NUM;
	}

	public static final int MAGIC_NUM = 20180625;

	public static final byte[] EMPTY_ARRAY = new byte[0];
	/** Buff基准 */
	public static final int TLV_BODY_BUFFER_BASE = 1024;
	/** 已KB为单位的缓存大小的方根 */
	public static final int TLV_BODY_BUFFER_ROOT = 2;
	/** 缓存的方次 */
	public static final int TLV_BODY_BUFFER_POWER_LAW = 7;
	/** 缓存最大大小 */
	public static final int TLV_BODY_BUFFER_MAX_LENGTH = ((int) Math.pow(TLV_BODY_BUFFER_ROOT,
			TLV_BODY_BUFFER_POWER_LAW)) * TLV_BODY_BUFFER_BASE;

	public static final int TLV_BODY_BUFFER_INIT_COUNT = 128;

	// 允许客户端发送的最大长度 (2 ^ 7) * 1024
	public static final int MAXPACKAGELENGTH = TLV_BODY_BUFFER_MAX_LENGTH;
	// 长度偏移量
	public static final int LENGTH_FIELD_OFFSET = 3;
	// 长度的长度
	public static final int LENGTH_FIELD_LENGTH = 4;
	//
	public static final int CHECKNUM_LENGTH = 8;
	//
	public static final int MAGICNUM_LENGTH = 4;
	//
	public static final int STUBID_LENGTH = 8;
	//
	public static final int HEAD_LENGTH = LENGTH_FIELD_OFFSET + LENGTH_FIELD_LENGTH + CHECKNUM_LENGTH + MAGICNUM_LENGTH
			+ STUBID_LENGTH;

	public static final int LENGTH_ADJUSTMENT = -(LENGTH_FIELD_OFFSET + LENGTH_FIELD_LENGTH);

	public static final int INITIAL_BYTES_TO_STRIP = 0;

	public static final int WRITE_BUFFER_WATER_MARK_LOW = 16 * TLV_BODY_BUFFER_BASE;

	public static final int WRITE_BUFFER_WATER_MARK_HIGH = 64 * TLV_BODY_BUFFER_BASE;

	public static final int DECODER_BUFFER_SIZE = 1024 * 4;

	// public static final WriteBufferWaterMark MARK = new WriteBufferWaterMark(
	// WRITE_BUFFER_WATER_MARK_LOW, WRITE_BUFFER_WATER_MARK_HIGH );

	public static final String CODE1_VALUE = "CODE1_VALUE";

	public static final String CODE2_VALUE = "CODE2_VALUE";

	public static final int MAX_CODE1 = 100;
	public static final int MAX_CODE2 = 2000;

	public static final GameLogicProcessor[][] LOGIC_SUBROUTINE_GROUP = new GameLogicProcessor[MAX_CODE1][MAX_CODE2];

	public static final int KEEPALIVE_FREETIME = 15000;

	public static final int KEEPALIVE_WAITTIME = 5000;

	public static final int KEEPALIVE_TRYCOUNT = 13;

	public static final int KEEPALIVE_TICKTIME = 500;

}
