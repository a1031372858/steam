package com.xu.utility;

import com.alibaba.druid.util.StringUtils;
import com.xu.common.Constant;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public static String doGet(String url, Map<String, String> param) {

		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					if(!StringUtils.isEmpty(param.get(key))) {
						builder.addParameter(key, param.get(key));
					}
				}
			}

			URI uri = builder.build();
			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);

			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return resultString;
	}

	public static String doGet(String url) {
		return doGet(url, null);
	}

	public static String doPost(String url, Map<String, String> param) {
		return  doPost(url,param,null);
	}

	public static String doPost(String url, Map<String, String> param,Header[] headers) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> paramList = new ArrayList<>();
			for (String key : param.keySet()) {
				paramList.add(new BasicNameValuePair(key, param.get(key)));
			}
			// 模拟表单
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return resultString;
	}

	public static String doPost(String url) {
		return doPost(url, null);
	}
	
	public static String doPostJson(Map<String, Object> params, String json) {
		return  doPostJson(params,json,null);
	}

	public static String doPostJson(Map<String, Object> params, String json,Header[] headers) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(params.get("url")+ "");
			httpPost.setHeader("token", params.get("tokenName") + "");
//			httpPost.setHeaders(headers);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return resultString;
	}

	public static String doPostJson(String url, String json) {
		return  doPostJson(url,json,null);
	}
	public static String doPostJson(String url, String json,Header[] headers) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeaders(headers);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return resultString;
	}
	
	/**
	 * 
	 *@Description:附件上传
	 *@author:yingkangkang
	 *@date:2018年8月13日
	 *@param url
	 *@param bytes
	 *@param headers
	 *@return
	 */
	public static String doPostBytes(String url, byte[] bytes, Header[] headers) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeaders(headers);
            
            // 创建请求内容
            ByteArrayEntity  arrayEntity = new ByteArrayEntity(bytes);
            httpPost.setEntity(arrayEntity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        } finally {
            try {
                response.close();
            } catch (IOException e) {
            	logger.error(e.getMessage(), e);
            }
        }
        return resultString;
    }

	public static void main(String[] args) {
		Map<String, String> tokenInfo = new HashMap<>();
		tokenInfo.put("ticketCode", "5a475600-8337-4683-8616-491b6f063983");
		tokenInfo.put("hostIp", "222.64.123.168");
		try {
			String result = 
					doPost(Constant.DOMAIN+"/cert/check", tokenInfo);
			
			System.out.println(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
