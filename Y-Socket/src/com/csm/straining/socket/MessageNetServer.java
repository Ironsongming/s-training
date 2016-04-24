package com.csm.straining.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.strainging.cache.RedisConfig;
import com.csm.strainging.cache.impl.session.SessionServerCache;
import com.csm.straining.common.socket.netkit.NetkitContext;
import com.csm.straining.common.socket.netkit.Session;
import com.csm.straining.common.socket.netkit.SessionGroup;
import com.csm.straining.common.socket.server.NetServer;
import com.csm.straining.common.socket.server.listener.ConnectionEventListener;
import com.csm.straining.common.socket.server.listener.SessionClosedEventListener;
import com.csm.straining.common.socket.server.listener.SessionCreatedEventListener;
import com.csm.straining.repeater.client.MessageRepeaterClient;
import com.csm.straining.repeater.client.RepeaterCode;
import com.csm.straining.socket.action.HeartbeatAction;
import com.csm.straining.socket.action.LoginAction;
import com.csm.straining.socket.action.LogoutAction;
import com.csm.straining.socket.action.TestAction;
import com.csm.straining.socket.action.UserChatAction;
import com.csm.straining.socket.action.repeater.ForceOfflineAction;
import com.csm.straining.socket.action.repeater.UserChatPushAction;
import com.csm.straining.socket.cons.MessageCode;
import com.csm.straining.socket.filter.MessageFilter;
import com.lamfire.utils.JSON;


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
		
		// 初始化缓存
		RedisConfig.ins().init();
				
		// 清除userID－serverID
		SessionServerCache.delByServerID(MessageRepeaterClient.ins().getServerID());

		
		MessageRepeaterClient.ins().registerRepeater(RepeaterCode.ForceOfflinePushPID.REQUEST, ForceOfflineAction.class);
		MessageRepeaterClient.ins().registerRepeater(RepeaterCode.UserChatPushID.REQUEST, UserChatPushAction.class);
		
		// 登录中继服务器
		MessageRepeaterClient.ins().setup();
		
		
		MessageRepeaterClient.ins().startClient();
		
		
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
		context.registerAction(MessageCode.LoginPID.REQUEST, LoginAction.class);
		context.registerAction(MessageCode.LogoutPID.REQUEST, LogoutAction.class);
		context.registerAction(MessageCode.HeartbeatPID.REQUEST, HeartbeatAction.class);
		context.registerAction(MessageCode.UserChatPID.REQUEST, UserChatAction.class);
	}
	
	public void addUserSession(long userID, Session session) {
		Session oldSession = userSessionGroup.remove(userID);
		if (oldSession != null) {
			oldSession.close();
		}
		userSessionGroup.add(userID, session);
	}
	
	public Session getUserSession(long userID) {
		return userSessionGroup.get(userID);
	}
	
	public void removeUserSession(Session session) {
		userSessionGroup.remove(session);
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
