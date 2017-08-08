package com.cmbc.gateway.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import com.cmbc.gateway.core.utils.JsonUtil;
import com.cmbc.gateway.core.utils.MessageChangeUtil;
import com.cmbc.gateway.core.utils.ResponseUtil;
import com.cmbc.gateway.core.utils.TimeUtil;
import com.cmbc.gateway.exception.GateWayException;
import com.cmbc.gateway.http.HttpClientTransport;
import com.cmbc.gateway.http.HttpPropertiesUtil;

@Slf4j
public abstract class BaseService extends HttpPropertiesUtil{

	@Resource
	public HttpClientTransport httpClientTransport;// 发送http请求
	@Resource
	public MessageChangeUtil messageChangeUtil;
	@Resource
	public ResponseUtil responseUtil;
	@Resource
	public JsonUtil jsonUtil;

	public abstract Map<String, Object> executeService(Map<String, Object> head, Map<String, Object> body) throws GateWayException;
	public abstract String submitPassThroughService(String formatType,String cryptType,String signType,String data) throws GateWayException;
	protected String submitService(Map<String, Object> head, Map<String, Object> body) throws GateWayException {
		// Object reqBody = body.get("body");
		// String url = (String) body.get("url");
		// Map<String, Object> reqHead = createRequestHead(head, body);
		// String reqStr = createRequestMapString(reqHead, reqBody);
		// String respStr = httpClientTransport.submit(url, messageChangeUtil.object2Json(body));
		// Map<String, Object> message = (Map<String, Object>) body.get("message");
		// Map<String, Object> date = (Map<String, Object>) message.get("data");
		// Map<String, Object> heads = (Map<String, Object>) date.get("head");
		// String respStr = httpClientTransport.submitTest(url, messageChangeUtil.object2Json(body), (String) heads.get("tranCode"));
		// String respStr = httpClientTransport.submit(url, messageChangeUtil.object2Json(body));
		
		String transdata = messageChangeUtil.object2Json(body);
		String param = this.getParam() + transdata;
		String respStr = httpClientTransport.submit(this.getUrl(), param);
		return respStr;
	}

	protected Map<String, Object> createRequestHead(Map<String, Object> head, Map<String, Object> body) {
		log.info("创建向后台请求head");
		Map<String, Object> reqHead = new HashMap<String, Object>();
		reqHead.put("url", body.get("url"));
		reqHead.put("userId", head.get("userId"));
		reqHead.put("sessionInfo", head.get("sessionInfo"));
		reqHead.put("checkTokenFlag", head.get("checkTokenFlag"));
		reqHead.put("logId", TimeUtil.get17LTime());
		reqHead.put("type", head.get("type"));
		log.info("创建向后台请求head完成");
		return reqHead;
	}

	protected String createRequestMapString(Map<String, Object> reqHead, Object reqBody) throws GateWayException {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info("创建向后台请求报文");
		reqMap.put("head", reqHead);
		reqMap.put("body", reqBody);
		String reqStr = messageChangeUtil.object2Json(reqMap);
		log.info("创建向后台请求报文完成");
		return reqStr;
	}

	/**
	 * 
	 * @param respHead
	 * @param respBody
	 * @return
	 * @throws GateWayException
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> createResponseContext(Map<String, Object> respHead, Object respBody) throws GateWayException {
		log.debug("创建响应报文开始");
		if (respHead.containsKey("sessionInfo")) {
			respHead.remove("sessionInfo");
		}
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("head", respHead);
		Map<String, Object> resp = null;
		try {
			if (respBody instanceof String) {
				resp = messageChangeUtil.json2Object(respBody.toString(), Map.class);
				context.put("body", resp);
			} else {
				log.debug("参数类型不是String");
				context.put("body", respBody);
			}
		} catch (Exception e) {
			context.put("body", respBody);
		}
		log.debug("创建响应报文完成");
		return context;
	}

}
