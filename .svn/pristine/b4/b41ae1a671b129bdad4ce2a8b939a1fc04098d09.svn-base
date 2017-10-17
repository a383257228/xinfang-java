package com.sinosoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 将组件中的加密工具拿出调整
 * 
 * @author lijun
 * @date 2016年7月2日
 */
public class FileCrypterUtil {
	
	private static final String SECRET_KEY = "xfd!@#$%^&BNMJUYXFFxiaojubaooftF92A8FD8FEC2&F09db";
	
	
	/**
	 * 文件加密(使用默认秘钥)
	 * @param scrFile 被加密文件对象
	 * @param outPath 加密后文件全路径
	 * @param secretKey 秘钥
	 * @throws Exception
	 */
	public static void encryptByFile(File scrFile, String outPath) throws Exception {
		FileInputStream fis = new FileInputStream(scrFile);
		encryptByStream(fis, outPath);
	}
	
	/**
	 * 文件解密(使用默认秘钥)
	 * @param scrFile 被解密文件对象
	 * @param outPath 解密后文件全路径
	 * @param secretKey 秘钥
	 * @throws Exception
	 */
	public static void decryptByFile(File scrFile, String outPath) throws Exception {
		FileInputStream fis = new FileInputStream(scrFile);
		decryptByStream(fis, outPath);
	}
	
	
	/**
	 * 文件加密(使用默认秘钥)
	 * @param fis 文件输入流
	 * @param outPath 输出文件全路径
	 * @param secretKey 秘钥
	 * @throws Exception
	 */
	public static void encryptByStream(InputStream fis, String outPath) throws Exception {
		crypt(fis, getOutputStream(outPath), getCipher(SECRET_KEY, Cipher.ENCRYPT_MODE));
	}
	
	/**
	 * 文件解密(使用默认秘钥)
	 * @param is 文件输入流
	 * @param outFilePath 输出文件路径
	 * @param secretKey 秘钥
	 * @throws Exception
	 */
	public static void decryptByStream(InputStream is, String outPath) throws Exception {
		crypt(is, getOutputStream(outPath), getCipher(SECRET_KEY, Cipher.DECRYPT_MODE));
	}
	/**
	 * 文件加密
	 * @param scrFile 被加密文件对象
	 * @param outPath 加密后文件全路径
	 * @param secretKey 秘钥
	 * @throws Exception
	 */
	public static void encryptByFile(File scrFile, String outPath, String secretKey) throws Exception {
		FileInputStream fis = new FileInputStream(scrFile);
		encryptByStream(fis, outPath, secretKey);
	}
	
	/**
	 * 文件解密
	 * @param scrFile 被解密文件对象
	 * @param outPath 解密后文件全路径
	 * @param secretKey 秘钥
	 * @throws Exception
	 */
	public static void decryptByFile(File scrFile, String outPath, String secretKey) throws Exception {
		FileInputStream fis = new FileInputStream(scrFile);
		decryptByStream(fis, outPath, secretKey);
	}
	

	/**
	 * 文件加密
	 * @param fis 文件输入流
	 * @param outPath 输出文件全路径
	 * @param secretKey 秘钥
	 * @throws Exception
	 */
	public static void encryptByStream(InputStream fis, String outPath, String secretKey) throws Exception {
		crypt(fis, getOutputStream(outPath), getCipher(secretKey, Cipher.ENCRYPT_MODE));
	}
	
	/**
	 * 文件解密
	 * @param is 文件输入流
	 * @param outFilePath 输出文件路径
	 * @param secretKey 秘钥
	 * @throws Exception
	 */
	public static void decryptByStream(InputStream is, String outPath, String secretKey) throws Exception {
		
		crypt(is, getOutputStream(outPath), getCipher(secretKey, Cipher.DECRYPT_MODE));
	}
	
	/**
	 * 传入被解密文件的流，通过输出流直接写出
	 * @param is
	 * @param os
	 * @param secretKey
	 * @throws Exception
	 */
	public static void decryptAndWriteByOutputStream(InputStream is, OutputStream os, String secretKey) throws Exception {
		crypt(is, os, getCipher(secretKey, Cipher.DECRYPT_MODE));
	}
	
	/**
	 * 传入被解密文件的流，通过输出流直接写出(使用默认秘钥)
	 * @param is
	 * @param os
	 * @param secretKey
	 * @throws Exception
	 */
	public static void decryptAndWriteByOutputStream(InputStream is, OutputStream os) throws Exception {
		crypt(is, os, getCipher(SECRET_KEY, Cipher.DECRYPT_MODE));
	}
	/**
	 * 传入被解密文件对象，通过输出流直接写出
	 * @param is
	 * @param os
	 * @param secretKey
	 * @throws Exception
	 */
	public static void decryptAndWriteByOutputStream(File srcFile, OutputStream os, String secretKey) throws Exception {
		FileInputStream fis = new FileInputStream(srcFile);
		crypt(fis, os, getCipher(secretKey, Cipher.DECRYPT_MODE));
	}
	
