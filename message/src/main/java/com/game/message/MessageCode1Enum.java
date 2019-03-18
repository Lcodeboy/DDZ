package com.game.message;

public enum MessageCode1Enum {
	
	//	////////////////////////////////////////////////////////////
	//	[0-10]		通用的协议格式类型
	//	[11-40]		服务器内部协议头
	//	[41-70]		客户端和服务器协议头
	//	////////////////////////////////////////////////////////////
	//	
	//	1		KeepLive		心跳包
	//	2		ReConn			重连	
	//	3		CheckTime		对时协议
	//	4		Test			测试
	//	5		Manager			服务器管理
	//	-------------------------------------------
	//	11 		RD			房间服发给数据中心
	//	12 		DR			数据中心发给房间服
	//	13 		RR			房间服发给房间服
	//	14 		RG			房间服发给游戏服
	//	15 		GR			游戏服发给房间服
	//	16		RIG			房间服发给内部网关服
	//	17		IGR			内部网关服发给房间服
	//	18		GAIG		游戏服发给内部网关服
	//	19		IGAG		内部网关服发给游戏服
	//	20		SIGNELIG	GM发送信号给网关服
	//	21		IGSIGNEL	网关服返回信号处理结果给GM
	//	22		WEBD			WEB和DataCenter
	//	23		DWEB			DataCenter和WEB
	//	24		WEBR  			WEB到房间服
	//	25		RWEB  			房间服到WEB
	//	26		WEBG  			WEB服到游戏服
	//	27		GWEB 			游戏服到WEB
	//	28		WEBIG 			WEB到网关服
	//	29		IGWEB 			网关服到WEB
	//	-------------------------------------------
	//	41 		CL			客户端发给登录服
	//	42 		LC			登录服发给客户端	
	//	43 		CR			客户端发给房间服
	//	44		RC			房间服发给客户端
	//	45 		CG			客户端发给游戏服
	//	46		GC			游戏服发给客户端
	//	
	//	/////////////////////////////////////////////////////////////
	
	//	通用
	KeepLive(1), ReConn(2), CheckTime(3), TEST(4), Manager(5),
	//	服务器内部
	RD(11), DR(12), RR(13), RG(14), GR(15), 
	RIG(16), IGR(17), GAIG(18), IGGA(19), SIGNELIG(20), IGSIGNEL(21), WEBD(22), DWEB(23),
	WEBR(24), RWEB(25), WEBG(26), GWEB(27), WEBIG(28), IGWEB(29),
	//	客户端对服务器
	CL(41), LC(42), CR(43), RC(44), CG(45), GC(46);
	
	
	private MessageCode1Enum( int value ) {
		this.value = value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
	
}
