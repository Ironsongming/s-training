package com.csm.straining.common.socket.netkit;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.csm.straining.common.socket.netkit.exception.SessionException;


/**
 * @author chensongming
 */
public class NetkitClient {
	
	private String host;
	private int port;
	private NetkitContext context;
	private ClientBootstrap bootstrap;
	private ChannelFactory channelFactory;
	
	public NetkitClient() {
		
	}
	
	public NetkitClient(String host, int port, NetkitContext context) {
		
		this.host = host;
		this.port = port;
		this.context = context;
		
		this.channelFactory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		this.bootstrap = new ClientBootstrap(channelFactory);
		this.bootstrap.setPipelineFactory(new NetkitChannelPipelineFactory(context));
	}
	
	public Session createSession() throws SessionException {
		ChannelFuture future = this.bootstrap.connect(new InetSocketAddress(host, port));
		
		CreateSessionSuccessListener listener = new CreateSessionSuccessListener();
		future.addListener(listener);
		
		try {
			return listener.getSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public void close() {
		this.context.release();
		this.channelFactory.releaseExternalResources();
		this.bootstrap.releaseExternalResources();
	}

	class CreateSessionSuccessListener implements ChannelFutureListener {
		
		private Object lock = new Object();
		private Session session;

		CreateSessionSuccessListener() {
		}
		
		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			try {
				this.session = NetkitClient.this.context.createSession(future.getChannel(), null, null);
			} catch (Exception e) {
				
			}
			
			synchronized (this.lock) {
				lock.notifyAll();
			}
			
		}
		
		private void join() throws InterruptedException {
			synchronized (this.lock) {
				while (null == this.session) {
					this.lock.wait();
				}
			}
		}
		
		public Session getSession() throws InterruptedException {
			join();
			return this.session;
		}
		
		
	}
}
