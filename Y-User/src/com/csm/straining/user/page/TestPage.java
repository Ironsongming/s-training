package com.csm.straining.user.page;

import com.csm.straining.common.http.page.JsonViewPage;
import com.csm.straining.common.model.info.common.ResponseStatus;
import com.csm.straining.user.refer.TestServiceReference;


/**
 * @author chensongming
 */
public class TestPage extends JsonViewPage {

	@Override
	protected Object doService() {
		
		System.out.println(TestServiceReference.sharedService().test());
		
		return new ResponseStatus();
	}

}
