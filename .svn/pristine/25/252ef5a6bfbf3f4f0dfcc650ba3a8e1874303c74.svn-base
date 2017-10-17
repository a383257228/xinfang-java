package com.sinosoft.xf.query.common.iface;


public class BWALLSqlBody extends SqlBody{
	
	public BWALLSqlBody(){
		super();
	}
	

	@Override
	public Object[] geneArgs(String timeRange) {
		String regionCode = SqlHelper.regionCode();
		Object[] timeArgs = timeArgs(timeRange);
		return new Object[] { regionCode, timeArgs[0], timeArgs[1] };
	}

	@Override
	protected void initPriSqlBody() {
		setPriSqlBody(getSqlBodyBWAll());
	}


	@Override
	protected void initJclSqlBody() {
//		String sqlBWJCL = gainSqlComponent("bwJCLCount");
//		setJclSqlBody(sqlBWJCL);
		setJclSqlBody(getSqlBodyBWALLJCL());
	}

}
