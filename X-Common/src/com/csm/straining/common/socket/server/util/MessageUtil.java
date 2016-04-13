package com.csm.straining.common.socket.server.util;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.Session;
import com.csm.straining.common.socket.netkit.message.Message;
import com.lamfire.utils.JSON;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class MessageUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);
	
	public static Message getMessage(int messageID) {
		return getMessage(messageID, JSON.toJsonString(new HashMap<String, Object>()));
	}
	
	public static Message getMessage(int messageID, String json) {
		Message message = new Message(messageID);
		
		if (StringUtils.isBlank(json)) {
			json = "";
		}
		
		byte[] bs = json.getBytes();
		
		message.setMessageContent(bs);
		return message;
	}
	
	public static void sendMessageAndRmCloseListener(Session session, Message message) {
		session.setOnClosedListener(null);
		session.sendMessageAndClose(message);
	}
}
