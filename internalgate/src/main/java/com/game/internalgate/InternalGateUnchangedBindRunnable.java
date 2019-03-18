package com.game.internalgate;

import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.threadpool.UnchangedBindRunnable;
import com.game.framework.network.GameLogicProcessor;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.process.SubSystem;

public class InternalGateUnchangedBindRunnable extends UnchangedBindRunnable {

	//
	private GameLogicProcessor processor = null;
	//
	private GameTCPIOClient gameTCPIOClient = null;
	//
	private Object message = null;
	//
	private SubSystem subSystem = null;

	public InternalGateUnchangedBindRunnable(SubSystem subSystem, GameTCPIOClient gameTCPIOClient,
			GameLogicProcessor processor, Object message) {
		super((int) (gameTCPIOClient.getStubID() & 0xFFFFFFFF));

		this.gameTCPIOClient = gameTCPIOClient;
		this.processor = processor;
		this.message = message;
		this.subSystem = subSystem;
	}

	public GameLogicProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(GameLogicProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void run() {
		try {
			processor.handler(subSystem, gameTCPIOClient, message);
		} catch (Exception e) {
			ProcessGlobalData.gameLog.basicErr("LogicProcessorUBR Error", e);
		}
	}

}
