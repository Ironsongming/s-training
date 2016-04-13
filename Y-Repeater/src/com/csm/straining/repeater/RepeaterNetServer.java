package com.csm.straining.repeater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.NetkitContext;
import com.csm.straining.common.socket.netkit.Session;
import com.csm.straining.common.socket.netkit.SessionGroup;
import com.csm.straining.common.socket.server.NetServer;
import com.csm.straining.common.socket.server.listener.ConnectionEventListener;
import com.csm.straining.common.socket.server.listener.SessionClosedEventListener;
import com.csm.straining.common.socket.server.listener.SessionCreatedEventListener;
import com.csm.straining.repeater.action.HeartbeatAction;
import com.csm.straining.repeater.action.LoginAction;
import com.csm.straining.repeater.action.TestAction;
import com.csm.straining.repeater.action.message.RepeaterForceOfflineAction;
import com.csm.straining.repeater.client.RepeaterCode;


/**
 * @author chensongming
 */
public class RepeaterNetServer extends NetServer{

	private static final Logger logger = LoggerFactory.getLogger(RepeaterNetServer.class);
	
	private RepeaterNetServer() {
		
	}
	
	private static RepeaterNetServer instance = new RepeaterNetServer();
	
	public static RepeaterNetServer ins() {
		return instance;
	}
	
	private SessionGroup messageSessionGroup = new SessionGroup();
	
	@Override
	protected void setupServer() throws Exception {
		
	}
	
	@Override
	protected void setupNetContext(NetkitContext context) {
		logger.info("setup net context");
		
		// 注册Action
		registerAction(context);
		
		// 添加Channel监听器
		context.setChannelEventListener(new ConnectionEventListener());
		
		// 添加会话监听
		context.setSessionListenerOnClose(new SessionClosedEventListener());
		context.setSessionListenerOnCreate(new SessionCreatedEventListener());
	}
	
	private void registerAction(NetkitContext context) {
		context.registerAction(RepeaterCode.TestPID.REQUEST, TestAction.class);
		context.registerAction(RepeaterCode.HeartbeatPID.REQUEST, HeartbeatAction.class);
		context.registerAction(RepeaterCode.LoginPID.REQUEST, LoginAction.class);
		context.registerAction(RepeaterCode.ForceOfflineSendPID.REQUEST, RepeaterForceOfflineAction.class);
	}
	
	public void addMessageSession(int clientID, Session session) {
		Session oldSession = messageSessionGroup.remove(clientID);
		if (oldSession != null) {
			oldSession.close();
		}
		messageSessionGroup.add(clientID, session);
	}
	
	public Session getClientSession(int clientID) {
		return messageSessionGroup.get(clientID);
	}
	
	@Override
	protected String getListenAddress() {
		return Config.getNetListenHost();
	}
	
	@Override
	protected int getListenPort() {
		return Config.getNetListenPort();
	}
	
	public static void main(String[] args) {
		RepeaterNetServer server = ins();
		try {
			server.setup();
			server.startServer();
		} catch (Throwable e) {
			String msg = "Exception encountered start up";
			logger.error(msg, e);
			System.exit(3);
		}
	}
	

}
