package com.csm.straining.common.socket.netkit;

import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.message.Message;


/**
 * @author chensongming
 */
public interface ActionFilter {
	
	void doFilter(Action action, Message message, ActionChain actionChain) throws ActionException;
	
}
