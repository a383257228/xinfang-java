package com.sinosoft.xf.query.common.impl.chart.ajt;

import org.apache.commons.lang.StringUtils;

import com.runqian.report4.usermodel.Context;
import com.sinosoft.xf.query.common.iface.ChartSqlHelper;
import com.sinosoft.xf.query.view.ZQChartParams;

public class AJTSqlHelper extends ChartSqlHelper {
	
	public AJTSqlHelper(Context ctx) {
		super(ctx);
	};
	
	

	/* 
	 * AJT不需要改变统计口径
	 * @see com.sinosoft.xf.query.common.iface.ChartSqlHelper#condition()
	 */
	@Override
	protected String condition() {
		return "";
	}



	@Override
	public String geneCountSql() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String commonCondition(ZQChartParams params) {
		String condition = super.commonCondition(params);
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
		return condition;
	}

	@Override
	protected String condition(ZQChartParams params) {
		String condition = commonCondition(params);
		if (StringUtils.isNotBlank(params.getParentIssueCode())) {
			condition += " and left(mainIssue.issue_type_code,4)='"
					+ params.getParentIssueCode() + "' ";
		}
		return condition;
	}
	
	

}
