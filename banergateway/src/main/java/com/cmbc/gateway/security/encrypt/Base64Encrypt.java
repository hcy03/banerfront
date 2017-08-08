package com.cmbc.gateway.security.encrypt;

import com.cmbc.gateway.security.encrypt.EncryptUtil.EncryptType;
import com.cmbc.gateway.security.ifca.AbsEncrypt;

public class Base64Encrypt extends AbsEncrypt {

	private static Base64Encrypt base64Encrypt = null;

	public static Base64Encrypt getInstance() {
		if (base64Encrypt == null) {
			base64Encrypt = new Base64Encrypt();
		}
		return base64Encrypt;
	}

	@Override
	public String encrypt(String plaintext, Object key) throws Exception {
		return new String(Base64.encodeBase64(plaintext
				.getBytes(getEncCodeing())), getEncCodeing());
	}

	@Override
	public String decrypt(String ciphertext, Object key) throws Exception {
		return new String(Base64.decodeBase64(ciphertext
				.getBytes(getEncCodeing())), getEncCodeing());
	}

	@Override
	public String getEncryptName() {
		return EncryptType.BASE64.name();
	}
}
