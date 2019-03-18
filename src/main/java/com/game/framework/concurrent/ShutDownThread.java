package com.game.framework.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShutDownThread extends Thread{

    private List<Runnable> runnableList = Collections.synchronizedList(new ArrayList<Runnable>());

    public ShutDownThread() {
        Runtime.getRuntime().addShutdownHook(this);
    }
    public void registerHook( Runnable hook ) {
        runnableList.add( hook );
    }

    public void run() {

        for( Runnable runnable : runnableList ) {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
//                ProcessGlobalData.gameLog.basicErr("ShutDownThread Run Error ", e);
            }
        }

    }
}
