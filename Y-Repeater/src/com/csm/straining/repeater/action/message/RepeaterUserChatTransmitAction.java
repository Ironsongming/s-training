package com.csm.straining.repeater.action.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.Session;
import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.message.Message;
import com.csm.straining.common.socket.server.action.ActionSupport;
import com.csm.straining.common.socket.server.util.MessageUtil;
import com.csm.straining.repeater.RepeaterNetServer;
import com.csm.straining.repeater.client.RepeaterCode;
import com.csm.straining.repeater.client.RepeaterMessage;
import com.csm.straining.repeater.cons.Names;
import com.lamfire.utils.JSON;


/**
 * @author chensongming
 */
public class RepeaterUserChatTransmitAction extends ActionSupport {

	private static final Logger logger = LoggerFactory.getLogger(RepeaterUserChatTransmitAction.class);

	
	@Override
	protected void execute() throws ActionException {
		logger.debug("RepeaterUserChatTransmitAction clientID : " + getSession().getAttribute(Names.KEY_CLIENT_ID));
		
		RepeaterMessage message = JSON.toObjectNoExp(getRequestJson(), RepeaterMessage.class);
		if (message == null) {
			return;
		}
		
		Session session = RepeaterNetServer.ins().getClientSession(message.getTargetClientID());
		if (session == null) {
			logger.debug("RepeaterUserChatTransmitAction no target client");
			return;
		}
		
		Message repeaterMessage = MessageUtil.getMessage(RepeaterCode.UserChatPushID.REQUEST, getRequestJson());
		try {
			session.sendMessage(repeaterMessage);
		} catch (Exception e) {
			logger.debug("RepeaterUserChatTransmitAction error");
		}
		
	}
	
	

}
