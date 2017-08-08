package com.cmbc.gateway.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.util.UrlPathHelper;

import com.cmbc.gateway.core.utils.PropertiesUtil;


public class GetMethodDefense implements IHttpSecurity,InitializingBean {
	private static final Log logger = LogFactory.getLog(GetMethodDefense.class);
	
	
	private String ignoreUrl;
	
	private List<String> ignoreUrlList = new ArrayList<String>();

	public void setIgnoreUrl(String ignoreUrl) {
		this.ignoreUrl = ignoreUrl;
	}


	@Override
	public boolean attackDef(HttpServletRequest request) {
		if("get".equalsIgnoreCase(request.getMethod())){
			String lookupPath = new UrlPathHelper().getLookupPathForRequest(request);
			if(!ignoreUrlList.contains(lookupPath)){
				logger.error("lookupPath="+lookupPath);
				return false;
			}
		}
		return true;
	}

	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		if(!StringUtils.isBlank(ignoreUrl)){
			if(ignoreUrl.startsWith("P{") && ignoreUrl.endsWith("}")){
				try{
					String configPath = System.getProperty("schome_pe_config");
					if (configPath == null || configPath.length() <= 0) {
						configPath = "/META-INF/config/config.properties";
					}
					ignoreUrl = PropertiesUtil.readProperty(configPath, ignoreUrl.substring(2,ignoreUrl.length() - 1));
				}catch(Exception e){
					logger.error("param[ingoreUrl] error,value=[" + ignoreUrl + "]");
				}
			}
		}
		if(ignoreUrl!=null && ignoreUrl.length()>0){
			String[] strArg = ignoreUrl.split(",");
			for(String str:strArg){
				ignoreUrlList.add(str);
			}
		}
	}

}
