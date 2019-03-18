package com.game.framework.mmo.scene;

import com.game.framework.exception.FSMException;
import com.game.framework.fsm.State;
import com.game.framework.mmo.GameScene;

public abstract class AbstractGameSceneState implements State<GameScene> {

    protected int stateid = 0;

    public AbstractGameSceneState() {
    }

    public int getStateid() {
        return stateid;
    }

    @Override
    public void enter(long time, GameScene entity) throws FSMException {

    }

    @Override
    public void execute(long time, GameScene entity) throws FSMException {

    }

    @Override
    public void exit(long time, GameScene entity) throws FSMException {

    }
}
