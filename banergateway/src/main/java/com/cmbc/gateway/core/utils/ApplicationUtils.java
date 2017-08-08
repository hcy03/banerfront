/*
 * 功  能：简单说明该类的功能
 * 
 * 文件名：ApplicationUtils.java
 * 
 * 描  述：
 * 
 * [变更履历]
 * Version   变更日         部署              作者           变更内容
 * -----------------------------------------------------------------------------
 * V1.00     2015年10月8日   Rmitec    ZhouJing     CREATE
 * -----------------------------------------------------------------------------
 *
 *
 * Copyright (c) 2015 Rmitec All Rights Reserved.
 *┌─────────────────────────────────────────────────—────┐
 *│ 版权声明                               Rmitec       │
 *└──────────────────────────────—————————————————————───┘
 */

package com.cmbc.gateway.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * DESCRIPTION:
 * 
 * <p>
 * <a href="ApplicationUtils.java"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:ZhouJing2@rmitec.com.cn">ZhouJing</a>
 * 
 * @version Revision: 1.0 Date: 2015年10月8日 上午9:41:23
 * 
 */

public class ApplicationUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) applicationContext.getBean(beanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		// TODO Auto-generated method stub
		ApplicationUtils.applicationContext = arg0;

	}

}
