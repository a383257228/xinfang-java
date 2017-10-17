package com.sinosoft.xf.query.common.factory;

import com.sinosoft.xf.query.common.iface.BWALLSqlBody;
import com.sinosoft.xf.query.common.iface.BWSqlBody;
import com.sinosoft.xf.query.common.iface.QSSqlBody;
import com.sinosoft.xf.query.common.iface.SqlBody;

public class SqlBodyFactory {

	public SqlBody createSqlBody(int type){
		if(SqlBody.BW==type){
			return new BWSqlBody();
		}else if(SqlBody.QS==type){
			return new QSSqlBody();
		}else if(SqlBody.BWALL==type)
			return new BWALLSqlBody();
		else if(SqlBody.DEFAULT_SQLBODY==type)
			return new BWALLSqlBody();
		return null;
	}
}
