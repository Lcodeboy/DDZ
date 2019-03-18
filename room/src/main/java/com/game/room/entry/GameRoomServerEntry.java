package com.game.room.entry;

import com.game.framework.ProcessGlobalData;
import com.game.framework.network.process.GameProcess;
import com.game.room.RoomServer;
import com.game.room.RoomServerThreadLocalInit;

public class GameRoomServerEntry implements Runnable{
    // 全局配置文件, 系统中的所有节点都公用一个配置文件
    private String iniFile = null;
    // 此节点的配置
    private String section = null;
    // Spring的上下文环境
    private String appCTX = null;
    //
    private GameProcess gameProcess = null;

    public GameRoomServerEntry() throws Exception {

    }

    public void run() {
        try {
            gameProcess.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() throws Exception {
        ProcessGlobalData.initAll(iniFile, section, appCTX);

        gameProcess = new GameProcess(section);

        gameProcess.registerSubSystem(new RoomServerThreadLocalInit());
//        gameProcess.registerSubSystem(new RoomServerUDP());
        gameProcess.registerSubSystem(new RoomServer());
        gameProcess.init();
    }

    public void start() {
        Thread boostrapThread = new Thread(this);
        boostrapThread.setName("RoomBoostrap");
        boostrapThread.start();
    }

    public void stop() throws Exception {
        gameProcess.stop();
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("com.mchange.v2.log.MLog", "com.mchange.v2.log.log4j.Log4jMLog");
        // System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
        try {
            GameRoomServerEntry entry = new GameRoomServerEntry();

            entry.section = args[0];
            entry.appCTX = "com/game/room/config/appctx.xml";
            entry.iniFile = "gamesystem.ini";

            entry.init();
            entry.start();
        } catch (Exception e) {
            e.printStackTrace();
            ProcessGlobalData.gameLog.basicErr("Entry Error", e);
            Thread.sleep(Integer.valueOf(args[1]));
        }
    }
}
