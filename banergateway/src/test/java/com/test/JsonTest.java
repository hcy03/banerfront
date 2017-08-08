package com.test;

import lombok.extern.slf4j.Slf4j;

import com.cmbc.gateway.core.utils.FastJsonUtils;

@Slf4j
public class JsonTest {

	public static void main(String[] args) {

		//将json字符串转换成bean
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("'subscribe': '1',");
		sb.append("'openid': 'o6_bmjrPTlm6_2sgVt7hMZOPfL2M',");
		sb.append("'nickname': 'Band',");
		sb.append("'sex': '1',");
		sb.append("'language': 'zh_CN',");
		sb.append("'city': '广州',");
		sb.append("'province': '广东',");
		sb.append("'country': '中国',");
		sb.append("'headimgurl': 'http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0',");
		sb.append("'subscribe_time': '1382694957',");
		sb.append("'unionid': ' o6_bmasdasdsad6_2sgVt7hMZOPfL',");
		sb.append("'remark': '',");
		sb.append("'groupid': '0'");
		sb.append("}");

	}

}
