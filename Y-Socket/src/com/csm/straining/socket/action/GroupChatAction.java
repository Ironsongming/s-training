package com.csm.straining.socket.action;

import java.util.ArrayList;
import java.util.List;

import com.csm.strainging.cache.impl.session.SessionServerCache;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.message.Message;
import com.csm.straining.common.socket.server.action.ActionSupport;
import com.csm.straining.common.socket.server.util.MessageUtil;
import com.csm.straining.dataaccess.caps.contact.GroupUserCaps;
import com.csm.straining.dataaccess.entity.contact.GroupUser;
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
public class GroupChatAction extends ActionSupport{

	private static final Logger logger = LoggerFactory.getLogger(GroupChatAction.class);
	
	@Override
	protected void execute() throws ActionException {
		
		logger.debug("GroupChatAction begin");
		
		long senderID = getParamsLong("sid");
		long receiverGroupID = getParamsLong("rgid");
		String text = getParamsString("text");
		long createAt = getParamsLong("create_at");
		
		if (receiverGroupID <= 0 || senderID <= 0) {
			logger.info("[GroupChatAction] error receiverGroupID : " + receiverGroupID + " ,senderID : " + senderID);
			return;
		}
		
		if (StringUtils.isBlank(text)) {
			logger.info("[GroupChatAction] error text is blank");
			return;
		}
		
		if (createAt <= 0) {
			logger.info("[GroupChatAction] error createAt : " + createAt);
			return;
		}
		
		List<GroupUser> groupUsers = new ArrayList<GroupUser>();
		try {
			groupUsers = GroupUserCaps.getGroupUsersByGroupID(receiverGroupID);
		} catch (CoreException e) {
			logger.info("[GroupChatAction] error text is blank");
			throw new ActionException(e);
		}
		
		for (GroupUser groupUser : groupUsers) {
			if (groupUser.getStatus() != Status.GroupUser.NORMAL) {
				continue;
			}
			
			long receiverID = groupUser.getUserID();
			if (receiverID == senderID) {
				continue;
			}
			
			int serverID = SessionServerCache.getServerID(receiverID);
			if (serverID <= 0) {
				logger.info("[GroupChatAction] receiverID " + receiverID + "hasn't logined");
				continue;
			}
			
			RepeaterMessage repeaterMessage = new RepeaterMessage();
			repeaterMessage.setMsg(getRequestJson());
			repeaterMessage.setTargetClientID(serverID);
			repeaterMessage.setTargetID(receiverID);
			
			Message message = MessageUtil.getMessage(RepeaterCode.GroupChatSendPID.REQUEST, JSON.toJsonString(repeaterMessage));
			MessageRepeaterClient.ins().send(message);
			
			logger.debug("GroupChatAction item finished");
		}
		logger.debug("GroupChatAction finished");
	}
	
	

}
