package com.cmbc.gateway.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付编号对应类型枚举
 * 
 * @author licd
 *
 */
public enum PayNumberType{

	/** mkey开头编号前两位是商户收款码 **/
	FIRST_MERCHANT("CF","mkey开头编号前两位是商户收款码","") ,
	/** mkey开头编号前两位是自定义规则的收款码 **/
	FIRST_RULE("61","mkey开头编号前两位是自定义规则的收款码","") ,
	/** 收单码 **/
	RECEIVEORDER("05","收单码","") ,
	/** 卡券活动批次码,用来领取卡券 **/
	DELIVER("08","卡券活动批次码,用来领取卡券","1") ,
	/** 已领取卡券码,用来核销卡券 **/
	COUPONS("08","已领取卡券码,用来核销卡券","2") ,
	/** 订单二维码,用来支付订单 **/
	ORDER("12","订单二维码,用来支付订单","");

	private String code;
	private String desc;
	private String childCode;// 二维码子类型

	private PayNumberType(String code, String desc, String childCode) {
		this.code = code;
		this.desc = desc;
		this.childCode = childCode;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public String getChildCode() {
		return childCode;
	}

	public static Map<String, String> getDataMap() {
		Map<String, String> keyValueMap = new HashMap<String, String>();
		for (PayNumberType single : PayNumberType.values()) {
			keyValueMap.put(single.code, single.desc);
		}
		return keyValueMap;
	}

	public static String getDesc(String code) {
		return getDataMap().get(code);
	}
}
