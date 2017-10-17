package com.sinosoft.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;

import com.sinosoft.util.TimeUtils;

public class test {
	public static void main(String[] args) throws ParseException {
		/*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
		Timestamp aa= TimeUtils.getAllTimestamp();
		String str1 = df.format(aa);
		System.out.println(str1);*/
		//Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
		//String str = df.format(now);
//		System.out.println(str);
		
		
		//System.out.println(str1.substring(0, 4));
		
		/*StringBuffer startDate = new StringBuffer();
		StringBuffer endDate = new StringBuffer();
		DateTime date = new DateTime();
		startDate = startDate.append(date.getYear()).append("-4-01 00:00:00"); 
		endDate = endDate.append(date.getYear()).append("-").append(date.getMonthOfYear()).append("-").append(date.getDayOfMonth()).append(" 23:59:59"); 
		
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(TimeUtils.string2Date(startDate.toString()));
		System.out.println(TimeUtils.string2Date(endDate.toString()));*/
		
		/*String name ="2017-6"; 1 2 3   4 5 6   7 8 9   10 11 12
		System.out.println(name.substring(0, 4));
		System.out.println(name.substring(5, 6));*/
	/*	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        System.out.println(getDaysOfMonth(sdf.parse("2017-06-2")));  */
		
		
		/*StringBuffer start = new StringBuffer();
		StringBuffer end = new StringBuffer();
		
		DateTime d = new DateTime();
		System.out.println(d.getYear()+"-01-01");
		System.out.println(d.getYear()+"-"+d.getMonthOfYear()+"-"+d.getDayOfMonth()+"");
		
		System.out.println((d.getYear()-1)+"-01-01");
		System.out.println((d.getYear()-1)+"-"+d.getMonthOfYear()+"-"+d.getDayOfMonth()+"");*/
		
		
		/*if(d.getMonthOfYear()>=1&&d.getMonthOfYear()<=3){
			System.out.println(d.getMonthOfYear()+"==1-3");
		}else if(d.getMonthOfYear()>=4&&d.getMonthOfYear()<=6){
			System.out.println(d.getMonthOfYear()+"==4-6");
		}else if(d.getMonthOfYear()>=7&&d.getMonthOfYear()<=9){
			System.out.println(d.getMonthOfYear()+"==7-9");
		}else if(d.getMonthOfYear()>=10&&d.getMonthOfYear()<=12){
			System.out.println(d.getMonthOfYear()+"==10-12");
		}*/
		
		/*DateTime month = new DateTime();
		String s = month.getYear()+"-"+(month.getMonthOfYear()-1)+"-"+(month.getDayOfMonth()+1)+" 00:00:00";
		String e = month.getYear()+"-"+month.getMonthOfYear()+"-"+(month.getDayOfMonth())+" 23:59:59";
		
		String w = month.getYear()+"-"+(month.getMonthOfYear()-2)+"-"+month.getDayOfMonth()+" 00:00:00";
		String q = month.getYear()+"-"+(month.getMonthOfYear()-1)+"-"+month.getDayOfMonth()+" 23:59:59";
		System.out.println(s);
		System.out.println(e);
		System.out.println("===============");
		System.out.println(w);
		System.out.println(q);*/
		
		Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("typeName", "aa");
	    map.put("count", "12");
	    Map<String, Object> map1 = new HashMap<String, Object>();  
	    map1.put("typeName", "bb");
	    map1.put("count", "23");
	    Map<String, Object> map2 = new HashMap<String, Object>();  
	    map2.put("typeName", "cc"); 
	    map2.put("count", "33");
	    
	    Map<String, Object> map3 = new HashMap<String, Object>();  
	    map3.put("typeName", "aa");  
	    map3.put("count", "23");
	    Map<String, Object> map4 = new HashMap<String, Object>();  
	    map4.put("typeName", "bb");  
	    map4.put("count", "11");
	    Map<String, Object> map5 = new HashMap<String, Object>();  
	    map5.put("typeName", "dd");
	    map5.put("count", "5");
	    
	    
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
	    list.add(map); 
	    list.add(map1);
	    list.add(map2);
	    List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();  
	    list1.add(map3); 
	    list1.add(map4);
	    list1.add(map5);
	    
	    Map<String,Integer> cMap = new HashMap<String, Integer>();
	    
		for (Map<String, Object> m1 : list) {
			for (String k1 : m1.keySet()) {
				if("typeName".equals(k1)){
					for (Map<String, Object> m2 : list1) {
						System.out.println(m2.containsValue(m1.get(k1)));
						for (String k2 : m2.keySet()) {
						}
					}
				}
			}
		}
		ArrayList<Entry<String, Integer>> arrayList = new ArrayList<Entry<String, Integer>>(cMap.entrySet());  
		//排序  
		Collections.sort(arrayList, new Comparator<Map.Entry<String, Integer>>() {  
		    public int compare(Map.Entry<String, Integer> map1, Map.Entry<String, Integer> map2) {  
		        return (map2.getValue() - map1.getValue());  
		    }  
		});  
		//输出  
		for (Entry<String, Integer> entry : arrayList) {  
		    System.out.println(entry.getKey() + "\t" + entry.getValue());  
		}  
	}
	/*public static int getDaysOfMonth(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    }  */
}