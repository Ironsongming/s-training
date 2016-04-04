package com.csm.straining.common.socket.netkit.exception;


/**
 * @author chensongming
 */
public class SessionException extends RuntimeException {

	private static final long serialVersionUID = 5808794827092436611L;

	public SessionException() {

	}
	
	public SessionException(String msg) {
		super(msg);
	}
	
	public SessionException(Throwable e) {
		super(e);
	}
	
	public SessionException(String msg, Throwable e) {
		super(msg, e);
	}
	
}
