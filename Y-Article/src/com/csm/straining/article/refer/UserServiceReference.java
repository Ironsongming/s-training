package com.csm.straining.article.refer;

import com.csm.straining.article.Config;
import com.csm.straining.common.i.user.UserService;
import com.csm.straining.common.rpc.RpcClient;


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
