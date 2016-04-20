package com.csm.straining.common.i.contact;

import java.util.List;
import java.util.Set;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.contact.entity.GroupEntity;
import com.csm.straining.common.i.user.entity.UserEntity;


/**
 * @author chensongming
 */
public interface ContactService {
	
	void follow(long userID, long followUserID) throws AppException, CoreException;
	
	void unfollow(long userID, long followUserID) throws AppException, CoreException;
	
	List<Long> getFollowByUser(long userID) throws CoreException;
	
	List<UserEntity> getFollowEntityByUser(long userID) throws CoreException;
	
	boolean isFollow(long userID, long followID) throws CoreException;
	
	GroupEntity createGroup(long userID, Set<Long> memberIDs) throws AppException, CoreException;
	
	GroupEntity getRichGroupByID(long groupID) throws CoreException, AppException;
	
	List<GroupEntity> getSimpleGroupsByUserID(long userID) throws CoreException;
	
	void quitGroup(long userID, long groupID) throws CoreException;

}
