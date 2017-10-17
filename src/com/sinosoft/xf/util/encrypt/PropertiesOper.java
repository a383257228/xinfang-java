package com.sinosoft.xf.util.encrypt;


import java.io.InputStream;
import java.util.Properties;


/**
 * Title: �����ļ�������
 * Description:
 * @author WEIQIAN
 * @date 2006-4-24
 */
public class PropertiesOper {

	private static Properties props =  new Properties();

	private static Properties getInstance(){
		return props;
	}
	
	/**
	 * ��ȡ�ļ���Properties
	 * @param PFILE �ļ�ʵ��
	 * @return Properties
	 */
	public static Properties read(InputStream fis){
		try 
		{ 
			getInstance();
			props.load(fis);
			return props;
		} 
		catch(Exception e) 
		{ 
			e.printStackTrace();
			return null;
		} 
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
