package com.csm.straining.common.i.training.cons;


/**
 * @author chensongming
 */
public enum TrainingGearType {
	
	NIKE(1, "NIKE", 10),
	MCDAVID(2, "McDavid", 10),
	DECATHLON(3, "Decathlon", 10),
	NONE(4, "无", 0);
	
	private int key;
	private String val;
	private int score;
	
	private TrainingGearType(int key, String val, int score) {
		this.key = key;
		this.val = val;
		this.score = score;
	}

	public int getKey() {
		return this.key;
	}
	
	public String getVal() {
		return this.val;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public static TrainingGearType valueof(int key) {
		for (TrainingGearType trainingType : TrainingGearType.values()) {
			if (trainingType.getKey() == key) {
				return trainingType;
			}
		}
		
		return null;
	}
	
	public static boolean isValueof(int key) {
		for (TrainingGearType trainingType : TrainingGearType.values()) {
			if (trainingType.getKey() == key) {
				return true;
			}
		}
		
		return false;
	}

}
