package com.game.datacenter;

import java.io.File;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.game.common.cache.redis.DefaultRedisContextFacade;
import com.game.common.staticdata.GiftShopStaticDataProvider;
import com.game.common.staticdata.RobatStaticDataProvider;
import com.game.common.staticdata.ScheduleStaticDataProvider;
import com.game.common.staticdata.bean.RobatStaticData;
import com.game.common.staticdata.bean.ScheduleStaticData;
import com.game.datacenter.matchgame.MatchResultNotify;
import com.game.datacenter.matchgame.MatchingGameManager;
import com.game.datacenter.searchlimit.SearchLimitGameManager;
import com.game.datacenter.service.PlayerService;
import com.game.datacenter.unit.DataCenterPlayerUnit;
import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.keepqueue.KeepTaskManager;
import com.game.framework.concurrent.threadpool.UnchangedBindThreadPool;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.network.GameTCPIOClient;
import com.game.framework.network.node.ServerNode;
import com.game.framework.network.node.ServerNodeManager;
import com.game.framework.process.GameProcess;
import com.game.framework.util.IniFileReader;
import com.game.message.proto.ProtoContext_Common.GameStaticDataType;
import com.game.message.proto.ProtoContext_Common.ServerNodeType;

/**
 * 数据中心服务器
 */
public class DataCenterServer extends GameProcess {

	//	游戏找人
	private MatchingGameManager matchingGameManager = null;
	//	模糊匹配
	private SearchLimitGameManager searchLimitGameManager = null;
	
	private ServerNodeManager<DataCenterConn> serverNodeManager = new ServerNodeManager<DataCenterConn>();
	
	@SuppressWarnings("rawtypes")
	private AbstractStaticDataProvider[] dataProviderArray = null;
	
	public DataCenterServer(String section) {
		super(section);
	}

	public MatchingGameManager getMatchingGameManager() {
		return matchingGameManager;
	}
	
	public SearchLimitGameManager getSearchLimitGameManager() {
		return searchLimitGameManager;
	}
	
	private void loadAllRobat() {
		List<Object> allList = getAllStaticData(GameStaticDataType.ROBATDATA);
		
		PlayerService playerService = (PlayerService)ProcessGlobalData.appCTX.getBean("playerService");
		
		for( Object obj : allList ) {
			playerService.pushRobotUnit(DataCenterPlayerUnit.valueOf((RobatStaticData)obj));
		}
	}
	
