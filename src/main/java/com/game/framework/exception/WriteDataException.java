package com.game.framework.exception;

public class WriteDataException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public WriteDataException() {
		super();
	}

	public WriteDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WriteDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public WriteDataException(String message) {
		super(message);
	}

	public WriteDataException(Throwable cause) {
		super(cause);
	}
	
}
