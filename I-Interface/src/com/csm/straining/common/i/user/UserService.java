package com.csm.straining.common.i.user;

import java.util.List;
import java.util.Set;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.contact.entity.GroupEntity;
import com.csm.straining.common.i.training.Entity.TrainingEntity;
import com.csm.straining.common.i.training.params.TrainingItemParams;
import com.csm.straining.common.i.training.params.TrainingParams;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.common.i.user.params.UserParams;


/**
 * @author chensongming
 */
public interface UserService {

	
	/** --------------------User------------------ **/
	
	UserEntity createUserAccount(String phone, String password) throws CoreException, AppException;
	
	UserEntity getUserByID(long userID) throws CoreException;
	
	long loginByPhonePwd(String phone, String password) throws CoreException, AppException;
	
	void updateUserDetail(long optUserID, UserParams params) throws CoreException, AppException;
	
	List<UserEntity> getUserEntitiesByScoreTop20() throws CoreException, AppException;
	
	
	/** --------------------Contact------------------ **/
	void follow(long userID, long followUserID) throws AppException, CoreException;
	
	void unfollow(long userID, long followUserID) throws AppException, CoreException;
	
	List<Long> getFollowByUser(long userID) throws CoreException;
	
	List<UserEntity> getFollowEntityByUser(long userID) throws CoreException;
	
	boolean isFollow(long userID, long followID) throws CoreException;
	
	GroupEntity createGroup(long userID, Set<Long> memberIDs) throws AppException, CoreException;
	
	GroupEntity getRichGroupByID(long groupID) throws CoreException, AppException;
	
	List<GroupEntity> getSimpleGroupsByUserID(long userID) throws CoreException;
	
	void quitGroup(long userID, long groupID) throws CoreException, AppException;
	
	/** --------------------training------------------ **/
	TrainingEntity createTraining(TrainingParams params) throws AppException, CoreException;
	
	void startTraining(long userID, long trainingID) throws CoreException, AppException;
	
	void finishTraining(long userID, long trainingID, int consumTime, List<TrainingItemParams> trainingItems) throws CoreException, AppException;
	
	TrainingEntity getTrainingEntityByID(long trainingID) throws CoreException, AppException;
	
	List<TrainingEntity> getTrainingEntitiesByUserID(long userID, long start, int count) throws CoreException, AppException;
	
}
