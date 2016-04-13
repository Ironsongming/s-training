package com.csm.straining.repeater.client;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
		
		Map<String, Object> loginBean = new HashMap<String, Object>();
		loginBean.put("repeaterClientID", getServerID());
		loginBean.put("repeaterClientType", getServerType());
		Message loginMessage = MessageUtil.getMessage(RepeaterCode.LoginPID.REQUEST, JSON.toJsonString(loginBean));
		try {
			session.sendMessage(loginMessage);
			logger.info("client login repeater done ....");
		} catch (Exception e) {
			logger.info("client login repeater error ....");
		} finally {
			startHeartbeat();
		}
	}
	
	public void loginCallBack() {
		
	}
	
	public void startHeartbeat() {
		stopHeartbeat();
		scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				Message hbMessage = MessageUtil.getMessage(RepeaterCode.HeartbeatPID.REQUEST);
				try {
					session.sendMessage(hbMessage);
				} catch (Exception e) {
					heartbeatErrorcCount ++;
					logger.info("client heartbeat error ....");
				} finally {
					if (heartbeatErrorcCount >= getMaxHeartbeatErrorcCount()) {
						logger.info("heartbeatErrorcCount reach : " + getMaxHeartbeatErrorcCount());
						logger.info("client restart : " + getMaxHeartbeatErrorcCount());
						heartbeatErrorcCount = 0;
						startClient();
					}
				}
			}
		}, 10 , 10, TimeUnit.SECONDS);
	}
	
	public void stopHeartbeat() {
		if (scheduledFuture != null) {
			scheduledFuture.cancel(true);
		}
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
	
	protected abstract String getServerType();
	
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
