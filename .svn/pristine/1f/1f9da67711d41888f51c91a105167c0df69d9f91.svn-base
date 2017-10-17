package com.sinosoft.xf.query.common.impl.single;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.constants.BusinessConstants;
import com.sinosoft.xf.query.common.Macros;
import com.sinosoft.xf.query.common.factory.SqlBodyFactory;
import com.sinosoft.xf.query.common.iface.SqlBody;
import com.sinosoft.xf.query.common.iface.SqlHelper;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 单项查询
 * 
 * @author hjh
 * 
 * 2015-2-12下午05:30:35
 */
public abstract class SingleSqlHelper extends SqlHelper {
	private static SqlBodyFactory sqlBodyFactory = new SqlBodyFactory();
	private SqlBody sqlBody;
	

	public SingleSqlHelper() {
		this(SqlBody.DEFAULT_SQLBODY);
	}

	public SingleSqlHelper(int type) {
		sqlBody = sqlBodyFactory.createSqlBody(type);
		macros = sqlBody.getMacros();
	}
	/**
	 * 生成统计数据的查询sql，默认情况下生成直接查总数的sql
	 * 
	 * @author hjh
	 * @param sqlBody sql语句体，通常包括from和where
	 * @return 2015-1-22下午04:31:28
	 */
	public String geneCountSql(String sqlBody) {
		return acceptCount(sqlBody);
	}
	/**
	 * 返回统计数据的sql
	 * @author hjh
	 * @return
	 * 2015-2-7下午09:55:34
	 */
	public String geneStatisSql() {
		String tempSql = geneCountSql(sqlBody() + " " + condition());
		String sql = replacePlaceholder(tempSql,getMacros().getMacros());
		return sql;
	}
	/**
	 * 单项查询sql钻取
	 * @author hjh
	 * @return
	 * 2015-2-13下午05:38:24
	 */
//	public String singleDetailedSql() {
//		String select = sqlBody.getSingleDetailedSelect()+",rownumber() over(order by basic.create_date) as rownumber_ ";
//		String tempSql = select + sqlBody() + " " + condition() + " order by basic.create_date ";
//		return tempSql;
//	}
	protected String sqlBody(){
		return sqlBody.getPriSqlBody();
	}
	/**
	 * 通常由子类复写，添加自定义and条件
	 * @author hjh
	 * @return
	 * 2015-2-7下午10:37:49
	 */
	public String condition(){
//		return SqlUtil.NOT_SHJI/*+SqlUtil.mrChuCondition()*/;
		PersonInfo personInfo = (PersonInfo) Struts2Utils.getSessionAttribute("personInfo");
		return " and not( basic.region_code="+"'"+personInfo.getRegionCode()+"'"+" and basic.petition_source_code in "+BusinessConstants.SHJ_CODE+") ";
//		
	}
	/**
	 * 受理数统计
	 * 
	 * @author hjh
	 * @param sqlBody
	 * @return 2015-1-20下午04:37:19
	 */
	private String acceptCount(String sqlBody) {
		return "select count(distinct basic.petition_no) as count " + sqlBody;
	}
	// @Override
	protected String wtdltj(String sqlBody) {
		return "select left(mainIssue.issue_type_code,4)  as issue_type_code ,${time_range} as time_range, count(1) as count  "
				+ sqlBody
				+ " group by left(mainIssue.issue_type_code,4) order by count desc ";
	}

	/**
	 * 月趋势统计
	 * 
	 * @author hjh
	 * @param sqlBody
	 * @return 2015-2-7下午11:26:08
	 */
	protected String monthTrendtj(String sqlBody) {
		return "select count(basic.oid) as num, basic.actual_date  as type "
				+ sqlBody + " group by  basic.actual_date  order by type ";
	}

	/**
	 * 年趋势统计
	 * 
	 * @author hjh
	 * @param sqlBody
	 * @return 2015-2-7下午11:26:08
	 */
	protected String yearTrendtj(String sqlBody) {
		return "select count(basic.oid) as num, left(basic.actual_date,4)  as type "
				+ sqlBody + " group by  left(basic.actual_date,4)  order by type ";
	}

	/**
	 * 职级统计
	 * 
	 * @author hjh
	 * @param sqlBody
	 * @return 2015-2-7下午11:26:08
	 */
	protected String objClstj(String sqlBody) {
		return "select lowerBasic.object_class_code,lowerBasic.object_class_name,count(1) as count  "
				+ sqlBody
				+ " group by lowerBasic.object_class_code,lowerBasic.object_class_name ";
	}

	/**
	 * 信访类别统计
	 * 
	 * @author hjh
	 * @param sqlBody
	 * @return 2015-2-7下午11:26:08
	 */
	protected String ptnClstj(String sqlBody) {
		return "select lowerBasic.petition_class_code,lowerBasic.petition_class_name,count(1) as count  "
				+ sqlBody
				+ " group by lowerBasic.petition_class_code,lowerBasic.petition_class_name order by lowerBasic.petition_class_code ";
	}

	/**
	 * 信访来源统计
	 * 
	 * @author hjh
	 * @param sqlBody
	 * @return 2015-2-7下午11:26:08
	 */
	protected String ptnSrctj(String sqlBody) {
		return "select left(basic.petition_source_code,4) as petition_source_code,basic.petition_source_name,count(1) as count  "
				+ sqlBody
				+ " group by left(basic.petition_source_code,4) ,basic.petition_source_name ";
	}

	/**
	 * 信访方式统计
	 * 
	 * @author hjh
	 * @param sqlBody
	 * @return 2015-2-7下午11:26:08
	 */
	protected String ptnTypetj(String sqlBody) {
		return "select lowerBasic.petition_type_code,lowerBasic.petition_type_name,count(1) as count  "
				+ sqlBody
				+ " group by lowerBasic.petition_type_code,lowerBasic.petition_type_name order by lowerBasic.petition_type_code ";
	}

	/**
	 * 单位性质统计
	 * 
	 * @author hjh
	 * @param sqlBody
	 * @return 2015-2-7下午11:26:08
	 */
	protected String workTypetj(String sqlBody) {
		return "select accused.Accused_Work_Type_Code,accused.Accused_Work_Type_Name,count(1) as count  "
				+ sqlBody
				+ " group by accused.Accused_Work_Type_Code,accused.Accused_Work_Type_Name order by accused.Accused_Work_Type_Code ";
	}
	private Macros macros;
	public Macros getMacros() {
		return macros;
	}

	public void setMacros(Macros macros) {
		this.macros = macros;
	}
	
	
	/**
	 * 获取结果集中属性的别名
	 * 
	 * @author hjh
	 * @return 2015-1-22下午07:23:54
	 */
	public String getFieldName() {
		return "COUNT";
	}
	
	/**
	 * 根据sql类型和时间范围生成参数
	 * 
	 * @author hjh
	 * @param type
	 *            查全市或查本委 SqlHelper.QS或SqlHelper.BW
	 * @param timeRange
	 *            如：SqlHelper.YEAR
	 * @return 2015-1-21下午01:06:24
	 */
	public Object[] geneArgs(String timeRange) {
		return sqlBody.geneArgs(timeRange);
	}
}
