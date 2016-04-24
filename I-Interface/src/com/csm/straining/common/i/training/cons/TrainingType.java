package com.csm.straining.common.i.training.cons;



/**
 * @author chensongming
 */
public enum TrainingType {
	
	PUSH_UP(1, "伏地挺身"),
	PULL_UP(2, "引体向上"),
	SIT_UP(3, "仰卧起坐"),
	CHEST_PRESS(4, "胸部推举"),
	SOVER(5, "飞鸟"),
	MILITARY_PRESS(6, "肩部推举"),
	SEATED_ROW(7, "坐姿划船"),
	OUTER_THIGH_ABDUCTOR(8, "腿部外弯"),
	INNER_THIGH_ABDUCTOR(9, "腿部内弯"),
	LEG_PRESS(10, "腿部推蹬"),
	LEG_EXTENSION(11, "腿部伸展"),
	STANDING_TRICEP(12, "三头肌训练"),
	STANDING_BICEP(13, "二头肌训练");	
	
	private int key;
	private String val;
	
	private TrainingType(int key, String val) {
		this.key = key;
		this.val = val;
	}

	public int getKey() {
		return this.key;
	}
	
	public String getVal() {
		return this.val;
	}
	
	public static TrainingType valueof(int key) {
		for (TrainingType trainingType : TrainingType.values()) {
			if (trainingType.getKey() == key) {
				return trainingType;
			}
		}
		
		return null;
	}
	
	public static boolean isValueof(int key) {
		for (TrainingType trainingType : TrainingType.values()) {
			if (trainingType.getKey() == key) {
				return true;
			}
		}
		
		return false;
	}

}
