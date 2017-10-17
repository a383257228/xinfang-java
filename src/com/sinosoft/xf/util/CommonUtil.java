package com.sinosoft.xf.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import org.joda.time.DateTime;

import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.NumberFormat;
import com.sinosoft.xf.constants.BusinessConstants;
import com.sinosoft.xf.constants.Constants;

/**
 * 普通工具类：
 * @author  HJM
 * @createDate 2011-9-7
 */
public class CommonUtil {
	/**
	 * 获取指定长度的随机数
	 * 
	 * @param len
	 *            指定的长度
	 * @return
	 */
	public static String genRandomNumString(int len) {
		int no = 0;
		char[] t = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		String str = "";
		Random r = new Random();
		synchronized (r) {
			no++;
		}
		for (int i = 0; i < len; i++) {
			str += t[(r.nextInt(32) + no) % t.length];
		}
		return str;
	}
	/**
	 * 传入的日期加一天
	 * @param dateStr 
	 * @return
	 */
	public static String plusOneDays(String dateStr) {
		return new DateTime(dateStr).plusDays(1).toString(Constants.Date_Short);
	}
	/**
	 * 日期比较-->当前日期和指定日期比较 当前日期<指定日期 返回：true 否则的话返回false
	 * 
	 * @param d
	 *            指定日期
	 * @return
	 */
	public static boolean comparisonDateTime(Date d) {
		boolean judge = false;
		if (new DateTime(d).isAfterNow()) {
			judge = true;
		}
		return judge;
	}
	/**
	 * 当前日期与指定日期的差（天数）
	 * 
	 * @param d指定日期的字符串
	 * @return 当前日期与指定日期的 相差天数
	 */
	public static long getDateDiff(String d) {
		return (new DateTime().getMillis() - new DateTime(d).getMillis()) / (1000 * 60 * 60 * 24);
	}
	/**
	 * 计算百分比
	 * @param total
	 * @param nowCount
	 * @return
	 */
	public static String calculatePersent(int total, int nowCount) {
		Integer persent = nowCount;
		if (total != 0) {
			persent =  nowCount * 1000 / total;
			if (persent > 0) {
				persent = (persent + 5) / 10;
			} else {
				persent = (persent - 5) / 10;
			}
		}else{
			return "0";
		}
		return persent.toString();
	}
	/**
	 * 根据工作日，
	 * @param num 工作日天数
	 * @param holidays 法定节假日字符串
	 * @param noholidays  调休字符串
	 * @return 截止日期
	 */
	public static String getNextWorkDay(int num, String holidays,String noholidays) {
		DateTime nowDateTime = new DateTime();
		int i=0;
		if (holidays != null && noholidays != null) {
			while(true){
				nowDateTime = nowDateTime.plusDays(1);
				String  strDate = nowDateTime.toString(Constants.Date_Short);
				if(noholidays.contains(strDate)){
					i = i+1;
					if(i>=num)
						break;
					continue;
				}
				if(holidays.contains(strDate)){
					continue;
				}
				int day_of_week = nowDateTime.getDayOfWeek();
				if (day_of_week == 6||day_of_week == 7) {
					continue;
				}else{
					i= i+1;
				}
				if(i>=num)
					break;
			}
		}else{
			nowDateTime = nowDateTime.plusDays(5);
		}
		return nowDateTime.toString(Constants.Date_Short);
	}
	
	/**
	 * 通过信访日期计算业务日期日期
	 * @param date
	 * @return
	 */
	public static String countActualDate(Date date) {
		if (date == null) {
			return null;
		}
		DateTime dateTime = new DateTime(date);
		int day = dateTime.getDayOfMonth();
		int month = dateTime.getMonthOfYear();
		int year = dateTime.getYear();
		if (day > BusinessConstants.monthEndDay) {
			if (month < 12) {
				month = month + 1;
			} else {
				month = 1;
				year = year + 1;
			}
		}
		NumberFormat nf = new DecimalFormat("00");
		String fmonth = nf.format(month);
		String actualDate = year + "-" + fmonth;
		return actualDate;
	}
	/**
	 * 重载countActualDate便于使用
	 * @param date
	 * @return
	 * add by hjh
	 */
	public static String countActualDate(String dateStr) {
		Date date = new DateTime(dateStr).toDate();
		return countActualDate(date);
	}
	/**
	 * 将字符串d的半角转换为全角
	 * @author jk
	 * @date 2014-7-17
	 * @return 字符串
	 */
	public static String ToSBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}
	public static void main(String[] args) {
		DateTime dateTime = new DateTime("2013-12");
		System.out.println(dateTime.toString(Constants.Date_Short));
//		String dateStr = "2013-10-01,2013-06-11,2013-06-12,2013-10-01";
//		String dateStr2 = "2013-06-08,2013-06-09";
//		String nextWorkDay = getNextWorkDay(5, dateStr,dateStr2);
//		System.out.println(nextWorkDay);
		System.out.println(countActualDate(TimeUtils.string2Time("2013-12-24")));
		
	}
}
