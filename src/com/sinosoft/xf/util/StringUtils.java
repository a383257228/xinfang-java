package com.sinosoft.xf.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 关于字符串的一些公用方法类
 */
public class StringUtils {
	public static boolean isBlank(String str) {
		if (str == null||"".equals(str)||"null".equals(str))
			return true;
//		for (int i = 0; i < str.length(); i++) {
//			if (!Character.isWhitespace(str.charAt(i))) {
//				return false;
//			}
//		}
		return false;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 清除换行、空格等符
	 * @author lijun 2014-5-23
	 * @param str  要清除的字符串
	 * @return  清除后的字符串
	 */
	public static String deleteNewLine(String str){
		if(null!=str){
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			str = m.replaceAll("");
		}
		return str;
	}
	/**
	 * 替换回车为<br/>
	 * @param str
	 * @return
	 */
	public static String replaceEnter(String str) {
		if(str==null||str.equals(""))
			return str;
		Pattern p = Pattern.compile("\\r\n|\n");
		Matcher m = p.matcher(str);
		return m.replaceAll("<br>");
	}

	/**
	 * 将字符串补数,将sourString的前面用cChar补足cLen长度的字符串,如果字符串超长，则不做处理
	 * 
	 * @date 2011-09-15
	 * @author wangxx 被哪些方法调用： 1、PetitionMaxNoInfoManager.getMaxPetitionNo
	 *         获取当前最大业务信访编号 1、PetitionMaxNoInfoManager.getCurrPetitionNo
	 *         获取当前最大显示信访编号 3、PetitionMaxNoInfoManager.getMaxNo 获得当前制定格式最大号
	 * @param sourString
	 *            源字符串
	 * @param cChar
	 *            补数用的字符
	 * @param cLen
	 *            字符串的目标长度
	 * @return 字符串
	 */
	public static String toLengthString(final String sourString, final String cChar, final int cLen) {
		int tLen = sourString.length();
		StringBuffer tReturn = new StringBuffer(cLen);
		if (tLen >= cLen) {
			tReturn.append(sourString);
		} else {
			int iMax = cLen - tLen;
			for (int i = 0; i < iMax; i++) {
				tReturn.append(cChar);
			}
			tReturn.append(sourString);
		}
		return tReturn.toString();
	}

	/**
	 * 取字符串,sourString为空，或者长度小于subLen原样返回，否则截取subLen长
	 * 
	 * @param sourString
	 *            源字符串
	 * @param subLen
	 *            取字符串长度
	 * @return 字符串
	 */
	public static String toSubString(String value, final int subLen) {
		if(StringUtils.isBlank(value))
			return value;
		else if(value.length()>subLen)
			return value.substring(0, subLen);
		return value;
	}
	/**
	 * 替换接收js中的敏感字符
	 * @param value
	 * @return
	 */
	public static String scriptingFilter(String value,String [] notParams) {
		if (value == null) {
			return null;   
		}
		String [] params={"<",">","\"","\'",";","(",")","&","+","=","|"};
		for (String string : params) {
			if(notParams!=null){
				for (String notString : notParams) {
					if(!notString.equals(string)){
						if(value.contains(string)){
							return null;
						}
					}
				}
			}else {
				if(value.contains(string)){
					return null;
				}
			}
		}
		return value;
	}
	/**
	 * 校验接收js中的敏感字符
	 * @param value
	 * @return
	 */
	public static boolean scriptingFilterBoolean(String value,String [] notParams) {
		if (value == null) {
			return false;   
		}
		String [] params={"<",">","\"","\'",";","(",")","&","+","=","|"};
		for (String string : params) {
			if(notParams!=null){
				for (String notString : notParams) {
					if(!notString.equals(string)){
						if(value.contains(string)){
							return false;
						}
					}
				}
			}else {
				if(value.contains(string)){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 替换接收js中关于sort的敏感字符,sort如果不合法直接返回null
	 * @param value
	 * @return
	 */
	public static String [] sortParams=
		{"docTypeName","docLetterNum","docLetterText","currPetitionNo","petitionNo","petitionDate"
		,"petitionTypeName","petitionSourceName","petitionClassName","XFPRTNO","XFDATE","REGIONNAME"
		,"OBJECTCLASSNAME","TOREGIONNAME","INPUTDATE","SENDDATE","ZFTYPECODE","ISSEND","ISRECEIVE"
		,"ALARMACTIVE","issueRegionName","dealTypeName","letterNo","reportDate","operateDate","consultDate"
		,"consultName","docTypeCode","tempDefineInfo.tempStyle","docName","title","docRelationInfo.currPetitionNo"
		,"creatorName","itemName","itemTableCname","validFlag","tempStyle","letterFlag","startTime"
		,"endTime","code","name","createDate"};
	public static String scriptingSortFilter(String value) {
		if (value == null) {
			return null;   
		}
		if(value.contains(",")){
			String[] strValue=value.split(",");
			int bool=0;
			for (String string : sortParams) {
				for (String sValue : strValue) {
					if(sValue.equalsIgnoreCase(string)){
						bool++;
					}
				}
			}
			if(bool==strValue.length){
				return value;
			}else {
				return null;
			}
		}else{
			for (String string : sortParams) {
				if(value.equalsIgnoreCase(string)){
					return value;
				}
			}
		}
		return null;
	}
}
