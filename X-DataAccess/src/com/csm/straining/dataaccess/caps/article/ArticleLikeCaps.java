package com.csm.straining.dataaccess.caps.article;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.dataaccess.Dao;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.entity.article.ArticleLike;
import com.csm.straining.dataaccess.entity.article.ArticleLikeExample;
import com.csm.straining.dataaccess.mapper.article.ArticleLikeMapper;


/**
 * @author chensongming
 */
public class ArticleLikeCaps {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleLikeCaps.class);
	
	public static ArticleLike insertArticleLike(ArticleLike domain) throws CoreException {
		
		Dao<ArticleLikeMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleLikeMapper.class);
			dao.mapper().insertSelective(domain);
		} catch (Exception e) {
			logger.debug("[ArticleLikeCaps] insertArticleLike : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		return domain;
	}
	
	public static ArticleLike getArticleLikeByUserID(long userID, long articleID) throws CoreException {
		Dao<ArticleLikeMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleLikeMapper.class);
			
			ArticleLikeExample exp = new ArticleLikeExample();
			ArticleLikeExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andArticleIDEqualTo(articleID);
			criteria.andStatusEqualTo(Status.ArticleLike.NORMAL);
			
			List<ArticleLike> res = dao.mapper().selectByExample(exp);
			
			return res == null || res.isEmpty() ? null : res.get(0);
		} catch (Exception e) {
			logger.debug("[ArticleLikeCaps] getArticleLikeByUserID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		
	}
	

}
