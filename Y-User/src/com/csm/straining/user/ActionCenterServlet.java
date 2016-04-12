package com.csm.straining.user;

import com.csm.straining.common.http.servlet.ViewPageServlet;
import com.csm.straining.user.page.PhoneLoginPage;
import com.csm.straining.user.page.TestPage;
import com.csm.straining.user.page.UserCreatePage;


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
	}

}
