package com.game.framework.process;

import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.ShutDownThread;
import com.game.framework.network.GameProcessState;
import com.game.framework.util.IniFileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class GameProcess {

    protected ShutDownThread shutDownThread = null;

    /** 初始化时间 */
    protected long initTime = 0;
    /** 启动时间 */
    protected long startTime = 0;
    /** 结束时间 */
    protected long stopTime = 0;
    /** 					*/
    protected String section = null;
    /** 进程的名字 */
    protected String name = null;
    /** 读取配置文件的接口 */
    protected IniFileReader configReader;
    /** 状态值 */
    protected volatile GameProcessState processState = GameProcessState.NONE;
    /** */
    protected ArrayList<SubSystem> subSystemList = new ArrayList<SubSystem>();
    /** */
    protected HashMap<String, SubSystem> subSystemMap = new HashMap<>();

    public void registerSubSystem( SubSystem subSystem ) {
//        ProcessGlobalData.gameLog.basic("Reg SubSystem " + subSystem.getSubSystemName());
        subSystemList.add( subSystem );
        subSystemMap.put(subSystem.getSubSystemName(), subSystem);
    }

    public String getName() {
        return name;
    }

    public String getSection() {
        return section;
    }

    public void registerHook(Runnable runnable) {
        shutDownThread.registerHook(runnable);
    }

    public SubSystem getSubSystem( String name ) {
        return subSystemMap.get( name );
    }

    public GameProcess( String section ) {
        this.configReader = ProcessGlobalData.configReader;
        this.section = section;
        shutDownThread = new ShutDownThread();
        shutDownThread.setName("ShutDownThread");
        ProcessGlobalData.gameProcess = this;
    }

    public IniFileReader getConfigReader() {
        return configReader;
    }

    public GameProcessState getState() {
        return processState;
    }

    public static enum LogicExecutorType {

        NONE(0), THREADPOOL(1), UNCHANGEDBIND(2);
        private int type;

        private LogicExecutorType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

    }

    public void init() throws Exception {
        System.currentTimeMillis();
        initTime = System.currentTimeMillis();
        processState = GameProcessState.INITING;

        // ////////////////////
        // 加载配置文件
        // ////////////////////

        File configDir = ProcessGlobalData.getConfigDirAddressingStrategy().getConfigDir();

        if (configDir == null) {
            throw new IllegalStateException("The GameFrameWork Not Support " + ProcessGlobalData.osPlatform);
        }

        name = configReader.getParam(section, "name");

        initSubSystem();

        processState = GameProcessState.INITED;
    }

    public void start() throws Exception {
        startTime = System.currentTimeMillis();
        processState = GameProcessState.STARTING;

        startSubSystem();

        processState = GameProcessState.RUNNING;
    }

    public void stop() throws Exception {
        stopTime = System.currentTimeMillis();
        processState = GameProcessState.STOPING;

        stopSubSystem();

        processState = GameProcessState.STOPED;
    }

    private void initSubSystem() throws Exception {
        for( SubSystem sub : subSystemList ) {
            ProcessGlobalData.gameLog.basic("Init SubSystem " + sub.getSubSystemName() + " <");
            sub.init(this);
            ProcessGlobalData.gameLog.basic("Init SubSystem " + sub.getSubSystemName() + " >");
        }
    }

    private void startSubSystem() throws Exception {
        for( SubSystem sub : subSystemList ) {
            ProcessGlobalData.gameLog.basic("Start SubSystem " + sub.getSubSystemName() + " <");
            sub.start(this);
            ProcessGlobalData.gameLog.basic("Start SubSystem " + sub.getSubSystemName() + " <");
        }
    }

    private void stopSubSystem() throws Exception {
        for( SubSystem sub : subSystemList ) {
            ProcessGlobalData.gameLog.basic("Stop SubSystem " + sub.getSubSystemName() + " <");
            sub.stop(this);
            ProcessGlobalData.gameLog.basic("Stop SubSystem " + sub.getSubSystemName() + " <");
        }
    }

}
