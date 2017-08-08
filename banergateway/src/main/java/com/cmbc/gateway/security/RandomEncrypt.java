/*
 * 功  能：简单说明该类的功能
 * 
 * 文件名：RandomEncrypt.java
 * 
 * 描  述：
 * 
 * [变更履历]
 * Version   变更日         部署              作者           变更内容
 * -----------------------------------------------------------------------------
 * V1.00     2015年10月27日   Rmitec    ZhouJing     CREATE
 * -----------------------------------------------------------------------------
 *
 *
 * Copyright (c) 2015 Rmitec All Rights Reserved.
 *┌─────────────────────────────────────────────────—────┐
 *│ 版权声明                               Rmitec       │
 *└──────────────────────────────—————————————————————───┘
 */

package com.cmbc.gateway.security;

import java.security.SecureRandom;

/**
 *  DESCRIPTION:八位随机数生成
 *
 * <p>
 * <a href="RandomEncrypt.java"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:ZhouJing2@rmitec.com.cn">ZhouJing</a>
 *
 * @version Revision: 1.0  Date: 2015年10月27日 上午9:22:22 
 *
 */

public class RandomEncrypt {
	private static RandomEncrypt randomEncrypt = null;

	public static RandomEncrypt getInstance() {
		if (randomEncrypt == null) {
			randomEncrypt = new RandomEncrypt();
		}
		return randomEncrypt;
	}

	
	public String getRandom() {
		byte bytes[] = SecureRandom.getSeed(8);
		StringBuffer sb = new StringBuffer(bytes.length*4);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(convertDigit(bytes[i] >> 4));
			sb.append(convertDigit(bytes[i] & 15));
		}
		return sb.toString();
	}
	
	private static char convertDigit(int value) {
		value &= 15;
		if (value >= 10)
			return (char) ((value - 10) + 97);
		else
			return (char) (value + 48);
	}
}
