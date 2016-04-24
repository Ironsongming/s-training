package com.csm.straining.dataaccess.mapper.training;

import com.csm.straining.dataaccess.entity.training.TrainingItem;
import java.util.List;

public interface TrainingItemCusMapper {
    
	void multiInsertTrainingItem(List<TrainingItem> items);
	
}