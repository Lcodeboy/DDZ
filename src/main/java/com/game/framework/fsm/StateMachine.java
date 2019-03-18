package com.game.framework.fsm;

/**
 * 状态机
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class StateMachine {
    /** 状态机的所有者 */
    private Object owner = null;
    /** */
    /** 当前状态 */
    private State currentState = null;
    /** 上一个状态 */
    private State previousState = null;
    /** 全局状态 */
    private State globalState = null;
    /** 下一个状态*/
    private State nextState = null;

    public void clear(){
        currentState = null;
        previousState = null;
        globalState = null;
        nextState = null;
    }

    public StateMachine(Object owner) {
        this.owner = owner;
    }

    public void update(long time){

        if (globalState != null){
            globalState.execute(time, owner);
        }
        if (currentState != null){
            currentState.execute(time, owner);
        }
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

    public State getNextState() {
        return nextState;
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
