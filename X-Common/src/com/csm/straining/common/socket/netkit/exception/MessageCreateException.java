package com.csm.straining.common.socket.netkit.exception;


/**
 * @author chensongming
 */
public class MessageCreateException extends Exception {

	private static final long serialVersionUID = -7062920944267907268L;

	public MessageCreateException() {

	}
	
	public MessageCreateException(String msg) {
		super(msg);
	}
	
	public MessageCreateException(Throwable e) {
		super(e);
	}
	
	public MessageCreateException(String msg, Throwable e) {
		super(msg, e);
	}
	
}
