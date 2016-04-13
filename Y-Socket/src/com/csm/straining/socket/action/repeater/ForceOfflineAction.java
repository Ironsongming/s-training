package com.csm.straining.socket.action.repeater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.strainging.cache.impl.session.SessionServerCache;
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
public class ForceOfflineAction extends ActionSupport {

	private static final Logger logger = LoggerFactory.getLogger(ForceOfflineAction.class);
	
	@Override
	protected void execute() throws ActionException {
		RepeaterMessage message = JSON.toObjectNoExp(getRequestJson(), RepeaterMessage.class);
		if (message == null) {
			return;
		}
		
		Session session = MessageNetServer.ins().getUserSession(message.getTargetID());
		if (session == null) {
			logger.info("ForceOfflineAction no target userID");
			return;
		}
		
		// 删除useID－serverID
		SessionServerCache.delByUserID(message.getTargetID());
		
		// 移除session
		MessageNetServer.ins().removeUserSession(session);
		
		Message msg = MessageUtil.getMessage(MessageCode.ForceOfflinePushPID.REQUEST, JSON.toJsonString(message.getMsg()));
		
		try {
			session.sendMessage(msg);
		} catch (Exception e) {
			logger.error("ForceOfflineAction error");
		}
	}
	

}
