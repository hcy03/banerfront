package com.cmbc.gateway.constants;

import java.util.ArrayList;
import java.util.List;

public class Constants {
	
	public static final String REQ_TYPE_FORM = "application/x-www-form-urlencoded";
	public static final String REQ_TYPE_MULFORM = "multipart/form-data";
	public static final String REQ_TYPE_JSON = "application/json";
	public static final String REQ_TYPE_FORM_PARAM = "envelop";
	public static final String UPLOAD_CONTEXT_KEY = "uploadContext";
	
	public static final String CHANNEL_H5 = "01";
	public static final String CHANNEL_APP = "02";

	// 渠道标识常量
	public static final List<String> channelList = new ArrayList<String>(); //
	static {
		channelList.add(CHANNEL_H5);
		channelList.add(CHANNEL_APP);
	}

	// 加密类型常量
	public static final String ENCRYPT_TYPE_NONE = "0"; // 不加密
	public static final String ENCRYPT_TYPE_AES = "1"; // AES加密
	public static final String ENCRYPT_TYPE_SM2 = "2";
	public static final String ENCRYPT_TYPE_SM4 = "3";

	// 请求接口类型常量
	public static final String OPER_GW_GETRANDOM = "GWRANDOM"; // 不需要做特殊处理的接口(查询)，包含发送不验图形验证码的发短信请求
	public static final String OPER_GW_KEYAGREE = "GWKEYAGREE"; // 不需要做特殊处理的接口(查询)，包含发送不验图形验证码的发短信请求
	public static final String OPER_COMMON = "COMMON"; // 不需要做特殊处理的接口(查询)。正常情况下必须验证token，无其他操作
	public static final String OPER_CREATECIPHER = "CREATECIPHER"; // 创建订单密文
	public static final String OPER_VERSION = "VERSION"; // PC版本
	public static final String OPER_LCBP_LOGIN = "LOGIN";
	public static final String OPER_LCBP_LOGINCIC = "LOGINCIC";
//	public static final String OPER_LCBP_REGISTER = "REGISTER";// 注册接口，暂不处理
	public static final String OPER_LCBP_SMS = "SMS";
	public static final String OPER_LCBP_SMSNCIC = "SMSNCIC";
	public static final String OPER_LCBP_CMS = "CMS";
	public static final String OPER_LCBP_LOGOUT = "LOGOUT"; // 登出接口，删除token，不去后台
	public static final String OPER_LCBP_DOWNLOAD = "DOWNLOAD"; // 登出接口，删除token，不去后台
	public static final String OPER_LCBP_MCTIN = "MCTIN"; // 登出接口，删除token，不去后台

	public static final List<String> typeList = new ArrayList<String>(); //
	static {
		typeList.add(OPER_GW_GETRANDOM);
		typeList.add(OPER_GW_KEYAGREE);
		typeList.add(OPER_COMMON);
		typeList.add(OPER_CREATECIPHER);
		typeList.add(OPER_VERSION);
		typeList.add(OPER_LCBP_LOGIN);
		typeList.add(OPER_LCBP_LOGINCIC);
//		typeList.add(OPER_LCBP_REGISTER);
		typeList.add(OPER_LCBP_SMS);
		typeList.add(OPER_LCBP_SMSNCIC);
		typeList.add(OPER_LCBP_CMS);
		typeList.add(OPER_LCBP_LOGOUT);
		typeList.add(OPER_LCBP_DOWNLOAD);
		typeList.add(OPER_LCBP_MCTIN);
	}
	
	public static final List<String> noCheckTokenInUse = new ArrayList<String>(); //
	static {
		noCheckTokenInUse.add(OPER_LCBP_SMS);
		noCheckTokenInUse.add(OPER_LCBP_SMSNCIC);
		noCheckTokenInUse.add(OPER_LCBP_CMS);
	}

	// checkTokenFlag 标记
	public static final String CTF_CHECK = "1";
	public static final String CTF_UNCHECK = "0";

	// token是否启用
	public static final String TOKEN_INUSE = "0";
	public static final String TOKEN_NOTUSE = "1";
	public static final String TOKEN_NOTUSE_LCBP = "2";

	// 响应结果
	public static final String RESPONSE_SUCCESS = "S";
	public static final String RESPONSE_SUCCESS_MSG = "网关处理请求成功";
	public static final String RESPONSE_ERROR = "E";
}
