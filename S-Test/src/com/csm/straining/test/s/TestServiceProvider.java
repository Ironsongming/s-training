package com.csm.straining.test.s;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.strainging.cache.RedisConfig;
import com.csm.straining.common.i.test.TestService;
import com.csm.straining.common.rpc.RpcServer;
import com.csm.straining.core.test.TestServiceImpl;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.caps.test.TestCaps;
import com.csm.straining.queue.HttpsqsConfig;


/**
 * @author chensongming
 */
public class TestServiceProvider extends RpcServer<TestService> {

	private static final Logger logger = LoggerFactory.getLogger(TestServiceProvider.class);
	
	private static TestServiceProvider server = new TestServiceProvider();
	
	public static TestServiceProvider sharedInstance() {
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
	protected TestService getServiceImpl() {
		return new TestServiceImpl();
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
		
		TestServiceProvider server = TestServiceProvider.sharedInstance();
		server.start();
		
	}
	

}
