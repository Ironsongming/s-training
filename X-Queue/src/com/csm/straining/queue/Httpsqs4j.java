package com.csm.straining.queue;


/**
 * @author chensongming
 */
public class Httpsqs4j {
	
	protected String prefix;
	
	protected String charset;
	
	protected boolean configured = false;
	
	
	public void setConnectionInfo(String ip, int port, String charset) {
		String prefix = "http://" + ip + ":" + port + "/?";
		this.prefix = prefix;
		this.charset = charset;
		this.configured = true;
	}
	
	public HttpsqsClient createHttpsqsClient() {
		return new HttpsqsClient(this);
	}

}
