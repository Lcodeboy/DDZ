package com.game.framework.network.handler;

import com.google.protobuf.GeneratedMessage;

public class GameTCPTLVMessage {

    //	Code1
    int code1 = 0;
    //	Code2
    int code2 = 0;
    //	消息数据
    GeneratedMessage msg = null;
    //
    long stubID = 0;

    /**
     *
     * @param pcode1
     * @param pcode2
     * @param pmsg
     */
    public GameTCPTLVMessage( int pcode1, int pcode2, long pstubID, GeneratedMessage pmsg ) {
        code1 = pcode1;
        code2 = pcode2;
        msg = pmsg;
        stubID = pstubID;
    }

}
