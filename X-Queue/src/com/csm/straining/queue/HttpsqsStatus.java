package com.csm.straining.queue;

import java.util.regex.Pattern;


/**
 * @author chensongming
 */
public class HttpsqsStatus {
	
	public String version;
	
	public String queueName;
	
	public long maxNumber;
	
	public long putPosition;
	
	public long putLap;
	
	public long getPosition;
	
	public long getLap;
	
	public long unreadNumber;
	
	protected static Pattern pattern = Pattern.compile("HTTP Simple Queue Service v(.+?)\\s(?:.+?)\\sQueue Name: (.+?)\\sMaximum number of queues: (\\d+)\\sPut position of queue \\((\\d+)st lap\\): (\\d+)\\sGet position of queue \\((\\d+)st lap\\): (\\d+)\\sNumber of unread queue: (\\d+)");
	

}
