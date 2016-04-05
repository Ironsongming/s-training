package com.csm.straining.common.socket.netkit;

import java.util.concurrent.TimeUnit;

import org.jboss.netty.channel.ChannelFuture;


/**
 * @author chensongming
 */
public class Future {
	
	private ChannelFuture channelFuture;
	private Session session;
	
	public Future(ChannelFuture channelFuture, Session session) {
		this.channelFuture = channelFuture;
		this.session = session;
	}

	public void addFutureListener(FutureListener listener) {
		this.channelFuture.addListener(listener);
	}
	
	public void removeFutureListener(FutureListener listener) {
		this.channelFuture.removeListener(listener);
	}
	
	public Session getSession() {
		return this.session;
	}
	
	public boolean isDone() {
		return this.channelFuture.isDone();
	}
	
	public boolean isCancelled() {
		return this.channelFuture.isCancelled();
	}
	
	public boolean isSuccess() {
		return this.channelFuture.isSuccess();
	}
	
	public Throwable getCause() {
		return this.channelFuture.getCause();
	}
	
	public boolean cancel() {
		return this.channelFuture.cancel();
	}
	
	public ChannelFuture await() throws InterruptedException {
		return this.channelFuture.await();
	}
	
	public ChannelFuture awaitUninterruptibly() {
		return this.channelFuture.awaitUninterruptibly();
	}
	
	public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
		return this.channelFuture.await(timeout, unit);
	}
	
	public boolean await(long timeoutMillis) throws InterruptedException {
		return this.channelFuture.await(timeoutMillis);
	}

	public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
		return this.channelFuture.awaitUninterruptibly(timeout, unit);
	}
	
	public boolean awaitUninterruptibly(long timeoutMillis) {
		return this.channelFuture.awaitUninterruptibly(timeoutMillis);
	}
	
	
	
}
