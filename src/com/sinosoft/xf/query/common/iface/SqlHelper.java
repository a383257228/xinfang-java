package com.sinosoft.xf.query.common.iface;

import java.util.HashMap;
import java.util.Map;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.util.sql.SqlUtil;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 提供基本sql方法体,拼接sql供外部使用 sqlBodyQS和sqlBodyBW是大部分sql的语句体，可能添加where条件，可能添加 group
 * by统计条件， 查询均是在此基础上进行统计 目前主要针对单个统计项进行查询如信访类别、信访方式等
 * 
 * @author hjh
 * 
 * 2015-1-20下午03:28:51
 */
public abstract class SqlHelper {


	/**
	 * 无意义，不使用的字段。
	 */
	public static final String NON = "NON";
	/**
	 * 自定义时间范围
	 */
	public static final String CUSTOM = "CUSTOM";
	/**
	 * 上年同期（上年业务年年初至上年本月本天）
	 */
	public static final String LAST_YEAR = "LAST_YEAR";
	/**
	 * 上年（业务年全年）
	 */
	public static final String LAST_YEAR_BUSINESS = "LAST_YEAR_BUSINESS";
	/**
	 * 本年
	 */
	public static final String YEAR = "YEAR";
	/**
	 * 本月
	 */
	public static final String MONTH = "MONTH";
	/**
	 * 上月
	 */
	public static final String LAST_MONTH = "LAST_MONTH";
	/**
	 * 上年本月同期
	 */
	public static final String LAST_YEAR_MONTH = "LAST_YEAR_MONTH";
	/**
	 * 五年统计
	 */
	public static final String FIVE_YEARS = "FIVE_YEARS";

	

	/**
	 * 批转未接受的信访件过滤
	 * @author hjh
	 * 2015年4月24日
	 * @return
	 */
	public static String batchSql(){
		if(Constants.SH_REGION_CDOE.equals(SqlHelper.regionCode())){
			return " and ( (trans.batch_flag='0' or trans.batch_flag is null) or (trans.batch_flag='1' and trans.receive_date is not null)) ";
		}
		//下级信少人少，不需要加批转控制
		return "";
	}


	

	/**
	 * 获取regionCode参数
	 * 
	 * @author hjh
	 * @return 2015-1-22下午04:43:49
	 */
	public static String regionCode() {
		PersonInfo person = (PersonInfo) Struts2Utils
				.getSessionAttribute("personInfo");
		return person.getRegionCode();
//		 return "310000000000";
	}

	/**
	 * 占位符替换 pattern ${macro1}
	 * 
	 * @author hjh
	 * @param
	 * @return 2014-8-14下午08:24:48
	 */
	public static String replacePlaceholder(String pattern,
			Map<String, String> arguments) {
		String formatedStr = pattern;
		for (String key : arguments.keySet()) {
			formatedStr = formatedStr.replaceAll("\\$\\{" + key + "\\}",
					arguments.get(key).toString());
		}
		return formatedStr;
	}
	//by wangk 润乾报表本委信访情况数据比较中增加了BWWTDLTJN的数据集
	protected String wtdltjn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
