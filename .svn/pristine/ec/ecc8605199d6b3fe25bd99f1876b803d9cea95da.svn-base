package com.sinosoft.xf.query.common;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

import com.sinosoft.xf.constants.Constants;
import com.sinosoftframework.core.utils.date.DateHelper;

/**
 * //需求更改：当月统计统计日期为：上月21日-本月20日
 *	//需求更改：当年统计统计日期为：上年11月21日-本年11月20日
 *  //需求： 若传入来的字符串为2014-03-02，则本月统计开始日期为2014-02-21，截止统计日期为2014-02-20
 *  		若传入来的字符串为2014-03-22，则本月统计开始日期为2014-03-21，截止统计日期为2014-04-20
 *  上月日期和上年日期都根据本月、本年日期运算出结果。
 *  
 * @author hjh
 *
 */
public class DateSetters {
	
	/**
	 * 本月开始
	 */
	protected DateTime monthStart;
	/**
	 * 本月结束
	 */
	protected DateTime monthEnd;
	/**
	 * 本年开始
	 */
	protected DateTime yearStart;
	/**
	 * 本年结束
	 */
	protected DateTime yearEnd;
	/**
	 * 去年开始
	 */
	protected DateTime lastYearStart;
	/**
	 * 去年结束
	 */
	protected DateTime lastYearEnd;
	/**
	 * 上月开始
	 */
	protected DateTime lastMonthStart;
	/**
	 * 上月结束
	 */
	protected DateTime lastMonthEnd;
	/**
	 * 去年本月开始
	 */
	protected DateTime lastYearMonthStart;
	/**
	 * 去年本月结束
	 */
	protected DateTime lastYearMonthEnd;
	
	public DateSetters(String dateStr){
		calc5Date(dateStr);
	}
	public DateSetters(){
		monthEnd = new DateTime();
		initTimeRanges(monthEnd);
	}
	
	/**
	 * 根据截止日期计算出5个时间统计范围
	 * @author hjh
	 * @param dateStr
	 * 2015-1-31下午12:21:43
	 */
	public void calc5Date(String dateStr){
		monthEnd = new DateTime(dateStr);
		initTimeRanges(monthEnd);
	}
	
	private void initTimeRanges(DateTime monthEnd){
		if(monthEnd.getDayOfMonth()>=21)
			monthStart = new DateTime(monthEnd.toString("yyyy-MM-21"));
		else
			monthStart = new DateTime(monthEnd.minusMonths(1).toString("yyyy-MM-21"));
		yearEnd = monthEnd;
		if(yearEnd.getMonthOfYear()==12 && yearEnd.getDayOfMonth()>=21)
			yearStart = new DateTime(yearEnd.toString("yyyy-12-21"));
		else
			yearStart = new DateTime(yearEnd.minusYears(1).toString("yyyy-12-21"));
		lastMonthEnd = monthEnd.minusMonths(1);
		lastMonthStart = monthStart.minusMonths(1);
		lastYearMonthEnd = monthEnd.minusYears(1);
		lastYearMonthStart = monthStart.minusYears(1);
		lastYearEnd = yearEnd.minusYears(1);
		lastYearStart = yearStart.minusYears(1);
	}

	/**
	 * 设置统计的月初日期
	 * @param dateStr
	 * @return
	 *//*
	@Deprecated
	public static String setStartDay(String dateStr){
		Date date = null;
		try {
			date = DateHelper.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH,-1);
			cal.set(Calendar.DAY_OF_MONTH, 21);
			date = cal.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("============================统计当月月初日期"+DateHelper.format(date));
		return DateHelper.format(date);
	}
	*//**
	 * 设置统计的月末日期
	 * @param dateStr
	 * @return
	 *//*
	@Deprecated
	public static String setEndDay(String dateStr){
		Date date = null;
		try {
			date = DateHelper.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_MONTH, 20);
			date = cal.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("=========================统计当月月末日期"+DateHelper.format(date));
		return DateHelper.format(date);
	}*/
	
	/**
	 * 例子：4月03日
	 * 设置本月开始统计的日期, 若天数在21日前, 则本月开始统计日期为3月21日，否则为4月21日
	 * @param dateStr
	 * @return
	 */@Deprecated
	public static String setTomonthStartDay(String dateStr){
		String temp = null;
		//设置天
		temp = setDateField(dateStr,Calendar.DAY_OF_MONTH,21);
		int day = getDateField(dateStr,Calendar.DAY_OF_MONTH);
		if(day<=20)
			temp = addDateField(temp,Calendar.MONTH,-1);//设置月
		return temp;
	}
	
