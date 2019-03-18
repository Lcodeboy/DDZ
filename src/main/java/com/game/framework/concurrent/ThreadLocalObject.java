package com.game.framework.concurrent;

public class ThreadLocalObject {
    private Object[] array = null;
    //

    public ThreadLocalObject(int size) {
        array = new Object[size];
    }
    public int getSize(){
        return array.length;
    }
    public void setValue(int index,Object object){
        array[index] = object;
    }
    public Object getValue(int index){
        return array[index];
    }
}
