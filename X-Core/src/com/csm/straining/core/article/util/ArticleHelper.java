package com.csm.straining.core.article.util;

import java.util.ArrayList;
import java.util.List;

import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.article.entity.ArticleCommentEntity;
import com.csm.straining.common.i.article.entity.ArticleEntity;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.core.user.util.UserHelper;
import com.csm.straining.dataaccess.caps.user.UserCaps;
import com.csm.straining.dataaccess.entity.article.Article;
import com.csm.straining.dataaccess.entity.article.ArticleComment;


/**
 * @author chensongming
 */
public class ArticleHelper {
	
	public static ArticleEntity domain2Entity(Article domain) {
		if (domain == null) {
			return null;
		}
		
		ArticleEntity entity = new ArticleEntity();
		entity.setId(domain.getId());
		entity.setUserID(domain.getUserID());
		entity.setTitle(domain.getTitle());
		entity.setContent(domain.getContent());
		entity.setCreateAt(domain.getCreateAt().getTime());
		entity.setStatus(domain.getStatus());
		entity.setReadCount(domain.getReadCount());
		entity.setLikeCount(domain.getLikeCount());
		entity.setDislikeCount(domain.getDislikeCount());
		entity.setCommentCount(domain.getCommentCount());
		
		return entity;
	}
	
	public static List<ArticleEntity> domain2Entity(List<Article> domains) {
		if (domains == null || domains.isEmpty()) {
			return new ArrayList<ArticleEntity>();
		}
		
		List<ArticleEntity> entities = new ArrayList<ArticleEntity>();
		
		for (Article domain : domains) {
			entities.add(domain2Entity(domain));
		}
		return entities;
	}
	
	public static ArticleCommentEntity domain2Entity(ArticleComment domain) {
		if (domain == null) {
			return null;
		}
		
		ArticleCommentEntity entity = new ArticleCommentEntity();
		entity.setId(domain.getId());
		entity.setArticleID(domain.getArticleID());
		entity.setUserID(domain.getUserID());
		if (domain.getReplyID() == null) {
			entity.setReplyUserID(0);
		} else {
			entity.setReplyUserID(domain.getReplyID());
		}
		
		entity.setStatus(domain.getStatus());
		entity.setContent(domain.getContent());
		entity.setCreateAt(domain.getCreateAt().getTime());
		
		return entity;
	}


}
