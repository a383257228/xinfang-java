package com.sinosoft.xf.query.common;

import java.util.HashMap;
import java.util.Map;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.constants.Constants;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 根据参数，动态构造报表的sql语句
 * @author hjh
 *
 * 2014-7-10下午05:32:20
 */
public class ChartHelper {
	
	/**
	 * 把basic表的${basicAlias}替换成指定字符串。
	 * 这不是好的实现。不过前期没设计好，后期也就只能先这样了。
	 * @param basicAlias
	 */
	public static String  replaceBasicAlias(String sql,String basicAlias){
		Map<String,String> alias = new HashMap<String,String>();
		alias.put("basicAlias", basicAlias);
		return ChartHelper.runqianMacroFormat(sql,alias);
	}
	/**
	 * 润乾占位符
	 * pattern ${macro1}
	 * @author hjh
	 * @param 
	 * @return
	 * 2014-8-14下午08:24:48
	 */
	public static String runqianMacroFormat(String pattern, Map<String,String> arguments){
       String formatedStr = pattern;
       for (String key : arguments.keySet()) {
           formatedStr = formatedStr.replaceAll("\\$\\{"+key+"\\}", arguments.get(key).toString());
       }
       return formatedStr;
   }
	/**
	 * 批转未接受的sql——下级无此过滤条件
	 * @author hjh
	 * @return
	 * 2014-11-21下午02:15:31
	 */
	public static String gainBatchSql(){
		PersonInfo personInfo =  (PersonInfo) Struts2Utils.getSession().getAttribute("personInfo");
		String regionCode = personInfo.getRegionCode();
		StringBuffer sb = new StringBuffer();  
		if(Constants.SH_REGION_CDOE.equals(regionCode)){
			sb.append("				and (                                                                                              ")  
			.append("      					 (t.batch_flag='0' or t.batch_flag is null)                                                ")  
			.append("   					 or (t.batch_flag='1' and t.RECEIVE_DATE is not null)                                        ")  
			.append("				)                                                                                                  ")
			;
		}
		return sb.toString();
	}
}
