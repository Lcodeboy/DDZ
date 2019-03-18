package com.game.framework.room;

import com.game.framework.network.GamePlayer;
import com.google.protobuf.GeneratedMessage;

public interface GameRoom {
    //	返回游戏的唯一ID
    public String getGameUUID();
    //	返回启动时间
    public long getStartTime();
    //	返回停止时间
    public long getStopTime();
    //	返回创建时间
    public long getCreateTime();
    //	返回停止-启动的时间
    public int getGameTime();

    //	//////////////////////////
    //	TimeOut
    //	//////////////////////////

    //	等待所有人全部加入的时间
    public int getWaitAllJoinTimeOut();
    //	等待所有人准备的超时时间
    public int getWaitAllReadyTimeOut();
    //	游戏的持续时间
    public int getGameDurationTime();
    //	用户操作的超时时间
    public int getPlayerOperationTimeOut();

    //	///////////////////////////
    //	Notify
    //	///////////////////////////

    //	用户操作超时时的通知
    public void timeOutNotifyPlayer(long playerId);
    //	游戏到达时间的通知
    public void timeOutNotifyGame();
    //	全部进入房间的超时通知
    public void timeOutNotifyAllJoin();
    //	全部准备的超时通知
    public void timeOutNotifyAllReady();
    //	用户认输的通知
    public void loseNotifyPlayer(long playerId);
    //	用户退出的通知
    public void quitNotifyPlayer(long playerId);
    //	求和通知
    public void peaceNotify();

    //	////////////////////////////
    //	停止游戏
    //	////////////////////////////

    //	广播数据
    public void broadcast(int pcode1, int pcode2, GeneratedMessage pmsg);

    public void sendOther(int pcode1, int pcode2, long playerId, GeneratedMessage pmsg);

    //	返回等待谁
    public long getWaitPlayerId();
    //
    public void setWaitPlayerId(long playerId);
    //	返回等待用户操作的开始时间
    public long getWaitPlayerStartTime();

    public boolean isGameOver();

    public void stopGame(StopGameType stopGameEnum);

    public StopGameType getStopGameType();

    //	所有人是否全部加入房间
    public boolean isJoinAll();
    //	通知房间, 所有人都已经加入房间
    public void setJoinAll();

    //
    public boolean isReadyAll();
    //
    public void setReadyAll();

    /**
     *
     * @param tickTime
     */
    public void execute(long tickTime);

    /**
     * 退出
     * @param playerId
     */
    public void quitGame(long playerId);

    /**
     * 认输
     * @param playerId
     */
    public void loseGame(long playerId);

    /**
     * 求和
     * @param playerId
     */
    public void peace(long playerId);

    /**
     *
     * @param gamePlayer
     */
    public void reconnNotify(GamePlayer gamePlayer);

    public int getGameTypeValue();
}
