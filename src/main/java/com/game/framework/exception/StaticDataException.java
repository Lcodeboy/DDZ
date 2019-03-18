package com.game.framework.exception;

public class StaticDataException extends RuntimeException {
		
	private static final long serialVersionUID = 1L;

	public StaticDataException() {
		super();
	}

	public StaticDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StaticDataException(String message, Throwable cause) {
		super(message, cause);

	}

	public StaticDataException(String message) {
		super(message);
	}

	public StaticDataException(Throwable cause) {
		super(cause);
	}

}
