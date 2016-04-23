package com.csm.straining.article.page.moment.like;

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
public class MomentLikeCreatePage extends AuthJsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(MomentLikeCreatePage.class);
	
	@Override
	protected Object doService() {
		try {
			long momentID = NumberUtils.toLong(getParamStringRequired("moment_id"));
			int type = NumberUtils.toInt(getParamStringRequired("type"));
			return MomentService.createMomentLike(currentUserID, momentID, (byte)type);
		} catch (ParamVaildException e) {
			logger.debug("[MomentLikeCreatePage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[MomentLikeCreatePage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[MomentLikeCreatePage] Exception :", e);
			return new ErrorStatus(100);
		}
	}

}
