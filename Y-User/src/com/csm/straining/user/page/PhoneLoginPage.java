package com.csm.straining.user.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.http.page.JsonViewPage;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.common.model.info.common.ParamErrorStatus;
import com.csm.straining.user.service.UserService;

/**
 * @author chensongming
 */
public class PhoneLoginPage extends JsonViewPage {
	
	private static final Logger logger = LoggerFactory.getLogger(PhoneLoginPage.class);

	@Override
	protected Object doService() {
		
		try {
			String phone = getParamStringRequired("phone");
			String password = getParamStringRequired("password");
			
			
			return UserService.userCreateResp(phone, password);
		} catch (ParamVaildException e) {
			logger.debug("[PhoneLoginPage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[PhoneLoginPage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[PhoneLoginPage] Exception :", e);
			return new ErrorStatus(100);
		}
		
		
		
	}

}
