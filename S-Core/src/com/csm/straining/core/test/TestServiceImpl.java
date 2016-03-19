package com.csm.straining.core.test;

import com.csm.straining.common.i.test.TestService;
import com.csm.straining.dataaccess.caps.test.TestCaps;


/**
 * @author chensongming
 */
public class TestServiceImpl implements TestService {

	@Override
	public String test() {
		TestCaps.insert();
		return "TEST";
	}

}
