package com.csm.straining.core.user.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.common.i.user.params.UserParams;
import com.csm.straining.core.user.util.UserHelper;
import com.csm.straining.dataaccess.caps.user.UserCaps;
import com.csm.straining.dataaccess.caps.user.UserRankCaps;
import com.csm.straining.dataaccess.entity.user.User;
import com.csm.straining.dataaccess.entity.user.UserRank;
import com.lamfire.code.MD5;


/**
 * @author chensongming
 */
public class UserCore {

	private static final Logger logger = LoggerFactory.getLogger(UserCore.class);
	
	
	public static boolean existUserID(long userID) throws CoreException {
		return UserCaps.existUserID(userID);
	}
	
	public static boolean existUserIDs(Set<Long> userIDs) throws CoreException {
		return UserCaps.existUserIDs(userIDs);
	}
	
	public static List<UserEntity> getUsersOrderByScoreTop20() throws CoreException {
		List<UserRank> userRanks = UserRankCaps.getUserRanksTop20();
		
		List<Long> preLoadUserIDs = new ArrayList<Long>();
 		for (UserRank userRank : userRanks) {
			preLoadUserIDs.add(userRank.getUserID());
		}
 		
 		List<UserEntity> userEntities = new ArrayList<UserEntity>();
 		Map<Long, UserEntity> userMap = getSimpleUserMapByIDs(preLoadUserIDs);
 		for (UserRank userRank : userRanks) {
 			UserEntity userEntity = userMap.get(userRank.getUserID());
 			userEntity.setRank(userRank.getRank());
 			userEntity.setScore(userRank.getScore());
 			userEntities.add(userEntity);
		}
 		return userEntities;
	}
	
	public static UserEntity createUserAccount(String phone, String password) throws CoreException, AppException {
		
		if (phone.length() != 11) {
			throw new AppException("手机号码不合法");
		}
		
		if (password.length() > 16) {
			throw new AppException("密码过长");
		}
		
		if (password.contains(",") || password.contains("\\") || password.contains("\"") || 
				password.contains("\'") || password.contains("`") || password.contains(".") || password.contains("?")) {
			throw new AppException("密码包含不合法字符");
		}
		
		User authDomain = UserCaps.getUserByPhone(phone);
		if (authDomain != null) {
			throw new AppException("该号码已经被注册了");
		}
		
		User domain = new User();
		domain.setPhone(phone);
		domain.setPassword(MD5.sign(password));
		domain.setCreateAt(new Date());
		
		return UserHelper.domain2Entity(UserCaps.insertUser(domain));
	}
	
	public static UserEntity createUserAccount(User domain) throws CoreException {
		return UserHelper.domain2Entity(UserCaps.insertUser(domain));
	}
	
	public static UserEntity getUserByID(long userID) throws CoreException {
		UserEntity userEntity =  UserHelper.domain2Entity(UserCaps.getUserByID(userID));
		
		if (userEntity == null) {
			return null;
		}
		
		UserRank userRank = UserRankCaps.getUserRankByUserID(userID);
		if (null != userRank) {
			userEntity.setScore(userRank.getScore());
			userEntity.setRank(userRank.getRank());
		} else {
			userEntity.setScore(0);
			userEntity.setRank(0);
		}
		return userEntity;
	}
	
	public static UserEntity getSimpleUserByID(long userID) throws CoreException {
		return UserHelper.domain2Entity(UserCaps.getUserByID(userID));
	}
	
	public static List<UserEntity> getSimpleUserByIDs(List<Long> userIDs) throws CoreException {
		return UserHelper.domain2Entity(UserCaps.getUserByIDs(userIDs));
	}
	
	public static Map<Long, UserEntity> getSimpleUserMapByIDs(List<Long> userIDs) throws CoreException {
		return UserHelper.domain2EntityMap(UserCaps.getUserByIDs(userIDs));
	}
	
	public static long loginByPhonePwd(String phone, String password) throws CoreException, AppException {
		User domain = UserCaps.getUserByPhone(phone);
		
		if (domain == null) {
			throw new AppException("该用户不存在");
		}
		
		if (MD5.sign(password).equals(domain.getPassword())) {
			return domain.getId();
		} else {
			throw new AppException("密码不一致");
		}
	}
	
	public static void updateUserDetail(long optUserID, UserParams params) throws CoreException, AppException {
		
		if (optUserID != params.getUserID()) {
			throw new AppException("你不能修改其他用户的资料");
		}
 		
		User user = new User();
		user.setId(params.getUserID());
		user.setUsername(params.getUsername());
		user.setPassword(params.getPassword());
		user.setAvatar(params.getAvatar());
		user.setSignNature(params.getSignNature());
		
		UserCaps.updateUserDetail(user);
	}
}
