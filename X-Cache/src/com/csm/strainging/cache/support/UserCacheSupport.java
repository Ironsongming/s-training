package com.csm.strainging.cache.support;

import com.csm.strainging.cache.RedisCache;
import com.csm.strainging.cache.RedisConfig;
import com.csm.strainging.cache.RedisNames;


/**
 * @author chensongming
 */
public class UserCacheSupport {
	
	public static RedisCache getCache() {
		return RedisConfig.ins().getRedisCache(RedisNames.USER);
	}

}
