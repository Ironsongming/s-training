package com.csm.straining.article.refer;

import com.csm.straining.article.Config;
import com.csm.straining.common.i.article.ArticleService;
import com.csm.straining.common.i.test.TestService;
import com.csm.straining.common.rpc.RpcClient;


/**
 * @author chensongming
 */
public class ArticleServiceReference extends RpcClient<ArticleService> {
	
	private static ArticleServiceReference reference = new ArticleServiceReference();
	
	public static ArticleService sharedService() {
		return reference.getReference();
	}

	@Override
	protected String getApplicationName() {
		return Config.getApplicationName();
	}

	@Override
	protected String getZookeeperAddr() {
		return Config.getZkperAddr();
	}
}
