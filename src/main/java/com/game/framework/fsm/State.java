package com.game.framework.fsm;

import com.game.framework.exception.FSMException;

public interface State<T> {
    /**
     * 状态 - 进入逻辑
     */
    public void enter(long time, T entity) throws FSMException;

    /**
     * 状态 - 具体逻辑
     */
    public void execute(long time, T entity) throws FSMException;

    /**
     * 状态 - 退出逻辑
     */
    public void exit(long time, T entity) throws FSMException;
}
