package com.csm.straining.queue;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.configcenter.ConfigLoadListener;
import com.csm.straining.common.configcenter.StoreConfigCore;
import com.lamfire.utils.HttpClient;
import com.lamfire.utils.JSON;
import com.lamfire.utils.NumberUtils;
import com.lamfire.utils.StringUtils;



/**
 * @author chensongming
 */
public class HttpsqsConfig implements ConfigLoadListener {

	private static final Logger logger = LoggerFactory.getLogger(HttpsqsConfig.class);
	
	private HttpsqsConfig() {
	}
	
	private static HttpsqsConfig instance = new HttpsqsConfig();
	
	public static HttpsqsConfig ins() {
		return instance;
	}
	
	private Properties props = new Properties();
	private StoreConfigCore storeConfigCore = new StoreConfigCore();
	
	private Map<String, HttpsqsClient> httpsqsClientMap = new ConcurrentHashMap<String, HttpsqsClient>();
	
	public void init() {
		HttpsqsConfig.ins().initT();
	}
	
	public void initT() {
		props = storeConfigCore.init(StoreConfigCore.HTTPSQS, 5);
		
		logger.debug(JSON.toJsonString(props));
		
		Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Object, Object> entry = it.next();
			String key = (String) entry.getKey();
			initQueue(key, props);
		}
		
		storeConfigCore.setConfigLoadListener(this);
		
	}
	
	@Override
	public void onConfigLoad(Properties props) {
		Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Object, Object> entry = it.next();
			String key = (String) entry.getKey();
			
			String value = StringUtils.defaultString(props.getProperty(key));
			String oldVal = StringUtils.defaultString(this.props.getProperty(key));
			
			boolean needReload = false;
			
			if (!value.equals(oldVal)) {
				needReload = true;
			}
			
			if (needReload) {
				logger.info("[httpsqs config reload] httpsqs queue name [" + key + "], reload start");
				initQueue(key, props);
				logger.info("[httpsqs config reload] httpsqs queue name [" + key + "], reload end");
			}
		}
		
		this.props = props;
	}
	
	public HttpsqsClient getHttpsqsClient(String key) {
		return httpsqsClientMap.get(key);
	}
	
	private void initQueue(String key, Properties props) {
		HttpsqsConnectInfo info = getHttpsqsConnectInfo(key, props);
		
		if (info == null) {
			return;
		}
		
		Httpsqs4j httpsqs4j = new Httpsqs4j();
		httpsqs4j.setConnectionInfo(info.getHost(), info.getPort(), "UTF-8");
		httpsqsClientMap.put(key, httpsqs4j.createHttpsqsClient());
	}
	
	private HttpsqsConnectInfo getHttpsqsConnectInfo(String key, Properties props) {
		String val = props.getProperty(key);
		if (null == val) {
			return null;
		}
		
		String[] tokens = val.split(":");
		if (tokens.length != 2) {
			return null;
		}
		
		HttpsqsConnectInfo info = new HttpsqsConnectInfo();
		info.setHost(tokens[0]);
		info.setPort(NumberUtils.toInt(tokens[1]));
		return info;
	}
	
}
