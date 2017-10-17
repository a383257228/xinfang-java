package com.sinosoft.xf.query.common.impl.chart.bw;

import org.apache.commons.lang.StringUtils;

import com.runqian.report4.usermodel.Context;
import com.sinosoft.xf.query.common.JubaoException;
import com.sinosoft.xf.query.common.impl.chart.Range5SqlHelper;
import com.sinosoft.xf.query.view.ZQChartParams;

public class BWJTTJSqlHelper extends Range5SqlHelper {
	
	public BWJTTJSqlHelper(Context ctx){
		super(ctx);
	};
	
	@Override
	protected String sqlBody() {
		return super.sqlBody()
				+ " and lowerBasic.person_num>=5  and lowerBasic.PETITION_TYPE_CODE = 2 ";
	}

	@Override
	public String geneCountSql() {
		return super.jttj();
	}

	@Override
	public String[] fields() {
		return jtFields();
	}

	@Override
	public String condition(ZQChartParams params) {
		String condition = "";
		String ptnClsCode = params.getPetitionClassCode();
		if(StringUtils.isNotBlank(ptnClsCode)) {
			if(ptnClsCode.equals("123"))
				condition += " and lowerBasic.petition_class_code!='4'";
			else if(ptnClsCode.equals("4"))
				condition += " and lowerBasic.petition_class_code='4'";
			else 
				throw new JubaoException("集体访的petitionClassCode参数不合法");
		}
		return condition;
	}
	
	
}
