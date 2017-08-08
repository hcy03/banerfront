package com.cmbc.gateway.security;



public class XssDefense extends AbstractHttpSecurity {
	
	protected String attack(String str){
		String newStr = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		newStr = newStr.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		newStr = newStr.replaceAll("'", "&#39;");
		newStr = newStr.replaceAll("eval\\((.*)\\)", "");
		newStr = newStr.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']","\"\"");
		newStr = newStr.replaceAll("script","");
		return newStr;
	}	

}
