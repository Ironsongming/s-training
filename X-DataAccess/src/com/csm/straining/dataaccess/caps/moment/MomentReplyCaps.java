package com.csm.straining.dataaccess.caps.moment;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.dataaccess.Dao;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.entity.moment.MomentReply;
import com.csm.straining.dataaccess.entity.moment.MomentReplyExample;
import com.csm.straining.dataaccess.mapper.moment.MomentReplyMapper;


/**
 * @author chensongming
 */
public class MomentReplyCaps {
	
	private static final Logger logger = LoggerFactory.getLogger(MomentReplyCaps.class);
	
	public static MomentReply getMomentReplyByID(long id) throws CoreException {
		Dao<MomentReplyMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(MomentReplyMapper.class);
			
			MomentReplyExample exp = new MomentReplyExample();
			MomentReplyExample.Criteria criteria = exp.createCriteria();
			
			criteria.andIdEqualTo(id);
			criteria.andStatusEqualTo(Status.MomentReply.NORMAL);
			
			List<MomentReply> res = dao.mapper().selectByExample(exp);
			return res == null || res.isEmpty() ? null : res.get(0);
		} catch (Exception e) {
			logger.debug("[MomentReplyCaps] getMomentReplyByID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static MomentReply insertMomentReply(MomentReply domain) throws CoreException {
		Dao<MomentReplyMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(MomentReplyMapper.class);
			dao.mapper().insertSelective(domain);
		} catch (Exception e) {
			logger.debug("[MomentReplyCaps] insertMomentReply : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		return domain;
	}
	
	public static void delMomentReplyByID(long momentReplyID) throws CoreException {
		Dao<MomentReplyMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(MomentReplyMapper.class);
			
			MomentReply domain = new MomentReply();
			domain.setId(momentReplyID);
			domain.setStatus(Status.MomentReply.DELETE);
			
			dao.mapper().updateByPrimaryKeySelective(domain);
			
		} catch (Exception e) {
			logger.debug("[MomentReplyCaps] delMomentReplyByID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static List<MomentReply> getMomentRepliesByMomentID(long momentID) throws CoreException {
		
		Dao<MomentReplyMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(MomentReplyMapper.class);
			
			MomentReplyExample exp = new MomentReplyExample();
			MomentReplyExample.Criteria criteria = exp.createCriteria();
			
			exp.setDistinct(true);
			exp.setOrderByClause("id asc");
			criteria.andMomentIDEqualTo(momentID);
			criteria.andStatusEqualTo(Status.MomentReply.NORMAL);
			
			List<MomentReply> res = dao.mapper().selectByExample(exp);
			return res == null ? new ArrayList<MomentReply>() : res;
			
		} catch (Exception e) {
			logger.debug("[MomentReplyCaps] getMomentRepliesByMomentID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static List<MomentReply> getMomentRepliesByMomentIDs(List<Long> momentIDs) throws CoreException {
		
		if (momentIDs == null || momentIDs.isEmpty()) {
			return new ArrayList<MomentReply>();
		}
		
		Dao<MomentReplyMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(MomentReplyMapper.class);
			
			MomentReplyExample exp = new MomentReplyExample();
			MomentReplyExample.Criteria criteria = exp.createCriteria();
			
			exp.setDistinct(true);
			exp.setOrderByClause("id asc");
			criteria.andMomentIDIn(momentIDs);
			criteria.andStatusEqualTo(Status.MomentReply.NORMAL);
			
			List<MomentReply> res = dao.mapper().selectByExample(exp);
			return res == null ? new ArrayList<MomentReply>() : res;
			
		} catch (Exception e) {
			logger.debug("[MomentCaps] getMomentRepliesByMomentIDs : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	
	
	
}
