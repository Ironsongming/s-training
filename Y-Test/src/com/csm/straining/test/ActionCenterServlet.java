package com.csm.straining.test;

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
		addViewPage("/test", 1, PLATFORM_COMMON, null);
	}

}
