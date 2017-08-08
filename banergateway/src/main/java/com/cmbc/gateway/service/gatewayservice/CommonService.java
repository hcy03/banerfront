package com.cmbc.gateway.service.gatewayservice;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.cmbc.gateway.exception.GateWayException;
import com.cmbc.gateway.service.BaseService;

@Slf4j
@Service("COMMON")
public class CommonService extends BaseService {

	@Override
	public Map<String, Object> executeService(Map<String, Object> head, Map<String, Object> body) throws GateWayException {
		log.debug("[COMMON]一般类型请求");
		String respBody = submitService(head, body);
		// tokenDao.updateCrtTimeByUserId(userId);
		Map<String, Object> respContext = createResponseContext(head, respBody);
		return respContext;
	}
	@Override
	public String submitPassThroughService(String formatType,String cryptType,String signType,String data) throws GateWayException {
		
		String param = "formatType="+formatType+"&cryptType="+cryptType+"&signType="+signType+"&data="+data;
		log.info("配置的新参数param："+param);
		String respStr = httpClientTransport.submit(this.getUrl(), param);
		return respStr;
	}

}
