package com.csm.straining.article;

import com.csm.straining.article.page.ArticleCreatePage;
import com.csm.straining.article.page.ArticleDeletePage;
import com.csm.straining.article.page.ArticleDetailPage;
import com.csm.straining.article.page.ArticleRecommedListPage;
import com.csm.straining.article.page.ArticleUserListPage;
import com.csm.straining.article.page.comment.ArticleCommentCreatePage;
import com.csm.straining.article.page.comment.ArticleCommentDeletePage;
import com.csm.straining.article.page.like.ArticleLikeCreatePage;
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
		
	}

}
