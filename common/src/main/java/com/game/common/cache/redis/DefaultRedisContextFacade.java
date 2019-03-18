package com.game.common.cache.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.game.common.CommonConstantContext;
import com.game.common.cache.redis.bean.CBeanJoinFriendState;
import com.game.common.cache.redis.bean.CBeanMatchLog;
import com.game.common.cache.redis.bean.CRobatGiftItem;
import com.game.common.cache.redis.bean.CRobatShowoff;
import com.game.framework.ProcessGlobalData;
import com.game.framework.ProcessGlobalData.ImportantEnv;
import com.game.framework.cache.redis.RedisContext;
import com.game.framework.cache.redis.RedisContextFacade;
import com.game.framework.util.DateUtil;
import com.game.framework.util.geohash.GeoRange;
import com.game.framework.util.geohash.GeoSearch;
import com.game.message.proto.ProtoContext_Common.CharmRankItemFirstList;
import com.game.message.proto.ProtoContext_Common.CharmRankItemList;
import com.game.message.proto.ProtoContext_Common.ConsumeRankItemFirstList;
import com.game.message.proto.ProtoContext_Common.ConsumeRankItemList;
import com.game.message.proto.ProtoContext_Common.GameDayRank;
import com.game.message.proto.ProtoContext_Common.GameRankItemFirstList;
import com.game.message.proto.ProtoContext_Common.GameRankItemList;
import com.game.message.proto.ProtoContext_Common.GameType;
import com.game.message.proto.ProtoContext_Common.PlayerCardData;
import com.game.message.proto.ProtoContext_Common.PlayerLeastData;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

/**
 * 
 * @author suchen
 * @date 2018年8月23日上午10:02:23
 */
public class DefaultRedisContextFacade implements RedisContextFacade {
	private RedisContext redisContext = null;

	public DefaultRedisContextFacade(RedisContext redisContext) {
		this.redisContext = redisContext;
	}

	// 附近的人
	public static final String KEY_PREFIX_NEARBY;

	// 两人当天对局场数
	public static final String KEY_TWOPLAYER_GAMECOUNT;

	// 游戏排行榜
	public static final String KEY_PERSONALGAME_DAY;
	//
	public static final String KEY_PERSONALGAME_WEEK;

	public static final String KEY_GAMERANK_DAY;
	//
	public static final String KEY_GAMEINDEXRANK_DAY;
	//
	public static final String KEY_GAMERANK_WEEK;
	//
	public static final String KEY_GAMEINDEXRANK_WEEK;

	// 魅力榜
	public static final String KEY_PERSONALCHARM_DAY;
	//
	public static final String KEY_PERSONALCHARM_TOTAL;
	//
	public static final String KEY_CHARMRANK_DAY;
	//
	public static final String KEY_CHARMINDEXRANK_DAY;
	//
	public static final String KEY_CHARMRANK_TOTAL;
	//
	public static final String KEY_CHARMINDEXRANK_TOTAL;

	// 土豪榜
	public static final String KEY_PERSONALCONSUME_DAY;
	//
	public static final String KEY_PERSONALCONSUME_WEEK;
	//
	public static final String KEY_CONSUMERANK_DAY;
	//
	public static final String KEY_CONSUMEINDEXRANK_DAY;
	//
	public static final String KEY_CONSUMERANK_WEEK;
	//
	public static final String KEY_CONSUMEINDEXRANK_WEEK;

	// 用户的好友状态表(添加好友处)
	public static final String KEY_PREFIX_FRIENDSTATELIST;
	// 记录被拉黑前的关系
	public static final String KEY_BLACKOTHER_BEFORE;

	// 匹配记录
	public static final String KEY_PREFIX_MATCHLOG;
	// 模糊查找记录
	public static final String KEY_PLAYER_SEARCHLIMIT_DAY;
	// 每个人游戏类型的玩的总次数的计数器
	public static final String KEY_PLAYER_GAMECOUNT;
	// 每个游戏类型的在玩对数的计数器
	public static final String KEY_TOTAL_GAMECOUNT;
	// 游戏找人的计数器
	public static final String KEY_GFP_RESULT_WIN_BIG_COUNT;
	// 游戏找人的计数器
	public static final String KEY_GFP_RESULT_WIN_SMALL_COUNT;
	// 相册背景图片
	public static final String KEY_BACKGROUND_PHOTOS;
	// 用户性别是否已修改过
	public static final String KEY_PLAYER_SEX;
	// 当天游戏找人的计数器
	public static final String KEY_GFP_RESULT_WIN_BIG_COUNT_TODAY;
	// 当天游戏找人的计数器
	public static final String KEY_GFP_RESULT_WIN_SMALL_COUNT_TODAY;
	// 姓名是否存在
	public static final String KEY_ONLY_NAME;
	// 聊天语音房间计数器
	public static final String KEY_CHAT_HOME_ID;
	// 游戏类型排行
	public static final String KEY_RANGKBYTYPE_GAMECOUNT;
	// 机器人背包
	public static final String KEY_ROBAT_ITEM_DATA;
	// 机器人记录
	public static final String KEY_ROBAT_SHOWOFF_DATA;
	// 结算界面准备进入游戏
	public static final String KEY_SETTLEMENT_READY_GAME;
	// 退出时间
	public static final String KEY_QUITTIME;
	// ROOM服的人数计数器
	public static final String KEY_ROOM_PEOPLE_COUNTER;
	// AnyPayStruct
	public static final String KEY_ANY_PAY_STRUCT;
	// 惩罚时间计数器
	public static final String KEY_PLAYER_PUNISHTIME_COUNTER;
	//	
	public static final String KEY_GRAFFITI_QUIT_TIME;
	
	public static final String KEY_TOKEN;
	
	public static final String KEY_WELCOME;
	
	public static final String KEY_YUEWAN;
	
