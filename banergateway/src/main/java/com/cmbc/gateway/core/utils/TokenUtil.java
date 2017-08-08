package com.cmbc.gateway.core.utils;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;

@Slf4j
public class TokenUtil {

	public static String getNextToken() {
		UUID uuid = UUID.randomUUID();
		return StringUtils.replaceChars(uuid.toString().substring(20), "-", "") + TimeUtil.get17LTime();

	}

	public static void main(String[] args) {
		String token = getNextToken();
		log.info(token.length() + ": " + token);
	}

}
