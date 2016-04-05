package com.csm.straining.queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author chensongming
 */
public class QueueConsumerManager {

	private static Logger logger = LoggerFactory.getLogger(QueueConsumerManager.class);
	
	private QueueConsumerManager() {
		
	}
	
	private static QueueConsumerManager instance = new QueueConsumerManager();
	
	public static QueueConsumerManager ins() {
		return instance;
	}
	
	private boolean isInited = false;
	
	private static Map<String, QueueTask> tasks = new HashMap<String, QueueTask>();
	
	public synchronized void registerTask(QueueTask initTask) {
		logger.info("[qc_manager_register_task]" + initTask.getQueueName());
		
		QueueTask task = tasks.get(initTask.getQueueName());
		
		if (null == task) {
			tasks.put(initTask.getQueueName(), initTask);
		}
	}
	
	public synchronized void init() {
		logger.info("[qc_manager_init] begin...");
		if (isInited) {
			logger.info("[qc_manager_init] abort...");
			return;
		}
		
		QueueConsumerConfigManager.ins().init();
		
		startAll();
		
		isInited = true;
		
		logger.info("[qc_manager_init] end...");
	}
	
	private void startAll() {
		logger.info("[qc_manager_start_all_task] begin...");
		
		Iterator<Entry<String, QueueTask>> it = tasks.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, QueueTask> entry = it.next();
			QueueTask task = entry.getValue();
			
			QueueConsumerConfigInfo configInfo = QueueConsumerConfigManager.ins().getConfigInfo(task.getQueueName());
			task.startTask(configInfo.getThreadNum());
		}
		
		logger.info("[qc_manager_start_all_task] finished...");
	}
	
	public void shutdown() {
		logger.info("qc_manager_shutdown...");
		
		QueueConsumerConfigManager.ins().stop();
		
		Iterator<Entry<String, QueueTask>> it = tasks.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, QueueTask> entry = it.next();
			QueueTask task = entry.getValue();
			
			task.stopTask();
		}
		
		logger.info("[qc_manager_start_all_task] finished...");
	}
	
	public void onConfigChange(QueueConsumerConfigInfo info) {
		logger.info("qc_manager_on_config_change" + info.toString());
		QueueTask task = tasks.get(info.getName());
		if (task != null) {
			task.restartTask(info.getThreadNum());
		}
	}
 	
}
