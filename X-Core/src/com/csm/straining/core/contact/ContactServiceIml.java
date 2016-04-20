package com.csm.straining.core.contact;

import java.util.List;
import java.util.Set;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.contact.ContactService;
import com.csm.straining.common.i.contact.entity.GroupEntity;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.core.contact.core.ContactCore;


/**
 * @author chensongming
 */
public class ContactServiceIml implements ContactService {

	@Override
	public void follow(long userID, long followUserID) throws AppException,
			CoreException {
		ContactCore.follow(userID, followUserID);
	}

	@Override
	public void unfollow(long userID, long followUserID) throws AppException,
			CoreException {
		ContactCore.unfollow(userID, followUserID);
	}

	@Override
	public List<Long> getFollowByUser(long userID) throws CoreException {
		return ContactCore.getFollowByUser(userID);
	}
	
	@Override
	public List<UserEntity> getFollowEntityByUser(long userID) throws CoreException {
		return ContactCore.getFollowEntityByUser(userID);
	}

	@Override
	public boolean isFollow(long userID, long followID) throws CoreException {
		return ContactCore.isFollow(userID, followID);
	}

	@Override
	public GroupEntity createGroup(long userID, Set<Long> memberIDs)
			throws AppException, CoreException {
		return ContactCore.createGroup(userID, memberIDs);
	}

	@Override
	public GroupEntity getRichGroupByID(long groupID) throws CoreException,
			AppException {
		return ContactCore.getRichGroupByID(groupID);
	}

	@Override
	public List<GroupEntity> getSimpleGroupsByUserID(long userID)
			throws CoreException {
		return ContactCore.getSimpleGroupsByUserID(userID);
	}

	@Override
	public void quitGroup(long userID, long groupID) throws CoreException {
		ContactCore.quitGroup(userID, groupID);
	}

}
