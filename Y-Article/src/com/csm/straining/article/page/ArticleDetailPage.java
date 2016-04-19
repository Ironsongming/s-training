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
public class ArticleDetailPage extends AuthJsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(ArticleDetailPage.class);
	
	@Override
	protected Object doService() {
		try {
			long articleID = NumberUtils.toLong(getParamStringRequired("article_id"));
			return ArticleService.articleDetailResp(articleID, currentUserID);
			
		} catch (ParamVaildException e) {
			logger.debug("[ArticleDetailPage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[ArticleDetailPage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[ArticleDetailPage] Exception :", e);
			return new ErrorStatus(100);
		}
	}
	

}
