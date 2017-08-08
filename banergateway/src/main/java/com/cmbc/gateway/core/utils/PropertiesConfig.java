package com.cmbc.gateway.core.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Slf4j
public class PropertiesConfig {

	private static final Log logger = LogFactory.getLog(PropertiesConfig.class);
	private static final String PROP_CONF_KEY = "schome_pe_config";
	private static Properties prop = null;

	private static void init() throws Exception {
		if (prop != null) {
			return;
		}
		String configPath = System.getProperty(PROP_CONF_KEY);
		InputStream in = null;
		if (configPath != null && configPath.length() > 0) {
			in = new FileInputStream(configPath);
		} else {
			in = PropertiesConfig.class.getResourceAsStream("/META-INF/config/config.properties");
		}
		if (in != null) {
			prop = new Properties();
			prop.load(in);
			in.close();
		}
		if (prop == null) {
			throw new Exception("read [" + configPath + "] failed!");
		}
		// config param
		Enumeration<?> enu = prop.propertyNames();
		while (enu.hasMoreElements()) {
			Object key = enu.nextElement();
			if (!(key instanceof String)) {
				continue;
			}
			String val = prop.getProperty((String) key);
			String newVal = "";
			int begin = 0;
			while ((begin = val.indexOf("P{")) != -1) {
				int end = val.indexOf("}", begin);
				String paramVal = "";
				if (end != -1) {
					String param = val.substring(begin + 2, end);
					if (prop.containsKey(param)) {
						paramVal = prop.getProperty(param);
					} else {
						paramVal = "P{" + param + "}";
					}
					newVal += val.substring(0, begin) + paramVal;
					if (val.length() > end + 1) {
						val = val.substring(end + 1);
						continue;
					} else {
						val = val.substring(end + 1);
						break;
					}
				} else {
					paramVal = val.substring(begin);
					newVal += paramVal;
					break;
				}
			}
			newVal += val;
			if (logger.isInfoEnabled()) {
				logger.info("PARAM{" + key + "]  VALUE[" + newVal + "]");
			}
			if (!newVal.equals(prop.getProperty((String) key))) {
				prop.put(key, newVal);
			}
		}
	}

	public static String getPropValue(String key) throws Exception {
		if (prop == null) {
			init();
		}
		if (prop.containsKey(key)) {
			return prop.getProperty(key);
		}
		return null;
	}

	public static String getPropValue(String key, String defValue) {
		try {
			if (prop == null) {
				init();
			}
			if (prop.containsKey(key)) {
				return prop.getProperty(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defValue;
	}

	public static void main(String[] args) {
		try {
			log.info(PropertiesConfig.getPropValue("wxNo"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}