package com.gwghk.agora.video.agoravideo.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 摘要：HttpClient 工具类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年9月13日
 */
public class HttpClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	private static String charset = "UTF-8";
	private static int CONNECT_TIMEOUT = 10000; // 设置连接超时时间10秒
	private static int SOCKET_TIMEOUT = 30000; // 设置数据传输超时时间30秒

	/**
	 * HttpClient的POST请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param params
	 *            请求的键值参数
	 * @param headers
	 *            头部参数
	 * @return 把地址返回的JSON转为Map
	 */
	public static String doPostWithMap(String url, Map<String, String> params, Map<String, String> headers) {
		return doPostWithMap(url, params, headers, charset);
	}

	/**
	 * HttpClient的POST请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param params
	 *            请求的键值参数
	 * @param headers
	 *            头部参数
	 * @param charset
	 *            要求返回时转换为某字符集
	 * @return 返回请求后的实体内容字符串
	 */
	public static String doPostWithMap(String url, Map<String, String> params, Map<String, String> headers,
			String charset) {
		logger.info("doPost->start,url:{},param:{},charset:{}", new Object[] { url, params, charset });
		HttpPost httpPost = new HttpPost(url);
		if (params != null && params.size() > 0) {
			HttpEntity httpEntity = buildHttpEntity(params);
			httpPost.setEntity(httpEntity);
		}
		return post(httpPost, headers, charset);
	}

	/**
	 * HttpClient的POST请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param JSON
	 *            请求的参数为JSON字符串
	 * @param headers
	 *            头部参数
	 * @return 把地址返回的JSON转为Map
	 */
	public static String doPostWithJson(String url, String JSON, Map<String, String> headers) {
		logger.info("doPost->start,url:{},param:{}", new Object[] { url, JSON });
		HttpPost httpPost = new HttpPost(url);
		if (StringUtil.isNotEmpty(JSON)) {
			StringEntity entity = new StringEntity(JSON, "utf-8");// 解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
		}
		return post(httpPost, headers, charset);
	}

	/**
	 * HttpClient的POST请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param JSON
	 *            请求的参数为JSON字符串
	 * @param headers
	 *            头部参数
	 * @param charset
	 *            要求返回时转换为某字符集
	 * @return 返回请求后的实体内容字符串
	 */
	public static String doPostWithJson(String url, String JSON, Map<String, String> headers, String charset) {
		logger.info("doPost->start,url:{},param:{},charset:{}", new Object[] { url, JSON, charset });
		HttpPost httpPost = new HttpPost(url);
		if (StringUtil.isNotEmpty(JSON)) {
			StringEntity entity = new StringEntity(JSON, "utf-8");// 解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
		}
		return post(httpPost, headers, charset);
	}

	/**
	 * HttpClient的POST请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param binary
	 *            请求的参数为字节数据,会以流传到url
	 * @param headers
	 *            头部参数
	 * @return 把地址返回的JSON转为Map
	 */
	public static String doPostWithByte(String url, byte[] binary, Map<String, String> headers) {
		return doPostWithByte(url, binary, headers, charset);
	}

	/**
	 * HttpClient的POST请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param binary
	 *            请求的参数为字节数据,会以流传到url
	 * @param headers
	 *            头部参数
	 * @param charset
	 *            要求返回时转换为某字符集
	 * @return 返回请求后的实体内容字符串
	 */
	public static String doPostWithByte(String url, byte[] binary, Map<String, String> headers, String charset) {
		logger.info("doPost->start,url:{},param:{},charset:{}", new Object[] { url, new String(binary), charset });
		HttpPost httpPost = new HttpPost(url);
		if (binary != null) {
			HttpEntity httpEntity = buildHttpEntity(binary);
			httpPost.setEntity(httpEntity);
		}
		return post(httpPost, headers, charset);
	}

	 

	/**
	 * HttpClient的GET请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param headers
	 *            头部参数
	 */
	public static String doGet(String url, Map<String, String> headers) {
		logger.info("doGet->start,url:{}", url);
		HttpGet httpGet = new HttpGet(url);
		return get(httpGet, headers, charset);
	}

	/**
	 * 根据键值参数,创建请求的实体
	 * 
	 * @param params
	 *            键值参数
	 * @return 请求的实体HttpEntity
	 */
	private static HttpEntity buildHttpEntity(Map<String, String> params) {
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			nvp.add(new BasicNameValuePair(key, params.get(key)));
		}
		try {
			return new UrlEncodedFormEntity(nvp, charset);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据字节数据,创建请求的实体
	 * 
	 * @param binary
	 *            字节数据
	 * @return 请求的实体HttpEntity
	 */
	private static HttpEntity buildHttpEntity(byte[] binary) {
		return EntityBuilder.create().setBinary(binary).build();
	}

	/**
	 * 执行POST
	 * 
	 * @param httpPost
	 *            HttpClient执行的httpPost
	 * @param headers
	 *            头部参数
	 * @param charset
	 *            编码
	 * @return Post请求的结果内容
	 */
	private static String post(HttpPost httpPost, Map<String, String> headers, String charset) {
		try {
			long start = System.currentTimeMillis();
			// 设置头部信息
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			// 设置连接超时时间和处理时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
					.setConnectTimeout(CONNECT_TIMEOUT).build();
			httpPost.setConfig(requestConfig);
			HttpResponse httpResponse = HttpClients.createDefault().execute(httpPost);
			logger.info("post->cost time : {}ms", System.currentTimeMillis() - start);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				logger.info("post->end,success！ url:{}", httpPost.getURI());
				HttpEntity httpEntity = httpResponse.getEntity();
				return EntityUtils.toString(httpEntity, StringUtil.isNotEmpty(charset) ? charset : charset);
			} else {
			    logger.error("post->end,fail！url:{},err code:{}", new Object[] { httpPost.getURI(), statusCode });
			    HttpEntity httpEntity = httpResponse.getEntity();
			    if(httpEntity!=null){
			        return EntityUtils.toString(httpEntity, StringUtil.isNotEmpty(charset) ? charset : charset);
			    }
                return null;
			}
		} catch (Exception e) {
			logger.error("post->end,err！url:{} , err:{}", new Object[] { httpPost.getURI(), e });
			return null;
		}
	}

	/**
	 * 执行get
	 * 
	 * @param httpGet
	 *            HttpClient执行的httpGet
	 * @param headers
	 *            头部参数
	 * @param charset
	 *            编码
	 * @return get请求的结果内容
	 */
	private static String get(HttpGet httpGet, Map<String, String> headers, String charset) {
		try {
			long start = System.currentTimeMillis();
			// 设置头部信息
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpGet.setHeader(entry.getKey(), entry.getValue());
				}
			}
			// 设置连接超时时间和处理时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
					.setConnectTimeout(CONNECT_TIMEOUT).build();
			httpGet.setConfig(requestConfig);
			HttpResponse httpResponse = HttpClients.createDefault().execute(httpGet);
			logger.info("get->cost time : {}ms", System.currentTimeMillis() - start);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				logger.info("get->end,success!");
				HttpEntity httpEntity = httpResponse.getEntity();
				return EntityUtils.toString(httpEntity, StringUtil.isNotEmpty(charset) ? charset : charset);
			} else {
				logger.error("get->end,fail！url:{},err code:{}", new Object[] { httpGet.getURI(), statusCode });
				return null;
			}
		} catch (Exception e) {
			logger.error("get->end,err！url:{} , err:{}", new Object[] { httpGet.getURI(), e });
			return null;
		}
	}
	
	public static void main(String[] args) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("a", "123");
		System.out.println(HttpClientUtil.doGet("https://www.baidu.com", null));
	}
}