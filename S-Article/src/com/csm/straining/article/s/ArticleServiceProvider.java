package com.csm.straining.article.s;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.strainging.cache.RedisConfig;
import com.csm.straining.common.i.article.ArticleService;
import com.csm.straining.common.i.user.UserService;
import com.csm.straining.common.rpc.RpcServer;
import com.csm.straining.core.article.ArticleServiceImpl;
import com.csm.straining.core.user.UserServiceImpl;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.queue.HttpsqsConfig;


/**
 * @author chensongming
 */
public class ArticleServiceProvider extends RpcServer<ArticleService> {

	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceProvider.class);
	
	private static ArticleServiceProvider server = new ArticleServiceProvider();
	
	public static ArticleServiceProvider sharedInstance() {
		return server;
	}
	
	@Override
	protected String getApplicationName() {
		return Config.getApplicationName();
	}

	@Override
	protected String getZookeeperAddr() {
		return Config.getZookeeperAddress();
	}
	
	@Override
	protected int getServiceThreadCount() {
		return Config.getServiceThreadCount();
	}
	
	@Override
	protected ArticleService getServiceImpl() {
		return new ArticleServiceImpl();
	}

	@Override
	protected int getServicePort() {
		return Config.getServicePort();
	}
	
	@Override
	protected String getServiceHost() {
		return Config.getServiceHost();
	}
	
	public static void main(String[] args) {
		
		DbConfig.ins().init();
		RedisConfig.ins().init();
		HttpsqsConfig.ins().init();
		
		ArticleServiceProvider server = ArticleServiceProvider.sharedInstance();
		server.start();
		
	}
	

}
