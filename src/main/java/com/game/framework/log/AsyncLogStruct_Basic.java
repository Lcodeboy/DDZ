package com.game.framework.log;

import com.game.framework.util.ThreadUtil;

class AsyncLogStruct_Basic extends AsyncLogStruct {
	public AsyncLogStruct_Basic(Thread thread, long nowTime, String message) {
		this.message = message;

		saveLogThread = thread;
		saveLogTime = nowTime;
	}

	public AsyncLogStruct_Basic(Thread thread, long nowTime, String funname, String message) {
		this.message = message;
		this.funname = funname;

		saveLogThread = thread;
		saveLogTime = nowTime;
	}

	@Override
	public void saveLog() {
		
		if (funname == null) {
			LogFacade.basic(saveLogTime + " [" + 
					ThreadUtil.toString1(saveLogThread) + "] $ " +
					message);
		} else {
			LogFacade.basic(saveLogTime + " [" + 
					ThreadUtil.toString1(saveLogThread) + "] $ " +
					funname + " " +
					message);
		}
	}

}
