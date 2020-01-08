package com.zhuodewen.www.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * HttpClient工具类
 * @author 86134
 *
 */
public class HttpClientUtil {
	
	/**
	 * 带参数的get请求
	 * @param url
	 * @param param
	 * @return String
	 */
	public static String doGet(String url, Map<String, String> param) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build(); 
			HttpGet httpGet = new HttpGet(uri);
			response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}
	
	/**
	 * 不带参数的get请求
	 * @param url
	 * @return String
	 */
	public static String doGet(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			URI uri = builder.build();
			HttpGet httpGet = new HttpGet(uri);
			response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}
 
	/**
	 * 带参数的post请求
	 * @param url
	 * @param param
	 * @return String
	 */
	public static String doPost(String url, Map<String, String> param) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}
 
	/**
	 * 不带参数的post请求
	 * @param url
	 * @return String
	 */
	public static String doPost(String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}
	
	/**
	 * 传送json类型的post请求,并返回JSON字符串
	 * @param url
	 * @param json
	 * @return String
	 */
	public static String doPostJson(String url, String json) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url.trim());
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			//httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");	//设置请求头编码,防止中文乱码(作用同上)
			//entity.setContentType("application/json");	//作用同上
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost);
			
			//====================解决重定向问题===========================
			int code = response.getStatusLine().getStatusCode();
	        String newUri="";
	        if (code == 302) {
	            Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
	            newUri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
	            httpPost = new HttpPost(newUri);
	            entity = new StringEntity(json);
	            httpPost.setEntity(entity);
	            response = httpClient.execute(httpPost);
	            code = response.getStatusLine().getStatusCode();
	        }
	      //====================解决重定向问题===========================
	        
			resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}
	
	/**
	 * 传送json类型的post请求,并返回JSON对象
	 * @param url
	 * @param json
	 * @return String
	 */
	public static JSONObject doPostJsonGetJson(String url, String json) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String resultString = "";
		CloseableHttpResponse response = null;
		JSONObject jsonResult = null;
		try {
			HttpPost httpPost = new HttpPost(url.trim());
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");	//设置发送的参数类型为JSON,并设置请求头编码(防止中文乱码)
			httpPost.setHeader("Accept", "application/json");						//设置接收的参数类型为JSON
			
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost);
			
			//====================解决重定向问题===========================
			int code = response.getStatusLine().getStatusCode();
	        String newUri="";
	        if (code == 302) {
	            Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
	            newUri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
	            httpPost = new HttpPost(newUri);
	            entity = new StringEntity(json);
	            httpPost.setEntity(entity);
	            response = httpClient.execute(httpPost);
	            code = response.getStatusLine().getStatusCode();
	        }
	      //====================解决重定向问题===========================
			resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			jsonResult= JSON.parseObject(resultString);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jsonResult;
	}
	
}
