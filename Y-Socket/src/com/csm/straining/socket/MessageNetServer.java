package com.csm.straining.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.NetkitContext;
import com.csm.straining.common.socket.netkit.SessionGroup;
import com.csm.straining.common.socket.server.NetServer;
import com.csm.straining.common.socket.server.listener.ConnectionEventListener;
import com.csm.straining.common.socket.server.listener.SessionClosedEventListener;
import com.csm.straining.common.socket.server.listener.SessionCreatedEventListener;
import com.csm.straining.socket.action.TestAction;
import com.csm.straining.socket.cons.MessageCode;
import com.csm.straining.socket.filter.MessageFilter;


/**
 * @author chensongming
 */
public class MessageNetServer extends NetServer{

	private static final Logger logger = LoggerFactory.getLogger(MessageNetServer.class);
	
	private MessageNetServer() {
		
	}
	
	private static MessageNetServer instance = new MessageNetServer();
	
	public static MessageNetServer ins() {
		return instance;
	}
	
	private SessionGroup userSessionGroup = new SessionGroup();
	
	@Override
	protected void setupServer() throws Exception {
		
	}
	
	@Override
	protected void setupNetContext(NetkitContext context) {
		logger.info("setup net context");
		
		// 添加过滤器
		context.addActionFilter(new MessageFilter());
		
		// 注册Action
		registerAction(context);
		
		// 添加Channel监听器
		context.setChannelEventListener(new ConnectionEventListener());
		
		// 添加会话监听
		context.setSessionListenerOnClose(new SessionClosedEventListener());
		context.setSessionListenerOnCreate(new SessionCreatedEventListener());
	}
	
	private void registerAction(NetkitContext context) {
		context.registerAction(MessageCode.TestPID.REQUEST, TestAction.class);
	}
	
	@Override
	protected String getListenAddress() {
		return Config.getNetListenHost();
//		return "0.0.0.0";
	}
	
	@Override
	protected int getListenPort() {
		return Config.getNetListenPort();
//		return 7400;
	}
	
	public static void main(String[] args) {
		MessageNetServer server = ins();
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
