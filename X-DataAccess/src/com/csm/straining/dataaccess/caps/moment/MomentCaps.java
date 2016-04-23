package com.csm.straining.dataaccess.caps.moment;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.dataaccess.Dao;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.entity.moment.Moment;
import com.csm.straining.dataaccess.entity.moment.MomentExample;
import com.csm.straining.dataaccess.mapper.moment.MomentCusMapper;
import com.csm.straining.dataaccess.mapper.moment.MomentMapper;


/**
 * @author chensongming
 */
public class MomentCaps {
	
	private static final Logger logger = LoggerFactory.getLogger(MomentCaps.class);
	
	public static void incrTranferCount(long momentID) throws CoreException {
		Dao<MomentCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(MomentCusMapper.class);
			dao.mapper().incrTranferCount(momentID);
		} catch (Exception e) {
			logger.debug("[MomentCaps] incrTranferCount : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void incrReplyCount(long momentID) throws CoreException {
		Dao<MomentCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(MomentCusMapper.class);
			dao.mapper().incrReplyCount(momentID);
		} catch (Exception e) {
			logger.debug("[MomentCaps] incrReplyCount : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void incrLikeCount(long momentID) throws CoreException {
		Dao<MomentCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(MomentCusMapper.class);
			dao.mapper().incrLikeCount(momentID);
		} catch (Exception e) {
			logger.debug("[MomentCaps] incrLikeCount : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void descReplyCount(long momentID) throws CoreException {
		Dao<MomentCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(MomentCusMapper.class);
			dao.mapper().descReplyCount(momentID);
		} catch (Exception e) {
			logger.debug("[MomentCaps] descReplyCount : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void descLikeCount(long momentID) throws CoreException {
		Dao<MomentCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(MomentCusMapper.class);
			dao.mapper().descLikeCount(momentID);
		} catch (Exception e) {
			logger.debug("[MomentCaps] descLikeCount : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static Moment insertMoment(Moment domain) throws CoreException {
		Dao<MomentMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(MomentMapper.class);
			dao.mapper().insertSelective(domain);
		} catch (Exception e) {
			logger.debug("[MomentCaps] insertMoment : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		return domain;
	}
	
	public static boolean existMomentNormal(long momentID) throws CoreException {
		
		Dao<MomentMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(MomentMapper.class);

			MomentExample exp = new MomentExample();
			MomentExample.Criteria criteria = exp.createCriteria();
			
			criteria.andIdEqualTo(momentID);
			criteria.andStatusEqualTo(Status.Moment.NORMAL);
			
			return dao.mapper().countByExample(exp) > 0 ? true : false;
 		} catch (Exception e) {
			logger.debug("[MomentCaps] existMomentNormal : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static Moment getMomentByID(long momentID) throws CoreException {
		
		Dao<MomentMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(MomentMapper.class);
			
			MomentExample exp = new MomentExample();
			MomentExample.Criteria criteria = exp.createCriteria();
			
			criteria.andIdEqualTo(momentID);
			criteria.andStatusEqualTo(Status.Moment.NORMAL);
			
			List<Moment> res = dao.mapper().selectByExample(exp);
			
			return res == null || res.isEmpty() ? null : res.get(0);
			
		} catch (Exception e) {
			logger.debug("[MomentCaps] getMomentByID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void delMomentByID(long momentID) throws CoreException {
		Dao<MomentMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(MomentMapper.class);
			
			Moment domain = new Moment();
			domain.setId(momentID);
			domain.setStatus(Status.Moment.DELETE);
			
			dao.mapper().updateByPrimaryKeySelective(domain);
		} catch (Exception e) {
			logger.debug("[MomentCaps] delMomentByID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static List<Moment> getMomentsByUserID(long userID, long start, int count) throws CoreException {
		
		count = count <= 0 ? 20 : count;
		
		Dao<MomentMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(MomentMapper.class);
			
			MomentExample exp = new MomentExample();
			MomentExample.Criteria criteria = exp.createCriteria();
			
			exp.setOrderByClause("id desc");
			exp.setDistinct(true);
			exp.setLimit(count);
			
			if (start > 0) {
				criteria.andIdLessThan(start);
			}

			criteria.andUserIDEqualTo(userID);
			criteria.andStatusEqualTo(Status.Moment.NORMAL);
			
			List<Moment> res = dao.mapper().selectByExample(exp);
			return res == null ? new ArrayList<Moment>() : res;
		} catch (Exception e) {
			logger.debug("[MomentCaps] getMomentsByUserID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}	
	}
	
	public static List<Moment> getMomentsByUserIDs(List<Long> userIDs, long start , int count) throws CoreException {
		
		if (userIDs == null || userIDs.isEmpty()) {
			return new ArrayList<Moment>();
		}
		
		count = count <= 0 ? 20 : count;
 		
		Dao<MomentMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(MomentMapper.class);
			
			MomentExample exp = new MomentExample();
			MomentExample.Criteria criteria = exp.createCriteria();
			
			exp.setOrderByClause("id desc");
			exp.setDistinct(true);
			exp.setLimit(count);

			if (start > 0) {
				criteria.andIdLessThan(start);
			}
			
			criteria.andUserIDIn(userIDs);
			criteria.andStatusEqualTo(Status.Moment.NORMAL);
			
			List<Moment> res = dao.mapper().selectByExample(exp);
			return res == null ? new ArrayList<Moment>() : res;
		} catch (Exception e) {
			logger.debug("[MomentCaps] getMomentsByUserID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static List<Moment> getMomentsByIDs(List<Long> momentIDs) throws CoreException {
		
		if (momentIDs == null || momentIDs.isEmpty()) {
			return new ArrayList<Moment>();
		}
		
		Dao<MomentMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(MomentMapper.class);
			
			MomentExample exp = new MomentExample();
			MomentExample.Criteria criteria = exp.createCriteria();

			exp.setDistinct(true);

			criteria.andIdIn(momentIDs);
			criteria.andStatusEqualTo(Status.Moment.NORMAL);
			
			List<Moment> res = dao.mapper().selectByExample(exp);
			return res == null ? new ArrayList<Moment>() : res;
		} catch (Exception e) {
			logger.debug("[MomentCaps] getMomentsByIDs : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	
	

}
