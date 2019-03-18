package com.game.framework;

import com.game.framework.boot.ConfigDirAddressingStrategy;
import com.game.framework.boot.SysEnvConfigDirAddressingStrategy;
import com.game.framework.boot.UserConfigDirAddressingStrategy;
import com.game.framework.cache.redis.RedisContext;
import com.game.framework.cache.redis.RedisContextFacade;
import com.game.framework.concurrent.ThreadLocalObject;
import com.game.framework.log.GameLog;
import com.game.framework.log.SyncLog;
import com.game.framework.process.GameProcess;
import com.game.framework.room.GameRoom;
import com.game.framework.util.IniFileReader;
import com.google.protobuf.GeneratedMessage;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 *  进程级共享的全局数据, 应用层可继承此类, 对其进行扩展.
 */
public class ProcessGlobalData {
		
	/** Spring 的上下文 */ // 应用方赋值
	public static ApplicationContext appCTX = null;
	/** 进程ID */ 
	public static int processID = 0;
	/** 主函数的参数数组 */ // 应用方赋值
	public static String[] mainParam = null;
	/** 程序的入口目录 */
	public static File userDir;
	/** 操作系统类型的枚举(是Win还是Linux) */
	public static OSPlatform osPlatform;
	/** 创建此进程的时间 */
	public static long createProcessTime;
	/** JVM的运行时系统的管理接口 */
	public static RuntimeMXBean runMXBean;
	/** 初始化CPU的核心数量 */
	public static int cpuCount;
	/** 线程本地变量 */
	private static ThreadLocal<ThreadLocalObject> threadLocal = new ThreadLocal<ThreadLocalObject>();
	/** 配置文件根目录的寻址策略 */
	public static ConfigDirAddressingStrategy[] configDirAddressingStrategyArray = new ConfigDirAddressingStrategy[OSPlatform
			.values().length];
	/** 计划任务 */ // 应用方赋值
	public static ScheduledExecutorService appLVScheduledExecutor = null;
	/** 子任务线程池 */ // 应用方赋值
	public static Executor appLVSSubtaskExecutor = null;
	/** 心跳任务 */ // 应用方赋值
	public static Executor appLVSKeepTaskExecutor = null;
	/** 业务逻辑的线程池 */ // 应用方赋值
	public static Executor appLVSLogicProcessorExecutor = null;
	/** MMO场景TICK线程池 */ // 应用方赋值
	public static Executor appLVSceneTickProcessorExecutor = null;
	/** MQ的处理线程 */ // 应用方赋值
	public static Executor appLVRockMQProcessorExecutor = null;
	/** ROOM的处理线程 */ // 应用方赋值
	public static Executor appLVRoomTickProcessorExecutor = null;
	/** 本地变量的索引表 */ // 应用方赋值
	public static int THREADLOCAL_MAX_VALUE = 0;
	/** 需要创建实例 */ // 应用方赋值
	public static IniFileReader configReader = null;
	/** 需要创建实例 */
//	private static AsyncLog asyncLog = new AsyncLog();
	/** */
	private static SyncLog syncLog = new SyncLog();
	/** 开启心跳	*/
	public static volatile boolean openKeenalive = true;
	/** 默认是10帧每一秒 */
	public static int sceneTickTimeOut = 1000 / 10;
	/** 默认是10帧每一秒 */
	public static int roomTickTimeOut = 1000 / 10;
	
	public static GameLog gameLog = null;
	/** Redis 入口 */
	public static RedisContext redisContext = null;
	/** Redis 入口 */
	public static RedisContextFacade redisContextFacade = null;

	public static GameProcess gameProcess = null;

	public static File excelExportTXTDir = null;

	public static HashMap<String, GameRoom>[] gameRoomCache = null;
	
	public static AtomicInteger[] gameRoomCounter = null;
	
	public static void registerConfigDirAddressingStrategy(OSPlatform osPlatform,
			ConfigDirAddressingStrategy strategy) {
		configDirAddressingStrategyArray[osPlatform.num] = strategy;
	}

	public static ConfigDirAddressingStrategy getConfigDirAddressingStrategy() {
		return configDirAddressingStrategyArray[osPlatform.num];
	}

