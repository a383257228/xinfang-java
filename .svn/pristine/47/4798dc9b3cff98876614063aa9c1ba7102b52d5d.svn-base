package com.sinosoft.xf.query.common.factory;

import com.runqian.report4.usermodel.Context;
import com.sinosoft.xf.query.common.iface.ChartSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.ShjiChartSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.ajt.AJTIssueZLTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.ajt.AJTJCLSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.ajt.AJTJTTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.ajt.AJTRSTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.ajt.AJTWTDLTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.ajt.AJTWTTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.ajt.AJTZLTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.bw.BWJCLSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.bw.BWJTTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.bw.BWRSTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.bw.BWWTDLTJNSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.bw.BWWTDLTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.bw.BWWTTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.bw.BWZLTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.juji.JujiJCLSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.juji.JujiJTTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.juji.JujiRSTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.juji.JujiWTDLTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.juji.JujiWTTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.juji.JujiZLTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.qs.QSJCLSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.qs.QSJTTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.qs.QSRSTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.qs.QSWTDLTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.qs.QSWTTJSqlHelper;
import com.sinosoft.xf.query.common.impl.chart.qs.QSZLTJSqlHelper;

/**
 * SqlHelper对象生产工厂
 * 
 * @author hjh
 * 
 * 2015-1-22下午03:58:51
 */
public class ChartSqlHelperFactory {

	/**
	 * AJT 总量统计
	 */
	public static final String AJT_ZLTJ = "ajtzltj";
	/**
	 * AJT 基础量统计
	 */
	public static final String AJT_JCLTJ = "ajtjcltj";
	/**
	 * AJT 重复件统计和署名件统计
	 */
	public static final String AJT_RSTJ = "ajtrstj";
	/**
	 * AJT 重复件统计和署名件统计
	 */
	public static final String AJT_WTDLTJ = "ajtwtdltj";
	/**
	 * 上级转交办统计
	 */
	public static final String SJJBTJ = "sjjbtj";
	/**
	 * AJT集体统计
	 */
	public static final String AJT_JTTJ = "ajtjttj";
	/**
	 * AJT问题总量统计（业务范围外）
	 */
	public static final String AJT_ISZLTJ = "ajtiszltj";
	/**
	 * AJT问题统计（表二、表三）
	 */
	public static final String AJT_WTTJ = "ajtwttj";
	/**
	 * 本委总量统计
	 */
	public static final String BW_ZLTJ = "bwzltj";
	/**
	 * 本委问题大类统计(问题类别为4位)
	 */
	public static final String BW_WTDLTJ = "bwwtdltj";
	/**
	 * 本委问题大类统计(问题类别为6位)
	 */
	public static final String BW_WTDLTJN = "bwwtdltjn";
	/**
	 * 本委重复件、署名举报统计
	 */
	public static final String BW_RSTJ = "bwrstj";
	/**
	 * 本委集体访统计
	 */
	public static final String BW_JTTJ = "bwjttj";
	/**
	 * 本委基础量统计
	 */
	public static final String BW_JCLTJ = "bwjcltj";
	/**
	 * 本委问题统计
	 */
	public static final String BW_WTTJ = "bwwttj";
	/**
	 * 全市/全区总量统计
	 */
	public static final String QS_ZLTJ = "qszltj";
	/**
	 * 全市/全区问题大类统计
	 */
	public static final String QS_WTDLTJ = "qswtdltj";
	/**
	 * 全市/全区重复件、署名举报统计
	 */
	public static final String QS_RSTJ = "qsrstj";
	/**
	 * 全市/全区集体访统计
	 */
	public static final String QS_JTTJ = "qsjttj";
	/**
	 * 全市/全区基础量统计
	 */
	public static final String QS_JCLTJ = "qsjcltj";
	/**
	 * 全市/全区问题统计
	 */
	public static final String QS_WTTJ = "qswttj";
	/**
	 * 局级总量统计
	 */
	public static final String Juji_ZLTJ = "jujizltj";
	/**
	 * 局级问题大类统计
	 */
	public static final String Juji_WTDLTJ = "jujiwtdltj";
	/**
	 * 局级重复件、署名举报统计
	 */
	public static final String Juji_RSTJ = "jujirstj";
	/**
	 * 局级集体访统计
	 */
	public static final String Juji_JTTJ = "jujijttj";
	/**
	 * 局级基础量统计
	 */
	public static final String Juji_JCLTJ = "jujijcltj";
	/**
	 * 局级问题统计
	 */
	public static final String Juji_WTTJ = "jujiwttj";

