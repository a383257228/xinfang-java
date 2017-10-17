package com.sinosoft.xf.query.common.impl.single;


/**
 * 信访类别查询统计帮助类
 * @author hjh
 *
 * 2015-1-22下午04:54:53
 */
public class PtnTypeSqlHelper extends SingleSqlHelper {
	public PtnTypeSqlHelper() {
		super();
	}
	public PtnTypeSqlHelper(int type) {
		super(type);
	}
//	@Override
//	public String geneCountSql(int type) {
//		String sqlBody = sqlBody(type);
//		return "select lowerBasic.petition_type_code,lowerBasic.petition_type_name,count(1) as count  "
//		+ sqlBody
//		+ " group by lowerBasic.petition_type_code,lowerBasic.petition_type_name order by lowerBasic.petition_type_code ";
//	}
	@Override
	public String geneCountSql(String sqlBody) {
		return super.ptnTypetj(sqlBody);
	}
	@Override
	public String getFieldName() {
		return "PETITION_TYPE_CODE";
	}
}
