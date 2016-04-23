package com.csm.straining.common.i.article;


import java.util.List;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.article.entity.ArticleCommentEntity;
import com.csm.straining.common.i.article.entity.ArticleEntity;
import com.csm.straining.common.i.moment.entity.MomentEntity;


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
	
	/** -----------------动态相关----------------- **/
	
	void createMoment(long userID, String content) throws CoreException, AppException;
	
	void transferMoment(long userID, String content, long transferID) throws CoreException, AppException;
	
	void delMomentByID(long optUserID, long momentID) throws CoreException, AppException;
	
	MomentEntity getRichMomentEntityByID(long momentID) throws CoreException, AppException;
	
	List<MomentEntity> getUserHomeRichMoments(long userID, long start, int count) throws CoreException, AppException;
	
	List<MomentEntity> getUserRichMoments(long userID, long start, int count) throws CoreException, AppException;
	
	void createMomentReply(long userID, long momentID, long replyUserID, String content) throws AppException, CoreException;
	
	void delMomentReplyByID(long userID, long replyID) throws CoreException, AppException;
	
	void createMomentLike(long userID, long momentID, byte type) throws AppException, CoreException;
	
	
	
	
}
