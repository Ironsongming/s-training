package com.csm.straining.common.http.page;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.cons.ParamNames;
import com.csm.straining.common.exception.ParamVaildException;
import com.lamfire.utils.NumberUtils;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public abstract class ViewPage {
	
	private static final Logger logger = LoggerFactory.getLogger(ViewPage.class);
	
	protected HttpServletRequest req;
	protected HttpServletResponse resp;
	
	public void setupReqAndResp(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
	
	protected void beforeDoService() {}
	
	protected abstract Object doService();
	
	protected void afterDoService() {}
	
	protected abstract void renderResponse(Object obj) throws Exception;

	public void process() throws Exception {
		logger.debug("[Parameters]: " + req.getParameterMap());
		
		Object obj = null;
		if (auth() || !authAutoFail()) {
			beforeDoService();
			obj = doService();
			afterDoService();
		} else {
			obj = getAuthFailResult();
		}
		
		renderResponse(obj);
	}
	
	protected boolean auth() {
		return true;
	}
	
	protected boolean authAutoFail() {
		return true;
	}
	
	protected Object getAuthFailResult() {
		return null;
	}
	
	protected String getParamString(String key) {
		return StringUtils.trim(req.getParameter(key));
	}
	
	protected String getParamStringRequired(String key) throws ParamVaildException {
		String t = getParamString(key);
		if (StringUtils.isEmpty(t)) {
			throw new ParamVaildException(101, key);
		}
		return t;
	}

	protected Object getAttribute(String key) {
		return req.getAttribute(key);
	}
	
	protected int getAppVerison() throws ParamVaildException {
		return NumberUtils.toInt(getParamStringRequired(ParamNames.APP_VERSION));
	}
	
	protected int getApiVersion() throws ParamVaildException {
		return NumberUtils.toInt(getParamStringRequired(ParamNames.API_VERSION));
	}
	
	protected int getPlatForm() throws ParamVaildException {
		return NumberUtils.toInt(getParamStringRequired(ParamNames.PLATFORM));
	}
	
	protected String getRealIP() {
		return req.getHeader("X-Real-IP");
	}
	
	protected String getRemoteAddr() {
		return req.getHeader("X-Remote-Addr");
	}
	
	protected String getHeaders() {
		@SuppressWarnings("rawtypes")
		Enumeration headerNames = req.getHeaderNames();
		StringBuilder sb = new StringBuilder();
		while (headerNames.hasMoreElements()) {
			String hn = (String) headerNames.nextElement();
			String val = req.getHeader(hn);
			sb.append(hn).append(":").append(val).append("\n");
		}
		return sb.toString();
	}
	
}
