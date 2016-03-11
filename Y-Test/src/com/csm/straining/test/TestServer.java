package com.csm.straining.test;

import org.eclipse.jetty.webapp.WebAppContext;

import com.csm.straining.common.http.page.ViewPageFilter;
import com.csm.straining.common.http.server.HttpServer;


/**
 * @author chensongming
 */
public class TestServer extends HttpServer {

	@Override
	protected void setupServer() throws Exception {
		setupHttpServer(getFileInRoot("webapp").getAbsolutePath());
	}

	@Override
	protected void setupWebContext(WebAppContext webapp) {
		super.setupWebContext(webapp);
		
		webapp.addFilter(ViewPageFilter.class, "/*", null);
		
	}

}
