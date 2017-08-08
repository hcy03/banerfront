package com.cmbc.gateway.security.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.cmbc.gateway.security.encrypt.EncryptUtil.EncryptType;
import com.cmbc.gateway.security.ifca.AbsEncrypt;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;


/**
 * AES128
 * 
 * @author pengning
 * 
 */
public class AESEncrypt extends AbsEncrypt {
	
	private static AESEncrypt aesEncrypt = null;

	public static AESEncrypt getInstance() {
		if (aesEncrypt == null) {
			aesEncrypt = new AESEncrypt();
		}
		return aesEncrypt;
	}


	private byte[] _encrypt(String plaintext, byte[] salt) throws Exception {
		SecretKeySpec key = getAesKey(salt);
		Cipher cipher = Cipher.getInstance(AES_ALGORITHM_ECB);
		byte[] byteContent = plaintext.getBytes(getEncCodeing());
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] result = cipher.doFinal(byteContent);
		return result; 
	}

	/**
	 * 加密
	 * 
	 * @param plaintext
	 * @param salt
	 * @return String 
	 */
//	@Override
//	public String encrypt(String plaintext) throws Exception {
//		return byte2Hex(_encrypt(plaintext, DEFAULT_AES_KEY));
//	}

	/**
	 * 解密
	 * @param ciphertext
	 * @param salt
	 * @return byte[]
	 * @throws Exception
	 */
	private byte[] _decrypt(byte[] ciphertext, byte[] salt) throws Exception {
		SecretKeySpec key = getAesKey(salt);
		Cipher cipher = Cipher.getInstance(AES_ALGORITHM_ECB);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] result = cipher.doFinal(ciphertext);
		return result; 
	}

//	@Override
//	public String decrypt(String ciphertext) throws Exception {
//		return new String(_decrypt(hex2Byte(ciphertext), DEFAULT_AES_KEY));
//	}

	private String byte2Hex(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	private byte[] hex2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	@Override
	public String encrypt(String plaintext, Object key) throws Exception {
		byte[] bts = null;
		if(key!=null){
			bts = (byte[]) key;
		}
		return byte2Hex(_encrypt(plaintext,bts));
	}

	@Override
	public String decrypt(String ciphertext, Object key) throws Exception {
		byte[] bts = null;
		if(key!=null){
			bts = (byte[]) key;
		}
		return new String(_decrypt(hex2Byte(ciphertext), bts),"UTF-8");
	}


	@Override
	public String getEncryptName() {
		return EncryptType.AES.name();
	}


//	@Override
//	public int getKeyLen() {
//		return DEFAULT_AES_KEY.length;
//	}
}
