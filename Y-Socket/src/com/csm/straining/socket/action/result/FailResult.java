package com.csm.straining.socket.action.result;

import java.util.HashMap;
import java.util.Map;

import com.lamfire.utils.JSON;


/**
 * @author chensongming
 */
public class FailResult {

	public Map<String, Object> res = new HashMap<String, Object>();
	
	public FailResult(String msg) {
		res.put("msg", msg);
	}
	
	public String toJson() {
		return JSON.toJsonString(res);
	}

}
