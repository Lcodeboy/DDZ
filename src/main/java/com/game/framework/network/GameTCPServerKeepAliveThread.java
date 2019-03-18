package com.game.framework.network;

import com.game.framework.ProcessGlobalData;

import java.util.ArrayList;

public class GameTCPServerKeepAliveThread extends Thread {
	private GameTCPServer gameTCPServer = null;
	
	private GameTCPIOClientGroup[] gameTCPIOClientGroup = null;
	
	private int tryCount = 0;
	
//	public GameTCPServerKeepAliveThread( GameTCPServer gameTCPServer ) {
//		this( gameTCPServer,	MessageContext.KEEPALIVE_TRYCOUNT );
//	}
	
	public GameTCPServerKeepAliveThread(GameTCPServer gameTCPServer, int tryCount ) {
		this.gameTCPServer = gameTCPServer;
		gameTCPIOClientGroup = gameTCPServer.getTCPIOClientGroup();
		setName("KeepAliveThread");
		this.tryCount = tryCount;
	}
	
	public void keepalive() {
		
		ArrayList<GameTCPIOClient> keepaliveList = null;
		GameTCPIOClient ioClient = null;
		
		for( int i = 0, size = gameTCPIOClientGroup.length; i < size; i++ ) {
			
			keepaliveList = gameTCPIOClientGroup[i].getAllNeedKeepalive();
			
			for( int j = 0, jsize = keepaliveList.size(); j < jsize; j++ ) {
			
				ioClient = keepaliveList.get(j);
				
				if( ioClient.tryCount.get() >= tryCount ) {
					//	保活失败关闭链接
					ioClient.channelContext.close();
				} else {
					try {
						ioClient.tryCount.incrementAndGet();
						gameTCPServer.sendKeeplive(keepaliveList.get(j));
					} catch (Exception e) {
						ProcessGlobalData.gameLog.basicErr("Send KeepAlive Error"  + ioClient);
					}
				}
			}
		}
	}
	
	public void run() {
	
		while( true ) {
			try {
				Thread.sleep(MessageContext.KEEPALIVE_TICKTIME);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			keepalive();
		}
	}
	
}
