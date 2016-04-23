package com.csm.straining.dataaccess.caps.moment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.dataaccess.Dao;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.entity.moment.MomentLike;
import com.csm.straining.dataaccess.entity.moment.MomentLikeExample;
import com.csm.straining.dataaccess.mapper.moment.MomentLikeMapper;


/**
 * @author chensongming
 */
public class MomentLikeCaps {
	
	private static final Logger logger = LoggerFactory.getLogger(MomentLikeCaps.class);
	
	public static MomentLike getMomentLike(long userID, long momentID) throws CoreException {
		Dao<MomentLikeMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(MomentLikeMapper.class);
			
			MomentLikeExample exp = new MomentLikeExample();
			MomentLikeExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andMomentIDEqualTo(momentID);
			
			List<MomentLike> res = dao.mapper().selectByExample(exp);
			return res == null || res.isEmpty() ? null : res.get(0);
		} catch (Exception e) {
			logger.debug("[MomentLikeCaps] getMomentLike : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static MomentLike insertMomentLike(MomentLike domain) throws CoreException {
		Dao<MomentLikeMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(MomentLikeMapper.class);
			dao.mapper().insertSelective(domain);
		} catch (Exception e) {
			logger.debug("[MomentLikeCaps] insertMomentLike : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		return domain;
	}
	
	public static void cancel(long userID, long momentID) throws CoreException {
		Dao<MomentLikeMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(MomentLikeMapper.class);
			
			MomentLike domain = new MomentLike();
			domain.setStatus(Status.MomentLike.CANCEL);
			
			MomentLikeExample exp = new MomentLikeExample();
			MomentLikeExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andMomentIDEqualTo(momentID);

			dao.mapper().updateByExampleSelective(domain, exp);
			
		} catch (Exception e) {
			logger.debug("[MomentLikeCaps] cancel : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void like(long userID, long momentID) throws CoreException {
		Dao<MomentLikeMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(MomentLikeMapper.class);
			
			MomentLike domain = new MomentLike();
			domain.setCreateAt(new Date());
			domain.setStatus(Status.MomentLike.NORMAL);
			
			MomentLikeExample exp = new MomentLikeExample();
			MomentLikeExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andMomentIDEqualTo(momentID);

			dao.mapper().updateByExampleSelective(domain, exp);
			
		} catch (Exception e) {
			logger.debug("[MomentLikeCaps] like : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static List<MomentLike> getMomentLikesByMomentID(long momentID) throws CoreException {
		
		Dao<MomentLikeMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(MomentLikeMapper.class);
			
			MomentLikeExample exp = new MomentLikeExample();
			MomentLikeExample.Criteria criteria = exp.createCriteria();
			
			exp.setDistinct(true);
			exp.setOrderByClause("id desc");
			criteria.andMomentIDEqualTo(momentID);
			criteria.andStatusEqualTo(Status.MomentLike.NORMAL);
			
			List<MomentLike> res = dao.mapper().selectByExample(exp);
			return res == null ? new ArrayList<MomentLike>() : res;
			
		} catch (Exception e) {
			logger.debug("[MomentLikeCaps] getMomentLikesByMomentID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static List<MomentLike> getMomentLikesByMomentIDs(List<Long> momentIDs) throws CoreException {
		
		if (momentIDs == null || momentIDs.isEmpty()) {
			return new ArrayList<MomentLike>();
		}
		
		Dao<MomentLikeMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(MomentLikeMapper.class);
			
			MomentLikeExample exp = new MomentLikeExample();
			MomentLikeExample.Criteria criteria = exp.createCriteria();
			
			exp.setDistinct(true);
			exp.setOrderByClause("id desc");
			criteria.andMomentIDIn(momentIDs);
			criteria.andStatusEqualTo(Status.MomentLike.NORMAL);
			
			List<MomentLike> res = dao.mapper().selectByExample(exp);
			return res == null ? new ArrayList<MomentLike>() : res;
			
		} catch (Exception e) {
			logger.debug("[MomentLikeCaps] getMomentLikesByMomentIDs : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	
	
	
}
