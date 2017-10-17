package com.sinosoft.xf.query.common.impl.chart.bw;

import com.runqian.report4.usermodel.Context;
import com.sinosoft.xf.query.common.impl.chart.Range5SqlHelper;

public class BWJCLSqlHelper extends Range5SqlHelper {
	public BWJCLSqlHelper(Context ctx) {
		super(ctx);
	};

//	@Override
//	protected int getSqlType() {
//		String isContainSuper = (String) getCtx().getParamValue(
//				"isContainSuper");
//		if (isContainSuper != null && "0".equals(isContainSuper.trim()))
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
		return jcltj();
	}

	@Override
	public String[] fields() {
		return jcltjFields();
	}

}
