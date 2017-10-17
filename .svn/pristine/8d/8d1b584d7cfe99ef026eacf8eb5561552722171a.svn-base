package com.sinosoft.xf.query.common.iface;

import com.sinosoft.xf.query.common.BusinessHelper;

public class QSSqlBody extends SqlBody{
	
	public QSSqlBody(){
		super();
	}
	

	@Override
	public Object[] geneArgs(String timeRange) {
		String regionCode = SqlHelper.regionCode();
		String regionCodeLike = BusinessHelper.regionLikeValue(regionCode);
		Object[] timeArgs = timeArgs(timeRange);
		return new Object[] { regionCodeLike, timeArgs[0], timeArgs[1],
				regionCode };
	}

	@Override
	protected void initPriSqlBody() {
		setPriSqlBody(getSqlBodyQS());
	}
	@Override
	protected void initJclSqlBody() {
		setJclSqlBody(getSqlBodyQSJCL());
	}
}
