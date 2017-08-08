package com.cmbc.gateway.service;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cmbc.gateway.constants.Constants;
import com.cmbc.gateway.constants.GWExceptionConstants;
import com.cmbc.gateway.constants.NotNullConstants;
import com.cmbc.gateway.core.utils.ApplicationContextUtil;
import com.cmbc.gateway.core.utils.JsonUtil;
import com.cmbc.gateway.core.utils.MessageChangeUtil;
import com.cmbc.gateway.core.utils.NullCheckUtil;
import com.cmbc.gateway.core.utils.ResponseUtil;
import com.cmbc.gateway.exception.GateWayException;
import com.cmbc.gateway.service.gatewayservice.CommonService;

@Slf4j
@Service("gateWayService")
public class GateWayService {

	@Resource
	private ResponseUtil responseUtil;
	@Resource
	private JsonUtil jsonUtil;
	@Resource
	private MessageChangeUtil messageChangeUtil;
	@Resource
	private CommonService commonService;

	public void executeRequest(HttpServletRequest request, HttpServletResponse response) throws GateWayException {
		/** Step1：获取到客户端请求报文的请求头和体, 进行头部参数空值校验 */
		log.debug("开始执行网关请求");
		Map<String, Object> context = jsonUtil.getInfoMap(request.getAttribute("context"));
		NullCheckUtil.checkKeyNull(NotNullConstants.FIELD_REQCONTEXT, context, "context");
		Map<String, Object> head = jsonUtil.getInfoMap(context.get("head")); // context-head
		Map<String, Object> body = jsonUtil.getInfoMap(context.get("body")); // context-body(message-data-[head+body])

		String operType = (String) head.get("type");
		if (!Constants.typeList.contains(operType)) {
			throw new GateWayException(GWExceptionConstants.REQ0010_CODE, StringUtils.replace(
					GWExceptionConstants.REQ0010_DESC, "{0}", operType));
		}
		log.debug("请求类型为: [ " + operType + " ]---------------");
		// 头部参数空值校验
		checkContextHead(head);
		// 校验token相关

		/** Step2：根据请求类型做不同的服务业务逻辑 */
		Object respContext;
		BaseService service = ApplicationContextUtil.getBean(operType, BaseService.class);
		if (service == null) {
			throw new GateWayException(GWExceptionConstants.REQ0010_CODE, StringUtils.replace(
					GWExceptionConstants.REQ0010_DESC, "{0}", "context|head|type"));
		}
		respContext = service.executeService(head, body);
		responseUtil.responseSuccess(request, response, respContext);
	}

	/**
	 * 
	 * @description <p>
	 *              执行透传
	 *              </p>
	 * @auther liujian
	 * @date 2017年5月31日 下午3:25:14
	 * @update
	 * @param request
	 * @param response
	 * @throws GateWayException
	 */
	public String excutePassThrough(HttpServletRequest request, HttpServletResponse response) throws GateWayException {
		/** Step1：获取到客户端请求报文 */
		log.debug("开始执行透传请求");
		//获取请求参数
		String formatType = request.getParameter("formatType");
		String cryptType = request.getParameter("cryptType");
		String signType = request.getParameter("signType");
		String requestdata = request.getParameter("data");
		log.info("formatType= "+formatType+" cryptType= "+cryptType+" signType= "+signType+" requestdata= "+requestdata);
		//进行参数组装
		String resp = commonService.submitPassThroughService(formatType, cryptType, signType, requestdata);
		
		
		/** Step2：重新组建报文体，透传给QR */
		
		return resp;
	}

	/**
	 * 头部参数空值校验
	 * 
	 * @param head
	 * @throws GateWayException
	 */
	private void checkContextHead(Map<String, Object> head) throws GateWayException {
		log.debug("[context-head]参数校验");
		String channel = (String) head.get("channel");
		if (!Constants.channelList.contains(channel)) {
			throw new GateWayException(GWExceptionConstants.REQ0010_CODE, StringUtils.replace(
					GWExceptionConstants.REQ0010_DESC, "{0}", "channel"));
		}
		String operType = (String) head.get("type");
		if (!Constants.typeList.contains(operType)) {
			throw new GateWayException(GWExceptionConstants.REQ0010_CODE, StringUtils.replace(
					GWExceptionConstants.REQ0010_DESC, "{0}", operType));
		}
		// 空值校验
		NullCheckUtil.checkNull(NotNullConstants.CONTEXT, head);
		// 关联空值校验
		NullCheckUtil.checkUnionNull(NotNullConstants.UNION_TYPE, head);
		NullCheckUtil.checkUnionNull(NotNullConstants.UNION_TYPE_LCBP, head);
		NullCheckUtil.checkUnionNull(NotNullConstants.UNION_TOKEN, head);
		log.debug("[context-head]参数校验通过");
	}

}