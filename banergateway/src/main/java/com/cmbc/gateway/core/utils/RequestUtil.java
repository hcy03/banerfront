package com.cmbc.gateway.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import com.cmbc.gateway.constants.Constants;
import com.cmbc.gateway.constants.GWExceptionConstants;
import com.cmbc.gateway.constants.NotNullConstants;
import com.cmbc.gateway.exception.GateWayException;

/**
 * 原始请求处理工具类,将请求参数转到request的属性当中
 * 
 * @author rm-xiahaobo
 * 
 */
@Slf4j
@Component
public class RequestUtil {
	
	private static String httpPropPath = "/gateway/conf/http.properties";

	@Resource
	private MessageChangeUtil messageChangeUtil;
	@Resource
	private JsonUtil jsonUtil;

	/**
	 * 读取请求参数，设置在请求对象属性当中
	 * 
	 * @param request
	 * @return
	 * @throws GateWayException
	 */
	@SuppressWarnings("unchecked")
	public HttpServletRequest requestParse(HttpServletRequest request) throws GateWayException {
		log.debug("Content-Type: " + request.getContentType());
		String reqStr = "";
		if (request.getContentType() != null) {
			if (request.getContentType().contains(Constants.REQ_TYPE_FORM)) {
				reqStr = getFormReqStr(request);
			} else if (request.getContentType().contains(Constants.REQ_TYPE_JSON)) {
				reqStr = getJsonReqStr(request);
			} else {
				throw new GateWayException(GWExceptionConstants.REQ0000_CODE, GWExceptionConstants.REQ0000_DESC + "请求ContentType不合法");
			}
		}
		Map<String, Object> reqMap = messageChangeUtil.json2Object(reqStr, Map.class);
		NullCheckUtil.checkNull(NotNullConstants.GWCONTEXT, reqMap);
		NullCheckUtil.checkKeyNull(NotNullConstants.FIELD_GWREQ, reqMap, "请求报文");
		NullCheckUtil.checkUnionNotNull(NotNullConstants.UNION_SESSIONID, reqMap);
		log.debug("开始封装请求参数");
		setRequestAttr(request, reqMap);
		encryptControll(request, reqMap);
		log.debug("封装请求参数完成");
		return request;
	}

	/**
	 * 请求的加解密处理
	 * 
	 * @param reqMap
	 *            请求参数Map
	 * @throws GateWayException
	 */
	private void encryptControll(HttpServletRequest request, Map<String, Object> reqMap) throws GateWayException {
		Map<String, Object> context = null;
		log.info("加密类型为[0|不加密]");
		context = jsonUtil.getInfoMap(reqMap.get("context"));
		request.removeAttribute("context");
		request.setAttribute("context", context);
	}

	/**
	 * 设置请求参数
	 * 
	 * @param request
	 * @param reqMap
	 */
	private void setRequestAttr(HttpServletRequest request, Map<String, Object> reqMap) {
		Iterator<String> it = reqMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			// log.info("key : " + key + ", val : " + reqMap.get(key)
			// + ", type : " + reqMap.get(key).getClass().getSimpleName());
			request.setAttribute(key, reqMap.get(key));
		}
	}

	@SuppressWarnings("unused")
	private String getContextString(Object context) throws GateWayException {
		if (context instanceof String) {
			return (String) context;
		} else {
			return messageChangeUtil.object2Json(context);
		}
	}

	/**
	 * 获取json类型的请求参数
	 * 
	 * @param request
	 * @return
	 * @throws GateWayException
	 */
	private String getJsonReqStr(HttpServletRequest request) throws GateWayException {
		log.info("获取json请求参数");
		byte[] b = new byte[1024 * 10];
		StringBuffer sb = new StringBuffer();
		ServletInputStream reader = null;
		try {
			reader = request.getInputStream();
			int i = -1;
			while ((i = reader.read(b)) != -1) {
				sb.append(new String(b, 0, i, "UTF-8"));
			}
		} catch (Exception e) {
			log.debug(ExceptionUtils.getFullStackTrace(e));
			throw new GateWayException(GWExceptionConstants.SYS0001_CODE, GWExceptionConstants.SYS0001_DESC + "获取Json请求数据失败");
		}
		log.info("json请求参数为：" + sb.toString());
		return sb.toString();
	}

	/**
	 * 获取form类型的请求参数
	 * 
	 * @param request
	 * @return
	 * @throws GateWayException
	 */
	private String getFormReqStr(HttpServletRequest request) throws GateWayException {
		log.info("获取form请求参数");
		String reqStr = request.getParameter(Constants.REQ_TYPE_FORM_PARAM);
		if (StringUtils.isBlank(reqStr)) {
			throw new GateWayException(GWExceptionConstants.REQ0010_CODE, StringUtils.replace(GWExceptionConstants.REQ0010_DESC, "{0}", "null"));
		}
		try {
			reqStr = URLDecoder.decode(reqStr, request.getCharacterEncoding());
		} catch (UnsupportedEncodingException e) {
			throw new GateWayException(GWExceptionConstants.REQ0003_CODE, GWExceptionConstants.REQ0003_DESC);
		}
		log.info("form请求参数为：" + reqStr);
		return reqStr;
	}
	

	// 解析后台请求网关的JSON数据
	public String getJsonReqStr2(HttpServletRequest request) throws GateWayException {
		log.info("Content-Type: " + request.getContentType());
		String reqStr = "";
		if (request.getContentType() != null) {
			if (request.getContentType().contains(Constants.REQ_TYPE_JSON)) {
				reqStr = getJsonReqStr(request);
			} else {
				throw new GateWayException(GWExceptionConstants.REQ0000_CODE, GWExceptionConstants.REQ0000_DESC + "请求ContentType不合法");
			}
		}
		return reqStr;
	}
	
	/**
	 * 拼接请求参数
	 * @param tranCode 接口标识
	 * @param body 请求业务参数
	 * @param channel 渠道类型
	 * @param serviceid 服务号ID
	 * @return
	 * @throws GateWayException
	 */
	public String buildRequestParams(String channel,String serviceid,String tranCode,Map<String,Object> body) throws GateWayException {
		
		String param = PropertiesUtil.readProperty(httpPropPath, "param");
		
		Map<String,Object> content = new HashMap<String,Object>();
		
		Map<String,Object> message = new HashMap<String, Object>();
		message.put("sign", "");
		
		String tranDateTime = AppserverUtils.getDate14();
		StringBuffer tranFlow = new StringBuffer();
		tranFlow.append("AAAAAAAAAAAA").append(AppserverUtils.getDate8()).append(AppserverUtils.getDateTime7());
		
		Map<String,Object> head = new HashMap<String, Object>();
		head.put("deviceId", "");
		head.put("isBreakOut", "");
		head.put("osType", "");
		head.put("osVersion", "");
		head.put("ip", "");
		head.put("appVersion", "");
		head.put("serviceid", serviceid);
		
		head.put("version", "1.0.0");
		head.put("channel", channel);
		head.put("merchantNo", "AAAAAAAAAAAA");
		head.put("tranDateTime", tranDateTime);
		head.put("tranFlow", tranFlow.toString());
		head.put("tranCode", tranCode);
		head.put("respCode", "");
		head.put("respMsg", "");
		
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("head", head);
		data.put("body", body);
		message.put("data", data);
		
		content.put("message", message);
		
		String reqJson = param + messageChangeUtil.object2Json(content);
		log.info("请求内管系统参数为==========" + reqJson);
		return reqJson;
	}

}