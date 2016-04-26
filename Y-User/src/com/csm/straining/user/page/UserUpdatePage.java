package com.csm.straining.user.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.i.user.params.UserParams;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;
import com.csm.straining.user.refer.UserServiceReference;
import com.csm.straining.user.service.UserService;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class UserUpdatePage extends AuthJsonViewPage{

	private static final Logger logger = LoggerFactory.getLogger(UserUpdatePage.class);
	
	@Override
	protected Object doService() {
		
		try {
			
			UserParams params = parseParams();
			
			return UserService.userUpdateDetailResp(currentUserID, params);
		} catch (AppException e) {
			logger.debug("[UserUpdatePage] AppException :", e);
			return new ErrorStatus(100);
		} catch (Exception e) {
			logger.debug("[UserUpdatePage] Exception :", e);
			return new ErrorStatus(100);
		}
		
	}
	
	private UserParams parseParams() {
		UserParams params = new UserParams();

		String username = getParamString("username");
		String password = getParamString("password");
		String signNature = getParamString("sign_nature");
		String avatar = getParamString("avatar");
		
		params.setUserID(currentUserID);			
		
		if (StringUtils.isNotBlank(username)) {
			params.setUsername(username);			
		}
		
		if (StringUtils.isNotBlank(password)) {
			params.setPassword(password);
		}
		
		if (StringUtils.isNotBlank(signNature)) {
			params.setSignNature(signNature);			
		}
		
		if (StringUtils.isNotBlank(avatar)) {
			params.setAvatar(avatar);
		}
		
		return params;
	}

}
