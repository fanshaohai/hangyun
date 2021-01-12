package org.kechuang.common.utils.pay;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {

	static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private static PoolingHttpClientConnectionManager connMgr;

	private static RequestConfig requestConfig;

	private final static int MAX_TIMEOUT = 70000;

	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
		connMgr.setValidateAfterInactivity(1000);
		RequestConfig.Builder configBuilder = RequestConfig.custom();

		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);

		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);

		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		requestConfig = configBuilder.build();
	}

	/**
	 * 发送 GET 请求（HTTP），不带输入数据
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url,new HashMap());
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doGet(String url, Map<String, Object> params) {
		String apiUrl = url;
		StringBuffer param = new StringBuffer();
		int i = 0;
		for (String key : params.keySet()) {
			if (i == 0){
				param.append("?");
			}else{
				param.append("&");
			}
			param.append(key).append("=").append(params.get(key));
			i++;
		}
		apiUrl += param;
		String result = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(apiUrl);
			httpGet.addHeader(HTTP.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");

			response = httpclient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity !=null) {
				result = EntityUtils.toString(entity,"UTF-8");
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 发送 POST 请求（HTTP），不带输入数据
	 * @param url
	 * @return
	 */
	public static String doPost(String url) {
		return doPost(url,new HashMap());
	}

	/**
	 * 发送 POST 请求（HTTP），带字符串
	 * @param url
	 * @param str
	 * @return
	 */
	public static String doPost(String url, String str) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
		try {
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new StringEntity(str, Charset.forName("UTF-8")));
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity !=null) {
				result = EntityUtils.toString(entity,"UTF-8");
			}
		}catch (IOException e) {
			logger.error("发送 POST请求失败，原因：{}", e.getMessage());
		}finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}


	/**
	 * 发送 POST 请求（HTTP），K-V形式
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, Map<String, Object> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");

		try {
			httpPost.setConfig(requestConfig);
			List pairList = new ArrayList<>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity,"UTF-8");
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式
	 * @param url
	 * @param json
	 * @return
	 */
	public static String doPost(String url, JSONObject json) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response =null;
		String result =null;
		HttpPost httpPost =new HttpPost(url);
		httpPost.addHeader(HTTP.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity =new StringEntity(json.toJSONString(),"UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity !=null) {
				result = EntityUtils.toString(entity,"UTF-8");
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (response !=null) {
				try {
					EntityUtils.consume(response.getEntity());
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
