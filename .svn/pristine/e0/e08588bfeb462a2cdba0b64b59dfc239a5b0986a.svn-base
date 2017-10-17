package com.sinosoft.xf.query.common.iface;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.type.NullableType;
import org.hibernate.type.Type;

import com.runqian.report4.usermodel.Context;
import com.sinosoft.xf.query.common.BusinessTime;
import com.sinosoft.xf.query.common.Macros;
import com.sinosoft.xf.query.common.factory.SqlBodyFactory;
import com.sinosoft.xf.query.view.ZQChartParams;
import com.sinosoft.xf.util.TimeUtils;
import com.sinosoft.xf.util.sql.SqlUtil;

/**
 * 
 * @author hjh
 * 
 * 2015-1-29下午05:24:23
 */
public abstract class ChartSqlHelper extends SqlHelper {

	/**
	 * 润乾参数容器
	 */
	private Context ctx;
	/**
	 * 时间范围参数。
	 */
	private String timeRange = CUSTOM;
	private Macros macros;
	public Macros getMacros() {
		return macros;
	}

	public void setMacros(Macros macros) {
		this.macros = macros;
	}

	public ChartSqlHelper() {
		super();
	}

	public ChartSqlHelper(Context ctx) {
		this.ctx = ctx;
		int type = getSqlType();
		sqlBody = sqlBodyFactory.createSqlBody(type);
		this.setMacros(new Macros(ctx));
		addCustomMacros();
	};

	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
		getMacros().setTimeRangeMacro(timeRange);
	}
	/**
	 * 添加自定义宏
	 * @author hjh
	 * 2015-1-30下午10:25:22
	 */
	protected void addCustomMacros(){	
	}
	/**
	 * 获取报表数据集查询字段名
	 * 
	 * @author hjh
	 * @return 2015-1-29下午05:25:00
	 */
	public String[] fields() {
		return new String[] { "COUNT" };
	}

	/**
	 * 报表数据集查询sql
	 * 
	 * @author hjh
	 * @return 2015-1-29下午05:25:49
	 */
	public abstract String geneCountSql();
	
	/**
	 * 返回统计数据的sql
	 * @author hjh
	 * @return
	 * 2015-2-7下午09:55:34
	 */
	public String geneStatisSql() {
		String tempSql = geneCountSql();
		String sql = replacePlaceholder(tempSql,getMacros().getMacros());
		return sql;
	}
	
	/**
	 * 获取钻取明细sql
	 * 
	 * @author hjh
	 * @param params
	 * @return 2015-1-29下午05:24:25
	 */
	public String detailedSql(ZQChartParams params) {
		return "select rownumber() over(order by basic.create_date) as rownumber_, basic.oid,basic.petition_date,basic.curr_petition_no,basic.petition_no,basic.petition_prt_no,lowerBasic.REGION_CODE,lowerBasic.important_flag,lowerBasic.petition_type_name,basic.petition_source_name,basic.create_date,basic.creator_id,basic.creator_name,basic.modify_date,basic.modify_id,basic.modify_name " + sqlBody() + condition(params)
				+ " group by basic.oid,basic.petition_date,basic.curr_petition_no,basic.petition_no,basic.petition_prt_no,lowerBasic.REGION_CODE,lowerBasic.important_flag,lowerBasic.petition_type_name,basic.petition_source_name,basic.create_date,basic.creator_id,basic.creator_name,basic.modify_date,basic.modify_id,basic.modify_name order by basic.create_date ";
	}

	private String pagedSql(String sql) {
		return "select * from ( " + sql
				+ ") as temp_ where rownumber_ >= ? and rownumber_ <= ?";
	}
	public String pagedDetailedSql(ZQChartParams params) {
		String tempSql = pagedSql(detailedSql(params));
		return replacePlaceholder(tempSql,getMacros().getMacros());
	}
	
	public Object[] pagedArgs(ZQChartParams params){
		Object[] objs = geneArgs();
		List<Object> argList =  new ArrayList<Object>(Arrays.asList(objs));
		argList.add(params.getStart()+1);
		argList.add(params.getStart()+params.getLimit());
		return argList.toArray();
	}

	/**
	 * 钻取明细总数Sql
	 * 
	 * @author hjh
	 * @param params
	 * @return 2015-1-29下午05:27:55
	 */
	public String detailedCountSql(ZQChartParams params) {
		String tempSql = "select count(distinct basic.oid) as count " + sqlBody() + condition(params);
		String sql = replacePlaceholder(tempSql,getMacros().getMacros());
		return sql;
	}

	/**
	 * 生成sql查询参数
	 * 
	 * @author hjh
	 * @return 2015-1-30下午05:10:11
	 */
	public Object[] geneArgs() {
		String regionCode = regionCode();
		Timestamp[] timeRange = geneTimeArgs();
		return new Object[] { regionCode, timeRange[0], timeRange[1] };
	}

	/**
	 * 根据时间范围time_range和传入的时间endDate，生成时间参数startDate,endDate
	 * 
	 * @author hjh
	 * @return 2015-1-31下午03:14:20
	 */
	public Timestamp[] geneTimeArgs() {
		String startDateStr = (String) ctx.getParamValue("startDate");
		String endDateStr = (String) ctx.getParamValue("endDate");
		Timestamp startDate = null;
		Timestamp endDate = null;
		BusinessTime businessTime = new BusinessTime(endDateStr);
		if (CUSTOM.equals(timeRange)) {
			startDate = TimeUtils.string2Date(startDateStr);
			endDate = TimeUtils.string2Date(endDateStr);
		} else if (MONTH.equals(timeRange)) {
			startDate = businessTime.getMonthStartTime();
			endDate = businessTime.getMonthEndTime();
		} else if (YEAR.equals(timeRange)) {
			startDate = businessTime.getYearStartTime();
			endDate = businessTime.getYearEndTime();
		} else if (LAST_MONTH.equals(timeRange)) {
			startDate = businessTime.getLastMonthStartTime();
			endDate = businessTime.getLastMonthEndTime();
		} else if (LAST_YEAR.equals(timeRange)) {
			startDate = businessTime.getLastYearStartTime();
			endDate = businessTime.getLastYearEndTime();
		} else if (LAST_YEAR_MONTH.equals(timeRange)) {
			startDate = businessTime.getLastYearMonthStartTime();
			endDate = businessTime.getLastYearMonthEndTime();
		}
		return new Timestamp[] { startDate, endDate };
	}

	/**
	 * 返回Hibernate参数类型（与geneArgs()）对应
	 * 
	 * @author hjh
	 * @return 2015-1-29下午05:50:07
	 */
	public Type[] argTypes() {
		return new NullableType[] { Hibernate.STRING, Hibernate.TIMESTAMP,
				Hibernate.TIMESTAMP };
	}
	/**
	 * 返回Hibernate参数类型（与geneArgs()）对应
	 * 
	 * @author hjh
	 * @return 2015-1-29下午05:50:07
	 */
	public Type[] argPagedTypes() {
		Type[] types =  argTypes();
		ArrayList<Type> pageTypes = new ArrayList<Type>(Arrays.asList(types));
		pageTypes.add(Hibernate.INTEGER);
		pageTypes.add(Hibernate.INTEGER);
		Type[] pagedTypes = new Type[types.length+2];
		return pageTypes.toArray(pagedTypes);
	}
	
	public static void main(String[] args) {
//		Type[] types = ChartSqlHelper.argPagedTypes();
//		System.out.println(types.toString());
	}

	/**
	 * 根据isContainSuper值返回对应sql类型。SqlHelper将据此生成基本sql体 isContainSuper 是否包含上级，是：1
	 * 否：0
	 * 
	 * @author hjh
	 * @param ctx
	 * @return 2015-1-26下午05:55:36
	 */
	protected int getSqlType() {
		String isContainSuper = (String) ctx.getParamValue("isContainSuper");
		if (isContainSuper != null && "0".equals(isContainSuper.trim()))
			return SqlBody.BW;
		else
			return SqlBody.BWALL;
	}
	private static SqlBodyFactory sqlBodyFactory = new SqlBodyFactory();
	private SqlBody sqlBody;
	/**
	 * 返回sql语句体
	 * 
	 * @author hjh
	 * @param type
	 * @return 2015-1-20下午05:51:49
	 */
	protected String sqlBody() {
		return sqlBody.getPriSqlBody()+condition();
	}
	
	/**
	 * 添加需求
	 * @author hjh
	 * @return
	 * 2015-3-9下午03:19:00
	 */
	protected String condition(){
//		return SqlUtil.mrChuCondition();
		return "";
	}

	/**
	 * 通过
	 * 
	 * @author hjh
	 * @return 2015-1-26下午05:44:41
	 */
	// public Map<String, String> getMacrosMap() {
	//		
	// // 设置地域权限参数（宏）
	// String xf_region = geneXFRegionSql(regions);
	// // map.put("xf_region", xf_region);
	// return null;
	// }
	protected Context getCtx() {
		return this.ctx;
	}

	protected void setCtx(Context ctx) {
		this.ctx = ctx;
	}

	/**
	 * 集体统计sql
	 * 
	 * @author hjh
	 * @param sqlbody
	 * @return 2015-1-30下午02:51:19
	 */
	protected String jttj() {
		return "select lowerBasic.petition_class_code ,sum(lowerBasic.person_num) sum_person_num,${time_range} as time_range ,count(1) as count "
				+ sqlBody() + " group by lowerBasic.petition_class_code  ";
	}

	/**
	 * 总量统计sql
	 * 
	 * @author hjh
	 * @param sqlbody
	 * @return 2015-1-30下午02:51:19
	 */
	protected String zltj() {
		return "select lowerBasic.object_class_code,lowerBasic.petition_type_code,lowerBasic.petition_class_code,lowerBasic.petition_urge_code,lowerBasic.is_swgat,sum(lowerBasic.person_num) sum_person_num,${time_range} as time_range,count(1) as count "
				+ sqlBody()
				+ " group by lowerBasic.object_class_code,lowerBasic.petition_type_code,lowerBasic.petition_class_code,lowerBasic.petition_urge_code,lowerBasic.is_swgat ";
	}

	/**
	 * 总量统计对应字段名
	 * 
	 * @author hjh
	 * @return 2015-1-30下午03:43:09
	 */
	protected String[] zlFields() {
		String[] fields = { "OBJECT_CLASS_CODE", "PETITION_TYPE_CODE",
				"PETITION_CLASS_CODE", "PETITION_URGE_CODE", "SUM_PERSON_NUM",
				"TIME_RANGE", "COUNT","IS_SWGAT" };
		return fields;
	}

	/**
	 * RSTJ数据集(用于统计署名举报、重复件数)
	 * 
	 * @author hjh
	 * @return 2015-1-30下午02:58:40
	 */
	protected String rstj() {
		return "select lowerBasic.object_class_code,lowerBasic.petition_type_code, lowerBasic.repeat_flag,case when (lowerBasic.first_accuser is null or lowerBasic.first_accuser='') then 0 else 1 end as first_accuser_flag,${time_range} as time_range,count(1) as count "
				+ sqlBody()
				+ " group by lowerBasic.object_class_code,lowerBasic.petition_type_code,lowerBasic.repeat_flag, case when (lowerBasic.first_accuser is null or lowerBasic.first_accuser='') then 0 else 1 end  ";
	}

	/**
	 * 总量统计对应字段名
	 * 
	 * @author hjh
	 * @return 2015-1-30下午03:43:09
	 */
	protected String[] rsFields() {
		String[] fields = { "OBJECT_CLASS_CODE", "PETITION_TYPE_CODE",
				"REPEAT_FLAG", "FIRST_ACCUSER_FLAG", "TIME_RANGE", "COUNT" };
		return fields;
	}

	/**
	 * 总量统计对应字段名
	 * 
	 * @author hjh
	 * @return 2015-1-30下午03:43:09
	 */
	protected String[] jtFields() {
		String[] fields = { "PETITION_CLASS_CODE", "SUM_PERSON_NUM",
				"TIME_RANGE", "COUNT" };
		return fields;
	}

	/**
	 * 总量统计对应字段名
	 * 
	 * @author hjh
	 * @return 2015-1-30下午03:43:09
	 */
	/*
	 * protected String[] wtdlFields() { String[] fields = {
	 * "OBJECT_CLASS_CODE", "PETITION_TYPE_CODE",
	 * "ISSUE_TYPE_CODE","TIME_RANGE", "COUNT" }; return fields; }
	 */
	/**
	 * 总量统计对应字段名
	 * 
	 * @author hjh
	 * @return 2015-1-30下午03:43:09
	 */
	protected String[] wtFields() {
		String[] fields = { "OBJECT_CLASS_CODE", "PETITION_TYPE_CODE",
				"ISSUE_TYPE_CODE", "TIME_RANGE", "COUNT" };
		return fields;
	}

	/**
	 * 问题大类统计
	 * 
	 * @author hjh
	 * @return 2015-1-30下午03:01:20
	 */
	protected String wtdltj() {
		return "select lowerBasic.object_class_code,lowerBasic.petition_type_code,left(issue.issue_type_code,4) as issue_type_code,${time_range} as time_range,count(1) as count "
				+ sqlBody()
				+ " group by lowerBasic.object_class_code,lowerBasic.petition_type_code,left(issue.issue_type_code,4) ";
	}

	protected String jcltj() {
		return "select lowerBasic.object_class_code,lowerBasic.petition_type_code,count(1) as count "
				+ sqlBody()
				+ " group by lowerBasic.object_class_code,lowerBasic.petition_type_code ";
	}

	protected String[] jcltjFields() {
		String[] fields = { "OBJECT_CLASS_CODE", "PETITION_TYPE_CODE", "COUNT" };
		return fields;
	}

	/**
	 * 问题统计
	 * 
	 * @author hjh
	 * @return 2015-1-30下午03:01:20
	 */
	protected String wttj() {
		return "select lowerBasic.object_class_code,lowerBasic.petition_type_code,issue.issue_type_code ,${time_range} as time_range,count(1) as count "
				+ sqlBody()
				+ " group by lowerBasic.object_class_code,lowerBasic.petition_type_code,issue.issue_type_code ";
	}

	protected String iswtzltj() {
		return "select lowerBasic.object_class_code,lowerBasic.petition_type_code,mainIssue.issue_type_code ,${time_range} as time_range,count(1) as count "
				+ sqlBody()
				+ " group by lowerBasic.object_class_code,lowerBasic.petition_type_code,mainIssue.issue_type_code ";
	}

	/**
	 * 通用条件sql构造
	 * 
	 * @author hjh
	 * @param params
	 * @return 2015-2-2下午04:09:58
	 */
	protected String commonCondition(ZQChartParams params) {
		String condition = "";
		if (StringUtils.isNotBlank(params.getPetitionClassCode())) {
			condition += " and lowerBasic.petition_class_code='"
					+ params.getPetitionClassCode() + "'";
		}
		if (StringUtils.isNotBlank(params.getPetitionTypeCode())) {
			condition += " and lowerBasic.petition_type_code='"
					+ params.getPetitionTypeCode() + "'";
		}

		if (StringUtils.isNotBlank(params.getRepeatFlag())
				&& "1".equals(params.getRepeatFlag())) {
			condition += " and lowerBasic.repeat_flag = '1'";
		}
		if (StringUtils.isNotBlank(params.getFirstAccuserFlag())
				&& "1".equals(params.getFirstAccuserFlag())) {
			condition += " and lowerBasic.first_accuser !='' and  lowerBasic.first_accuser is not null ";
		}
		if (StringUtils.isNotBlank(params.getPetitionUrgeCode())
				&& "1".equals(params.getPetitionUrgeCode())) {
			condition += " and lowerBasic.petition_urge_code='1' ";
		}
		return condition;
	}

	/**
	 * 添加钻取条件sql
	 * 
	 * @author hjh
	 * @param params
	 * @return 2015-1-29下午05:26:12
	 */
	protected String condition(ZQChartParams params) {
		String condition = commonCondition(params);
		if (StringUtils.isNotBlank(params.getObjectClassCode())) {
			String value = params.getObjectClassCode();
			if ("0200".equals(value) || "0300".equals(value)
					|| "0400".equals(value) || "0500".equals(value))
				condition += " and left(lowerBasic.object_class_code,2) like '"
						+ value.substring(0, 2) + "%'";
			else
				condition += " and lowerBasic.object_class_code = '" + value
						+ "'";
		}
		if (StringUtils.isNotBlank(params.getIssueTypeCode())) {
			condition += " and issue.issue_type_code='"
					+ params.getIssueTypeCode() + "' ";
		}
		if (StringUtils.isNotBlank(params.getParentIssueCode())) {
			condition += " and left(mainIssue.issue_type_code,4)='"
					+ params.getParentIssueCode() + "' ";
		}
		return condition;
	}

	protected SqlBody getSqlBody() {
		return sqlBody;
	}
	//by wangk 润乾报表本委信访情况数据比较中增加了BWWTDLTJN的数据集
	protected String wtdltjn() {
		// TODO Auto-generated method stub
		return null;
	}

}
