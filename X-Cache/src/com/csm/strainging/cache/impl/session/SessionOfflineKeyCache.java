package com.csm.strainging.cache.impl.session;

import com.csm.strainging.cache.support.SessionCacheSupport;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class SessionOfflineKeyCache extends SessionCacheSupport {
	
	private static final int EXPIRE = 60 * 60 * 3; // 3 hour
	
	public static void setAndExpire(String sessionKey, long userID) {
		getCache().setex(getKey(sessionKey), Long.toString(userID), EXPIRE);
	}
	
	public static long get(String sessionKey) {
		String v = getCache().get(getKey(sessionKey));
		if (StringUtils.isNotBlank(v)) {
			return Long.parseLong(v);
		}
		return 0;
	}
	
	public static void del(String sessionKey) {
		getCache().del(getKey(sessionKey));
	}
	
	public static boolean exists(String sessionKey) {
		return get(sessionKey) > 0;
	}
	
	private static String getKey(String sessionKey) {
		return "sk:offline:" + sessionKey;
	}
	
	

}
