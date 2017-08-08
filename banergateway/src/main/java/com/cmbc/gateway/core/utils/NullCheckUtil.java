package com.cmbc.gateway.core.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cmbc.gateway.constants.GWExceptionConstants;
import com.cmbc.gateway.exception.GateWayException;

public class NullCheckUtil {
	/**
	 * 对valMap中的keys进行关联空值检测
	 * 
	 * @param rules
	 *            要求是有序的Map，第一个元素是索引key
	 * @param valMap
	 *            需要检测关联非空的参数Map
	 * @return
	 * @throws GateWayException
	 */
	public static void checkUnionNull(Map<String, List<String>> rules,
			Map<String, Object> valMap) throws GateWayException {
		Set<String> keys = rules.keySet();
		Iterator<String> ki = keys.iterator();
		String keyIndex = ki.next();
		String unionValue = (String) valMap.get(keyIndex);
		if (StringUtils.isBlank(unionValue)) {
			throw new GateWayException(GWExceptionConstants.REQ1000_CODE,
					StringUtils.replace(GWExceptionConstants.REQ1000_DESC,
							"{0}", keyIndex));
		}
		List<String> notNullKey = rules.get(unionValue);
		if (notNullKey == null) {
			return;
		}
		for (String unionKey : notNullKey) {
			if (StringUtils.isBlank((String) valMap.get(unionKey))) {
				String exception = StringUtils.replace(
						GWExceptionConstants.REQ2000_DESC, "{0}", keyIndex);
				exception = StringUtils.replace(exception, "{1}", unionValue);
				exception = StringUtils.replace(exception, "{2}", unionKey);
				throw new GateWayException(GWExceptionConstants.REQ2000_CODE,
						exception);
			}
		}
	}

	/**
	 * 非规则内关联字符空值检测
	 * 
	 * @param rules
	 * @param valMap
	 * @throws GateWayException
	 */
	public static void checkUnionNotNull(Map<String, String> rules,
			Map<String, Object> valMap) throws GateWayException {
		// 获取规则及关联字段
		for (String key : rules.keySet()) {
			String[] rule = rules.get(key).split("\\|");
			String unionValue = (String) valMap.get(key);
			if (StringUtils.isBlank(unionValue)) {
				throw new GateWayException(GWExceptionConstants.REQ1000_CODE,
						StringUtils.replace(GWExceptionConstants.REQ1000_DESC,
								"{0}", key));
			}
			// 非指定关联值时不允许空值
			if (!unionValue.equals(rule[0])) {
				if (!StringUtils.isBlank((String) valMap.get(rule[1]))) {
					return;
				}
				String exception = StringUtils.replace(
						GWExceptionConstants.REQ2000_DESC, "{0}", key);
				exception = StringUtils.replace(exception, "{1}", unionValue);
				exception = StringUtils.replace(exception, "{2}", rule[1]);
				throw new GateWayException(GWExceptionConstants.REQ2000_CODE,
						exception);
			}
			continue;
		}
	}

	/**
	 * 对valMap中的keys进行空值检测
	 * 
	 * @param keys
	 *            要求非空的值的key
	 * @param valMap
	 *            需要检测非空的map对象
	 * @throws GateWayException
	 */
	public static void checkNull(String[] keys, Map<String, Object> valMap)
			throws GateWayException {
		for (String key : keys) {
			if (valMap.get(key) instanceof String ? StringUtils
					.isBlank((String) valMap.get(key))
					: (valMap.get(key) == null)) {
				throw new GateWayException(GWExceptionConstants.REQ1000_CODE,
						StringUtils.replace(GWExceptionConstants.REQ1000_DESC,
								"{0}", key));
			}
		}
	}

	/**
	 * 
	 * @param keys
	 *            要求必有的字段
	 * @param valMap
	 *            字段的map
	 * @param where
	 * @throws GateWayException
	 */
	public static void checkKeyNull(String[] keys, Map<String, Object> valMap,
			String where) throws GateWayException {
		for (String key : keys) {
			if (!valMap.containsKey(key)) {
				throw new GateWayException(GWExceptionConstants.REQ1100_CODE,
						StringUtils.replace(GWExceptionConstants.REQ1100_DESC,
								"{0}", key) + "(在[" + where + "]中)");
			}
		}
	}
}
