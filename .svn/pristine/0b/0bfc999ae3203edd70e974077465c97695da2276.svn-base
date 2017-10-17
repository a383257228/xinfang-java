package com.sinosoft.xf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * DES证书证书加解密算法
 * @author  HJM
 * @createDate 2011-9-7
 */
public class DESUtil {
	protected static final Logger log = LoggerFactory.getLogger(DESUtil.class);
	
	public static void main(String[] args) {
		try {
//			String path = "D:\\workspace\\zjw_p2\\WebRoot\\key\\000000.key";
//			generateKey("350000","d:\\aaa.key");
//			System.out.println(encrypt("12345!@#$%^&*()_+?><发动机撒开发了觉得", path));
//			System.out.println(decrypt("Qw6RGScP97eb48DJx3qfA/ejG5ts8zuST5MsMvRZBjX1HiSNOtlIZMPL8vQMu1v6", path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据证书加密字符串
	 * 
	 * @param data
	 *            需要加密的字符串
	 * @param filePath
	 *            证书路径
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws GeneralSecurityException
	 */
	public static String encrypt(String data, String filePath) throws Exception {
		ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(new File(filePath)));
		int mode = Cipher.ENCRYPT_MODE;
		Key key = (Key) keyIn.readObject();
		keyIn.close();
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(mode, key);
		byte[] outBytes = cipher.doFinal(data.getBytes());
		BASE64Encoder base64Encoder = new BASE64Encoder();
		String en= base64Encoder.encode(outBytes);
		return en;
	}

	/**
	 * 根据证书解密字符串
	 * 
	 * @param data
	 *            需要解密的字符串
	 * @param filePath
	 *            证书路径
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data, String filePath) throws Exception {
		ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(new File(filePath)));
		int mode = Cipher.DECRYPT_MODE;
		Key key = (Key) keyIn.readObject();
		keyIn.close();
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(mode, key);
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte[] pasByte = cipher.doFinal(base64Decoder.decodeBuffer(data));
		String de = new String(pasByte, "UTF-8");
		return de;
	}
	/**
	 * 生成新的DES证书
	 * @param XFSregion 所属区域编码
	 * @param path   证书路径
	 * @throws Exception
	 */
	public static void generateKey(String XFSregion,String path) throws Exception {
		KeyGenerator keygen = KeyGenerator.getInstance("DES");
		SecureRandom random = new SecureRandom(XFSregion.getBytes());
		keygen.init(random);
		SecretKey key = keygen.generateKey();
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
		out.writeObject(key);
		out.close();
	}
}