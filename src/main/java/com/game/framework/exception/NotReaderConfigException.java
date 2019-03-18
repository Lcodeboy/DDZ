/**
 * Copyright: Copyright (c) 2016
 * Company:CYOU
 *
 * @Title: ConfigReaderException.java
 * @Package com.game.framework.basic 
 * @author chen.su
 * @date 2017年11月9日 下午1:55:38
 * @version 
 */
package com.game.framework.exception;

public class NotReaderConfigException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 */
	public NotReaderConfigException() {
		super();
		
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public NotReaderConfigException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param arg0
	 * @param arg1
	 */
	public NotReaderConfigException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param arg0
	 */
	public NotReaderConfigException(String arg0) {
		super(arg0);
		
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param arg0
	 */
	public NotReaderConfigException(Throwable arg0) {
		super(arg0);
		
	}

}
