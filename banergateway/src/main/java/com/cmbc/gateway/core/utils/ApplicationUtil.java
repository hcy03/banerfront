package com.cmbc.gateway.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationUtil implements ApplicationContextAware {
	// 单例处理
	private static ApplicationUtil applicationUtil = new ApplicationUtil();

	private ApplicationUtil() {

	}

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		applicationUtil.applicationContext = applicationContext;
	}

	/**
	 * 获取应用上下文
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		ApplicationContext context = applicationUtil.applicationContext;
		return context;
	}

}
