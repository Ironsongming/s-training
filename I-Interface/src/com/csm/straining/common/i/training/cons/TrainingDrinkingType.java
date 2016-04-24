package com.csm.straining.common.i.training.cons;


/**
 * @author chensongming
 */
public enum TrainingDrinkingType {
	
	MUSCLE_TECH(1, "肌肉科技", 20),
	PROTEIN_POWDER(2, "其他蛋白粉", 15),
	SKIM_MIKE(3, "脱脂牛奶", 10),
	ENERGY_DRINK(4, "功能性饮料", 8),
	NONE(5, "无", 0);
	
	private int key;
	private String val;
	private int score;
	
	private TrainingDrinkingType(int key, String val, int score) {
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
	
	public static TrainingDrinkingType valueof(int key) {
		for (TrainingDrinkingType trainingType : TrainingDrinkingType.values()) {
			if (trainingType.getKey() == key) {
				return trainingType;
			}
		}
		
		return null;
	}
	
	public static boolean isValueof(int key) {
		for (TrainingDrinkingType trainingType : TrainingDrinkingType.values()) {
			if (trainingType.getKey() == key) {
				return true;
			}
		}
		
		return false;
	}

}