	static {
		String prefix = System.getenv(ImportantEnv.REDIS_PREFIX);

		if (prefix == null) {
			prefix = "";
		}

		KEY_TWOPLAYER_GAMECOUNT = prefix + "P.TwoPlayerCounter";
		KEY_PREFIX_FRIENDSTATELIST = prefix + "P.FriendStateList.";

		KEY_PERSONALGAME_DAY = prefix + "Rank.PersonalGame.Day2";
		KEY_GAMERANK_DAY = prefix + "Rank.GameRank.Day2";
		KEY_GAMEINDEXRANK_DAY = prefix + "Rank.GameIndexRank.Day2";
		KEY_PERSONALGAME_WEEK = prefix + "Rank.PersonalConsume.Week2";
		KEY_GAMERANK_WEEK = prefix + "Rank.GameRank.Week2";
		KEY_GAMEINDEXRANK_WEEK = prefix + "Rank.GameIndexRank.Week2";

		KEY_BLACKOTHER_BEFORE = prefix + "P.GameBlackOther.Before.";
		KEY_PREFIX_MATCHLOG = prefix + "P.MatchLog.";
		KEY_PLAYER_GAMECOUNT = prefix + "P.PlayerGameCount";
		KEY_TOTAL_GAMECOUNT = prefix + "P.TotalGameCount";
		KEY_PLAYER_SEARCHLIMIT_DAY = prefix + "P.SearchLimit.";
		KEY_GFP_RESULT_WIN_BIG_COUNT = prefix + "P.GFPCount.BIG.";
		KEY_GFP_RESULT_WIN_SMALL_COUNT = prefix + "P.GFPCount.SMALL.";
		KEY_BACKGROUND_PHOTOS = prefix + "P.BACKGROUND_PHOTO";
		KEY_PLAYER_SEX = prefix + "P.PlayerSex";
		KEY_PREFIX_NEARBY = prefix + "P.NEARBYZSET1.";
		KEY_GFP_RESULT_WIN_BIG_COUNT_TODAY = prefix + "P.GFPCount.BIG.";
		KEY_GFP_RESULT_WIN_SMALL_COUNT_TODAY = prefix + "P.GFPCount.SMALL.";
		KEY_ROBAT_ITEM_DATA = prefix + "R.ItemData";
		KEY_ROBAT_SHOWOFF_DATA = prefix + "R.ShowOffData";
		KEY_ONLY_NAME = prefix + "P.NameSet";
		KEY_CHAT_HOME_ID = prefix + "P.ChatHomeId";
		KEY_RANGKBYTYPE_GAMECOUNT = prefix + "P.Count";
		KEY_SETTLEMENT_READY_GAME = prefix + "P.Settlement.Game.";
		KEY_QUITTIME = prefix + "P.QuitTime";

		KEY_PERSONALCHARM_DAY = prefix + "Rank.PersonalCharm.Day2";
		KEY_CHARMRANK_DAY = prefix + "Rank.CharmRank.Day2";
		KEY_CHARMINDEXRANK_DAY = prefix + "Rank.CharmIndexRank.Day2";
		KEY_PERSONALCHARM_TOTAL = prefix + "Rank.PersonalCharm.Total2";
		KEY_CHARMRANK_TOTAL = prefix + "Rank.CharmRank.Total2";
		KEY_CHARMINDEXRANK_TOTAL = prefix + "Rank.CharmIndexRank.Total2";

		KEY_PERSONALCONSUME_DAY = prefix + "Rank.PersonalConsume.Day2";
		KEY_CONSUMERANK_DAY = prefix + "Rank.ConsumeRank.Day2";
		KEY_CONSUMEINDEXRANK_DAY = prefix + "Rank.ConsumeIndexRank.Day2";
		KEY_CONSUMERANK_WEEK = prefix + "Rank.ConsumeRank.Week2";
		KEY_CONSUMEINDEXRANK_WEEK = prefix + "Rank.ConsumeIndexRank.Week2";
		KEY_PERSONALCONSUME_WEEK = prefix + "Rank.PersonalConsume.Week2";

		KEY_ROOM_PEOPLE_COUNTER = prefix + "Room.People.Counter.";

		KEY_ANY_PAY_STRUCT = prefix + "PAY.LOG.";

		KEY_PLAYER_PUNISHTIME_COUNTER = prefix + "P.PunishTime.Counter";
		
		KEY_GRAFFITI_QUIT_TIME = prefix + "P.Graffiti_quit_time";
		
		KEY_TOKEN = prefix + "P.ToKen.";
		
		KEY_WELCOME = prefix + "P.WEL.";
		
		KEY_YUEWAN = prefix + "P.YUEWAN.";
		
	}
	
