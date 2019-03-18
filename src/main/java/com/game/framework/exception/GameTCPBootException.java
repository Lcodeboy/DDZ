
package com.game.framework.exception;

public class GameTCPBootException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 */
	public GameTCPBootException() {
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
	public GameTCPBootException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param arg0
	 * @param arg1
	 */
	public GameTCPBootException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param arg0
	 */
	public GameTCPBootException(String arg0) {
		super(arg0);
		
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param arg0
	 */
	public GameTCPBootException(Throwable arg0) {
		super(arg0);
		
	}
	
}
