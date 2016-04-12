package com.csm.strainging.cache.impl.user;

import com.csm.strainging.cache.support.UserCacheSupport;


/**
 * @author chensongming
 */
public class UserInfoCache extends UserCacheSupport{
	
	private static final String SESSION_KEY = "key";
	
	private static String getKey(long userID) {
		return "user:" + Long.toString(userID);
	}

	public static void setSessionKey(long userID, String key) {
		getCache().hset(getKey(userID), SESSION_KEY, key);
	}
	
	public static String getSessionKey(long userID) {
		return getCache().hget(getKey(userID), SESSION_KEY);
	}
	
	public static void delSessionKey(long userID) {
		getCache().hdel(getKey(userID), SESSION_KEY);
	}
	
	
	
}
