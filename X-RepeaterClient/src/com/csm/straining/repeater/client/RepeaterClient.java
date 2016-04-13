package com.csm.straining.repeater.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.NetkitClient;
import com.csm.straining.common.socket.netkit.NetkitContext;
import com.csm.straining.common.socket.netkit.Session;
import com.csm.straining.common.socket.netkit.message.Message;
import com.csm.straining.common.socket.server.util.MessageUtil;
import com.lamfire.utils.JSON;


/**
 * @author chensongming
 */
public abstract class RepeaterClient extends NetkitClient {
	
	
	private static final Logger logger = LoggerFactory.getLogger(RepeaterClient.class);
	private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	
	private NetkitClient client;
	private Session session;
	
	private ScheduledFuture<?> scheduledFuture;
	private Map<Integer, Class<?>> repeaterMap = new HashMap<Integer, Class<?>>();
	
	private int heartbeatErrorcCount = 0;
	
	public void startClient() {
		logger.debug("repeater client start....");
		session = client.createSession();
		loginRepeater();
	}
	
	public void closeClient() {
		logger.debug("repeater client close....");
	}
	
	public void setup() {
		setupNetClient();
	}
	
	private void setupNetClient() {
		NetkitContext context = new NetkitContext();
		client = new NetkitClient(getRepeaterHost(), getRepeaterPort(), context);
		
		logger.info("setup Repeater client with : " + getRepeaterHost() +  ":" + getRepeaterPort());
		
		setupNetContext(context);
	}
	
	protected void setupNetContext(NetkitContext context) {
		for (Entry<Integer, Class<?>> entry : repeaterMap.entrySet()) {
			context.registerAction(entry.getKey(), entry.getValue());
		}
	}
	
	public void registerRepeater(int funcID, Class<?> clazz) {
		repeaterMap.put(funcID, clazz);
	}
	
	public void loginRepeater() {
		logger.info("client login repeater....");
		
		// 先做测试连接
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "chensongming client login repeater");
		
//		while (true) {
//			Message message = MessageUtil.getMessage(RepeaterCode.TestPID.REQUEST, JSON.toJsonString(map));
//			send(message);
//			
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//		}
	}
	
	public void loginCallBack() {
		
	}
	
	public void send(Message message) {
		logger.info("repeater message : [" + message.getMessageID() + "] " + message.getMessageContentAsString());
		try {
			this.session.sendMessage(message);	
		} catch (Exception e) {
			logger.error("repeater message exception : ", e);
		}
	}
	
	protected abstract int getServerID();
	
	protected String getRepeaterHost() {
		return "127.0.0.1";
	}
	
	protected int getRepeaterPort() {
		return 7200;
	}
	
	protected int getMaxHeartbeatErrorcCount() {
		return 3;
	}
	
}
