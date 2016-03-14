package com.csm.straining.common.exception;


/**
 * @author chensongming
 */
public class ParamVaildException extends Exception {

	private static final long serialVersionUID = 5929469788396601593L;
	
	private int code;
	private String key;

	public ParamVaildException(int code, String key) {
		super();
		this.code = code;
		this.key = key;
	}

}
