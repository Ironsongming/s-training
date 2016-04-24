package com.csm.straining.core.training.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.common.i.training.Entity.TrainingEntity;
import com.csm.straining.common.i.training.Entity.TrainingItemEntity;
import com.csm.straining.common.i.training.cons.TrainingDrinkingType;
import com.csm.straining.common.i.training.cons.TrainingGearType;
import com.csm.straining.common.i.training.cons.TrainingLocationType;
import com.csm.straining.common.i.training.cons.TrainingType;
import com.csm.straining.common.i.training.params.TrainingItemParams;
import com.csm.straining.common.i.training.params.TrainingParams;
import com.csm.straining.core.training.util.TrainingHelper;
import com.csm.straining.core.user.core.UserCore;
import com.csm.straining.dataaccess.caps.training.TrainingCaps;
import com.csm.straining.dataaccess.caps.training.TrainingItemCaps;
import com.csm.straining.dataaccess.entity.training.Training;
import com.csm.straining.dataaccess.entity.training.TrainingItem;
import com.lamfire.utils.JSON;


/**
 * @author chensongming
 */
public class TrainingCore {
	
	private static final Logger logger = LoggerFactory.getLogger(TrainingCore.class);

	public static TrainingEntity createTraining(TrainingParams params) throws AppException, CoreException {
		
		if (params.getUserID() <= 0 || !UserCore.existUserID(params.getUserID())) {
			throw new AppException("该用户不存在");
		}
		
		if (!TrainingType.isValueof(params.getType())) {
			throw new AppException("健身类型不存在");
		}
		
		if (!TrainingLocationType.isValueof(params.getLocation())) {
			throw new AppException("健身地点类型不存在");
		}
		
		if (!TrainingDrinkingType.isValueof(params.getDrinking())) {
			throw new AppException("健身饮料类型不存在");
		}
		
		if (!TrainingGearType.isValueof(params.getGear())) {
			throw new AppException("健身设备类型不存在");
		}
		
		Training domain = new Training();
		domain.setUserID(params.getUserID());
		domain.setType(params.getType());
		domain.setLocation(params.getLocation());
		domain.setDrinking(params.getDrinking());
		domain.setGear(params.getGear());
		
		if (params.getPresetCount() > 0) {
			domain.setPresetCount(params.getPresetCount());
		}
		
		if (params.getPresetGroup() > 0) {
			domain.setPresetGroup(params.getPresetGroup());
		}
		
		if (params.getPerBreakTime() > 0) {
			domain.setPerBreakTime(params.getPerBreakTime());
		}
		
		if (params.getBeginAt() > 0) {
			domain.setBeginAt(new Date(params.getBeginAt()));
		}
		
		domain.setCreateAt(new Date());
		long res = TrainingCaps.insertTraining(domain);
		TrainingEntity trainingEntity =  getTrainingEntityByID(res);
		if (trainingEntity == null) {
			throw new AppException("获取任务详情失败");
		}
		return trainingEntity;
		
	}
	
	public static void startTraining(long userID, long trainingID) throws CoreException, AppException {
		
		Training domain = TrainingCaps.getTrainingByID(trainingID);
		if (domain == null) {
			throw new AppException("不存在该任务");
		}
		
		if (domain.getStatus() == Status.Training.START) {
			throw new AppException("该任务已在进行中");
		}
		
		if (domain.getStatus() == Status.Training.FINISH) {
			throw new AppException("该任务已结束，请重新创建任务");
		}
		
		if (domain.getUserID() != userID) {
			throw new AppException("你无权操作该任务");
		}
		
		TrainingCaps.startTraining(trainingID);
	}
	
	public static void finishTraining(long userID, long trainingID, int consumTime, List<TrainingItemParams> trainingItems) throws CoreException, AppException {
		
		Training domain = TrainingCaps.getTrainingByID(trainingID);
		if (domain == null) {
			throw new AppException("不存在该任务");
		}
		
		if (domain.getStatus() == Status.Training.FINISH) {
			throw new AppException("该任务已经结束，不可重复操作");
		}
		
		int sumCount = 0;
		int groupCount = 0;
		int breakTime = 0;
		if (trainingItems != null) {
			for (TrainingItemParams trainingItemParams : trainingItems) {
				sumCount += trainingItemParams.getCount();
				breakTime += trainingItemParams.getBreakTime();
			}
			groupCount = trainingItems.size();
		}
		
		logger.debug("songming here domian : " + JSON.toJsonString(domain));
		logger.debug("songming here userID : " + userID);
		if (domain.getUserID() != userID) {
			throw new AppException("你无权操作该任务");
		}
		
		TrainingCaps.finishTraining(trainingID, sumCount, groupCount, breakTime, consumTime);
		insertTrainingItems(trainingItems);
	}
	
	private static void insertTrainingItems(List<TrainingItemParams> params) throws CoreException {
		if (params == null || params.isEmpty()) {
			return;
		}
		List<TrainingItem> domains = new ArrayList<TrainingItem>();
		for (TrainingItemParams trainingItemParams : params) {
			TrainingItem domain = new TrainingItem();
			domain.setBreakTime(trainingItemParams.getBreakTime());
			domain.setCount(trainingItemParams.getCount());
			domain.setCreateAt(new Date());
			domain.setTrainingID(trainingItemParams.getTrainingID());
			domains.add(domain);
		}
		
		TrainingItemCaps.multiInsertTrainingItem(domains);
		
	}
	
	public static TrainingEntity getTrainingEntityByID(long trainingID) throws CoreException, AppException {
		
		TrainingEntity trainingEntity = TrainingHelper.domain2Entity(TrainingCaps.getTrainingByID(trainingID));
		
		if (trainingEntity == null) {
			throw new AppException("该任务不存在");
		}
		
		List<TrainingItem> trainingItems = TrainingItemCaps.getTrainingItemsByTrainingID(trainingID);
		for (TrainingItem trainingItem : trainingItems) {
			TrainingItemEntity trainingItemEntity = new TrainingItemEntity();
			trainingItemEntity.setTrainingID(trainingItem.getTrainingID());
			trainingItemEntity.setItemID(trainingItem.getId());
			trainingItemEntity.setCount(trainingItem.getCount());
			trainingItemEntity.setBreakTime(trainingItem.getBreakTime());
			trainingEntity.getItems().add(trainingItemEntity);
		}
		
		return trainingEntity;
	}
	
	public static List<TrainingEntity> getTrainingEntitiesByUserID(long userID, long start, int count) throws CoreException, AppException {
		return TrainingHelper.domain2Entity(TrainingCaps.getTrainingsByUserID(userID, start, count));	
	}
	
}
