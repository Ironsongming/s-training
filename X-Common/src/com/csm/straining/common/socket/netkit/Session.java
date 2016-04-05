package com.csm.straining.common.socket.netkit;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.Channels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.event.NetkitSessionListener;
import com.csm.straining.common.socket.netkit.exception.SessionException;
import com.csm.straining.common.socket.netkit.message.Message;


/**
 * @author chensongming
 */
public class Session implements MessageSendable{
	
	private static final Logger logger = LoggerFactory.getLogger(Session.class);
	private NetkitContext context;
	private Channel channel;
	private final Map<String, Object> attributeMap = new HashMap<String, Object>();
	private NetkitSessionListener onCreatedListener = null;
	private NetkitSessionListener onClosedListener = null;

	private long lastCommunicationTime = System.currentTimeMillis();
	private final MessageDispatcher dispatcher = new MessageDispatcher();
	
	Session(NetkitContext context, Channel channel) {
		this.context = context;
		this.channel = channel;
		ChannelFuture future = channel.getCloseFuture();
		future.addListener(new ChannelClosedListener());
	}
	
	public NetkitContext getContext() {
		return this.context;
	}
	
	public Channel getChannel() {
		return this.channel;
	}
	
	public void setAttribute(String key, Object val) {
		this.attributeMap.put(key, val);
	}
	
	public Object getAttribute(String key) {
		return attributeMap.get(key);
	}
 	
	public Set<String> getAttributeNames() {
		return this.attributeMap.keySet();
	}
	
	public Object removeAttribute(String key) {
		return this.attributeMap.remove(key);
	}
	
	public int getSessionID() {
		return this.channel.getId().intValue();
	}
	
	public void onSendMessage(Message message) {
		this.lastCommunicationTime = System.currentTimeMillis();
	}
	
	public void onRecvMessage(Message message) {
		this.lastCommunicationTime = System.currentTimeMillis();
		
		MessageTask task = createMessageTask(this.dispatcher, message);
		getContext().getWorkerExecutor().submit(task);
		
	}
	
	@Override
	public Future sendMessage(Message message) throws SessionException {
		onSendMessage(message);
		if (!this.channel.isConnected()) {
			throw new SessionException("channel was closed cannot send message");
		}
		
		ChannelFuture future = Channels.write(this.channel, message);
		return new Future(future, this);
	}
	
	
	public Future sendMessageAndClose(Message message) {
		ChannelFuture future = Channels.write(this.channel, message);
		future.addListener(ChannelFutureListener.CLOSE);
		return new Future(future, this);
	}
	
	public SocketAddress getRemoteAddress() {
		return this.channel.getRemoteAddress();
	}
	
	public SocketAddress getLocalAddress() {
		return this.channel.getLocalAddress();
	}
	
	public Future close() {
		ChannelFuture future = this.channel.close();
		return new Future(future, this);
	}
	
	public void setOnCreatedListener(NetkitSessionListener onCreatedListener) {
		this.onCreatedListener = onCreatedListener;
	}
	
	public NetkitSessionListener getOnCreatedListener() {
		return this.onCreatedListener;
	}
	
	public void setOnClosedListener(NetkitSessionListener onClosedListener) {
		this.onClosedListener = onClosedListener;
	}
	
	public NetkitSessionListener getOnClosedListener() {
		return this.onClosedListener;
	}
	
	public boolean isClosed() {
		return !this.channel.isOpen();
	}
	
	public boolean isConnected() {
		return this.channel.isConnected();
	}

	public boolean isSendable() {
		return this.channel.isWritable();
	}
	
	public long getLastCommunicationTime() {
		return this.lastCommunicationTime;
	}
	
	public MessageTask createMessageTask(MessageDispatcher dispatcher, Message message) {
		return new MessageTask(dispatcher, this, message);
	}
	
	protected void onCreated() {
		try {
			if (getOnCreatedListener() != null) {
				getOnCreatedListener().onEvent(this);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
	}
	
	protected void onClosed() {
		try {
			if (getOnClosedListener() != null) {
				getOnClosedListener().onEvent(this);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
		this.attributeMap.clear();
	}
	
	class ChannelClosedListener implements ChannelFutureListener {
		ChannelClosedListener() {

		}

		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			Session.this.close();
		}
	}
	
}
