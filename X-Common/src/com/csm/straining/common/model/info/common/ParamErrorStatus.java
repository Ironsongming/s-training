package com.csm.straining.common.model.info.common;


/**
 * @author chensongming
 */
public class ParamErrorStatus extends ErrorStatus {
	
	private String arg;
	
	public ParamErrorStatus(int code, String arg) {
		super(code);
		this.arg = arg;
	}
	
	public String getArg() {
		return this.arg;
	}
	
	
	
	

}
