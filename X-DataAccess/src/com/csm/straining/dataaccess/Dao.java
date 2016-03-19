package com.csm.straining.dataaccess;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author chensongming
 */
public class Dao<T> {
	
	private static final Logger logger = LoggerFactory.getLogger(Dao.class);
	private T mapper;
	
	private SqlSession session;
	
	public T mapper() {
		return mapper;
	}

	public void setMapper(T mapper) {
		this.mapper = mapper;
	}
	
	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	SqlSession getSession() {
		return session;
	}
	
	public void commit(boolean isForce) {
		session.commit(isForce);
	}
	
	public void commit() {
		session.commit();
	}
	
	public void rollback(boolean isForce) {
		session.rollback(isForce);
	}
	
	public void rollback() {
		session.rollback();
	}
	
	public void close() {
		session.close();
	}
}
