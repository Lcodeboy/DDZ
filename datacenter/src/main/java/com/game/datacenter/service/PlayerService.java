package com.game.datacenter.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.cache.redis.DefaultRedisContextFacade;
import com.game.dao.DAOContext;
import com.game.dao.PlayerBaseMapper;
import com.game.dao.bean.DBPlayerBase;
import com.game.datacenter.memory.MEMPlayerBase;
import com.game.datacenter.unit.DataCenterPlayerUnit;
import com.game.framework.ProcessGlobalData;
import com.game.message.proto.ProtoContext_Common.BackpackType;
import com.game.message.proto.ProtoContext_Common.NotifyType;
import com.game.message.proto.ProtoContext_Common.PlayerState;
import com.game.message.proto.ProtoContext_Common.PlayerType;

@Service
public class PlayerService {

	@Autowired
	private DAOContext daoContext = null;
	//
	private ConcurrentHashMap<Integer, DataCenterPlayerUnit> playerUnitMap = new ConcurrentHashMap<Integer, DataCenterPlayerUnit>();
	//
	private HashMap<Integer, DataCenterPlayerUnit> robotMap = new HashMap<Integer, DataCenterPlayerUnit>();
	//
	private ArrayList<DataCenterPlayerUnit> robotList = new ArrayList<>();

	public void pushRobotUnit(DataCenterPlayerUnit playerUnit) {
		robotMap.put(playerUnit.getPlayerBase().getPlayerid(), playerUnit);
		robotList.add(playerUnit);
	}

	public ArrayList<DataCenterPlayerUnit> getAllRobatPlayerUnit() {
		return robotList;
	}

	public DataCenterPlayerUnit getRobatPlayerUnit(int playerId) {
		return robotMap.get(playerId);
	}

	/**
	 * 
	 * @param playerId
	 * @param playerUnit
	 */
	private void pushPlayerUnit(int playerId, DataCenterPlayerUnit playerUnit) {
		playerUnitMap.put(playerId, playerUnit);
	}
	
	public int getPlayerCount() {
		return playerUnitMap.size();
	}

	// 这个函数不要轻易调用
	public ArrayList<DataCenterPlayerUnit> getAllNORMALPlayerUnit() {

		ArrayList<DataCenterPlayerUnit> allPlayerUnit = new ArrayList<DataCenterPlayerUnit>();

		Collection<DataCenterPlayerUnit> collection = playerUnitMap.values();

		for (DataCenterPlayerUnit playerUnit : collection) {
			if (playerUnit.getPlayerType() == PlayerType.ROBAT) {
				continue;
			}

			allPlayerUnit.add(playerUnit);
		}

		return allPlayerUnit;
	}

	// 这个函数不要轻易调用
	public ArrayList<DataCenterPlayerUnit> getAllNORMALOnlinePlayerUnit() {

		ArrayList<DataCenterPlayerUnit> allPlayerUnit = new ArrayList<DataCenterPlayerUnit>();

		Collection<DataCenterPlayerUnit> collection = playerUnitMap.values();

		for (DataCenterPlayerUnit playerUnit : collection) {
			if (playerUnit.getPlayerType() == PlayerType.ROBAT) {
				continue;
			}

			if (playerUnit.getPlayerState() == PlayerState.GAMEING
					|| playerUnit.getPlayerState() == PlayerState.ONLINE) {
				allPlayerUnit.add(playerUnit);
			}

		}

		return allPlayerUnit;
	}

	public DataCenterPlayerUnit getPlayerUnitFromCache(int playerId) {
		DataCenterPlayerUnit playerUnit = playerUnitMap.get(playerId);

		return playerUnit;
	}

