package com.csm.straining.common.http.page;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lamfire.utils.JSON;


/**
 * @author chensongming
 */
public abstract class JsonViewPage extends AccessLogViewPage {

	private static final Logger logger = LoggerFactory.getLogger(JsonViewPage.class);

	@Override
	protected void renderResponse(Object obj) throws Exception {
		String res = JSON.toJsonString(obj);
		
		ObjectMapper om = new ObjectMapper();
		ObjectWriter ow = om.defaultPrettyPrintingWriter();
		String logStr = ow.writeValueAsString(obj);
		
		logger.debug("[response json] : " + logStr);
		
		byte[] resData = res.getBytes();
	
		resp.setContentLength(resData.length);
		resp.setContentType("application/json;charset=UTF-8");
		resp.getOutputStream().write(resData);
	}
	

}
