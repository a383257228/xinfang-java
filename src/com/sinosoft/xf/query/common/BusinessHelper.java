package com.sinosoft.xf.query.common;

import org.apache.commons.lang.StringUtils;

/**
 * 专用于业务数据处理的方法
 * @author hjh
 *
 * 2014-9-15上午11:38:49
 */
public class BusinessHelper {

	//中央、省、市、区县
	public static final int ZHONG_YANG = 0;
	public static final int SHENG = 2;
	public static final int SHI = 4;
	public static final int QU_XIAN = 6;
	
	/**
	 * 判别regioncode所属的行政级别
	 * @author hjh
	 * @param regionCode
	 * @return
	 * 2014-9-30下午01:00:38
	 */
	public static Integer gainAdminiLevel(String regionCode){
		if(StringUtils.isNotBlank(regionCode)){
			if(regionCode.equals("000000000000"))
				return ZHONG_YANG;
			else if(regionCode.substring(2).equals("0000000000"))
				return SHENG;
			else if(regionCode.substring(4).equals("00000000"))
				return SHI;
			else if(regionCode.substring(6).equals("000000"))
				return QU_XIAN;
		}
		throw new RuntimeException("region_code非法：" + regionCode);
	}
	
	/**
	 * 返回regionCode的like字符串 如：'31%'
	 * @author hjh
	 * @param regionCode
	 * @return
	 * 2014-12-22上午11:25:46
	 */
	public static String regionLike(String regionCode){
//		int levl = BusinessHelper.gainAdminiLevel(regionCode);
		String likeStr = " '"+regionLikeValue(regionCode)+"' ";
		return likeStr;
	}
	/**
	 * 返回regionCode的like字符串 如：31%
	 * @author hjh
	 * @param regionCode
	 * @return
	 * 2014-12-22上午11:25:46
	 */
	public static String regionLikeValue(String regionCode){
		int levl = BusinessHelper.gainAdminiLevel(regionCode);
		String likeStr = regionCode.substring(0, levl)+"%";
		return likeStr;
	}
	
}
