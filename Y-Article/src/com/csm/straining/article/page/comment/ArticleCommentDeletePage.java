package com.csm.straining.article.page.comment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.article.service.ArticleService;
import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.common.model.info.common.ParamErrorStatus;
import com.csm.straining.common.model.info.common.ResponseStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;
import com.lamfire.utils.NumberUtils;


/**
 * @author chensongming
 */
public class ArticleCommentDeletePage extends AuthJsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(ArticleCommentDeletePage.class);
	
	@Override
	protected Object doService() {
		try {
			
			long commentID = NumberUtils.toLong(getParamStringRequired("comment_id"));
			return ArticleService.articleCommentDeleteResp(currentUserID, commentID);
			
		} catch (ParamVaildException e) {
			logger.debug("[ArticleCreatePage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[ArticleCreatePage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[ArticleCreatePage] Exception :", e);
			return new ErrorStatus(100);
		}
	}
	

}
