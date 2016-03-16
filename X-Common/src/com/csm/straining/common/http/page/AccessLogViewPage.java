package com.csm.straining.common.http.page;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lamfire.utils.JSON;


/**
 * @author chensongming
 */
public abstract class AccessLogViewPage extends ViewPage {
	
	private static final Logger logger = LoggerFactory.getLogger(AccessLogViewPage.class);

	private long startAt;
	private long endAt;
	
	@Override
	protected void beforeDoService() {
		super.beforeDoService();
		
		startAt = System.currentTimeMillis();
		
		String uri = (String) req.getAttribute("__path__");
		
		Map<String, String> params = req.getParameterMap();
		
		String realIP = getRealIP();
		
		logger.debug("----------------------request begin---------------------");
		logger.debug("[request begin at ] : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(startAt)));
		logger.debug("[request uri] : " + uri);
		logger.debug("[request ip] : " + realIP);
		logger.debug("[request parmas] : " + JSON.toJsonString(params));
	}
	
	@Override
	protected void afterDoService(Object obj) {
		
		endAt = System.currentTimeMillis();
		
		logger.debug("[request end at] : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(endAt)));
		logger.debug("[request spend time] : " + (endAt - startAt) + "/ms");
		logger.debug("---------------------request end------------------------");
		super.afterDoService(obj);
	}

}
