package com.sinosoft.xf.query.common.impl.chart.bw;

import com.runqian.report4.usermodel.Context;
import com.sinosoft.xf.query.common.impl.chart.Range5SqlHelper;

public class BWZLTJSqlHelper extends Range5SqlHelper {

	public BWZLTJSqlHelper(Context ctx) {
		super(ctx);
	};

	@Override
	public String geneCountSql() {
		return zltj();
	}

	@Override
	public String[] fields() {
		return zlFields();
	}


}
