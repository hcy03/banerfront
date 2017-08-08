package com.cmbc.gateway.core.utils;

import org.springframework.context.ApplicationContext;

public class ApplicationContextUtil {

	private static ApplicationContext applicationContext = ApplicationUtil.getApplicationContext();

	public static <T> T getBean(String beanName, Class<T> beanClass) {
		return applicationContext.getBean(beanName, beanClass);
	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

}
