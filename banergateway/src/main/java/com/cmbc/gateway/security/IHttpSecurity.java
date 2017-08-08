package com.cmbc.gateway.security;

import javax.servlet.http.HttpServletRequest;

import com.cmbc.gateway.exception.GateWayException;

public interface IHttpSecurity {

	public boolean attackDef(HttpServletRequest request) throws GateWayException;
}
