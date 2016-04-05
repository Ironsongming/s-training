package com.csm.straining.queue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.queue.HttpsqsClient;
import com.csm.straining.queue.HttpsqsConfig;
import com.csm.straining.queue.HttpsqsException;


/**
 * @author chensongming
 */
public abstract class QueueTask {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueTask.class);
	
	private List<DealRun> threadRunList;
	
	private static Map<String, QueueGetStat> statCounterMap = new ConcurrentHashMap<String, QueueTask.QueueGetStat>();

	private static SyncLockMap syncLockMap = new SyncLockMap();
	
	private static Timer statTimer;
	
	public void restartTask(int threadNum) {
		logger.info("[qc_task_restart] " + getQueueName() + ", num: " + threadNum);
		
		stopTask();
		startTask(threadNum);
	}
	
	public void stopTask() {
		logger.info("[qc_task_stop] " + getQueueName());
		for (DealRun dealRun : threadRunList) {
			dealRun.terminate();
		}
	}
	
	public void startTask(int threadNum) {
		logger.info("[qc_task_start] " + getQueueName() + ", num: " + threadNum);
		
		threadRunList = new ArrayList<QueueTask.DealRun>();
		
		for (int i = 0; i < threadNum; i++) {
			DealRun threadRun = new DealRun();
			new Thread(threadRun).start();
			threadRunList.add(threadRun);
		}
		
		logger.info("[queue_start] " + threadNum);
		
	}
	
	public abstract String getQueueName();
	
	private class DealRun implements Runnable {

		private volatile boolean running = true;
		
		public void terminate() {
			running = false;
		}
		
		@Override
		public void run() {
			while (true) {
				if (!running) {
					logger.info("[qc_task_thread_stop] " + getQueueName() + ", thread: " + Thread.currentThread());
					break;
				}
				
				QueueGetStat stat = statCounterMap.get(getQueueName());
				if (stat == null) {
					Object lock = syncLockMap.getLockObject(getQueueName());
					synchronized (lock) {
						stat = statCounterMap.get(getQueueName());
						if (stat == null) {
							stat = new QueueGetStat();
							statCounterMap.put(getQueueName(), stat);
						}
					}
				}
				
				stat.total.incrementAndGet();
				
				String v = null;
				long s = System.currentTimeMillis();
				try {
					v = HttpsqsConfig.ins().getHttpsqsClient(getQueueName()).getString(getQueueName());
				} catch (HttpsqsException e) {
					logger.error("error get from queue: " + getQueueName(), e);
				}
				
				long t = System.currentTimeMillis() - s;
				if (t > 1000L * 2) {
					logger.warn(String.format("[queue_get_long_time] name: %s, time: %s, v: ", getQueueName(), t) +  v);
				}
				
				if (v == null) {
					stat.miss.incrementAndGet();
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						
					}
					
					continue;
				} else {
					stat.hit.incrementAndGet();	
				}
				
				toProcess(v);
			}
			
		}
		
		private void toProcess(final String v) {
			long s = System.currentTimeMillis();
			try {
				process(v);
			} catch (Throwable e) {
				logger.error("error process queue " + getQueueName(), e);
			}
			
			long t = System.currentTimeMillis() - s;
			
			if (t > 1000L * 3) {
				logger.warn(String.format("[queue_process_long_time] name: %s, time: %s, v: ", getQueueName(), t) +  v);
			}
		}
		
	}
	
	static {
		statTimer = new Timer("queue task statistic timer");
		statTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				doStatistic();
			}
		}, 1000L * 30, 1000L * 30);
	}
	
	private static void doStatistic() {
		Iterator<Entry<String, QueueGetStat>> it = statCounterMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, QueueGetStat> entry = it.next();
			String queueName = entry.getKey();
			QueueGetStat stat = entry.getValue();
			
			int total = stat.total.getAndSet(0);
			int hit = stat.hit.getAndSet(0);
			int miss = stat.miss.getAndSet(0);
			
			logger.info(String.format("[queue_stat] name: %s, total: %d, hit: %d, miss: %d ", queueName, total, hit, miss));
 		}
	}
	
	public static void shutdown() {
		statTimer.cancel();
	}
	
	protected abstract void process(String v) throws Exception;
	
	static class QueueGetStat {
		AtomicInteger total = new AtomicInteger(0);
		AtomicInteger hit = new AtomicInteger(0);
		AtomicInteger miss = new AtomicInteger(0);
	}

}
