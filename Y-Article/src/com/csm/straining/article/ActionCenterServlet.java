package com.csm.straining.article;

import com.csm.straining.article.page.ArticleCreatePage;
import com.csm.straining.article.page.ArticleDeletePage;
import com.csm.straining.article.page.ArticleDetailPage;
import com.csm.straining.article.page.ArticleRecommedListPage;
import com.csm.straining.article.page.ArticleUserListPage;
import com.csm.straining.article.page.comment.ArticleCommentCreatePage;
import com.csm.straining.article.page.comment.ArticleCommentDeletePage;
import com.csm.straining.article.page.like.ArticleLikeCreatePage;
import com.csm.straining.article.page.moment.MomentCreatePage;
import com.csm.straining.article.page.moment.MomentDeletePage;
import com.csm.straining.article.page.moment.MomentDetailPage;
import com.csm.straining.article.page.moment.MomentListPage;
import com.csm.straining.article.page.moment.MomentTransferPage;
import com.csm.straining.article.page.moment.MomentUserListPage;
import com.csm.straining.article.page.moment.like.MomentLikeCreatePage;
import com.csm.straining.article.page.moment.reply.MomentReplyCreatePage;
import com.csm.straining.article.page.moment.reply.MomentReplyDeletePage;
import com.csm.straining.common.http.servlet.ViewPageServlet;


/**
 * @author chensongming
 */
public class ActionCenterServlet extends ViewPageServlet {

	private static final long serialVersionUID = 2730589394801401855L;

	@Override
	protected void registerViewPages() {
		v1();
	}
	
	private void v1() {
		
		addViewPage("/article/create", 1, PLATFORM_COMMON, ArticleCreatePage.class);
		addViewPage("/article/delete", 1, PLATFORM_COMMON, ArticleDeletePage.class);
		addViewPage("/article/detail", 1, PLATFORM_COMMON, ArticleDetailPage.class);
		addViewPage("/article/list/recommed", 1, PLATFORM_COMMON, ArticleRecommedListPage.class);
		addViewPage("/article/list/user", 1, PLATFORM_COMMON, ArticleUserListPage.class);
		addViewPage("/article/comment/create", 1, PLATFORM_COMMON, ArticleCommentCreatePage.class);
		addViewPage("/article/comment/delete", 1, PLATFORM_COMMON, ArticleCommentDeletePage.class);
		addViewPage("/article/like/create", 1, PLATFORM_COMMON, ArticleLikeCreatePage.class);
		
		
		addViewPage("/moment/create", 1, PLATFORM_COMMON, MomentCreatePage.class);
		addViewPage("/moment/transfer", 1, PLATFORM_COMMON, MomentTransferPage.class);
		addViewPage("/moment/delete", 1, PLATFORM_COMMON, MomentDeletePage.class);
		addViewPage("/moment/detail", 1, PLATFORM_COMMON, MomentDetailPage.class);
		addViewPage("/moment/list", 1, PLATFORM_COMMON, MomentListPage.class);
		addViewPage("/moment/list/user", 1, PLATFORM_COMMON, MomentUserListPage.class);
		addViewPage("/moment/reply/create", 1, PLATFORM_COMMON, MomentReplyCreatePage.class);
		addViewPage("/moment/reply/delete", 1, PLATFORM_COMMON, MomentReplyDeletePage.class);
		addViewPage("/moment/like/create", 1, PLATFORM_COMMON, MomentLikeCreatePage.class);
		
		
		
		
	}

}
