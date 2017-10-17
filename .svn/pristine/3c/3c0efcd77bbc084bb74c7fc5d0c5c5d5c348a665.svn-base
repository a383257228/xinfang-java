package com.sinosoft.xf.query.common.impl.chart.ajt;

import com.runqian.report4.usermodel.Context;

public class AJTJTTJSqlHelper extends AJTSqlHelper {

	public AJTJTTJSqlHelper(Context ctx) {
		super(ctx);
	};

	@Override
	protected String sqlBody() {
		return super.sqlBody()
				+ " and lowerBasic.person_num>=5  and lowerBasic.PETITION_TYPE_CODE = 2 ";
	}

	@Override
	public String geneCountSql() {
		return super.jttj();
	}

	@Override
	public String[] fields() {
		return jtFields();
	}
}
