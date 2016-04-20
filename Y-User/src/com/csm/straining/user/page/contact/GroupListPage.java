package com.csm.straining.user.page.contact;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.common.model.info.common.ParamErrorStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;
import com.csm.straining.user.service.ContactService;
import com.lamfire.utils.NumberUtils;


/**
 * @author chensongming
 */
public class GroupListPage extends AuthJsonViewPage{

	private static final Logger logger = LoggerFactory.getLogger(GroupListPage.class);
	
	@Override
	protected Object doService() {
		try {
			return ContactService.getGroups(currentUserID);
		} catch (Exception e) {
			logger.debug("[GroupListPage] Exception :", e);
			return new ErrorStatus(100);
		}
	}
	
	

}
