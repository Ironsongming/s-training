package com.csm.straining.common.model.info.common;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.csm.straining.common.cons.Status;
import com.csm.straining.common.exception.AppException;
import com.lamfire.utils.PropertiesUtils;


/**
 * @author chensongming
 */
public class ErrorStatus extends ResponseStatus {
	
	private int code;
	private String msg;
	
	private static Map<String, String> prop;
	
	static {
		prop = PropertiesUtils.loadAsMap("error_code.properties", ErrorStatus.class);
	}
	
	public ErrorStatus(int code) {
		setStatus(Status.RespStatus.FAIL);
		
		this.code = code;
		this.msg = prop.get(String.valueOf(code));
		
		if (this.msg != null) {
			try {
				this.msg = new String(this.msg.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
	}
	
	public ErrorStatus(AppException e) {
		setStatus(Status.RespStatus.FAIL);
		
		this.code = e.getCode();
		
		switch (this.code) {
		case 0:
		case 104:
			this.code = 104;
			this.msg = e.getMsg();
			break;

		default:
			this.msg = prop.get(e);
			break;
		}
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	
	

}
