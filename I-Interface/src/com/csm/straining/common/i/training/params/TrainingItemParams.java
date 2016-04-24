package com.csm.straining.common.i.training.params;

import java.io.Serializable;


/**
 * @author chensongming
 */
public class TrainingItemParams implements Serializable{

	private static final long serialVersionUID = 4656116121835096957L;
	private long trainingID;
	private int count;
	private int breakTime;
	
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
