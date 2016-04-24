package com.csm.straining.common.i.training.cons;



/**
 * @author chensongming
 */
public enum TrainingLocationType {
	
	OUTDOOR(1, "室外/操场", 20),
	GYM(2, "室内/健身房", 100),
	INDOOR(3, "室内/自卑健身器材", 80);
	
	private int key;
	private String val;
	private int score;
	
	private TrainingLocationType(int key, String val, int score) {
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
	
	public static TrainingLocationType valueof(int key) {
		for (TrainingLocationType trainingType : TrainingLocationType.values()) {
			if (trainingType.getKey() == key) {
				return trainingType;
			}
		}
		
		return null;
	}
	
	public static boolean isValueof(int key) {
		for (TrainingLocationType trainingType : TrainingLocationType.values()) {
			if (trainingType.getKey() == key) {
				return true;
			}
		}
		
		return false;
	}

}
