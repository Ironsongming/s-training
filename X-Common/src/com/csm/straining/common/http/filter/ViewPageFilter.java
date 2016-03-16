package com.csm.straining.common.http.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.cons.ParamNames;
import com.lamfire.utils.JSON;
import com.lamfire.utils.NumberUtils;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class ViewPageFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(ViewPageFilter.class);
	public static final String IGNORE_VERSION_URIS = "IGNORE_VERSION_URIS";
	public static final String IGNORE_VERSION_URIS_SPLIT = ",";

	private static Set<String> ignoreVersionURISet = new HashSet<String>();
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) req;
		String incomingURI = httpServletRequest.getRequestURI();
		
		if (ignoreVersionURISet.contains(incomingURI)) {
			req.setAttribute("__path__", incomingURI);
			logger.debug("[Dispather_Forward_IGNORE]: " + incomingURI);
		} else {
			int apiVersion = NumberUtils.toInt(httpServletRequest.getParameter(ParamNames.API_VERSION));
			
			if (apiVersion < 0) {
				apiVersion = 1;
			}
			
			int platform = NumberUtils.toInt(httpServletRequest.getParameter(ParamNames.PLATFORM));
			
			req.setAttribute("__path__", incomingURI);
			req.setAttribute("__platform__", String.valueOf(platform));
			req.setAttribute("__version__", String.valueOf(apiVersion));
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/__s__");
		dispatcher.forward(req, resp);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String URIS = filterConfig.getInitParameter(IGNORE_VERSION_URIS);
		logger.debug("[FilterConfig InitParameter] " + IGNORE_VERSION_URIS + " = " + URIS);

		String[] uriArray = StringUtils.split(URIS, IGNORE_VERSION_URIS_SPLIT);
		
		if (uriArray == null) {
			return;
		}
		
		for (String uri : uriArray) {
			ignoreVersionURISet.add(uri);
		}
		
		logger.debug("[ignoreVersionURISet]: " + ignoreVersionURISet);
		
	}

}
