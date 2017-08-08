package com.cmbc.gateway.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cmbc.gateway.exception.GateWayException;
import com.cmbc.gateway.service.WxPageJumpService;

/**
 * 
 * 微信和支付宝公众号页面统一跳转
 * 
 * @author rm-licundong
 * 
 */
@Slf4j
@Controller
public class PageJumpController {

	// 微信页面跳转服务
	@Resource(name = "wxPageJumpService")
	private WxPageJumpService wxPageJumpService;

	/**
	 * 接收公众号菜单请求,调用微信公众平台接口查询openid
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/wxindex.do", method = RequestMethod.GET)
	public void wxindex(HttpServletRequest request, HttpServletResponse response) throws IOException {

		try {
			wxPageJumpService.sendWxRequest(request, response);
		} catch (GateWayException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}

	}

	/**
	 * 接收公众号请求,将openid和菜单标识传递到统一跳转页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/pagejump.do", method = RequestMethod.GET)
	public void pagejump(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			wxPageJumpService.jumpPage(request, response);
		} catch (GateWayException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getFullStackTrace(e));
		}
	}

}
