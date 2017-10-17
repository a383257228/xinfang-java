package com.sinosoft.fullTextSearch.sql.from.impl;

import com.sinosoft.fullTextSearch.sql.from.FromSql;

public class FromDeleteRecordSql implements FromSql{

	@Override
	public String get() {
		return " FROM DELETE_OID_RECORD ";
	}

}
