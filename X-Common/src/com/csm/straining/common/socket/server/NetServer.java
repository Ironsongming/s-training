package com.csm.straining.common.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.NetkitContext;
import com.csm.straining.common.socket.netkit.NetkitServer;
import com.csm.straining.common.socket.netkit.Session;


/**
 * @author chensongming
 */
public abstract class NetServer {
	
	private static final Logger logger = LoggerFactory.getLogger(NetServer.class);
	private NetkitServer netServer;
	
	public void setup() throws Exception{
		
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				logger.debug("fatal exception in thread" + t, e);
				
				if (e instanceof OutOfMemoryError) {
					System.exit(100);
				}
			}
		});
		
		setupServer();
		setupNetServer();
		
	}
	
	protected abstract void setupServer() throws Exception;
	
	protected void setupNetContext(NetkitContext context) {
		
	}
	
	private void setupNetServer() {
		NetkitContext context = new NetkitContext();
		netServer = new NetkitServer(context, getListenAddress(), getListenPort());
		
		logger.info("[net server bind] : " + getListenAddress() + ":" + getListenPort());
		
		setupNetContext(context);
	}
	
	protected String getListenAddress() {
		return "127.0.0.1";
	}
	
	protected int getListenPort() {
		return 7200;
	}
	
	protected Session getSession(int sessionID) {
		return netServer.getServerContext().getSession(sessionID);
	}
	
	public void startServer() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					stopServer();
				} catch (Exception e) {
				}
			}
		});
		
		logger.info("[net server starting up........]");
		netServer.startup();
	}
	
	public void stopServer() {
		logger.info("[net server shutting down]");
		if (netServer != null) {
			netServer.shutdown();
		}
	}
	
	

}
