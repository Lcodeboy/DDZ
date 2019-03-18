package com.game.framework.support.fsm;

import com.game.framework.exception.FSMException;

/**
 * 
 * @Description 智能体的状态
 */
public interface State<T> {
	
	public int getStateId();
	
	/**
	 * 
	 * @Description 状态-进入逻辑
	 * @param entity
	 */
	public void enter(long time, T entity) throws FSMException;
	
	/**
	 * 
	 * @Description 状态-具体逻辑
	 * @param entity
	 */
	public void execute(long time, T entity) throws FSMException;
	
	/**
	 * 
	 * @Description 状态-退出逻辑
	 * @param entity
	 */
	public void exit(long time, T entity) throws FSMException;
	
}
