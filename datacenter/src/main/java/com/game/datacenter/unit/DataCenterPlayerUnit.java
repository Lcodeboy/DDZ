package com.game.datacenter.unit;

import java.util.ArrayList;
import java.util.List;
import com.game.common.cache.redis.DefaultRedisContextFacade;
import com.game.common.staticdata.bean.RobatStaticData;
import com.game.datacenter.DataCenterServer;
import com.game.datacenter.matchgame.MatchingGameManager;
import com.game.datacenter.memory.MEMPlayerBase;
import com.game.datacenter.searchlimit.SearchLimitGameManager;
import com.game.framework.ProcessGlobalData;
import com.game.framework.network.GeneratedMessagePack;
import com.game.message.proto.ProtoContext_Common.GameType;
import com.game.message.proto.ProtoContext_Common.PlayerState;
import com.game.message.proto.ProtoContext_Common.PlayerType;

public class DataCenterPlayerUnit {
	//
	private volatile PlayerState playerState = null;
	//
	private MEMPlayerBase playerBase = null;
	//
	private MEMPlayerRelation playerRelation = null;
	//
	private List<MEMPlayerBackpack>[] playerBackpackGroup = null;
	//
	private MEMPlayerBattleresult playerBattleresult = null;
	//
	private MEMPlayerCoin playerCoin = null;
	//
	private MEMPlayerShowoff playerShowoff = null;
	//
	private PlayerType playerType = null;
	// -1表示该用户离线
	private volatile int roomServerId = -1;
	//
	private DataCenterPlayerBattleCounter battleCounter = null;
	//
	private ArrayList<GeneratedMessagePack> playerOfflineMSGList = null;
	//
	private volatile int chatDuadPlayerId = 0;
	//
	protected long joinProcessTime = 0;
	//
	protected long quitProcessTime = 0;
	//
	protected List<MEMPlayerNotify> playerNotifyList = null;
	// 记录游戏找人匹配（非模糊匹配）时的游戏类型 1. 防止反复点击匹配 2. cancle 时
	private volatile GameType gameType = null;
	// 防止模糊匹配反复点击
	private volatile boolean isSearchLimit = false;
	//
	protected String token = null;
	
	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public int getPlayer32Id() {
		return playerBase.getPlayerid();
	}

	public List<MEMPlayerNotify> getPlayerNotifyList() {
		return playerNotifyList;
	}

	public void setPlayerNotifyList(List<MEMPlayerNotify> broadcastNotify) {
		this.playerNotifyList = broadcastNotify;
	}

	public static DataCenterPlayerUnit valueOf(RobatStaticData robatStaticData) {
		DataCenterPlayerUnit playerUnit = new DataCenterPlayerUnit();
		MEMPlayerBase playerBase = new MEMPlayerBase();
		MEMPlayerShowoff showoff = new MEMPlayerShowoff();

		playerBase.copyFrom(robatStaticData);
		showoff.setPlayerid(playerBase.getPlayerid());

		playerUnit.setPlayerBase(playerBase);
		playerUnit.setPlayerType(PlayerType.ROBAT);
		playerUnit.setPlayerState(PlayerState.ONLINE);
		playerUnit.setRoomServerId(-1);
		return playerUnit;
	}

	public String toDebugRankString() {
		int golang = battleCounter.getBattleCounter(GameType.Golang);
		int animalChess = battleCounter.getBattleCounter(GameType.AnimalChess);
		int guessAircraft = battleCounter.getBattleCounter(GameType.GuessAircraft);

		return playerBase.getPlayerid() + " " + playerBase.getPlayername() + " " + playerBase.getHead() + " "
				+ playerBase.isSex() + " # " + "golangCounter " + golang + " animalChessCounter " + animalChess
				+ " guessAircraftCounter " + guessAircraft;
	}

	public DataCenterPlayerUnit() {
		battleCounter = new DataCenterPlayerBattleCounter();
		playerOfflineMSGList = new ArrayList<GeneratedMessagePack>();
	}

	public void addOfflineMSG(GeneratedMessagePack msg) {
		playerOfflineMSGList.add(msg);
	}

	public ArrayList<GeneratedMessagePack> getOfflineMSGList() {
		return playerOfflineMSGList;
	}

	public MEMPlayerShowoff getPlayerShowoff() {
		return playerShowoff;
	}

	public void setPlayerShowoff(MEMPlayerShowoff playerShowoff) {
		this.playerShowoff = playerShowoff;
	}

