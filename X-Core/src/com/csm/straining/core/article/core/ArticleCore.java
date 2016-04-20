package com.csm.straining.core.article.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.article.cons.ArticleLikeType;
import com.csm.straining.common.i.article.entity.ArticleCommentEntity;
import com.csm.straining.common.i.article.entity.ArticleEntity;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.core.article.util.ArticleHelper;
import com.csm.straining.core.user.core.UserCore;
import com.csm.straining.dataaccess.caps.article.ArticleCaps;
import com.csm.straining.dataaccess.caps.article.ArticleCommentCaps;
import com.csm.straining.dataaccess.caps.article.ArticleLikeCaps;
import com.csm.straining.dataaccess.entity.article.Article;
import com.csm.straining.dataaccess.entity.article.ArticleComment;
import com.csm.straining.dataaccess.entity.article.ArticleLike;
import com.lamfire.utils.JSON;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class ArticleCore {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleCore.class);
	
	/** -----------------------文章相关----------------------**/
	
	public static void createArticle(long userID, String title, String content) throws CoreException, AppException {
		
		if (userID <= 0 || !UserCore.existUserID(userID)) {
			throw new AppException("该用户不存在");
		}
		
		if (StringUtils.isBlank(title)) {
			throw new AppException("文章标题不可为空");
		}
		
		if (StringUtils.isBlank(content)) {
			throw new AppException("文章内容不可为空");
		}
		
		Article domain = new Article();
		domain.setUserID(userID);
		domain.setTitle(title);
		domain.setContent(content);
		domain.setCreateAt(new Date());
		
		ArticleCaps.insertArticle(domain);
	}
	
	public static void delArticleByID(long optUserID, long articleID) throws CoreException, AppException {
		Article domain = ArticleCaps.getArticleByID(articleID);
		
		if(null == domain) {
			throw new AppException("文章不存在");
		}
		
		if(domain.getUserID() != optUserID) {
			throw new AppException("你没有权限删除该文章");
		}
		
		ArticleCaps.delArticleByID(articleID);
	}
	
	public static ArticleEntity getSimpleArticleByID(long articleID, boolean isVisit) throws CoreException {
		
		if (isVisit) {
			ArticleCaps.incrReadCount(articleID);
		}
		 
		return ArticleHelper.domain2Entity(ArticleCaps.getArticleByID(articleID));
	}
	
	public static ArticleEntity getSimpleArticleByID(long articleID) throws CoreException {
		return getSimpleArticleByID(articleID, true);
	}
	
	public static ArticleEntity getRichArticleByID(long articleID, boolean isVisit) throws CoreException, AppException {
		
		// 增加阅读数
		if (isVisit) {
			ArticleCaps.incrReadCount(articleID);
		}
		
		// 获取文章实体
		ArticleEntity articleEntity = ArticleHelper.domain2Entity(ArticleCaps.getArticleByID(articleID));
		
		if (articleEntity == null) {
			return null;
		}
		
		// 获取文章创建者
		articleEntity.setUser(UserCore.getSimpleUserByID(articleEntity.getUserID()));
		
		if (articleEntity.getUser() == null) {
			logger.debug(String.format("[database error inconsistent] userID : %d ,articleID : %d", articleEntity.getUserID(), articleID));
			throw new AppException("获取文章内容失败");
		}
		
		// 获取文章评论列表
		List<ArticleComment> articleComments = ArticleCommentCaps.getCommentsByArticleID(articleID);
		Set<Long> preLoadUserIDSet = new HashSet<Long>();
		for (ArticleComment articleComment : articleComments) {
			if (articleComment == null) {
				continue;
			}
			preLoadUserIDSet.add(articleComment.getUserID());
			if (articleComment.getReplyID() != null) {
				preLoadUserIDSet.add(articleComment.getReplyID());
			}
		}
		
		// 去重
		List<Long> preLoadUserIDs = new ArrayList<Long>(preLoadUserIDSet);
		Map<Long, UserEntity> preLoadUserMap = UserCore.getSimpleUserMapByIDs(preLoadUserIDs);
		
		// 组装entity
		for (ArticleComment articleComment : articleComments) {
			ArticleCommentEntity articleCommentEntity = ArticleHelper.domain2Entity(articleComment);
			if (articleComment == null) {
				continue;
			}
			UserEntity user = preLoadUserMap.get(articleCommentEntity.getUserID());
			if (user == null) {
				continue;
			}
			articleCommentEntity.setUser(user);
			UserEntity replyUser = preLoadUserMap.get(articleCommentEntity.getReplyUserID());
			articleCommentEntity.setReplyUser(replyUser);
			
			articleEntity.getComments().add(articleCommentEntity);
		} 
		
		 
		return articleEntity;
	}
	
	public static ArticleEntity getRichArticleByID(long articleID) throws CoreException, AppException {
		return getRichArticleByID(articleID, true);
	}
	
	public static List<ArticleEntity> getArticlesByUserID(long targetUserID, long start, int count) throws CoreException {
		
		List<ArticleEntity> articleEntities = ArticleHelper.domain2Entity(ArticleCaps.getArticlesByUserID(targetUserID, start, count));
		
		Set<Long> preLoadUserIDSet = new HashSet<Long>();
		for (ArticleEntity articleEntity : articleEntities) {
			preLoadUserIDSet.add(articleEntity.getUserID());
		} 
		
		// 去重
		List<Long> preLoadUserIDs = new ArrayList<Long>(preLoadUserIDSet);
		Map<Long, UserEntity> preLoadUserMap = UserCore.getSimpleUserMapByIDs(preLoadUserIDs);
		
		for (ArticleEntity articleEntity : articleEntities) {
			UserEntity user = preLoadUserMap.get(articleEntity.getUserID());
			if (user == null) {
				continue;
			}
			articleEntity.setUser(user);
		} 
		
		return articleEntities;
	}
	
	public static List<ArticleEntity> getRecommendArticles(long start, int count) throws CoreException {
		
		List<ArticleEntity> articleEntities = ArticleHelper.domain2Entity(ArticleCaps.getArticles(start, count));
		
		Set<Long> preLoadUserIDSet = new HashSet<Long>();
		for (ArticleEntity articleEntity : articleEntities) {
			preLoadUserIDSet.add(articleEntity.getUserID());
		} 
		
		// 去重
		List<Long> preLoadUserIDs = new ArrayList<Long>(preLoadUserIDSet);
		Map<Long, UserEntity> preLoadUserMap = UserCore.getSimpleUserMapByIDs(preLoadUserIDs);
		
		for (ArticleEntity articleEntity : articleEntities) {
			UserEntity user = preLoadUserMap.get(articleEntity.getUserID());
			if (user == null) {
				continue;
			}
			articleEntity.setUser(user);
		} 
		
		return articleEntities;
	}
	
	
	/** -----------------------文章评论相关---------------------- **/
	
	public static ArticleCommentEntity createArticleComment(long articleID, long userID, long replyID, String content) throws AppException, CoreException {
		
		if (!ArticleCaps.existsArticleID(articleID)) {
			throw new AppException("该文章不存在");
		}
		
		if (userID <= 0 || !UserCore.existUserID(userID)) {
			throw new AppException("该用户不存在");
		}
		
		if (replyID < 0 || (replyID != 0 && !UserCore.existUserID(replyID))) {
			throw new AppException("该用户不存在");
		}
		
		if (StringUtils.isBlank(content)) {
			throw new AppException("评论内容不可为空");
		}
		
		if (userID == replyID) {
			throw new AppException("不可回复自己");
		}
		
		ArticleComment domain = new ArticleComment();
		domain.setArticleID(articleID);
		domain.setContent(content);
		domain.setCreateAt(new Date());
		domain.setUserID(userID);
		
		if (replyID > 0) {
			domain.setReplyID(replyID);
		}
		
		ArticleCommentEntity entity = ArticleHelper.domain2Entity(ArticleCommentCaps.insertArticleComment(domain));
		
		if (entity == null) {
			throw new AppException("评论失败");
		}
		
		// 预加载数据
		entity.setUser(UserCore.getSimpleUserByID(entity.getUserID()));
		
		if (entity.getUser() == null) {
			logger.debug(String.format("[database error inconsistent] userID : %d ,commentID : %d", entity.getUserID(), entity.getId()));
			throw new AppException("获取评论内容失败");
		}
		
		entity.setReplyUser(UserCore.getSimpleUserByID(entity.getReplyUserID()));
		
		// 增加评论数
		ArticleCaps.incrCommentCount(articleID);
		
		
		return entity;
	}
	
	public static void delArticleCommentByID(long optUserID, long commentID) throws CoreException, AppException {
		
		ArticleComment comment = ArticleCommentCaps.getCommentsByID(commentID);
		if (null == comment) {
			throw new AppException("该评论不存在");
		}
		
		Article article = ArticleCaps.getArticleByID(comment.getArticleID());
		if (null == article) {
			throw new AppException("该文章不存在");
		}
		
		if (article.getUserID() != optUserID && comment.getUserID() != optUserID) {
			throw new AppException("你没有权限删除该评论");
		}
		
		ArticleCommentCaps.delArticleCommentByID(commentID);
		ArticleCaps.descCommentCount(article.getId());
	}
	
	/** -----------------------文章点赞相关---------------------- **/
	public static void createArticleLike(long userID, long articleID, byte type) throws CoreException, AppException {
		
		if (userID <= 0 || !UserCore.existUserID(userID)) {
			throw new AppException("该用户不存在");
		}
		
		if (articleID <= 0 || !ArticleCaps.existsArticleID(articleID)) {
			throw new AppException("该文章不存在");
		}
		
		ArticleLike like = ArticleLikeCaps.getArticleLikeByUserID(userID, articleID);
		if (like != null) {
			ArticleLikeType articleLikeType = ArticleLikeType.valueof(like.getType());
			switch (articleLikeType) {
			case LIKE:
				throw new AppException("你已点赞了该文章");
				
			case DISLIKE:
				throw new AppException("你已点灭了该文章");

			default:
				logger.debug("[database error inconsistent] articleLikeID : " + like.getId());
				throw new AppException("数据错误");
			}
		} else {
			if (!ArticleLikeType.isValueof(type)) {
				throw new AppException("参数错误");
			}
			ArticleLike domain = new ArticleLike();
			domain.setArticleID(articleID);
			domain.setUserID(userID);
			domain.setType(type);
			domain.setCreateAt(new Date());
			ArticleLikeCaps.insertArticleLike(domain);
			
			ArticleLikeType articleLikeType = ArticleLikeType.valueof(type);
			switch (articleLikeType) {
			case LIKE:
				ArticleCaps.incrLikeCount(articleID);
				break;
			case DISLIKE:
				ArticleCaps.incrDislikeCount(articleID);
				break;
			}
		}
	}
	

}
