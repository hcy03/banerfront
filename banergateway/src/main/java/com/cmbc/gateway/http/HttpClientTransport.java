package com.cmbc.gateway.http;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.cmbc.gateway.constants.Constants;
import com.cmbc.gateway.constants.GWExceptionConstants;
import com.cmbc.gateway.core.utils.PropertiesUtil;
import com.cmbc.gateway.exception.GateWayException;

@Slf4j
public class HttpClientTransport extends HttpPropertiesUtil{
	private static String unhttpPropPath = "/gateway/conf/unhttp.properties";

	public String submit(String url, String reqStr) throws GateWayException {
		CloseableHttpClient httpClient = getHttpClient();
		HttpPost httppost = getHttpPost(url);
		CloseableHttpResponse response = null;
		String responseStr = "";
		try {
			StringEntity se = new StringEntity(reqStr, this.getCharacterEncoding());
			se.setContentType(Constants.REQ_TYPE_FORM);
			httppost.setEntity(se);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(this.getSocketTimeOut()).setConnectTimeout(this.getConnTimeOut()).build();// 设置请求和传输超时时间
			httppost.setConfig(requestConfig);
			log.info("开始请求后台，请求地址：" + httppost.getURI());
			log.info("请求数据：" + reqStr);
			response = httpClient.execute(httppost);
			responseCheck(response, httppost);
			HttpEntity entity = response.getEntity();
			responseStr = EntityUtils.toString(entity, this.getCharacterEncoding());
		} catch (GateWayException e) {
			throw e;
		} catch (HttpHostConnectException e) {
			throw new GateWayException(GWExceptionConstants.SYS0002_CODE, GWExceptionConstants.SYS0002_DESC + "Connection Refused");
		} catch (Exception e) {
			throw new GateWayException(GWExceptionConstants.SYS0002_CODE, GWExceptionConstants.SYS0002_DESC + e.getMessage());
		} finally {
			try {
				if (response != null)
					response.close();
				if (httpClient != null)
					httpClient.close();
			} catch (IOException e) {
				log.debug(ExceptionUtils.getFullStackTrace(e));
			}
		}
		log.info("收到响应，响应信息：" + responseStr);
		return responseStr;
	}

	public String submitTest(String url, String reqStr, String reqCode) throws GateWayException {
		CloseableHttpClient httpClient = getHttpClient();
		HttpPost httppost = getHttpPost(url);
		CloseableHttpResponse response = null;
		String responseStr = "";
		try {
			StringEntity se = new StringEntity(reqStr, this.getCharacterEncoding());
			se.setContentType(Constants.REQ_TYPE_JSON);
			httppost.setEntity(se);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(this.getSocketTimeOut()).setConnectTimeout(this.getConnTimeOut()).build();// 设置请求和传输超时时间
			httppost.setConfig(requestConfig);
			log.info("开始请求后台，请求地址：" + httppost.getURI());
			log.info("请求数据：" + reqStr);
			responseStr = PropertiesUtil.readProperty(unhttpPropPath, reqCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GateWayException(GWExceptionConstants.SYS0002_CODE, GWExceptionConstants.SYS0002_DESC + e.getMessage());
		} finally {
			try {
				if (response != null)
					response.close();
				if (httpClient != null)
					httpClient.close();
			} catch (IOException e) {
				log.debug(ExceptionUtils.getFullStackTrace(e));
			}
		}
		log.info("收到响应，响应信息：" + responseStr);
		return responseStr;
	}

	public HttpResponse submitForResponse(String url, String reqStr) throws GateWayException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = getHttpPost(url);
		int socketTimeOut = this.getSocketTimeOutDownLoad();
		int connTimeOut = this.getConnTimeOutDownLoad();
		HttpResponse response = null;
		try {
			StringEntity se = new StringEntity(reqStr, "utf-8");
			se.setContentType(Constants.REQ_TYPE_JSON);
			httppost.setEntity(se);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout(connTimeOut).build();// 设置请求和传输超时时间
			httppost.setConfig(requestConfig);
			log.info("开始请求后台，请求地址：" + httppost.getURI());
			log.info("请求数据：" + reqStr);
			response = httpClient.execute(httppost);
			responseCheck(response, httppost);
			return response;
		} catch (GateWayException e) {
			throw e;
		} catch (HttpHostConnectException e) {
			throw new GateWayException(GWExceptionConstants.SYS0002_CODE, GWExceptionConstants.SYS0002_DESC + "Connection Refused");
		} catch (Exception e) {
			throw new GateWayException(GWExceptionConstants.SYS0002_CODE, GWExceptionConstants.SYS0002_DESC + e.getMessage());
		}
	}

	private CloseableHttpClient getHttpClient() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(this.getMaxTotal());
		cm.setDefaultMaxPerRoute(this.getMaxPerRoute());
		HttpHost host = new HttpHost(this.getIp(), this.getPort());
		cm.setMaxPerRoute(new HttpRoute(host), this.getMaxPerRoute());
		HttpClientBuilder httpBulder = HttpClients.custom();
		httpBulder.setConnectionManager(cm);
		CloseableHttpClient httpClient = httpBulder.build();
		return httpClient;
	}

	private HttpPost getHttpPost(String url) {
//		StringBuffer sbf = new StringBuffer();
//		sbf.append(this.getProtocol()).append("://").append(this.getIp()).append(":").append(this.getPort()).append("/").append(this.getProject()).append(url.startsWith("/") ? "" : "/")
//				.append(url);
//		return new HttpPost(sbf.toString());
		StringBuffer sbf = new StringBuffer();
		sbf.append(this.getHttpUrl());
		return new HttpPost(sbf.toString());
	}

	private void responseCheck(HttpResponse response, HttpPost httppost) throws GateWayException {
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			log.info("请求后台响应失败：[" + statusCode + "]-[" + httppost.getURI() + "]");
			httppost.abort();
			throw new GateWayException(statusCode + "", GWExceptionConstants.HTTP_DESC);
		}
	}
}
