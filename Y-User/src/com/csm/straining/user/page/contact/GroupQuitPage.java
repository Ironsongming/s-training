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
public class GroupQuitPage extends AuthJsonViewPage{

	private static final Logger logger = LoggerFactory.getLogger(GroupQuitPage.class);
	
	@Override
	protected Object doService() {
		try {
			
			long groupID = NumberUtils.toLong(getParamStringRequired("group_id"));
			
			return ContactService.quitGroup(currentUserID, groupID);
			
		} catch (ParamVaildException e) {
			logger.debug("[UnFollowPage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[UnFollowPage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[UnFollowPage] Exception :", e);
			return new ErrorStatus(100);
		}
	}

}