	/**
	 * 例子：4月03日
	 * 设置本月结束统计的日期, 若天数在21日前, 则本月截止统计日期为4月21日，否则为5月21日
	 * @param dateStr
	 * @return
	 */@Deprecated
	public static String setTomonthEndDay(String dateStr){
		//设置天
		String temp = setDateField(dateStr,Calendar.DAY_OF_MONTH,20);
		int day = getDateField(dateStr,Calendar.DAY_OF_MONTH);
		if(day>=21)
			temp = addDateField(temp,Calendar.MONTH,1);//设置月
		return temp;
	}
	/**
	 * 设置上月开始统计的日期
	 * @param dateStr
	 * @return
	 */@Deprecated
	public static String setLastmonthStartDay(String dateStr){
		String temp = setTomonthStartDay(dateStr);
		temp = addDateField(temp,Calendar.MONTH,-1);
		return temp;
	}
	/**
	 * 设置上月结束统计的日期
	 * @param dateStr
	 * @return
	 */@Deprecated
	public static String setLastmonthEndDay(String dateStr){
		String temp = setTomonthEndDay(dateStr);
		temp = addDateField(temp,Calendar.MONTH,-1);
		return temp;
	}
	
	/**
	 * 设置本年开始统计的日期 若传入2014-12-24，查的是2014-12-21到2015-12-20
	 * @param dateStr
	 * @return
	 */@Deprecated
	public static String setToyearStartDay(String dateStr){
		
		//设置月
		String temp = setDateField(dateStr,Calendar.MONTH,11);
		//设置天
		temp = setDateField(temp,Calendar.DAY_OF_MONTH,21);
		
		int month = getDateField(dateStr,Calendar.MONTH);
		int day = getDateField(dateStr,Calendar.DAY_OF_MONTH);
		if(month<11 || day<=20){ //若为12月21日后，开始年份+1
			//设置年
			temp = addDateField(temp,Calendar.YEAR,-1);
		}
		
		return temp;
	}
	
	
	/**
	 * 设置本年结束统计的日期
	 * @param dateStr
	 * @return
	 */@Deprecated
	public static String setToyearEndDay(String dateStr){
		String temp = setToyearStartDay(dateStr);
		temp = addDateField(temp,Calendar.YEAR,1);
		//设置天
		temp = setDateField(temp,Calendar.DAY_OF_MONTH,20);
		return temp;
	}
	
	/**
	 * 设置去年开始统计的日期
	 * @param dateStr
	 * @return
	 */@Deprecated
	public static String setLastyearStartDay(String dateStr){
		String temp = setToyearStartDay(dateStr);
		temp = addDateField(temp,Calendar.YEAR,-1);
		return temp;
	}

	/**
	 * 设置上年结束统计的日期
	 * 
	 * 日期统计需求变化：上年结束日期为上年本月结束日期
	 * @param dateStr
	 * @return
	 */@Deprecated
	public static String setLastyearEndDay(String dateStr){
		/*String temp = setToyearEndDay(dateStr);
		temp = addDateField(temp,Calendar.YEAR,-1);
		return temp;*/
		return setLastyearMonthEndDay(dateStr);
	}
	
	/**
	 * 设置上年本月开始统计的日期
	 * @author hjh
	 * @param 
	 * @return
	 * 2014-6-3下午04:52:23
	 */@Deprecated
	public static String setLastyearMonthStartDay(String dateStr) {
		String temp = setTomonthStartDay(dateStr);
		temp = addDateField(temp,Calendar.YEAR,-1);
		return temp;
	}
	
	/**
	 * 设置上年本月结束统计的日期
	 * @author hjh
	 * @param 
	 * @return
	 * 2014-6-3下午04:52:42
	 */@Deprecated
	public static String setLastyearMonthEndDay(String dateStr) {
		String temp = setTomonthEndDay(dateStr);
		temp = addDateField(temp,Calendar.YEAR,-1);
		return temp;
	}
	
	/**
	 * 设置同比本月结束统计的日期
	 * @param dateStr
	 * @return
	 */@Deprecated
	public static String setLastyearMonthEndDay2(String dateStr){
		DateTime dt = new DateTime(dateStr);
		return dt.plusYears(-1).toString(Constants.Date_Short);
	}
	
