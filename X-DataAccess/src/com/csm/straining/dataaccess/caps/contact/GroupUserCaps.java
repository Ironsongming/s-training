package com.csm.straining.dataaccess.caps.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.dataaccess.Dao;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.entity.contact.GroupUser;
import com.csm.straining.dataaccess.entity.contact.GroupUserExample;
import com.csm.straining.dataaccess.mapper.contact.GroupCusMapper;
import com.csm.straining.dataaccess.mapper.contact.GroupUserMapper;


/**
 * @author chensongming
 */
public class GroupUserCaps {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupUserCaps.class);
	
	
	public static void multiInsertGroupUser(Set<GroupUser> domains) throws CoreException {
		
		if (domains == null || domains.isEmpty()) {
			return;
		}
		
		Dao<GroupCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(GroupCusMapper.class);
			dao.mapper().multiInsertGroupUser(new ArrayList<GroupUser>(domains));
		} catch (Exception e) {
			logger.debug("[GroupUserCaps] multiInsertGroupUser : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void insertGroupUser(GroupUser domain) throws CoreException {
		
		Dao<GroupUserMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(GroupUserMapper.class);
			dao.mapper().insertSelective(domain);
		} catch (Exception e) {
			logger.debug("[GroupUserCaps] insertGroupUser : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void delGroupUser(long userID, long groupID) throws CoreException {
		
		Dao<GroupUserMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(GroupUserMapper.class);
			
			GroupUserExample exp = new GroupUserExample();
			GroupUserExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andGroupIDEqualTo(groupID);
			
			GroupUser domain = new GroupUser();
			domain.setStatus(Status.GroupUser.QUIT);
			dao.mapper().updateByExampleSelective(domain, exp);
		} catch (Exception e) {
			logger.debug("[GroupUserCaps] delGroupUser : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void updateGroupUser2Normal(long userID, long groupID) throws CoreException {
		
		Dao<GroupUserMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(GroupUserMapper.class);
			
			GroupUserExample exp = new GroupUserExample();
			GroupUserExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andGroupIDEqualTo(groupID);
			
			GroupUser domain = new GroupUser();
			domain.setStatus(Status.GroupUser.NORMAL);
			dao.mapper().updateByExampleSelective(domain, exp);
		} catch (Exception e) {
			logger.debug("[GroupUserCaps] updateGroupUser2Normal : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		
	}
	
	public static List<GroupUser> getGroupUsersByUserID(long userID) throws CoreException {
		
		Dao<GroupUserMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(GroupUserMapper.class);
			
			GroupUserExample exp = new GroupUserExample();
			GroupUserExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andStatusEqualTo(Status.GroupUser.NORMAL);
			List<GroupUser> res = dao.mapper().selectByExample(exp);
			return res == null ? new ArrayList<GroupUser>() : res;
		} catch (Exception e) {
			logger.debug("[GroupUserCaps] getGroupUsersByUserID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		
	}
	
	public static List<GroupUser> getGroupUsersByGroupID(long groupID) throws CoreException {
		
		Dao<GroupUserMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(GroupUserMapper.class);
			GroupUserExample exp = new GroupUserExample();
			GroupUserExample.Criteria criteria = exp.createCriteria();
			criteria.andGroupIDEqualTo(groupID);
			criteria.andStatusEqualTo(Status.GroupUser.NORMAL);
			List<GroupUser> res = dao.mapper().selectByExample(exp);
			return res == null ? new ArrayList<GroupUser>() : res;
		} catch (Exception e) {
			logger.debug("[GroupUserCaps] getGroupUsersByGroupID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		
	}
	
	public static GroupUser getGroupUser(long userID, long groupID) throws CoreException {
		
		Dao<GroupUserMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(GroupUserMapper.class);
			
			GroupUserExample exp = new GroupUserExample();
			GroupUserExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andGroupIDEqualTo(groupID);
			criteria.andStatusEqualTo(Status.GroupUser.NORMAL);
			List<GroupUser> res = dao.mapper().selectByExample(exp);
			return res == null || res.isEmpty() ? new GroupUser(): res.get(0);
		} catch (Exception e) {
			logger.debug("[GroupUserCaps] getGroupUser : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		
	}

}
