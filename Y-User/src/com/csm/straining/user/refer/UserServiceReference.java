package com.csm.straining.user.refer;

import com.csm.straining.common.i.user.UserService;
import com.csm.straining.common.rpc.RpcClient;
import com.csm.straining.user.Config;


/**
 * @author chensongming
 */
public class UserServiceReference extends RpcClient<UserService> {
	
	private static UserServiceReference reference = new UserServiceReference();
	
	public static UserService sharedService() {
		return reference.getReference();
	}

	@Override
	protected String getApplicationName() {
//		return "y-user";
		return Config.getApplicationName();
	}

	@Override
	protected String getZookeeperAddr() {
//		return "localhost:2181";
		return Config.getZkperAddr();
	}
}
