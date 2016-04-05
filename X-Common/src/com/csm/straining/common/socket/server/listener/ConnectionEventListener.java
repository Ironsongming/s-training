package com.csm.straining.common.socket.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.event.ChannelEvent;
import com.csm.straining.common.socket.netkit.event.NetkitChannelEventListener;
import com.csm.straining.common.socket.netkit.message.Message;


/**
 * @author chensongming
 */
public class ConnectionEventListener implements NetkitChannelEventListener{
	
	private static final Logger logger = LoggerFactory.getLogger(ConnectionEventListener.class);

	@Override
	public void onOpen(ChannelEvent channelEvent) {
		logger.debug("onOpen [" + channelEvent.getChannel().getRemoteAddress().toString() + "] - " + channelEvent.getChannelID());
	}

	@Override
	public void onConnected(ChannelEvent channelEvent) {
		logger.debug("onConnected [" + channelEvent.getChannel().getRemoteAddress().toString() + "] - " + channelEvent.getChannelID());
	}

	@Override
	public void onDisConnected(ChannelEvent channelEvent) {
		logger.debug("onDisConnected [" + channelEvent.getChannel().getRemoteAddress().toString() + "] - " + channelEvent.getChannelID());
	}

	@Override
	public void onExceptionCaught(ChannelEvent channelEvent, Throwable throwable) {
		logger.debug("onExceptionCaught [" + channelEvent.getChannel().getRemoteAddress().toString() + "] - " + throwable.getMessage() + " - " + channelEvent.getChannelID());
	}

	@Override
	public void onClosed(ChannelEvent channelEvent) {
		logger.debug("onClosed [" + channelEvent.getChannel().getRemoteAddress().toString() + "] - " + channelEvent.getChannelID());
	}

	@Override
	public void onMessageRecv(ChannelEvent channelEvent, Message message) {
		byte[] content = message.getMessageContent();
		String str = content == null ? "" : new String(content);
		logger.debug("onMessageRecv [" + channelEvent.getChannel().getRemoteAddress().toString() + "] - " + channelEvent.getChannelID() + "-" + message.getMessageID() + "-" + str);
	}
	
	

}
