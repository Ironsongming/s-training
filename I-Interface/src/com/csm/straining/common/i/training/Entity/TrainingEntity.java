package com.csm.straining.common.i.training.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author chensongming
 */
public class TrainingEntity implements Serializable {

	private static final long serialVersionUID = 868157025534557562L;
	
	private long trainingID;

	private long userID;

	private int type;

	private long beginAt;

	private long finishAt;

	private int presetCount;

	private int presetGroup;

    private int perBreakTime;

    private int location;

    private int drinking;

    private int gear;

    private int actualCount;

    private int actualGroup;

    private int actualBreakTime;

    private int actualConsumTime;

    private byte status;

    private long createAt;
    
    private List<TrainingItemEntity> items = new ArrayList<TrainingItemEntity>();
    
	public long getTrainingID() {
		return trainingID;
	}

	public void setTrainingID(long trainingID) {
		this.trainingID = trainingID;
	}

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

	public long getBeginAt() {
		return beginAt;
	}

	public void setBeginAt(long beginAt) {
		this.beginAt = beginAt;
	}

	public long getFinishAt() {
		return finishAt;
	}

	public void setFinishAt(long finishAt) {
		this.finishAt = finishAt;
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

	public int getActualCount() {
		return actualCount;
	}

	public void setActualCount(int actualCount) {
		this.actualCount = actualCount;
	}

	public int getActualGroup() {
		return actualGroup;
	}

	public void setActualGroup(int actualGroup) {
		this.actualGroup = actualGroup;
	}

	public int getActualBreakTime() {
		return actualBreakTime;
	}

	public void setActualBreakTime(int actualBreakTime) {
		this.actualBreakTime = actualBreakTime;
	}

	public int getActualConsumTime() {
		return actualConsumTime;
	}

	public void setActualConsumTime(int actualConsumTime) {
		this.actualConsumTime = actualConsumTime;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}
	
	public List<TrainingItemEntity> getItems() {
		return items;
	}
	
	public void setItems(List<TrainingItemEntity> items) {
		this.items = items;
	}
    
    

}
