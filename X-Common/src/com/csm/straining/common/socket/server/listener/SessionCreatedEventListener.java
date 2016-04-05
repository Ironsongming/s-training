package com.csm.straining.common.socket.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.Session;
import com.csm.straining.common.socket.netkit.event.NetkitSessionListener;


/**
 * @author chensongming
 */
public class SessionCreatedEventListener implements NetkitSessionListener {
	
	private static Logger logger = LoggerFactory.getLogger(SessionCreatedEventListener.class);

	@Override
	public void onEvent(Session session) {
		logger.debug("Session-session[" + session.getSessionID() + "] created - " + session.getRemoteAddress().toString());
	}

}
