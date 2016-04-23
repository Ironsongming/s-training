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
public class MomentDetailPage extends AuthJsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(MomentDetailPage.class);
	
	@Override
	protected Object doService() {
		try {
			long momentID = NumberUtils.toLong(getParamStringRequired("moment_id"));
			return MomentService.getMomentDetailResp(currentUserID, momentID);
		} catch (ParamVaildException e) {
			logger.debug("[MomentDetailPage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[MomentDetailPage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[MomentDetailPage] Exception :", e);
			return new ErrorStatus(100);
		}
	}

}