	/**
	 * 创建具体SqlHelper对象
	 * 
	 * @author hjh
	 * @param queryItemStr
	 * @return 2015-1-23下午03:20:22
	 */
	public ChartSqlHelper createSqlHelper(String queryItemStr, Context ctx) {
		try {
			String queryItem = queryItemStr.toLowerCase();
			if (AJT_ZLTJ.equals(queryItem))
				return new AJTZLTJSqlHelper(ctx);
			else if (AJT_JCLTJ.equals(queryItem))
				return new AJTJCLSqlHelper(ctx);
			else if (AJT_RSTJ.equals(queryItem))
				return new AJTRSTJSqlHelper(ctx);
			else if (AJT_WTDLTJ.equals(queryItem))
				return new AJTWTDLTJSqlHelper(ctx);
			else if (SJJBTJ.equals(queryItem))
				return new ShjiChartSqlHelper(ctx);
			else if (AJT_JTTJ.equals(queryItem))
				return new AJTJTTJSqlHelper(ctx);
			else if (AJT_ISZLTJ.equals(queryItem))
				return new AJTIssueZLTJSqlHelper(ctx);
			else if (AJT_WTTJ.equals(queryItem))
				return new AJTWTTJSqlHelper(ctx);
			else if (BW_ZLTJ.equals(queryItem))
				return new BWZLTJSqlHelper(ctx);
			else if (BW_WTDLTJN.equals(queryItem))
				return new BWWTDLTJNSqlHelper(ctx);
			else if (BW_WTDLTJ.equals(queryItem))
				return new BWWTDLTJSqlHelper(ctx);
			else if (BW_RSTJ.equals(queryItem))
				return new BWRSTJSqlHelper(ctx);
			else if (BW_JTTJ.equals(queryItem))
				return new BWJTTJSqlHelper(ctx);
			else if (BW_JCLTJ.equals(queryItem))
				return new BWJCLSqlHelper(ctx);
			else if (BW_WTTJ.equals(queryItem))
				return new BWWTTJSqlHelper(ctx);
			else if (QS_ZLTJ.equals(queryItem))
				return new QSZLTJSqlHelper(ctx);
			else if (QS_WTDLTJ.equals(queryItem))
				return new QSWTDLTJSqlHelper(ctx);
			else if (QS_RSTJ.equals(queryItem))
				return new QSRSTJSqlHelper(ctx);
			else if (QS_JTTJ.equals(queryItem))
				return new QSJTTJSqlHelper(ctx);
			else if (QS_JCLTJ.equals(queryItem))
				return new QSJCLSqlHelper(ctx);
			else if (QS_WTTJ.equals(queryItem))
				return new QSWTTJSqlHelper(ctx);
			else if (Juji_ZLTJ.equals(queryItem))
				return new JujiZLTJSqlHelper(ctx);
			else if (Juji_WTDLTJ.equals(queryItem))
				return new JujiWTDLTJSqlHelper(ctx);
			else if (Juji_RSTJ.equals(queryItem))
				return new JujiRSTJSqlHelper(ctx);
			else if (Juji_JTTJ.equals(queryItem))
				return new JujiJTTJSqlHelper(ctx);
			else if (Juji_JCLTJ.equals(queryItem))
				return new JujiJCLSqlHelper(ctx);
			else if (Juji_WTTJ.equals(queryItem))
				return new JujiWTTJSqlHelper(ctx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断该数据集查询是否已被重构, 是则使用新方法查询(AJT)
	 * 
	 * @author hjh
	 * @param propertyName
	 *            前台传来的值，用于确定统计项
	 * @return 2015-1-22下午07:04:25
	 */
	public static boolean hasRefactored(String datasetName) {
		String[] items = { AJT_ZLTJ, AJT_JCLTJ, AJT_RSTJ, AJT_WTDLTJ, SJJBTJ,
				AJT_JTTJ, AJT_ISZLTJ, AJT_WTTJ };
		for (String item : items) {
			if (datasetName.toLowerCase().equals(item))
				return true;
		}
		return false;
	}

}
