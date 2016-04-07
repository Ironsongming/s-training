package com.csm.straining.common.exception;


/**
 * @author chensongming
 */
public class AppException extends Exception{
	
	private static final long serialVersionUID = -1864251794212999874L;
	private int code;
	private String msg;

	public AppException(int code) {
		super();
		this.code = code;
	}
	
	public AppException(String msg) {
		super();
		this.msg = msg;
	}
	
	public AppException(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	@Override
	public String toString() {
		return "[" + msg + "] " + code;
	}
	
}
