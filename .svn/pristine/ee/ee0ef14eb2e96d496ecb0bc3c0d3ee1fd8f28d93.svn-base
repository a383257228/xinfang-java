package com.sinosoft.xf.query.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.runqian.report4.usermodel.Context;
import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.query.common.iface.SqlHelper;

/**
 * 润乾宏，专门用于处理占位符。
 * 
 * @author hjh
 * 
 * 2015-1-30下午06:35:57
 */
public class Macros {
	private Map<String, String> macros = new HashMap<String, String>();
	private Context ctx;
	/**
	 * 非报表的宏初始化
	 */
	public Macros() {
		init();
	}
	/**
	 * 报表宏初始化
	 * @param ctx
	 */
	public Macros(Context ctx) {
		this();
		this.ctx = ctx;
		setTimeRangeMacro();
		setXFRegion();
	}

	private void init() {
		String[] macroNames = { "xf_region", "joinTableSql", "auth",
				"time_range","batch" };
		for (String macroName : macroNames)
			macros.put(macroName, "");
		setTimeRangeMacro(SqlHelper.NON);
		setAuthMacro();
		setBatchMacro();
	}

	/**
	 * 报表的权限串通过宏添加，查询前通过宏置换添加权限语句。单项查询则是构造基本sql体时构造权限语句
	 * 
	 * @author hjh 2015-1-28下午03:28:45
	 */
	public void setAuthMacro() {
		//macros.put("auth", SqlHelper.authSql());
	}
	/**
	 * 批转未接受的过滤语句
	 * 
	 * @author hjh 2015-1-28下午03:28:45
	 */
	public void setBatchMacro() {
		macros.put("batch", SqlHelper.batchSql());
	}

	/**
	 * 报表的时间宏，用于标记记录的时间区间。默认为CUSTOM
	 * 
	 * @author hjh 2015-1-28下午03:28:45
	 */
	public void setTimeRangeMacro() {
		setTimeRangeMacro(SqlHelper.CUSTOM);
	}

	/**
	 * 连接issue表
	 * @author hjh
	 * 2015-1-30下午10:26:44
	 */
	public void joinIssueTable() {
		macros.put("joinTableSql",
						" left join issue_type_info issue on issue.petition_basic_info_oid=lowerBasic.oid ");
	}
	
	public void setXFRegion(){
		macros.put("xf_region", geneXFRegionSql());
	}
	
	/**
	 * 设置地域权限参数——上海本委才需要加地域权限
	 * 
	 * @author hjh
	 * @param
	 * @return 2014-6-3下午10:19:29
	 */
	public String geneXFRegionSql() {
		String regions = (String) ctx.getParamValue("XFRegion");
		String sql = "";
		if (hasXFRegion() && StringUtils.isNotBlank(regions)) {
			String[] XFRegion = null;
			XFRegion = regions.split(";");
			sql += " and (";
			if (XFRegion != null) {
				sql += " basic.sub_issue_region_code in ( ";
				for (int i = 0; i < XFRegion.length; i++) {
					if (i == 0) {
						sql += "'" + XFRegion[i] + "'";
					} else {
						sql += ", '" + XFRegion[i] + "'";
					}
				}
				sql += " ) ";
			} else {
				sql += "1=1";
			}
			sql += ")";
		}
		return sql;
	}
	
	/**
	 * 应否增加地域权限条件的拼接
	 * @author hjh
	 * 2015年6月18日
	 * @return
	 */
	public boolean hasXFRegion(){
		// 只有上海本委才有此条件
//		String regionCode = SqlHelper.regionCode();
//		return Constants.SH_REGION_CDOE.equals(regionCode) ;
		return true;
	}

	/**
	 * 报表的时间宏，用于标记记录的时间区间。默认为CUSTOM. 可能的值：ChartSqlHelper.MONTH,
	 * ChartSqlHelper.YEAR, ChartSqlHelper.LAST_MONTH,
	 * ChartSqlHelper.LAST_YEAR_MONTH, ChartSqlHelper.LAST_YEAR,CUSTOM
	 * 
	 * @author hjh 2015-1-28下午03:28:45
	 */
	public void setTimeRangeMacro(String timeRange) {
		macros.put("time_range", "'"+timeRange+"'");
	}

	public Map<String, String> getMacros() {
		return macros;
	}

	public void setMacros(Map<String, String> macros) {
		this.macros = macros;
	}

}
