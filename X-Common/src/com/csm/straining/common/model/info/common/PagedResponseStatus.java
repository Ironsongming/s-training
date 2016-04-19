package com.csm.straining.common.model.info.common;


/**
 * @author chensongming
 */
public class PagedResponseStatus extends ResponseStatus{
	
	private String start;
	private int more;
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public int getMore() {
		return more;
	}
	public void setMore(int more) {
		this.more = more;
	}
	
	

}
