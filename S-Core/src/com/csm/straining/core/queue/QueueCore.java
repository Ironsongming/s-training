package com.csm.straining.core.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.queue.HttpsqsClient;
import com.csm.straining.queue.HttpsqsConfig;
import com.csm.straining.queue.HttpsqsException;
import com.csm.straining.queue.QueueNames;


/**
 * @author chensongming
 */
public class QueueCore {
	
	private static Logger logger = LoggerFactory.getLogger(QueueCore.class);
	
	public static void test(String s) {
		HttpsqsClient client = HttpsqsConfig.ins().getHttpsqsClient(QueueNames.test.TEST);
		try {
			client.putString(QueueNames.test.TEST, s);
		} catch (HttpsqsException e) {
			logger.error("[Error_queue_TEST]", e);
		}
				
	}

}
