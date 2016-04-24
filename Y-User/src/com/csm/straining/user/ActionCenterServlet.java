package com.csm.straining.user;

import com.csm.straining.common.http.servlet.ViewPageServlet;
import com.csm.straining.user.page.FollowListPage;
import com.csm.straining.user.page.PhoneLoginPage;
import com.csm.straining.user.page.TestPage;
import com.csm.straining.user.page.UserCreatePage;
import com.csm.straining.user.page.UserDetailPage;
import com.csm.straining.user.page.UserRankPage;
import com.csm.straining.user.page.UserUpdatePage;
import com.csm.straining.user.page.contact.FollowPage;
import com.csm.straining.user.page.contact.GroupCreatePage;
import com.csm.straining.user.page.contact.GroupDetailPage;
import com.csm.straining.user.page.contact.GroupListPage;
import com.csm.straining.user.page.contact.GroupQuitPage;
import com.csm.straining.user.page.contact.UnFollowPage;
import com.csm.straining.user.page.training.TrainingCreatePage;
import com.csm.straining.user.page.training.TrainingDetailPage;
import com.csm.straining.user.page.training.TrainingFinishPage;
import com.csm.straining.user.page.training.TrainingListPage;
import com.csm.straining.user.page.training.TrainingPreCreatePage;
import com.csm.straining.user.page.training.TrainingStartPage;


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
		addViewPage("/user/account/create", 1, PLATFORM_COMMON, UserCreatePage.class);
		addViewPage("/user/login/phone", 1, PLATFORM_COMMON, PhoneLoginPage.class);
		addViewPage("/user/detail", 1, PLATFORM_COMMON, UserDetailPage.class);
		addViewPage("/user/detail/update", 1, PLATFORM_COMMON, UserUpdatePage.class);

		
		addViewPage("/user/list/follow", 1, PLATFORM_COMMON, FollowListPage.class);
		addViewPage("/user/list/rank", 1, PLATFORM_COMMON, UserRankPage.class);
		
		
		addViewPage("/contact/follow", 1, PLATFORM_COMMON, FollowPage.class);
		addViewPage("/contact/unfollow", 1, PLATFORM_COMMON, UnFollowPage.class);
		addViewPage("/contact/group/create", 1, PLATFORM_COMMON, GroupCreatePage.class);
		addViewPage("/contact/group/detail", 1, PLATFORM_COMMON, GroupDetailPage.class);
		addViewPage("/contact/group/list", 1, PLATFORM_COMMON, GroupListPage.class);
		addViewPage("/contact/group/quit", 1, PLATFORM_COMMON, GroupQuitPage.class);
		
		
		addViewPage("/training/create", 1, PLATFORM_COMMON, TrainingCreatePage.class);
		addViewPage("/training/detail", 1, PLATFORM_COMMON, TrainingDetailPage.class);
		addViewPage("/training/finish", 1, PLATFORM_COMMON, TrainingFinishPage.class);
		addViewPage("/training/list", 1, PLATFORM_COMMON, TrainingListPage.class);
		addViewPage("/training/pre/create", 1, PLATFORM_COMMON, TrainingPreCreatePage.class);
		addViewPage("/training/start", 1, PLATFORM_COMMON, TrainingStartPage.class);
		
	}

}
