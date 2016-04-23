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
public class MomentTransferPage extends AuthJsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(MomentTransferPage.class);
	
	@Override
	protected Object doService() {
		try {
			String content = getParamStringRequired("content");
			long transferID = NumberUtils.toLong(getParamStringRequired("transfer_id"));
			return MomentService.transferMoment(currentUserID, content, transferID);
		} catch (ParamVaildException e) {
			logger.debug("[MomentTransferPage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[MomentTransferPage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[MomentTransferPage] Exception :", e);
			return new ErrorStatus(100);
		}
	}

}
