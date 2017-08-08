package com.cmbc.gateway.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.util.UrlPathHelper;

import com.cmbc.gateway.core.utils.PropertiesConfig;

public class CsrfDefense implements IHttpSecurity, InitializingBean {
	
	private String referUrl;
	private String ignoreUrl;
	
	private List<String> referUrlList = new ArrayList<String>();
	private List<String> ignoreUrlList = new ArrayList<String>();

	
	public void setReferUrl(String referUrl) {
		this.referUrl = referUrl;
	}


	public void setIgnoreUrl(String ignoreUrl) {
		this.ignoreUrl = ignoreUrl;
	}


	public boolean attackDef(HttpServletRequest request) {
		String lookupPath = new UrlPathHelper().getLookupPathForRequest(request);
		if(!ignoreUrlList.contains(lookupPath)){
			String ref = request.getHeader("Referer");
			if(StringUtils.isBlank(ref)){
				return false;
			}
			ref = ref.replaceAll("http://|https://", "");
			for(String refU:referUrlList){
				if(ref.startsWith(refU)){
					return true;
				}else{
					continue;
				}
			}
			return false;
		}else{
			return true;
		}
	}
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(!StringUtils.isBlank(referUrl)){
			if(referUrl.startsWith("P{") && referUrl.endsWith("}")){
				try{
					referUrl = PropertiesConfig.getPropValue(referUrl.substring(2,referUrl.length() - 1));
				}catch(Exception e){
				}
			}
		}
		if(referUrl!=null && referUrl.length()>0){
			String[] strArg = referUrl.split(",");
			for(String str:strArg){
				referUrlList.add(str);
			}
		}
	
		if(!StringUtils.isBlank(ignoreUrl)){
			if(ignoreUrl.startsWith("P{") && ignoreUrl.endsWith("}")){
				try{
					ignoreUrl = PropertiesConfig.getPropValue(ignoreUrl.substring(2,ignoreUrl.length() - 1));
				}catch(Exception e){
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
