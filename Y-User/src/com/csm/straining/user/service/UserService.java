package com.csm.straining.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.common.i.user.entity.UserEntity;
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
		resp.key = "597981586";
		
		UserEntity userEntity = UserServiceReference.sharedService().createUserAccount(phone, password);
		return resp;
	}
	
	public static PhoneLoginResp phoneLoginResp(String phone, String password) throws CoreException, AppException {
		PhoneLoginResp resp = new PhoneLoginResp();
		
		long userID = UserServiceReference.sharedService().loginByPhonePwd(phone, password);
		
		
		
		UserEntity user = UserServiceReference.sharedService().getUserByID(userID);
		
		if (user == null || user.getStatus() != Status.User.NORMAL) {
			logger.debug("data error loginByPhonePwd & getUserByID from user Status: " + user == null ? "user is null" : user.getStatus() + "");
		}
		
		return resp;
	}
	

}
