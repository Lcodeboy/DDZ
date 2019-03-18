package com.game.internalgate.entry;

import com.game.framework.ProcessGlobalData;
import com.game.internalgate.InternalGateServer;
//import com.game.internalgate.InternalGateUDP;

//import testing.TestGraffitiPanel_Cai;


public class InternalGateServerEntry {
	// 	全局配置文件, 系统中的所有节点都公用一个配置文件
	private String iniFile = null;
	// 	此节点的配置
	private String section = null;
	// 	Spring的上下文环境
	private String appCTX = null;
	//	
	private InternalGateServer internalGateServer = null;
	
	public void init() throws Exception {
		ProcessGlobalData.initAll(iniFile, section, appCTX);
		
		internalGateServer = new InternalGateServer(section);
//		internalGateServer.registerSubSystem(new InternalGateUDP("UDPNode"));
		internalGateServer.init();
	}

	public void start() throws Exception {
		internalGateServer.start();
	}

//	public static TestGraffitiPanel_Cai cai = null;
	public static void main(String[] args) throws Exception {

		try {
			InternalGateServerEntry entry = new InternalGateServerEntry();

			entry.section = args[0];
			entry.appCTX = "com/game/internalgate/config/appctx.xml";
			entry.iniFile = "gamesystem.ini";

			entry.init();
			entry.start();

		} catch (Exception e) {
			e.printStackTrace();
			ProcessGlobalData.gameLog.basicErr("Entry Error", e);
			Thread.sleep(Integer.valueOf(args[1]));
		}
		
//		cai = new TestGraffitiPanel_Cai();
//		cai.cai();
		
	}

}
