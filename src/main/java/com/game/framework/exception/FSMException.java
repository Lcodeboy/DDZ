package com.game.framework.exception;

public class FSMException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public FSMException() {
		super();
	}

	public FSMException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public FSMException(String message, Throwable cause) {
		super(message, cause);

	}

	public FSMException(String message) {
		super(message);

	}

	public FSMException(Throwable cause) {
		super(cause);

	}

}
