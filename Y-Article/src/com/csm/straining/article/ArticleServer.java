package com.csm.straining.article;

import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.strainging.cache.RedisConfig;
import com.csm.straining.common.http.filter.ViewPageFilter;
import com.csm.straining.common.http.server.HttpServer;
import com.csm.straining.common.http.servlet.ViewPageServlet;
import com.csm.straining.repeater.client.MessageRepeaterClient;


/**
 * @author chensongming
 */
public class ArticleServer extends HttpServer {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleServer.class);
	private static ArticleServer server;
	
	@Override
	protected void setupServer() throws Exception {
		setupHttpServer(getFileInRoot("webapp").getAbsolutePath());
	}

	@Override
	protected void setupWebContext(WebAppContext webapp) {
		super.setupWebContext(webapp);
		
		webapp.addFilter(ViewPageFilter.class, "/*", null);
		webapp.addServlet(ActionCenterServlet.class, "/__s__");
	}
	
	@Override
	protected String getHttpListenAddress() {
//		return "127.0.0.1";
		return Config.getHttpListenHost();
	}
	
	@Override
	protected int getHttpListenPort() {
//		return 8080;
		return Config.getHttpListenPort();
	}
	
	public static void main(String[] args) {
		server = new ArticleServer();
		try {
			
			RedisConfig.ins().init();
			
			server.setup(System.getProperty("server.root.path"));
			server.startServer();
		} catch (Throwable e) {
			String msg = "Exception encountered during startup.";
			logger.debug(msg, e);
			System.out.print(msg);
			e.printStackTrace();
			System.exit(3);
		}

	}
	
	

}
