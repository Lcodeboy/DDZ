package com.game.framework.network;

/**
 * 一个标记接口表示游戏中的一个真实玩家
 *
 */
public interface GamePlayer {
    /**
     * 返回加入到此进程的时间
     * @return
     */
    public long getJoinProcessTime();

    /**
     *
     * @return
     */
    public long getQuitProcessTime();

    /**
     *
     * @return
     */
    public long getPlayerID();

    public int get32PlayerID();
    /**
     *
     * @param playerID
     */
    public void setPlayerID(long playerID);


    public GameTCPIOClient getTCPIOClient();

    public void setGameTCPIOClient(GameTCPIOClient gameTCPIOClient);
}
