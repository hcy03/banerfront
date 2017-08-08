package com.cmbc.gateway.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.msds.baseUtil.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmbc.gateway.exception.GateWayException;
import com.cmbc.gateway.service.GateWayService;

/**
 * 网关接收请求的入口
 * 
 * @author rm-xiahaobo
 * 
 */
@Slf4j
@Controller
public class GateWayController {

	// 网关服务
	@Resource(name = "gateWayService")
	private GateWayService service;

	/**
	 * 接收网关请求，交由网关服务处理类处理
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws GateWayException
	 */
	@ResponseBody
	@RequestMapping("/gwserver.do")
	public void gateway(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			service.executeRequest(request, response);
		} catch (GateWayException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		} catch (Exception e) {
			log.error("处理网关请求失败", e);
		}
	}

	/**
	 * 
	 * @description <p>
	 *              用于消息透传的接口
	 *              </p>
	 * @auther liujian
	 * @date 2017年5月31日 下午3:17:43
	 * @update
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/gwpsserver.do")
	public String passThrough(HttpServletRequest request, HttpServletResponse response) {
		try {
			return service.excutePassThrough(request, response);
		} catch (GateWayException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return "消息透传失败";
		} catch (Exception e) {
			log.error("消息透传失败", e);
			return "消息透传失败";
		}
	}
}
