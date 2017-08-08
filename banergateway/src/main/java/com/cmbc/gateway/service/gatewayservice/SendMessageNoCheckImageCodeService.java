package com.cmbc.gateway.service.gatewayservice;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.cmbc.gateway.exception.GateWayException;
import com.cmbc.gateway.service.BaseService;

@Slf4j
@Service("SMSNCIC")
public class SendMessageNoCheckImageCodeService extends BaseService {

	@Override
	public Map<String, Object> executeService(Map<String, Object> head, Map<String, Object> body) throws GateWayException {
		log.info("[SMSNCIC]发送短信验证码请求, 不验证图形验证码");
		String respStr = submitService(head, body);
		return createResponseContext(head, respStr);
	}

	@Override
	public String submitPassThroughService(String formatType, String cryptType,
			String signType, String data) throws GateWayException {
		// TODO Auto-generated method stub
		return null;
	}
}
