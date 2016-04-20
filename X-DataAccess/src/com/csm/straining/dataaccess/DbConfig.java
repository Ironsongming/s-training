package com.csm.straining.dataaccess;

import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.configcenter.ConfigLoadListener;
import com.csm.straining.common.configcenter.StoreConfigCore;
import com.lamfire.utils.JSON;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class DbConfig implements ConfigLoadListener{

	private static final Logger logger = LoggerFactory.getLogger(DbConfig.class);
	
	private static final String PACKAGE_ROOT = "com.csm.straining.dataaccess.mapper.";
	
	private DbConfig() {}

	private static DbConfig instance = new DbConfig();
	
	public static DbConfig ins() {
		return instance;
	}
	
	private Map<String, SqlSessionFactory> packageMap = new ConcurrentHashMap<String, SqlSessionFactory>();
	private Map<Class<?>, SqlSessionFactory> clsMap = new ConcurrentHashMap<Class<?>, SqlSessionFactory>();
	
	private static String[] dbs = new String[] {"test", "user", "article", "contact"};
	
	private Properties props = new Properties();
	private StoreConfigCore storeConfigCore = new StoreConfigCore();
	
	public void init() {
		DbConfig.ins().initT();
	}
	
	public void initT() {
		props = storeConfigCore.init(StoreConfigCore.MYSQL, 5);
		
		for (String db : dbs) {
			initDB(db, props);
		}
		
		storeConfigCore.setConfigLoadListener(this);
	}
	
	private void initDB(String db, Properties props) {
		try {
			Properties sp = new Properties();
			
			sp.setProperty("driver", getConfigDriver(db, props));
			sp.setProperty("url", getConfigUrl(db, props));
			sp.setProperty("username", getConfigUserName(db, props));
			sp.setProperty("password", getConfigPassword(db, props));
			
			String poolMaximumActiveConnections = getConfigPoolMaximumActiveConnections(db, props);
			if (StringUtils.isBlank(poolMaximumActiveConnections)) {
				poolMaximumActiveConnections = getConfigDefaultPoolMaximumActiveConnections(props);
			}
			String poolMaximumIdleConnections = getConfigPoolMaximumIdleConnections(db, props);
			if (StringUtils.isBlank(poolMaximumIdleConnections)) {
				poolMaximumIdleConnections = getConfigDefaultPoolMaximumIdleConnections(props);
			}
			String poolMaximumCheckoutTime = getConfigPoolMaximumCheckoutTime(db, props);
			if (StringUtils.isBlank(poolMaximumCheckoutTime)) {
				poolMaximumCheckoutTime = getConfigDefaultPoolMaximumCheckoutTime(props);
			}
			String poolTimeToWait = getConfigPoolTimeToWait(db, props);
			if (StringUtils.isBlank(poolTimeToWait)) {
				poolTimeToWait = getConfigDefaultPoolTimeToWait(props);
			} 
			
			// 同一时间最大连接数
			sp.setProperty("poolMaximumActiveConnections", poolMaximumActiveConnections);

			// 连接最大空闲数
			sp.setProperty("poolMaximumIdleConnections", poolMaximumIdleConnections);
			
			// 连接被每个任务占用的最大时间
			sp.setProperty("poolMaximumCheckoutTime", poolMaximumCheckoutTime);
			
			// 连接池中无可用连接，最大等待时间
			sp.setProperty("poolTimeToWait", poolTimeToWait);
			
			sp.setProperty("poolPingEnabled", "true");
			sp.setProperty("poolPingQuery", "SELECT 0 , '" + db + "'");
			sp.setProperty("poolPingConnectionsNotUsedFor", "7200000"); //2h
			
			DataSourceFactory dsf = new PooledDataSourceFactory();
			dsf.setProperties(sp);
			
			DataSource ds = dsf.getDataSource();
			TransactionFactory tf = new JdbcTransactionFactory();
			String envID = "development";
			Environment env = new Environment(envID, tf, ds);
			Configuration config = new Configuration(env);

			config.setDefaultStatementTimeout(2000);
			
			String packageName = PACKAGE_ROOT + db;
			
			config.addMappers(packageName);
			SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(config);
			SqlSessionFactory ossf = packageMap.put(packageName, ssf);
			
			// 打印信息
			StringBuilder sbLog = new StringBuilder();
			sbLog.append(String.format("\n[DbConfig] ------------------- %s -------------------- \n", db));
			sbLog.append("[DbConfig Environment] ").append(envID).append('\n');
			sbLog.append("[DbConfig poolMaximumActiveConnections] ").append(poolMaximumActiveConnections).append('\n');
			sbLog.append("[DbConfig poolMaximumIdleConnections] ").append(poolMaximumIdleConnections).append('\n');
			sbLog.append("[DbConfig poolMaximumCheckoutTime] ").append(poolMaximumCheckoutTime).append('\n');
			sbLog.append("[DbConfig poolTimeToWait] ").append(poolTimeToWait).append('\n');
			logger.info(sbLog.toString());
			
			// 如果有老连接，释放老连接
			forceClose(db, ossf);
		} catch (Exception e) {
			logger.error("[database init error] DB: " + db, e);
		}
		
	}
	
	private void forceClose(String db, SqlSessionFactory ssf) {
		if (ssf == null) {
			return;
		}
		
		long sleepTime = 1000L * 10;
		logger.info("[Dbconfig force_close_previous_connections] DB: " + db + ", try sleeping " + sleepTime + "ms");

		try {
			Thread.sleep(sleepTime); // 防止关闭的连接正在被使用，所以等待10s再释放
		} catch (InterruptedException e) {
			
		}
		
		logger.info("[Dbconfig force_close_previous_connections] DB: " + db + ", start ");
		
		DataSource ds = ssf.getConfiguration().getEnvironment().getDataSource();
		if (ds != null) {
			PooledDataSource pds = ((PooledDataSource) ds);
			pds.forceCloseAll();
		}
		logger.info("[Dbconfig force_close_previous_connections] DB: " + db + ", end ");
	}
	
	@Override
	public void onConfigLoad(Properties props) {
		for (String db : dbs) {
			boolean needReload = false;
			
			if (!getConfigDriver(db, props).equals(getConfigDriver(db, this.props)) 
					|| !getConfigUrl(db, props).equals(getConfigUrl(db, this.props)) 
					|| !getConfigUserName(db, props).equals(getConfigUserName(db, this.props)) 
					|| !getConfigPassword(db, props).equals(getConfigPassword(db, this.props))
					|| !getConfigPoolMaximumActiveConnections(db, props).equals(getConfigPoolMaximumActiveConnections(db, this.props)) 
					|| !getConfigPoolMaximumIdleConnections(db, props).equals(getConfigPoolMaximumIdleConnections(db, this.props)) 
					|| !getConfigPoolMaximumCheckoutTime(db, props).equals(getConfigPoolMaximumCheckoutTime(db, this.props))
					|| !getConfigPoolTimeToWait(db, props).equals(getConfigPoolTimeToWait(db, this.props)) 
					|| !getConfigDefaultPoolMaximumActiveConnections(props).equals(getConfigDefaultPoolMaximumActiveConnections(this.props)) 
					|| !getConfigDefaultPoolMaximumIdleConnections(props).equals(getConfigDefaultPoolMaximumIdleConnections(this.props))
					|| !getConfigDefaultPoolMaximumCheckoutTime(props).equals(getConfigDefaultPoolMaximumCheckoutTime(this.props)) 
					|| !getConfigDefaultPoolTimeToWait(props).equals(getConfigDefaultPoolTimeToWait(this.props))) {
				needReload = true;
			}

			if (needReload) {
				logger.info("[DbConfig server_config_need_refresh] mysql DB: " + db + ", start...");

				initDB(db, props);;
				clsMap.clear(); // 重要，否则仍然使用的老配置

				logger.info("[DbConfig server_config_need_refresh] mysql DB: " + db + ", finished.");
			} else {
				
			}
			
		}
		
		this.props = props;
	}
	
	private String getConfigDriver(String db, Properties props) {
		return StringUtils.defaultString(props.getProperty(String.format("%s.driver", db)));
	}
	
	private String getConfigUrl(String db, Properties props) {
		return StringUtils.defaultString(props.getProperty(String.format("%s.url", db)));
	}
	
	private String getConfigUserName(String db, Properties props) {
		return StringUtils.defaultString(props.getProperty(String.format("%s.username", db)));
	}
	
	private String getConfigPassword(String db, Properties props) {
		return StringUtils.defaultString(props.getProperty(String.format("%s.password", db)));
	}
	
	private String getConfigPoolMaximumActiveConnections(String db, Properties props) {
		return StringUtils.defaultString(props.getProperty(String.format("%s.poolMaximumActiveConnections", db)));
	}
	
	private String getConfigPoolMaximumIdleConnections(String db, Properties props) {
		return StringUtils.defaultString(props.getProperty(String.format("%s.poolMaximumIdleConnections", db)));
	}
	
	private String getConfigPoolMaximumCheckoutTime(String db, Properties props) {
		return StringUtils.defaultString(props.getProperty(String.format("%s.poolMaximumCheckoutTime", db)));
	}
	
	private String getConfigPoolTimeToWait(String db, Properties props) {
		return StringUtils.defaultString(props.getProperty(String.format("%s.poolTimeToWait", db)));
	}
	
	private String getConfigDefaultPoolMaximumActiveConnections(Properties props) {
		return StringUtils.defaultString(props.getProperty("poolMaximumActiveConnections"));
	}

	private String getConfigDefaultPoolMaximumIdleConnections(Properties props) {
		return StringUtils.defaultString(props.getProperty("poolMaximumIdleConnections"));
	}

	private String getConfigDefaultPoolMaximumCheckoutTime(Properties props) {
		return StringUtils.defaultString(props.getProperty("poolMaximumCheckoutTime"));
	}

	private String getConfigDefaultPoolTimeToWait(Properties props) {
		return StringUtils.defaultString(props.getProperty("poolTimeToWait"));
	}
	
	public <T> Dao<T> openSessionMasterT(Class<T> mapper, boolean isAutoCommit) throws SQLException {
		SqlSessionFactory ssf = clsMap.get(mapper);
		if (ssf == null) {
			String packageName = mapper.getPackage().getName();
			ssf = packageMap.get(packageName);
			clsMap.put(mapper, ssf);
		}
		
		Dao<T> wrapper = new Dao<T>();
		SqlSession ss = ssf.openSession(isAutoCommit);
		ss.getConnection().setReadOnly(false);
		wrapper.setMapper(ss.getMapper(mapper));
		wrapper.setSession(ss);
		return wrapper;
	}
	
	public static <T> Dao<T> openSessionMaster(Class<T> mapper) throws SQLException {
		return DbConfig.ins().openSessionMasterT(mapper, true);
	}
	
	public static <T> Dao<T> openSessionMaster(Class<T> mapper, boolean isAutoCommit) throws SQLException {
		return DbConfig.ins().openSessionMasterT(mapper, isAutoCommit);
	}
}
