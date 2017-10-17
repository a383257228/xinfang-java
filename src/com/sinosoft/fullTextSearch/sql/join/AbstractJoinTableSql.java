package com.sinosoft.fullTextSearch.sql.join;

public abstract class AbstractJoinTableSql implements JoinTableSql{
	public abstract String get(String joinType);
}
