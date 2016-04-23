package com.csm.straining.article.page.moment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.article.service.MomentService;
import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.common.model.info.common.ParamErrorStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;


/**
 * @author chensongming
 */
public class MomentCreatePage extends AuthJsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(MomentCreatePage.class);
	
	@Override
	protected Object doService() {
		try {
			String content = getParamStringRequired("content");
			return MomentService.createMoment(currentUserID, content);
		} catch (ParamVaildException e) {
			logger.debug("[MomentCreatePage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[MomentCreatePage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[MomentCreatePage] Exception :", e);
			return new ErrorStatus(100);
		}
	}

}
