package com.sinosoft.xf.query.common.impl.single;


public class JujiSqlHelper extends SingleSqlHelper {
	public JujiSqlHelper() {
		super();
	}
	public JujiSqlHelper(int type) {
		super(type);
	}
//	@Override
//	public String geneCountSql(int type) {
//		String sql = super.geneCountSql(type);
//		String jujiSql = " and lowerBasic.object_class_code in  ('0300','0302') ";
//		return sql + jujiSql;
//	}
	@Override
	public String condition() {
		return super.condition()+" and lowerBasic.object_class_code in  ('0300','0302') ";
	}
}
