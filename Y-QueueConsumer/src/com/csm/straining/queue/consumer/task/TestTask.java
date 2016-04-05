package com.csm.straining.queue.consumer.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.queue.QueueNames;
import com.csm.straining.queue.QueueTask;



/**
 * @author chensongming
 */
public class TestTask extends QueueTask {
	
	private static final Logger logger = LoggerFactory.getLogger(TestTask.class);

	@Override
	public String getQueueName() {
		return QueueNames.test.TEST;
	}

	@Override
	protected void process(String v) throws Exception {
		logger.info("test task :" + v);
	}

}
