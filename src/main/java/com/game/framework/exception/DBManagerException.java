package com.game.framework.exception;

public class DBManagerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DBManagerException() {
		super();
	}

	public DBManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DBManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBManagerException(String message) {
		super(message);
	}

	public DBManagerException(Throwable cause) {
		super(cause);
	}

	
}
