package com.csm.straining.core.training.util;


import java.util.ArrayList;
import java.util.List;

import com.csm.straining.common.i.training.Entity.TrainingEntity;
import com.csm.straining.dataaccess.entity.training.Training;


/**
 * @author chensongming
 */
public class TrainingHelper {
	
	public static TrainingEntity domain2Entity(Training domain) {
		if (domain == null) {
			return null;
		}
		
		TrainingEntity entity = new TrainingEntity();
		entity.setTrainingID(domain.getId());
		entity.setUserID(domain.getUserID());
		entity.setType(domain.getType());
		entity.setBeginAt(domain.getBeginAt().getTime());
		entity.setFinishAt(domain.getFinishAt() == null ? 0 : domain.getFinishAt().getTime());
		entity.setPresetCount(domain.getPresetCount());
		entity.setPresetGroup(domain.getPresetGroup());
		entity.setPerBreakTime(domain.getPerBreakTime());
		entity.setLocation(domain.getLocation());
		entity.setDrinking(domain.getDrinking());
		entity.setGear(domain.getGear());
		entity.setActualCount(domain.getActualCount());
		entity.setActualGroup(domain.getActualGroup());
		entity.setActualConsumTime(domain.getActualConsumTime());
		entity.setActualBreakTime(domain.getActualBreakTime());
		entity.setStatus(domain.getStatus());
		entity.setCreateAt(domain.getCreateAt().getTime());
		return entity;
	}
	
	public static List<TrainingEntity> domain2Entity(List<Training> domains) {
		if (domains == null || domains.isEmpty()) {
			return new ArrayList<TrainingEntity>();
		}
		
		List<TrainingEntity> entities = new ArrayList<TrainingEntity>();
		for (Training training : domains) {
			entities.add(domain2Entity(training));
		}
		
		return entities;
	}

}
