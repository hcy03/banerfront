package com.cmbc.gateway.core.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertiesUtil {

	private static Map<String, Properties> propMap = new HashMap<String, Properties>();

	private static String root_patch_fix = System.getProperty("contextpath");

	/**
	 * 初始化配置文件
	 * 
	 * @param propPath
	 */
	private static synchronized Properties init(String propsPath) {
		if (!propMap.containsKey(propsPath)) {
			loadProperties(propsPath);
		}
		return propMap.get(propsPath);
	}

	/**
	 * 获取初始化参数
	 * 
	 * @param propName
	 * @return
	 */
	public static String readProperty(String propsPath, String propName) {
		Properties props = init(propsPath);
		String prop = "";
		try {
			prop = new String(props.getProperty(propName).getBytes(), "UTF-8");
		} catch (Exception e) {
			log.info("获取初始化参数异常.propsPath=[{}];propName=[{}]", propsPath, propName);
			log.error("获取初始化参数异常。", e);
		}
		return prop;
	}

	/**
	 * 缓存中没有该配置文件信息，则加载它
	 * 加载量超过一定程度就释放掉
	 * 
	 * @param propsPath
	 */
	private static void loadProperties(String propsPath) {
		if (propMap.size() > 1000) {
			propMap.clear();
		}
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(root_patch_fix + propsPath);
			props.load(in);
			propMap.put(propsPath, props);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
