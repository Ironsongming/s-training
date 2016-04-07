package com.csm.straining.common.exception;


/**
 * @author chensongming
 */
public class CoreException extends Exception {

	private static final long serialVersionUID = -2556452869544602635L;
	
	public static final int DATABASE = 1;
	public static final int UPLOAD = 2;
	public static final int UNKNOWN = -1;
	
	private int type;
	
	public CoreException(int type) {
		super();
		this.type = type;
	}
	
	public CoreException(int type, String message) {
		super(message);
		this.type = type;
	}
	
	public CoreException(int type, Throwable e) {
		super(e);
		this.type = type;
	}
	
	public CoreException(int type, String message, Throwable e) {
		super(message, e);
		this.type = type;
	}
	
	public CoreException() {
		this(UNKNOWN);
	}
	
	public CoreException(String message) {
		this(UNKNOWN, message);
	}
	
	public CoreException(Throwable e) {
		this(UNKNOWN, e);
	}
	
	public CoreException(String message, Throwable e) {
		this(UNKNOWN, message, e);
	}
	
}