	//	0	表示可以进行游戏
	//	1	在保护时间内情况1
	//	2	在保护时间内情况2
	//	3	发生修改
	public int casYueWan( int activePlayerId, int passivePlayerId, long nowTime ) {

		int code = 0;
		
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();
		
		/**
		 *  WATCH mykey
		    val = GET mykey
		      val = val + 1
		      MULTI
		      SET mykey $val
		      EXEC
		 */
		try {
			String key1 = KEY_YUEWAN + activePlayerId + "." + passivePlayerId;
			String key2 = KEY_YUEWAN + passivePlayerId + "." + activePlayerId;
			
			String value = null;
			
			if( (value = jedis.get(key1)) != null && Long.valueOf(value) > nowTime - 1000 ) {
				return 1;
			}
			
			if( (value = jedis.get(key2)) != null && Long.valueOf(value) > nowTime - 1000 ) {
				return 2;
			}
			
			jedis.watch(key1, key2);
			Transaction transaction = jedis.multi();
			transaction.set(key1, "" + nowTime);
			transaction.set(key2, "" + nowTime);
			List<Object> results = transaction.exec();
			if( results == null || results.isEmpty() ) {
				code = 3;
			}
			jedis.unwatch();
		} catch (Exception e) {
			ProcessGlobalData.gameLog.basicErr("", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		
		return code;
	}
	
	/**
	 * @param playerId
	 *            设置玩家退出游戏惩罚次数
	 */
	public void setPunishTimeCounter(int playerId, long time) {
		String date = DateUtil.yyyyMMdd(time);
		String key = KEY_PLAYER_PUNISHTIME_COUNTER + playerId + date;
		String key1 = KEY_GRAFFITI_QUIT_TIME + playerId;
		
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			jedis.incr(key);
			jedis.set(key1, "" + time);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long getGraffitiQuitTime( int playerId ) {
		String key1 = KEY_GRAFFITI_QUIT_TIME + playerId;
		
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();
		
		try {
			return Long.valueOf(jedis.get( key1 ));
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	/**
	 * @param playerId
	 * @return 玩家退出游戏惩罚次数
	 */
	public int getPunishTimeCounter(int playerId) {
		String date = DateUtil.yyyyMMdd(System.currentTimeMillis());
		String key = KEY_PLAYER_PUNISHTIME_COUNTER + playerId + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			String value = jedis.get(key);
			
			return value == null ? 0 : Integer.valueOf(value);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 
	 * @param playerId
	 * @param time
	 */
	public boolean setQuitTime(long playerId, long time) {
		String key = KEY_QUITTIME + playerId;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.set(key, "" + time).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 
	 * @param playerId
	 * @return
	 */
	public long getQuitTime(long playerId) {
		String key = KEY_QUITTIME + playerId;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			String data = jedis.get(key);

			return data == null ? 0 : Long.valueOf(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean setAnyPay(String key1, byte[] array) {
		String key = KEY_ANY_PAY_STRUCT + key1;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.set(key.getBytes(), array).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public byte[] getAnyPay(String key1) {
		String key = KEY_ANY_PAY_STRUCT + key1;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes());

			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 
	 * @param activePlayerId
	 * @param passivePlayerId
	 * @return
	 */

	public boolean setSettlementGame(int activePlayerId, int passivePlayerId, String prevGameUUID) {
		String key = KEY_SETTLEMENT_READY_GAME + activePlayerId + "." + passivePlayerId + "_" + prevGameUUID;

		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();
		try {
			return jedis.setnx(key, "") == 0;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 
	 * @param activePlayerId
	 * @param passivePlayerId
	 * @return
	 */

	public long delSettlementgame(int activePlayerId, int passivePlayerId, String prevGameUUID) {
		String key = KEY_SETTLEMENT_READY_GAME + activePlayerId + "." + passivePlayerId + "_" + prevGameUUID;

		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();
		try {
			Long result = jedis.del(key);
			return result == null ? -1 : result;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean setRankByType(String date, GameDayRank gameDayRank) throws Exception {
		String key = KEY_RANGKBYTYPE_GAMECOUNT + date;

		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();
		try {
			return jedis.set(key.getBytes("UTF-8"), gameDayRank.toByteArray()).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public GameDayRank getRankByType() throws Exception {
		String date = DateUtil.yyyyMMdd(System.currentTimeMillis());

		String key = KEY_RANGKBYTYPE_GAMECOUNT + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes("UTF-8"));

			if (data == null) {
				return null;
			}

			return GameDayRank.parseFrom(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean hasPlayerName(String name) {

		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();

		try {
			return jedis.sadd(KEY_ONLY_NAME, name) == 0;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	public String createKey(int activeID, int passiveId, String date) {
		String key = null;
		if (activeID > passiveId) {
			key = activeID + "_" + passiveId + "_" + date;
		} else
			key = passiveId + "_" + activeID + "_" + date;

		return key;
	}

	public int addAndGetChatHomeId() {

		String key = KEY_CHAT_HOME_ID;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.incr(key).intValue();
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public int addGameCount(int activeID, int passiveID) {
		String Date = DateUtil.yyyyMMdd(System.currentTimeMillis());
		String key = this.createKey(activeID, passiveID, Date);

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.incr(key).intValue();
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean setGameDayIndexRank(String date, GameRankItemFirstList rankItemList) throws Exception {
		String key = KEY_GAMEINDEXRANK_DAY + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.set(key.getBytes("UTF-8"), rankItemList.toByteArray()).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				ProcessGlobalData.gameLog.basic("GameDayIndexRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public boolean setGameWeekIndexRank(String date, GameRankItemFirstList rankItemList) throws Exception {
		String key = KEY_GAMEINDEXRANK_WEEK + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.set(key.getBytes("UTF-8"), rankItemList.toByteArray()).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				ProcessGlobalData.gameLog.basic("GameWeekIndexRankKey" + " " + key);
				jedis.close();
			}
		}

	}

	public boolean setGameWeekRank(String date, GameType gameType, boolean sex, GameRankItemList rankItemList)
			throws Exception {
		String key = KEY_GAMERANK_WEEK + date + "." + gameType.getNumber() + "." + (sex ? "M" : "W");

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.set(key.getBytes("UTF-8"), rankItemList.toByteArray()).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				ProcessGlobalData.gameLog.basic("GameWeekRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public boolean setGameDayRank(String date, GameType gameType, boolean sex, GameRankItemList rankItemList)
			throws Exception {
		String key = KEY_GAMERANK_DAY + date + "." + gameType.getNumber() + "." + (sex ? "M" : "W");

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.set(key.getBytes("UTF-8"), rankItemList.toByteArray()).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				ProcessGlobalData.gameLog.basic("GameDayRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public GameRankItemList getGameWeekRank(String date, GameType gameType, boolean sex) throws Exception {

		String key = KEY_GAMERANK_WEEK + date + "." + gameType.getNumber() + "." + (sex ? "M" : "W");

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes("UTF-8"));

			if (data == null) {
				return null;
			}

			return GameRankItemList.parseFrom(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public GameRankItemList getGameDayRank(String date, GameType gameType, boolean sex) throws Exception {
		String key = KEY_GAMERANK_DAY + date + "." + gameType.getNumber() + "." + (sex ? "M" : "W");

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes("UTF-8"));

			if (data == null) {
				return null;
			}

			return GameRankItemList.parseFrom(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public GameRankItemFirstList getGameDayIndexRank(String date) throws Exception {
		String key = KEY_GAMEINDEXRANK_DAY + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes("UTF-8"));

			if (data == null) {
				return null;
			}

			return GameRankItemFirstList.parseFrom(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public GameRankItemFirstList getGameWeekIndexRank(String date) throws Exception {
		String key = KEY_GAMEINDEXRANK_WEEK + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes("UTF-8"));

			if (data == null) {
				return null;
			}

			return GameRankItemFirstList.parseFrom(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void deleteBlackOtherBefore(int playerId, int otherPlayerId) {

		//
		String key = KEY_BLACKOTHER_BEFORE + playerId;
		//
		JedisPool jedisPool = redisContext.getJedisPool();
		//
		Jedis jedis = jedisPool.getResource();

		try {
			jedis.hdel(key, "" + otherPlayerId);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 
	 * @param playerId
	 * @param otherPlayerId
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public void setBlackOtherBefore(int playerId, int otherPlayerId, long time, byte relationType) throws Exception {

		//
		String key = KEY_BLACKOTHER_BEFORE + playerId;
		//
		JedisPool jedisPool = redisContext.getJedisPool();
		//
		Jedis jedis = jedisPool.getResource();

		try {
			jedis.hset(key, "" + otherPlayerId, time + "_" + relationType);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 
	 * @param playerId
	 * @return
	 */
	public String getBlackOtherBefore(int playerId, int otherPlayerId) {
		//
		String key = KEY_BLACKOTHER_BEFORE + playerId;
		//
		JedisPool jedisPool = redisContext.getJedisPool();
		//
		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.hget(key, "" + otherPlayerId);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void setPlayerSex(int playerId) {
		String key = KEY_PLAYER_SEX + playerId;

		boolean flag = true;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			jedis.setbit(key, playerId, flag);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	public boolean getPlayerSex(int playerId) {
		String key = KEY_PLAYER_SEX + playerId;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.getbit(key, playerId);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	public boolean addBackGroundPhotos(int playerId, String photoIndex) {
		String key = KEY_BACKGROUND_PHOTOS + playerId;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.set(key, photoIndex).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public String getBackGroundPhotos(int playerId) {
		// TODO ZXF 管道获取
		String key = KEY_BACKGROUND_PHOTOS + playerId;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.get(key);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	public void addFuzzyMatch(int playerId, PlayerCardData playerCardData) {

		String Date = DateUtil.yyyyMMdd(System.currentTimeMillis());

		String key = KEY_PLAYER_SEARCHLIMIT_DAY + Date + playerId;

		String cardkey = "" + playerCardData.getPlayerID();

		JSONObject json = new JSONObject();

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		json.put("name", playerCardData.getName());
		json.put("head", playerCardData.getHead());
		json.put("playerId", playerCardData.getPlayerID());
		json.put("sex", playerCardData.getSex());

		try {
			jedis.hset(key, cardkey, json.toJSONString());
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	// 人找游戏
	public List<PlayerLeastData> getFuzzyMatch(int playerId) throws Exception {

		String Date = DateUtil.yyyyMMdd(System.currentTimeMillis());

		String key = KEY_PLAYER_SEARCHLIMIT_DAY + Date + playerId;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		ArrayList<PlayerLeastData> list = null;

		try {
			Map<String, String> map = jedis.hgetAll(key);
			if (map == null || map.isEmpty()) {
				return null;
			}
			list = new ArrayList<PlayerLeastData>(map.size());
			JSONObject json = null;

			PlayerLeastData.Builder dataBuilder = PlayerLeastData.newBuilder();
			for (String value : map.values()) {

				json = JSONObject.parseObject(value);

				dataBuilder.setName(json.getString("name"));
				dataBuilder.setHead(json.getString("head"));
				dataBuilder.setPlayerid(json.getInteger("playerId"));
				dataBuilder.setSex(json.getBoolean("sex"));
				list.add(dataBuilder.build());
			}

			return list;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void incrPlayerGameCount(int playerId, GameType gametype) throws Exception {
		String key = KEY_PLAYER_GAMECOUNT + playerId + "." + gametype.getNumber();

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {

			jedis.incr(key);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public int[] getPlayerGameCount(int playerId, GameType[] gameTypeArray) throws Exception {

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();
		String key = null;
		Pipeline pipeline = jedis.pipelined();

		try {
			Response<String>[] array = new Response[gameTypeArray.length];

			for (int i = 0; i < gameTypeArray.length; i++) {
				key = KEY_PLAYER_GAMECOUNT + playerId + "." + gameTypeArray[i].getNumber();

				array[i] = pipeline.get(key);
			}

			pipeline.sync();

			int[] result = new int[gameTypeArray.length];

			for (int i = 0; i < gameTypeArray.length; i++) {
				String result1 = array[i].get();

				result[i] = result1 == null ? 0 : Integer.valueOf(result1);
			}
			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void incrTotalGameCount(GameType gametype) throws Exception {
		String key = KEY_TOTAL_GAMECOUNT + "." + gametype.getNumber();

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			jedis.incr(key);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	/**
	 * 
	 * @param gametype
	 * @throws Exception
	 */
	public void decrTotalGameCount(GameType gametype) throws Exception {
		String key = KEY_TOTAL_GAMECOUNT + "." + gametype.getNumber();

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			jedis.decr(key);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	public void delTotalGameCount(GameType gametype) throws Exception {
		String key = KEY_TOTAL_GAMECOUNT + "." + gametype.getNumber();

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			jedis.del(key);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public int[] getTotalGameCount(GameType[] gameTypeArray) throws Exception {
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();
		String key = null;
		Pipeline pipeline = jedis.pipelined();

		try {
			Response<String>[] array = new Response[gameTypeArray.length];

			for (int i = 0; i < gameTypeArray.length; i++) {
				key = KEY_TOTAL_GAMECOUNT + "." + gameTypeArray[i].getNumber();

				array[i] = pipeline.get(key);
			}

			pipeline.sync();

			int[] result = new int[gameTypeArray.length];

			String result1 = null;

			for (int i = 0; i < gameTypeArray.length; i++) {
				result1 = array[i].get();
				result[i] = result1 == null ? 0 : Integer.valueOf(result1);
			}
			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 
	 * @param playerId
	 * @return
	 */
	public boolean addNearbyPeople(int playerId, long postion, double latitude, double longitude) {

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		JSONObject json = new JSONObject();

		json.put("playerId", playerId);
		json.put("latitude", latitude);
		json.put("longitude", longitude);

		// optional int32 playerID = 1;
		// // 昵称
		// optional string name = 2;
		// // 生日
		// optional string birthday = 3;
		// // 性别
		// optional bool sex = 4;
		// // 头像
		// optional string head = 5;
		// //
		// optional string city = 7;

		try {

			// 集合名
			// [pos, pid]
			return jedis.zadd(KEY_PREFIX_NEARBY, postion, json.toJSONString()) != 0;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean addMatchLog(int playerId, int othreadPlayerId, String head, String name, boolean sex) {
		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();

		JSONObject json = new JSONObject();

		json.put("id", othreadPlayerId);
		json.put("head", head);
		json.put("name", name);
		json.put("sex", sex);

		try {
			return jedis.lpush(KEY_PREFIX_MATCHLOG + playerId, json.toJSONString()) != 0;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public ArrayList<CBeanMatchLog> getMatchLog(int playerId) {

		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();

		try {
			List<String> resultList = jedis.lrange(KEY_PREFIX_MATCHLOG + playerId, 0, -1);

			if (resultList == null || resultList.isEmpty()) {
				return null;
			}

			ArrayList<CBeanMatchLog> matchLog = new ArrayList<CBeanMatchLog>(resultList.size());

			JSONObject json = null;

			for (String info : resultList) {
				json = JSONObject.parseObject(info);
				matchLog.add(new CBeanMatchLog().copyFrom(json));
			}

			return matchLog;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public Set<Tuple> getNearbyPeople(double latitude, double longitude, double range) {
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();
		List<GeoRange> geoRanges = GeoSearch.range(latitude, longitude, range);
		Response<Set<Tuple>>[] responseArray = new Response[geoRanges.size()];
		try {
			Pipeline pipeline = jedis.pipelined();
			for (int i = 0; i < geoRanges.size(); i++) {
				GeoRange geoRange = geoRanges.get(i);
				long min = geoRange.getMin();
				long max = geoRange.getMax();

				responseArray[i] = pipeline.zrangeByScoreWithScores(KEY_PREFIX_NEARBY, min, max);
			}

			pipeline.sync();
			HashSet<Tuple> resultTupleSet = new HashSet<Tuple>();

			for (int i = 0; i < responseArray.length; i++) {
				Response<Set<Tuple>> tempTupleResponse = responseArray[i];
				Set<Tuple> tempTupleSet = tempTupleResponse.get();
				resultTupleSet.addAll(tempTupleSet);
			}

			return resultTupleSet;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean delNearbyPeople(int playerId, double latitude, double longitude) {
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();
		JSONObject json = new JSONObject();

		json.put("playerId", playerId);
		json.put("latitude", latitude);
		json.put("longitude", longitude);
		try {
			return jedis.zrem(KEY_PREFIX_NEARBY, json.toJSONString()) != 0;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	public CBeanJoinFriendState[] getFriendStateList(int playerId) {
		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();

		try {

			List<String> resultList = jedis.hvals(KEY_PREFIX_FRIENDSTATELIST + playerId);

			if (resultList == null || resultList.isEmpty()) {
				return null;
			}

			CBeanJoinFriendState[] friendStateList = new CBeanJoinFriendState[resultList.size()];

			JSONObject json = null;

			int i = 0;

			for (String info : resultList) {
				json = JSONObject.parseObject(info);
				friendStateList[i++] = new CBeanJoinFriendState().copyFrom(json);
			}

			return friendStateList;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void removeFriendStateList(int playerId, int otherId) {

		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();

		try {
			String key = KEY_PREFIX_FRIENDSTATELIST + playerId;
			jedis.hdel(key, "" + otherId);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean updateFriendStateList(int playerId, int otherId, boolean dir, boolean state) {
		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();

		try {
			String key = KEY_PREFIX_FRIENDSTATELIST + playerId;

			String key1 = "" + otherId;

			String info = jedis.hget(key, key1);

			if (info == null) {
				return false;
			}

			JSONObject json = JSONObject.parseObject(info);
			CBeanJoinFriendState friendState = new CBeanJoinFriendState().copyFrom(json);
			friendState.setState(state);
			friendState.setDir(dir);
			JSONObject json1 = friendState.copyTo(json);

			jedis.hset(key, key1, json1.toJSONString());

			return true;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean addFriendStateList(int playerId, int otherPlayerId, boolean dir, boolean state, String head,
			String name, long time) {

		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();

		JSONObject json = new JSONObject();

		json.put("id", otherPlayerId);
		json.put("dir", dir);
		json.put("state", state);
		json.put("head", head);
		json.put("name", name);
		json.put("time", time);

		String key = KEY_PREFIX_FRIENDSTATELIST + playerId;
		String key1 = "" + otherPlayerId;

		try {
			return jedis.hset(key, key1, json.toJSONString()) != 0;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 返回这两个用户的对局记录胜负次数
	 * 
	 * @param playerIdA
	 * @param playerIdB
	 * @return
	 */
	public int[] getGFPCount(int playerIdA, int playerIdB) {
		if (playerIdA == playerIdB) {
			return null;
		}
		Jedis jedis = null;

		try {

			JedisPool jeidsPool = redisContext.getJedisPool();

			jedis = jeidsPool.getResource();

			String key1 = (playerIdA > playerIdB ? playerIdA + "_" + playerIdB : playerIdB + "_" + playerIdA);

			String bigKey = KEY_GFP_RESULT_WIN_BIG_COUNT + key1;
			String smallKey = KEY_GFP_RESULT_WIN_SMALL_COUNT + key1;

			Pipeline pipeline = jedis.pipelined();

			//
			Response<String> bigResponse = pipeline.get(bigKey);
			//
			Response<String> smallResponse = pipeline.get(smallKey);

			pipeline.sync();

			//
			int bigValue = bigResponse.get() == null ? 0 : Integer.valueOf(bigResponse.get());
			//
			int smallValue = smallResponse.get() == null ? 0 : Integer.valueOf(smallResponse.get());

			if (playerIdA > playerIdB) {
				return new int[] { bigValue, smallValue };
			}

			return new int[] { smallValue, bigValue };

		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 添加对局记录胜负次数
	 * 
	 * @param playerIdA
	 * @param playerIdB
	 * @param aWin
	 */
	public void addGFPCount(int playerIdA, int playerIdB, boolean aWin) {
		if (playerIdA == playerIdB) {
			return;
		}

		Jedis jedis = null;

		try {

			JedisPool jeidsPool = redisContext.getJedisPool();

			jedis = jeidsPool.getResource();

			String key1 = (playerIdA > playerIdB ? playerIdA + "_" + playerIdB : playerIdB + "_" + playerIdA);

			if (playerIdA > playerIdB) {
				if (aWin) {
					jedis.incr(KEY_GFP_RESULT_WIN_BIG_COUNT + key1);
				} else {
					jedis.incr(KEY_GFP_RESULT_WIN_SMALL_COUNT + key1);
				}
			} else {
				if (aWin) {
					jedis.incr(KEY_GFP_RESULT_WIN_SMALL_COUNT + key1);
				} else {
					jedis.incr(KEY_GFP_RESULT_WIN_BIG_COUNT + key1);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public Map<String, String> getBlackOtherBefore(int playerId) {
		//
		String key = KEY_BLACKOTHER_BEFORE + playerId;
		//
		JedisPool jedisPool = redisContext.getJedisPool();
		//
		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.hgetAll(key);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public int getGameCount(int activeID, int passiveID) {
		String Date = DateUtil.yyyyMMdd(System.currentTimeMillis());
		String key = KEY_TWOPLAYER_GAMECOUNT + this.createKey(activeID, passiveID, Date);
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return Integer.valueOf(jedis.get(key));
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public int[] getGFPCountToday(int playerIdA, int playerIdB) {
		if (playerIdA == playerIdB) {
			return null;
		}
		Jedis jedis = null;

		String Date = DateUtil.yyyyMMdd(System.currentTimeMillis());

		try {

			JedisPool jeidsPool = redisContext.getJedisPool();

			jedis = jeidsPool.getResource();

			String key = this.createKey(playerIdA, playerIdB, Date);

			String bigKey = KEY_GFP_RESULT_WIN_BIG_COUNT_TODAY + key;
			String smallKey = KEY_GFP_RESULT_WIN_SMALL_COUNT_TODAY + key;

			Pipeline pipeline = jedis.pipelined();

			//
			Response<String> bigResponse = pipeline.get(bigKey);
			//
			Response<String> smallResponse = pipeline.get(smallKey);

			pipeline.sync();

			//
			int bigValue = bigResponse.get() == null ? 0 : Integer.valueOf(bigResponse.get());
			//
			int smallValue = smallResponse.get() == null ? 0 : Integer.valueOf(smallResponse.get());
			if (playerIdA > playerIdB) {
				return new int[] { bigValue, smallValue };
			}

			return new int[] { smallValue, bigValue };

		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void addGFPCountToday(int playerIdA, int playerIdB, boolean aWin) {
		if (playerIdA == playerIdB) {
			return;
		}

		Jedis jedis = null;
		String Date = DateUtil.yyyyMMdd(System.currentTimeMillis());

		try {

			JedisPool jeidsPool = redisContext.getJedisPool();

			jedis = jeidsPool.getResource();

			// String key = (playerIdA > playerIdB ? playerIdA + "_" + playerIdB : playerIdB
			// + "_" + playerIdA);
			String key = this.createKey(playerIdA, playerIdB, Date);
			if (playerIdA > playerIdB) {
				if (aWin) {
					jedis.incr(KEY_GFP_RESULT_WIN_BIG_COUNT_TODAY + key);
				} else {
					jedis.incr(KEY_GFP_RESULT_WIN_SMALL_COUNT_TODAY + key);
				}
			} else {
				if (aWin) {
					jedis.incr(KEY_GFP_RESULT_WIN_SMALL_COUNT_TODAY + key);
				} else {
					jedis.incr(KEY_GFP_RESULT_WIN_BIG_COUNT_TODAY + key);
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void setRobatShowoff(int robatId, int charm, float gift, float consume) {
		String key = KEY_ROBAT_SHOWOFF_DATA + robatId;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {

			JSONObject json = new JSONObject();

			json.put("charm", charm);
			json.put("gift", gift);
			json.put("consume", consume);

			jedis.set(key, json.toJSONString());
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public CRobatShowoff getRobatShowoff(int robatId) {
		String key = KEY_ROBAT_SHOWOFF_DATA + robatId;
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			String value = jedis.get(key);

			if (value == null) {
				return new CRobatShowoff();
			}

			JSONObject json = JSONObject.parseObject(jedis.get(key));

			Integer charm = json.getInteger("charm");
			Float gift = json.getFloat("gift");
			Float consume = json.getFloat("consume");

			CRobatShowoff result = new CRobatShowoff();

			result.setCharm(charm == null ? 0 : charm);
			result.setGift(gift == null ? 0 : gift);
			result.setConsume(consume == null ? 0 : consume);

			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void updateRobatGiftItem(int robatId, int itemId, int itemCount) {
		String key = KEY_ROBAT_ITEM_DATA + robatId + "." + itemId;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			jedis.incrBy(key, itemCount);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public CRobatGiftItem[] getRobatGiftItem(int robatId, int[] itemIdArray) {
		String key = KEY_ROBAT_ITEM_DATA + robatId;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		CRobatGiftItem[] resultArray = null;

		try {
			resultArray = new CRobatGiftItem[itemIdArray.length];
			Pipeline pipeline = jedis.pipelined();

			Response<String>[] array = new Response[itemIdArray.length];

			for (int i = 0; i < itemIdArray.length; i++) {
				array[i] = pipeline.get(key + "." + itemIdArray[i]);
			}

			pipeline.sync();

			for (int i = 0; i < itemIdArray.length; i++) {
				if (array[i].get() == null) {
					continue;
				}

				resultArray[i] = new CRobatGiftItem();
				resultArray[i].setItemcount(Integer.valueOf(array[i].get()));
				resultArray[i].setItemid(itemIdArray[i]);
			}

			return resultArray;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void delGameWeekIndexRank(String date, GameRankItemFirstList rankItemList) throws Exception {
		String key = KEY_GAMEINDEXRANK_WEEK + date;
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			jedis.del(key.getBytes("UTF-8"));
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void delGameWeekRank(String date, GameType gameType, boolean sex) throws Exception {
		String key = KEY_GAMERANK_WEEK + date + "." + gameType.getNumber() + "." + (sex ? "M" : "W");
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(key.getBytes("UTF-8"));
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void delGameDayRank(String date, GameType gameType, boolean sex) throws Exception {
		String key = KEY_GAMERANK_DAY + date + "." + gameType.getNumber() + "." + (sex ? "M" : "W");
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(key.getBytes("UTF-8"));
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void delGameDayIndexRank(String date) throws Exception {
		String key = KEY_GAMEINDEXRANK_DAY + date;
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(key.getBytes("UTF-8"));
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean setCharmDayIndexRank(String date, CharmRankItemFirstList rankItemList) throws Exception {
		String key = KEY_CHARMINDEXRANK_DAY + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.set(key.getBytes("UTF-8"), rankItemList.toByteArray()).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("CharmDayIndexRankKey" + " " + key);
				// ProcessGlobalData.gameLog.basic("CharmDayIndexRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public boolean setCharmTotalIndexRank(String date, CharmRankItemFirstList rankItemList) throws Exception {
		String key = KEY_CHARMINDEXRANK_TOTAL + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.set(key.getBytes("UTF-8"), rankItemList.toByteArray()).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("CharmTotalIndexRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public boolean setCharmTotalRank(String date, boolean sex, CharmRankItemList rankItemList) throws Exception {
		String key = KEY_CHARMRANK_TOTAL + date + "." + (sex ? "M" : "W");

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.set(key.getBytes("UTF-8"), rankItemList.toByteArray()).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				ProcessGlobalData.gameLog.basic("CharmTotalRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public double setCharmTotalIncrement(boolean sex, int playerId, int Increment) {
		String key = KEY_PERSONALCHARM_TOTAL + "." + (sex ? "M" : "W");
		String player = "" + playerId;
		JedisPool jedisPool = redisContext.getJedisPool();
		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.zincrby(key, Increment, player);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				ProcessGlobalData.gameLog.basic("CharmTotalRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public void delCharmTotalIncrement(boolean sex, int playerId) {
		String key = KEY_PERSONALCHARM_TOTAL + "." + (sex ? "M" : "W");
		String player = "" + playerId;
		JedisPool jedisPool = redisContext.getJedisPool();
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.zrem(key, player);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				ProcessGlobalData.gameLog.basic("CharmTotalRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public double setCharmDayIncrement(String date, boolean sex, int playerId, int Increment) {
		String key = KEY_PERSONALCHARM_DAY + date + "." + (sex ? "M" : "W");
		String player = "" + playerId;
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.zincrby(key, Increment, player);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("CharmDayRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public void delCharmDayIncrement(String date, boolean sex, int playerId) {
		String key = KEY_PERSONALCHARM_DAY + date + "." + (sex ? "M" : "W");
		String player = "" + playerId;
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			jedis.zrem(key, player);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("CharmDayRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public Set<Tuple> getCharmTotalIncrement(boolean sex, int start, int end) {
		String key = KEY_PERSONALCHARM_TOTAL + "." + (sex ? "M" : "W");
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.zrevrangeWithScores(key, 0, 49);
			// jedis.zrevincrby(key, 0, 49);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("CharmDayRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public Set<Tuple> getCharmDayIncrement(String date, boolean sex, int start, int end) {
		String key = KEY_PERSONALCHARM_DAY + date + "." + (sex ? "M" : "W");
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.zrevrangeWithScores(key, start, end);
			// jedis.zrevincrby(key, 0, 49);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean setCharmDayRank(String date, boolean sex, CharmRankItemList rankItemList) throws Exception {
		String key = KEY_CHARMRANK_DAY + date + "." + (sex ? "M" : "W");

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.set(key.getBytes("UTF-8"), rankItemList.toByteArray()).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				ProcessGlobalData.gameLog.basic("CharmDayRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public CharmRankItemList getCharmTotalRank(String date, boolean sex) throws Exception {

		String key = KEY_CHARMRANK_TOTAL + date + "." + (sex ? "M" : "W");

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes("UTF-8"));

			if (data == null) {
				return null;
			}

			return CharmRankItemList.parseFrom(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public CharmRankItemList getCharmDayRank(String date, boolean sex) throws Exception {
		String key = KEY_CHARMRANK_DAY + date + "." + (sex ? "M" : "W");

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes("UTF-8"));

			if (data == null) {
				return null;
			}

			return CharmRankItemList.parseFrom(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public CharmRankItemFirstList getCharmDayIndexRank(String date) throws Exception {
		String key = KEY_CHARMINDEXRANK_DAY + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes("UTF-8"));

			if (data == null) {
				return null;
			}

			return CharmRankItemFirstList.parseFrom(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean setConsumeDayIndexRank(String date, ConsumeRankItemFirstList rankItemList) throws Exception {
		String key = KEY_CONSUMEINDEXRANK_DAY + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.set(key.getBytes("UTF-8"), rankItemList.toByteArray()).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("ConsumeDayIndexRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public boolean setConsumeWeekRank(String date, boolean sex, ConsumeRankItemList rankItemList) throws Exception {
		String key = KEY_CONSUMERANK_WEEK + date + "." + (sex ? "M" : "W");

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.set(key.getBytes("UTF-8"), rankItemList.toByteArray()).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				ProcessGlobalData.gameLog.basic("ConsumeWeekRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public boolean setConsumeDayRank(String date, boolean sex, ConsumeRankItemList rankItemList) throws Exception {
		String key = KEY_CONSUMERANK_DAY + date + "." + (sex ? "M" : "W");

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.set(key.getBytes("UTF-8"), rankItemList.toByteArray()).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				ProcessGlobalData.gameLog.basic("ConsumeDayRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public ConsumeRankItemList getConsumeWeekRank(String date, boolean sex) throws Exception {

		String key = KEY_CONSUMERANK_WEEK + date + "." + (sex ? "M" : "W");

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes("UTF-8"));

			if (data == null) {
				return null;
			}

			return ConsumeRankItemList.parseFrom(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public ConsumeRankItemList getConsumeDayRank(String date, boolean sex) throws Exception {
		String key = KEY_CONSUMERANK_DAY + date + "." + (sex ? "M" : "W");

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes("UTF-8"));

			if (data == null) {
				return null;
			}

			return ConsumeRankItemList.parseFrom(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public ConsumeRankItemFirstList getConsumeDayIndexRank(String date) throws Exception {
		String key = KEY_CONSUMEINDEXRANK_DAY + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes("UTF-8"));

			if (data == null) {
				return null;
			}

			return ConsumeRankItemFirstList.parseFrom(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public double setConsumeDayIncrement(String date, boolean sex, int playerId, int Increment) {
		String key = KEY_PERSONALCONSUME_DAY + date + "." + (sex ? "M" : "W");
		String player = "" + playerId;
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.zincrby(key, Increment, player);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				System.out.println(key);

				// ProcessGlobalData.gameLog.basic("CharmDayRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public void delConsumeDayIncrement(String date, boolean sex, int playerId) {
		String key = KEY_PERSONALCONSUME_DAY + date + "." + (sex ? "M" : "W");
		String player = "" + playerId;
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			jedis.zrem(key, player);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("CharmDayRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public Set<Tuple> getConsumeDayIncrement(String date, boolean sex, int start, int end) {
		String key = KEY_PERSONALCONSUME_DAY + date + "." + (sex ? "M" : "W");
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.zrevrangeWithScores(key, start, end);
			// jedis.zrevincrby(key, 0, 49);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("CharmDayRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public void setConsumeWeekIncrement(String date, boolean sex, String[] dateArray) {
		String key = KEY_PERSONALCONSUME_WEEK + date + "." + (sex ? "M" : "W");
		String[] daykeys = new String[7];
		for (int i = 0; i < dateArray.length; i++) {
			daykeys[i] = KEY_PERSONALCONSUME_DAY + dateArray[i] + "." + (sex ? "M" : "W");
		}

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			jedis.zunionstore(key, daykeys[0], daykeys[1], daykeys[2], daykeys[3], daykeys[4], daykeys[5], daykeys[6]);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("ConsumeWeekRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public void modifyToLastWeekConsume(long time, boolean sex, int playerId) {
		String[] dateArray = DateUtil.dayToLastWeek(time);
		double consume = 0;
		String date = null;
		for (int i = 0; i < dateArray.length; i++) {
			date = dateArray[i];
			consume = setConsumeDayIncrement(date, !sex, playerId, 0);
			if (consume != 0) {
				delConsumeDayIncrement(date, !sex, playerId);
				setConsumeDayIncrement(date, sex, playerId, (int) consume);
			}

		}

	}

	public Set<Tuple> getConsumeWeekIncrement(String date, boolean sex, int start, int end) {
		String key = KEY_PERSONALCONSUME_WEEK + date + "." + (sex ? "M" : "W");
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.zrevrangeWithScores(key, start, end);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean setConsumeWeekIndexRank(String date, ConsumeRankItemFirstList rankItemList) throws Exception {
		String key = KEY_CONSUMEINDEXRANK_WEEK + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.set(key.getBytes("UTF-8"), rankItemList.toByteArray()).equals(RedisContext.OK);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("ConsumeWeekIndexRankKey" + " " + key);
				jedis.close();
			}
		}

	}

	public ConsumeRankItemFirstList getConsumeWeekIndexRank(String date) throws Exception {
		String key = KEY_CONSUMEINDEXRANK_WEEK + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes("UTF-8"));

			if (data == null) {
				return null;
			}

			return ConsumeRankItemFirstList.parseFrom(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public CharmRankItemFirstList getCharmTotalIndexRank(String date) throws Exception {
		String key = KEY_GAMEINDEXRANK_WEEK + date;

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			byte[] data = jedis.get(key.getBytes("UTF-8"));

			if (data == null) {
				return null;
			}

			return CharmRankItemFirstList.parseFrom(data);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public String getPlayerName(int playerID) throws Exception {
		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();

		try {
			return jedis.get("P.Name." + playerID);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	public void setPlayerToken(int playerID, String token) throws Exception {
		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();

		try {
			jedis.set(KEY_TOKEN + playerID, token);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public int incrPlayerID() throws Exception {
		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();

		try {

			int result = jedis.incr("PlayerID").intValue();
//			System.out.println("INCR PlayerID " + result);
			if (Integer.MAX_VALUE <= result) {
				throw new Exception("Bound Check-1");
			} else if (Integer.MAX_VALUE - result < CommonConstantContext.PLAYERID_OFFSET) {
				throw new Exception("Bound Check-2");
			} else {
				return result + CommonConstantContext.PLAYERID_OFFSET;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	public String getPlayerToken(int playerID) throws Exception {
		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();

		try {
			return jedis.get(KEY_TOKEN + playerID);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void setPlayerName(int playerID, String name) throws Exception {
		JedisPool jeidsPool = redisContext.getJedisPool();

		Jedis jedis = jeidsPool.getResource();

		try {
			jedis.set("P.Name." + playerID, name);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void setRoomPeopleCounter(int node, int Increment) {
		String nodeId = "" + node;
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			jedis.zincrby(KEY_ROOM_PEOPLE_COUNTER, Increment, nodeId);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public Set<Tuple> getRoomPeopleCounter(int start, int end) {
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {

			return jedis.zrevrangeWithScores(KEY_ROOM_PEOPLE_COUNTER, start, end);
			//
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	// 游戏榜 日榜
	public double setGameDayIncrement(String date, boolean sex, int playerId, int Increment, int gameType) {
		String key = KEY_PERSONALGAME_DAY + date + "." + gameType + "." + (sex ? "M" : "W");
		String player = "" + playerId;
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.zincrby(key, Increment, player);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("CharmDayRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public void delGameDayIncrement(String date, boolean sex, int playerId, int gameType) {
		String key = KEY_PERSONALGAME_DAY + date + "." + gameType + "." + (sex ? "M" : "W");
		String player = "" + playerId;
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			jedis.zrem(key, player);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("CharmDayRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public Set<Tuple> getGameDayIncrement(String date, boolean sex, int start, int end, GameType gameType) {
		String key = KEY_PERSONALGAME_DAY + date + "." + gameType.getNumber() + "." + (sex ? "M" : "W");
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.zrevrangeWithScores(key, start, end);
			// jedis.zrevincrby(key, 0, 49);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("CharmDayRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	// 游戏榜 周榜
	public void setGameWeekIncrement(String date, boolean sex, String[] dateArray, GameType gameType) {
		String key = KEY_PERSONALGAME_WEEK + date + "." + (sex ? "M" : "W");
		String[] daykeys = new String[7];
		for (int i = 0; i < dateArray.length; i++) {

			daykeys[i] = KEY_PERSONALGAME_DAY + dateArray[i] + "." + gameType.getNumber() + "." + (sex ? "M" : "W");
		}

		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			jedis.zunionstore(key, daykeys[0], daykeys[1], daykeys[2], daykeys[3], daykeys[4], daykeys[5], daykeys[6]);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				// ProcessGlobalData.gameLog.basic("ConsumeWeekRankKey" + " " + key);
				jedis.close();
			}
		}
	}

	public void modifyToLastWeekGame(long time, boolean sex, int playerId) {
		String[] dateArray = DateUtil.dayToLastWeek(time);
		double count = 0;
		String date = null;
		int gameValue = 0;
		for (int i = 0, length = GameType.values().length; i < length; i++) {
			gameValue = GameType.valueOf(i).getNumber();
			for (int j = 0, dateLength = dateArray.length; j < dateLength; j++) {
				date = dateArray[j];
				count = setGameDayIncrement(date, !sex, playerId, 0, gameValue);
				if (count != 0) {
					delGameDayIncrement(date, !sex, playerId, gameValue);
					setGameDayIncrement(date, sex, playerId, (int) count, gameValue);
				}
			}
		}

	}

	public Set<Tuple> getGameWeekIncrement(String date, boolean sex, int start, int end) {
		String key = KEY_PERSONALGAME_WEEK + date + "." + (sex ? "M" : "W");
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();

		try {
			return jedis.zrevrangeWithScores(key, start, end);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public boolean isWelMSG( long playerId ) {
		
		String key = KEY_WELCOME + playerId;
		JedisPool jedisPool = redisContext.getJedisPool();

		Jedis jedis = jedisPool.getResource();
		
		try {
			return jedis.setnx(key, "L") == 1L;
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}
