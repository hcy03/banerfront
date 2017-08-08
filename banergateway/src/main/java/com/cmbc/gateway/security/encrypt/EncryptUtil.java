package com.cmbc.gateway.security.encrypt;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

import com.cmbc.gateway.core.utils.ClassUtil;
import com.cmbc.gateway.exception.GateWayException;
import com.cmbc.gateway.security.ifca.AbsEncrypt;
import com.cmbc.gateway.security.ifca.IEncrypt;

/**
 * 获取加解密实现工具类
 * 
 * @author pengning
 * 
 */
public class EncryptUtil {
	public static List<IEncrypt> messageEncryptList = new ArrayList<IEncrypt>();

	public static void addMessageEncrypt(IEncrypt encrypt) {
		messageEncryptList.add(encrypt);
	}

	/**
	 * 抽取随机算法
	 * 
	 * @return
	 */
	// public static IEncrypt getRandomEncrypt(){
	// IEncrypt encrypt = null;
	// SecureRandom secureRandom = new SecureRandom();
	// int len = messageEncryptList.size();
	// if(len==1){
	// encrypt = messageEncryptList.get(0);
	// }else if(len>1){
	// int seq = (int)(secureRandom.nextDouble()*len);
	// encrypt = messageEncryptList.get(seq);
	// }
	// return encrypt;
	// }
	// /**
	// * 获取加密随机字符串
	// * @param len
	// * @return
	// */
	// public static byte[] getRandomKeyByte(int len){
	// if(len==0){
	// return null;
	// }
	// return SecureRandom.getSeed(len);
	// }
	//

	public enum EncryptType {
		AES, MD5, RSABC, RSA, AESJCE, AESNOJCE, BASE64, DESEDE, PBEMD5DES
	}

	public static IEncrypt getEncrypt(EncryptType type) {
		return getEncrypt(type, AbsEncrypt.encCodeing);
	}

	/**
	 * 获取加解密实现
	 * 
	 * @param encryptType
	 * @return
	 */
	public static IEncrypt getEncrypt(EncryptType type, String encoding) {
		IEncrypt encrypt = null;
		switch (type) {
		case AES:
			encrypt = AESEncrypt.getInstance();
			break;
		case AESNOJCE:
			encrypt = AESNoJCEEncrypt.getInstance();
			break;
		case BASE64:
			encrypt = Base64Encrypt.getInstance();
			break;
		default:
			encrypt = AESEncrypt.getInstance();
			break;
		}
		encrypt.setEncCodeing(encoding);
		return encrypt;
	}

	public static IEncrypt getEncrypt(String encryptClass)
			throws GateWayException {
		IEncrypt encrypt = ClassUtil.getInstance(encryptClass, IEncrypt.class);
		return encrypt;
	}

	/**
	 * 设置RSA加解密公私钥
	 * 
	 * @param pubKey
	 * @param priKey
	 */
	public static void setRSAPublicKey(RSAPublicKey pubKey) {
		AbsEncrypt.DEFAULT_RSA_PUBLIC_KEY = pubKey;
	}

	/**
	 * 设置RSA加解密公私钥
	 * 
	 * @param pubKey
	 * @param priKey
	 */
	public static void setRSAPrivateKey(RSAPrivateKey priKey) {
		AbsEncrypt.DEFAULT_RSA_PRIVATE_KEY = priKey;
	}

}
