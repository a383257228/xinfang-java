package com.sinosoft.xf.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>Title: PropertiesUtils.java</p>
 * <p>Description:读取配置文件的工具类</p>
 * @author Dustin
 */
public class PropertiesUtils {
	/**
	 * @param fileName properties文件名.
	 * @param key properties 文件中对应的key.
	 * @return String 返回相应的key对应的value的值.
	 * @throws IOException 如果找不到properties文件则抛出该异常.
	 */
	public static String getPropertiesValue(final String fileName,final String key)
			throws IOException {
		final Properties properties = new Properties();
		final InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(fileName+".properties");
		// 根据文件路径实例化输入流
		properties.load(inputStream);
		return properties.getProperty(key);
	}
}
