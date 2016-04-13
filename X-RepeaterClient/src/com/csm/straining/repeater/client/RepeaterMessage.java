package com.csm.straining.repeater.client;


/**
 * @author chensongming
 */
public class RepeaterMessage {
	
	private int targetClientID;
	private long targetID;
	
	private Object msg;

	public int getTargetClientID() {
		return targetClientID;
	}

	public void setTargetClientID(int targetClientID) {
		this.targetClientID = targetClientID;
	}

	public long getTargetID() {
		return targetID;
	}

	public void setTargetID(long targetID) {
		this.targetID = targetID;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}
	
	
	

}
