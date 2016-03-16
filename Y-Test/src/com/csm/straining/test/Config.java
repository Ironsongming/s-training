package com.csm.straining.test;

import java.util.Map;

import com.lamfire.utils.PropertiesUtils;


/**
 * @author chensongming
 */
public class Config {
	
	private Config() {}
	
	private static Map<String, String> configMap;
	
	static {
		configMap = PropertiesUtils.loadAsMap("server.properties", Config.class);
	}
	
	public static String getValue(String key) {
		return configMap.get(key);
	}
	
	public static String getHttpListenHost() {
		return getValue("http.listen.host");
	}
	
	public static int getHttpListenPort() {
		String portStr = getValue("http.listen.port");
		return Integer.parseInt(portStr);
	}

	public static String getApplicationName() {
		return getValue("application.name");
	}
	
	public static String getZkperAddr() {
		return getValue("zookeeper.address");
	}
}
