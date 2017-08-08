package com.cmbc.gateway.core.utils;

import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.cmbc.gateway.constants.GWExceptionConstants;
import com.cmbc.gateway.exception.GateWayException;

@Slf4j
@Component("jsonUtil")
public class JsonUtil {

	@Resource
	private MessageChangeUtil messageChangeUtil;

	@SuppressWarnings("unchecked")
	public Map<String, Object> getInfoMap(Object info) throws GateWayException {
		if (info instanceof Map) {
			return (Map<String, Object>) info;
		} else if (info instanceof String) {
			return messageChangeUtil.json2Object(info.toString(), Map.class);
		} else {
			String obj = messageChangeUtil.object2Json(info);
			return messageChangeUtil.json2Object(obj, Map.class);
		}
	}

	/**
	 * 获取多层json字符串的值
	 * 
	 * @param response
	 * @param key
	 * @return
	 * @throws GateWayException
	 */
	@SuppressWarnings("unchecked")
	public String getJsonKey(Object response, String... key) throws GateWayException {
		String result = null;
		Map<String, Object> temp = getInfoMap(response);
		int i = 0;
		try {
			for (; i < key.length - 1; i++) {
				temp = (Map<String, Object>) temp.get(key[i]);
			}
		} catch (NullPointerException e) {
			throw new GateWayException(GWExceptionConstants.GW0001_CODE, GWExceptionConstants.GW0001_DESC + "第[" + i
					+ 1 + "]层");
		}
		try {
			result = temp.get(key[key.length - 1]).toString();
		} catch (NullPointerException e) {
			throw new GateWayException(GWExceptionConstants.GW0001_CODE, GWExceptionConstants.GW0001_DESC + "第["
					+ key.length + "]层");
		}
		return result;
	}

	/**
	 * 将两个Json串拼成一个Json串，map内有重复的json2覆盖json1
	 * 
	 * @param json1
	 * @param json2
	 * @return
	 */
	// public static String mergeJson(String json1, String json2) {
	//
	// @SuppressWarnings("unchecked")
	// Map<String, Object> json1Map = (HashMap<String, Object>) JSON
	// .parseObject(json1, HashMap.class);
	// @SuppressWarnings("unchecked")
	// Map<String, Object> json2Map = (HashMap<String, Object>) JSON
	// .parseObject(json2, HashMap.class);
	// json1Map.putAll(json2Map);
	// return JSON.toJSONString(json1Map);
	//
	// }

	/**
	 * 将两个JSON格式的context 组装成一个JSON格式的context
	 * 
	 * @param serviceContext
	 *            数据库上下文
	 * @param fileContext
	 *            数据源文件传入上下文
	 * @return
	 */
	// public static String getNewContext(String serviceContext, String
	// fileContext) {
	//
	// Set<String> jsonKeys = null;
	// Map<String, Object> valueMap = new HashMap<String, Object>();
	// // 保证传入的为Json格式的字符串
	// JSONObject serviceJsonObj = (JSONObject) JSONObject
	// .parse(serviceContext);
	// JSONObject fileJsonObj = (JSONObject) JSONObject.parse(fileContext);
	// jsonKeys = serviceJsonObj.keySet();
	// for (Object key : jsonKeys) {
	// Object jsonValue = serviceJsonObj.get(key);
	// valueMap.put((String) key, jsonValue);
	// }
	// jsonKeys = fileJsonObj.keySet();
	// for (Object key : jsonKeys) {
	// Object jsonValue = fileJsonObj.get(key);
	// valueMap.put((String) key, jsonValue);
	// }
	// return JSON.toJSONString(valueMap);
	// }

	// Test
	// public static void main(String[] args) {
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("1", "user1");
	// map.put("2", "user2");
	// map.put("3", "user3");
	// map.put("4", "user4");
	// String str = JSON.toJSONString(map);
	//
	// // "{""1"":""user1""}"
	// Map<String, String> map2 = new HashMap<String, String>();
	// map2.put("5", "user5");
	// map2.put("6", "user6");
	// map2.put("7", "user7");
	// map2.put("8", "user8");
	// String str2 = JSON.toJSONString(map2);
	// log.inf(JsonUtil.getNewContext(str, str2));
	// }

}
