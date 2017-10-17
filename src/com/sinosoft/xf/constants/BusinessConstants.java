package com.sinosoft.xf.constants;

import java.util.Date;

import org.joda.time.DateTime;

import com.sinosoft.xf.query.common.DateSetters;
import com.sinosoft.xf.util.TimeUtils;

/**
 * 业务上需要的常量，主要是业务年开始结束月，业务月开始结束日， 国家节假日 调休需要上班的日
 */
public class BusinessConstants {
	public static final String BUSINESS_MONTH_START = "yyyy-MM-21";
	public static final String BUSINESS_MONTH_END = "yyyy-MM-20";
	public static final String BUSINESS_YEAR_START = "yyyy-12-21";
	public static final String BUSINESS_YEAR_END = "yyyy-12-20";
	public static final String BUSINESS_THREE_YEAR_BEFORE = "yyyy-01-01"; //专用于征询查询统计口径
	public static String holidayTime;// 国家规定节假日，格式2013-01-01
	public static String paidleaveTime;// 调休上班日，格式2013-01-01
	public static int monthStartDay = 21;
	public static int monthEndDay = 20;
	
	
	public static final String SHJ_CODE = " ('0201','0202') "; //上级转交办的code
	
	/**
	 * 时间范围常量
	 * @author hjh
	 *
	 * 2014-8-15下午04:25:33
	 */
	/*public enum TimeRange{
		LAST_YEAR("LAST_YEAR"),YEAR("YEAR"),MONTH("MONTH"),LAST_MONTH("LAST_MONTH"),
		LAST_YEAR_MONTH("LAST_YEAR_MONTH");
		private String v;
		TimeRange(String value){
			this.v=value;
		}
		@Override
		public String toString() {
			return v;
		}
	}*/
	/**
	 * 上一月业务月开始时间
	 */
	public static String lastMonthStart;
	/**
	 *  上一月业务月结束时间
	 */
	public static String lastMonthEnd;
	/**
	 * 上一年业务年开始时间
	 */
	public static String lastYearStart;
	/**
	 *  上一年业务年结束时间
	 */
	public static String lastYearEnd;
	/**
	 *  上一年业务年同比结束时间(本月为7月，则上年截至统计日期也为7月，而非12月20日)
	 *  by hjh
	 */
	public static String lastYearEnd2;
	/**
	 *  上一年业务年同月同比结束时间
	 *  by hjh
	 */
	public static String lastYearMonthEnd2;
	/**
	 *  上一年业务年同月同比开始时间
	 *  by hjh
	 */
	public static String lastYearMonthStart;
	/**
	 *  业务月开始时间,默认上月21号
	 */
	public static String monthStart;
	/**
	 *  业务月结束时间,默认当月20号
	 */
	public static String monthEnd;
	/**
	 *  业务年开始时间,默认去年12月21号
	 */
	public static String yearStart;
	/**
	 *  业务年结束时间,默认当年12月20号
	 */
	public static String yearEnd;
	/**
	 * 业务开始五年前
	 */
	public static String yearStartFiveYearBefore;
	/**
	 * 业务开始三年前
	 */
	public static String yearStartThreeYearBefore;
	/**
	 * 征询查询三年前
	 */
	public static String yearStartThreeYearBeforeConsult;
	/**
	 * 初始化各个时间
	 */
	public static void getInstance() {
		try {
			DateTime dateTime = new DateTime();
			yearStart = dateTime.minusYears(1).toString(BUSINESS_YEAR_START);
			yearEnd = dateTime.toString(BUSINESS_YEAR_END);
			lastYearStart = dateTime.minusYears(2).toString(BUSINESS_YEAR_START);
			lastYearEnd = dateTime.minusYears(1).toString(BUSINESS_YEAR_END);
			yearStartFiveYearBefore = dateTime.minusYears(5).toString(BUSINESS_YEAR_START);
			yearStartThreeYearBefore = dateTime.minusYears(3).toString(BUSINESS_YEAR_START);
			lastMonthStart = dateTime.minusMonths(2).toString(BUSINESS_MONTH_START);
			lastMonthEnd = dateTime.minusMonths(1).toString(BUSINESS_MONTH_END);
			monthStart = dateTime.minusMonths(1).toString(BUSINESS_MONTH_START);
			monthEnd = dateTime.toString(BUSINESS_MONTH_END);
			if(dateTime.getDayOfMonth()>20){//21号以后为下个月
				monthStart = dateTime.toString(BUSINESS_MONTH_START);
				monthEnd = dateTime.plusMonths(1).toString(BUSINESS_MONTH_END);
				lastMonthStart = dateTime.minusMonths(1).toString(BUSINESS_MONTH_START);
				lastMonthEnd = dateTime.toString(BUSINESS_MONTH_END);
				if(dateTime.getMonthOfYear()==12){//12月20号以后为下一年
					yearStart = dateTime.toString(BUSINESS_YEAR_START);
					yearEnd = dateTime.plusYears(1).toString(BUSINESS_YEAR_END);
					lastYearStart = dateTime.minusYears(1).toString(BUSINESS_YEAR_START);
					lastYearEnd = dateTime.toString(BUSINESS_YEAR_END);
					yearStartFiveYearBefore = dateTime.minusYears(4).toString(BUSINESS_YEAR_START);
					yearStartThreeYearBefore = dateTime.minusYears(2).toString(BUSINESS_YEAR_START);
				}
			}
			yearStartThreeYearBeforeConsult = dateTime.minusYears(3).toString(BUSINESS_THREE_YEAR_BEFORE);
			lastYearMonthEnd2 = DateSetters.setLastyearMonthEndDay2(TimeUtils.date2Str(new Date(), Constants.Date_Short));
			lastYearEnd2 = lastYearMonthEnd2;
			lastYearMonthStart = DateSetters.setLastyearMonthStartDay(TimeUtils.date2Str(new Date(), Constants.Date_Short));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
