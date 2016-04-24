package com.csm.straining.user.service;

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
import com.csm.straining.common.i.training.info.TrainingInfo;
import com.csm.straining.common.i.training.info.TrainingItemInfo;
import com.csm.straining.common.i.training.params.TrainingItemParams;
import com.csm.straining.common.i.training.params.TrainingParams;
import com.csm.straining.common.model.info.common.ResponseStatus;
import com.csm.straining.user.refer.UserServiceReference;
import com.csm.straining.user.resp.training.TrainingDetailResp;
import com.csm.straining.user.resp.training.TrainingListResp;


/**
 * @author chensongming
 */
public class TrainingService {
	
	private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);
	
	public static TrainingDetailResp trainingCreateResp(TrainingParams params) throws AppException, CoreException {
		
		TrainingEntity trainingEntity = UserServiceReference.sharedService().createTraining(params);
		if (trainingEntity == null) {
			throw new AppException("获取任务失败");
		}
		
		TrainingDetailResp resp = new TrainingDetailResp();
		resp.training = praseTrainingInfoDetail(trainingEntity);
		return resp;
	}
	
	public static TrainingDetailResp trainingDetailResp(long trainingID) throws AppException, CoreException {
		TrainingEntity trainingEntity = UserServiceReference.sharedService().getTrainingEntityByID(trainingID);
		if (trainingEntity == null) {
			throw new AppException("获取任务失败");
		}
		
		TrainingDetailResp resp = new TrainingDetailResp();
		resp.training = praseTrainingInfoDetail(trainingEntity);
		return resp;
	}
	
	public static ResponseStatus trainingStartResp(long optUserID, long trainingID) throws CoreException, AppException {
		UserServiceReference.sharedService().startTraining(optUserID, trainingID);
		return new ResponseStatus();
	}
	
	public static ResponseStatus trainingFinishResp(long optUserID, long trainingID, int consumTime, List<TrainingItemParams> trainingItems) throws CoreException, AppException {
		UserServiceReference.sharedService().finishTraining(optUserID, trainingID, consumTime, trainingItems);
		return new ResponseStatus();
	}
	
	public static TrainingListResp trainingListResp(long userID, long start, int count) throws CoreException, AppException {
		List<TrainingEntity> trainingEntities = UserServiceReference.sharedService().getTrainingEntitiesByUserID(userID, start, count + 1);
		TrainingListResp resp = new TrainingListResp();
		for (TrainingEntity trainingEntity : trainingEntities) {
			resp.trainings.add(praseTrainingInfoItem(trainingEntity));
		}
		
		if (resp.trainings.size() > count) {
			resp.setMore(1);
		} else {
			resp.setMore(0);
			
			if (resp.trainings.size() > 0) {
				resp.setStart(Long.toString(resp.trainings.get(resp.trainings.size() - 1).trainingID));
			}
		}
		return resp;
	}
	
	private static TrainingInfo praseTrainingInfoDetail(TrainingEntity trainingEntity) {
		if (trainingEntity == null) {
			return null;
		}
		
		TrainingInfo trainingInfo = new TrainingInfo();
		
		trainingInfo.trainingID = trainingEntity.getTrainingID();
		trainingInfo.userID = trainingEntity.getUserID();
		trainingInfo.type = trainingEntity.getType();
		trainingInfo.beginAt = trainingEntity.getBeginAt();
		trainingInfo.finishAt = trainingEntity.getFinishAt();
		trainingInfo.presetCount = trainingEntity.getPresetCount();
		trainingInfo.presetGroup = trainingEntity.getPresetGroup();
		trainingInfo.perBreakTime = trainingEntity.getPerBreakTime();
		trainingInfo.location = trainingEntity.getLocation();
		trainingInfo.drinking = trainingEntity.getDrinking();
		trainingInfo.gear = trainingEntity.getGear();
		trainingInfo.actualCount = trainingEntity.getActualCount();
		trainingInfo.actualGroup = trainingEntity.getActualGroup();
		trainingInfo.actualBreakTime = trainingEntity.getActualBreakTime();
		trainingInfo.actualConsumTime = trainingEntity.getActualConsumTime();
		trainingInfo.actualConsumTime = trainingEntity.getActualConsumTime();
		trainingInfo.status = trainingEntity.getStatus();
		trainingInfo.createAt = trainingEntity.getCreateAt();
		
		for (TrainingItemEntity trainingItemEntity : trainingEntity.getItems()) {
			TrainingItemInfo trainingItemInfo = new TrainingItemInfo();
			trainingItemInfo.breakTime = trainingItemEntity.getBreakTime();
			trainingItemInfo.count = trainingItemEntity.getCount();
			trainingInfo.items.add(trainingItemInfo);
		}
		
		StringBuilder sb = new StringBuilder();
		if (trainingInfo.status == Status.Training.CREATE || trainingInfo.status == Status.Training.START) {
			sb.append("运动目标: ").append("\n")
				.append("在 ").append(trainingInfo.presetGroup).append(" 组内完成 ").append(trainingInfo.presetCount).append(" 个 ").append(TrainingType.valueof(trainingInfo.type).getVal()).append(";\n")
				.append("由于你在 ").append(TrainingLocationType.valueof(trainingInfo.location).getVal()).append(" 环境中，并平均休息 ").append(trainingInfo.perBreakTime).append(" 秒").append(";\n")
				.append("同时你选择使用 ").append(TrainingGearType.valueof(trainingInfo.gear).getVal()).append(" 护具和 ").append(TrainingDrinkingType.valueof(trainingInfo.drinking).getVal()).append(" 作为能量补充;\n")
				.append("根据你实际的完成结果来最终评定你的得分");
				
		} else {
			sb.append("运动预设目标: ").append("\n")
			.append("在 ").append(trainingInfo.presetGroup).append(" 组内完成 ").append(trainingInfo.presetCount).append(" 个 ").append(TrainingType.valueof(trainingInfo.type).getVal()).append(";\n")
			.append("选择 ").append(TrainingLocationType.valueof(trainingInfo.location).getVal()).append(" 环境，计划平均休息 ").append(trainingInfo.perBreakTime).append(" 秒").append(";\n")
			.append("选择使用 ").append(TrainingGearType.valueof(trainingInfo.gear).getVal()).append(" 护具和 ").append(TrainingDrinkingType.valueof(trainingInfo.drinking).getVal()).append(" 作为能量补充;\n");
		}
		trainingInfo.content = sb.toString();
		
		trainingInfo.title = new StringBuilder(TrainingType.valueof(trainingInfo.type).getVal()).append(" #").append(trainingInfo.presetGroup).append("G-").append(trainingInfo.presetCount).toString();
		if (trainingInfo.actualCount >= trainingInfo.presetCount) {
			trainingInfo.isSuccess = 1;
		}
		return trainingInfo;
	}
	
	private static TrainingInfo praseTrainingInfoItem(TrainingEntity trainingEntity) {
		if (trainingEntity == null) {
			return null;
		}
		
		TrainingInfo trainingInfo = new TrainingInfo();
		
		trainingInfo.trainingID = trainingEntity.getTrainingID();
		trainingInfo.userID = trainingEntity.getUserID();
		trainingInfo.type = trainingEntity.getType();
		trainingInfo.beginAt = trainingEntity.getBeginAt();
		trainingInfo.finishAt = trainingEntity.getFinishAt();
		trainingInfo.presetCount = trainingEntity.getPresetCount();
		trainingInfo.presetGroup = trainingEntity.getPresetGroup();
		trainingInfo.perBreakTime = trainingEntity.getPerBreakTime();
		trainingInfo.location = trainingEntity.getLocation();
		trainingInfo.drinking = trainingEntity.getDrinking();
		trainingInfo.gear = trainingEntity.getGear();
		trainingInfo.actualCount = trainingEntity.getActualCount();
		trainingInfo.actualGroup = trainingEntity.getActualGroup();
		trainingInfo.actualBreakTime = trainingEntity.getActualBreakTime();
		trainingInfo.actualConsumTime = trainingEntity.getActualConsumTime();
		trainingInfo.actualConsumTime = trainingEntity.getActualConsumTime();
		trainingInfo.status = trainingEntity.getStatus();
		trainingInfo.createAt = trainingEntity.getCreateAt();
		
		for (TrainingItemEntity trainingItemEntity : trainingEntity.getItems()) {
			TrainingItemInfo trainingItemInfo = new TrainingItemInfo();
			trainingItemInfo.breakTime = trainingItemEntity.getBreakTime();
			trainingItemInfo.count = trainingItemEntity.getCount();
			trainingInfo.items.add(trainingItemInfo);
		}
		
		StringBuilder sb = new StringBuilder();
		if (trainingInfo.status == Status.Training.CREATE || trainingInfo.status == Status.Training.START) {
			sb.append("运动目标: ").append("\n")
				.append("在 ").append(trainingInfo.presetGroup).append(" 内完成 ").append(trainingInfo.presetCount).append(" 个 ").append(TrainingType.valueof(trainingInfo.type).getVal()).append(";\n")
				.append("由于你在 ").append(TrainingLocationType.valueof(trainingInfo.location).getVal()).append(" 环境中，并平均休息 ").append(trainingInfo.perBreakTime).append(" 秒").append(";\n")
				.append("同时你选择使用 ").append(TrainingGearType.valueof(trainingInfo.gear).getVal()).append(" 护具和 ").append(TrainingDrinkingType.valueof(trainingInfo.drinking).getVal()).append(" 作为能量补充;\n")
				.append("根据你实际的完成结果来最终评定你的得分");
				
		} else {
			sb.append("运动预设目标: ").append("\n")
			.append("在 ").append(trainingInfo.presetGroup).append(" 内完成 ").append(trainingInfo.presetCount).append(" 个 ").append(TrainingType.valueof(trainingInfo.type).getVal()).append(";\n")
			.append("选择 ").append(TrainingLocationType.valueof(trainingInfo.location).getVal()).append(" 环境，计划平均休息 ").append(trainingInfo.perBreakTime).append(" 秒").append(";\n")
			.append("选择使用 ").append(TrainingGearType.valueof(trainingInfo.gear).getVal()).append(" 护具和 ").append(TrainingDrinkingType.valueof(trainingInfo.drinking).getVal()).append(" 作为能量补充;\n");
		}
		
		trainingInfo.content = sb.toString();
		trainingInfo.title = new StringBuilder(TrainingType.valueof(trainingInfo.type).getVal()).append(" #").append(trainingInfo.presetGroup).append("G-").append(trainingInfo.presetCount * trainingInfo.presetGroup).toString();
		if (trainingInfo.actualCount >= trainingInfo.presetCount) {
			trainingInfo.isSuccess = 1;
		}
		return trainingInfo;
	}
	
}
