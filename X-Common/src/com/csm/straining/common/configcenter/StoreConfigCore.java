package com.csm.straining.common.configcenter;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lamfire.utils.PropertiesUtils;


/**
 * @author chensongming
 */
public class StoreConfigCore {
	
	public static final String MYSQL = "mysql";
	public static final String REDIS = "redis";
	public static final String HTTPSQS = "httpsqs";
	
	private static final Logger logger = LoggerFactory.getLogger(StoreConfigCore.class);

	private Timer timer;
	
	private ConfigLoadListener loadListener;

	public Properties init(String type, int durationInSeconds) {
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				loadProperties(type);
			}
		}, 1000L * 30, 1000L * durationInSeconds);
		
		return loadProperties(type);
	}
	
	private Properties loadProperties(String type) {
		
//		File f = new File("/Users/chensongming/Documents/graduation-design/code/s-training/X-Common/conf/" + type + ".properties");
		File f = new File("/usr/local/site/conf/" + type + ".properties");
	
		Properties props = PropertiesUtils.load(f);
		
		if (loadListener != null) {
			loadListener.onConfigLoad(props);
		}
		return props;
	}
	
	public void setConfigLoadListener(ConfigLoadListener loadListener) {
		this.loadListener = loadListener;
	}
	
}
