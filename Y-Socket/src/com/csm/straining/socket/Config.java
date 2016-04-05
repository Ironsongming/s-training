package com.csm.straining.socket;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lamfire.utils.PropertiesUtils;


/**
 * @author chensongming
 */
public class Config {
	
	private static Logger logger = LoggerFactory.getLogger(Config.class);
	private static Map<String, String> configMap = null;

	static {
		configMap = PropertiesUtils.loadAsMap("message.properties", Config.class);
	}
	
	public static String getValue(String key) {
		return configMap.get(key);
	}

	public static String getNetListenHost() {
		return getValue("net.listen.host");
	}

	public static int getNetListenPort() {
		String portStr = getValue("net.listen.port");
		return Integer.parseInt(portStr);
	}
	
}
