package com.csm.straining.core.user;

import java.util.List;
import java.util.Set;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status.User;
import com.csm.straining.common.i.contact.entity.GroupEntity;
import com.csm.straining.common.i.training.Entity.TrainingEntity;
import com.csm.straining.common.i.training.params.TrainingItemParams;
import com.csm.straining.common.i.training.params.TrainingParams;
import com.csm.straining.common.i.user.UserService;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.common.i.user.params.UserParams;
import com.csm.straining.core.contact.core.ContactCore;
import com.csm.straining.core.training.core.TrainingCore;
import com.csm.straining.core.user.core.UserCore;
import com.csm.straining.core.user.util.UserHelper;


/**
 * @author chensongming
 */
public class UserServiceImpl implements UserService{
	
	/** --------------------User------------------ **/
	
	/**
	 * 插入用户
	 * @throws AppException 
	 * @throws CoreException 
	 */
	@Override
	public UserEntity createUserAccount(String phone, String password) throws CoreException, AppException {
		return UserCore.createUserAccount(phone, password);
	}
	
	/**
	 * 根据用户ID获取用户
	 * @throws CoreException 
	 */
	@Override
	public UserEntity getUserByID(long userID) throws CoreException {
		return UserCore.getUserByID(userID);
	}
	
	/**
	 * 注册电话登录
	 */
	@Override
	public long loginByPhonePwd(String phone, String password) throws CoreException, AppException {
		return UserCore.loginByPhonePwd(phone, password);
	}
	
	/**
	 * 更新用户资料
	 */
	@Override
	public void updateUserDetail(long optUserID, UserParams params) throws CoreException, AppException {
		UserCore.updateUserDetail(optUserID, params);
	}
	
	@Override
	public List<UserEntity> getUserEntitiesByScoreTop20() throws CoreException, AppException {
		return UserCore.getUsersOrderByScoreTop20();
	}
	
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
	public void quitGroup(long userID, long groupID) throws CoreException, AppException {
		ContactCore.quitGroup(userID, groupID);
	}

	@Override
	public TrainingEntity createTraining(TrainingParams params) throws AppException,
			CoreException {
		return TrainingCore.createTraining(params);
	}

	@Override
	public void startTraining(long userID, long trainingID) throws CoreException,
			AppException {
		TrainingCore.startTraining(userID, trainingID);
	}

	@Override
	public void finishTraining(long userID, long trainingID, int consumTime, List<TrainingItemParams> trainingItems) throws CoreException,
			AppException {
		TrainingCore.finishTraining(userID, trainingID, consumTime, trainingItems);
	}

	@Override
	public TrainingEntity getTrainingEntityByID(long trainingID)
			throws CoreException, AppException {
		return TrainingCore.getTrainingEntityByID(trainingID);
	}

	@Override
	public List<TrainingEntity> getTrainingEntitiesByUserID(long userID,
			long start, int count) throws CoreException, AppException {
		return TrainingCore.getTrainingEntitiesByUserID(userID, start, count);
	}
	
	

}
