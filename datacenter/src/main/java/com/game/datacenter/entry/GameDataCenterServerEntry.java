
package com.game.datacenter.entry;

import com.game.datacenter.DataCenterServer;
import com.game.framework.ProcessGlobalData;

public class GameDataCenterServerEntry implements Runnable {

	// 全局配置文件, 系统中的所有节点都公用一个配置文件
	private String iniFile = null;
	// 此节点的配置
	private String section = null;
	// Spring的上下文环境
	private String appCTX = null;
	//
	private DataCenterServer gameProcess = null;

	public GameDataCenterServerEntry() throws Exception {

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
		gameProcess = new DataCenterServer(section);
		gameProcess.init();
	}

	public void start() {
		Thread boostrapThread = new Thread(this);
		boostrapThread.setName("DataCenterBoostrap");
		boostrapThread.start();
	}

	public void stop() throws Exception {
		gameProcess.stop();
	}

	public static void main(String[] args) throws Exception {

		System.setProperty("com.mchange.v2.log.MLog", "com.mchange.v2.log.log4j.Log4jMLog");
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();

		try {
			GameDataCenterServerEntry entry = new GameDataCenterServerEntry();
			entry.section = args[0];
			entry.appCTX = "com/game/datacenter/config/appctx.xml";
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
