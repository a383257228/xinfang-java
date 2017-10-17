package com.sinosoft.fullTextSearch.sql.test;

import org.junit.Test;

import com.sinosoft.fullTextSearch.sql.Sql;
import com.sinosoft.fullTextSearch.sql.from.impl.FromBasicSql;
import com.sinosoft.fullTextSearch.sql.join.JoinTableSql;
import com.sinosoft.fullTextSearch.sql.join.impl.JoinIssueSql;
import com.sinosoft.fullTextSearch.sql.join.impl.JoinTraceSql;
import com.sinosoft.fullTextSearch.sql.select.impl.SelectDistinctPetitionIssueSql;
import com.sinosoft.fullTextSearch.sql.where.impl.WhereTraceTimestampSql;

public class TestSql {

	@Test
	public void test(){
		Sql sql = new Sql();
		sql.setSelectSql(new SelectDistinctPetitionIssueSql());
		sql.setFromSql(new FromBasicSql());
		sql.addJoinTableSql(new JoinIssueSql());
		sql.addJoinTableSql(new JoinTraceSql(),Sql.JOIN_TYPE_LEFT);
		sql.setWhereSql(new WhereTraceTimestampSql());
		System.out.println(sql.toString());
	}
}
