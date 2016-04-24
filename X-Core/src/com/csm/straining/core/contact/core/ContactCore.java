package com.csm.straining.core.contact.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.common.i.contact.entity.GroupEntity;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.core.user.core.UserCore;
import com.csm.straining.dataaccess.caps.contact.FollowCaps;
import com.csm.straining.dataaccess.caps.contact.GroupCaps;
import com.csm.straining.dataaccess.caps.contact.GroupUserCaps;
import com.csm.straining.dataaccess.caps.user.UserCaps;
import com.csm.straining.dataaccess.entity.contact.Follow;
import com.csm.straining.dataaccess.entity.contact.Group;
import com.csm.straining.dataaccess.entity.contact.GroupUser;


/**
 * @author chensongming
 */
public class ContactCore {
	
	private static final Logger logger = LoggerFactory.getLogger(ContactCore.class);
	
	/** --------------------好友相关------------------- **/
	public static void follow(long userID, long followUserID) throws AppException, CoreException {
		
		if (userID <= 0 || !UserCore.existUserID(userID)) {
			throw new AppException("操作用户不存在");
		}
		
		if (followUserID <= 0 || !UserCore.existUserID(followUserID)) {
			throw new AppException("用户不存在");
		}
		
		if (userID == followUserID) {
			throw new AppException("数据错误");
		}
		
		Follow follow = FollowCaps.getFollow(userID, followUserID);
		if (follow != null) {
			if (follow.getStatus() == Status.Follow.NORMAL) {
				throw new AppException("你已关注该用户");
			} else {
				FollowCaps.updateFollow2Normal(userID, followUserID);
				return;
			}
		}
		
		
		Follow domain = new Follow();
		domain.setUserID(userID);
		domain.setFollowID(followUserID);
		domain.setCreateAt(new Date());
		
		FollowCaps.insertFollow(domain);
	}
	
	public static void unfollow(long userID, long followUserID) throws AppException, CoreException {
		
		if (userID <= 0 || !UserCore.existUserID(userID)) {
			throw new AppException("操作用户不存在");
		}
		
		if (followUserID <= 0 || !UserCore.existUserID(followUserID)) {
			throw new AppException("用户不存在");
		}
		
		if (userID == followUserID) {
			throw new AppException("数据错误");
		}
		
		FollowCaps.delFollowByID(userID, followUserID);
	}
	
	public static List<Long> getFollowByUser(long userID) throws CoreException {
		List<Long> followUserIDs = new ArrayList<Long>();
		List<Follow> follows = FollowCaps.getFollowByUserID(userID);
		
		for (Follow follow : follows) {
			followUserIDs.add(follow.getFollowID());
		}
		return followUserIDs;
	}
	
	public static List<UserEntity> getFollowEntityByUser(long userID) throws CoreException {
		List<Long> followUserIDs = new ArrayList<Long>();
		List<Follow> follows = FollowCaps.getFollowByUserID(userID);
		
		for (Follow follow : follows) {
			followUserIDs.add(follow.getFollowID());
		}
		
		return UserCore.getSimpleUserByIDs(followUserIDs);
	}
	
	public static boolean isFollow(long userID, long followID) throws CoreException {
		return FollowCaps.existFollowNormal(userID, followID);
	}
	
	/** --------------------群相关------------------- **/
	public static GroupEntity createGroup(long userID, Set<Long> memberIDs) throws AppException, CoreException {
		
		if (memberIDs == null || memberIDs.isEmpty()) {
			throw new AppException("请选择成员");
		}
		
		if (!memberIDs.contains(userID)) {
			throw new AppException("数据错误");
		}
		
		if (memberIDs.size() == 1) {
			throw new AppException("请选择成员");
		}
		
		List<UserEntity> userEntities = UserCore.getSimpleUserByIDs(new ArrayList<Long>(memberIDs));
		
		if (userEntities.isEmpty()) {
			throw new AppException("请选择成员");
		}
		
		if (userEntities.size() != memberIDs.size()) {
			throw new AppException("部分成员不存在");
		}
		
		GroupEntity groupEntity = new GroupEntity();
		
		Date now = new Date();
		Group group = new Group();
		group.setUserID(userID);
		group.setCreateAt(now);
		group.setMemberCount(memberIDs.size());
		
		StringBuilder sb = new StringBuilder();
		for (UserEntity userEntity : userEntities) {
			sb.append(userEntity.getUsername());
		}
		
		if (sb.length() > 30) {
			group.setGroupName(sb.substring(0, 30) + "...");
		} else {
			group.setGroupName(sb.toString());
		}
		
		GroupCaps.insertGroup(group);
		
		Set<GroupUser> domains = new HashSet<GroupUser>();
		for (UserEntity userEntity : userEntities) {
			GroupUser domain = new GroupUser();
			domain.setCreateAt(now);
			domain.setGroupID(group.getId());
			domain.setUserID(userEntity.getUserID());
			domains.add(domain);
		}
		GroupUserCaps.multiInsertGroupUser(domains);
		
		
		groupEntity.setUserEntities(userEntities);
		groupEntity.setGroupID(group.getId());
		groupEntity.setGroupName(group.getGroupName());
		groupEntity.setMemberCount(group.getMemberCount());
		
		return groupEntity;
	}
	
	public static GroupEntity getRichGroupByID(long groupID) throws CoreException, AppException {
		Group group = GroupCaps.getGroupByID(groupID);
		
		if (group == null) {
			throw new AppException("该群不存在");
		}
		
		List<GroupUser> groupUsers = GroupUserCaps.getGroupUsersByGroupID(groupID);
		Set<Long> preLoadUserIDs = new HashSet<Long>();
		for (GroupUser groupUser : groupUsers) {
			preLoadUserIDs.add(groupUser.getUserID());
		}
		
		List<UserEntity> users = UserCore.getSimpleUserByIDs(new ArrayList<Long>(preLoadUserIDs));
		
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setGroupID(groupID);
		groupEntity.setGroupName(group.getGroupName());
		groupEntity.setMemberCount(group.getMemberCount());
		
		groupEntity.setUserEntities(users);
		
		return groupEntity;
	}
	
	public static List<GroupEntity> getSimpleGroupsByUserID(long userID) throws CoreException {
		List<GroupUser> groupUsers = GroupUserCaps.getGroupUsersByUserID(userID);
		
		List<Long> preLoadGroupIDs = new ArrayList<Long>();
		for (GroupUser groupUser : groupUsers) {
			preLoadGroupIDs.add(groupUser.getGroupID());
		}
		
		List<Group> groups = GroupCaps.getGroupByIDs(preLoadGroupIDs);
		
		List<GroupEntity> groupEntities = new ArrayList<GroupEntity>();
		
		for (Group group : groups) {
			GroupEntity groupEntity = new GroupEntity();
			groupEntity.setGroupID(group.getId());
			groupEntity.setGroupName(group.getGroupName());
			groupEntity.setMemberCount(group.getMemberCount());
			
			groupEntities.add(groupEntity);
		}
		
		return groupEntities;
	}
	
	/** --------------------群成员相关------------------- **/
	public static void quitGroup(long userID, long groupID) throws CoreException, AppException{
		Group group = GroupCaps.getGroupByID(groupID);
		if (group == null) {
			throw new AppException("该群不存在");
		}
		
		GroupUser groupUser = GroupUserCaps.getGroupUser(userID, groupID);
		if (groupUser == null) {
			throw new AppException("你仍没加入该群，无法执行退出操作");
		}
		
		GroupUserCaps.delGroupUser(userID, groupID);
		GroupCaps.descGroupMemberCount(groupID);
	}
}
