package com.csm.straining.article.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.article.service.ArticleService;
import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.common.model.info.common.ParamErrorStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;
import com.lamfire.utils.NumberUtils;


/**
 * @author chensongming
 */
public class ArticleDeletePage extends AuthJsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(ArticleDeletePage.class);
	
	@Override
	protected Object doService() {
		try {
			
			long articleID = NumberUtils.toLong(getParamStringRequired("article_id"));
			
			return ArticleService.articleDeleteResp(currentUserID, articleID);
		} catch (ParamVaildException e) {
			logger.debug("[ArticleDeletePage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[ArticleDeletePage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[ArticleDeletePage] Exception :", e);
			return new ErrorStatus(100);
		}
	}
	

}
