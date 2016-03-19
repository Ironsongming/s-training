package com.csm.straining.common.configcenter;

import java.util.Properties;


/**
 * @author chensongming
 */
public interface ConfigLoadListener {
	
	void onConfigLoad(Properties props);

}
