package com.sinosoft.fullTextSearch.sql.join.impl;

import com.sinosoft.fullTextSearch.sql.join.AbstractJoinTableSql;

/**
 * 
 * @author lij
 * @date 2017年4月13日
 */
public class JoinIssueSql extends AbstractJoinTableSql {

	@Override
	public String get(String joinType) {
		return joinType+" join petition_issue_info issue "
				+ " on basic.oid = issue.petition_basic_info_oid";
	}


}
