package com.csm.straining.common.socket.netkit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;


/**
 * @author chensongming
 */
public class SessionGroup {
	
	private final ConcurrentMap<Serializable, Session> sessionMap = new ConcurrentHashMap<Serializable, Session>();
	
	private final ChannelFutureListener remover = new ChannelFutureListener() {
		
		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			int sessionID = future.getChannel().getId().intValue();
			
		}
	};

	public synchronized boolean add(Serializable key, Session session) {
		Channel channel = session.getChannel();
		
		boolean isAdded = this.sessionMap.putIfAbsent(key, session) == null;
		if (isAdded) {
			channel.getCloseFuture().addListener(this.remover);
		}
		return isAdded;
 	}
	
	public synchronized void close() {
		for (Map.Entry<Serializable, Session> entry : this.sessionMap.entrySet()) {
			Session s = entry.getValue();
			s.close();
		}
		this.sessionMap.clear();
	}
	
	public boolean isEmpty() {
		return this.sessionMap.isEmpty();
	}
	
	public synchronized int remove(Session session) {
		if (session == null) {
			return 0;
		}
		List<Serializable> keys = findKeysBySessionID(session.getSessionID());
		int count = remove(keys);
		return count;
	}
	
	public synchronized int remove(Collection<Serializable> keys) {
		int count = 0;
		for (Serializable key : keys) {
			Session s = remove(key);
			if (s != null) {
				count ++;
			}
		}
		
		return count;
	}
	
	public synchronized Session remove(Serializable key) {
		Session s = this.sessionMap.remove(key);
		if (s == null) {
			return null;
		}
		
		s.getChannel().getCloseFuture().removeListener(this.remover);
		return s;
	}
	
	public List<Serializable> findKeysBySessionID(int sessionID) {
		List<Serializable> keys = new ArrayList<Serializable>();
		for (Map.Entry<Serializable, Session> entry : this.sessionMap.entrySet()) {
			Session session = entry.getValue();
			if (session.getSessionID() == sessionID) {
				keys.add(entry.getKey());
			}
		}
		
		return keys;
	}
	
	public Session get(Serializable key) {
		return this.sessionMap.get(key);
	}
	
	public int size() {
		return this.sessionMap.size();
	}
	
	public Collection<Session> sessions() {
		return this.sessionMap.values();
	}
	
	public Collection<Serializable> keys() {
		return this.sessionMap.keySet();
	}
}
