package com.sinosoft.xf.query.common.iface;

import java.sql.Timestamp;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sinosoft.xf.constants.BusinessConstants;
import com.sinosoft.xf.query.common.Macros;
import com.sinosoft.xf.util.TimeUtils;

/**
 * 生成sql基本语句体和基本参数
 * @author hjh
 *
 * 2015-2-7下午10:20:45
 */
public abstract class SqlBody {
	
	/**
	 * 全市标记，表示基本sql用于统计全市数据
	 */
	public static final int QS = 0;
	/**
	 * 本委标记，表示基本sql用于统计本委数据（默认去除上级转交办）
	 */
	public static final int BW = 1;
	
	/**
	 * 查询本委全部信访件标记，与BW 的区别在于，BWALL不会默认去除上级转交办的
	 */
	public static final int BWALL = 2;
	/**
	 * 单项查询本委标记，根据需求改变为BW或BWALL
	 */
	public static final int SINGLE_BW = BW;
	/**
	 * 本委基础量统计（不含上级）
	 */
	public static final int BWJCL = 3;
	/**
	 * 本委基础量统计（含上级）
	 */
	public static final int BWALLJCL = 4;
	/**
	 * 全市基础量统计（含上级）
	 */
	public static final int QSJCL = 5;
	
	/**
	 * 默认sql语句体
	 */
	public static final int DEFAULT_SQLBODY = BWALL;

	/**
	 * 自定义时间范围
	 */
	public static final String CUSTOM = "CUSTOM";
	/**
	 * 上年同期（上年业务年年初至上年本月本天）
	 */
	public static final String LAST_YEAR = "LAST_YEAR";
	/**
	 * 上年（业务年全年）
	 */
	public static final String LAST_YEAR_BUSINESS = "LAST_YEAR_BUSINESS";
	/**
	 * 本年
	 */
	public static final String YEAR = "YEAR";
	/**
	 * 本月
	 */
	public static final String MONTH = "MONTH";
	/**
	 * 上月
	 */
	public static final String LAST_MONTH = "LAST_MONTH";
	/**
	 * 上年本月同期
	 */
	public static final String LAST_YEAR_MONTH = "LAST_YEAR_MONTH";
	/**
	 * 五年统计
	 */
	public static final String FIVE_YEARS = "FIVE_YEARS";
	
	/**
	 * 全市/全区去除上级转交办sql
	 */
	private static String sqlBodyQS;
	/**
	 * 本委去除上级转交办sql
	 */
	private static String sqlBodyBW;
	/**
	 * 本委所有信访件(未去除上级转交办)
	 */
	private static String sqlBodyBWAll;
	/**
	 * 本委所有信访件基础量(去除上级转交办)
	 */
	private static String sqlBodyBWJCL;
	/**
	 * 本委所有信访件基础量(未去除上级转交办)
	 */
	private static String sqlBodyBWALLJCL;
	/**
	 * 全市/全区基础量sql
	 */
	private static String sqlBodyQSJCL;
	
	/**
	 * 单项查询钻取的select语句
	 */
	private static String singleDetailedSelect;
	
	/**
	 * 基本sql语句体
	 */
	private String priSqlBody;
	/**
	 * 基础量sql语句体
	 */
	private String jclSqlBody;
	
	/**
	 * sql宏替换语句容器
	 */
	private Macros macros;
	static{
		initSqlBody();
	}
	public SqlBody(){
		initPriSqlBody();
		initJclSqlBody();
		macros = new Macros();
	}
	
	/**
	 * 获取所有sql基本语句体
	 * @author hjh
	 * 2015-1-30下午01:20:42
	 */
	public static void initSqlBody(){
		String fromSql = gainSqlComponent("fromSql");
		String whereSqlQS = gainSqlComponent("whereSqlQS");
		String whereSqlBW = gainSqlComponent("whereSqlBW");
		String whereSqlBWAll = gainSqlComponent("whereSqlBWAll");
		String sqlBWJCL = gainSqlComponent("bwJCLCount");
		String sqlBWALLJCL = gainSqlComponent("bwALLJCLCount");
		sqlBodyQS = fromSql + whereSqlQS;
		sqlBodyBW = fromSql + whereSqlBW;
		sqlBodyBWAll = fromSql + whereSqlBWAll;
		sqlBodyBWJCL = sqlBWJCL;
		sqlBodyBWALLJCL = sqlBWALLJCL;
		sqlBodyQSJCL = gainSqlComponent("qsJCLCount");
		singleDetailedSelect = gainSqlComponent("singleDetailedSelect");
		
	}
	/**
	 * 获取基本sql语句体,默认为本委全部（包含上级）
	 * @author hjh
	 * 2015-1-30下午01:20:42
	 */
	protected void initPriSqlBody(){
		throw new UnsupportedOperationException();
//		setPriSqlBody(sqlBodyBWAll);
	};
	/**
	 * 获取基础量sql语句体
	 * @author hjh
	 * 2015-1-30下午01:20:42
	 */
	protected void initJclSqlBody(){
		throw new UnsupportedOperationException();
	};
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
	public abstract Object[] geneArgs(String timeRange);

