package com.cmbc.gateway.core.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import com.cmbc.gateway.constants.Constants;
import com.cmbc.gateway.exception.GateWayException;

@Slf4j
@Component
public class ResponseUtil {
	
	private static String httpPropPath = "/gateway/conf/http.properties";

	@Resource
	public MessageChangeUtil messageChangeUtil;

	/**
	 * 错误信息响应
	 * 
	 * @param ge
	 *            错误信息
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	public void responseError(GateWayException ge, HttpServletRequest request, HttpServletResponse response) {
		log.info("网关请求完成, 开始响应错误信息");
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("resCode", ge.getCode());
		respMap.put("resMessage", ge.getMessage());
		respMap.put("context", "");
		setResponseCommInfo(respMap, request);
		String responseStr = null;
		try {
			responseStr = messageChangeUtil.object2Json(respMap);
		} catch (GateWayException e) {
			log.debug("响应时异常：" + ExceptionUtils.getFullStackTrace(e));
			responseStr = "响应时异常：" + e.getCode() + ": " + e.getMessage();
		}
		doResponse(responseStr, request, response);
	}

	public void responseSuccess(HttpServletRequest request, HttpServletResponse response, Object context) {
		log.debug("网关请求完成, 开始响应成功信息");
		String encryptType = (String) request.getAttribute("encryptType");
		Map<String, Object> respMap = new HashMap<String, Object>();
		setResponseCommInfo(respMap, request);
		respMap.put("resCode", Constants.RESPONSE_SUCCESS);
		respMap.put("resMessage", Constants.RESPONSE_SUCCESS_MSG);
		if (null == encryptType || Constants.ENCRYPT_TYPE_NONE.equals(encryptType)) {
			respMap.put("context", context);
			try {
				doResponse(messageChangeUtil.object2Json(respMap), request, response);
			} catch (GateWayException e) {
				responseError(e, request, response);
			}
		} else {
			responseCipher(encryptType, context, request, response, respMap);
		}
	}

	/**
	 * 做密文响应
	 * 
	 * @param encryptType
	 *            加密类型
	 * @param context
	 *            明文
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @param respMap
	 *            响应信息
	 */
	private void responseCipher(String encryptType, Object context, HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> respMap) {
		respMap.put("context", context);
		try {
			doResponse(messageChangeUtil.object2Json(respMap), request, response);
		} catch (GateWayException e) {
			responseError(e, request, response);
		}
	}

	/**
	 * 执行响应
	 * 
	 * @param responseJson
	 * @param request
	 * @param response
	 */
	private void doResponse(String responseJson, HttpServletRequest request, HttpServletResponse response) {
		log.info("响应信息: " + responseJson);
		PrintWriter out = null;
		try {

			out = response.getWriter();
			out.write(responseJson);
			out.flush();
			out.close();
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>响应完成>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		} catch (IOException e) {
			log.debug(ExceptionUtils.getFullStackTrace(e));
		}
	}

	private void setResponseCommInfo(Map<String, Object> respMap, HttpServletRequest request) {
		respMap.put("encryptType", request.getAttribute("encryptType"));
		respMap.put("sessionId", request.getAttribute("sessionId"));
	}
	
	/******************************************扫码支付下单响应*********************************************/
	/**
	 * 下单错误信息响应
	 * 
	 * @param ge
	 *            错误信息
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	public void responsePayError(GateWayException ge, HttpServletRequest request, HttpServletResponse response) {
		log.info("网关请求完成, 开始响应错误信息");
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("resCode", ge.getCode());
		respMap.put("resMessage", ge.getMessage());
		String responseStr = null;
		try {
			responseStr = messageChangeUtil.object2Json(respMap);
		} catch (GateWayException e) {
			log.debug("响应时异常：" + ExceptionUtils.getFullStackTrace(e));
			responseStr = "响应时异常：" + e.getCode() + ": " + e.getMessage();
		}
		doPayResponse(responseStr, request, response);
	}

	/**
	 * 下单成功信息响应
	 * @param request
	 * @param response
	 * @param data
	 *        支付要素信息
	 */
	public void responsePaySuccess(HttpServletRequest request, HttpServletResponse response, Map<String,Object> data) {
		log.info("网关请求完成, 开始响应成功信息");
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("resCode", Constants.RESPONSE_SUCCESS);
		respMap.put("resMessage", Constants.RESPONSE_SUCCESS_MSG);
		respMap.put("data", data);
		try {
			doPayResponse(messageChangeUtil.object2Json(respMap), request, response);
		} catch (GateWayException e) {
			responseError(e, request, response);
		}
	}
	
