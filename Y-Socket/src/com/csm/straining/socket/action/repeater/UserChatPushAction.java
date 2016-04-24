package com.csm.straining.socket.action.repeater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.Session;
import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.message.Message;
import com.csm.straining.common.socket.server.action.ActionSupport;
import com.csm.straining.common.socket.server.util.MessageUtil;
import com.csm.straining.repeater.client.RepeaterMessage;
import com.csm.straining.socket.MessageNetServer;
import com.csm.straining.socket.cons.MessageCode;
import com.lamfire.utils.JSON;


/**
 * @author chensongming
 */
public class UserChatPushAction extends ActionSupport{
	
	private static final Logger logger = LoggerFactory.getLogger(UserChatPushAction.class);

	@Override
	protected void execute() throws ActionException {
		RepeaterMessage message = JSON.toObjectNoExp(getRequestJson(), RepeaterMessage.class);
		if (message == null) {
			return;
		}
		
		Session session = MessageNetServer.ins().getUserSession(message.getTargetID());
		if (session == null) {
			logger.info("UserChatPushAction no target userID");
			return;
		}
		
		Message msg = MessageUtil.getMessage(MessageCode.UserChatPushPID.REQUEST, JSON.toJsonString(message.getMsg()));
		
		try {
			session.sendMessage(msg);
		} catch (Exception e) {
			logger.error("UserChatPushAction error");
		}
	}

}
