package com.sinosoft.xf.query.common.impl.chart.ajt;

import org.apache.commons.lang.StringUtils;

import com.runqian.report4.usermodel.Context;
import com.sinosoft.xf.query.view.ZQChartParams;

public class AJTWTDLTJSqlHelper extends AJTSqlHelper {

	public AJTWTDLTJSqlHelper(Context ctx) {
		super(ctx);
	};
//
//	@Override
//	protected int getSqlType(){
//		String isContainSuper = (String) getCtx().getParamValue("isContainSuper");
//		if(isContainSuper!=null && "0".equals(isContainSuper.trim()))
//			return SqlHelper.BWJCL;
//		else
//			return SqlHelper.BWALLJCL;
//	}
	@Override
	protected String sqlBody() {
		return getSqlBody().getJclSqlBody()+condition();
	}

	@Override
	public String geneCountSql() {
//		return wtdltj();
		//中间的select distinct lowerBasic.oid,lowerBasic.object_class_code,lowerBasic.petition_type_code,left(issue.issue_type_code,4)  as issue_type_code"
		//是用于去除多个问题大类相同的相同信访件
		return "select object_class_code,petition_type_code,issue_type_code,'CUSTOM' as time_range,count(1) as count "
		+"from("
		+"select distinct lowerBasic.oid,lowerBasic.object_class_code,lowerBasic.petition_type_code,left(issue.issue_type_code,4)  as issue_type_code"
		+sqlBody()
		+")"
		+"group by object_class_code,petition_type_code,issue_type_code"
		;
	}

	@Override
	protected void addCustomMacros() {
		getMacros().joinIssueTable();
	}

	@Override
	protected String condition(ZQChartParams params) {
		String condition = commonCondition(params);
		if (StringUtils.isNotBlank(params.getParentIssueCode())) {
			condition += " and left(issue.issue_type_code,4)='"
					+ params.getParentIssueCode() + "' ";
		}
		return condition;
	}

	@Override
	public String[] fields() {
		return wtFields();
	}
}
