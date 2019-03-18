/**
 * Copyright: Copyright (c) 2016
 * Company:CYOU
 *
 * @Title: AsyncLogStruct_GameInfo.java
 * @Package com.game.framework.log.async 
 * @author chen.su
 * @date 2017年11月15日 下午3:56:43
 * @version 
 */
package com.game.framework.log;

import com.game.framework.util.ThreadUtil;

/**
 * @Description:
 * @author chen.su
 * @date 2017年11月15日 下午3:56:43
 */
class AsyncLogStruct_LogicErr extends AsyncLogStruct {
	public AsyncLogStruct_LogicErr(Thread thread, long nowTime, String message) {
		this.message = message;

		saveLogThread = thread;
		saveLogTime = nowTime;
	}

	public AsyncLogStruct_LogicErr(Thread thread, long nowTime, String funname, String message) {
		this.message = message;
		this.funname = funname;

		saveLogThread = thread;
		saveLogTime = nowTime;
	}

	@Override
	public void saveLog() {
		
		if (funname == null) {
			LogFacade.logicErr(saveLogTime + " [" + 
					ThreadUtil.toString1(saveLogThread) + "] $ " +
					message);
		} else {
			LogFacade.logicErr(saveLogTime + " [" + 
					ThreadUtil.toString1(saveLogThread) + "] $ " +
					funname + " " +
					message);
		}
	}

}
