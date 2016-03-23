package com.csm.straining.test.refer;

import com.csm.straining.common.i.test.TestService;
import com.csm.straining.common.rpc.RpcClient;
import com.csm.straining.test.Config;


/**
 * @author chensongming
 */
public class TestServiceReference extends RpcClient<TestService> {
	
	private static TestServiceReference reference = new TestServiceReference();
	
	public static TestService sharedService() {
		return reference.getReference();
	}

	@Override
	protected String getApplicationName() {
//		return "y-test";
		return Config.getApplicationName();
	}

	@Override
	protected String getZookeeperAddr() {
//		return "localhost:2181";
		return Config.getZkperAddr();
	}
}
