package com.csm.straining.common.socket.netkit;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;


/**
 * @author chensongming
 */
public abstract class FutureListener implements ChannelFutureListener{
	
	private Session session;
	
	protected void setSession(Session session) {
		this.session = session;
	}
		
	@Override
	public void operationComplete(ChannelFuture arg0) throws Exception {
		onComplete(session);
	}

	public abstract void onComplete(Session session);
	
	
}
