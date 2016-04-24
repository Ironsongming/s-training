package com.csm.straining.user.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;
import com.csm.straining.user.service.UserService;


/**
 * @author chensongming
 */
public class UserRankPage extends AuthJsonViewPage{

	private static final Logger logger = LoggerFactory.getLogger(UserRankPage.class);
	
	@Override
	protected Object doService() {
		try {
			
			return UserService.userRankTop20Resp();
			
		} catch (AppException e) {
			logger.debug("[FollowPage] AppException :", e);
			return new ErrorStatus(100);
		} catch (Exception e) {
			logger.debug("[FollowPage] Exception :", e);
			return new ErrorStatus(100);
		}
	}

}
