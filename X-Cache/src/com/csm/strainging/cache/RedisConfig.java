package com.csm.strainging.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.management.InstanceAlreadyExistsException;
import javax.swing.JEditorPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.csm.straining.common.configcenter.ConfigLoadListener;
import com.csm.straining.common.configcenter.StoreConfigCore;
import com.lamfire.utils.NumberUtils;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class RedisConfig implements ConfigLoadListener {

	private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
	
	private static RedisConfig instance = new RedisConfig();
	
	private Properties props = new Properties();
	private StoreConfigCore storeConfigCore = new StoreConfigCore();
	private Map<String, RedisCache> redisPoolMap = new HashMap<String, RedisCache>();
	
	private RedisConfig() {
		
	}
	
	public static RedisConfig ins() {
		return instance;
	}
	
	public void init() {
		props = storeConfigCore.init(StoreConfigCore.REDIS, 5);
		
		Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Object, Object> e = it.next();
			String key = (String) e.getKey();
			
			if (key.endsWith(".server")) {
				String cache = key.substring(0, key.lastIndexOf('.'));
				System.out.println("cache is XXXXXXXXXX" + cache);
				initCache(cache, props);
			}
			
		}
		
		storeConfigCore.setConfigLoadListener(this);
		
	}
	
	private void initCache(String cache, Properties props) {
		String[] hosts = getServers(cache, props);
		
		JedisPoolConfig jpc = new JedisPoolConfig();
		jpc.setMaxTotal(getMaxActive(cache, props));
		jpc.setMinIdle(getMinIdle(cache, props));
		jpc.setMaxIdle(getMaxIdle(cache, props));
		jpc.setMaxWaitMillis(getMaxWait(cache, props));
		jpc.setMinEvictableIdleTimeMillis(getMinEvictableIdleTimeMillis(cache, props));
		jpc.setNumTestsPerEvictionRun(getNumTestsPerEvictionRun(cache, props));
		jpc.setSoftMinEvictableIdleTimeMillis(getSoftMinEvictableIdleTimeMillis(cache, props));
		jpc.setTimeBetweenEvictionRunsMillis(getTimeBetweenEvictionRunsMillis(cache, props));
		jpc.setBlockWhenExhausted(getWhenExhaustedAction(cache, props));
		
		int connectionTimeout = getConnectionTimeout(cache, props);
		
		// 主库
		String[] master = hosts[0].split(":");
		JedisPool masterIP = new JedisPool(jpc, master[0], Integer.parseInt(master[1]), connectionTimeout);
		
		// 从库
		List<JedisPool> slaveJPs = new ArrayList<JedisPool>();
		for (int i = 0; i < hosts.length; i++) {
			String[] slave = hosts[i].split(":");
			JedisPool slaveJP = new JedisPool(jpc, slave[0], Integer.parseInt(slave[1]), connectionTimeout);
			slaveJPs.add(slaveJP);
		}
		
		RedisPool rp = new RedisPool(cache);
		rp.setMaster(masterIP);
		rp.setSlaves(slaveJPs);

		redisPoolMap.put(cache, new RedisCache(rp));
	}
	
	private String[] getServers(String cache, Properties props) {
		String hosts = getServersStr(cache, props);
		if (StringUtils.isNotBlank(hosts)) {
			return hosts.split(",");
		}
		return new String[0];
	}
	
	private String getServersStr(String cache, Properties props) {
		return StringUtils.defaultString(props.getProperty(String.format("%s.server", cache)));
	}
	
	private int getMaxActive(String cache, Properties props) {
		String t = props.getProperty(String.format("%s.maxActive", cache));
		if (null == t) {
			t = props.getProperty("maxActive");
		}
		return NumberUtils.toInt(t, 16);
	}
	
	private int getMinIdle(String cache, Properties props) {
		String t = props.getProperty(String.format("%s.minIdle", cache));
		if (null == t) {
			t = props.getProperty("minIdle");
		}
		return NumberUtils.toInt(t, 4);
	}
	
	private int getMaxIdle(String cache, Properties props) {
		String t = props.getProperty(String.format("%s.maxIdle", cache));
		if (null == t) {
			t = props.getProperty("maxIdle");
		}
		return NumberUtils.toInt(t, 16);
	}
	
	private long getMaxWait(String cache, Properties props) {
		String t = props.getProperty(String.format("%s.maxWait", cache));
		if (null == t) {
			t = props.getProperty("maxWait");
		}
		return NumberUtils.toLong(t, 30000);
	}
	
	private long getMinEvictableIdleTimeMillis(String cache, Properties props) {
		String t = props.getProperty(String.format("%s.minEvictableIdleTimeMillis", cache));
		if (null == t) {
			t = props.getProperty("minEvictableIdleTimeMillis");
		}
		return NumberUtils.toLong(t, 60000);
	}
	
	private int getNumTestsPerEvictionRun(String cache, Properties props) {
		String t = props.getProperty(String.format("%s.numTestsPerEvictionRun", cache));
		if (null == t) {
			t = props.getProperty("numTestsPerEvictionRun");
		}
		return NumberUtils.toInt(t, -1);
	}
	
	private long getSoftMinEvictableIdleTimeMillis(String cache, Properties props) {
		String t = props.getProperty(String.format("%s.softMinEvictableIdleTimeMillis", cache));
		if (null == t) {
			t = props.getProperty("softMinEvictableIdleTimeMillis");
		}
		return NumberUtils.toLong(t, -1);
	}
	
	private long getTimeBetweenEvictionRunsMillis(String cache, Properties props) {
		String t = props.getProperty(String.format("%s.timeBetweenEvictionRunsMillis", cache));
		if (null == t) {
			t = props.getProperty("timeBetweenEvictionRunsMillis");
		}
		return NumberUtils.toLong(t, 30000);
	}
	
	private boolean getWhenExhaustedAction(String cache, Properties props) {
		String t = props.getProperty(String.format("%s.whenExhaustedAction", cache));
		if (null == t) {
			t = props.getProperty("whenExhaustedAction");
		}
		return "1".equals(t);
	}
	
	private int getConnectionTimeout(String cache, Properties props) {
		String t = props.getProperty(String.format("%s.connection.timeout", cache));
		if (null == t) {
			t = props.getProperty("connection.timeout");
		}
		return NumberUtils.toInt(t, 60000);
	}
	
	@Override
	public void onConfigLoad(Properties props) {
		
		Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Object, Object> e = it.next();
			String key = (String) e.getKey();	
			
			if (!key.endsWith(".server")) {
				continue;
			}

			String cache = key.substring(0, key.lastIndexOf("."));
			boolean needReload = false;
			
			
			if (!getServersStr(cache, props).equals(getServersStr(cache, this.props))
					|| getMaxActive(cache, props) != getMaxActive(cache, this.props) 
					|| getMinIdle(cache, props) != getMinIdle(cache, this.props)
					|| getMaxIdle(cache, props) != getMaxIdle(cache, this.props) 
					|| getMaxWait(cache, props) != getMaxWait(cache, this.props)
					|| getMinEvictableIdleTimeMillis(cache, props) != getMinEvictableIdleTimeMillis(cache, this.props) 
					|| getNumTestsPerEvictionRun(cache, props) != getNumTestsPerEvictionRun(cache, this.props)
					|| getSoftMinEvictableIdleTimeMillis(cache, props) != getSoftMinEvictableIdleTimeMillis(cache, this.props) 
					|| getTimeBetweenEvictionRunsMillis(cache, props) != getTimeBetweenEvictionRunsMillis(cache, this.props)
					|| getWhenExhaustedAction(cache, props) != getWhenExhaustedAction(cache, this.props) 
					|| getConnectionTimeout(cache, props) != getConnectionTimeout(cache, this.props)) {
				needReload = true;
			}
			
			if (needReload) {
				logger.info("[RedisConfig server_config_need_refresh] redis: " + cache + ", start...");
				
				initCache(cache, props);
				
				logger.info("[RedisConfig server_config_need_refresh] redis: " + cache + ", end...");
			}
			
		}
		
		this.props = props;
	}

	public RedisCache getRedisCache(String cache) {
		return redisPoolMap.get(cache);
	}
	
}
