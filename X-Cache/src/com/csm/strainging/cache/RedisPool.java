package com.csm.strainging.cache;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPool;


/**
 * @author chensongming
 */
public class RedisPool {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisPool.class);
	
	private String cache;
	private JedisPool master;
	private List<JedisPool> slaves;
	
	private final Random random = new Random();
	
	
	public RedisPool(String cache) {
		this.cache = cache;
	}
	
	public String getCacheName() {
		return cache;
	}
	
	public void setMaster(JedisPool master) {
		this.master = master;
	}
	
	public JedisPool getMaster() {
		return master;
	}
	
	public void setSlaves(List<JedisPool> slaves) {
		this.slaves = slaves;
	}
	
	public JedisPool getSlave() {
		
		if (slaves == null || slaves.isEmpty()) {
			return master;
		}
		
		return slaves.get(random.nextInt(slaves.size()));
	}

}
