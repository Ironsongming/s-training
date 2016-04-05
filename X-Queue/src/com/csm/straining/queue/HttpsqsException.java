package com.csm.straining.queue;


/**
 * @author chensongming
 */
public class HttpsqsException extends Exception {

	private static final long serialVersionUID = 8791736091840375629L;
	
	public HttpsqsException() {
		super();
	}
	
	public HttpsqsException(String message) {
		super(message);
	}
	
	public HttpsqsException(String message, Throwable cause) {
		super(message, cause);
	}

}
