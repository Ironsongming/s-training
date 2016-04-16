package com.csm.straining.common.i.user;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.common.i.user.params.UserParams;


/**
 * @author chensongming
 */
public interface UserService {

	
	/** --------------------User------------------ **/
	
	/**
	 * 插入用户
	 * 
	 * @param params
	 * @return
	 * @throws AppException 
	 * @throws CoreException 
	 */
	UserEntity createUserAccount(String phone, String password) throws CoreException, AppException;
	
	
	/**
	 * 根据用户ID获取用户
	 * 
	 * @param userID
	 * @return
	 * @throws CoreException 
	 */
	UserEntity getUserByID(long userID) throws CoreException;
	
	/**
	 * 注册电话登录
	 * 
	 * @param phone
	 * @param password
	 * @return
	 * @throws CoreException
	 * @throws AppException
	 */
	long loginByPhonePwd(String phone, String password) throws CoreException, AppException;
	
	/**
	 * 更新用户资料
	 * 
	 * @param user
	 */
	void updateUserDetail(UserParams params) throws CoreException;
	
}
