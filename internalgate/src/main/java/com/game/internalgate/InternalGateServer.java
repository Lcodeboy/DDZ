package com.game.internalgate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.game.common.cache.redis.DefaultRedisContextFacade;
import com.game.common.staticdata.RobatStaticDataProvider;
import com.game.common.staticdata.bean.RobatStaticData;
import com.game.framework.ProcessGlobalData;
import com.game.framework.concurrent.threadpool.UnchangedBindThreadPool;
import com.game.framework.dataprovier.AbstractStaticDataProvider;
import com.game.framework.network.node.ServerNode;
import com.game.framework.network.node.ServerNodeManager;
import com.game.framework.process.GameProcess;
import com.game.framework.util.IniFileReader;
import com.game.internalgate.bean.IniGatePlayerInfo;
import com.game.message.proto.ProtoContext_Common.DuadMSG;
import com.game.message.proto.ProtoContext_Common.GameStaticDataType;
import com.game.message.proto.ProtoContext_Common.ServerNodeType;

public class InternalGateServer extends GameProcess {

	public InternalGateServer(String section) {
		super(section);
	}

	@SuppressWarnings("rawtypes")
	private AbstractStaticDataProvider[] dataProviderArray = null;

	private ConcurrentHashMap<Long, IniGatePlayerInfo> playerMap = new ConcurrentHashMap<Long, IniGatePlayerInfo>();
	//
	private HashMap<Integer, IniGatePlayerInfo> robotMap = new HashMap<>();
	//
	private ArrayList<IniGatePlayerInfo> robotList = new ArrayList<>();
	//
	private ServerNodeManager<InternalGateConn> serverNodeManager = new ServerNodeManager<InternalGateConn>();
	
	private ConcurrentHashMap<Long, LinkedList<DuadMSG>> offlineMSGMap = new ConcurrentHashMap<>();
	
	public ArrayList<IniGatePlayerInfo> getALLRobotList() {
		return robotList;
	}

	public IniGatePlayerInfo getRobot(int id) {
		return robotMap.get(id);
	}
	
	public LinkedList<DuadMSG> getOffLineMSGList( long playerId ) {
		return offlineMSGMap.get(playerId);
	}
	
	public boolean removeOffLineMSGList( long playerId ) {
		return offlineMSGMap.remove( playerId ) != null;
	}
	
	public void addOffLineMSGList( long playerId, LinkedList<DuadMSG> linkedList ) {
		offlineMSGMap.put(playerId, linkedList);
	}
	
	public void pushRobotUnit(RobatStaticData robatStaticData) {
		IniGatePlayerInfo gatePlayerInfo = IniGatePlayerInfo.valueOf(robatStaticData);

		robotMap.put(robatStaticData.getId(), gatePlayerInfo);
		robotList.add(gatePlayerInfo);
	}

	public void init() throws Exception {

		String[] iniNode = ProcessGlobalData.configReader.getParam(section, "iniNode").split(";");

		if (iniNode == null || iniNode.length == 0) {
			throw new Exception("Not Found IniNode");
		}

		int count1 = Integer.valueOf(ProcessGlobalData.configReader.getParam(section, "logicThreadPoolCount1"));
		int count2 = Integer.valueOf(ProcessGlobalData.configReader.getParam(section, "logicThreadPoolCount2"));

		ProcessGlobalData.appLVSLogicProcessorExecutor = new UnchangedBindThreadPool(count1, count2, "LogicThread");

		String nodeName = null;

		ServerNode<InternalGateConn> serverNode = null;

		IniFileReader configReader = getConfigReader();
		String section = getSection();

		// //////////////////////////////
		// RoomServer 都是奇数 >= 1
		// GameServer 都是偶数 >= 2
		// //////////////////////////////
		addServerNode(null);
		for (int i = 0; i < iniNode.length; i++) {
			nodeName = iniNode[i];
			serverNode = new ServerNode<InternalGateConn>();

			int id = configReader.getIntegerParam(section, nodeName + ".id", -1);
			ServerNodeType nodeType = ServerNodeType
					.valueOf(configReader.getIntegerParam(section, nodeName + ".type", -1));

			if (id == -1 || nodeType == null) {
				throw new IllegalArgumentException();
			}

			if (nodeType == ServerNodeType.GAME && id % 2 != 0) {
				throw new IllegalArgumentException("GameServer NodeId must is Even Number");
			} else if (nodeType == ServerNodeType.ROOM && id % 2 == 0) {
				throw new IllegalArgumentException("RoomServer NodeId must is Odd Number");
			}

			serverNode.setNode(new InternalGateConn(serverNode, nodeName, id));
			serverNode.setId(id);
			serverNode.setNodeType(nodeType.getNumber());
			addServerNode(serverNode);
			registerSubSystem(serverNode.getNode());
		}

		super.init();
		ProcessGlobalData.redisContextFacade = new DefaultRedisContextFacade(ProcessGlobalData.redisContext);
		// 加载静态数据
		ProcessGlobalData.excelExportTXTDir = new File(
				System.getenv(ProcessGlobalData.ImportantEnv.GAME_EXCEL_EXPORTTXT));
		dataProviderArray = new AbstractStaticDataProvider[GameStaticDataType.values().length + 1];
		loadAllStaticData();
		// 加载机器人
		loadAllRobat();
	}

	private void loadAllRobat() {
		List<Object> allList = getAllStaticData(GameStaticDataType.ROBATDATA);

		for (Object data : allList) {
			pushRobotUnit((RobatStaticData) data);
		}
	}

	private void loadAllStaticData() {
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

	public ServerNode<InternalGateConn> getServerNode(int id) {
		return serverNodeManager.getServerNode(id);
	}

	public void addServerNode(ServerNode<InternalGateConn> gateConn) {
		serverNodeManager.addNode(gateConn);
	}

	public IniGatePlayerInfo getPlayerInfo(long playerId) {
		return playerMap.get(playerId);
	}
	
	public void removePlayerInfo( long playerId ) {
		playerMap.remove( playerId );
	}
	
	public void putPlayerInfo(IniGatePlayerInfo playerInfo) {
		playerMap.put(playerInfo.getPlayerId(), playerInfo);
	}

	public ServerNode<InternalGateConn> getMinPeopleServer(int nodeType) {
		return serverNodeManager.getMinPeopleServer(nodeType);
	}
	
	public int getPlayerCounter() {
		return playerMap.size();
	}
}
