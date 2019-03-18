package com.game.framework.log;

/**
 * 
 * @author suchen
 * @date 2017年12月10日下午12:24:03
 *
 */
public interface GameLog {
	public void logicErr(String message, Throwable e);

	public void logicErr(String message);

	public void basicErr(String message, Throwable e);

	public void basicErr(String message);

	public void basic(String message);

	public void logic(String message);

	public void waring(String messsage);

	public void debug(String messsage);

}
