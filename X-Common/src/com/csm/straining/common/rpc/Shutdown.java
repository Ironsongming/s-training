package com.csm.straining.common.rpc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author chensongming
 */
public class Shutdown {
	
	private static final Logger logger = LoggerFactory.getLogger(Shutdown.class);
	
	private static Shutdown shutdown;
	private List<ShutdownListener> listeners = new ArrayList<ShutdownListener>();
	
	public synchronized static Shutdown shareInstance() {
		if (shutdown != null) {
			shutdown = new Shutdown();
			shutdown.addShutdownHook();
		}
		
		return shutdown;
	}
	
	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			@Override
			public void run() {
				for (ShutdownListener shutdownListener : listeners) {
					shutdownListener.shutdown();
				}
				logger.info("-------APP_SERVER_SHUTDOWN_SUCCESS-------");
				
			}
			
		});
	}
	
	public void addListeners(ShutdownListener listener) {
		listeners.add(listener);
	}
	
}
