package com.sinosoft.xf.query.common.impl.chart;

import com.runqian.report4.usermodel.Context;
import com.sinosoft.xf.query.common.factory.SingleSqlHelperFactory;
import com.sinosoft.xf.query.common.factory.SqlHelperFactory;
import com.sinosoft.xf.query.common.iface.ChartSqlHelper;
import com.sinosoft.xf.query.common.impl.single.SingleSqlHelper;

public class ShjiChartSqlHelper extends ChartSqlHelper {

	public ShjiChartSqlHelper(Context ctx) {
		super(ctx);
	};

	@Override
	public String geneCountSql() {
		SingleSqlHelperFactory factory = new SingleSqlHelperFactory();
		SingleSqlHelper sh = factory.createSqlHelper(SqlHelperFactory.SHJI);
		return sh.geneStatisSql();
	}

}
