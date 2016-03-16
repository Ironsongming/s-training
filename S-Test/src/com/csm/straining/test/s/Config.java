package com.csm.straining.test.s;

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
	
	// RPC Server
	public static String getApplicationName() {
		return getValue("application.name");
	}

	public static String getZookeeperAddress() {
		return getValue("zookeeper.address");
	}

	public static int getServicePort() {
		return Integer.parseInt(getValue("service.port"));
	}
	
	public static String getServiceHost() {
		return getValue("service.host");
	}
	
	public static int getServiceThreadCount() {
		return Integer.parseInt(getValue("service.thread.count"));
	}
}
