package com.cmbc.gateway.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cmbc.gateway.security.SecurityChain;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
	private SecurityChain securityChain;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		try {
			securityChain.doDefense(request, response);
			return true;
		} catch (SecurityException e) {
			return false;
		}
	}

	public SecurityChain getSecurityChain() {
		return securityChain;
	}

	public void setSecurityChain(SecurityChain securityChain) {
		this.securityChain = securityChain;
	}

}
