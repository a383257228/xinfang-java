package com.sinosoft.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

import com.sinosoft.xf.constants.Constants;

/**
 * @see 提供时间处理的工具类
 * @date 2011-04-23
 * @author wangxx
 */
public class TimeUtils {
	/**
	 * 将String转换为Timestamp类型格式为日期格式
	 * @date 2011-11-22
	 * @author Oba
	 * @param dateString 需要转换的字符串
	 * @return dateTime 返回的时间
	 */
	public final static Timestamp string2Date(String dateString){
		try {
			if(dateString==null||"".equals(dateString)){
				return null;
			}
			if(dateString.length()>10){
				dateString = dateString.substring(0, 10);
			}
			final DateFormat dateFormat = new SimpleDateFormat(Constants.Date_Short, Locale.CHINESE);// 设定格式
		    dateFormat.setLenient(false);// 严格控制输入 比如2010－02－31，根本没有这一天 ，也会认为时间格式不对。
		    Date timeDate;
			timeDate = dateFormat.parse(dateString);
			final Timestamp dateTime = new Timestamp(timeDate.getTime());// Timestamp类型,timeDate.getTime()返回一个long型  
			return dateTime;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取日期标示为130101 用于标示2013-01-01
	 * @date 2013-03-21
	 * @author Dustin
	 * @return 为130101格式的日期
	 */
	public final static String getYyMyDd(){
		Date date = new Date();
		SimpleDateFormat dateToStr = new SimpleDateFormat("yyMMdd");
        String tempString = dateToStr.format(date);
		return tempString;
	}
	
	/**
	 * 时间转字符串
	 * @author hjh
	 * 2015年9月24日
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String time2Str(Date date, String pattern){
		SimpleDateFormat dateToStr = new SimpleDateFormat(pattern);
        String tempString = dateToStr.format(date);
		return tempString;
	}
	/**
	 * Timestamp转换为str
	 * @param dateString
	 * @return
	 */
	public final static String Timestamp2Str(Timestamp dateString) {
		if (dateString == null) {
			return "";
		}
		String temp = dateString.toString();
		if (temp.length() >= 10) {
			return temp.substring(0, 10);
		} else {
			return temp;
		}
	}
	
	/**
	 * timestamp转换string类型，并制定格式
	 * @param dateString
	 * @param datePattern
	 * @return
	 */
	public static String timestamp2Str(Timestamp dateString,String datePattern){
		String tsStr = "";   
        DateFormat sdf = new SimpleDateFormat(datePattern);   
        try {   
            //方法一   
            tsStr = sdf.format(dateString);   
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
        return tsStr;
	}
	
	/**
	 * 将字符串类型的日期修改为指定格式的日期，如果参数format为空字符串，就显示默认的2012-03-09格式
	 * @param stringDate 字符串类型的日期
	 * @param format 生成格式
	 * @return 指定格式的string类型的日期
	 */
	public final static String string2StingDate(String stringDate,String format){
		String dateFormat = "yyyy年MM月dd日";
		if(!"".equals(format)){
			dateFormat = format;
		}
		Timestamp date = string2Date(stringDate);
		SimpleDateFormat dateToStr = new SimpleDateFormat(dateFormat);
        String tempString = dateToStr.format(date);
		return tempString;
	}
	/**
	 * 获取一年前年末时间时间
	 * @date 2013-12-13
	 * @author zpj
	 * @return 当前日期
	 * @throws ParseException 
	 */
	public final static Date getBeforOneYear() throws ParseException{
		DateTime dt = new DateTime();
		DateTime before1year = dt.minusYears(1);
		DateTime yearLastDay =  before1year.dayOfYear().withMaximumValue();
		Date d1 = new Date(yearLastDay.getMillis());
		return d1;
	}
	/**
	 * 将String转换为Timestamp类型
	 * @date 2011-05-05
	 * @author wangxx
	 * @param dateString
	 * @return dateTime
	 */
	public final static Timestamp string2Time(final String dateString) {
		try {
			if (dateString == null || "".equals(dateString)) {
				return null;
			}
			DateFormat dateFormat = new SimpleDateFormat(Constants.Date_Short, Locale.ENGLISH);
			if (dateString != null && !dateString.equals("") && dateString.length() > 10)
				dateFormat = new SimpleDateFormat(Constants.DateLong, Locale.ENGLISH);//设定格式
			dateFormat.setLenient(false);// 严格控制输入 比如2010－02－31，根本没有这一天 ，也会认为时间格式不对。
			Date timeDate = dateFormat.parse(dateString);
			final Timestamp dateTime = new Timestamp(timeDate.getTime());// Timestamp类型,timeDate.getTime()返回一个long型  
			return dateTime;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将String转换为Timestamp类型
	 * @author hjh
	 * @param dateString日期字符串
	 * @param datePattern日期格式
	 * @return
	 * 2014-6-22上午12:11:29
	 * @throws ParseException 
	 */
	public final static Timestamp string2Time(final String dateString,String datePattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		return new Timestamp(sdf.parse(dateString).getTime());
	}
//	/**
//	 * 将日期转化为yyyy-MM-dd-hh:mm:ss 的字符串
//	 * @date 2011-05-05
//	 * @author wangxx
//	 * @param date
//	 * @return tempString
//	 * @throws java.text.ParseException
//	 */
//	public final static String time2String(final Date date){
//		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
//		return sdf.format(date);
//	}
	/**
	 * 获取当前时间，精确到天
	 * @date 2011-07-04
	 * @author wangxx
	 * @return 当前时间
	 */
	public final static Timestamp getTimestamp(){
		Date date = new Date();
		SimpleDateFormat dateToStr = new SimpleDateFormat(Constants.Date_Short);
        String tempString = dateToStr.format(date);
        Timestamp timestamp = string2Date(tempString);
        return timestamp;
	}
//	/**
//	 * 获取当前时间，精确到天
//	 * @date 2013-04-13
//	 * @author zpj
//	 * @return String
//	 */
//	public final static String getTimesFormateZ(Date date){
//		if(date==null){
//			return null;
//		}
//		SimpleDateFormat dateToStr = new SimpleDateFormat(Constants.Date_Short);
//		String tempString = dateToStr.format(date);
//		return tempString;
//	}
	/**
	 * 将日期转换成指定格式的字符串
	 * 
	 * @param d
	 *            要转换的日期
	 * @param fmt
	 *            指定格式的字符串 如：yyyyMMddhhmmss
	 */
	public static String date2Str(Date d, String fmt) {
		if(fmt==null||"".equals(fmt)){
			fmt = Constants.Date_Short;
		}
		return new DateTime(d).toString(fmt);
	}
	/**
	 * 获取 指定格式的日期字符串
	 * 
	 * @param fmt
	 *            如：yyyyMMddhhmmss
	 * @return
	 */
	public static String getDate(String fmt) {
		return new DateTime().toString(fmt);
	}
	/**
	 * 获取当前时间,精确到秒
	 * @date 2011-08-01
	 * @author wangxx
	 * @return 当前时间
	 */
	public final static Timestamp getAllTimestamp(){
       return new Timestamp(new DateTime().getMillis());
	}
	/**
	 * 获取本年度标识，此处为自然月
	 * @date 2011-05-13
	 * @author wangxx
	 * @return 4位年度标识
	 */
	public final static String getYear(){
		return String.valueOf(new DateTime().getYear());
	}
//	/**
//	 * 获取本年度标识，此处为自然月
//	 * @date 2011-05-13
//	 * @author wangxx
//	 * @return 4位年度标识
//	 */
//	public final static String getDate(){
//		return new DateTime().toString(Constants.Date_CN_Short);
//	}
//	/**
//	 * 获取本年度标识，此处为自然月
//	 * @date 2011-05-13
//	 * @author wangxx
//	 * @return 4位年度标识
//	 */
//	public final static String getTime(){
//		Date date = new Date();
//		SimpleDateFormat dateToStr = new SimpleDateFormat(Constants.DateLong);
//        String tempString = dateToStr.format(date);
//		return tempString;
//	}
	
	/**
	 * 获取日期标示为130101 用于标示2013-01-01
	 * @date 2013-03-21
	 * @author Dustin
	 * @return 为130101格式的日期
	 */
	public final static String getYyMmDd(){
		return new DateTime().toString("yyMMdd");
	}
	/**
	 * @see 测试主函数
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(final String[] args) throws ParseException {
		System.out.println(Timestamp2Str(getAllTimestamp()));
	}
	/**
	 * 获取当前时间，精确到天
	 * @date 2011-07-04
	 * @author wangxx
	 * @return 当前日期
	 * @throws ParseException 
	 */
	public final static Timestamp getCountDate(){
		try {
			Date date = new Date();
			SimpleDateFormat dateToStr = new SimpleDateFormat("yyyy-MM-dd");
	        String tempString = dateToStr.format(date);
	        Timestamp timestamp = string2Date(tempString);
	        return timestamp;
		} catch (Exception e) {
			System.out.println("Dustin:基本信息countDate字段有问题。");
			e.printStackTrace();
			return null;
		}
	}
}