package com.sinosoft.fullTextSearch.sql;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.fullTextSearch.sql.from.FromSql;
import com.sinosoft.fullTextSearch.sql.join.JoinTableSql;
import com.sinosoft.fullTextSearch.sql.select.SelectSql;
import com.sinosoft.fullTextSearch.sql.where.WhereSql;

public class Sql {
	
	public static final String JOIN_TYPE_INNER = " inner ";
	public static final String JOIN_TYPE_LEFT = " left ";
	public static final String JOIN_TYPE_RIGHT = " right ";

	private SelectSql selectSql;
	private FromSql fromSql;
	private List<Object[]> joinTableSqls = new ArrayList<Object[]>();
	private WhereSql whereSql;
	private final String WHERE_STRING = " where 1=1 ";
	
	public Sql() {
	}
	
	public Sql(SelectSql selectSql, FromSql fromSql, WhereSql whereSql){
		this.selectSql = selectSql;
		this.fromSql = fromSql;
		this.whereSql = whereSql;
	}
	
	public Sql addJoinTableSql(JoinTableSql joinTableSql, String joinType){
		joinTableSqls.add(new Object[]{joinTableSql,joinType});
		return this;
	}
	
	public Sql addJoinTableSql(JoinTableSql joinTableSql){
		addJoinTableSql(joinTableSql,JOIN_TYPE_INNER);
		return this;
	}
	
	public String toString() {
		//如果select语句不存在，抛出异常
		if(this.selectSql==null) throw new RuntimeException("Missing select statement!"); 
		//如果from语句不存在，抛出异常
		if(this.fromSql==null) throw new RuntimeException("Missing from statement!"); 
		//拼接select语句与from语句
		String sql= this.selectSql.get() + this.fromSql.get();
		//拼接join表连接语句
		if(joinTableSqls!=null){
			for(Object[] joinTable : joinTableSqls){
				JoinTableSql joinTableSql = (JoinTableSql) joinTable[0];
				String joinType = (String) joinTable[1];
				sql += joinTableSql.get(joinType);
			}
		}
		//拼接 where 1=1语句
		sql += WHERE_STRING;
		//拼接其他过滤条件
		if(whereSql!=null){
			sql += whereSql.get();
		}
		return sql;
	}

	public void setSelectSql(SelectSql selectSql) {
		this.selectSql = selectSql;
	}

	public void setFromSql(FromSql fromSql) {
		this.fromSql = fromSql;
	}


	public void setWhereSql(WhereSql whereSql) {
		this.whereSql = whereSql;
	}

	public String getWhereString() {
		return WHERE_STRING;
	}
	
	
}
