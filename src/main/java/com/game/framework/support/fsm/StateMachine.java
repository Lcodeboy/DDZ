package com.game.framework.support.fsm;

import com.game.framework.ProcessGlobalData;

/**
 * 
 * @Description 状态机
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class StateMachine {

	/** 状态机的所有者 */
	private Object owner = null;
	/** 当前状态 */
	private State currentState = null;
	/** 上一个状态 */
	private State previousState = null;
	/** 全局状态 */
	private State globalState = null;
	/** 下一个状态*/
	private State nextState = null;
	
	private boolean changeStateLog = false;
	
	public void clear() {
		currentState = null;
		previousState = null;
		globalState = null;
		nextState = null;
	}
	
	/**
	 * 
	 * @Description
	 * @author chen.su
	 * @date 2015-4-25 上午10:42:07
	 * @param t
	 */
	public StateMachine(Object t, boolean changeStateLog) {
		owner = t;
		this.changeStateLog = changeStateLog;
	}

	public void update( long time ) {
		
		if (globalState != null) {
			globalState.execute(time, owner);
		}

		if (currentState != null) {
			currentState.execute(time, owner);
		}
	}

	public void changeState(long time, State newState) {
		previousState = currentState;
		nextState = newState;
		if( changeStateLog ) {
			
			ProcessGlobalData.gameLog.basic( getOwner().toString() +
					" PrevState " + previousState.getClass().getSimpleName() + 
					" NewState " + newState.getClass().getSimpleName());
		}
		
		currentState.exit(time, owner );
		currentState = newState;
		currentState.enter(time, owner );
	}

	public State getNextState() {
		return nextState;
	}
	
	public Object getOwner() {
		return owner;
	}

	public void setOwner(Object owner) {
		this.owner = owner;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public State getPreviousState() {
		return previousState;
	}

	public void setPreviousState(State previousState) {
		this.previousState = previousState;
	}

	public State getGlobalState() {
		return globalState;
	}

	public void setGlobalState(State globalState) {
		this.globalState = globalState;
	}
	
	public boolean isInState(State state) {
		if (currentState == null || state == null) {
			return false;
		}

		if (currentState.equals(state)) {
			return true;
		}
		
		return false;
	}

}
