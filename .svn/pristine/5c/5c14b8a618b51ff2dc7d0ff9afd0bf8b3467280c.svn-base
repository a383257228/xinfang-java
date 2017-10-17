package com.sinosoft.xf.query.common.impl.chart.bw;

import com.runqian.report4.usermodel.Context;
import com.sinosoft.xf.query.common.impl.chart.Range5SqlHelper;

public class BWWTTJSqlHelper extends Range5SqlHelper {

	public BWWTTJSqlHelper(Context ctx) {
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
		return wttj();
	}

	@Override
	public String[] fields() {
		return wtFields();
	}

}
