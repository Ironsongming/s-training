package com.csm.straining.common.socket.server.action;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.Action;
import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.exception.SessionException;
import com.csm.straining.common.socket.netkit.message.Message;
import com.csm.straining.common.socket.server.util.MessageUtil;
import com.lamfire.utils.JSON;


/**
 * @author chensongming
 */
public abstract class ActionSupport extends Action {

	private static final Logger logger = LoggerFactory.getLogger(ActionSupport.class);
	
	private Map<String, Object> paramsMap = new HashMap<String, Object>();
	private String requestJsonStr = "{}";
	
	protected int version;
	
	@Override
	public void execute(Message message) throws ActionException {
		version = message.getVersion();
		byte[] content = message.getMessageContent();
		if (content != null && content.length > 0) {
			requestJsonStr = new String(content);
			paramsMap = JSON.toMap(requestJsonStr);
		}
		execute();
	}
	
	protected abstract void execute() throws ActionException;
	
	protected String getRequestJson() {
		return this.requestJsonStr;
	}
	
	protected Map<String, Object> getParamsMap() {
		return this.paramsMap;
	}

	protected Object getParams(String key) {
		return this.paramsMap.get(key);
	}
	
	protected String getParamsString(String key, String defaultVal) {
		Object val = this.paramsMap.get(key);
		return val instanceof String ? (String) val : defaultVal;
	}
	
	protected Integer getParamsInt(String key, Integer defaultVal) {
		Object val = this.paramsMap.get(key);
		
		return val instanceof String || val instanceof Integer ? (Integer) val : defaultVal;
	}
	
	protected Long getParamsLong(String key, Long defaultVal) {
		Object val = this.paramsMap.get(key);
		return val instanceof String || val instanceof Long ? (Long) val : defaultVal;
	}
	
	protected String getParamsString(String key) {
		return getParamsString(key, null);
	}
	
	protected Integer getParamsInt(String key) {
		return getParamsInt(key, null);
	}
	
	protected Long getParamsLong(String key) {
		return getParamsLong(key, null);
	}
	
	protected void sendMessage(int messageID) throws SessionException {
		Message message = MessageUtil.getMessage(messageID);
		getSession().sendMessage(message);
	}
	
	protected void sendMessage(int messageID, String json) throws SessionException {
		Message message = MessageUtil.getMessage(messageID, json);
		getSession().sendMessage(message);
	}
	
	
	
	
	
}
