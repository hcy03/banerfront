package com.cmbc.gateway.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmbc.gateway.constants.GWExceptionConstants;
import com.cmbc.gateway.exception.GateWayException;

public class SecurityChain {

	private List<IHttpSecurity> chainList;

	public List<IHttpSecurity> getChainList() {
		return chainList;
	}

	public void setChainList(List<IHttpSecurity> chainList) {
		this.chainList = chainList;
	}

	public void doDefense(HttpServletRequest request, HttpServletResponse response) throws SecurityException, GateWayException {
		for (IHttpSecurity security : chainList) {
			boolean checkResult = security.attackDef(request);
			if (!checkResult) {
				response.setStatus(HttpServletResponse.SC_OK);
				throw new GateWayException(GWExceptionConstants.SET0001_CODE, GWExceptionConstants.SET0001_DESC);
			}
		}
	}
}