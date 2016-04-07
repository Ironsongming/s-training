package com.csm.straining.pic;

import com.csm.straining.common.http.servlet.ViewPageServlet;
import com.csm.straining.pic.page.LoadPage;
import com.csm.straining.pic.page.UploadPage;


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
		addViewPage("/upload", 1, PLATFORM_COMMON, UploadPage.class);
		addViewPage("/load", 1, PLATFORM_COMMON, LoadPage.class);
	}

}
