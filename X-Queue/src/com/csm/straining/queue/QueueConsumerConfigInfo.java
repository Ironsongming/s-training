package com.csm.straining.queue;


/**
 * @author chensongming
 */
public class QueueConsumerConfigInfo {
	
	private String name;
	private boolean isOn = false;
	private int threadNum = 0;
	
	public QueueConsumerConfigInfo(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}
	
	@Override
	public String toString() {
		return "[consumer name] : " + name + ", [consumer isOn] : " + isOn + ", [threadNum] : " + threadNum;
	}
	
}
