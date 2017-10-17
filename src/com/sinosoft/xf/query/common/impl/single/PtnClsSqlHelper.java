package com.sinosoft.xf.query.common.impl.single;


public class PtnClsSqlHelper extends SingleSqlHelper {
	public PtnClsSqlHelper() {
		super();
	}
	public PtnClsSqlHelper(int type) {
		super(type);
	}
//	@Override
//	public String geneCountSql(int type) {
//		String sqlBody = sqlBody(type);
//		return "select lowerBasic.petition_class_code,lowerBasic.petition_class_name,count(1) as count  "
//		+ sqlBody
//		+ " group by lowerBasic.petition_class_code,lowerBasic.petition_class_name order by lowerBasic.petition_class_code ";
//	}
	
	
	@Override
	public String geneCountSql(String sqlBody) {
		return super.ptnClstj(sqlBody);
	}
	@Override
	public String getFieldName() {
		return "PETITION_CLASS_CODE";
	}
}
