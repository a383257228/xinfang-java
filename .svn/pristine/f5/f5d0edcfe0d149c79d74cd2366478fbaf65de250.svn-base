package com.sinosoft.xf.util.encrypt;


import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


/**
 * Title: 加密类
 * Description: 通过base64的apache实现进行对字符串的加密解密
 * @author WEIQIAN
 * @date 2006-7-3
 */
public class Encrypt {

	public static Set fieldSet;

	public Encrypt() {
		if(fieldSet==null){
		Properties prop = PropertiesOper.read(PropertiesOper.class.
                getClassLoader().
                getResourceAsStream("encrypt.properties"));
		fieldSet = prop.keySet();
		}
	}

	public static String encryptOld(String src){
		if(src==null)
			return null;
		else if ("".equals(src))
			return "";
		return Base64.encode(src.getBytes()).trim();
	}

	public static String encrypt(String src){
		if(src==null)
			return null;
		else if ("".equals(src))
			return "";

		StringBuffer ret = new StringBuffer();
		try {
			byte[] b = src.getBytes("GBK");

			for(int i=0;i<b.length;i++)
				ret.append(byteToHex(b[i]));

			return ret.toString();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return null;
		}

	}

	public static String decrypt(String src){
		if(src==null)
			return null;
		else if ("".equals(src))
			return "";
		try{
			byte[] b = new byte[src.length()/2];
			for(int i=0;i<src.length();i+=2){
				String s = src.substring(i,i+2);
				b[i/2] = hexToByte(s);
			}


			return new String(b,"GBK");
		}catch(Exception ex){
			return src;
		}
	}


	public static String decryptOld(String src){
		String ret ;
		if(src==null)
			return null;
		else if ("".equals(src))
			return "";
		try{
			ret = new String(Base64.decode(src));
		}catch(Exception ex){
			return src;
		}
		return ret;
	}
        public String decryptColumn(String colName,String colValue){
            String ret = colValue;
            Iterator i = fieldSet.iterator();
            while(i.hasNext()){
                String fieldName = i.next().toString();
                if (colName.trim().endsWith(fieldName.trim())){
                    ret = decrypt(colValue);
                    break;
                }
            }
            return ret;
        }

	//ExeSql中的where部分会出现需要加密的字段
	public String encryptSql(String srcSql){
		String retStr = srcSql ;
		Iterator i = fieldSet.iterator();
		while(i.hasNext()){
			String fieldName = i.next().toString();
			retStr = replaceEncryptStr(retStr,fieldName);
		}
		return retStr;
	}
//	孙哲修改　替换单引号 2009-3-14
	private String replaceEncryptStr(String srcSql,String fieldName){
		StringBuffer ret = new StringBuffer();
		int index = srcSql.toLowerCase().indexOf(fieldName);
		String temp = "";
		while(index!=-1){
			String before = srcSql.substring(0,index+fieldName.length());
			ret.append(before);
			srcSql = srcSql.substring(index+fieldName.length());
			if(srcSql.replace(" ", "").startsWith("='")||srcSql.replace(" ", "").startsWith("!='")||srcSql.replace(" ", "").startsWith("<>'")){
				before = srcSql.substring(0,srcSql.indexOf("'"));
				ret.append(before);
				srcSql = srcSql.substring(srcSql.indexOf("'")+1);
				String encryptStr = encrypt(srcSql.substring(0,srcSql.indexOf("'")));
				srcSql = srcSql.substring(srcSql.indexOf("'")+1);
				ret.append("'");
				ret.append(encryptStr);
				ret.append("'");
			}
			else if(srcSql.replace(" ", "").startsWith("like'%")){
				before = srcSql.substring(0,srcSql.indexOf("%"));
				ret.append(before);
				srcSql = srcSql.substring(srcSql.indexOf("%")+1);
				String encryptStr = encrypt(srcSql.substring(0,srcSql.indexOf("'")));
				srcSql = srcSql.substring(srcSql.indexOf("%")+1);
				ret.append("");
				ret.append("%"+encryptStr.substring(0,(encryptStr.length()-2))+"%");
				ret.append("");
			}
			else if(srcSql.replace(" ", "").startsWith("like'")){
				before = srcSql.substring(0,srcSql.indexOf("'"));
				ret.append(before);
				srcSql = srcSql.substring(srcSql.indexOf("'")+1);
//				孙哲修改　替换单引号 2009-3-14
				temp = srcSql.substring(0,srcSql.indexOf("%"));
				temp = temp.replaceAll("'", "‘");
				srcSql = temp+srcSql.substring(srcSql.indexOf("%"));
				String encryptStr = encrypt(srcSql.substring(0,srcSql.indexOf("%")));
				srcSql = srcSql.substring(srcSql.indexOf("'")+1);
				ret.append("'");
				ret.append(encryptStr);
				ret.append("%'");
			}
			index = srcSql.toLowerCase().indexOf(fieldName);
		}
		ret.append(srcSql);
		return ret.toString();
	}


	static public String byteToHex(byte b) {
	      // Returns hex String representation of byte b
	      char hexDigit[] = {
	         '0', '1', '2', '3', '4', '5', '6', '7',
	         '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	      };
	      char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
	      return new String(array);
	   }


	static public byte hexToByte(String s) {
	      // Returns hex String representation of byte b
	      char hexDigit[] = {
	         '0', '1', '2', '3', '4', '5', '6', '7',
	         '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	      };

	      byte lo =0;
	      byte hi =0;

	      for(int i=0;i<hexDigit.length;i++){
	    	  if(hexDigit[i]==s.charAt(0))
	    		  hi = (byte) ((byte) i*16);
	    	  if(hexDigit[i]==s.charAt(1))
	    		  lo = (byte) i;
	      }

	      return (byte) (lo+hi);

	   }

	public static void main(String[] args)
    {
		//System.out.println(new Encrypt().encrypt("192.168.71.66:6888"));
		System.out.println(encrypt("中央纪委信访室"));
		System.out.println(decrypt("d6d0d1ebbccdceafd0c5b7c3cad2"));
    }

}

