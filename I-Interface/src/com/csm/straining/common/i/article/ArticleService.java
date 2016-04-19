package com.csm.straining.common.i.article;


import java.util.List;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.article.entity.ArticleCommentEntity;
import com.csm.straining.common.i.article.entity.ArticleEntity;


/**
 * @author chensongming
 */
public interface ArticleService {
	
	
	void createArticle(long userID, String title, String content) throws CoreException, AppException;
	
	void delArticleByID(long optUserID, long articleID) throws CoreException, AppException;
	
	ArticleEntity getRichArticleByID(long articleID) throws CoreException, AppException;
	
	List<ArticleEntity> getArticlesByUserID(long targetUserID, long start, int count) throws CoreException;
	
	List<ArticleEntity> getRecommendArticles(long start, int count) throws CoreException;
	
	ArticleCommentEntity createArticleComment(long articleID, long userID, long replyID, String content) throws AppException, CoreException;
	
	void delArticleCommentByID(long optUserID, long commentID) throws CoreException, AppException;

	void createArticleLike(long userID, long articleID, byte type) throws CoreException, AppException;
	
}
