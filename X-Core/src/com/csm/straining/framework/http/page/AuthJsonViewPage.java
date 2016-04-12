package com.csm.straining.framework.http.page;

import com.csm.strainging.cache.impl.session.SessionKeyCache;
import com.csm.straining.common.http.page.JsonViewPage;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public abstract class AuthJsonViewPage extends JsonViewPage{

	@Override
	protected boolean auth() {
		
		String key = getParamString("key");
		
		if (StringUtils.isNotBlank(key)) {
			long userID = SessionKeyCache.get(key);
			
			if (userID < 0) {
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	protected Object getAuthFailResult() {
		return new ErrorStatus(103);
	}
	

}