	public DataCenterPlayerUnit getPlayerUnitFromCacheOnline(int playerId) {
		DataCenterPlayerUnit playerUnit = playerUnitMap.get(playerId);

		if (playerUnit == null) {
			return null;
		}

		if (playerUnit.getPlayerState() == PlayerState.GAMEING || playerUnit.getPlayerState() == PlayerState.ONLINE) {
			return playerUnit;
		}

		return playerUnit;
	}
	
//	public void deletePlayerUnitFromCache(int playerId) {
//		playerUnitMap.remove(playerId);
//	}

	@SuppressWarnings({ "unchecked" })
	public DataCenterPlayerUnit getPlayerUnit(int playerId) {

		DataCenterPlayerUnit playerUnit = playerUnitMap.get(playerId);

		// 如果缓存中没有, 就去数据库中查询, 在放进缓存
		if (playerUnit == null) {
			DBPlayerBase dbPlayerBase = daoContext.getPlayerBaseMapper().selectPlayer(playerId);
			if (dbPlayerBase == null) {
				return null;
			}
			DBPlayerRelation dbPlayerRelation = daoContext.getPlayerRelationMapper().selectPlayerRelation(playerId);
			if (dbPlayerRelation == null) {
				return null;
			}
			
			playerUnit = new DataCenterPlayerUnit();
			
			MEMPlayerBase playerBase = new MEMPlayerBase();
			MEMPlayerRelation playerRelation = new MEMPlayerRelation();

			// 数据库中查询到的该用户的所有道具信息
			List<DBPlayerBackpack> dbPlayerBackpackAllList = daoContext.getPlayerBackpackMapper().selectById(playerId);

			if (dbPlayerBackpackAllList != null && !dbPlayerBackpackAllList.isEmpty()) {
				int backPackTypeLength = BackpackType.values().length;

				ArrayList<MEMPlayerBackpack>[] memGroup = new ArrayList[backPackTypeLength];

				playerUnit.setPlayerBackpackGroup(memGroup);

				for (int i = 0; i < backPackTypeLength; i++) {
					memGroup[i] = new ArrayList<MEMPlayerBackpack>();
				}

				for (DBPlayerBackpack dbBackPack : dbPlayerBackpackAllList) {
					// 将背包下的所有记录拷贝
					MEMPlayerBackpack backpack = new MEMPlayerBackpack();

					backpack.copyFrom(dbBackPack);

					playerUnit.getPlayerBackpackGroup()[backpack.getBtype()].add(backpack);
				}
			}

			// ///////////////////
			// coin AND showoff
			// ///////////////////
			DBPlayerCoin dbPlayerCoin = daoContext.getPlayerCoinMapper().selectPlayer(playerId);
			MEMPlayerCoin playerCoin = new MEMPlayerCoin();

			DBPlayerShowoff dbPlayerShowoff = daoContext.getPlayerShowoffMapper().selectPlayerShowoff(playerId);
			MEMPlayerShowoff playerShowoff = new MEMPlayerShowoff();

			playerShowoff.copyFrom(dbPlayerShowoff);
			playerCoin.copyFrom(dbPlayerCoin);
			playerBase.copyFrom(dbPlayerBase);
			playerRelation.copyFrom(dbPlayerRelation);

			playerUnit.setPlayerBase(playerBase);
			playerUnit.setPlayerRelation(playerRelation);
			playerUnit.setPlayerType(PlayerType.NORMAL);
			playerUnit.setPlayerCoin(playerCoin);
			playerUnit.setPlayerShowoff(playerShowoff);

			long quitTime = ((DefaultRedisContextFacade) ProcessGlobalData.redisContextFacade)
					.getQuitTime(playerBase.getPlayerid());

			playerUnit.setQuitProcessTime(quitTime == 0 ? playerBase.getCreatetime() : quitTime);

			pushPlayerUnit(playerId, playerUnit);
		}

		return playerUnit;
	}

