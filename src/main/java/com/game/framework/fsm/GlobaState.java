package com.game.framework.fsm;

import com.game.framework.exception.FSMException;

/**
 * 全局状态
 *  首先处理自身上的Buff列表的Buff看是否到时, 如果到时则触发, 如过触发成功则做相应的业务逻辑.
 * 	然后处理自身使用的技能, 如果技能生效时间到达则计算收到技能的角色 发送通知.
 * @param <T>
 */
public class GlobaState<T> extends AbstractState<T> {
    @SuppressWarnings("rawtypes")
    public static GlobaState INSTATE = new GlobaState();
    @Override
    public void enter(long time, T entity) throws FSMException {

    }

    @Override
    public void execute(long time, T entity) throws FSMException {

    }

    @Override
    public void exit(long time, T entity) throws FSMException {

    }
}
