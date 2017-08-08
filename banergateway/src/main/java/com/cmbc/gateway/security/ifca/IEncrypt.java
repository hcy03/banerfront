package com.cmbc.gateway.security.ifca;



public interface IEncrypt {
//	byte[] DEFAULT_AES_KEY = {69, -35, -53, -45, 23, 106, 60, 49, -40, 58, 62, -75, 96, -9, 12, 101};
/*//	
//	String DEFAULT_PASSWORD_KEY = "Ruimin.gd.Ocean's Fourteen.DWMNTH2CJFLCWL";
//	byte[] DEFAULT_DESEDE_KEY= {-92, 73, -57, 35, 56, -56, -88, 4, 84, 19, 49, 74, -3, -14, 11, 2, 21, 124, 42, 22, -29, 87, 117, -32};
//	byte[] DEFAULT_DESEDE_IV= {-56, -88, -14, 11, 22, -29, 87, -32};
//	
//	byte[] DEFAULT_PBE_SALT= {-40, 58, 62, -75,-56, -88,-29, 87};
*/	
	/**
	 * 加密
	 * @param plaintext
	 * @return
	 * @throws Exception
	 */
//	public String encrypt(String plaintext) throws Exception;
//	/**
//	 * 解密
//	 * @param ciphertext
//	 * @return
//	 * @throws Exception
//	 */
//	public String decrypt(String ciphertext) throws Exception;
	/**
	 *  加密
	 * @param plaintext
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public  String encrypt(String plaintext,Object key)  throws Exception;
	/**
	 * 解密
	 * @param ciphertext
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public  String decrypt(String ciphertext,Object key) throws Exception;
	
	public String getEncCodeing();
	
	public void setEncCodeing(String encoding);
	
	public String getEncryptName();
	
//	public int getKeyLen();
	
}
