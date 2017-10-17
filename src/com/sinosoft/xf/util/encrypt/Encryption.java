package com.sinosoft.xf.util.encrypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.apache.ws.security.util.Base64;

/**
 * 该类用于加密时使用
 * 
 * @date 2012-02-29
 * @author oba
 */
public class Encryption {
	// 生成密钥的参数
	final static String keyStr = "oba";

	/**
	 * 加密
	 * 
	 * @param scr
	 *            要加密的字符串
	 * @param key
	 *            秘钥
	 * @return 密文
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static String encrypt(String scr, Key key) {
		Cipher cipher = null;
		String enCode = null;
		// 用KEY进行加密：
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] ciphertext = cipher.doFinal(scr.getBytes());
			enCode = new sun.misc.BASE64Encoder().encode(ciphertext);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return enCode;
	}

	/**
	 * 解密
	 * 
	 * @param scr
	 *            密文
	 * @param key
	 *            秘钥
	 * @return 解密后明文
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static String decrypt(String scr, Key key) {
		Cipher cipher = null;
		String deCode = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] b1 = new sun.misc.BASE64Decoder().decodeBuffer(scr);
			byte[] b = cipher.doFinal(b1);
			deCode = new String(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return deCode;
	}

	/**
	 * 生成密钥
	 * 
	 * @param key
	 *            制定字符串，初始化秘钥
	 * @return 密钥
	 * @throws NoSuchAlgorithmException
	 */
	public static Key getKey(String keyStr) {
		Key key = null;
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("DES");
			SecureRandom secrand = SecureRandom.getInstance("SHA1PRNG");
			secrand.setSeed(keyStr.getBytes()); // 初始化随机产生器
			keyGen.init(56, secrand); // 初始化密钥生成器
			key = keyGen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return key;
	}

