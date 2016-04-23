package com.csm.straining.core.article;

import java.util.List;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.article.ArticleService;
import com.csm.straining.common.i.article.entity.ArticleCommentEntity;
import com.csm.straining.common.i.article.entity.ArticleEntity;
import com.csm.straining.common.i.moment.entity.MomentEntity;
import com.csm.straining.core.article.core.ArticleCore;
import com.csm.straining.core.moment.core.MomentCore;


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

	@Override
	public void createMoment(long userID, String content) throws CoreException,
			AppException {
		MomentCore.createMoment(userID, content);
	}

	@Override
	public void transferMoment(long userID, String content, long transferID)
			throws CoreException, AppException {
		MomentCore.transferMoment(userID, content, transferID);
	}

	@Override
	public void delMomentByID(long optUserID, long momentID) throws CoreException, AppException {
		MomentCore.delMomentByID(optUserID, momentID);
	}

	@Override
	public MomentEntity getRichMomentEntityByID(long momentID)
			throws CoreException, AppException {
		return MomentCore.getRichMomentEntityByID(momentID);
	}

	@Override
	public List<MomentEntity> getUserHomeRichMoments(long userID, long start,
			int count) throws CoreException, AppException {
		return MomentCore.getUserHomeRichMoments(userID, start, count);
	}

	@Override
	public List<MomentEntity> getUserRichMoments(long userID, long start,
			int count) throws CoreException, AppException {
		return MomentCore.getUserRichMoments(userID, start, count);
	}

	@Override
	public void createMomentReply(long userID, long momentID, long replyUserID,
			String content) throws AppException, CoreException {
		MomentCore.createMomentReply(userID, momentID, replyUserID, content);
	}

	@Override
	public void delMomentReplyByID(long userID, long replyID)
			throws CoreException, AppException {
		MomentCore.delMomentReplyByID(userID, replyID);
	}

	@Override
	public void createMomentLike(long userID, long momentID, byte type)
			throws AppException, CoreException {
		MomentCore.createMomentLike(userID, momentID, type);
	}
	
	
	

}
