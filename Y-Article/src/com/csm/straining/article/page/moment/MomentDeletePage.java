package com.csm.straining.article.page.moment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.article.service.MomentService;
import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.common.model.info.common.ParamErrorStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;
import com.lamfire.utils.NumberUtils;


/**
 * @author chensongming
 */
public class MomentDeletePage extends AuthJsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(MomentDeletePage.class);
	
	@Override
	protected Object doService() {
		try {
			long momentID = NumberUtils.toLong(getParamStringRequired("moment_id"));
			return MomentService.delMomentByID(currentUserID, momentID);
		} catch (ParamVaildException e) {
			logger.debug("[MomentDeletePage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[MomentDeletePage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[MomentCreatePage] Exception :", e);
			return new ErrorStatus(100);
		}
	}

}
