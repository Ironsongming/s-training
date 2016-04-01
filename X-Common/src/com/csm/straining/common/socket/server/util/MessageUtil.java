package com.csm.straining.common.socket.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.message.Message;
import com.lamfire.utils.JSON;


/**
 * @author chensongming
 */
public class MessageUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);
	
	public static Message getMessage(int messageID) {
		return getMessage(messageID, "");
	}
	
	public static Message getMessage(int messageID, String json) {
		Message message = new Message(messageID);
		
		byte[] bs = json.getBytes();
		
		message.setMessageContent(bs);
		return message;
	}
}
