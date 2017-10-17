package com.sinosoft.fullTextSearch.sql.where.impl;

import com.sinosoft.fullTextSearch.sql.where.WhereSql;

public class WhereDeleteRecordTimestampSql implements WhereSql {

	@Override
	public String get() {
		return " AND INSERT_TIMESTAMP >= ("
				+ " SELECT max(extract_time) FROM ES_TIMESTAMP"
				+ " WHERE index_name = 'petitioninfo'"
				+ " AND index_type = 'petitionissue'" + "AND extract_type = '1'"
				+ " )";
	}

}
