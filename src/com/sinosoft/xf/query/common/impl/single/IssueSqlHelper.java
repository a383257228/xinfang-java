package com.sinosoft.xf.query.common.impl.single;


/**
 * 问题大类统计
 * @author hjh
 *
 * 2015-1-22下午04:57:16
 */
public class IssueSqlHelper extends SingleSqlHelper {
	public IssueSqlHelper() {
		super();
	}
	public IssueSqlHelper(int type) {
		super(type);
	}
//	@Override
//	public String geneCountSql(int type) {
//		String sqlBody = sqlBody(type);
//		return "select left(mainIssue.issue_type_code,4)  as issue_type_code , count(1) as count  "
//		+ sqlBody
//		+ " group by left(mainIssue.issue_type_code,4) order by count desc ";
//	}
	
	

	@Override
	public String geneCountSql(String sqlBody) {
		return super.wtdltj(sqlBody);
	}
	@Override
	public String getFieldName() {
		return "ISSUE_TYPE_CODE";
	}
	
	

}
