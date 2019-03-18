package com.game.framework.mmo;

import com.game.framework.exception.FSMException;

public interface GameSceneStateHandler {

    public int getLogicType();

    public void enter(long time, GameScene entity) throws FSMException;

    public void execute(long time, GameScene entity) throws FSMException;

    public void exit(long time, GameScene entity) throws FSMException;
}
