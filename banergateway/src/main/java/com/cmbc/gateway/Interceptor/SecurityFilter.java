package com.cmbc.gateway.Interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cmbc.gateway.core.utils.RequestUtil;
import com.cmbc.gateway.core.utils.ResponseUtil;
import com.cmbc.gateway.exception.GateWayException;
import com.cmbc.gateway.security.SecurityChain;

/**
 * 请求过滤器
 * 
 * @author rm-xiahaobo
 * 
 */
@Slf4j
public class SecurityFilter implements Filter {

	private RequestUtil requestUtil;
	private ResponseUtil responseUtil;
	private SecurityChain securityChain;

	// private ApplicationContext context;

	public void init(FilterConfig config) throws ServletException {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		securityChain = (SecurityChain) context.getBean("securityChain");
		requestUtil = (RequestUtil) context.getBean("requestUtil");
		responseUtil = (ResponseUtil) context.getBean("responseUtil");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<收到请求<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		log.debug("req========"+req.getRequestURI());
		log.debug("----------进入过滤器----------");
		try {
			req = requestUtil.requestParse(req);
			log.debug("开始安全过滤");
			securityChain.doDefense(req, resp);
			log.debug("安全过滤完成");
		} catch (GateWayException e) {
			log.debug(ExceptionUtils.getFullStackTrace(e));
			responseUtil.responseError(e, req, resp);
			return;
		}
		chain.doFilter(request, response);
	}

}
