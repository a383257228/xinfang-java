package com.sinosoft.xf.query.common.impl.single;


/**
 * 年度趋势查询
 * @author hjh
 *
 * 2015-1-23下午03:23:24
 */
public class YearTrendSqlHelper extends SingleSqlHelper {
	public YearTrendSqlHelper() {
		super();
	}
	public YearTrendSqlHelper(int type) {
		super(type);
	}
//	@Override
//	public String geneCountSql(int type) {
//		String sqlBody = sqlBody(type);
//		return "select count(basic.oid) as num,substr(basic.actual_date,1,4)  as type "
//		+ sqlBody
//		+ " group by substr(basic.actual_date,1,4)  order by type ";
//	}
	
	@Override
	public String geneCountSql(String sqlBody) {
		return super.yearTrendtj(sqlBody);
	}
	
	@Override
	public String getFieldName() {
		return "TYPE";
	}
}
