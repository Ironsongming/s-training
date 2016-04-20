package com.csm.straining.user.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.contact.entity.GroupEntity;
import com.csm.straining.common.i.contact.info.GroupInfo;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.common.model.info.common.ResponseStatus;
import com.csm.straining.core.user.util.UserHelper;
import com.csm.straining.user.refer.UserServiceReference;
import com.csm.straining.user.resp.contact.GroupDetailResp;
import com.csm.straining.user.resp.contact.GroupListResp;


/**
 * @author chensongming
 */
public class ContactService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ContactService.class);
	
	public static ResponseStatus follow(long optUserID, long targetUserID) throws AppException, CoreException {
		if (targetUserID <= 0) {
			throw new AppException("用户不存在");
		}
		
		UserServiceReference.sharedService().follow(optUserID, targetUserID);
		return new ResponseStatus();
	}
	
	public static ResponseStatus unfollow(long optUserID, long targetUserID) throws AppException, CoreException {
		if (targetUserID <= 0) {
			throw new AppException("用户不存在");
		}
		
		UserServiceReference.sharedService().unfollow(optUserID, targetUserID);
		return new ResponseStatus();
	}
	
	public static ResponseStatus quitGroup(long optUserID, long groupID) throws CoreException, AppException {
		
		if (groupID <= 0) {
			throw new AppException("该群不存在");
		}
		
		UserServiceReference.sharedService().quitGroup(optUserID, groupID);
		return new ResponseStatus();
	}
	
	public static GroupDetailResp createGroup(long userID, Set<Long> memberIDs) throws AppException, CoreException {
		GroupEntity groupEntity = UserServiceReference.sharedService().createGroup(userID, memberIDs);
		
		
		GroupDetailResp resp = new GroupDetailResp();
		GroupInfo groupInfo = new GroupInfo();
		
		
		groupInfo.groupID = groupEntity.getGroupID();
		groupInfo.groupName = groupEntity.getGroupName();
		groupInfo.memberCount = groupEntity.getMemberCount();
		
		for (UserEntity userEntity : groupEntity.getUserEntities()) {
			groupInfo.members.add(UserHelper.entity2Info(userEntity));
		}
		
		resp.group = groupInfo;
		return resp;
	}
	
	public static GroupDetailResp getGroupByID(long groupID) throws AppException, CoreException {
		GroupEntity groupEntity = UserServiceReference.sharedService().getRichGroupByID(groupID);
		
		
		GroupDetailResp resp = new GroupDetailResp();
		GroupInfo groupInfo = new GroupInfo();
		
		
		groupInfo.groupID = groupEntity.getGroupID();
		groupInfo.groupName = groupEntity.getGroupName();
		groupInfo.memberCount = groupEntity.getMemberCount();
		
		for (UserEntity userEntity : groupEntity.getUserEntities()) {
			groupInfo.members.add(UserHelper.entity2Info(userEntity));
		}
		
		resp.group = groupInfo;
		return resp;
	}

	public static GroupListResp getGroups(long userID) throws CoreException {
		GroupListResp resp = new GroupListResp();
		
		List<GroupEntity> groupEntities = UserServiceReference.sharedService().getSimpleGroupsByUserID(userID);
		
		for (GroupEntity groupEntity : groupEntities) {
			GroupInfo groupInfo = new GroupInfo();
			groupInfo.groupID = groupEntity.getGroupID();
			groupInfo.groupName = groupEntity.getGroupName();
			groupInfo.memberCount = groupEntity.getMemberCount();
			resp.groups.add(groupInfo);
		}
		return resp;
	}
	
}
