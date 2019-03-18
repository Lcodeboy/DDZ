package com.game.framework.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntIDBuilder implements AtomicIDBuilder {

    private AtomicInteger atomicInteger = null;

    public AtomicIntIDBuilder(int counter) {
        this.atomicInteger = new AtomicInteger(counter);
    }
    public int getInt(){
        return atomicInteger.getAndIncrement();
    }
}
