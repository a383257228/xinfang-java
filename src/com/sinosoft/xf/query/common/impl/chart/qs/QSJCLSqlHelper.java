package com.sinosoft.xf.query.common.impl.chart.qs;

import com.runqian.report4.usermodel.Context;

public class QSJCLSqlHelper extends QSSqlHelper {
	public QSJCLSqlHelper(Context ctx) {
		super(ctx);
	};

//	@Override
//	protected int getSqlType() {
//		return QSJCL;
//	}
	@Override
	protected String sqlBody() {
		return getSqlBody().getJclSqlBody()+condition();
	}
	@Override
	public String geneCountSql() {
		return jcltj();
	}

	@Override
	public String[] fields() {
		return jcltjFields();
	}

}
