package com.csm.straining.article.page.moment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.article.service.ArticleService;
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
public class MomentUserListPage extends AuthJsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(MomentUserListPage.class);
	
	@Override
	protected Object doService() {
		try {
			
			long start = NumberUtils.toLong(getParamString("start"), 0);
			int count = NumberUtils.toInt(getParamString("count"), 20);
			long targetUserID = NumberUtils.toLong(getParamStringRequired("target_user_id"));
			return MomentService.getTargetMomentListResp(currentUserID, targetUserID, start, count);
		} catch (ParamVaildException e) {
			logger.debug("[MomentUserListPage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[MomentUserListPage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[MomentUserListPage] Exception :", e);
			return new ErrorStatus(100);
		}
	}
	

}
