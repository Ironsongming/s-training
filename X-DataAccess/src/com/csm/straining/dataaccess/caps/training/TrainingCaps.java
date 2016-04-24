package com.csm.straining.dataaccess.caps.training;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.dataaccess.Dao;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.entity.training.Training;
import com.csm.straining.dataaccess.entity.training.TrainingExample;
import com.csm.straining.dataaccess.mapper.training.TrainingMapper;


/**
 * @author chensongming
 */
public class TrainingCaps {
	
	private static final Logger logger = LoggerFactory.getLogger(TrainingCaps.class);
	
	
	public static long insertTraining(Training domain) throws CoreException {
		Dao<TrainingMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(TrainingMapper.class);
			dao.mapper().insertSelective(domain);
		} catch (Exception e) {
			logger.debug("[TrainingCaps] insertTraining : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		
		return domain.getId();
	}
	
	public static void startTraining(long trainingID) throws CoreException {
		
		Dao<TrainingMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(TrainingMapper.class);
			
			Training domain = new Training();
			domain.setId(trainingID);
			domain.setBeginAt(new Date());
			domain.setStatus(Status.Training.START);
			
			dao.mapper().updateByPrimaryKeySelective(domain);
		} catch (Exception e) {
			logger.debug("[TrainingCaps] startTraining : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void finishTraining(long trainingID, int sumCount, int groupCount, int breakTime, int consumTime) throws CoreException {
		Dao<TrainingMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(TrainingMapper.class);
			
			Training domain = new Training();
			domain.setId(trainingID);
			domain.setActualCount(sumCount);
			domain.setActualGroup(groupCount);
			domain.setActualBreakTime(breakTime);
			domain.setActualConsumTime(consumTime);
			
			domain.setStatus(Status.Training.FINISH);
			domain.setFinishAt(new Date());
			
			dao.mapper().updateByPrimaryKeySelective(domain);
		} catch (Exception e) {
			logger.debug("[TrainingCaps] finishTraining : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static Training getTrainingByID(long trainingID) throws CoreException {
		Dao<TrainingMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(TrainingMapper.class);
			return dao.mapper().selectByPrimaryKey(trainingID);
		} catch (Exception e) {
			logger.debug("[TrainingCaps] getTrainingByID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static List<Training> getTrainingsByUserID(long userID, long start, int count) throws CoreException {
		Dao<TrainingMapper> dao = null;
		
		count = count < 0 ? 20 : count;
		
		try {
			dao = DbConfig.openSessionMaster(TrainingMapper.class);
			
			
			TrainingExample exp = new TrainingExample();
			TrainingExample.Criteria criteria = exp.createCriteria();
			
			
			exp.setOrderByClause("id desc");
			exp.setLimit(count);
			
			criteria.andUserIDEqualTo(userID);
			
			if (start > 0) {
				criteria.andIdLessThan(start);
			}
			
			List<Training> res = dao.mapper().selectByExample(exp);
			
			return res == null ? new ArrayList<Training>() : res;  
		} catch (Exception e) {
			logger.debug("[TrainingCaps] getTrainingsByUserID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	

}
