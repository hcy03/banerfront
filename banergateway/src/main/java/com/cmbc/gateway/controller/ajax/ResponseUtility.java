package com.cmbc.gateway.controller.ajax;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;

/**
 * 客户端结果集通用类
 * @author licd
 *
 */
public class ResponseUtility {
	
	/**
	 * 生成状态结果
	 * @param isSuccess
	 * @param error
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String buildResults(Boolean isSuccess, String error) {
		if (isSuccess) {
			Response r = new Response(0);
			r.setResponses(null);
			return JSONObject.toJSON(r).toString();
		} else {
			Response<Map<String, String>> r = new Response<Map<String, String>>(1);
			r.setResponses(new ImmutableMap.Builder<String, String>().put("info", error).build());
			return JSONObject.toJSON(r).toString();
		}
	}
	
	/**
	 * 生成单个值结果
	 * @param isSuccess
	 * @param error
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String buildResults(Boolean isSuccess, String error,String key,String value) {
		if (isSuccess) {
			Response r = new Response(0);
			r.setResponses(new ImmutableMap.Builder<String, String>().put(key, value).build());
			return JSONObject.toJSON(r).toString();
		} else {
			Response<Map<String, String>> r = new Response<Map<String, String>>(1);
			r.setResponses(new ImmutableMap.Builder<String, String>().put("info", error).build());
			return JSONObject.toJSON(r).toString();
		}
	}
	
	/**
	 * 生成结果集
	 * @param isSuccess
	 * @param error
	 * @param resultList
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String buildResults(Boolean isSuccess, String error, List<Map<String, Object>> resultList) {

		String result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			if (isSuccess) {
				Response r = new Response(0);
				r.setResponses(resultList);
				result = objectMapper.writeValueAsString(r);
			} else {
				Response<Map<String, String>> r = new Response<Map<String, String>>(1);
				r.setResponses(new ImmutableMap.Builder<String, String>().put("info", error).build());
				result = objectMapper.writeValueAsString(r);
			}
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		return result;
	}
	
	/**
	 * 生成多值详情结果
	 * @param isSuccess
	 * @param error
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String buildResults(Boolean isSuccess, String error, ImmutableMap<String, Object> resultMap) {
		
		String result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			if (isSuccess) {
				Response r = new Response(0);
				r.setResponses(resultMap);
				result = objectMapper.writeValueAsString(r);
			} else {
				Response<Map<String, String>> r = new Response<Map<String, String>>(1);
				r.setResponses(new ImmutableMap.Builder<String, String>().put("info", error).build());
				result = objectMapper.writeValueAsString(r);
			}
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		return result;
	}
	
	/**
	 * 生成多值详情结果
	 * @param isSuccess
	 * @param errorcode
	 * @param error
	 * @param resultMap
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String buildResults(Boolean isSuccess, Integer errorCode ,String error, ImmutableMap<String, Object> resultMap) {
		String result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			if (isSuccess) {
				Response r = new Response(0);
				r.setResponses(resultMap);
				result = objectMapper.writeValueAsString(r);
			} else {
				Response<Map<String, String>> r = new Response<Map<String, String>>(errorCode);
				r.setResponses(new ImmutableMap.Builder<String, String>().put("info", error).build());
				result = objectMapper.writeValueAsString(r);
			}
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		return result;
	}

}
