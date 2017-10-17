package com.sinosoft.fullTextSearch.sql.where.impl;

import com.sinosoft.fullTextSearch.sql.where.WhereSql;

public class WhereTraceTimestampSql implements WhereSql{

	@Override
	public String get() {
		return " and trace.insert_time >= ( "
				+ "select max(extract_time) from es_timestamp where index_name = 'petitioninfo' and index_type = 'petitionissue'"
				+ ")";
	}

}
