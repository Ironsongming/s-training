package com.csm.straining.common.socket.netkit.exception;


/**
 * @author chensongming
 */
public class MessageConvertException extends Exception {

	private static final long serialVersionUID = -1983689231986836518L;

	public MessageConvertException() {

	}
	
	public MessageConvertException(String msg) {
		super(msg);
	}
	
	public MessageConvertException(Throwable e) {
		super(e);
	}
	
	public MessageConvertException(String msg, Throwable e) {
		super(msg, e);
	}
	
}
