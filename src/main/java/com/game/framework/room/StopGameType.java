package com.game.framework.room;

public enum  StopGameType {
    //	加入超时
    JOIN_TIMEOUT(1),
    //	正常
    NORMAL(2),
    //	异常
    EXCEPTION(3),
    //	认输
    LOSER(4),
    //	和局
    PEACE(5),
    //	准备超时
    READY_TIMEOUT(6),
    //	操作超时
    OPERATION_TIMEOUT(7),
    //
    QUIT(8);

    private StopGameType( int value ) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }
}
