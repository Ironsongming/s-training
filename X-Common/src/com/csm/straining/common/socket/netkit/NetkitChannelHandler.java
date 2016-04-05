package com.csm.straining.common.socket.netkit;


import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.event.ChannelEvent;
import com.csm.straining.common.socket.netkit.event.NetkitChannelEventListener;
import com.csm.straining.common.socket.netkit.event.SendMsgCompleteListener;
import com.csm.straining.common.socket.netkit.message.Message;
import com.lamfire.utils.JSON;


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
		logger.info("channelClosed [" + e.getChannel().getId() + "]");
		if (channelEventListener != null) {
			ChannelEvent evt = new ChannelEvent(this.context, e.getChannel());
			this.channelEventListener.onClosed(evt);
		}
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelConnected(ctx, e);
		logger.info("channelConnected [" + e.getChannel().getId() + "]");
		logger.info("channelConnected [" + e.getChannel().toString() + "]");
		logger.info("channelConnected [" + e.getChannel().getRemoteAddress() + "]");
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
		logger.info("channelDisconnected [" + e.getChannel().getId() + "]");
		if (channelEventListener != null) {
			ChannelEvent evt = new ChannelEvent(this.context, e.getChannel());
			this.channelEventListener.onDisConnected(evt);
		}
	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		logger.info("channelOpen [" + e.getChannel().getId() + "]");
		logger.info("channelOpen [" + e.getChannel().toString() + "]");
		logger.info("channelOpen [" + e.getChannel().getRemoteAddress() + "]");

		if (channelEventListener != null) {
			ChannelEvent evt = new ChannelEvent(this.context, e.getChannel());
			this.channelEventListener.onOpen(evt);
		}
		
		this.session = this.context.createSession(e.getChannel());
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		logger.info("messageReceived [" + e.getChannel().getId() + "]");
		logger.info("messageReceived [" + e.getChannel().toString() + "]");
		logger.info("messageReceived [" + e.getChannel().getRemoteAddress() + "]");
		
		if (!(e.getMessage() instanceof Message)) {
			return;
		}
		
		Message msg = (Message) e.getMessage();
		logger.info(JSON.toJsonString(msg));
		
		try {
			if (this.channelEventListener != null) {
				ChannelEvent evt = new ChannelEvent(this.context, e.getChannel());
				this.channelEventListener.onMessageRecv(evt, msg);
			}
		} catch (Exception exception) {
			logger.warn("handler message receive event listener exception", exception);
		}
		
		logger.info("messageReceived [" + e.getChannel().getId() + "]");
		
		this.session.onRecvMessage(msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		
		if (this.channelEventListener != null) { 
			ChannelEvent event = new ChannelEvent(this.context, e.getChannel());
			this.channelEventListener.onExceptionCaught(event, e.getCause());
		}
		
		e.getFuture().cancel();
		Channel channel = e.getChannel();
		
		logger.warn("channel [" + channel.getId() + "] exception, " + e.getCause().getMessage());
		
	}
	
}
