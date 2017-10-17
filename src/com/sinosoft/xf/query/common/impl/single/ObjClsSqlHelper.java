package com.sinosoft.xf.query.common.impl.single;


/**
 * 职级（行政级别）统计
 * @author hjh
 *
 * 2015-1-23上午11:46:27
 */
public class ObjClsSqlHelper extends SingleSqlHelper {
	public ObjClsSqlHelper() {
		super();
	}
	public ObjClsSqlHelper(int type) {
		super(type);
	}
	
//	@Override
//	public String geneCountSql(int type) {
//		String sqlBody = sqlBody(type);
//		return "select lowerBasic.object_class_code,lowerBasic.object_class_name,count(1) as count  "
//		+ sqlBody
//		+ " group by lowerBasic.object_class_code,lowerBasic.object_class_name ";
//	}
	
	
	@Override
	public String geneCountSql(String sqlBody) {
		return super.objClstj(sqlBody);
	}
	@Override
	public String getFieldName() {
		return "OBJECT_CLASS_CODE";
	}
}
