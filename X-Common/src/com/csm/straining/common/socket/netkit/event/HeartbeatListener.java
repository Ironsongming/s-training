package com.csm.straining.common.socket.netkit.event;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.NetkitContext;
import com.csm.straining.common.socket.netkit.Session;


/**
 * @author chensongming
 */
public class HeartbeatListener implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(HeartbeatListener.class);
	private NetkitContext context;
	
	public HeartbeatListener(NetkitContext context) {
		this.context = context;
	}
	
	@Override
	public void run() {
		try {
			Collection<Session> sessions = this.context.getAllSessions();
			for (Session session : sessions) {
				long heartbeatTime = session.getLastCommunicationTime();
				long diffTime = System.currentTimeMillis() - heartbeatTime;
				if (diffTime > this.context.getSessionTimeout()) {
					logger.warn("the session [" +  session.getSessionID() + "] was timeout, close this channel [" + session.getRemoteAddress() + "]");
					session.close();
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}

}
