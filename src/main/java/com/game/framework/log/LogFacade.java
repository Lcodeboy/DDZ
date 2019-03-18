/**
 * Copyright: Copyright (c) 2016
 * Company:CYOU
 *
 * @Title: LogFacade.java
 * @Package com.game.framework.log 
 * @author chen.su
 * @date 2017年11月15日 下午3:59:22
 * @version 
 */
package com.game.framework.log;

import org.apache.log4j.Logger;

/**
 * @Description:
 * @author chen.su
 * @date 2017年11月15日 下午3:59:22
 */
public final class LogFacade {
	
	private LogFacade() {
		
	}
	
	// /////////////////////////////////
	// log4j 配置
	// /////////////////////////////////
	
	/** 底层日志 */
	private static final Logger basicLog = Logger.getLogger("basic");
	/** 底层错误日志 */
	private static final Logger basicErrLog = Logger.getLogger("basicError");
	
	/** 游戏的业务逻辑日志 	*/
	private static final Logger logicLog = Logger.getLogger("logic");
	/** 游戏的业务逻辑错误日志 	*/
	private static final Logger logicErrorLog = Logger.getLogger("logicErr");
	
	/** 警告				*/
	private static final Logger waringLog = Logger.getLogger("waring");
	/** DEBUG				*/
	private static final Logger debugLog = Logger.getLogger("debug");
	
	public static void debug( String message ) {
		debugLog.info(message);
	}
	
	public static void basic( String message ) {
		basicLog.info(message);
	}
	
	public static void basicErr(String message) {
		basicErrLog.error(message);
	}
	
	public static void basicErr(String message, Throwable e) {
		basicErrLog.error(message, e);
	}

	public static void logic(String message) {
		logicLog.info(message);
	}
	
	public static void logicErr(String message) {
		logicErrorLog.error(message);
	}
	
	public static void logicErr(String message, Throwable e) {
		logicErrorLog.error(message, e);
	}
	
	public static void waring(String message) {
		waringLog.info(message);
	}

}
