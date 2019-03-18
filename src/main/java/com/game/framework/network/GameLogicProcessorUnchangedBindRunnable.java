package com.game.framework.network;

import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.threadpool.UnchangedBindRunnable;
import com.game.framework.process.SubSystem;

public class GameLogicProcessorUnchangedBindRunnable extends UnchangedBindRunnable {
    //
    private GameLogicProcessor processor = null;
    //
    private GameTCPIOClient gameTCPIOClient = null;
    //
    private Object message = null;
    //
    private SubSystem gameProcess = null;

    public GameLogicProcessorUnchangedBindRunnable(SubSystem gameProcess, GameTCPIOClient gameTCPIOClient,
                                                   GameLogicProcessor processor, Object message ) {
        super((int)(gameTCPIOClient.getStubID() & 0xFFFFFFFF));

        this.gameTCPIOClient = gameTCPIOClient;
        this.processor = processor;
        this.message = message;
        this.gameProcess = gameProcess;
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
            processor.handler(gameProcess, gameTCPIOClient, message);
        } catch (Exception e) {
            if( gameTCPIOClient == null ) {
                ProcessGlobalData.gameLog.basicErr("GameLogicProcessorUBR Error IOClient == null", e);
            } else if( gameTCPIOClient.gamePlayer == null ) {
                ProcessGlobalData.gameLog.basicErr("GameLogicProcessorUBR Error GamePlayer == null Channel = " + gameTCPIOClient.channelContext, e);
            } else {
                ProcessGlobalData.gameLog.basicErr("GameLogicProcessorUBR Error GamePlayer " + gameTCPIOClient.gamePlayer.get32PlayerID() + " Channel = " + gameTCPIOClient.channelContext, e);
            }
        }
    }

}
