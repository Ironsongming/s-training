package com.csm.straining.user.page.contact;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.common.model.info.common.ParamErrorStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;
import com.csm.straining.user.service.ContactService;
import com.lamfire.utils.NumberUtils;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class GroupCreatePage extends AuthJsonViewPage{

	private static final Logger logger = LoggerFactory.getLogger(GroupCreatePage.class);
	
	@Override
	protected Object doService() {
		try {
			
			String memberIDsStr = getParamStringRequired("member_ids");
			
			if (StringUtils.isBlank(memberIDsStr)) {
				throw new ParamVaildException(102, "memberIDs");
			}
			
			String[] memberIDsArr = memberIDsStr.split(",");
			Set<Long> memberIDs = new HashSet<Long>();
			
			for (String v : memberIDsArr) {
				memberIDs.add(NumberUtils.toLong(v));
			}
			
			return ContactService.createGroup(currentUserID, memberIDs);
			
		} catch (ParamVaildException e) {
			logger.debug("[GroupCreatePage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[GroupCreatePage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[GroupCreatePage] Exception :", e);
			return new ErrorStatus(100);
		}
	}
	
	

}
