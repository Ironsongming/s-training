package com.csm.straining.article.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.article.refer.ArticleServiceReference;
import com.csm.straining.article.resp.ArticleDetailResp;
import com.csm.straining.article.resp.ArticleListResp;
import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.article.cons.ArticleLikeType;
import com.csm.straining.common.i.article.entity.ArticleCommentEntity;
import com.csm.straining.common.i.article.entity.ArticleEntity;
import com.csm.straining.common.i.article.info.ArticleCommentInfo;
import com.csm.straining.common.i.article.info.ArticleInfo;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.common.i.user.info.UserInfo;
import com.csm.straining.common.model.info.common.ResponseStatus;
import com.csm.straining.common.util.ImageUtil;
import com.csm.straining.core.user.util.UserHelper;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class ArticleService {

	private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);
	
	
	public static ResponseStatus articleCreateResp(long userID, String title, String content) throws CoreException, AppException {
		
		ArticleDetailResp resp = new ArticleDetailResp();
		
		if (StringUtils.isBlank(title)) {
			throw new AppException("文章标题不可为空");
		}
		
		if (StringUtils.isBlank(content)) {
			throw new AppException("文章内容不可为空");
		}
		
		ArticleServiceReference.sharedService().createArticle(userID, title, content);
		return new ResponseStatus();
	}
	
	private static ArticleInfo parseArticleDetailInfo(ArticleEntity articleEntity, long optUserID) {
		ArticleInfo articleInfo = new ArticleInfo();
		articleInfo.articleID = articleEntity.getId();
		articleInfo.title = articleEntity.getTitle();
		articleInfo.content = articleEntity.getContent();
		articleInfo.readCount = articleEntity.getReadCount();
		articleInfo.likeCount = articleEntity.getLikeCount();
		articleInfo.dislikeCount = articleEntity.getDislikeCount();
		articleInfo.commentCount = articleEntity.getCommentCount();
		articleInfo.createAt = articleEntity.getCreateAt();
		
		UserInfo user = UserHelper.entity2Info(articleEntity.getUser());
		articleInfo.user = user;
		
		for (ArticleCommentEntity articleCommentEntity : articleEntity.getComments()) {
			
			ArticleCommentInfo articleCommentInfo = new ArticleCommentInfo();
			articleCommentInfo.articleCommentID = articleCommentEntity.getId();
			articleCommentInfo.content = articleCommentEntity.getContent();
			articleCommentInfo.user = UserHelper.entity2Info(articleCommentEntity.getUser());
			articleCommentInfo.replyUser = UserHelper.entity2Info(articleCommentEntity.getReplyUser());
			
			if (optUserID == articleEntity.getUserID() || optUserID == articleCommentEntity.getUserID()) {
				articleCommentInfo.canDelete = 1;
			}
			
			articleInfo.comments.add(articleCommentInfo);
		}
		
		return articleInfo;
	}
	
	private static ArticleInfo parseArticleListItemInfo(ArticleEntity articleEntity, long optUserID) {
		ArticleInfo articleInfo = new ArticleInfo();
		articleInfo.articleID = articleEntity.getId();
		articleInfo.title = articleEntity.getTitle();
		articleInfo.content = articleEntity.getContent();
		articleInfo.readCount = articleEntity.getReadCount();
		articleInfo.likeCount = articleEntity.getLikeCount();
		articleInfo.dislikeCount = articleEntity.getDislikeCount();
		articleInfo.commentCount = articleEntity.getCommentCount();
		articleInfo.createAt = articleEntity.getCreateAt();
		
		
		UserInfo user = UserHelper.entity2Info(articleEntity.getUser());
		articleInfo.user = user;
		
		if (optUserID  == articleEntity.getUserID()) {
			articleInfo.canDelete = 1;
		}
		
		return articleInfo;
	}
	
	public static ResponseStatus articleDeleteResp(long optUserID, long articleID) throws AppException, CoreException {
		ArticleServiceReference.sharedService().delArticleByID(optUserID, articleID);
		return new ResponseStatus();
	}
	
	public static ArticleDetailResp articleDetailResp(long articleID, long optUserID) throws CoreException, AppException {
		
		ArticleDetailResp resp = new ArticleDetailResp();
		ArticleEntity articleEntity = ArticleServiceReference.sharedService().getRichArticleByID(articleID);
		if (articleEntity == null) {
			throw new AppException("该文章不存在或已被删除");
		}
		
		resp.article = parseArticleDetailInfo(articleEntity, optUserID);
		return resp;
	}
	
	public static ArticleListResp articleUserListResp(long optUserID, long targetUserID, long start, int count) throws AppException, CoreException{
		ArticleListResp resp = new ArticleListResp();
		
		List<ArticleEntity> articleEntities = ArticleServiceReference.sharedService().getArticlesByUserID(targetUserID, start, count + 1);
		
		for (ArticleEntity articleEntity : articleEntities) {
			resp.articles.add(parseArticleListItemInfo(articleEntity, optUserID));
		}
		
		if (resp.articles.size() > count) {
			resp.setMore(1);
		} else {
			resp.setMore(0);
			
			if (resp.articles.size() > 0) {
				resp.setStart(Long.toString(resp.articles.get(resp.articles.size() - 1).articleID));
			}
		}
		
		return resp;
	}
	
	public static ArticleListResp articleRecommendListResp(long optUserID, long start, int count) throws AppException, CoreException{
		ArticleListResp resp = new ArticleListResp();
		
//		List<ArticleEntity> articleEntities = ArticleServiceReference.sharedService().getArticlesByUserID(optUserID, start, count + 1);
		List<ArticleEntity> articleEntities = ArticleServiceReference.sharedService().getRecommendArticles(start, count + 1);
		
		for (ArticleEntity articleEntity : articleEntities) {
			resp.articles.add(parseArticleListItemInfo(articleEntity, optUserID));
		}
		
		if (resp.articles.size() > count) {
			resp.setMore(1);
		} else {
			resp.setMore(0);
			
			if (resp.articles.size() > 0) {
				resp.setStart(Long.toString(resp.articles.get(resp.articles.size() - 1).articleID));
			}
		}
		
		return resp;
	}
	
	public static ResponseStatus articleCommentCreateResp(long optUserID, long referUserID, long articleID, String content) throws AppException, CoreException{
		
		if (StringUtils.isBlank(content)) {
			throw new AppException("评论内容不可为空");
		}
		ArticleServiceReference.sharedService().createArticleComment(articleID, optUserID, referUserID, content);
		return new ResponseStatus();
	}
	
	public static ResponseStatus articleCommentDeleteResp(long optUserID, long commentID) throws AppException, CoreException{
		ArticleServiceReference.sharedService().delArticleCommentByID(optUserID, commentID);
		return new ResponseStatus();
	}
	
	public static ResponseStatus articleLikeCreateResp(long optUserID, long articleID, int type) throws AppException, CoreException{
		
		if (!ArticleLikeType.isValueof((byte)type)) {
			throw new AppException("请检查type参数");
		}
		ArticleServiceReference.sharedService().createArticleLike(optUserID, articleID, (byte) type);
		return new ResponseStatus();
	}	
	
}
