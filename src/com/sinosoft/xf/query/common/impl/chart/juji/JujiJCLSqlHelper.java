package com.sinosoft.xf.query.common.impl.chart.juji;

import com.runqian.report4.usermodel.Context;

public class JujiJCLSqlHelper extends JujiSqlHelper {
	public JujiJCLSqlHelper(Context ctx) {
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
	public String geneCountSql() {
		return jcltj();
	}

	@Override
	public String[] fields() {
		return jcltjFields();
	}

	@Override
	protected String sqlBody() {
		return getSqlBody().getJclSqlBody()+getJujiCon()+condition();
	}

}
