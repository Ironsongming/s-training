package com.csm.straining.common.socket.netkit;


import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.WriteCompletionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.event.ChannelEvent;
import com.csm.straining.common.socket.netkit.event.NetkitChannelEventListener;
import com.csm.straining.common.socket.netkit.event.SendMsgCompleteListener;
import com.csm.straining.common.socket.netkit.message.Message;


/**
 * @author chensongming
 */
public class NetkitChannelHandler extends SimpleChannelUpstreamHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(NetkitChannelHandler.class);
	private NetkitContext context;
	private NetkitChannelEventListener channelEventListener;
	private Session session;
	
	public NetkitChannelHandler(NetkitContext context) {
		this.context = context;
		this.channelEventListener = context.getChannelEventListener();
	}	

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelClosed(ctx, e);
		if (channelEventListener != null) {
			ChannelEvent evt = new ChannelEvent(this.context, e.getChannel());
			this.channelEventListener.onClosed(evt);
		}
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelConnected(ctx, e);
		
		if (channelEventListener != null) {
			ChannelEvent evt = new ChannelEvent(this.context, e.getChannel());
			this.channelEventListener.onConnected(evt);
		}
		
		int sessionSize = this.context.getSessionSize();
		if (sessionSize > this.context.getMaxConnections()) {
			Message message = new Message(0);
			
			// FIXME
			this.session.sendMessage(message).addFutureListener(new SendMsgCompleteListener() {
				
				@Override
				public void onComplete(Session session) {
					session.close();
				}
			});
			logger.warn("[WARNING] server connection reached max");
		}
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		super.channelDisconnected(ctx, e);
		if (channelEventListener != null) {
			ChannelEvent evt = new ChannelEvent(this.context, e.getChannel());
			this.channelEventListener.onDisConnected(evt);
		}
	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelOpen(ctx, e);
		if (channelEventListener != null) {
			ChannelEvent evt = new ChannelEvent(this.context, e.getChannel());
			this.channelEventListener.open(evt);
		}
		
		this.session = this.context.createSession(e.getChannel());
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		if (!(e.getMessage() instanceof Message)) {
			return;
		}
		
		Message msg = (Message) e.getMessage();
		try {
			if (this.channelEventListener != null) {
				ChannelEvent evt = new ChannelEvent(this.context, e.getChannel());
				this.channelEventListener.onMessageRecv(evt, msg);
			}
		} catch (Exception exception) {
			logger.warn("handler message receive event listener exception", exception);
		}
	}

	
}
