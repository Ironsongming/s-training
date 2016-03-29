package com.csm.straining.common.socket.netkit;

import java.util.Iterator;

import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.message.Message;


/**
 * @author chensongming
 */
public class ActionChain {
	
	private Iterator<ActionFilter> it;
	
	public void setIterator(Iterator<ActionFilter> it) {
		this.it = it;
	}
	
	public void doChain(Action action, Message message) throws ActionException {
		if (this.it.hasNext()) {
			this.it.next().doFilter(action, message, this);
			return;
		}
		
		action.execute(message);
	}

}
