package com.cmbc.gateway.security.ifca;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.spec.SecretKeySpec;

import com.cmbc.gateway.constants.GWExceptionConstants;
import com.cmbc.gateway.exception.GateWayException;

public abstract class AbsEncrypt implements IEncrypt {
	public static String encCodeing = "UTF-8";
	public static final String AES_ALGORITHM = "AES";
	public static final String AES_ALGORITHM_ECB = "AES";// 兼容IOS写法, 默认方式即为：AES/ECB/PKCS7Padding
	public static final String DES_ALGORITHM = "DESede";

	/**
	 * 支持以下任意一种算法
	 * 
	 * <pre>
	 * PBEWithMD5AndDES 
	 * PBEWithMD5AndTripleDES 
	 * PBEWithSHA1AndDESede
	 * PBEWithSHA1AndRC2_40
	 * </pre>
	 */
	public static final String PBE_ALGORITHM = "PBEWITHMD5andDES";

	public static RSAPublicKey DEFAULT_RSA_PUBLIC_KEY = null;
	public static RSAPrivateKey DEFAULT_RSA_PRIVATE_KEY = null;

	public SecretKeySpec getAesKey(byte[] salt) throws GateWayException {
		SecretKeySpec key = null;
		if (salt != null) {
			key = new SecretKeySpec(salt, AES_ALGORITHM);
		} else {
			throw new GateWayException(GWExceptionConstants.GW1000_CODE,
					GWExceptionConstants.GW1000_DESC + ": 密钥为空");
		}
		return key;
	}

	@Override
	public String getEncCodeing() {
		return encCodeing;
	}

	@Override
	public void setEncCodeing(String encoding) {
		if (encoding != null) {
			encCodeing = encoding;
		}
	}

}
