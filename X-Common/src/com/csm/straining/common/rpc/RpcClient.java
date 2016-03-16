package com.csm.straining.common.rpc;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;


/**
 * @author chensongming
 */
public abstract class RpcClient<T> {
	
	private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
	
	private ApplicationConfig application;
	private RegistryConfig registry;
	private MonitorConfig monitor;
	private ReferenceConfig<T> reference;
	
	public RpcClient() {
		init();
	}
	
	public void init() {
	
		// 当前应用配置
		initApplicationConfig();
				
		// 注册中心配置
		initRegistryConfig();

		// 监控
		initMonitorConfig();
		
		// 服务提供者暴露服务配置
		initReferenceConfig();
		
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

	private void initReferenceConfig() {
		reference = new ReferenceConfig<T>();
		reference.setApplication(application);
		reference.setRegistry(registry);
		reference.setMonitor(monitor);
		
		reference.setInterface(getInterfaceClass());
		reference.setVersion("1.0");
		reference.setTimeout(10000);
		reference.setRetries(0);
		
	}
	
	public T getReference() {
		return reference.get();
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

	
	
	
	
	
	
}
