package com.game.framework.cache.redis;

import com.game.framework.ProcessGlobalData;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
public final class RedisContext {

	private JedisPool redisPool = null;
	
	public static final String OK = "OK";
	
	public JedisPool getJedisPool() {
		return redisPool;
	}
	
	public RedisContext() {
		JedisPoolConfig config = new JedisPoolConfig();
		
		
		config.setBlockWhenExhausted(true);

		config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

		config.setJmxEnabled(true);

		config.setJmxNamePrefix("pool");

		config.setLifo(true);

		config.setMaxIdle(8);

		config.setMaxTotal(8);

		config.setMaxWaitMillis(100000);
		
		config.setMinEvictableIdleTimeMillis(1800000);

		config.setMinIdle(0);
		//	设置检测线程每次检测的对象数
		config.setNumTestsPerEvictionRun(-1);

		config.setSoftMinEvictableIdleTimeMillis(1800000);
		//	设置检测线程的运行时间间隔
		config.setTimeBetweenEvictionRunsMillis(60000);
		
		config.setTestWhileIdle(true);
		
		config.setTestOnBorrow(false);
		
		//	testWhileIdle、testOnBorrow为True，分别表示 在空闲时检查有效性、在获取连接的时候检查有效性 检查到无效连接时，会清理掉无效的连接，并重新获取新的连接。
		
		config.setTestOnCreate(false);
		
		config.setTestOnReturn(false);
		
		String redisAddress = System.getenv(ProcessGlobalData.ImportantEnv.GAME_REDIS_ADDRESS);

		if (redisAddress == null || redisAddress.isEmpty()) {
			redisAddress = "192.168.1.83";
		}
		
		String redisPort = System.getenv(ProcessGlobalData.ImportantEnv.GAME_REDIS_PORT);
		int port = 0;
		
		if (redisPort == null || redisPort.isEmpty()) {
			port = 6379;
		} else {
			port = Integer.valueOf( redisPort );
		}
		
		String redisPassword = System.getenv(ProcessGlobalData.ImportantEnv.GAME_REDIS_PASSWORD);
		
		if( redisPassword == null ) {
			redisPool = new JedisPool(config, redisAddress, port);
		} else {
			redisPool = new JedisPool(config, redisAddress, port, 10000, redisPassword);
		}
		
		
	}
	
}
