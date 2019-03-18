
package com.game.framework.log;

import com.game.framework.util.ThreadUtil;

class AsyncLogExceptionStruct_LogicErr extends AsyncLogExceptionStruct {

	public AsyncLogExceptionStruct_LogicErr(Thread thread, long nowTime, String message, Throwable e) {
		this.message = message;
		this.e = e;

		saveLogThread = thread;
		saveLogTime = nowTime;
	}

	public AsyncLogExceptionStruct_LogicErr(Thread thread, long nowTime, String funname, String message, Throwable e) {
		this.message = message;
		this.e = e;
		this.funname = funname;

		saveLogThread = thread;
		saveLogTime = nowTime;
	}

	@Override
	public void saveLog() {
		
		if (funname == null) {
			LogFacade.logicErr(saveLogTime + "#"
					+ ThreadUtil.toString1(Thread.currentThread()) + "##" + message, e);
		} else {
			LogFacade.logicErr(saveLogTime + "#"
					+ ThreadUtil.toString1(Thread.currentThread()) + "#" + funname + "#" + message, e);
		}

	}

}