	/**
	 * 执行下单响应
	 * 
	 * @param responseJson
	 * @param request
	 * @param response
	 */
	private void doPayResponse(String responseJson, HttpServletRequest request, HttpServletResponse response) {
		log.info("响应信息: " + responseJson);
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.write(responseJson);
			out.flush();
			out.close();
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>响应完成>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		} catch (IOException e) {
			log.debug(ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	public void responseNoticeSuccess(HttpServletRequest request, HttpServletResponse response, Map<String,Object> body,String tranCode,String channel,String serviceid) {
		log.info("网关请求完成, 开始响应错误信息");
		Map<String, Object> respMap = createResponseMessage(Constants.RESPONSE_SUCCESS,tranCode,"",body,channel,serviceid);
		try {
			String message = messageChangeUtil.object2Json(respMap);
			String param = PropertiesUtil.readProperty(httpPropPath, "param")+message;
			doResponse(param, request, response);
		} catch (GateWayException e) {
			responseError(e, request, response);
		}
	}
	
	public void responseNoticeError(GateWayException ge, HttpServletRequest request, HttpServletResponse response, String tranCode,String channel,String serviceid) {
		log.info("网关请求完成, 开始响应失败信息");
		Map<String, Object> respMap = createResponseMessage(ge.getCode(),tranCode,ge.getMessage(),null,channel,serviceid);
		try {
			String message = messageChangeUtil.object2Json(respMap);
			String param = PropertiesUtil.readProperty(httpPropPath, "param")+message;
			doResponse(param, request, response);
		} catch (GateWayException e) {
			responseError(e, request, response);
		}
	}
	
	
	/**
	 * 创建通知
	 * @param respCode
	 * @param tranCode
	 * @param respMsg
	 * @param body
	 * @param channel  渠道类型
	 * @param serviceid 服务号ID
	 * @return
	 */
	public Map<String,Object> createResponseMessage(String respCode,String tranCode,String respMsg,Map<String, Object> body,String channel,String serviceid){
		
		Map<String,Object> content = new HashMap<String, Object>();
		Map<String,Object> message = new HashMap<String, Object>();
		Map<String,Object> head = createResponseHead(respCode,tranCode,respMsg,channel,serviceid);
		
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("head", head);
		if(body == null){
			data.put("body", new HashMap<String, Object>());
		}else{
			data.put("body", body);
		}
		message.put("sign", "");
		message.put("data", data);
		content.put("message", message);
		
		return content;
	}

	
	/**
	 * 返回响应报文
	 * @param respCode
	 * @param tranCode
	 * @param respMsg
	 * @param channel
	 * @param serviceid
	 * @return
	 */
	public Map<String,Object> createResponseHead(String respCode,String tranCode,String respMsg,String channel,String serviceid){
		
		Map<String,Object> head = new HashMap<String, Object>();
		head.put("respCode", respCode);
		head.put("tranCode", tranCode);
		head.put("respMsg", respMsg);
		head.put("tranFlow", "AAAAAAAAAAAA" + AppserverUtils.getDate15());
		head.put("merchantNo", "AAAAAAAAAAAA");
		head.put("channel", channel);
		head.put("tranDateTime", AppserverUtils.getDate14());
		head.put("version", "1.0.0");
		
		head.put("deviceId", "");
		head.put("isBreakOut", "");
		head.put("osType", "");
		head.put("osVersion", "");
		head.put("ip", "");
		head.put("appVersion", "");
		head.put("serviceid", serviceid);
		
		return head;
		
	}
	
	
	/**
	 * 消息错误信息响应
	 * 
	 * @param ge
	 *            错误信息
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	public void responseMsgError(GateWayException ge, HttpServletRequest request, HttpServletResponse response, Map<String,Object> head) {
		log.info("网关请求完成, 开始响应错误信息");
		head.put("resCode", ge.getCode());
		head.put("resMessage", ge.getMessage());
		
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("head", head);
		respMap.put("body", "");
		setResponseCommInfo(respMap, request);
		String responseStr = null;
		try {
			responseStr = messageChangeUtil.object2Json(respMap);
		} catch (GateWayException e) {
			log.debug("响应时异常：" + ExceptionUtils.getFullStackTrace(e));
			responseStr = "响应时异常：" + e.getCode() + ": " + e.getMessage();
		}
		doResponse(responseStr, request, response);
	}
	
	/**
	 * 消息成功信息响应
	 * @param request
	 * @param response 
	 * @param body 响应内容
	 */
	public void responseMsgSuccess(HttpServletRequest request, HttpServletResponse response ,Object body, Map<String,Object> head) {
		log.info("网关请求完成, 开始响应成功信息");
		head.put("resCode", Constants.RESPONSE_SUCCESS);
		head.put("resMessage", Constants.RESPONSE_SUCCESS_MSG);
		
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("head", head);
		respMap.put("body", body);
		try {
			doResponse(messageChangeUtil.object2Json(respMap), request, response);
		} catch (GateWayException e) {
			responseMsgError(e, request, response,head);
		}
	}

	
	
	
	/**消息推送
	 * 执行响应
	 * 
	 * @param responseJson
	 * @param request
	 * @param response
	 */
	public void responseMsgSuccess(HttpServletRequest request, HttpServletResponse response,String data) {
		log.info("网关请求完成, 开始响应成功信息");

		try {
			doResponse(messageChangeUtil.object2Json(data), request, response);
		} catch (GateWayException e) {
			responseError(e, request, response);
		}
	}
	

	/**
	 * 消息成功信息响应
	 * @param request
	 * @param response 
	 * @param body 响应内容
	 */
	public void responseBossMsgSuccess(HttpServletRequest request, HttpServletResponse response ,Object body, Map<String,Object> head) {
		log.info("网关请求完成, 开始响应成功信息");
		
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("head", head);
		respMap.put("body", body);
		try {
			response.setCharacterEncoding("UTF-8(编码的方式)");
			response.setContentType("text/html;charset=UTF-8");
			doResponse(messageChangeUtil.object2Json(respMap), request, response);
		} catch (GateWayException e) {
			responseMsgError(e, request, response,head);
		}
	}
	
	
}
