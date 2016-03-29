package com.csm.straining.common.socket.netkit.event;

import java.util.EventListener;

import com.csm.straining.common.socket.netkit.Session;
import com.csm.straining.common.socket.netkit.message.Message;


/**
 * @author chensongming
 */
public interface NetkitChannelEventListener extends EventListener {
	
	void open(ChannelEvent channelEvent);
	
	void onConnected(ChannelEvent channelEvent);
	
	void onDisConnected(ChannelEvent channelEvent);
	
	void onExceptionCaught(ChannelEvent channelEvent, Throwable throwable);
	
	void onClosed(ChannelEvent channelEvent);
	
	void onMessageRecv(ChannelEvent channelEvent, Message message);
}
