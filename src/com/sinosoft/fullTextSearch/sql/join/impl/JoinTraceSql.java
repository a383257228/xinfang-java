package com.sinosoft.fullTextSearch.sql.join.impl;

import com.sinosoft.fullTextSearch.sql.join.AbstractJoinTableSql;

public class JoinTraceSql extends AbstractJoinTableSql{

	@Override
	public String get(String joinType) {
		return joinType+" join petition_circulation_state_trace_info trace "
				+ " on basic.oid = trace.petition_basic_info_oid";
	}

}
