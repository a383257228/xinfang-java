package com.sinosoft.xf.util.encrypt;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.crypto.Cipher;

import javax.crypto.SecretKey;

import javax.crypto.SecretKeyFactory;

import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import sun.misc.BASE64Decoder;

import sun.misc.BASE64Encoder;

/**
 * 加密解密类,加密方法getEncryptedString([String key,]String
 * input),解密方法getDecryptedString([String key,]String input)
 * 
 * @author zdy
 * @time 2014-2-17
 * @example
 */
public class Crypt {

	public static Logger logger = Logger.getLogger(Crypt.class.getName());

	public static String KEYSTRING = "sinosoft-yunwei-key";

	// --------------------------------------------------------

	// 获得密钥

	public static SecretKey getKey(String s) throws Exception {

		// s ="g8TlgLEc6oqZxdwGe6pDiKB8Y";

		// System.out.println("s==" + s);

		char[] ss = s.toCharArray();

		String sss = "";

		for (int i = 0; i < ss.length; i = i + 2) {

			sss = sss + ss[i];

		}

		SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");

		DESKeySpec ks = new DESKeySpec(sss.substring(0, 8).getBytes());

		SecretKey kd = kf.generateSecret(ks);

		return kd;

	}

	// --------------------------------------------------------------

	// 返回加密后的字符串

	// key是用于生成密钥的字符串，input是要加密的字符串

	public static String getEncryptedString(String key, String input) {

		String base64 = "";

		try {

			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			cipher.init(Cipher.ENCRYPT_MODE, getKey(key));

			// System.out.print("getKey(key)===" + getKey(key) + "key==" + key);

			byte[] inputBytes = input.getBytes();

			byte[] outputBytes = cipher.doFinal(inputBytes);

			BASE64Encoder encoder = new BASE64Encoder();

			base64 = encoder.encode(outputBytes);

		} catch (Exception e) {

			base64 = e.getMessage();

			logger.debug("加密出错：" + e.getMessage());

		}

		return base64;

	}

	// --------------------------------------------------------------

	// 返回解密后的字符串

	// key是用于生成密钥的字符串，input是要解密的字符串

	public static String getDecryptedString(String key, String input) {

		String result = null;

		try {

			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			cipher.init(Cipher.DECRYPT_MODE, getKey(key));

			BASE64Decoder decoder = new BASE64Decoder();

			byte[] raw = decoder.decodeBuffer(input);

			byte[] stringBytes = cipher.doFinal(raw);

			result = new String(stringBytes, "UTF8");

		} catch (Exception e) {

			result = e.getMessage();

			logger.debug("解密出错：" + e.getMessage());

		}

		return result;

	}

	public static String getKeyByResource() {

		// String str = ApplicationResource.getValueByKey("password.key");
		// //从配置文件中获取202cb962ac59075b964b07152d234b70

		String str = "";

		if (str != null && !str.equals("")) {

			return str;

		} else {

			return KEYSTRING;

		}

	}

	/**
	 * 
	 * 加密
	 * 
	 * @param input
	 * 加密前的字符串
	 * 
	 * @return
	 */

	public static String getEncryptedString(String input) {

		return getEncryptedString(getKeyByResource(), input);

	}

	/**
	 * 
	 * 解密
	 * 
	 * @param input
	 *            加密后的字符串
	 * 
	 * @return
	 */

	public static String getDecryptedString(String input) {

		return getDecryptedString(getKeyByResource(), input);

	}
	
	
	/**
	 * 
	 * @param filePath 文件路径,绝对路径
	 * @return map,存放properties内容
	 * @throws Exception
	 */
	public static Map<String, String> getPropertiesByAbsolutePath(String filePath) throws Exception{
		
		Properties properties = new Properties();
		Map<String, String> map = new HashMap<String, String>();
		
		properties.load(new FileInputStream(filePath));
		
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		
		for (Entry<Object, Object> entry : entrySet) {
			map.put((String) entry.getKey(), Crypt.getDecryptedString((String) entry.getValue()));
		}
		
		return map;
	}
	/**
	 * 将项目路径配置到server.xml的方式部署项目，此情下获取资源文件。资源文件需放在resource下
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> getPropertiesByRelativePath1(String fileName) throws Exception{
		
		String path = ServletActionContext.getServletContext().getRealPath("");
		path = path.substring(0, path.lastIndexOf("\\"));
		path = path+"\\resource\\"+fileName;
		Properties properties = new Properties();
		Map<String, String> map = new HashMap<String, String>();
		
		properties.load(new FileInputStream(path));
		
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		
		for (Entry<Object, Object> entry : entrySet) {
			map.put((String) entry.getKey(), Crypt.getDecryptedString((String) entry.getValue()));
		}
		
		return map;
	}
	/**
	 * 项目部署到Tomcat后利用相对路径获取资源文件，资源文件需放在src或resource下
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getPropertiesByRelativePath2(String fileName) throws Exception{
		Properties properties = new Properties();
		Map<String, String> map = new HashMap<String, String>();
		InputStream in = ServletActionContext.getServletContext().getResourceAsStream("/WEB-INF/classes/"+fileName);
		properties.load(in);
		
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		
		for (Entry<Object, Object> entry : entrySet) {
			map.put((String) entry.getKey(), Crypt.getDecryptedString((String) entry.getValue()));
		}
		
		return map;
	}
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Crypt c = new Crypt();

		System.out.println("cityCode"+c.getEncryptedString("310000000000"));//加密
		System.out.println("areaCode"+c.getEncryptedString("310100000000,313000000000,310400000000,310500000000,310600000000,310600000000" +
				",310800000000,310900000000,311000000000,311200000000,311300000000,,311400000000,311500000000,311600000000,311700000000" +
				",311800000000,312000000000,31AB00000000,31AA00000000,31AC00000000,31AD00000000,31AE00000000,31AF00000000,31AH00000000" +
				",31AI00000000,31AJ00000000,31AK00000000,31AL00000000,31AM00000000,31AN00000000,31AO00000000,31AP00000000,31AQ00000000" +
				",31AS00000000,31AT00000000,31AU00000000,31AV00000000,31AW00000000,31AX00000000,31AZ00000000,31BA00000000,31BB00000000" +
				",31BC00000000,31BD00000000,31BE00000000,31BF00000000,31BG00000000,31BH00000000,31BI00000000,31BJ00000000,31BK00000000" +
				",31BL00000000,31BM00000000,31BN00000000,31BO00000000,31BP00000000,31BQ00000000,31BR00000000,31BS00000000,31BT00000000" +
				",31BU00000000,31BV00000000,31DD00000000"));//加密
		System.out.println("regionname" +c.getEncryptedString("上海市"));//加密
		System.out.println(c.getEncryptedString("信访室"));//加密
		System.out.println(c.getEncryptedString("127.0.0.1:80"));//加密
		System.out.println(12);//加密
//		System.out.println(c.getDecryptedString("nB1LvujMsGQ="));//解密

	}
}
