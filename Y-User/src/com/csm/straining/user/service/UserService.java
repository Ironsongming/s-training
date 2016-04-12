package com.csm.straining.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.strainging.cache.impl.session.SessionKeyCache;
import com.csm.strainging.cache.impl.user.UserInfoCache;
import com.csm.strainging.cache.support.UserCacheSupport;
import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.common.util.CommonUtil;
import com.csm.straining.user.refer.UserServiceReference;
import com.csm.straining.user.resp.PhoneLoginResp;
import com.csm.straining.user.resp.UserCreateResp;


/**
 * @author chensongming
 */
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public static UserCreateResp userCreateResp(String phone, String password) throws CoreException, AppException {
		
		UserCreateResp resp = new UserCreateResp();
		
		UserEntity userEntity = UserServiceReference.sharedService().createUserAccount(phone, password);
		
		// 添加Session
		String key = assignAndLogin(userEntity.getUserID());
		resp.key = key;
		resp.userID = userEntity.getUserID();
		
		return resp;
	}
	
	public static PhoneLoginResp phoneLoginResp(String phone, String password) throws CoreException, AppException {
		PhoneLoginResp resp = new PhoneLoginResp();
		
		long userID = UserServiceReference.sharedService().loginByPhonePwd(phone, password);
		
		// 添加Session
		String key = assignAndLogin(userID);
		
		UserEntity user = UserServiceReference.sharedService().getUserByID(userID);
		
		if (user == null || user.getStatus() != Status.User.NORMAL) {
			logger.debug("data error loginByPhonePwd & getUserByID from user Status: " + user == null ? "user is null" : user.getStatus() + "");
		}
		
		resp.key = key;
		resp.userID = user.getUserID();
		
		return resp;
	}
	
	private static String assignAndLogin(long userID) {
		
		String sessionKey = CommonUtil.getUUID(userID);
		
		SessionKeyCache.setAndExpire(sessionKey, userID);
		
		String oldKey = UserInfoCache.getSessionKey(userID);
		if (null != oldKey) {
			SessionKeyCache.del(oldKey);
//			forceOldKeyLogout(userID, oldKey);
		}
		
		UserInfoCache.setSessionKey(userID, sessionKey);
		
		return sessionKey;
	}
	
//	private static void forceOldKeyLogout(long userID, String offlineKey) {
//		
//	}

}
