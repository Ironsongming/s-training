package com.csm.straining.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author chensongming
 */
public class SyncLockMap {
	
	private static final Logger logger = LoggerFactory.getLogger(SyncLockMap.class);
	
	static class SyncObj {
		long lastUpdateTime;	
		
		void refreshTime() {
			this.lastUpdateTime = System.currentTimeMillis();
		}
	}
	
	private Map<String, SyncObj> objMap = new ConcurrentHashMap<String, SyncLockMap.SyncObj>();
	
	private static Object lockObj = new Object();
	
	private int cleanInterval = 1000;
	
	public SyncLockMap() {
		this(60 * 1000 * 2);
	}

	public SyncLockMap(int expireTime) {
		this.cleanInterval = expireTime;
		Timer cleanupTimer = new Timer("Sync Object Map cleanup");
		cleanupTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if (objMap.size() < 1000) {
					return;
				}
				
				synchronized (lockObj) {
					List<String> removableKeys = new ArrayList<String>();
					
					long s = System.currentTimeMillis();
					for (String key : objMap.keySet()) {
						SyncObj obj = objMap.get(key);
						if (obj != null) {
							if (System.currentTimeMillis() - obj.lastUpdateTime > cleanInterval) {
								removableKeys.add(key);
							}
						}
					}
					
					for (String key : removableKeys) {
						objMap.remove(key);
					}
					
					logger.info("cleanup unused sync obj " + removableKeys.size() + ", left: " 
								+ objMap.size() + ", use time: " + (System.currentTimeMillis() - s) + " ms");
				}
 			}
		}, cleanInterval, cleanInterval);
		
	}
	
	public Object getLockObject(String key) {
		if (key == null) {
			logger.warn("key of lock obj is null");
			return new Object();
		}
		
		synchronized (lockObj) {
			SyncObj obj = objMap.get(key);
			if (obj == null) {
				obj = new SyncObj();
				objMap.put(key, obj);
			}
			obj.refreshTime();
			return obj;
		}
	}
}
