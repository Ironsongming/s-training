package com.csm.straining.common.socket.netkit;

import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.message.Message;


/**
 * @author chensongming
 */
public abstract class Action {
	
	private Session session;
	
	public abstract void execute(Message message) throws ActionException;
	
	public Session getSession() {
		return this.session;
	}
	
	protected void setSession(Session session) {
		this.session = session;
	}
	
}
