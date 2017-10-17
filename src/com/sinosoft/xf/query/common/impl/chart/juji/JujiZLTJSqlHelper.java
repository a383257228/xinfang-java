package com.sinosoft.xf.query.common.impl.chart.juji;

import com.runqian.report4.usermodel.Context;

public class JujiZLTJSqlHelper extends JujiSqlHelper {

	public JujiZLTJSqlHelper(Context ctx) {
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
