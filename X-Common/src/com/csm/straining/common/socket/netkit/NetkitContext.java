package com.csm.straining.common.socket.netkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.channel.Channel;

import com.csm.straining.common.socket.netkit.event.NetkitChannelEventListener;
import com.csm.straining.common.socket.netkit.event.NetkitSessionListener;


/**
 * @author chensongming
 */
public class NetkitContext {
	
	private static final Long DefaultSessionTimeout = 120000L;
	
	private final Map<Integer, Class<Action>> registedActions = new ConcurrentHashMap<Integer, Class<Action>>();
	private final List<ActionFilter> actionFilters = new ArrayList<ActionFilter>();
	private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();
	private final SessionGroup sessionGroup = new SessionGroup();
	private NetkitChannelEventListener channelEventListener = null;
	private NetkitSessionListener sessionListenerOnCreate = null;
	private NetkitSessionListener sessionListenerOnClose = null;
	
	private long sessionTimeout = 120000L;
	private int maxConnections = 10000;
	
	private ExecutorService bossExecutor;
	private ExecutorService workerExecutor;
	
	public void setBossExecutor(ExecutorService bossExecutor) {
		this.bossExecutor = bossExecutor;
	}
	
	public ExecutorService getBossExecutor() {
		if (this.bossExecutor == null) {
			this.bossExecutor = Executors.newCachedThreadPool(); 
		}
		return bossExecutor;
	}

	public void setWorkerExecutor(ExecutorService workerExecutor) {
		this.workerExecutor = workerExecutor;
	}
	
	public ExecutorService getWorkerExecutor() {
		if (this.workerExecutor == null) {
			this.workerExecutor = Executors.newCachedThreadPool();
		}
		return workerExecutor;
	}
	
	public long getSessionTimeout() {
		return sessionTimeout;
	}
	
	public void addActionFilter(ActionFilter filter) {
		if (filter == null) {
			throw new NullPointerException("action filter was null");
		}
		this.actionFilters.add(filter);
	}
	
	public int getSessionSize() {
		return this.sessionGroup.size();
	}
	
	public List<ActionFilter> getActionFilterList() {
		return Collections.unmodifiableList(this.actionFilters);
	}
	
	public void registerAction(int funtionID, Class<Action> clazz) {
		this.registedActions.put(funtionID, clazz);
	}
	
	public Class<Action> getRegisterAction(int funtionID) {
		return this.registedActions.get(funtionID);
	}
	
	public void setChannelEventListener(
			NetkitChannelEventListener channelEventListener) {
		this.channelEventListener = channelEventListener;
	}
	
	public NetkitChannelEventListener getChannelEventListener() {
		return channelEventListener;
	}
	
	public void setSessionListenerOnCreate(
			NetkitSessionListener sessionListenerOnCreate) {
		this.sessionListenerOnCreate = sessionListenerOnCreate;
	}
	
	public NetkitSessionListener getSessionListenerOnCreate() {
		return sessionListenerOnCreate;
	}
	
	public void setSessionListenerOnClose(
			NetkitSessionListener sessionListenerOnClose) {
		this.sessionListenerOnClose = sessionListenerOnClose;
	}
	
	public NetkitSessionListener getSessionListenerOnClose() {
		return sessionListenerOnClose;
	}
	
	public Session getSession(Channel channel) {
		return getSession(channel.getId().intValue());
	}
	
	public Session getSession(int sessionID) {
		return this.sessionGroup.get(sessionID);
	}
	
	protected Session createSession(Channel channel) {
		if (channel == null) {
			return null;
		}
		
		Session session = getSession(channel);
		if (session != null) {
			session.setOnCreatedListener(this.sessionListenerOnCreate);
			session.setOnClosedListener(this.sessionListenerOnClose);
			return session;
		}
		
		session = new Session(this, channel);
		this.sessionGroup.add(channel.getId().intValue(), session);
		return session;
	}
	
	protected Session createSession(Channel channel, NetkitSessionListener onCreatedListener, NetkitSessionListener onClosedListener) {
		this.sessionListenerOnCreate = onCreatedListener;
		this.sessionListenerOnClose = onClosedListener;
		return createSession(channel);
	}
	
	public Collection<Session> getAllSessions() {
		return this.sessionGroup.sessions();
	}
	
	public void release() {
		this.sessionGroup.close();
		this.bossExecutor.shutdownNow();
		this.workerExecutor.shutdownNow();
	}
	
	public int getMaxConnections() {
		return this.maxConnections;
	}
	
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}
	
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	public Object getAttribute(String key) {
		return this.attributes.get(key);
	}
	
	public void setAttribute(String key, Object value) {
		this.attributes.put(key, value);
	}
	
	
	
	

}
