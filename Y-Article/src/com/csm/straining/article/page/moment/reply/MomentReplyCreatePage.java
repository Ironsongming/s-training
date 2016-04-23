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
public class MomentReplyCreatePage extends AuthJsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(MomentReplyCreatePage.class);
	
	@Override
	protected Object doService() {
		try {
			long momentID = NumberUtils.toLong(getParamStringRequired("moment_id"));
			long replyUserID = NumberUtils.toLong(getParamString("reply_user_id"), 0);
			String content = getParamStringRequired("content");
			return MomentService.createMomentReply(currentUserID, momentID, replyUserID, content);
		} catch (ParamVaildException e) {
			logger.debug("[MomentReplyCreatePage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[MomentReplyCreatePage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[MomentReplyCreatePage] Exception :", e);
			return new ErrorStatus(100);
		}
	}

}
