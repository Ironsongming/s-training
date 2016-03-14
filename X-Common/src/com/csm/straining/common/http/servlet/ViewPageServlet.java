package com.csm.straining.common.http.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.http.page.ViewPage;
import com.lamfire.utils.NumberUtils;


/**
 * @author chensongming
 */
public abstract class ViewPageServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 5863786175690085910L;
	private static final Logger logger = LoggerFactory.getLogger(ViewPageServlet.class);
	private static final String DEFAULT_PATH = "__DEFAULT__";
	private static final int MAX_VERSION = 100;
	
	protected static final int PLATFORM_COMMON = 0;
	protected static final int PLATFORM_IOS = 1;
	protected static final int PLATFORM_ANDROID = 2;
	
	// 记录接口版本对应的精确关系
	private static final Map<String, Class<? extends ViewPage>> viewPageMap = new HashMap<String, Class<? extends ViewPage>>();
	
	// 记录接口寻址的缓存
	private static final Map<String, Class<? extends ViewPage>> viewPageCache = new HashMap<String, Class<? extends ViewPage>>();
	
	@Override
	public void init() throws ServletException {
		registerViewPages();
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String __path__ = (String) req.getAttribute("__path__");
		if (__path__ == null || __path__.length() <= 0) {
			__path__ = DEFAULT_PATH;
		}
		
		int __version__ = NumberUtils.toInt((String) req.getAttribute("__version__"));
		int __platform__ = NumberUtils.toInt((String) req.getAttribute("__platform__"));
		
		if (__version__ > MAX_VERSION) {
			resp.sendError(404);
			return;
		}
		
		Class<? extends ViewPage> pageCls = null;
		String originPath = getPath(__path__, __version__, __platform__);
		
		// 查询缓存
		pageCls = viewPageCache.get(originPath);
		
		// 缓存没有，api_version逐级降级查询
		if (pageCls == null) {			
			logger.debug(String.format("[servlet_mapping cache miss ...] path: %s, version: %d, platform: %d, actionCls: %s", __path__, __version__, __platform__, pageCls));
			
			for (int v = __version__; v > 0; v--) {
				// 先寻址api_version 和 platform都对应的page
				String path = getPath(__path__, v, __platform__);
				pageCls = viewPageMap.get(path);
				
				// 该版本的platform不存在，查询公用平台
				if (pageCls == null) {
					path = getPath(__path__, v, PLATFORM_COMMON);
					pageCls = viewPageMap.get(path);
				}
				
				if (pageCls != null) {
					viewPageCache.put(path, pageCls);
					break;
				}
			}
		} else {
			logger.debug(String.format("[servlet_mapping cache bingo ...] path: %s, version: %d, platform: %d, actionCls: %s", __path__, __version__, __platform__, pageCls));
		}
		
		if (pageCls != null) {
			try {
				ViewPage page = pageCls.newInstance();
				page.setupReqAndResp(req, resp);
				page.process();
			} catch (Exception e) {
				logger.error("[error process request]:" + e);
				resp.sendError(500);
			}
 		} else {
 			resp.sendError(400);
 		}
		
		
	}
	
	protected abstract void registerViewPages();
	
	protected static void addViewPage(String path, int apiVersion, int platform, Class<? extends ViewPage> cls) {
		String fullPath = getPath(path, apiVersion, platform);
		addViewPage(fullPath, apiVersion, platform, cls);
	}
	
	private static void addViewPage(String path, Class<? extends ViewPage> cls) {
		viewPageMap.put(path, cls);
	}
	
	protected static boolean exists(String key) {
		return viewPageMap.containsKey(key);	
	}
	
	private static String getPath(String path, int apiVersion, int platform) {
		return new StringBuilder(path).append("/").append(platform).append("/").append(apiVersion).toString();	
	}

}
