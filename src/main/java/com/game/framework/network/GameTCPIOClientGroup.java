package com.game.framework.network;

import com.game.framework.network.GameTCPIOClient.NetIOState;

import java.util.ArrayList;

public class GameTCPIOClientGroup {
	//	
	private ArrayList<GameTCPIOClient> ioClientList = new ArrayList<GameTCPIOClient>();
	//	
	private ArrayList<GameTCPIOClient> ioClientTimeCountList = new ArrayList<GameTCPIOClient>();
	//	
	private Object lock = new Object();
	
	public void add( GameTCPIOClient tcpIOClient ) {
		synchronized ( lock ) {
			ioClientList.add( tcpIOClient );
		}
	}
	
	public void remove( GameTCPIOClient tcpIOClient ) {
		synchronized ( lock ) {
			ioClientList.remove( tcpIOClient );
		}
	}
	
	public ArrayList<GameTCPIOClient> getAllNeedKeepalive() {
		
		long now = System.currentTimeMillis();
		GameTCPIOClient ioClient = null;
		
		int tryCount = 0;
		
		synchronized (lock) {
			ioClientTimeCountList.clear();
			
			for( int i = 0; i < ioClientList.size(); i++ ) {
				ioClient = ioClientList.get(i);
				
				if( ioClient.netIOState == NetIOState.NETTY_ACTIVE
						|| ioClient.netIOState == NetIOState.NETTY_EXCEPTION ) {

					tryCount = ioClient.tryCount.get();
					
					if( tryCount > MessageContext.KEEPALIVE_TRYCOUNT ) {
						ioClientTimeCountList.add(ioClient);
					} else if( (now - ioClient.getReceiveDataTime()) >= 
							MessageContext.KEEPALIVE_WAITTIME * tryCount + MessageContext.KEEPALIVE_FREETIME ) {
						ioClientTimeCountList.add(ioClient);
					}	
				}
			}
			return ioClientTimeCountList;
		}

	}
	
}
