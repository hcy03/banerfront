package com.cmbc.gateway.core.utils;

import com.cmbc.gateway.constants.GWExceptionConstants;
import com.cmbc.gateway.exception.GateWayException;

public class ClassUtil {
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String classPath, Class<T> cls)
			throws GateWayException {
		try {
			cls = (Class<T>) Class.forName(classPath);
			return cls.newInstance();
		} catch (Exception e) {
			throw new GateWayException(GWExceptionConstants.GW1000_CODE,
					GWExceptionConstants.GW1000_DESC + ": 未找到加解密实现类");
		}
	}
}
