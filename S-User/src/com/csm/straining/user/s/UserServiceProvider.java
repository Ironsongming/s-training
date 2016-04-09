package com.csm.straining.user.s;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.strainging.cache.RedisConfig;
import com.csm.straining.common.i.user.UserService;
import com.csm.straining.common.rpc.RpcServer;
import com.csm.straining.core.user.UserServiceImpl;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.queue.HttpsqsConfig;


/**
 * @author chensongming
 */
public class UserServiceProvider extends RpcServer<UserService> {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceProvider.class);
	
	private static UserServiceProvider server = new UserServiceProvider();
	
	public static UserServiceProvider sharedInstance() {
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
	protected UserService getServiceImpl() {
		return new UserServiceImpl();
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
		
		UserServiceProvider server = UserServiceProvider.sharedInstance();
		server.start();
		
	}
	

}
