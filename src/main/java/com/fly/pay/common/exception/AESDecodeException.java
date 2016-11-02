package com.fly.pay.common.exception;

public class AESDecodeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5247088537370651778L;

	public AESDecodeException() {
	}

	public AESDecodeException(String message) {
		super(message);
	}

	public AESDecodeException(Throwable cause) {
		super(cause);
	}

	public AESDecodeException(String message, Throwable cause) {
		super(message, cause);
	}

}
