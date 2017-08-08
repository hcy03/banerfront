package com.cmbc.gateway.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NotNullConstants {
	// 非空字段值的规则-context中字段的非空值
	public static final String[] GWCONTEXT = new String[] { "context" };
	public static final String[] CONTEXT = new String[] { "checkTokenFlag", "type", "channel" };
	public static final String[] GWRANDOM = new String[] { "type" }; // 随机数必要字段信息
	public static final String[] GWKEYAGREE = new String[] { "randomCipher" };// 密钥协商必要字段信息
	public static final String[] GWVERSION = new String[] { "type", "name" };
	// 非空字段的检测-请求字段检测
	public static final String[] FIELD_GWREQ = new String[] { "sessionId", "encryptType", "context" };
	public static final String[] FIELD_REQCONTEXT = new String[] { "head", "body" };

	// 关联非空字段-context|head|type
	public static final Map<String, List<String>> UNION_TYPE = new LinkedHashMap<String, List<String>>();
	static List<String> typeIndex = new ArrayList<String>();
	static List<String> typeLogin = new ArrayList<String>();
	static List<String> typeSms = new ArrayList<String>();
	static {
		typeIndex.add(Constants.OPER_LCBP_LOGIN);
		typeIndex.add(Constants.OPER_LCBP_SMS);
		UNION_TYPE.put("type", typeIndex);

		typeLogin.add("randomCode");
		typeLogin.add("randomTaskId");
		// typeLogin.add("imageCode");
		// typeLogin.add("imageTaskId");
		UNION_TYPE.put(Constants.OPER_LCBP_LOGIN, typeLogin);

		typeSms.add("imageCode");
		typeSms.add("imageTaskId");
		UNION_TYPE.put(Constants.OPER_LCBP_SMS, typeSms);
	}
	public static final Map<String, List<String>> UNION_TYPE_LCBP = new LinkedHashMap<String, List<String>>();
	static List<String> typeIndexL = new ArrayList<String>();
	static List<String> typeLoginCic = new ArrayList<String>();
	static {
		typeIndexL.add(Constants.OPER_LCBP_LOGINCIC);
		UNION_TYPE_LCBP.put("type", typeIndexL);

		typeLoginCic.add("randomCode");
		typeLoginCic.add("randomTaskId");
		typeLoginCic.add("imageCode");
		typeLoginCic.add("imageTaskId");
		UNION_TYPE_LCBP.put(Constants.OPER_LCBP_LOGINCIC, typeLoginCic);
	}

	public static final Map<String, List<String>> UNION_TOKEN = new LinkedHashMap<String, List<String>>();
	static List<String> tokenIndex = new ArrayList<String>();
	static List<String> tokenCheck = new ArrayList<String>();
	static {
		tokenIndex.add(Constants.CTF_CHECK);
		UNION_TOKEN.put("checkTokenFlag", tokenIndex);

		tokenCheck.add("token");
		tokenCheck.add("userId");
		UNION_TOKEN.put(Constants.CTF_CHECK, tokenCheck);
	}

	// 排除类型非空检测
	public static final Map<String, String> UNION_SESSIONID = new HashMap<String, String>();
	static {
		UNION_SESSIONID.put("encryptType", "0|sessionId");
	}
}
