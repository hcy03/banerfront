package com.cmbc.gateway.security;



public class SqlInjectionDefense extends AbstractHttpSecurity {
	
	protected String attack(String str){
		String newStr = str.replaceAll("/\\w*((\\%27)|(\\'))((\\%6F)|o|(\\%4F))((\\%72)|r|(\\%52))/ix","");
		newStr = newStr.replaceAll("/((\\%3D)|(=))[^\\n]*((\\%27)|(\')|(\\-\\-)|(\\%3B)|(;))/i","");
		newStr = newStr.replaceAll("/((\\%27)|(\'))union/ix","");
		newStr = newStr.replaceAll("insert|update|delete|having|drop|(\'|%27).(and|or).(\'|%27)|(\'|%27).%7C{0,2}|%7C{2}","");
		return newStr;
	}	
}
