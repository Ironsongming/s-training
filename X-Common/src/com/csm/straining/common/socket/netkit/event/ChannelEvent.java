package com.csm.straining.common.socket.netkit.event;

import java.util.EventObject;

import org.jboss.netty.channel.Channel;

import com.csm.straining.common.socket.netkit.NetkitContext;


/**
 * @author chensongming
 */
public class ChannelEvent extends EventObject{
	
	private static final long serialVersionUID = -3652742933410613096L;
	private NetkitContext context;
	private Channel channel;
	
	public ChannelEvent(NetkitContext context, Channel channel) {
		super(channel);
		this.context = context;
		this.channel = channel;
	}
	
	public Channel getChannel() {
		return this.channel;
	}
	
	public NetkitContext getContext() {
		return this.context;
	}
	
	public int getChannelID() {
		return this.channel.getId().intValue();
	}
}
