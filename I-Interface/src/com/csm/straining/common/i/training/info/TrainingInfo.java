package com.csm.straining.common.i.training.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author chensongming
 */
public class TrainingInfo implements Serializable{

	private static final long serialVersionUID = -3070613021441769046L;
	
	public long trainingID;

	public long userID;

	public int type;

	public long beginAt;

	public long finishAt;

	public int presetCount;

	public int presetGroup;

	public int perBreakTime;

	public int location;

	public int drinking;

	public int gear;

	public int actualCount;

	public int actualGroup;

	public int actualBreakTime;

	public int actualConsumTime;

	public byte status;

	public long createAt;
	
	public String content = "";
	public String title = "";
	public int isSuccess = 0;
	public List<TrainingItemInfo> items = new ArrayList<TrainingItemInfo>();

}
