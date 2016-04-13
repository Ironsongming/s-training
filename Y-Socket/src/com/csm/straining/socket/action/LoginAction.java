package com.csm.straining.socket.action;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.strainging.cache.impl.session.SessionKeyCache;
import com.csm.strainging.cache.impl.session.SessionOfflineKeyCache;
import com.csm.strainging.cache.impl.session.SessionServerCache;
import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.message.Message;
import com.csm.straining.common.socket.server.action.ActionSupport;
import com.csm.straining.common.socket.server.util.MessageUtil;
import com.csm.straining.repeater.client.MessageRepeaterClient;
import com.csm.straining.socket.MessageNetServer;
import com.csm.straining.socket.action.result.FailResult;
import com.csm.straining.socket.cons.MessageCode;
import com.csm.straining.socket.cons.Names;
import com.lamfire.utils.JSON;


/**
 * @author chensongming
 */
public class LoginAction extends ActionSupport {

	private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);
	
	@Override
	protected void execute() throws ActionException {
		String key = getParamsString("key");
		
		// 没有key
		if (null == key) {
			Message message = MessageUtil.getMessage(MessageCode.LoginPID.FAIL, new FailResult("key不存在").toJson());
			MessageUtil.sendMessageAndRmCloseListener(getSession(), message);
			logger.warn("login no key" + key);
			return;
		}
		
		// key被强制登出
		boolean isOfflineKey = SessionOfflineKeyCache.exists(key);
		if (isOfflineKey) {
			Message message = MessageUtil.getMessage(MessageCode.LoginPID.FAIL, new FailResult("请重新登录").toJson());	
			MessageUtil.sendMessageAndRmCloseListener(getSession(), message);
			logger.warn("login offline key" + key);
			return;
		}
		
		// 获取userID
		long userID = SessionKeyCache.get(key);
		if (userID <= 0) {
			Message message = MessageUtil.getMessage(MessageCode.LoginPID.FAIL, new FailResult("该用户不存在").toJson());	
			MessageUtil.sendMessageAndRmCloseListener(getSession(), message);
			logger.warn("login no user" + key);
			return;
		}
		
		// 装填必要信息
		logger.debug(String.format("login user : userID : %d, version : %d", userID, version));
		getSession().setAttribute(Names.Session.KEY_ID, key);
		getSession().setAttribute(Names.Session.KEY_USER_ID, userID);
		getSession().setAttribute(Names.Session.KEY__V, version);
		
		// 保存用户Session
		MessageNetServer.ins().addUserSession(userID, getSession());
		
		// userID-serverID 存入缓存
		SessionServerCache.add(userID, MessageRepeaterClient.ins().getServerID());
		
		// 更新sk的有效期
		SessionKeyCache.expire(key);
		
		// 返回给客户端
		sendMessage(MessageCode.LoginPID.SUCCESS, JSON.toJsonString(new HashMap<String, Object>()));
		
	}
	
}
