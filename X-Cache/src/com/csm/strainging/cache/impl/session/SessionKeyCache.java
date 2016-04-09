package com.csm.strainging.cache.impl.session;

import com.csm.strainging.cache.support.SessionKeyCacheSupport;


/**
 * @author chensongming
 */
public class SessionKeyCache extends SessionKeyCacheSupport {
	
	private static final int EXPIRE = 60 * 60 * 3; // 3 hour
	
	public static void setAndExpire(String sessionKey, long userID) {
		getCache().setex(getKey(sessionKey), Long.toString(userID), EXPIRE);
	}
	
	public static String getKey(String sessionKey) {
		return "sk:" + sessionKey;
	}
	
	

}
