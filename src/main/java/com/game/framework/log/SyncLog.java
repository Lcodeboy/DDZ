package com.game.framework.log;

/**
 * 
 * @author suchen
 * @date 2018年12月28日下午4:00:34
 */
public class SyncLog implements GameLog {

	@Override
	public void logicErr(String message, Throwable e) {
		LogFacade.logicErr(message, e);
	}

	@Override
	public void logicErr(String message) {
		LogFacade.logicErr(message);
	}

	@Override
	public void basicErr(String message, Throwable e) {
		LogFacade.basicErr(message, e);
	}

	@Override
	public void basicErr(String message) {
		LogFacade.basicErr(message);	
	}

	@Override
	public void basic(String message) {
		LogFacade.basic(message);
	}

	@Override
	public void logic(String message) {
		LogFacade.logic(message);
	}

	@Override
	public void waring(String message) {
		LogFacade.waring(message);
	}

	@Override
	public void debug(String message) {
		LogFacade.waring(message);
	}

}
