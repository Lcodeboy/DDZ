package com.game.framework.exception;

public class IniFileReaderException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public IniFileReaderException() {
		super();
	}

	public IniFileReaderException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IniFileReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public IniFileReaderException(String message) {
		super(message);
	}

	public IniFileReaderException(Throwable cause) {
		super(cause);
	}

}