	public static void initAll(String iniFile, String section, String appCtx) throws Exception {

		// ////////////////////////////////////////////////////
		// 初始化 系统启动时间 createProcessTime
		// ////////////////////////////////////////////////////

		createProcessTime = System.currentTimeMillis();

		// 初始化配置文件寻址策略

		// 由于在Windows/MAC上开发居多所以采用环境变量, 因为每个人的IDE的user.dir不一样
		configDirAddressingStrategyArray[OSPlatform.Linux.num] = configDirAddressingStrategyArray[OSPlatform.Mac_OS.num] = configDirAddressingStrategyArray[OSPlatform.Mac_OS_X.num] = configDirAddressingStrategyArray[OSPlatform.Windows.num] = new SysEnvConfigDirAddressingStrategy();

		// 在Linux&Unix上运行居多, 所以通过user.dir
		configDirAddressingStrategyArray[OSPlatform.FreeBSD.num] = configDirAddressingStrategyArray[OSPlatform.HP_UX.num] = configDirAddressingStrategyArray[OSPlatform.Solaris.num] = new UserConfigDirAddressingStrategy();

		// /////////////////////////////////////////////////////
		// 初始化 用户目录 user.dir
		// /////////////////////////////////////////////////////

		userDir = new File(System.getProperty("user.dir"));

		// /////////////////////////////////////////////////////
		// 初始化 操作系统枚举 osPlatform
		// /////////////////////////////////////////////////////

		String OS = System.getProperty("os.name").toLowerCase();

		if (isAix(OS)) {
			osPlatform = OSPlatform.AIX;
		} else if (isDigitalUnix(OS)) {
			osPlatform = OSPlatform.Digital_Unix;
		} else if (isFreeBSD(OS)) {
			osPlatform = OSPlatform.FreeBSD;
		} else if (isHPUX(OS)) {
			osPlatform = OSPlatform.HP_UX;
		} else if (isIrix(OS)) {
			osPlatform = OSPlatform.Irix;
		} else if (isLinux(OS)) {
			osPlatform = OSPlatform.Linux;
		} else if (isMacOS(OS)) {
			osPlatform = OSPlatform.Mac_OS;
		} else if (isMacOSX(OS)) {
			osPlatform = OSPlatform.Mac_OS_X;
		} else if (isMPEiX(OS)) {
			osPlatform = OSPlatform.MPEiX;
		} else if (isNetWare(OS)) {
			osPlatform = OSPlatform.NetWare_411;
		} else if (isOpenVMS(OS)) {
			osPlatform = OSPlatform.OpenVMS;
		} else if (isOS2(OS)) {
			osPlatform = OSPlatform.OS2;
		} else if (isOS390(OS)) {
			osPlatform = OSPlatform.OS390;
		} else if (isOSF1(OS)) {
			osPlatform = OSPlatform.OSF1;
		} else if (isSolaris(OS)) {
			osPlatform = OSPlatform.Solaris;
		} else if (isSunOS(OS)) {
			osPlatform = OSPlatform.SunOS;
		} else if (isWindows(OS)) {
			osPlatform = OSPlatform.Windows;
		} else {
			osPlatform = OSPlatform.Others;
		}

		File configDir = getConfigDirAddressingStrategy().getConfigDir();

		// ////////////////////////////////////////////////////
		// 初始化异步日志接口
		// ////////////////////////////////////////////////////

		File logConfig = new File(configDir, section + ".log4j.properties");

		PropertyConfigurator.configure(new FileInputStream(logConfig));

//		asyncLog.start();
		gameLog = syncLog;

		gameLog.basic("ProcessGlobalData Bootstrap Start");
		gameLog.basic("ImportantEnv GAME_EXCEL_EXPORTTXT " + System.getenv(ImportantEnv.GAME_EXCEL_EXPORTTXT));
		gameLog.basic("ImportantEnv GAME_REDIS_ADDRESS " + System.getenv(ImportantEnv.GAME_REDIS_ADDRESS));
		gameLog.basic("ImportantEnv GAME_REDIS_PASSWORD " + System.getenv(ImportantEnv.GAME_REDIS_PASSWORD));
		gameLog.basic("ImportantEnv GAME_REDIS_PORT " + System.getenv(ImportantEnv.GAME_REDIS_PORT));
		gameLog.basic("ImportantEnv REDIS_PREFIX " + System.getenv(ImportantEnv.REDIS_PREFIX));
		// /////////////////////////////////////////////////////
		// 初始化 JVM的运行时系统的管理接口
		// /////////////////////////////////////////////////////

		runMXBean = ManagementFactory.getRuntimeMXBean();

		// /////////////////////////////////////////////////////
		// 初始化 CPU核心数量
		// /////////////////////////////////////////////////////

		cpuCount = Runtime.getRuntime().availableProcessors();

		File configReaderFile = new File(configDir, iniFile);

		gameLog.basic("Init GlobalConfig " + configReaderFile);

		// 初始化进程级的配置文件

		configReader = new IniFileReader(configReaderFile);
		configReader.reader();

		boolean appCTXClassPath = configReader.getBooleanParam(section, "appCTXClassPath", true);

		gameLog.basic("Init Spring AppCTX " + appCtx + " USEClassPath " + appCTXClassPath + " ConfigDir " + configDir);

		// 初始化Spring
		if (appCTXClassPath) {
			appCTX = new ClassPathXmlApplicationContext(appCtx);
		} else {
			int startIndex = appCtx.lastIndexOf("/");
			File appCTXFile = new File(configDir, appCtx.substring(startIndex + 1));
			String appCTXFileStr = "file:" + appCTXFile.toString();
			appCTX = new FileSystemXmlApplicationContext(appCTXFileStr);
		}

		boolean redisClient = configReader.getBooleanParam(section, "openRedis", false);

		if (redisClient) {
			// RedisContext
			redisContext = new RedisContext();
		}

		gameLog.basic("Spring AppCTX " + appCTX.getApplicationName());

		gameLog.basic("ProcessGlobalData Bootstrap End");

	}

	private static boolean isLinux(String OS) {
		return OS.indexOf("linux") >= 0;
	}

