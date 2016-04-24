package com.csm.straining.common.i.training.params;

import java.io.Serializable;


/**
 * @author chensongming
 */
public class TrainingParams implements Serializable {

	private static final long serialVersionUID = -896201712231578103L;
	
	private long userID;
	private int type;
	private int location;
	private int drinking;
	private int gear;
	private long beginAt;
	private int presetCount;
	private int presetGroup;
	private int perBreakTime;
	
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public int getDrinking() {
		return drinking;
	}
	public void setDrinking(int drinking) {
		this.drinking = drinking;
	}
	public int getGear() {
		return gear;
	}
	public void setGear(int gear) {
		this.gear = gear;
	}
	public long getBeginAt() {
		return beginAt;
	}
	public void setBeginAt(long beginAt) {
		this.beginAt = beginAt;
	}
	public int getPresetCount() {
		return presetCount;
	}
	public void setPresetCount(int presetCount) {
		this.presetCount = presetCount;
	}
	public int getPresetGroup() {
		return presetGroup;
	}
	public void setPresetGroup(int presetGroup) {
		this.presetGroup = presetGroup;
	}
	public int getPerBreakTime() {
		return perBreakTime;
	}
	public void setPerBreakTime(int perBreakTime) {
		this.perBreakTime = perBreakTime;
	}
	
	
	

}