	private void loadAllStaticData() {
		
		dataProviderArray[GameStaticDataType.SHCEDULE_VALUE] = new ScheduleStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "schedule.txt")).loader();

		dataProviderArray[GameStaticDataType.GIFTSHOP_VALUE] = new GiftShopStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "giftshop.txt")).loader();
		
		dataProviderArray[GameStaticDataType.ROBATDATA_VALUE] = new RobatStaticDataProvider(
				new File(ProcessGlobalData.excelExportTXTDir, "robot.txt")).loader();
		
	}

	
	
	public Object getStaticData(GameStaticDataType dataType, Object key) {
		return (dataProviderArray[dataType.getNumber()]).getStaticData(key);
	}

	public Object getStaticData(GameStaticDataType dataType, Object key1, Object key2) {
		return (dataProviderArray[dataType.getNumber()]).getStaticData(key1, key2);
	}

	public Object getStaticData(GameStaticDataType dataType, Object key1, Object key2, Object key3) {
		return (dataProviderArray[dataType.getNumber()]).getStaticData(key1, key2, key3);
	}
	@SuppressWarnings("unchecked")
	public List<Object> getAllStaticData(GameStaticDataType dataType) {
		return (dataProviderArray[dataType.getNumber()]).getAllStaticData();
	}
	public void init() throws Exception {

		String[] iniNode = ProcessGlobalData.configReader.getParam(section, "iniNode").split(";");

		if (iniNode == null || iniNode.length == 0) {
			throw new Exception("Not Found IniNode");
		}
		//	加载静态数据
		ProcessGlobalData.excelExportTXTDir = new File(System.getenv(ProcessGlobalData.ImportantEnv.GAME_EXCEL_EXPORTTXT));
		dataProviderArray = new AbstractStaticDataProvider[GameStaticDataType.values().length + 1];
		loadAllStaticData();
		//	加载机器人
		loadAllRobat();
		
		int count1 = Integer.valueOf(ProcessGlobalData.configReader.getParam(section, "logicThreadPoolCount1"));
		int count2 = Integer.valueOf(ProcessGlobalData.configReader.getParam(section, "logicThreadPoolCount2"));

		ProcessGlobalData.appLVSLogicProcessorExecutor = new UnchangedBindThreadPool(count1, count2, "LogicThread");

		String nodeName = null;

		ServerNode<DataCenterConn> serverNode = null;

		IniFileReader configReader = getConfigReader();
		String section = getSection();

		//	//////////////////////////////
		//	RoomServer 都是奇数 >=	1
		//	GameServer 都是偶数 >=	2
		//	//////////////////////////////
		
		
		
		for (int i = 0; i < iniNode.length; i ++) {
			
			nodeName = iniNode[i];
			serverNode = new ServerNode<DataCenterConn>();
			//	如果无value，默认为-1
			int id = configReader.getIntegerParam(section, nodeName + ".id", -1);
			ServerNodeType nodeType = ServerNodeType
					.valueOf(configReader.getIntegerParam(section, nodeName + ".type", -1));

			if (id == -1 || nodeType == null) {
				throw new IllegalArgumentException();
			}

			serverNode.setNode(new DataCenterConn(serverNode, nodeName, id));
			serverNode.setId(id);
			serverNode.setNodeType(nodeType.getNumber());
			
			addServerNode(null);
			addServerNode(serverNode);
			
			registerSubSystem(serverNode.getNode());
		}
		
		registerSubSystem(new DataCenterUDP("DataCenterServer"));
		
		super.init();

		MatchResultNotify matchResultNotify = (MatchResultNotify) ProcessGlobalData.appCTX.getBean("matchResultNotify");

		matchingGameManager = new MatchingGameManager(matchResultNotify, ProcessGlobalData.configReader.getIntegerParam(section, "matchResultNotify.TickTime", 50));
		matchingGameManager.start();

		SearchLimitNotify searchLimitNotify = (SearchLimitNotify) ProcessGlobalData.appCTX.getBean("searchLimitNotify");

		searchLimitGameManager = new SearchLimitGameManager(searchLimitNotify, ProcessGlobalData.configReader.getIntegerParam(section, "searchLimitNotify.TickTime", 50));
		searchLimitGameManager.start();
		
		ProcessGlobalData.redisContextFacade = new DefaultRedisContextFacade(ProcessGlobalData.redisContext);

		int corePoolSize = Integer
				.valueOf(ProcessGlobalData.configReader.getIntegerParam(section, "keepTaskPoolSize", 1));
		int maxinumPoolSize = Integer
				.valueOf(ProcessGlobalData.configReader.getIntegerParam(section, "keepTaskPoolSize", 1));

		LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();

		ProcessGlobalData.appLVSKeepTaskExecutor = new ThreadPoolExecutor(corePoolSize, maxinumPoolSize, 0,
				TimeUnit.MILLISECONDS, blockingQueue);

		((ThreadPoolExecutor) ProcessGlobalData.appLVSKeepTaskExecutor).prestartAllCoreThreads();
		
	}
	
	//	启动排行榜 
	void startRank() throws Exception {

		ScheduleStaticData data1 = (ScheduleStaticData)getStaticData(GameStaticDataType.SHCEDULE, 1);
		ScheduleStaticData data2 = (ScheduleStaticData)getStaticData(GameStaticDataType.SHCEDULE, 2);

		KeepTaskManager keepTaskManager = new KeepTaskManager(500);
		RankUtils rankUtils = new RankUtils(data1.getTimeType(), data1.getTime());
		RankUtils rankUtils2 = new RankUtils(data2.getTimeType(), data2.getTime());

		DayRankRunnableBuilder daybulider = new DayRankRunnableBuilder(keepTaskManager, rankUtils);
		keepTaskManager.addKeepTask(new DayRankKeepTask(keepTaskManager, daybulider, rankUtils));
		
		WeekRankRunnableBuilder weekbulider = new WeekRankRunnableBuilder(keepTaskManager, rankUtils2);
		keepTaskManager.addKeepTask(new WeekRankKeepTask(keepTaskManager, weekbulider, rankUtils2));

	}

	public ServerNode<DataCenterConn> getServerNode(int id) {
		return serverNodeManager.getServerNode(id);
	}
	
	public GameTCPIOClient getSerevrNodeIoClient(int id) {
		return getServerNode(id).getNode().getIoClient();
	}

	public void addServerNode(ServerNode<DataCenterConn> gateConn) {
		serverNodeManager.addNode(gateConn);
	}

	public ServerNode<DataCenterConn> getMinPeopleServer(int nodeType) {
		return serverNodeManager.getMinPeopleServer(nodeType);
	}
}
