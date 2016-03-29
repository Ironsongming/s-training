package com.csm.straining.common.socket.netkit;

import com.csm.straining.common.socket.netkit.exception.SessionException;
import com.csm.straining.common.socket.netkit.message.Message;


/**
 * @author chensongming
 */
public interface MessageSendable {
	
	Future sendMessage(Message message) throws SessionException;
	
}
