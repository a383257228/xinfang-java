package com.sinosoft.xf.util.sql;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;

import com.sinosoft.util.TimeUtils;
import com.sinosoft.xf.util.StringUtils;

public class DateUtil {
	//通过传的按钮名称拼接查询的日期字符串
	public static String queryStartdateAndEnddate(Map<String, Object> map) throws ParseException{
		StringBuffer sb = new StringBuffer();
		String[] dateValue = new String[2];
		String dateFlag = map.get("dateFlag").toString();
		String startDateValue =map.get("startDateValue").toString();
		String endDateValue = map.get("endDateValue").toString();
		
		if(dateFlag.equals("BY")){//选中本月   拼接本月的一号到今天
			DateTime month = new DateTime();
			dateValue[0] = month.getYear()+"-"+month.getMonthOfYear()+"-"+"01";
			dateValue[1] = month.getYear()+"-"+month.getMonthOfYear()+"-"+month.getDayOfMonth();
		}
		if(dateFlag.equals("BJD")){//选中本季度     拼接度的第一个月的1号到今天
			StringBuffer start = new StringBuffer();
			StringBuffer end = new StringBuffer();
			DateTime date = new DateTime();
			end = end.append(date.getYear()).append("-").append(date.getMonthOfYear()).append("-").append(date.getDayOfMonth());
			if(date.getMonthOfYear()>=1&&3>=date.getMonthOfYear()){
				start = start.append(date.getYear()).append("-1-01");
			}else if(date.getMonthOfYear()>=4&&6>=date.getMonthOfYear()){
				start = start.append(date.getYear()).append("-4-01"); 
			}else if(date.getMonthOfYear()>=7&&9>=date.getMonthOfYear()){
				start = start.append(date.getYear()).append("-7-01"); 
			}else if(date.getMonthOfYear()>=10&&12>=date.getMonthOfYear()){
				start = start.append(date.getYear()).append("-10-01"); 
			}
			dateValue[0] = start.toString();
			dateValue[1] = end.toString();
		}
		if(dateFlag.equals("BN")){//选中本年   拼接今年的一月一号到今天
			DateTime year = new DateTime();
			dateValue[0] = year.getYear()+"-01-01";
			dateValue[1] = year.getYear()+"-"+year.getMonthOfYear()+"-"+year.getDayOfMonth();
		}
		if(dateFlag.equals("ZDYC")||dateFlag.equals("ZDY")){//选中自定义    拼接开始日期月的第一天到结束日期月的最后一天
			dateValue[0] = startDateValue+"-01";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			if(StringUtils.isNotBlank(endDateValue)){
				int day = getDaysOfMonth(sdf.parse(endDateValue+"-01"));
				dateValue[1] = endDateValue+"-"+day;
			}else{
				Timestamp dateNow= TimeUtils.getAllTimestamp();
				dateValue[1] = sdf.format(dateNow);
			}
		}
		sb.append(" and  basic.petition_date>='").append(dateValue[0]).append("'");
		sb.append(" and  basic.petition_date<='").append(dateValue[1]).append("' ");
		return sb.toString();
	}
	
