package com.sinosoft.xf.query.common.impl.single;


public class MonthTrendSqlHelper extends SingleSqlHelper {
	public MonthTrendSqlHelper() {
		super();
	}
	public MonthTrendSqlHelper(int type) {
		super(type);
	}
//	@Override
//	public String geneCountSql(int type) {
//		String sqlBody = sqlBody(type);
//		return "select count(basic.oid) as num, basic.actual_date  as type "
//		+ sqlBody
//		+ " group by  basic.actual_date  order by type ";
//	}
	
	
	@Override
	public String geneCountSql(String sqlBody) {
		return super.monthTrendtj(sqlBody);
	}
	@Override
	public String getFieldName() {
		return "TYPE";
	}
}
