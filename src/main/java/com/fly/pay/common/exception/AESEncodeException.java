package com.fly.pay.common.exception;

public class AESEncodeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6094899870897566636L;

	public AESEncodeException() {
	}

	public AESEncodeException(String message) {
		super(message);
	}

	public AESEncodeException(Throwable cause) {
		super(cause);
	}

	public AESEncodeException(String message, Throwable cause) {
		super(message, cause);
	}

}