	//各月趋势的本季度返回日期
	public static String[] queryDate(String dateFlag,String startDateValue,String endDateValue) throws ParseException{
		String[] dateValue = new String[3];
		String startDate = "";
		String endDate = "";
		String mon = "";//存放结束日期的月份
		
		StringBuffer start = new StringBuffer();
		StringBuffer end = new StringBuffer();
		if(dateFlag.equals("BJD")){//选中本季度 
			DateTime date = new DateTime();
			if(date.getMonthOfYear()>=1&&3>=date.getMonthOfYear()){
				start = start.append(date.getYear()).append("-1-01");
				end = end.append(date.getYear()).append("-3-31");
				mon = "3";
			}else if(date.getMonthOfYear()>=4&&6>=date.getMonthOfYear()){
				start = start.append(date.getYear()).append("-4-01"); 
				end = end.append(date.getYear()).append("-6-30");
				mon = "6";
			}else if(date.getMonthOfYear()>=7&&9>=date.getMonthOfYear()){
				start = start.append(date.getYear()).append("-7-01"); 
				end = end.append(date.getYear()).append("-9-30");
				mon = "9";
			}else if(date.getMonthOfYear()>=10&&12>=date.getMonthOfYear()){
				start = start.append(date.getYear()).append("-10-01"); 
				end = end.append(date.getYear()).append("-12-31");
				mon = "12";
			}
			startDate = start.toString();
			endDate = end.toString();
		}else if(dateFlag.equals("BN")){
			DateTime year = new DateTime();
			startDate = year.getYear()+"-1-01";
			endDate = year.getYear()+"-"+year.getMonthOfYear()+"-"+year.getDayOfMonth();
			mon = year.getMonthOfYear()+"";
		}else if(dateFlag.equals("ZDYC")||dateFlag.equals("ZDY")){
			startDate = startDateValue+"-01";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			if(StringUtils.isNotBlank(endDateValue)){
				int day = getDaysOfMonth(sdf.parse(endDateValue+"-01"));
				endDate = endDateValue+"-"+day;
				mon = endDateValue.substring(5);
			}else{
				Timestamp dateNow= TimeUtils.getAllTimestamp();
				endDate = sdf.format(dateNow);
				mon = endDate.substring(5, 7);
			}
		}
		
		dateValue[0] = startDate;
		dateValue[1] = endDate;
		dateValue[2] = mon;
		return dateValue;
	}
	//各月趋势、同比、环比钻取列表查询拼接时间参数
	public static String queryStartEnd(Map<String, Object> map){
		StringBuffer sb = new StringBuffer();
		String[] strDate = new String[2];
	    
	    String m = map.get("month").toString();//前台传的参数带月字截取一下
		String month = m.substring(0, m.indexOf("月"));
		
		DateTime date = new DateTime();
		int day = 1;//判断月份有多少天
		if("1".equals(month)||"3".equals(month)||"5".equals(month)||"7".equals(month)||"8".equals(month)
				||"10".equals(month)||"12".equals(month)){
			day=31;
		}else if("2".equals(month)){
			day=28;
		}else{
			day=30;
		}
		
		if(null!=map.get("flag")&&""!=map.get("flag")){//如果不是空说明是同比 传过来的参数是  去年/件数  今年/件数
			String str = map.get("flag").toString();
			String year = str.substring(0, str.indexOf("/"));
			if("去年".equals(year)){
				strDate[1] = (date.getYear()-1)+"-"+month+"-"+day;
				strDate[0] = (date.getYear()-1)+"-"+month+"-01";
			}else if("今年".equals(year)){
				strDate[1] = date.getYear()+"-"+month+"-"+day;
				strDate[0] = date.getYear()+"-"+month+"-01";
			}
		}else if(m.indexOf("年")>0){//说明是各月趋势自定义查询查的包括去年的数据
			strDate[1] = m.substring(0, m.indexOf("年"))+"-"+m.substring(m.indexOf("年")+1, m.indexOf("月"))+"-"+day;
			strDate[0] = m.substring(0, m.indexOf("年"))+"-"+m.substring(m.indexOf("年")+1, m.indexOf("月"))+"-01";
		}else{//今年的数据
			strDate[1] = date.getYear()+"-"+month+"-"+day;
			strDate[0] = date.getYear()+"-"+month+"-01";
		}
		sb.append(" and  basic.petition_date>='").append(strDate[0]).append("'");
		sb.append(" and  basic.petition_date<='").append(strDate[1]).append("' ");
		return sb.toString();
	}
	/**
	 * 查询指定月的天数
	 * @author guoh
	 * @date  2017-06-29
	 * @param date
	 * @return
	 */
	public static int getDaysOfMonth(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    } 
}