	/**
	 * 加密
	 * 
	 * @param scr
	 *            要加密的字符串
	 * @author wangxx
	 * @return 密文
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static String encrypt(String scr) {
		Cipher cipher = null;
		String enCode = null;
		Key key = null;
		// 用KEY进行加密：
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("DES");
			SecureRandom secrand = SecureRandom.getInstance("SHA1PRNG");
			secrand.setSeed(keyStr.getBytes()); // 初始化随机产生器
			keyGen.init(56, secrand); // 初始化密钥生成器
			key = keyGen.generateKey();
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] ciphertext = cipher.doFinal(scr.getBytes());
			enCode = new sun.misc.BASE64Encoder().encode(ciphertext);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return enCode;
	}
	/**
	 * 加密
	 * 
	 * @param scr
	 *            要加密的字符串
	 * @author wangxx
	 * @return 密文
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static byte[] encrypt(String scr,String key1) {
		Cipher cipher = null;
		String enCode = null;
		Key key = null;
		// 用KEY进行加密：
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("DES");
			SecureRandom secrand = SecureRandom.getInstance("SHA1PRNG");
			secrand.setSeed(keyStr.getBytes()); // 初始化随机产生器
			keyGen.init(56, secrand); // 初始化密钥生成器
			key = keyGen.generateKey();
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] ciphertext = cipher.doFinal(scr.getBytes());
			enCode = new sun.misc.BASE64Encoder().encode(ciphertext);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return enCode.getBytes();
	}

	/**
	 * 解密
	 * 
	 * @param scr
	 *            密文
	 * @param key
	 *            秘钥
	 * @return 解密后明文
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static String decrypt(String scr) {
		Cipher cipher = null;
		String deCode = null;
		Key key = null;
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("DES");
			SecureRandom secrand = SecureRandom.getInstance("SHA1PRNG");
			secrand.setSeed(keyStr.getBytes()); // 初始化随机产生器
			keyGen.init(56, secrand); // 初始化密钥生成器
			key = keyGen.generateKey();
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] b1 = new sun.misc.BASE64Decoder().decodeBuffer(scr);
			byte[] b = cipher.doFinal(b1);
			deCode = new String(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return deCode;
	}
	/**
	 * 解密
	 * 
	 * @param scr
	 *            密文
	 * @param key
	 *            秘钥
	 * @return 解密后明文
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static String decrypt(byte[] bt) {
		Cipher cipher = null;
		String deCode = null;
		Key key = null;
		String scr = new String(bt);
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("DES");
			SecureRandom secrand = SecureRandom.getInstance("SHA1PRNG");
			secrand.setSeed(keyStr.getBytes()); // 初始化随机产生器
			keyGen.init(56, secrand); // 初始化密钥生成器
			key = keyGen.generateKey();
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] b1 = new sun.misc.BASE64Decoder().decodeBuffer(scr);
			byte[] b = cipher.doFinal(b1);
			deCode = new String(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return deCode;
	}

	/**
	 * @author wangxx 测试方法
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException,
			ClassNotFoundException {
		 String xx = encrypt("wangxx");
		 String oo = decrypt(xx);
		 System.out.println("**********经过解密之后的********"+oo);
		 Encryption e = new Encryption();
		 e.testFileEncrypt();
	}
	/**
	 * 测试对word文件流的加密解密算法
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void testFileEncrypt() throws IOException, ClassNotFoundException {
//		enCryptoFile("D:\\workspace\\hbbjw\\WebRoot\\document\\doc\\aa.doc",
//		 	"D:\\workspace\\hbbjw\\WebRoot\\document\\doc\\aa.doc");
		deCryptoFile("D:\\workspace\\hbbjw\\WebRoot\\document\\template\\abc20120301040348.doc",
			"D:\\workspace\\hbbjw\\WebRoot\\document\\template\\abc.doc");
	}

	/**
	 * 文件转化为字节数组
	 * @param file
	 * @return
	 */
	public static byte[] getBytesFromFile(File file) {
		byte[] ret = null;
		try {
			if (file == null) {
				return null;
			}
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
			byte[] b = new byte[4096];
			int n;
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}
			in.close();
			out.close();
			ret = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/*
	 * 加密文件 file1加密后产生file2密文
	 */
	public static boolean enCryptoFile(String file, String file2) {
		// 输入文件转换成为bytes
		File file_in = new File(file);
		byte[] filebyte = getBytesFromFile(file_in);
		// 定义输出文件
		File file_out = new File(file2);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file_out);
			String fileContext = Base64.encode(filebyte);
			out.write(fileContext.getBytes(), 0, fileContext.getBytes().length);
			out.close();
			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("File enCrypto Error!");
		}
		return false;
	}
	

	/**
	 * 解密文件
	 * 将file路径对应的文件读入进行加密并将机密后的内容输出到file2中
	 * @param file 需要加密文件路径
	 * @param file2 加密后的的文件路径
	 * @date 2012-03-01
	 * @author oba
	 * @return true成功，false失败
	 */
	public static boolean enCryptoFile(File fileIn,String file2) {
		// 输入文件转换成为bytes
		byte[] filebyte = getBytesFromFile(fileIn);
		// 定义输出文件
		File file_out = new File(file2);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file_out);
			String fileContext = Base64.encode(filebyte);
			out.write(fileContext.getBytes(), 0, fileContext.getBytes().length);
			out.close();
			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("File enCrypto Error!");
		}
		return false;
	}

	/**
	 * 解密文件
	 * 将file路径对应的文件读入进行解密并将机密后的内容输出到file2中
	 * @param file 需要解密文件路径
	 * @param file2 解密后的的文件路径
	 * @date 2012-03-01
	 * @author oba
	 * @return true成功，false失败
	 */
	public static boolean deCryptoFile(String file, String file2) {
		// 输入文件转换成为bytes
		File file_in = new File(file);
		byte[] filebyte = getBytesFromFile(file_in);
		String fileContext = new String(filebyte);
		// 定义输出文件
		File file_out = new File(file2);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file_out);
			filebyte = Base64.decode(fileContext);
			out.write(filebyte, 0, filebyte.length);
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("File deCrypto Error!");
			return false;
		}
	}
	
	/**
	 * 解密文件
	 * 将file路径下的文件读入并进行解密返回字节流
	 * @param file解密的文件路径
	 * @date 2012-03-01
	 * @author oba
	 * @return 字节流
	 */
	public static ByteArrayInputStream deCryptoFile(String file){
		// 输入文件转换成为bytes
		File file_in = new File(file);
		byte[] filebyte = getBytesFromFile(file_in);
		String fileContext = new String(filebyte);
		// 定义输出文件
		try {
			filebyte = Base64.decode(fileContext);
			ByteArrayInputStream in = new ByteArrayInputStream(filebyte,0,filebyte.length);
			return in;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("File deCrypto Error!");
			return null;
		}
	}
}
