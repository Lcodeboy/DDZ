package com.game.framework.process;

/**
 * 进程中的子系统实现该接口 子系统生命周期
 */
public interface LifecycleController {
    public abstract void init(GameProcess gameProcess) throws Exception;

    public abstract void start(GameProcess gameProcess) throws Exception;

    public abstract void stop(GameProcess gameProcess) throws Exception;

}
