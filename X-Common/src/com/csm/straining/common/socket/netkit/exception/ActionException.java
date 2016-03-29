package com.csm.straining.common.socket.netkit.exception;


/**
 * @author chensongming
 */
public class ActionException extends Exception {

	private static final long serialVersionUID = -3066380025687210472L;

	public ActionException() {

	}
	
	public ActionException(String msg) {
		super(msg);
	}
	
	public ActionException(Throwable e) {
		super(e);
	}
	
	public ActionException(String msg, Throwable e) {
		super(msg, e);
	}
	
}
