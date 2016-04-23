package com.csm.straining.article.page.moment.reply;

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
public class MomentReplyDeletePage extends AuthJsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(MomentReplyDeletePage.class);
	
	@Override
	protected Object doService() {
		try {
			long replyID = NumberUtils.toLong(getParamStringRequired("reply_id"), 0);
			return MomentService.delMomentReplyByID(currentUserID, replyID);
		} catch (ParamVaildException e) {
			logger.debug("[MomentReplyDeletePage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[MomentReplyDeletePage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[MomentReplyDeletePage] Exception :", e);
			return new ErrorStatus(100);
		}
	}

}
