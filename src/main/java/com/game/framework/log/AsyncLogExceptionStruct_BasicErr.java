
package com.game.framework.log;

import com.game.framework.util.ThreadUtil;

class AsyncLogExceptionStruct_BasicErr extends AsyncLogExceptionStruct {
	public AsyncLogExceptionStruct_BasicErr(Thread thread, long nowTime, String message, Throwable e) {
		this.message = message;
		this.e = e;

		saveLogThread = thread;
		saveLogTime = nowTime;
	}

	public AsyncLogExceptionStruct_BasicErr(Thread thread, long nowTime, String funname, String message, Throwable e) {
		this.message = message;
		this.e = e;
		this.funname = funname;

		saveLogThread = thread;
		saveLogTime = nowTime;
	}

	@Override
	public void saveLog() {
		
		if (funname == null) {
			LogFacade.basicErr(saveLogTime + "#"
					+ ThreadUtil.toString1(Thread.currentThread()) + "##" + message, e);
		} else {
			LogFacade.basicErr(saveLogTime + "#"
					+ ThreadUtil.toString1(Thread.currentThread()) + "#" + funname + "#" + message, e);
		}
	}

}
