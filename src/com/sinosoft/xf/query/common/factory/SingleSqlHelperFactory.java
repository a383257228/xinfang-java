package com.sinosoft.xf.query.common.factory;

import org.apache.log4j.Logger;

import com.sinosoft.xf.query.common.iface.SqlBody;
import com.sinosoft.xf.query.common.impl.single.AcceptSqlHelper;
import com.sinosoft.xf.query.common.impl.single.IssueSqlHelper;
import com.sinosoft.xf.query.common.impl.single.IssueTop5SqlHelper;
import com.sinosoft.xf.query.common.impl.single.JujiSqlHelper;
import com.sinosoft.xf.query.common.impl.single.MonthTrendSqlHelper;
import com.sinosoft.xf.query.common.impl.single.ObjClsSqlHelper;
import com.sinosoft.xf.query.common.impl.single.PtnClsSqlHelper;
import com.sinosoft.xf.query.common.impl.single.PtnSrcSqlHelper;
import com.sinosoft.xf.query.common.impl.single.PtnTypeSqlHelper;
import com.sinosoft.xf.query.common.impl.single.ShjiSqlHelper;
import com.sinosoft.xf.query.common.impl.single.SingleSqlHelper;
import com.sinosoft.xf.query.common.impl.single.WorkTypeSqlHelper;
import com.sinosoft.xf.query.common.impl.single.YearTrendSqlHelper;
/**
 * SqlHelper对象生产工厂
 * @author hjh
 *
 * 2015-1-22下午03:58:51
 */
public class SingleSqlHelperFactory {
	static Logger logger = Logger.getLogger(SingleSqlHelperFactory.class); //LogDemo为相关的类

	public static final String ACCEPT = "accept";
	/**
	 * 检举控告前五类问题查询
	 */
	public static final String ISSUE_TOPE5 = "issuetope5";
	/**
	 * 局级件查询
	 */
	public static final String JUJI = "juji";
	/**
	 * 信访类别查询
	 */
	public static final String PETITION_CLASS = "petitionclasscode";
	/**
	 * 信访方式查询
	 */
	public static final String PETITION_TYPE = "petitiontypecode";
	/**
	 * 信访来源查询
	 */
	public static final String PETITION_SOURCE = "petitionsourcecode";
	/**
	 * 单位性质查询
	 */
	public static final String ACCUSED_WORK_TYPE = "accusedworktypecode";
	/**
	 * 职级查询
	 */
	public static final String OBJECT_CLASS = "objectclasscode";
	/**
	 * 问题大类分类查询
	 */
	public static final String ISSUE_TYPE = "issuetypecode";
	/**
	 * 月趋势统计
	 */
	public static final String MONTH_TREND = "yearmonth";
	/**
	 * 年趋势统计
	 */
	public static final String YEAR_TREND = "year";
	/**
	 * 上级转交办查询
	 */
	public static final String SHJI = "shji";
	
	/**
	 * 判断该统计项查询是否已被重构, 是则使用新方法查询
	 * @author hjh
	 * @param propertyName 前台传来的值，用于确定统计项
	 * @return
	 * 2015-1-22下午07:04:25
	 */
	public static boolean hasRefactored(String propertyName){
		String[] items = {"petitionTypeCode","petitionClassCode","accusedWorkTypeCode","petitionSourceCode","objectClassCode","year","yearMonth","issueTypeCode"};
		for(String item : items){
			if(propertyName.equals(item))
				return true;
		}
		return false;
	}
	/**
	 * 创建具体SqlHelper对象
	 * @author hjh
	 * @param queryItemStr
	 * @return
	 * 2015-1-23下午03:20:22
	 */
	public SingleSqlHelper createSqlHelper(String queryItemStr){
		logger.debug("queryItemStr:"+queryItemStr);
		/*try {
			String queryItem = queryItemStr.toLowerCase();
			if (ACCEPT.equals(queryItem)) {
				return new AcceptSqlHelper();
			} else if (ISSUE_TOPE5.equals(queryItem))
				return new IssueTop5SqlHelper();
			else if (JUJI.equals(queryItem))
				return new JujiSqlHelper();
			else if (PETITION_CLASS.equals(queryItem))
				return new PtnClsSqlHelper();
			else if (PETITION_TYPE.equals(queryItem))
				return new PtnTypeSqlHelper();
			else if (PETITION_SOURCE.equals(queryItem))
				return new PtnSrcSqlHelper();
			else if (SHJI.equals(queryItem))
				return new ShjiSqlHelper();
			else if (ACCUSED_WORK_TYPE.equals(queryItem))
				return new WorkTypeSqlHelper();
			else if (OBJECT_CLASS.equals(queryItem))
				return new ObjClsSqlHelper();
			else if (ISSUE_TYPE.equals(queryItem))
				return new IssueSqlHelper();
			else if (MONTH_TREND.equals(queryItem))
				return new MonthTrendSqlHelper();
			else if (YEAR_TREND.equals(queryItem))
				return new YearTrendSqlHelper();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;*/
		return createSqlHelper(queryItemStr,SqlBody.DEFAULT_SQLBODY);
	}
	/**
	 * 创建具体SqlHelper对象
	 * @author hjh
	 * @param queryItemStr
	 * @return
	 * 2015-1-23下午03:20:22
	 */
	public SingleSqlHelper createSqlHelper(String queryItemStr,int type){
//		System.out.println("queryItemStr:"+queryItemStr+"===type:"+type);
		try {
			String queryItem = queryItemStr.toLowerCase();
			if (ACCEPT.equals(queryItem)) {
				return new AcceptSqlHelper(type);
			} else if (ISSUE_TOPE5.equals(queryItem))
				return new IssueTop5SqlHelper(type);
			else if (JUJI.equals(queryItem))
				return new JujiSqlHelper(type);
			else if (PETITION_CLASS.equals(queryItem))
				return new PtnClsSqlHelper(type);
			else if (PETITION_TYPE.equals(queryItem))
				return new PtnTypeSqlHelper(type);
			else if (PETITION_SOURCE.equals(queryItem))
				return new PtnSrcSqlHelper(type);
			else if (SHJI.equals(queryItem))
				return new ShjiSqlHelper(type);
			else if (ACCUSED_WORK_TYPE.equals(queryItem))
				return new WorkTypeSqlHelper(type);
			else if (OBJECT_CLASS.equals(queryItem))
				return new ObjClsSqlHelper(type);
			else if (ISSUE_TYPE.equals(queryItem))
				return new IssueSqlHelper(type);
			else if (MONTH_TREND.equals(queryItem))
				return new MonthTrendSqlHelper(type);
			else if (YEAR_TREND.equals(queryItem))
				return new YearTrendSqlHelper(type);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
