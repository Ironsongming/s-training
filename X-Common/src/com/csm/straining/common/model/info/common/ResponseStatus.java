package com.csm.straining.common.model.info.common;

import com.csm.straining.common.cons.Status;


/**
 * @author chensongming
 */
public class ResponseStatus  {
	
	private long ts = System.currentTimeMillis();
	private short status = Status.RespStatus.SUCCESS;
	
	public long getTs() {
		return ts;
	}
	
	public short getStatus() {
		return status;
	}
	
	public void setStatus(short status) {
		this.status = status;
	}
}
