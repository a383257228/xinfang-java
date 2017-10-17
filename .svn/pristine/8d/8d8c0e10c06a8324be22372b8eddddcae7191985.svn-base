package com.sinosoft.xf.query.common.impl.chart.qs;

import com.runqian.report4.usermodel.Context;

public class QSWTTJSqlHelper extends QSSqlHelper {

	public QSWTTJSqlHelper(Context ctx) {
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
