package com.csm.straining.common.socket.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.Session;
import com.csm.straining.common.socket.netkit.event.NetkitSessionListener;


/**
 * @author chensongming
 */
public class SessionClosedEventListener implements NetkitSessionListener {
	
	private static Logger logger = LoggerFactory.getLogger(SessionClosedEventListener.class);

	@Override
	public void onEvent(Session session) {
		logger.debug("Session-session[" + session.getSessionID() + "] closed - " + session.getRemoteAddress().toString());
	}

}
