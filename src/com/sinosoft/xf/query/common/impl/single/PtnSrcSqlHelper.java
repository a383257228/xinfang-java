package com.sinosoft.xf.query.common.impl.single;

import com.sinosoft.xf.query.common.JubaoException;

public class PtnSrcSqlHelper extends SingleSqlHelper {
	public PtnSrcSqlHelper() {
		super();
	}
	public PtnSrcSqlHelper(int type) {
		super(type);
	}
//	@Override
//	public String geneCountSql(int type) {
//		if(type!=BWALL)
//			throw new JubaoException("信访来源的sql语句体错误, 请检查调用参数是否为BusinessConstants.SHJI。");
//		String sqlBody = sqlBody(type);
//		return "select left(basic.petition_source_code,4) as petition_source_code,basic.petition_source_name,count(1) as count  "
//		+ sqlBody
//		+ " group by left(basic.petition_source_code,4) ,basic.petition_source_name ";
//	}
	
	@Override
	public String geneCountSql(String sqlBody) {
		return super.ptnSrctj(sqlBody);
	}
	@Override
	public String getFieldName() {
		return "PETITION_SOURCE_CODE";
	}
}
