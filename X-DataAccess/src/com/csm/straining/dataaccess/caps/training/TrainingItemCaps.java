package com.csm.straining.dataaccess.caps.training;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.CoreException;
import com.csm.straining.dataaccess.Dao;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.entity.training.TrainingItem;
import com.csm.straining.dataaccess.entity.training.TrainingItemExample;
import com.csm.straining.dataaccess.mapper.training.TrainingItemCusMapper;
import com.csm.straining.dataaccess.mapper.training.TrainingItemMapper;



/**
 * @author chensongming
 */
public class TrainingItemCaps {
	
	private static final Logger logger = LoggerFactory.getLogger(TrainingItemCaps.class);

	public static void multiInsertTrainingItem(List<TrainingItem> domains) throws CoreException {
		if (domains == null || domains.isEmpty()) {
			return;
		}
		
		Dao<TrainingItemCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(TrainingItemCusMapper.class);
			dao.mapper().multiInsertTrainingItem(domains);
		} catch (Exception e) {
			logger.debug("[TrainingCaps] multiInsertTrainingItem : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static List<TrainingItem> getTrainingItemsByTrainingID(long trainingID) throws CoreException {
		
		Dao<TrainingItemMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(TrainingItemMapper.class);
			
			TrainingItemExample exp = new TrainingItemExample();
			TrainingItemExample.Criteria criteria = exp.createCriteria();

			exp.setOrderByClause("id asc");
			criteria.andTrainingIDEqualTo(trainingID);
			
			List<TrainingItem> res = dao.mapper().selectByExample(exp);
			return res == null ? new ArrayList<TrainingItem>() : res; 
		} catch (Exception e) {
			logger.debug("[TrainingCaps] getTrainingItemsByTrainingID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}

}
