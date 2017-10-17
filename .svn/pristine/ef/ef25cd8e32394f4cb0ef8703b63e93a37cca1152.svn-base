package com.sinosoft.xf.query.common.impl.chart.qs;

import java.sql.Timestamp;

import org.hibernate.Hibernate;
import org.hibernate.type.NullableType;
import org.hibernate.type.Type;

import com.runqian.report4.usermodel.Context;
import com.sinosoft.xf.query.common.BusinessHelper;
import com.sinosoft.xf.query.common.iface.SqlBody;
import com.sinosoft.xf.query.common.impl.chart.Range5SqlHelper;

public abstract class QSSqlHelper extends Range5SqlHelper {
	
	

	public QSSqlHelper(Context ctx) {
		super(ctx);
	}

	@Override
	protected int getSqlType() {
		return SqlBody.QS;
	}

	/**
	 * 生成sql查询参数
	 * 
	 * @author hjh
	 * @return 2015-1-30下午05:10:11
	 */
	@Override
	public Object[] geneArgs() {
		String regionCode = regionCode();
		String regionCodeLike = BusinessHelper.regionLikeValue(regionCode);
		Timestamp[] timeRange = geneTimeArgs();
		return new Object[] { regionCodeLike, timeRange[0], timeRange[1],
				regionCode };
	}

	/**
	 * 返回Hibernate参数类型（与geneArgs()）对应
	 * 
	 * @author hjh
	 * @return 2015-1-29下午05:50:07
	 */
	public Type[] argTypes() {
		return new NullableType[] { Hibernate.STRING, Hibernate.TIMESTAMP,
				Hibernate.TIMESTAMP, Hibernate.STRING };
	}
}
