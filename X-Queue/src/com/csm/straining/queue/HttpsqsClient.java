package com.csm.straining.queue;

import java.util.regex.Matcher;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;


/**
 * @author chensongming
 */
public class HttpsqsClient {
	
	private long pos;
	private Httpsqs4j httpsqs4j;
	private static CloseableHttpClient httpclient;
	
	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(1000);
		cm.setDefaultMaxPerRoute(100);
		httpclient = HttpClients.custom().setConnectionManager(cm).build();
	}
	
	protected HttpsqsClient(Httpsqs4j httpsqs4j) {
		this.httpsqs4j = httpsqs4j;
	}
	
	private String httpPost(String url, String data) throws HttpsqsException {
		HttpPost httpPost = new HttpPost(String.format("%s%s", httpsqs4j.prefix, url));
		httpPost.setEntity(new StringEntity(data, httpsqs4j.charset));
		// 设置请求和传输时间上限
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);
		return this.getSource(httpPost);
	}
	
	private String httpGet(String url) throws HttpsqsException {
		HttpGet httpGet = new HttpGet(String.format("%s%s", httpsqs4j.prefix, url));
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpGet.setConfig(requestConfig);
		return this.getSource(httpGet);
	}
	
	private String getSource(HttpUriRequest request) throws HttpsqsException {
		String result = null;
		try {
			CloseableHttpResponse response = httpclient.execute(request, new BasicHttpContext());
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, Consts.UTF_8);
				}
			} finally {
				response.close();
			}
		} catch (Throwable e) {
			throw new HttpsqsException("get source err", e);
		}
		
		if ("HTTPSQS_ERROR".equals(result)) {
			throw new HttpsqsException("Global error HTTPSQS_ERROR");
		}
		return result;
	}

	// 获取最后一次出入队列操作的位置值
	public long getLastPos() {
		return this.pos;
	}
	
	public HttpsqsStatus getStatus(String queueName) throws HttpsqsException {
		String url = "opt=status&name=" + queueName;
		String source = this.httpGet(url);
		Matcher matcher = HttpsqsStatus.pattern.matcher(source);
		
		if (matcher.find()) {
			HttpsqsStatus status = new HttpsqsStatus();
			status.version = matcher.group(1);
			status.queueName = matcher.group(2);
			status.maxNumber = Long.parseLong(matcher.group(3));
			status.getLap = Long.parseLong(matcher.group(4));
			status.getPosition = Long.parseLong(matcher.group(5));
			status.putLap = Long.parseLong(matcher.group(6));
			status.putPosition = Long.parseLong(matcher.group(7));
			status.unreadNumber = Long.parseLong(matcher.group(8));
			return status;
		}
		
		return null;
	}
	
	public void putString(String queueName, String str) throws HttpsqsException {
		String url = "opt=put&name=" + queueName;
		String source = this.httpPost(url, str);
		
		if (source.contains("HTTPSQS_PUT_END")) {
			throw new HttpsqsException("Queue [" + queueName + "] full");
		} else if (source.contains("HTTPSQS_PUT_ERROR")) {
			throw new HttpsqsException("Put data to queue [" + queueName + "] failed");
		}
	}
	
	public String getString(String queueName) throws HttpsqsException {
		String url = "opt=get&charset=" + httpsqs4j.charset + "&name=" + queueName;
		String source = this.httpGet(url);
		if (source.contains("HTTPSQS_GET_END")) {
			return null;
		}
		return source;
	}
	
	public String getStringAt(String queueName, long pos) throws HttpsqsException {
		if (pos < 1 || pos > 1000000000l) {
			throw new HttpsqsException("Pos out of range[1 - 1000000000].");
		}
		String url = "opt=view&charsetr=" + httpsqs4j.charset + "&name=" + queueName + "&pos=" + pos;
		return this.httpGet(url);
	}
	
	public boolean reset(String queueName) throws HttpsqsException {
		String url = "opt=reset&name=" + queueName;
		String source = this.httpGet(url);
		return source.contains("HTTPSQS_RESET_OK");
	}
	
	public boolean setMaxNumber(String queueName, long number) throws HttpsqsException {
		if (pos < 1 || pos > 1000000000l) {
			throw new HttpsqsException("Pos out of range[10 - 1000000000].");
		}
		String url = "opt=maxqueue&name=" + queueName + "&num=" + number;
		String source = this.httpGet(url);
		return source.contains("HTTPSQS_MAXQUEUE_OK");
	}	
	
}
