package com.csm.straining.socket.filter;

import com.csm.straining.common.socket.netkit.Action;
import com.csm.straining.common.socket.netkit.ActionChain;
import com.csm.straining.common.socket.netkit.ActionFilter;
import com.csm.straining.common.socket.netkit.Session;
import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.message.Message;
import com.csm.straining.socket.cons.Names;


/**
 * @author chensongming
 */
public class MessageFilter implements ActionFilter{

	
	@Override
	public void doFilter(Action action, Message message, ActionChain actionChain)
			throws ActionException {
		
		Session session = action.getSession();
		session.setAttribute(Names.Session.KEY_LAST_ACTIVE_TIME, System.currentTimeMillis());
		
		
		actionChain.doChain(action, message);
			
	}

}
