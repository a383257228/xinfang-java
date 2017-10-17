package com.sinosoft.xf.query.common.impl.single;

import org.springframework.util.Assert;

/**
 * 上级转交办件查询Sql
 * @author hjh
 *
 * 2015-1-22下午05:21:24
 */
public class ShjiSqlHelper extends SingleSqlHelper {
	public ShjiSqlHelper() {
		super();
	}
	public ShjiSqlHelper(int type) {
		super(type);
	}
//	@Override
//	public String geneCountSql(int type) {
////		if(type!=BWALL)
////			throw new JubaoException("上级转交办的sql语句体错误, 请检查调用参数是否为BusinessConstants.SHJI。");
//		Assert.isTrue(type!=BWALL);
//		String jujiSql = " and basic.petition_source_code in  ('0201','0202') ";
//		return super.geneCountSql(type) + jujiSql;
//	}
	@Override
	public String condition() {
		return " and basic.petition_source_code in  ('0201','0202') ";
	}

}
