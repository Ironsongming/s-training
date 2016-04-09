package com.csm.straining.core.test;

import com.csm.strainging.cache.impl.test.TestCache;
import com.csm.straining.common.i.test.TestService;
import com.csm.straining.core.queue.QueueCore;
import com.csm.straining.dataaccess.caps.test.TestCaps;


/**
 * @author chensongming
 */
public class TestServiceImpl implements TestService {

	@Override
	public String test() {
		TestCaps.insert();
		TestCache.set("chensongming");
		QueueCore.test("chensongming");
		return "TEST";
	}

}
