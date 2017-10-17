package com.sinosoft.xf.query.common.impl.chart.qs;

import com.runqian.report4.usermodel.Context;

/**
 * RSTJ数据集(用于统计署名举报、重复件数)
 * 
 * @author hjh
 * 
 * 2015-1-24下午03:45:57
 */
public class QSRSTJSqlHelper extends QSSqlHelper {
	public QSRSTJSqlHelper(Context ctx) {
		super(ctx);
	};
	
	@Override
	protected String sqlBody() {
		return super.sqlBody()
				+ " and ((lowerBasic.first_accuser is not null and lowerBasic.first_accuser != '') or basic.repeat_flag='1') ";
	}

	@Override
	public String geneCountSql() {
		return rstj();
	}

	@Override
	public String[] fields() {
		return rsFields();
	}
}
