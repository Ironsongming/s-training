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
	
	public static void delByUserID(long userID) {
		getCache().zrem(getKey(), Long.toString(userID));
	}
	
	public static int getServerID(long userID) {
		Double d = getCache().zscore(getKey(), Long.toString(userID));
		if (d != null) {
			return d.intValue();
		}
		return 0;
	}

}
