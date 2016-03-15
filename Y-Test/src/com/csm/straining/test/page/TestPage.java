package com.csm.straining.test.page;

import com.csm.straining.common.http.page.JsonViewPage;
import com.csm.straining.common.model.info.common.ResponseStatus;


/**
 * @author chensongming
 */
public class TestPage extends JsonViewPage {

	@Override
	protected Object doService() {
		return new ResponseStatus();
	}

}