	public int incrPlayerUnitCoin1(int playerid, int coin1) {
		DataCenterPlayerUnit playerUnit = getPlayerUnit(playerid);
		if (playerUnit == null) {
			return -1;
		}

		MEMPlayerCoin playerCoin = playerUnit.getPlayerCoin();

		playerCoin.setCoin1(playerCoin.getCoin1() + coin1);

		int result = daoContext.getPlayerCoinMapper().updateCoinOne(playerid, playerCoin.getCoin1());

		return result == 1 ? 0 : -2;
	}

	/**
	 * 
	 * @param playerId
	 * @param playername
	 * @param sex
	 * @param head
	 * @param birthday
	 * @param city
	 * @return
	 */
	public boolean updatePlayerUnit(int playerId, String playername, boolean sex, String head, String birthday,
			String city) {

		DataCenterPlayerUnit playerUnit = getPlayerUnit(playerId);

		if (playerUnit == null) {
			return false;
		}

		MEMPlayerBase playerBase = playerUnit.getPlayerBase();

		playerBase.setPlayername(playername);
		playerBase.setSex(sex);
		playerBase.setHead(head);

		boolean resetAge = !playerBase.getBirthday().equals(birthday);

		playerBase.setBirthday(birthday);
		playerBase.setCity(city);

		if (resetAge) {
			playerBase.resetAge();
		}

		PlayerBaseMapper playerBaseMapper = daoContext.getPlayerBaseMapper();

		return playerBaseMapper.update(playerId, playername, sex, head, birthday, city) == 1;
	}
	
	/**
	 * 
	 * @param playerUnit
	 */
	public void getPlayerNotifyMessage( DataCenterPlayerUnit playerUnit ) {
		List<DBPlayerNotify> playerNotifyList = daoContext.getPlayerNotifyMapper().select( playerUnit.getPlayer32Id() );
		//	playerUnit quitProcess 后的所有 notify
		List<DBBroadcastNotify> broadcastNotifyList = daoContext.getBroadcastNotifyMapper().selectLimit(
				playerUnit.getQuitProcessTime(), playerUnit.getPlayerBase().getCreatetime());
		
		MEMPlayerNotify temp = null;
		ArrayList<MEMPlayerNotify> resultList = new ArrayList<>(broadcastNotifyList.size() + playerNotifyList.size());
		
		long nowTime = System.currentTimeMillis();
		
		if( !broadcastNotifyList.isEmpty() ) {
			DBPlayerNotify temp1 = null;
			
			for( DBBroadcastNotify broadcastNotify : broadcastNotifyList ) {
				temp1 = new DBPlayerNotify(playerUnit.getPlayer32Id(), NotifyType.GM_VALUE, 
						broadcastNotify.getNotifyformat(), 0, broadcastNotify.getNotifytime(), 
						broadcastNotify.getNotifyinfo(), broadcastNotify.getId());
				daoContext.getPlayerNotifyMapper().insert2(temp1);
				temp = new MEMPlayerNotify();
				temp.copyFrom(temp1);
				temp.setReceivetime(nowTime);
				resultList.add( temp );
			}
		}
		
		for( DBPlayerNotify playerNotify : playerNotifyList ) {
			temp = new MEMPlayerNotify();
			temp.copyFrom(playerNotify);
			resultList.add( temp );
		}
		
		daoContext.getPlayerNotifyMapper().update(nowTime, playerUnit.getPlayer32Id());
				
		playerUnit.setPlayerNotifyList(resultList);
		
		
	}
	
	public void playerLogQuit( DataCenterPlayerUnit playerUnit, long nowTime ) {
		if( playerUnit.getToken() != null ) {
			daoContext.getPlayerLogMapper().updateQuitTime(playerUnit.getPlayer32Id(), nowTime, playerUnit.getToken());
		}
	}
	
	public void playerLogJoin( DataCenterPlayerUnit playerUnit, long nowTime ) {
		if( playerUnit.getToken() != null ) {
			daoContext.getPlayerLogMapper().updateJoinRoomTime(playerUnit.getPlayer32Id(), nowTime, playerUnit.getToken());
		}
	}
	
}
