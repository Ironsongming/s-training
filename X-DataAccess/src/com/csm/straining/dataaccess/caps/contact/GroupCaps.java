package com.csm.straining.dataaccess.caps.contact;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.CoreException;
import com.csm.straining.dataaccess.Dao;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.entity.contact.Group;
import com.csm.straining.dataaccess.entity.contact.GroupExample;
import com.csm.straining.dataaccess.mapper.contact.GroupCusMapper;
import com.csm.straining.dataaccess.mapper.contact.GroupMapper;


/**
 * @author chensongming
 */
public class GroupCaps {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupCaps.class);
	
	public static Group insertGroup(Group domain) throws CoreException {
		
		Dao<GroupMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(GroupMapper.class);
			dao.mapper().insertSelective(domain); 
			
			return domain;
		} catch (Exception e) {
			logger.debug("[GroupCaps] insertGroup : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static Group getGroupByID(long groupID) throws CoreException {
		Dao<GroupMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(GroupMapper.class);
			return dao.mapper().selectByPrimaryKey(groupID);
		} catch (Exception e) {
			logger.debug("[GroupCaps] insertGroup : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static List<Group> getGroupByIDs(List<Long> groupIDs) throws CoreException {
		
		if (groupIDs == null || groupIDs.isEmpty()) {
			return new ArrayList<Group>();
		}
		
		Dao<GroupMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(GroupMapper.class);
			
			GroupExample exp = new GroupExample();
			GroupExample.Criteria criteria = exp.createCriteria();
			
			exp.setDistinct(true);
			criteria.andIdIn(groupIDs);
			
			List<Group> res = dao.mapper().selectByExample(exp);
			
			return res == null ? new ArrayList<Group>() : res;
		} catch (Exception e) {
			logger.debug("[GroupCaps] getGroupByIDs : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void incrGroupMemberCount(long groupID) throws CoreException {
		
		Dao<GroupCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(GroupCusMapper.class);
			dao.mapper().incrGroupMemberCount(groupID);
		} catch (Exception e) {
			logger.debug("[GroupCaps] incrGroupMemberCount : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void descGroupMemberCount(long groupID) throws CoreException {
		
		Dao<GroupCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(GroupCusMapper.class);
			dao.mapper().descGroupMemberCount(groupID);
		} catch (Exception e) {
			logger.debug("[GroupCaps] descGroupMemberCount : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}	
	
}
