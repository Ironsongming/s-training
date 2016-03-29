package com.csm.straining.common.socket.netkit;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.csm.straining.common.socket.netkit.event.HeartbeatListener;


/**
 * @author chensongming
 */
public class NetkitServer {
	
	private NetkitContext context;
	private String bindHost = "0.0.0.0";
	
	private int port = 8000;
	private int maxConnections = 10000;
	private ServerBootstrap bootstrap = null;
	private ChannelFactory channelFactory = null;
	private Channel listenerChannel = null;
	
	private int maxMessagePacketSize = 1048576;
	private ScheduledExecutorService heartbeatService = Executors.newScheduledThreadPool(1);
	
	public int getMaxMessagePacketSize() {
		return this.maxMessagePacketSize;
	}
	
	public void setMaxMessagePacketSize(int maxMessagePacketSize) {
		this.maxMessagePacketSize = maxMessagePacketSize;
	}
	
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}
	
	public NetkitServer(NetkitContext context) {
		this.context = context;
	}
	
	public NetkitServer(NetkitContext context, String bindHost, int port) {
		this.context = context;
		this.bindHost = bindHost;
		this.port = port;
	}
	
	public synchronized void startup() {
		ExecutorService bossExecutor = this.context.getBossExecutor();
		ExecutorService workerExecutor = this.context.getWorkerExecutor();
		
		this.channelFactory = new NioServerSocketChannelFactory(bossExecutor, workerExecutor);
		this.bootstrap = new ServerBootstrap(this.channelFactory);
		this.context.setMaxConnections(maxConnections);
		
		NetkitChannelPipelineFactory pipelineFactory = new NetkitChannelPipelineFactory(this.context);
		
		this.bootstrap.setPipelineFactory(pipelineFactory);
		this.bootstrap.setOption("child.tcpNoDelay", true);
		this.bootstrap.setOption("child.keepAlive", true);
		
		this.listenerChannel = this.bootstrap.bind(new InetSocketAddress(this.bindHost, this.port));
		this.heartbeatService.scheduleAtFixedRate(new HeartbeatListener(this.context), 30L, 30L, TimeUnit.SECONDS);
		
	}
	
	public void shutdown() {
		this.listenerChannel.close();
		this.context.release();
		this.channelFactory.releaseExternalResources();
		this.heartbeatService.shutdown();
		this.bootstrap.releaseExternalResources();
	}
	
	public NetkitContext getServerContext() {
		return this.context;
	}
	

}