	public MEMPlayerCoin getPlayerCoin() {
		return playerCoin;
	}

	public void setPlayerCoin(MEMPlayerCoin playerCoin) {
		this.playerCoin = playerCoin;
	}

	public MEMPlayerBase getPlayerBase() {
		return playerBase;
	}

	public void setPlayerBase(MEMPlayerBase playerBase) {
		this.playerBase = playerBase;
	}

	public MEMPlayerRelation getPlayerRelation() {
		return playerRelation;
	}

	public void setPlayerRelation(MEMPlayerRelation playerRelation) {
		this.playerRelation = playerRelation;
	}

	public PlayerType getPlayerType() {
		return playerType;
	}

	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
	}

	public int getRoomServerId() {
		return roomServerId;
	}

	public void setRoomServerId(int roomServerId) {
		this.roomServerId = roomServerId;
	}

	public List<MEMPlayerBackpack>[] getPlayerBackpackGroup() {
		return playerBackpackGroup;
	}

	public void setPlayerBackpackGroup(List<MEMPlayerBackpack>[] playerBackpackGroup) {
		this.playerBackpackGroup = playerBackpackGroup;
	}

	public MEMPlayerBattleresult getPlayerBattleresult() {
		return playerBattleresult;
	}

	public void setPlayerBattleresult(MEMPlayerBattleresult playerBattleresult) {
		this.playerBattleresult = playerBattleresult;
	}

	public PlayerState getPlayerState() {
		
		if (playerState == null) {
			return PlayerState.OFFLINE;
		}

		if (roomServerId == -1 && playerType == PlayerType.NORMAL) {
			return PlayerState.OFFLINE;
		}
		
		return playerState;
	}

	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}

	public DataCenterPlayerBattleCounter getBattleCounter() {
		return battleCounter;
	}

//	public void setBattleCounter(DataCenterPlayerBattleCounter battleCounter) {
//		this.battleCounter = battleCounter;
//	}

	public int getChatDuadPlayerId() {
		return chatDuadPlayerId;
	}

	public void setChatDuadPlayerId(int chatDuadPlayerId) {
		this.chatDuadPlayerId = chatDuadPlayerId;
	}

	public long getJoinProcessTime() {
		return joinProcessTime;
	}

	public long getQuitProcessTime() {
		return quitProcessTime;
	}

	public void setQuitProcessTime(long quitProcessTime) {
		this.quitProcessTime = quitProcessTime;
	}
	
	public boolean isSearchLimit() {
		return isSearchLimit;
	}

	public void setSearchLimit(boolean isSearchLimit) {
		this.isSearchLimit = isSearchLimit;
	}

	public void online() {
		joinProcessTime = System.currentTimeMillis();
		setPlayerState(PlayerState.ONLINE);

		DefaultRedisContextFacade redisContextFacade = (DefaultRedisContextFacade) ProcessGlobalData.redisContextFacade;
		quitProcessTime = redisContextFacade.getQuitTime(playerBase.getPlayerid());

		ProcessGlobalData.gameLog.basic("Player " + getPlayer32Id() + " OnLine From " + getRoomServerId());

	}

	public void offline() {

		ProcessGlobalData.gameLog.basic("Player " + getPlayer32Id() + " OffLine From " + getRoomServerId());

		quitProcessTime = System.currentTimeMillis();
		setPlayerState(PlayerState.OFFLINE);
		setRoomServerId(-1);
		DefaultRedisContextFacade redisContextFacade = (DefaultRedisContextFacade) ProcessGlobalData.redisContextFacade;
		redisContextFacade.setQuitTime(playerBase.getPlayerid(), quitProcessTime);

		DataCenterServer dataCenterServer = (DataCenterServer) ProcessGlobalData.gameProcess;
		// 离线退出模糊匹配
		SearchLimitGameManager searchLimitGameManager = dataCenterServer.getSearchLimitGameManager();
		searchLimitGameManager.quit(getPlayer32Id(), playerBase.isSex(), playerBase.getAge());
		
		setSearchLimit(false);
		
		// 离线退出游戏找人匹配
		MatchingGameManager matchingGameManager = dataCenterServer.getMatchingGameManager();
		if (getGameType() != null) {
			// gameType 与 playerState 区别：退出该匹配时需要用 gameType 和 playerID去索引才能找到
			matchingGameManager.quit(getPlayer32Id(), getGameType());
			// 清空
			setGameType(null);
		}

	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
}
