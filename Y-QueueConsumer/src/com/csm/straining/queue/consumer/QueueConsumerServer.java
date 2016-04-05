package com.csm.straining.queue.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.queue.HttpsqsConfig;
import com.csm.straining.queue.QueueConsumerManager;
import com.csm.straining.queue.consumer.task.TestTask;


/**
 * @author chensongming
 */
public class QueueConsumerServer {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueConsumerServer.class);
	
	private QueueConsumerServer() {
		
	}
	
	private static QueueConsumerServer instance = new QueueConsumerServer();
	
	public static QueueConsumerServer ins() {
		return instance;
	}
	
	public void setup() {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				logger.error("fatal exception in thread" + t, e);
				
				if (e instanceof OutOfMemoryError) {
					System.exit(100);
				}
			
			}
		});
		
		HttpsqsConfig.ins().init();
		
	}
	
	public void startServer() throws Exception{
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					stopServer();
				} catch (Exception e) {
					
				}
			}
		});
		
		registerTask();
		QueueConsumerManager.ins().init();
		
		logger.info("[QueueConsumerManager startup]...");
	}
	
	public void registerTask() {
		QueueConsumerManager.ins().registerTask(new TestTask());
	}
	
	public void stopServer() {
		logger.info("[QueueConsumerSever shutting down]...");
		
		QueueConsumerManager.ins().shutdown();
		
	}
	
	public static void main(String[] args) {
		QueueConsumerServer server = ins();
		try {
			server.setup();
			server.startServer();
		} catch (Throwable e) {
			String msg = "Exception encountered during startup.";
			logger.debug(msg, e);
			System.out.print(msg);
			e.printStackTrace();
			System.exit(3);
		}
	}

}