	/**
	 * 获取日期字段(年、月、日等)，参数和返回值均为字符串。
	 * @param dateStr
	 * @return
	 */
	private static int getDateField(String dateStr,int field){
		Date date = null;
		Calendar cal = null;
		try {
			date = DateHelper.parse(dateStr);
			cal = Calendar.getInstance();
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal.get(field);
	}
	/**
	 * 设置日期字段(年、月、日等)，参数和返回值均为字符串。
	 * @param dateStr
	 * @return
	 */
	private static String setDateField(String dateStr,int field, int value){
		Date date = null;
		try {
			date = DateHelper.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(field, value);
			date = cal.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return DateHelper.format(date);
	}
	/**
	 * 运算增删日期字段值，(年、月、日等)，参数和返回值均为字符串。
	 * @param dateStr
	 * @return
	 */
	private static String addDateField(String dateStr,int field, int value){
		Date date = null;
		try {
			date = DateHelper.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(field, value);
			date = cal.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return DateHelper.format(date);
	}
	
	
	
	public static void main(String[] args){
		String testDate = "2016-12-29";
		System.out.println("传入日期："+testDate);
//		System.out.println("日："+DateSetters.getDateField(testDate,Calendar.DAY_OF_MONTH));
//		System.out.println(DateSetters.setStartDay("2014-03-02"));
//		System.out.print(DateSetters.setEndDay("2014-03-02"));
		DateSetters d = new DateSetters(testDate);
		System.out.println("上月开始统计日期："+DateSetters.setLastmonthStartDay(testDate)+"::"+d.lastMonthStart.toString("yyyy-MM-dd"));
		System.out.println("上月结束统计日期："+DateSetters.setLastmonthEndDay(testDate)+"::"+d.lastMonthEnd.toString("yyyy-MM-dd"));
		System.out.println("本月开始统计日期："+DateSetters.setTomonthStartDay(testDate)+"::"+d.monthStart.toString("yyyy-MM-dd"));
		System.out.println("本月结束统计日期："+DateSetters.setTomonthEndDay(testDate)+"::"+d.monthEnd.toString("yyyy-MM-dd"));
		
		System.out.println("上年开始统计日期："+DateSetters.setLastyearStartDay(testDate)+"::"+d.lastYearStart.toString("yyyy-MM-dd"));
		System.out.println("上年结束统计日期："+DateSetters.setLastyearEndDay(testDate)+"::"+d.lastYearEnd.toString("yyyy-MM-dd"));
		System.out.println("本年开始统计日期："+DateSetters.setToyearStartDay(testDate)+"::"+d.yearStart.toString("yyyy-MM-dd"));
		System.out.println("本年结束统计日期："+DateSetters.setToyearEndDay(testDate)+"::"+d.yearEnd.toString("yyyy-MM-dd"));
		System.out.println("上年本月开始统计日期："+DateSetters.setLastyearMonthStartDay(testDate)+"::"+d.lastYearMonthStart.toString("yyyy-MM-dd"));
		System.out.println("上年本月结束统计日期："+DateSetters.setLastyearMonthEndDay(testDate)+"::"+d.lastYearMonthEnd.toString("yyyy-MM-dd"));
		System.out.println("同比上年本月结束统计的日期："+DateSetters.setLastyearMonthEndDay2(testDate));
//		System.out.println("上月开始统计日期："+DateSetters.setLastmonthStartDay(testDate));
//		System.out.println("上月结束统计日期："+DateSetters.setLastmonthEndDay(testDate));
//		System.out.println("本月开始统计日期："+DateSetters.setTomonthStartDay(testDate));
//		System.out.println("本月结束统计日期："+DateSetters.setTomonthEndDay(testDate));
//		
//		System.out.println("上年开始统计日期："+DateSetters.setLastyearStartDay(testDate));
//		System.out.println("上年结束统计日期："+DateSetters.setLastyearEndDay(testDate));
//		System.out.println("本年开始统计日期："+DateSetters.setToyearStartDay(testDate));
//		System.out.println("本年结束统计日期："+DateSetters.setToyearEndDay(testDate));
//		System.out.println("上年本月开始统计日期："+DateSetters.setLastyearMonthStartDay(testDate));
//		System.out.println("上年本月结束统计日期："+DateSetters.setLastyearMonthEndDay(testDate));
//		System.out.println("同比上年本月结束统计的日期："+DateSetters.setLastyearMonthEndDay2(testDate));
		
	}
	
	
}
