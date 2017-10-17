package com.sinosoft.xf.query.common.impl.chart.juji;

import com.runqian.report4.usermodel.Context;
import com.sinosoft.xf.query.common.impl.chart.Range5SqlHelper;

public abstract class JujiSqlHelper extends Range5SqlHelper {
	private static String jujiCon = " and lowerBasic.object_class_code in ('0300','0302')";
	public JujiSqlHelper(Context ctx) {
		super(ctx);
	}

	@Override
	protected String sqlBody() {
		return super.sqlBody()+getJujiCon();
	}

	public static String getJujiCon() {
		return jujiCon;
	}

	public static void setJujiCon(String jujiCon) {
		JujiSqlHelper.jujiCon = jujiCon;
	}

}
