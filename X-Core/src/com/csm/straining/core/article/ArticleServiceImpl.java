package com.csm.straining.core.article;

import java.util.List;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.article.ArticleService;
import com.csm.straining.common.i.article.entity.ArticleCommentEntity;
import com.csm.straining.common.i.article.entity.ArticleEntity;
import com.csm.straining.core.article.core.ArticleCore;


/**
 * @author chensongming
 */
public class ArticleServiceImpl implements ArticleService {

	@Override
	public void createArticle(long userID, String title, String content)
			throws CoreException, AppException {
		ArticleCore.createArticle(userID, title, content);
	}

	@Override
	public void delArticleByID(long optUserID, long articleID)
			throws CoreException, AppException {
		ArticleCore.delArticleByID(optUserID, articleID);
	}

	@Override
	public ArticleEntity getRichArticleByID(long articleID)
			throws CoreException, AppException {
		return ArticleCore.getRichArticleByID(articleID);
	}

	@Override
	public List<ArticleEntity> getArticlesByUserID(long targetUserID,
			long start, int count) throws CoreException {
		return ArticleCore.getArticlesByUserID(targetUserID, start, count);
	}

	@Override
	public List<ArticleEntity> getRecommendArticles(long start, int count)
			throws CoreException {
		return ArticleCore.getRecommendArticles(start, count);
	}

	@Override
	public ArticleCommentEntity createArticleComment(long articleID,
			long userID, long replyID, String content) throws AppException,
			CoreException {
		return ArticleCore.createArticleComment(articleID, userID, replyID, content);
	}

	@Override
	public void delArticleCommentByID(long optUserID, long commentID)
			throws CoreException, AppException {
		ArticleCore.delArticleCommentByID(optUserID, commentID);
	}

	@Override
	public void createArticleLike(long userID, long articleID, byte type)
			throws CoreException, AppException {
		ArticleCore.createArticleLike(userID, articleID, type);
	}

}
