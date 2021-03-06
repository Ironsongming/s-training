package com.csm.straining.user.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;
import com.csm.straining.user.service.UserService;
import com.lamfire.utils.NumberUtils;


/**
 * @author chensongming
 */
public class UserDetailPage extends AuthJsonViewPage{

	private static final Logger logger = LoggerFactory.getLogger(UserDetailPage.class);
	
	@Override
	protected Object doService() {
		
		try {
			long targetUserID = NumberUtils.toLong(getParamStringRequired("target_user_id"));
			return UserService.userDetailResp(currentUserID, targetUserID);
		} catch (AppException e) {
			logger.debug("[UserDetailPage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[UserDetailPage] Exception :", e);
			return new ErrorStatus(100);
		}
		
		
		
	}

}
