package com.csm.straining.article.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.article.service.ArticleService;
import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.common.model.info.common.ParamErrorStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;


/**
 * @author chensongming
 */
public class ArticleCreatePage extends AuthJsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(ArticleCreatePage.class);
	
	@Override
	protected Object doService() {
		try {
			
			String title = getParamStringRequired("title");
			String content = getParamStringRequired("content");
			
			return ArticleService.articleCreateResp(currentUserID, title, content);
			
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