	/**
	 * 传入被解密文件对象，通过输出流直接写出(使用默认秘钥)
	 * @param is
	 * @param os
	 * @param secretKey
	 * @throws Exception
	 */
	public static void decryptAndWriteByOutputStream(File srcFile, OutputStream os) throws Exception {
		FileInputStream fis = new FileInputStream(srcFile);
		crypt(fis, os, getCipher(SECRET_KEY, Cipher.DECRYPT_MODE));
	}
	
	/**
	 * 通过输出文件路径获取输出流
	 * @param outPath
	 * @return
	 * @throws FileNotFoundException
	 */
	private static OutputStream getOutputStream(String outPath) throws FileNotFoundException{
		File outFile = new File(outPath);
		File pathFile = outFile.getParentFile();
		if (!pathFile.exists())
			pathFile.mkdirs();
		FileOutputStream os = new FileOutputStream(outFile);
		return os;
	}
	
	/**
	 * 获取加密解密所需的Cipher对象
	 * @param secretKey 秘钥
	 * @param cipherType 加密解密类型
	 * @return
	 * @throws Exception
	 */
	private static Cipher getCipher(String secretKey,int cipherType) throws Exception {
		String pass1 = secretKey.substring(4, 6);
		String pass2 = secretKey.substring(8, 10);
		String pass3 = secretKey.substring(5);
		String miyao = md5s(pass1) +md5s(pass2) + md5s(pass3);
		DESKeySpec desKS = new DESKeySpec(getKeyByStr(miyao.substring(0, 16)));
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(desKS);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(cipherType, sk);
		return cipher;
	}

	/**
	 * 将组件中的加密工具方法中的加密方法拿出调整 加密时传入输入流，输出路径，输出文件名
	 * 
	 * @param fis
	 * @param outPath
	 * @param outFileName
	 */
	private static void crypt(InputStream fis, OutputStream os, Cipher cipher) throws Exception {
		try {
			int blockSize = cipher.getBlockSize() * 1280;
	        int outputSize = cipher.getOutputSize(blockSize);  
			int inLength=0;
			byte[] inBytes = new byte[blockSize];
			byte[] outBytes = new byte[outputSize];
			boolean more = true;  
	        while (more) {
	            inLength = fis.read(inBytes);  
	            while(inLength<blockSize){
	            	int l = fis.read(inBytes, inLength, blockSize - inLength);
	            	if(l<=0){
	            		break;
	            	}
	            	inLength += l;
	            }
	            if (inLength == blockSize) {  
	                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);  
	                os.write(outBytes, 0, outLength);  
	            } else {  
	                more = false;  
	            }  
	        }  
	        if (inLength > 0)  
	            outBytes = cipher.doFinal(inBytes, 0, inLength);  
	        else  
	            outBytes = cipher.doFinal();  
	        os.write(outBytes);  
	        os.flush();
	        fis.close();
	        os.close();
		} finally {
			if (os != null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	private static byte[] getKeyByStr(String str) {
		byte[] bRet = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; ++i) {
			Integer itg = new Integer(16 * getChrInt(str.charAt(2 * i))
					+ getChrInt(str.charAt(2 * i + 1)));
			bRet[i] = itg.byteValue();
		}
		return bRet;
	}

	private static int getChrInt(char chr) {
		int iRet = 0;
		if (chr == "0".charAt(0))
			iRet = 0;
		if (chr == "1".charAt(0))
			iRet = 1;
		if (chr == "2".charAt(0))
			iRet = 2;
		if (chr == "3".charAt(0))
			iRet = 3;
		if (chr == "4".charAt(0))
			iRet = 4;
		if (chr == "5".charAt(0))
			iRet = 5;
		if (chr == "6".charAt(0))
			iRet = 6;
		if (chr == "7".charAt(0))
			iRet = 7;
		if (chr == "8".charAt(0))
			iRet = 8;
		if (chr == "9".charAt(0))
			iRet = 9;
		if (chr == "A".charAt(0))
			iRet = 10;
		if (chr == "B".charAt(0))
			iRet = 11;
		if (chr == "C".charAt(0))
			iRet = 12;
		if (chr == "D".charAt(0))
			iRet = 13;
		if (chr == "E".charAt(0))
			iRet = 14;
		if (chr == "F".charAt(0))
			iRet = 15;
		return iRet;
	}

	private static String md5s(String plainText) {
		String str = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte[] b = md.digest();

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; ++offset) {
				int i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			str = buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 自动随机生成加密解密秘钥
	 * @return 
	 * @return
	 */
	public static String getSecretKey(){
		StringBuilder builder = new StringBuilder(12);
	    for(int j=0;j<12;j++){
	    	int r = (int) (Math.random()*2);
	     	int rn1=(int)(48+Math.random()*10);
	     	int rn2=(int)(97+Math.random()*26);
	     	switch(r){
	     		case 0:   
		            builder.append((char)rn1);
		            break;
	     		case 1:
					builder.append((char)rn2);
					break;
     		}
    	}
	    return builder.toString();
	}
}
