package com.game.framework.log;

abstract class AsyncLogStruct {
	
	protected String message = null;
	
	protected Thread saveLogThread = null;
	
	protected String funname = null;
	
	protected long saveLogTime = 0;
	
	public abstract void saveLog();
	
}