	/**
	 * 根据时间范围和当前业务时间，得出查询字段actual_date相应值 例{"2014-01","2014-12"}
	 * 
	 * @author hjh
	 * @param timeRange
	 * @return 2015-1-21上午10:12:24
	 */
	public static Timestamp[] timeArgs(String timeRange) {
		BusinessConstants.getInstance();
		if (timeRange.equals(YEAR)) {
			return new Timestamp[] {
					TimeUtils.string2Date(BusinessConstants.yearStart),
					TimeUtils.string2Date(BusinessConstants.yearEnd) };
		} else if (timeRange.equals(LAST_YEAR)) {
			return new Timestamp[] {
					TimeUtils.string2Date(BusinessConstants.lastYearStart),
					TimeUtils.string2Date(BusinessConstants.lastYearEnd2) };
		} else if (timeRange.equals(LAST_YEAR_BUSINESS)) {
			return new Timestamp[] {
					TimeUtils.string2Date(BusinessConstants.lastYearStart),
					TimeUtils.string2Date(BusinessConstants.lastYearEnd) };
		} else if (timeRange.equals(SqlHelper.MONTH)) {
			return new Timestamp[] {
					TimeUtils.string2Date(BusinessConstants.monthStart),
					TimeUtils.string2Date(BusinessConstants.monthEnd) };
		} else if (timeRange.equals(LAST_YEAR_MONTH)) {
			return new Timestamp[] {
					TimeUtils.string2Date(BusinessConstants.lastYearMonthStart),
					TimeUtils.string2Date(BusinessConstants.lastYearMonthEnd2) };
		} else if (timeRange.equals(FIVE_YEARS)) {
			return new Timestamp[] {
					TimeUtils
							.string2Date(BusinessConstants.yearStartFiveYearBefore),
					TimeUtils.string2Date(BusinessConstants.yearEnd) };
		} else
			throw new RuntimeException("timeRange参数错误！");
	}
	
	public static Document commonSqlXml;
	
	/**
	 * 获取commonSql.xml文件中的sql元件
	 * 
	 * @author hjh
	 * @param elementName
	 * @return 2015-1-20下午04:35:23
	 */
	public static String gainSqlComponent(String elementName) {
		if(commonSqlXml==null){
			try {
				commonSqlXml = new SAXReader().read(SqlHelper.class
						.getResource("commonSql.xml"));
			} catch (DocumentException e) {
				e.printStackTrace();
			}// 当前类下
		}
		
		Element root = commonSqlXml.getRootElement();
		return root.elementText(elementName);
	}
	public String getPriSqlBody() {
		return priSqlBody;
	}
	public void setPriSqlBody(String priSqlBody) {
		this.priSqlBody = priSqlBody;
	}
	public String getJclSqlBody() {
		return jclSqlBody;
	}
	public void setJclSqlBody(String jclSqlBody) {
		this.jclSqlBody = jclSqlBody;
	}

	protected static String getSqlBodyQS() {
		return sqlBodyQS;
	}

	protected static void setSqlBodyQS(String sqlBodyQS) {
		SqlBody.sqlBodyQS = sqlBodyQS;
	}

	protected static String getSqlBodyBW() {
		return sqlBodyBW;
	}

	protected static void setSqlBodyBW(String sqlBodyBW) {
		SqlBody.sqlBodyBW = sqlBodyBW;
	}

	protected static String getSqlBodyBWAll() {
		return sqlBodyBWAll;
	}

	protected static void setSqlBodyBWAll(String sqlBodyBWAll) {
		SqlBody.sqlBodyBWAll = sqlBodyBWAll;
	}

	protected static String getSqlBodyBWJCL() {
		return sqlBodyBWJCL;
	}

	protected static void setSqlBodyBWJCL(String sqlBodyBWJCL) {
		SqlBody.sqlBodyBWJCL = sqlBodyBWJCL;
	}

	protected static String getSqlBodyBWALLJCL() {
		return sqlBodyBWALLJCL;
	}

	protected static void setSqlBodyBWALLJCL(String sqlBodyBWALLJCL) {
		SqlBody.sqlBodyBWALLJCL = sqlBodyBWALLJCL;
	}

	protected static String getSqlBodyQSJCL() {
		return sqlBodyQSJCL;
	}

	protected static void setSqlBodyQSJCL(String sqlBodyQSJCL) {
		SqlBody.sqlBodyQSJCL = sqlBodyQSJCL;
	}

	public Macros getMacros() {
		return macros;
	}

	public void setMacros(Macros macros) {
		this.macros = macros;
	}

	public static String getSingleDetailedSelect() {
		return singleDetailedSelect;
	}
	

}
