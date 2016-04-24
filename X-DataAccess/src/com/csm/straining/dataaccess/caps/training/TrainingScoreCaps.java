package com.csm.straining.dataaccess.caps.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.CoreException;
import com.csm.straining.dataaccess.Dao;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.entity.training.Training;
import com.csm.straining.dataaccess.entity.training.TrainingScore;
import com.csm.straining.dataaccess.mapper.training.TrainingMapper;
import com.csm.straining.dataaccess.mapper.training.TrainingScoreMapper;


/**
 * @author chensongming
 */
public class TrainingScoreCaps {
	
	private static final Logger logger = LoggerFactory.getLogger(TrainingScoreCaps.class);
	
	public static void insertTrainingScore(TrainingScore domain) throws CoreException {
		Dao<TrainingScoreMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(TrainingScoreMapper.class);
			dao.mapper().insertSelective(domain);
		} catch (Exception e) {
			logger.debug("[TrainingScoreCaps] insertTrainingScore : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
}
