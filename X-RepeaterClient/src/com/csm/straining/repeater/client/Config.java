package com.csm.straining.repeater.client;

import java.util.Map;

import com.lamfire.utils.PropertiesUtils;


/**
 * @author chensongming
 */
public class Config {
	
	private Config() {
		
	}
	
	private static Map<String, String> configMap;
	
	static {
		configMap = PropertiesUtils.loadAsMap("repeater-client.properties", Config.class);
	}
	
	public static String getValue(String key) {
		return configMap.get(key);
	}
	
	public static String getRepeaterHost() {
		return getValue("repeater.host");
	}
	
	public static int getRepeaterPort() {
		return Integer.parseInt(getValue("repeater.port"));
	}
	
	public static int getServerID() {
		return Integer.parseInt(getValue("server.id"));
	}
	
}
