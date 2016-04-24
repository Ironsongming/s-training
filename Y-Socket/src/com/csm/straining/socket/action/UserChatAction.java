package com.csm.straining.socket.action;

import com.csm.strainging.cache.impl.session.SessionServerCache;
import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.message.Message;
import com.csm.straining.common.socket.server.action.ActionSupport;
import com.csm.straining.common.socket.server.util.MessageUtil;
import com.csm.straining.repeater.client.MessageRepeaterClient;
import com.csm.straining.repeater.client.RepeaterCode;
import com.csm.straining.repeater.client.RepeaterMessage;
import com.lamfire.logger.Logger;
import com.lamfire.logger.LoggerFactory;
import com.lamfire.utils.JSON;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class UserChatAction extends ActionSupport{

	private static final Logger logger = LoggerFactory.getLogger(UserChatAction.class);
	
	@Override
	protected void execute() throws ActionException {
		
		logger.debug("UserChatAction begin");
		
		long senderID = getParamsLong("sid");
		long receiverID = getParamsLong("rid");
		String text = getParamsString("text");
		long createAt = getParamsLong("create_at");
		
		if (receiverID <= 0 || senderID <= 0 || senderID == receiverID) {
			logger.info("[UserChatAction] error receiverID : " + receiverID + " ,senderID : " + senderID);
			return;
		}
		
		if (StringUtils.isBlank(text)) {
			logger.info("[UserChatAction] error text is blank");
			return;
		}
		
		if (createAt <= 0) {
			logger.info("[UserChatAction] error createAt : " + createAt);
			return;
		}
		
		int serverID = SessionServerCache.getServerID(receiverID);
		if (serverID <= 0) {
			logger.info("[UserChatAction] receiverID " + receiverID + "hasn't logined");
			return;
		}
		
		RepeaterMessage repeaterMessage = new RepeaterMessage();
		repeaterMessage.setMsg(getRequestJson());
		repeaterMessage.setTargetClientID(serverID);
		repeaterMessage.setTargetID(receiverID);
		
		Message message = MessageUtil.getMessage(RepeaterCode.UserChatSendPID.REQUEST, JSON.toJsonString(repeaterMessage));
		MessageRepeaterClient.ins().send(message);
		
		logger.debug("UserChatAction finished");
	}
	
	

}
