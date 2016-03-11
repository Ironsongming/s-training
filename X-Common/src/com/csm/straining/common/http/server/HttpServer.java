package com.csm.straining.common.http.server;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.session.AbstractSessionManager;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author chensongming
 */
public abstract class HttpServer {

	private static Logger logger = LoggerFactory.getLogger(HttpServer.class);
	
	private Server httpServer;
	
	private Timer poolLogTimer;
	
	private String serverRootPath;
	
	private static final int THREADS_MAX = 500;
	private static final int THREADS_MIN = 100;
	private static final int THREADS_THRESHOLD = 50; // 当可用线程为此值时，定义为危险，拒绝访问
	
	public void setup(String serverRootPath) {
		this.serverRootPath = serverRootPath;
		
		//处理进程中抛出的一场
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				logger.debug("fatal exception in thread" + t, e);
				
				if (e instanceof OutOfMemoryError) {
					System.exit(100);
				}
			}
		});
	}
	
	protected abstract void setupServer() throws Exception;
	
	public void startServer() throws Exception {
		
		//当jvm关闭前执行的操作
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					stopServer();
				} catch (Exception e) {
					
				}
			}
		});
		
		if (httpServer != null) {
			logger.debug("http server starting up.......");
			
			httpServer.start();
			httpServer.join();
			
		}
		
	}
	
	public void stopServer() throws Exception{
		if (poolLogTimer != null) {
			poolLogTimer.cancel();
		} 
		
		if (httpServer != null) {
			httpServer.stop();
		}
		
	}
	
	protected void setupWebContext(WebAppContext webapp) {
		
	}
	
	protected void setupHttpServer(String resourceBase) {
		logger.debug("ResourceBase: " + resourceBase);
		
		String webContext = "/";
		
		httpServer = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		
		String listenAddr = getHttpListenAddress();
		if (listenAddr != null && listenAddr.length() > 0) {
			connector.setHost(listenAddr);
		}
		connector.setPort(getHttpListenPort());

		QueuedThreadPool pool = new QueuedThreadPool();
		pool.setMinThreads(THREADS_MIN);
		pool.setMaxThreads(THREADS_MAX);
		
		connector.setThreadPool(pool);
		
		httpServer.setConnectors(new Connector[] {connector});
		
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath(webContext);
		webapp.setResourceBase(resourceBase);
		
		setupWebContext(webapp);
		
		AbstractSessionManager sessionManager = getHttpSessionManager();
		
		if (sessionManager != null) {
			sessionManager.setSessionIdPathParameterName(null);
			webapp.setSessionHandler(new SessionHandler(sessionManager));
		}
		
		httpServer.setHandler(webapp);		
		
		String workerName = getHttpServerWorkerName();
		
		if (workerName != null) {
			HashSessionIdManager sessionIdManager = new HashSessionIdManager();
			sessionIdManager.setWorkerName(workerName);
			httpServer.setSessionIdManager(sessionIdManager);
		}
		
		initPoolLogTimer();
	}
	
	private void initPoolLogTimer() {
		poolLogTimer = new Timer();
		poolLogTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				QueuedThreadPool queuedThreadPool = getQueuedThreadPool();
				logger.info(String.format("[ThreadPool] threads: %d, idle: %d, left: %d  ", 
						queuedThreadPool.getThreads(), queuedThreadPool.getIdleThreads(), THREADS_MAX - queuedThreadPool.getThreads() + queuedThreadPool.getIdleThreads()));
			}
		}, 1000L * 10, 1000L * 10);
	}
	
	public QueuedThreadPool getQueuedThreadPool() {
		Connector[] connectors = httpServer.getConnectors();
		SelectChannelConnector connector = (SelectChannelConnector) connectors[0];
		return (QueuedThreadPool) connector.getThreadPool();
	}
	
	public File getFileInRoot(String subPath) {
		return new File(serverRootPath, subPath);
	}
	
	protected String getHttpListenAddress() {
		return "0.0.0.0";
	}

	protected int getHttpListenPort() {
		return 8080;
	}
	
	protected String getHttpServerWorkerName() {
		return null;
	}
	
	protected AbstractSessionManager getHttpSessionManager() {
		return new HashSessionManager();
	}
}
