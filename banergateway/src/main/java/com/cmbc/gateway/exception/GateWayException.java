package com.cmbc.gateway.exception;

public class GateWayException extends Exception {
	private static final long serialVersionUID = 985764312L;

	private String code;
	private String message;

	public GateWayException() {
		super();
	}

	public GateWayException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "[code=" + code + ", message=" + message + "]";
	}

}
