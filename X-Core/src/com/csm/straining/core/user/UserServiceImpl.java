package com.csm.straining.core.user;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.user.UserService;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.common.i.user.params.UserParams;
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
	

}
