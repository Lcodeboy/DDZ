package com.game.game.entry;

import com.game.framework.ProcessGlobalData;
import com.game.framework.process.GameProcess;
import com.game.game.GameServer;
import com.game.game.GameServerThreadLocalInit;

public class GameServerEntry implements Runnable {

	//	全局配置文件, 系统中的所有节点都公用一个配置文件
	private String iniFile = null;
	//	此节点的配置
	private String section = null;
	//	Spring的上下文环境
	private String appCTX = null;
	//
	private GameProcess gameProcess = null;
	
	public GameServerEntry() throws Exception {
		
	}

	public void run() {
		try {
			gameProcess.start();
		} catch (Exception e) {
			ProcessGlobalData.gameLog.basicErr("GameServerEntry Error", e);
		}
	}

	public void init() throws Exception {
		ProcessGlobalData.initAll(iniFile, section, appCTX);
		gameProcess = new GameProcess(section);
		gameProcess.registerSubSystem( new GameServerThreadLocalInit() );
//		gameProcess.registerSubSystem( new GameServerUDP() );
		gameProcess.registerSubSystem( new GameServer() );
		gameProcess.init();
	}

	public void start() {
		Thread boostrapThread = new Thread( this );
		boostrapThread.setName("GameBoostrap");
		boostrapThread.start();
	}

	public void stop() throws Exception {
		gameProcess.stop();
	}

	public static void main(String[] args) throws Exception {
		
		try {
			GameServerEntry entry = new GameServerEntry();
			
			entry.section = args[0];
			entry.appCTX = "com/game/game/config/appctx.xml";
			entry.iniFile = "gamesystem.ini";
			
			entry.init();
			entry.start();
		} catch ( Exception e ) {
			e.printStackTrace();
			ProcessGlobalData.gameLog.basicErr("Entry Error", e);
			Thread.sleep(Integer.valueOf(args[1]));
		}
		
	}

}
