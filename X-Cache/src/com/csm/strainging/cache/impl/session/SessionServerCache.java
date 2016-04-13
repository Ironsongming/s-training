package com.csm.strainging.cache.impl.session;

import com.csm.strainging.cache.support.SessionCacheSupport;


/**
 * @author chensongming
 */
public class SessionServerCache extends SessionCacheSupport{
	
	public static String getKey() {
		return "session:server";
	}
	
	public static void add(long userID, int serverID) {
		getCache().zadd(getKey(), serverID, Long.toString(userID));
	}
	
	public static void delByServerID(int serverID) {
		getCache().zremrangeByScore(getKey(), serverID, serverID);
	}

}
