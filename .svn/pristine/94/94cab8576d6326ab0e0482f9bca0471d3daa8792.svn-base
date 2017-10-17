package com.sinosoft.xf.query.common.impl.chart.ajt;

import com.runqian.report4.usermodel.Context;

public class AJTJCLSqlHelper extends AJTSqlHelper {
	public AJTJCLSqlHelper(Context ctx){
		super(ctx);
	};
	
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
		return jcltj();
	}

	@Override
	public String[] fields() {
		return jcltjFields();
	}

}
