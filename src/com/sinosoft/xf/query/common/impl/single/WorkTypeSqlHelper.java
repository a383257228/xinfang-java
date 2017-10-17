package com.sinosoft.xf.query.common.impl.single;


/**
 * 被反映人单位性质
 * @author hjh
 *
 * 2015-1-22下午06:30:37
 */
public class WorkTypeSqlHelper extends SingleSqlHelper {
	public WorkTypeSqlHelper() {
		super();
	}
	public WorkTypeSqlHelper(int type) {
		super(type);
	}
//	@Override
//	public String geneCountSql(int type) {
//		String sqlBody = sqlBody(type);
//		return "select accused.Accused_Work_Type_Code,accused.Accused_Work_Type_Name,count(1) as count  "
//		+ sqlBody
//		+ " group by accused.Accused_Work_Type_Code,accused.Accused_Work_Type_Name order by accused.Accused_Work_Type_Code ";
//	}
	@Override
	public String geneCountSql(String sqlBody) {
		return super.workTypetj(sqlBody);
	}
	@Override
	public String getFieldName() {
		return "ACCUSED_WORK_TYPE_CODE";
	}
}
