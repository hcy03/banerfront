package com.cmbc.gateway.service;

import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.cmbc.gateway.constants.QRConstants;
import com.cmbc.gateway.core.utils.MessageChangeUtil;
import com.cmbc.gateway.core.utils.PropertiesUtil;
import com.cmbc.gateway.core.utils.RequestUtil;
import com.cmbc.gateway.exception.GateWayException;
import com.cmbc.gateway.http.HttpClientTransport;

/**
 * 微信到交易系统 统一请求
 * @author licd
 *
 */
@Slf4j
@Service("wxToQrpayHttpService")
public class WxToQrpayHttpService {
	
	private static String httpPropPath = "/gateway/conf/http.properties";

	@Resource
	private HttpClientTransport httpClientTransport;// 发送http请求
	@Resource
	private MessageChangeUtil messageChangeUtil;
	@Resource
	private RequestUtil reqUtil;

	/**
	 * 代理商绑定
	 * @param transCode
	 * @param body
	 * @return
	 * @throws GateWayException
	 */
	@SuppressWarnings("unchecked")
	public boolean bind(String transCode, Map<String, Object> body) throws GateWayException {
		String param = reqUtil.buildRequestParams(QRConstants.WX_CHANNEL,QRConstants.WX_MER_SERVER_ID,transCode, body);
		String url = PropertiesUtil.readProperty(httpPropPath, "url");
		String respStr = httpClientTransport.submit(url, param);
		Map<String, Object> resp = messageChangeUtil.json2Object(respStr, Map.class);
		Map<String, Object> message = (Map<String, Object>) resp.get("message");
		Map<String, Object> data = (Map<String, Object>) message.get("data");
		Map<String, Object> head = (Map<String, Object>) data.get("head");
		String respCode = (String) head.get("respCode");
		String respMsg = (String) head.get("respMsg");
		if ("C000000000".equals(respCode)) {
			log.info("您为代理商推荐商户，代理商绑定成功");
			return true;
		} else {
			log.info("您为代理商推荐商户,代理商绑定失败,失败原因：" + respMsg);
			return false;
		}
	}
	
	/**
	 * Wx统一像交易系统发起请求
	 * @param reqServiceId
	 * @param transCode
	 * @param body
	 * @return
	 * @throws GateWayException
	 */
	@SuppressWarnings("unchecked")
	public boolean reqQrpay(String reqServiceId, String transCode,Map<String, Object> body) throws GateWayException {
		String param = reqUtil.buildRequestParams(QRConstants.WX_CHANNEL,reqServiceId,transCode, body);
		String url = PropertiesUtil.readProperty(httpPropPath, "url");
		String respStr = httpClientTransport.submit(url, param);
		Map<String, Object> resp = messageChangeUtil.json2Object(respStr, Map.class);
		Map<String, Object> message = (Map<String, Object>) resp.get("message");
		Map<String, Object> data = (Map<String, Object>) message.get("data");
		Map<String, Object> head = (Map<String, Object>) data.get("head");
		String respCode = (String) head.get("respCode");
		String respMsg = (String) head.get("respMsg");
		if ("C000000000".equals(respCode)) {
			log.info("发起请求成功 form " + transCode);
			return true;
		} else {
			log.info("发起请求失败,失败原因: " + respMsg);
			return false;
		}
	}

	

}
