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
public class MomentListPage extends AuthJsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(MomentListPage.class);
	
	@Override
	protected Object doService() {
		try {
			
			long start = NumberUtils.toLong(getParamString("start"), 0);
			int count = NumberUtils.toInt(getParamString("count"), 20);
			
			return MomentService.getMomentListResp(currentUserID, start, count);
		} catch (AppException e) {
			logger.debug("[MomentListPage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[MomentListPage] Exception :", e);
			return new ErrorStatus(100);
		}
	}
	

}
