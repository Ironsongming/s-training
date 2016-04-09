package com.csm.straining.common.util;

import java.net.InetAddress;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lamfire.code.UUIDGen;


/**
 * @author chensongming
 */
public class CommonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	/**
	 * 生成key
	 * 
	 * @param userID
	 * @return
	 */
	public static String getUUID(long userID) {
		try {
			UUID uuid = UUIDGen.getUUID(InetAddress.getLocalHost(), userID);
			return uuid.toString().replace("-", "");
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
		}
		return UUIDGen.uuidByTime();
	}

}
