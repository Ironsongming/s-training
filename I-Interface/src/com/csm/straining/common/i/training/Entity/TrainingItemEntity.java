package com.csm.straining.common.i.training.Entity;

import java.io.Serializable;


/**
 * @author chensongming
 */
public class TrainingItemEntity implements Serializable{

	private static final long serialVersionUID = 2040500679587048963L;

	private long itemID;
	private long trainingID;
	private int count;
	private int breakTime;
	
	public long getItemID() {
		return itemID;
	}
	public void setItemID(long itemID) {
		this.itemID = itemID;
	}
	public long getTrainingID() {
		return trainingID;
	}
	public void setTrainingID(long trainingID) {
		this.trainingID = trainingID;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getBreakTime() {
		return breakTime;
	}
	public void setBreakTime(int breakTime) {
		this.breakTime = breakTime;
	}
	
	
	
}
