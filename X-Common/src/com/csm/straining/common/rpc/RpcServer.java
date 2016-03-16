package com.csm.straining.common.rpc;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;


/**
 * @author chensongming
 */
public abstract class RpcServer<T> {
	
	private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
	
	private ApplicationConfig application;
	private RegistryConfig registry;
	private ProtocolConfig protocol;
	private MonitorConfig monitor;
	private ServiceConfig<T> service;
	
	private boolean canShutDown = false;
	
	public RpcServer() {
		init();
	}
	
	private void init() {
		
		// 拦截异常
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				logger.error("THREAD_TERMINATE " + t + "   " + e);
				e.printStackTrace();
			}
		});
		
		// 添加关闭钩子
		Shutdown.shareInstance().addListener(listener);
		
		// 当前应用配置
		initApplicationConfig();
		
		// 注册中心配置
		initRegistryConfig();
		
		// 监控
		initMonitorConfig();
		
		// 服务提供这协议配置
		initProtocolConfig();
		
		// 服务提供者暴露服务配置
		initServiceConfig();
	}
	
	private void initApplicationConfig() {
		application = new ApplicationConfig();
		application.setName(getApplicationName());
	}
	
	private void initRegistryConfig() {
		registry = new RegistryConfig();
		registry.setProtocol("zookeeper");
		registry.setAddress(getZookeeperAddr());
	}
	
	private void initMonitorConfig() {
		monitor = new MonitorConfig();
		monitor.setProtocol("registry");
	}
	
	private void initProtocolConfig() {
		protocol = new ProtocolConfig();
		protocol.setName("dubbo");
		protocol.setPort(getServicePort());
		String host = getServiceHost();
		
		if (host != null && host.equalsIgnoreCase("")) {
			protocol.setHost(host);
		}
		protocol.setThreads(getServiceThreadCount());
	}
	
	private void initServiceConfig() {
		service = new ServiceConfig<T>();
		service.setApplication(application);
		service.setRegistry(registry);
		service.setProtocol(protocol);
		service.setMonitor(monitor);
		service.setInterface(getInterfaceClass());
		service.setRef(getServiceImpl());
		service.setVersion("1.0");
		service.setTimeout(10000);
		service.setRetries(0); // 不做重新请求
	}
	
	@SuppressWarnings("unchecked")
	// 获取T的class
	private Class<T> getInterfaceClass() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return (Class<T>) params[0];
	}
	
	protected abstract String getApplicationName();
	 
	protected abstract String getZookeeperAddr();
	
	protected abstract T getServiceImpl();
	
	protected abstract int getServicePort();

	protected String getServiceHost() {
		return "";
	}
	
	protected int getServiceThreadCount() {
		return 3;
	}
	
	private ShutdownListener listener = new ShutdownListener() {
		
		@Override
		public void shutdown() {
			canShutDown = true;
		}
	};
	
	
	public void start() {
		service.export();
		
		while (true) {
			if (canShutDown) {
				//TODO 回收资源，备份得操作
				
				break;
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
