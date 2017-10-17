package com.sinosoft.xf.util.encrypt;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder; 
/**
 * 
 * @author lirb
 * @Create 2010-06-07 该类用于加密时使用
 * 
 */
public final class UnifyEncript {
	// 生成密钥的参数
	private final String keyStr = "sinosoftUnifiedUser";
	
	Key key;
	/**
	 * 生成密钥的方法
	 * @author li
	 * @date 2013-6-9
	 * @param strKey  要加密的字符
	 */
	public void setKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			// 防止linux下 随机生成key
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(strKey.getBytes());
			_generator.init(56, secureRandom);
			this.key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		}
	}
	
	public UnifyEncript() {
		super();
		this.setKey(this.keyStr);
	}
	/**
	 * 加密方法，返回加密的字符串
	 * @author zl
	 * @date 2013-6-9
	 * @param strMing
	 * @return
	 * @return String
	 */
	public String encript(String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("UTF8");
			byteMi = this.encryptByte(byteMing);
			strMi = base64en.encode(byteMi);
			strMi = strMi.replaceAll(" ", "%20");
			strMi = strMi.replaceAll("\\+", "%2B");
			strMi = strMi.replaceAll("/", "%2F");
			strMi = strMi.replaceAll("=", "%3D");
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}

		strMi = strMi.replaceAll(" ", "%20");
		strMi = strMi.replaceAll("\\+", "%2B");
		strMi = strMi.replaceAll("/", "%2F");
		strMi = strMi.replaceAll("=", "%3D");
		return strMi.replaceAll("\r\n","");
	}
	/**
	 * 加密以 byte[] 明文输入 ,byte[] 密文输出
	 * @author zl
	 * @date 2013-6-9
	 * @param byteS
	 * @return
	 */
	private byte[] encryptByte(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
	 * @author zl
	 * @date 2013-6-9
	 * @param byteD
	 * @return
	 * @return byte[]
	 */
	private byte[] decryptByte(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密 以 String 密文输入 ,String 明文输出
	 * @author zl
	 * @date 2013-6-9
	 * @param strMi
	 * @return
	 * @return String
	 */
	public String descript(String strMi) {
		
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
//			strMi = strMi.replaceAll("%20", " ");
//			strMi = strMi.replaceAll("%2B", "+");
//			strMi = strMi.replaceAll("%2F", "/");
//			strMi = strMi.replaceAll("%3D", "=");
			byteMi = base64De.decodeBuffer(strMi);
			byteMing = this.decryptByte(byteMi);
			strMing = new String(byteMing, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}
	
}
