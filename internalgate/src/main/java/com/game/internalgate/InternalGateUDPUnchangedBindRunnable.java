package com.game.internalgate;

import java.net.SocketAddress;

import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.threadpool.UnchangedBindRunnable;
import com.game.framework.network.GameLogicProcessor;
import com.game.framework.process.SubSystem;

public class InternalGateUDPUnchangedBindRunnable extends UnchangedBindRunnable {
	//
	private GameLogicProcessor processor = null;
	//
	private Object message = null;
	//
	private SubSystem subSystem = null;
	//
	private long stubId = 0;
	//
	private SocketAddress remoteAddress = null;
	
	public InternalGateUDPUnchangedBindRunnable(SubSystem subSystem,GameLogicProcessor processor, Object message, long stubId, SocketAddress remoteAddress) {
		super((int) (stubId & 0xFFFFFFFF));
		
		this.stubId = stubId;
		this.processor = processor;
		this.message = message;
		this.subSystem = subSystem;
		this.remoteAddress = remoteAddress;
	}
	
	@Override
	public void run() {
		try {
			processor.udpHandler(subSystem, remoteAddress, message, stubId);
		} catch (Exception e) {
			ProcessGlobalData.gameLog.basicErr("UDPLogicProcessorUBR Error", e);
		}
	}

}
