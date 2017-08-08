package com.cmbc.gateway.security.encrypt;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Hex;

import com.cmbc.gateway.constants.GWExceptionConstants;
import com.cmbc.gateway.exception.GateWayException;
import com.cmbc.gateway.security.encrypt.EncryptUtil.EncryptType;
import com.cmbc.gateway.security.ifca.AbsEncrypt;

public class AESNoJCEEncrypt extends AbsEncrypt {

	private static AESNoJCEEncrypt aesEncrypt = null;

	public static AESNoJCEEncrypt getInstance() {
		if (aesEncrypt == null) {
			aesEncrypt = new AESNoJCEEncrypt();
		}
		return aesEncrypt;
	}

	// @Override
	// public String encrypt(String plaintext) throws Exception {
	// return this.encrypt(plaintext, null);
	// }
	//
	// @Override
	// public String decrypt(String ciphertext) throws Exception {
	// return this.decrypt(ciphertext,null);
	// }

	@Override
	public String encrypt(String plaintext, Object key) throws Exception {
		byte[] bts = null;
		if (key != null) {
			bts = (byte[]) key;
		} else {
			throw new GateWayException(GWExceptionConstants.GW1000_CODE,
					GWExceptionConstants.GW1000_DESC + ": 密钥为空");
		}
		byte[] byteContent = plaintext.getBytes(getEncCodeing());// "UTF-8"
		int contLen = byteContent.length;
		BufferedBlockCipher engine = new PaddedBufferedBlockCipher(
				new CBCBlockCipher(new AESFastEngine()));
		engine.init(true, new ParametersWithIV(new KeyParameter(bts), bts));
		byte[] enc = new byte[engine.getOutputSize(contLen)];
		int size1 = engine.processBytes(byteContent, 0, contLen, enc, 0);
		int size2 = engine.doFinal(enc, size1);
		byte[] encryptedContent = new byte[size1 + size2];
		System.arraycopy(enc, 0, encryptedContent, 0, encryptedContent.length);
		return new String(Hex.encode(encryptedContent));
	}

	@Override
	public String decrypt(String ciphertext, Object key) throws Exception {
		byte[] bts = null;
		if (key != null) {
			bts = (byte[]) key;
		} else {
			// bts = DEFAULT_AES_KEY;
		}
		byte[] tmps = Hex.decode(ciphertext);
		BufferedBlockCipher engine = new PaddedBufferedBlockCipher(
				new CBCBlockCipher(new AESFastEngine()));
		engine.init(false, new ParametersWithIV(new KeyParameter(bts), bts));
		byte[] dec = new byte[engine.getOutputSize(tmps.length)];
		int size1 = engine.processBytes(tmps, 0, tmps.length, dec, 0);
		int size2 = engine.doFinal(dec, size1);
		byte[] decryptedContent = new byte[size1 + size2];
		System.arraycopy(dec, 0, decryptedContent, 0, decryptedContent.length);
		return new String(decryptedContent);
	}

	@Override
	public String getEncryptName() {
		return EncryptType.AESNOJCE.name();
	}

	// @Override
	// public int getKeyLen() {
	// return DEFAULT_AES_KEY.length;
	// }

}
