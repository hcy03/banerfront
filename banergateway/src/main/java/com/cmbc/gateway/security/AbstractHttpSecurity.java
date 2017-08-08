package com.cmbc.gateway.security;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;

import com.cmbc.gateway.core.utils.JsonUtil;
import com.cmbc.gateway.core.utils.MessageChangeUtil;
import com.cmbc.gateway.exception.GateWayException;

@Slf4j
public abstract class AbstractHttpSecurity implements IHttpSecurity {

	@Resource
	private MessageChangeUtil messageChangeUtil;
	@Resource
	private JsonUtil jsonUtil;

	public void init() {
	}

	@Override
	public boolean attackDef(HttpServletRequest request)
			throws GateWayException {
		// check request parameter
		if (!checkRequestParam(request)) {
			return false;
		}
		// check request header
		if (!checkRequestHeader(request)) {
			return false;
		}
		Map<String, Object> cm = jsonUtil.getInfoMap(request
				.getAttribute("context"));
		// check json data
		if (!checkJsonData(cm)) {
			return false;
		}
		return true;
	}

	private boolean checkRequestParam(HttpServletRequest request)
			throws GateWayException {
		log.debug("校验请求参数[开始]");
		Enumeration<?> names = request.getAttributeNames();
		boolean result = true;
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			Object value = request.getAttribute(name);
			if (value instanceof String) {
				result = doParamCheck((String) value);
			} else if (value instanceof Map) {
				log.debug(value.getClass().getName());
				log.debug(value.toString());
				result = doParamCheck(messageChangeUtil.object2Json(value));
			} else {
				continue;
			}
		}
		if (result) {
			log.debug("校验请求参数[通过]");
		} else {
			log.debug("校验请求参数[未通过]");
		}
		return result;
	}

	private boolean checkRequestHeader(HttpServletRequest request) {
		log.debug("校验请求头[开始]");
		Enumeration<?> names = request.getHeaderNames();
		boolean result = true;
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			if (!name.startsWith("_"))
				continue;
			String value = request.getHeader(name);
			result = doParamCheck(value);
		}
		if (result) {
			log.debug("校验请求头[通过]");
		} else {
			log.debug("校验请求头[未通过]");
		}
		return result;
	}

	private boolean checkJsonData(Map<?, ?> dataMap) throws GateWayException {
		log.debug("校验json数据[开始]");
		if (dataMap == null) {
			log.debug("无json数据[通过]");
			return true;
		}
		boolean result = true;
		Iterator<?> it = dataMap.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Object value = dataMap.get(key);
			if (value instanceof String) {
				result = doParamCheck((String) value);
			} else if (value instanceof Map) {
				result = doParamCheck(messageChangeUtil.object2Json(value));
			} else {
				// ignore
			}
		}
		if (result) {
			log.debug("校验json数据[通过]");
		} else {
			log.debug("校验json数据[未通过]");
		}
		return result;
	}

	private boolean doParamCheck(String value) {
		if (!StringUtils.isBlank(value)) {
			String newVal = attack(value);
			if (!newVal.equalsIgnoreCase(value)) {
				return false;
			}
		}
		return true;
	}

	protected abstract String attack(String str);

}
