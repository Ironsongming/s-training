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
import com.csm.straining.dataaccess.entity.article.ArticleComment;
import com.csm.straining.dataaccess.entity.article.ArticleCommentExample;
import com.csm.straining.dataaccess.entity.article.ArticleExample;
import com.csm.straining.dataaccess.mapper.article.ArticleCommentMapper;
import com.csm.straining.dataaccess.mapper.article.ArticleMapper;


/**
 * @author chensongming
 */
public class ArticleCommentCaps {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleCommentCaps.class);
	
	public static ArticleComment insertArticleComment(ArticleComment domain) throws CoreException {
		
		Dao<ArticleCommentMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleCommentMapper.class);
			dao.mapper().insertSelective(domain);
		} catch (Exception e) {
			logger.debug("[ArticleCommentCaps] insertArticleComment : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		return domain;
	}
	
	public static void delArticleCommentByID(long commentID) throws CoreException {
		
		Dao<ArticleCommentMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleCommentMapper.class);
			
			ArticleComment domain = new ArticleComment();
			domain.setId(commentID);
			domain.setStatus(Status.Article.DELETED);
			
			dao.mapper().updateByPrimaryKeySelective(domain);
		} catch (Exception e) {
			logger.debug("[ArticleCommentCaps] delArticleCommentByID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static List<ArticleComment> getCommentsByArticleID(long articleID) throws CoreException {
		
		Dao<ArticleCommentMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleCommentMapper.class);
			
			ArticleCommentExample exp = new ArticleCommentExample();
			ArticleCommentExample.Criteria criteria = exp.createCriteria();
			
			criteria.andArticleIDEqualTo(articleID);
			criteria.andStatusEqualTo(Status.ArticleComment.NORMAL);
			
			
			List<ArticleComment> res = dao.mapper().selectByExample(exp);
			
			return res == null ? new ArrayList<ArticleComment>() : res;
		} catch (Exception e) {
			logger.debug("[ArticleCommentCaps] getCommentsByArticleID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}
	
	public static ArticleComment getCommentsByID(long commentID) throws CoreException {
		
		Dao<ArticleCommentMapper> dao = null;
		
		try {
			dao = DbConfig.openSessionMaster(ArticleCommentMapper.class);
			
			ArticleCommentExample exp = new ArticleCommentExample();
			ArticleCommentExample.Criteria criteria = exp.createCriteria();
			
			criteria.andIdEqualTo(commentID);
			criteria.andStatusEqualTo(Status.ArticleComment.NORMAL);
			
			List<ArticleComment> res = dao.mapper().selectByExample(exp);
			
			return res == null || res.isEmpty() ? null : res.get(0);
		} catch (Exception e) {
			logger.debug("[ArticleCommentCaps] getCommentsByID : ", e);
			throw new CoreException(CoreException.DATABASE, e);
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}

}
