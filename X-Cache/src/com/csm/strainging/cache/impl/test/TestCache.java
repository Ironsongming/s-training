package com.csm.strainging.cache.impl.test;

import com.csm.strainging.cache.support.TestCacheSupport;


/**
 * @author chensongming
 */
public class TestCache extends TestCacheSupport {
	
	private static final int EXPIRE = 60 * 60 * 24 * 2;

	public static void set(String value) {
		getCache().setex(getKey(), value, EXPIRE);
	}
	
	private static String getKey() {
		return "t:" + System.currentTimeMillis();
	}
	
	

}
