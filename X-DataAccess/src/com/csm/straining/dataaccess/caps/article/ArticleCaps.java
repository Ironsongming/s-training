package com.csm.straining.dataaccess.caps.article;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.dataaccess.Dao;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.entity.article.Article;
import com.csm.straining.dataaccess.entity.article.ArticleExample;
import com.csm.straining.dataaccess.mapper.article.ArticleCusMapper;
import com.csm.straining.dataaccess.mapper.article.ArticleMapper;


/**
 * @author chensongming
 */
public class ArticleCaps {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleCaps.class);
	
	public static boolean existsArticleID(long articleID) throws CoreException {
		return existsArticleID(articleID, Status.Article.NORMAL);
	}
	
	public static boolean existsArticleID(long articleID, byte status) throws CoreException {
		Dao<ArticleMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(ArticleMapper.class);
			
			ArticleExample exp = new ArticleExample();
			ArticleExample.Criteria criteria = exp.createCriteria();
			
			criteria.andIdEqualTo(articleID);
			criteria.andStatusEqualTo(status);
			
			return dao.mapper().countByExample(exp) > 0 ? true : false;
		} catch (Exception e) {
			logger.debug("[ArticleCaps] getArticlesByUserID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static List<Article> getArticlesByUserID(long userID, long start, int count) throws CoreException {

		Dao<ArticleMapper> dao = null;
		
		count = count < 0 ? 20 : count;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleMapper.class);
			
			ArticleExample exp = new ArticleExample();
			ArticleExample.Criteria criteria = exp.createCriteria();
			
			criteria.andUserIDEqualTo(userID);
			criteria.andStatusEqualTo(Status.Article.NORMAL);
			
			if (start > 0) {
				criteria.andIdLessThan(start);
			}
			
			exp.setLimit(count);
			
			exp.setOrderByClause("id desc");
			
			List<Article> res = dao.mapper().selectByExample(exp);
			return res == null ? new ArrayList<Article>() : res;
		} catch (Exception e) {
			logger.debug("[ArticleCaps] getArticlesByUserID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static List<Article> getArticles(long start, int count) throws CoreException {

		Dao<ArticleMapper> dao = null;
		
		count = count < 0 ? 20 : count;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleMapper.class);
			
			ArticleExample exp = new ArticleExample();
			ArticleExample.Criteria criteria = exp.createCriteria();
			
			if (start > 0) {
				criteria.andIdLessThan(start);
			}
			criteria.andStatusEqualTo(Status.Article.NORMAL);
			exp.setLimit(count);
			
			exp.setOrderByClause("likeCount desc, dislikeCount asc");
			List<Article> res = dao.mapper().selectByExample(exp);
			return res == null ? new ArrayList<Article>() : res;
		} catch (Exception e) {
			logger.debug("[ArticleCaps] getArticlesByUserID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	
	
	public static Article insertArticle(Article domain) throws CoreException {
		
		Dao<ArticleMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleMapper.class);
			dao.mapper().insertSelective(domain);
		} catch (Exception e) {
			logger.debug("[ArticleCaps] insertArticle : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		return domain;
	}
	
	public static void delArticleByID(long articleID) throws CoreException {
		
		Dao<ArticleMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleMapper.class);
			
			Article domain = new Article();
			domain.setId(articleID);
			domain.setStatus(Status.Article.DELETED);
			
			dao.mapper().updateByPrimaryKeySelective(domain);
		} catch (Exception e) {
			logger.debug("[ArticleCaps] delArticleByID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static Article getArticleByID(long articleID) throws CoreException {
		
		Dao<ArticleMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleMapper.class);
			
			return dao.mapper().selectByPrimaryKey(articleID);
		} catch (Exception e) {
			logger.debug("[ArticleCaps] getArticleByID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void incrReadCount(long articleID) throws CoreException {
		Dao<ArticleCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleCusMapper.class);
			dao.mapper().incrReadCount(articleID);
		} catch (Exception e) {
			logger.debug("[ArticleCaps] incrReadCount : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void incrLikeCount(long articleID) throws CoreException {
		Dao<ArticleCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleCusMapper.class);
			dao.mapper().incrLikeCount(articleID);
		} catch (Exception e) {
			logger.debug("[ArticleCaps] incrLikeCount : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void incrDislikeCount(long articleID) throws CoreException {
		Dao<ArticleCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleCusMapper.class);
			dao.mapper().incrDislikeCount(articleID);
		} catch (Exception e) {
			logger.debug("[ArticleCaps] incrDislikeCount : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void incrCommentCount(long articleID) throws CoreException {
		Dao<ArticleCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleCusMapper.class);
			dao.mapper().incrCommentCount(articleID);
		} catch (Exception e) {
			logger.debug("[ArticleCaps] incrCommentCount : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static void descCommentCount(long articleID) throws CoreException {
		Dao<ArticleCusMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleCusMapper.class);
			dao.mapper().descCommentCount(articleID);
		} catch (Exception e) {
			logger.debug("[ArticleCaps] descCommentCount : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}

}
