package com.sinosoft.xf.query.common.impl.single;


/**
 * 检举控告前5类问题统计
 * @author hjh
 *
 * 2015-1-22下午04:57:16
 */
public class IssueTop5SqlHelper extends SingleSqlHelper {
	public IssueTop5SqlHelper() {
		super();
	}
	public IssueTop5SqlHelper(int type) {
		super(type);
	}
//	@Override
//	public String geneCountSql(int type) {
//		String sqlBody = sqlBody(type);
//		return "select left(mainIssue.issue_type_code,4)  as issue_type_code , count(1) as count  "
//		+ sqlBody
//		+ " and lowerBasic.petition_class_code='1' "
//		+ " group by left(mainIssue.issue_type_code,4) order by count desc ";
//	}
	
	@Override
	public String geneCountSql(String sqlBody) {
		return super.wtdltj(sqlBody);
	}
	@Override
	public String condition() {
		return super.condition()+" and lowerBasic.petition_class_code='1' ";
	}
	@Override
	public String getFieldName() {
		return "ISSUE_TYPE_CODE";
	}
	
	

}