	private static boolean isMacOS(String OS) {
		return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
	}

	private static boolean isMacOSX(String OS) {
		return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
	}

	private static boolean isWindows(String OS) {
		return OS.indexOf("windows") >= 0;
	}

	private static boolean isOS2(String OS) {
		return OS.indexOf("os/2") >= 0;
	}

	private static boolean isSolaris(String OS) {
		return OS.indexOf("solaris") >= 0;
	}

	private static boolean isSunOS(String OS) {
		return OS.indexOf("sunos") >= 0;
	}

	private static boolean isMPEiX(String OS) {
		return OS.indexOf("mpe/ix") >= 0;
	}

	private static boolean isHPUX(String OS) {
		return OS.indexOf("hp-ux") >= 0;
	}

	private static boolean isAix(String OS) {
		return OS.indexOf("aix") >= 0;
	}

	private static boolean isOS390(String OS) {
		return OS.indexOf("os/390") >= 0;
	}

	private static boolean isFreeBSD(String OS) {
		return OS.indexOf("freebsd") >= 0;
	}

	private static boolean isIrix(String OS) {
		return OS.indexOf("irix") >= 0;
	}

	private static boolean isDigitalUnix(String OS) {
		return OS.indexOf("digital") >= 0 && OS.indexOf("unix") > 0;
	}

	private static boolean isNetWare(String OS) {
		return OS.indexOf("netware") >= 0;
	}

	private static boolean isOSF1(String OS) {
		return OS.indexOf("osf1") >= 0;
	}

	private static boolean isOpenVMS(String OS) {
		return OS.indexOf("openvms") >= 0;
	}

	public static enum OSPlatform {
		Others(0, "Others"), Linux(1, "Linux"), Windows(2, "Windows"), Mac_OS(3, "Mac OS"), Mac_OS_X(4,
				"Mac OS X"), Solaris(5, "Solaris"), SunOS(6, "SunOS"), FreeBSD(7, "FreeBSD"), Any(8,
						"any"), OS2(9, "OS/2"), MPEiX(10, "MPE/iX"), HP_UX(11, "HP-UX"), AIX(12, "AIX"), OS390(13,
								"OS/390"), Irix(14, "Irix"), Digital_Unix(15, "Digital Unix"), NetWare_411(16,
										"NetWare"), OSF1(17, "OSF1"), OpenVMS(18, "OpenVMS");

		private OSPlatform(int num, String desc) {
			this.description = desc;
			this.num = num;
		}

		public String toString() {
			return description;
		}

		public int getNum() {
			return num;
		}

		private int num;

		private String description;
	}

//	public static ThreadLocalObject getAndCreateThreadLocal(int size) {
//		ThreadLocalObject threadLocalObject = threadLocal.get();
//
//		if (threadLocalObject == null) {
//			threadLocal.set(new ThreadLocalObject(size));
//		}
//
//		return threadLocalObject;
//	}

	public static void createThreadLocal(int size) {
		threadLocal.set(new ThreadLocalObject(size));
	}

	public static Object getThreadLocalValue(int index) {
		return threadLocal.get().getValue(index);
	}

	public static GeneratedMessage.Builder<?> getThreadLocalMSG(int index) {
		GeneratedMessage.Builder<?> MSG = (GeneratedMessage.Builder<?>) threadLocal.get().getValue(index);
		MSG.clear();
		return MSG;
	}

	public static void setThreadLocalValue(int index, Object obj) {
		threadLocal.get().setValue(index, obj);
	}

	private static GameRoom[] EMPTY_GAMEROOM_ARRAY = new GameRoom[0];

	public static GameRoom[] getAllGameRoom(int index) {
		Collection<GameRoom> gameRoomCollection = gameRoomCache[index].values();

		if (gameRoomCollection.isEmpty()) {
			return EMPTY_GAMEROOM_ARRAY;
		}
		return gameRoomCollection.toArray(new GameRoom[gameRoomCollection.size()]);
	}

	public static GameRoom getGameRoom(String gameUUID, int index) {
		return gameRoomCache[index].get(gameUUID);
	}

	public static void putGameRoom(String gameUUID, int index, GameRoom gameRoom) {
		gameRoomCache[index].put(gameUUID, gameRoom);
		gameRoomCounter[gameRoom.getGameTypeValue()].incrementAndGet();
	}

	public static void removeGameRoom(String gameUUID, int index) {
		GameRoom gameRoom = gameRoomCache[index].remove(gameUUID);
		if( gameRoom != null ) {
			gameRoomCounter[gameRoom.getGameTypeValue()].decrementAndGet();
		}
	}

	public static class ImportantEnv {

		public static final String REDIS_PREFIX = "REDIS_PREFIX";

		public static final String GAME_REDIS_ADDRESS = "GAME_REDIS_ADDRESS";

		public static final String GAME_REDIS_PORT = "GAME_REDIS_PORT";

		public static final String GAME_REDIS_PASSWORD = "GAME_REDIS_PASSWORD";

		public static final String GAME_EXCEL_EXPORTTXT = "GAME_EXCEL_EXPORTTXT";
	}

}
