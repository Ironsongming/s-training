package com.csm.straining.queue;

import java.util.Iterator;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lamfire.utils.NumberUtils;
import com.lamfire.utils.PropertiesUtils;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class QueueConsumerConfigManager {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueConsumerConfigManager.class);
	
	private QueueConsumerConfigManager() {
		
	}
	
	private static QueueConsumerConfigManager instance = new QueueConsumerConfigManager();
	
	public static QueueConsumerConfigManager ins() {
		return instance;
	}
	
	private boolean inInited = false;
	
	private Properties props = new Properties();
	
	private Timer timer;
	
	synchronized void init() {
		logger.info("qc_config_manager_init begin...");
		
		if (inInited) {
			logger.info("qc_config_manager_init abort...");
			return;
		}
		
		load();
		
		if (timer != null) {
			timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					load();
				}
			}, 1000L * 5, 1000L * 5);
		}
		logger.info("qc_config_manager_init finished...");
	}
	
	void stop() {
		logger.info("qc_config_manager_init stop...");
		timer.cancel();
	}
	
	private void load() {
		logger.info("qc_config_manager_init init...");
		
		Properties props = PropertiesUtils.load("qc.properties", QueueConsumerConfigManager.class);
		
		if (inInited) {
			Iterator<Object> it = props.keySet().iterator();
			while (it.hasNext()) {
				String name = (String) it.next();
				
				String oldVal = StringUtils.defaultString(this.props.getProperty(name));
				String newVal = StringUtils.defaultString(props.getProperty(name));
				
				if (!oldVal.equals(newVal)) {
					QueueConsumerConfigInfo info = getConfigInfo(name, props);
					logger.info("qc_config_manager_init change..." + info.toString());
					
					// 回调
					QueueConsumerManager.ins().onConfigChange(info);
				}
				
				
				
			}
		}
		
		this.props = props;
 		
	}
	
	public QueueConsumerConfigInfo getConfigInfo(String name) {
		return getConfigInfo(name, this.props);
	}
	
	private QueueConsumerConfigInfo getConfigInfo(String name, Properties props) {
		String val = StringUtils.defaultString(props.getProperty(name));
		
		if (StringUtils.isBlank(val)) {
			return new QueueConsumerConfigInfo(name);
		}
		
		String[] tokens = val.split(",");
		if (tokens.length != 2) {
			return new QueueConsumerConfigInfo(name);
		}
		
		QueueConsumerConfigInfo info = new QueueConsumerConfigInfo(name);
		info.setOn(tokens[0].equals("on"));
		info.setThreadNum(NumberUtils.toInt(tokens[1]));
		
		return info;
		
	}
	

}
