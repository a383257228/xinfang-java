package com.sinosoft.xf.query.common.impl.chart;

import org.apache.commons.lang.StringUtils;

import com.runqian.report4.usermodel.Context;
import com.sinosoft.xf.query.common.iface.ChartSqlHelper;
import com.sinosoft.xf.query.view.ZQChartParams;

public abstract class Range5SqlHelper extends ChartSqlHelper {

	public Range5SqlHelper(Context ctx) {
		super(ctx);
	}

	@Override
	protected String jcltj() {
		return "select ${time_range} as time_range ,count(1) as count "
				+ sqlBody();
	}
	@Override
	protected String[] jcltjFields() {
		String[] fields = { "TIME_RANGE", "COUNT" };
		return fields;
	}
	@Override
	protected String wtdltj() {
		return "select left(mainIssue.issue_type_code,4)  as issue_type_code ,${time_range} as time_range, count(1) as count  "
				+ sqlBody()
				+ " group by left(mainIssue.issue_type_code,4) order by count desc ";
	}
	//by wangk 润乾报表的本委信访数据比较中，增加了wtdltjn的数据集，查询问题类型为6位，
	@Override
	protected String wtdltjn() {
		return "select left(mainIssue.issue_type_code,6)  as issue_type_code ,${time_range} as time_range, count(1) as count  "
				+ sqlBody()
				+ " group by left(mainIssue.issue_type_code,6) order by count desc ";
	}
	@Override
	protected String rstj() {
		return "select lowerBasic.repeat_flag,case when (lowerBasic.first_accuser is null or lowerBasic.first_accuser='') then 0 else 1 end as first_accuser_flag,${time_range} as time_range,count(1) as count "
				+ sqlBody()
				+ " group by lowerBasic.object_class_code,lowerBasic.petition_type_code,lowerBasic.repeat_flag, case when (lowerBasic.first_accuser is null or lowerBasic.first_accuser='') then 0 else 1 end  ";
	}
	@Override
	protected String[] rsFields() {
		String[] fields = { 
				"REPEAT_FLAG", "FIRST_ACCUSER_FLAG","TIME_RANGE", "COUNT" };
		return fields;
	}
	@Override
	protected String wttj() {
		return "select mainIssue.issue_type_code ,${time_range} as time_range,count(1) as count "
				+ sqlBody() + " group by mainIssue.issue_type_code ";
	}
	@Override
	protected String[] wtFields() {
		String[] fields = { "ISSUE_TYPE_CODE", "TIME_RANGE", "COUNT" };
		return fields;
	}
	/**
	 * 添加钻取条件sql
	 * @author hjh
	 * @param params
	 * @return
	 * 2015-1-29下午05:26:12
	 */
	@Override
	protected String condition(ZQChartParams params){
		String condition = commonCondition(params);
		if (StringUtils.isNotBlank(params.getIsSwgat())&& "1".equals(params.getIsSwgat())) {
			condition += " and lowerBasic.is_Swgat='1' ";
		}
		if(StringUtils.isNotBlank(params.getObjectClassCode())) {
			String value = params.getObjectClassCode();
			if("overJuji".equals(value))
				condition += " and lowerBasic.object_class_code in('0200','0202','0300','0302')";
			else if("0400".equals(value)|| "0500".equals(value))
				condition += " and left(lowerBasic.object_class_code,2) like '"+value.substring(0, 2)+"%'";
			else
				condition += " and lowerBasic.object_class_code = '"+value+"'";
		}
		if(StringUtils.isNotBlank(params.getParentIssueCode())) {
			String value = params.getParentIssueCode();
			if("013".equals(value)){
				condition += " and left(mainIssue.issue_type_code,3) like '"
					+ value.substring(0, 3) + "%'";
			}else{
				condition += " and left(mainIssue.issue_type_code,4)='"
					+ value + "' ";
			}
		}
		if(StringUtils.isNotBlank(params.getIssueTypeCode())) {
			condition += " and mainIssue.issue_type_code='"+params.getIssueTypeCode()+"' ";
		}
		return condition;
	}
}
