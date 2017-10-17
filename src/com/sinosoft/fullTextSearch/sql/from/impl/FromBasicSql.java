package com.sinosoft.fullTextSearch.sql.from.impl;

import com.sinosoft.fullTextSearch.sql.from.FromSql;

public class FromBasicSql implements FromSql {

	@Override
	public String get() {
		String sql = " from petition_basic_info basic ";
		return sql;
	}

}
