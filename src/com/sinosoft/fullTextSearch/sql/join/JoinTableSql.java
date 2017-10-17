package com.sinosoft.fullTextSearch.sql.join;

public interface JoinTableSql {
	public String joinType = " inner ";
	public String get(String joinType);
}
