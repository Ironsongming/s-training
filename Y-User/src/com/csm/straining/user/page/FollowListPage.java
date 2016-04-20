package com.csm.straining.user.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;
import com.csm.straining.user.service.UserService;


/**
 * @author chensongming
 */
public class FollowListPage extends AuthJsonViewPage{

	private static final Logger logger = LoggerFactory.getLogger(FollowListPage.class);
	
	@Override
	protected Object doService() {
		try {
			
			return UserService.getFollowByUserID(currentUserID);
			
		} catch (Exception e) {
			logger.debug("[FollowPage] Exception :", e);
			return new ErrorStatus(100);
		}
	}

}
