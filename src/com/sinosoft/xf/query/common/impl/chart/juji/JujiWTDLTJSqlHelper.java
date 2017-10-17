package com.sinosoft.xf.query.common.impl.chart.juji;

import com.runqian.report4.usermodel.Context;

public class JujiWTDLTJSqlHelper extends JujiSqlHelper {

	public JujiWTDLTJSqlHelper(Context ctx) {
		super(ctx);
	};

	/*@Override
	protected int getSqlType(){
		
		String isContainSuper = (String) getCtx().getParamValue("isContainSuper");
		if(isContainSuper!=null && "0".equals(isContainSuper.trim()))
			return SqlHelper.BWJCL;
		else
			return SqlHelper.BWALLJCL;
	}*/

	@Override
	public String geneCountSql() {
		return wtdltj();
	}

	@Override
	protected void addCustomMacros() {
		getMacros().joinIssueTable();
	}

	@Override
	public String[] fields() {
		return wtFields();
	}
}
