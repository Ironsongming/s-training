package com.csm.straining.dataaccess.caps.contact;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.dataaccess.Dao;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.entity.contact.Follow;
import com.csm.straining.dataaccess.entity.contact.FollowExample;
import com.csm.straining.dataaccess.mapper.contact.FollowMapper;


/**
 * @author chensongming
 */
public class FollowCaps {
	
	private static final Logger logger = LoggerFactory.getLogger(FollowCaps.class);
	
	public static void insertFollow(Follow domain) throws CoreException {
		
		Dao<FollowMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(FollowMapper.class);
			dao.mapper().insertSelective(domain);
		} catch (Exception e) {
			logger.debug("[FollowCaps] insertFollow : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void delFollowByID(long userID, long followID) throws CoreException {
		
		Dao<FollowMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(FollowMapper.class);
			
			FollowExample exp = new FollowExample();
			FollowExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andFollowIDEqualTo(followID);
			
			Follow domain = new Follow();
			domain.setStatus(Status.Follow.DELETE);
			dao.mapper().updateByExampleSelective(domain, exp);
		} catch (Exception e) {
			logger.debug("[FollowCaps] delFollowByID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void updateFollow2Normal(long userID, long followID) throws CoreException {
		
		Dao<FollowMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(FollowMapper.class);
			
			Follow domain = new Follow();
			domain.setStatus(Status.Follow.NORMAL);
			
			FollowExample exp = new FollowExample();
			FollowExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andFollowIDEqualTo(followID);
			
			dao.mapper().updateByExampleSelective(domain, exp);
		} catch (Exception e) {
			logger.debug("[FollowCaps] updateFollow2Normal : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		
	}
	
	public static boolean existFollowNormal(long userID, long followID) throws CoreException {
		Dao<FollowMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(FollowMapper.class);
			
			FollowExample exp = new FollowExample();
			FollowExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andFollowIDEqualTo(followID);
			criteria.andStatusEqualTo(Status.Follow.NORMAL);
			
			return dao.mapper().countByExample(exp) > 0 ? true : false; 
		} catch (Exception e) {
			logger.debug("[FollowCaps] existFollowNormal : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static Follow getFollow(long userID, long followID) throws CoreException {
		Dao<FollowMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(FollowMapper.class);
			
			FollowExample exp = new FollowExample();
			FollowExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andFollowIDEqualTo(followID);
			
			List<Follow> res = dao.mapper().selectByExample(exp);
			
			return res == null || res.isEmpty() ? null : res.get(0);
		} catch (Exception e) {
			logger.debug("[FollowCaps] getFollow : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static List<Follow> getFollowByUserID(long userID) throws CoreException {
		
		Dao<FollowMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(FollowMapper.class);
			
			FollowExample exp = new FollowExample();
			FollowExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andStatusEqualTo(Status.Follow.NORMAL);
			
			List<Follow> res = dao.mapper().selectByExample(exp);
			
			return res == null ? new ArrayList<Follow>() : res;
		} catch (Exception e) {
			logger.debug("[FollowCaps] getFollowByUserID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	

}
