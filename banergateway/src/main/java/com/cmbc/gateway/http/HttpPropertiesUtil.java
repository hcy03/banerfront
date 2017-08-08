package com.cmbc.gateway.http;

import org.springframework.stereotype.Component;

import com.cmbc.gateway.core.utils.PropertiesUtil;

/**
 * http 属性文件
 * 
 * @author licd
 * 
 */
@Component
public class HttpPropertiesUtil {

	private static String httpPropPath = "/gateway/conf/http.properties";

	private int maxTotal = Integer.parseInt(PropertiesUtil.readProperty(httpPropPath, "maxTotal"));
	private int maxPerRoute = Integer.parseInt(PropertiesUtil.readProperty(httpPropPath, "maxPerRoute"));
	private int socketTimeOut = Integer.parseInt(PropertiesUtil.readProperty(httpPropPath, "socketTimeOut"));
	private int connTimeOut = Integer.parseInt(PropertiesUtil.readProperty(httpPropPath, "connTimeOut"));
	private int socketTimeOutDownLoad = Integer.parseInt(PropertiesUtil.readProperty(httpPropPath, "socketTimeOutDownLoad"));
	private int connTimeOutDownLoad = Integer.parseInt(PropertiesUtil.readProperty(httpPropPath, "connTimeOutDownLoad"));
	private String ip = PropertiesUtil.readProperty(httpPropPath, "ip");
	private String protocol = PropertiesUtil.readProperty(httpPropPath, "protocol");
	private int port = Integer.parseInt(PropertiesUtil.readProperty(httpPropPath, "port"));
	private String project = PropertiesUtil.readProperty(httpPropPath, "project");
	private String path = "/";
	private String characterEncoding = PropertiesUtil.readProperty(httpPropPath, "characterEncoding");
	private String url = PropertiesUtil.readProperty(httpPropPath, "url");
	private String param = PropertiesUtil.readProperty(httpPropPath, "param");
	private String httpUrl = PropertiesUtil.readProperty(httpPropPath, "http_url");

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxPerRoute() {
		return maxPerRoute;
	}

	public void setMaxPerRoute(int maxPerRoute) {
		this.maxPerRoute = maxPerRoute;
	}

	public int getSocketTimeOut() {
		return socketTimeOut;
	}

	public void setSocketTimeOut(int socketTimeOut) {
		this.socketTimeOut = socketTimeOut;
	}

	public int getConnTimeOut() {
		return connTimeOut;
	}

	public void setConnTimeOut(int connTimeOut) {
		this.connTimeOut = connTimeOut;
	}

	public int getSocketTimeOutDownLoad() {
		return socketTimeOutDownLoad;
	}

	public void setSocketTimeOutDownLoad(int socketTimeOutDownLoad) {
		this.socketTimeOutDownLoad = socketTimeOutDownLoad;
	}

	public int getConnTimeOutDownLoad() {
		return connTimeOutDownLoad;
	}

	public void setConnTimeOutDownLoad(int connTimeOutDownLoad) {
		this.connTimeOutDownLoad = connTimeOutDownLoad;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

}
