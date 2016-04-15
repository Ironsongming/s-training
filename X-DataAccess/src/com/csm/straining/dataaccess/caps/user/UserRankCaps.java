package com.csm.straining.dataaccess.caps.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.CoreException;
import com.csm.straining.dataaccess.Dao;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.entity.user.UserRank;
import com.csm.straining.dataaccess.entity.user.UserRankExample;
import com.csm.straining.dataaccess.mapper.user.UserRankMapper;


/**
 * @author chensongming
 */
public class UserRankCaps {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRankCaps.class);
	
	public static void insertUserRank(UserRank domain) throws CoreException {
		
		Dao<UserRankMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(UserRankMapper.class);
			dao.mapper().insertSelective(domain);
		} catch (Exception e) {
			logger.debug("[UserRankCaps] insertUserRank : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static boolean updateUserRank(long userID, UserRank userRank) throws CoreException {
		
		Dao<UserRankMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(UserRankMapper.class);
			
			UserRankExample exp = new UserRankExample();
			UserRankExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			
			return dao.mapper().updateByExampleSelective(userRank, exp) > 0 ? true : false;
		} catch (Exception e) {
			logger.debug("[UserRankCaps] updateUserRank : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		
	}
	
	public static UserRank getUserRankByUserID(long userID) throws CoreException {
		
		Dao<UserRankMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(UserRankMapper.class);
			
			UserRankExample exp = new UserRankExample();
			UserRankExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			exp.setLimit(1);
			
			List<UserRank> res = dao.mapper().selectByExample(exp);
			
			
			return res == null || res.isEmpty() ? null : res.get(0);
		} catch (Exception e) {
			logger.debug("[UserRankCaps] getUserRankByUserID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		
		
	}
	

}
