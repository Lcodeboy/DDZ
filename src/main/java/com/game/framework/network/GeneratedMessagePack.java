package com.game.framework.network;

import com.google.protobuf.GeneratedMessage;

public class GeneratedMessagePack {

	private int code1 = 0;
	
	private int code2 = 0;
	
	private GeneratedMessage msg = null;
	
	/**
	 * 
	 * @param code1
	 * @param code2
	 * @param msg
	 */
	public GeneratedMessagePack( int code1, int code2, GeneratedMessage msg ) {
		this.code1 = code1;
		this.code2 = code2;
		this.msg = msg;
	}

	public int getCode1() {
		return code1;
	}

	public void setCode1(int code1) {
		this.code1 = code1;
	}

	public int getCode2() {
		return code2;
	}

	public void setCode2(int code2) {
		this.code2 = code2;
	}

	public GeneratedMessage getMsg() {
		return msg;
	}

	public void setMsg(GeneratedMessage msg) {
		this.msg = msg;
	}
	
	
}
