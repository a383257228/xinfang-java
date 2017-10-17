package com.sinosoft.xf.query.common.impl.chart.ajt;

import com.runqian.report4.usermodel.Context;

public class AJTIssueZLTJSqlHelper extends AJTSqlHelper {
	public AJTIssueZLTJSqlHelper(Context ctx) {
		super(ctx);
	};

	@Override
	protected String sqlBody() {
		return super.sqlBody() + " and lowerBasic.petition_class_code='4' ";
	}

	@Override
	public String geneCountSql() {
		return super.iswtzltj();
	}

	// @Override
	// protected void addCustomMacros() {
	// this.getMacros().setXFRegion();
	//		
	// }

	@Override
	public String[] fields() {
		return wtFields();
	}

}
