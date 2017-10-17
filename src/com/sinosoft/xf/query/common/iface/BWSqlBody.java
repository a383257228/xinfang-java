package com.sinosoft.xf.query.common.iface;


/**
 * 本委受理的各统计项统计
 * @author hjh
 *
 * 2015-2-9上午11:28:06
 */
public class BWSqlBody extends SqlBody{
	
	public BWSqlBody(){
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
		setPriSqlBody(getSqlBodyBW());
//		setPriSqlBody(getSqlBodyBWAll());
	}


	@Override
	protected void initJclSqlBody() {
		setJclSqlBody(getSqlBodyBWJCL());
//		setJclSqlBody(getSqlBodyBWALLJCL());
	}

}
