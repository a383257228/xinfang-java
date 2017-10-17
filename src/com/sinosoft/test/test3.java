package com.sinosoft.test;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class test3 {
	public static void main(String[] args) {
		/*DateTime date = new DateTime();
		String start = (date.getYear()-1)+"-01-01";
		String end = (date.getYear()-1)+"-"+date.getMonthOfYear()+"-01";
		System.out.println(start);
		System.out.println(end);*/
		
		/*int a=39;
		int b=31;
		NumberFormat numberFormat = NumberFormat.getInstance();     
		numberFormat.setMaximumFractionDigits(2);     
		String result = numberFormat.format(((float)a-(float)b)/(float)b*100);  
		System.out.println("a和b的百分比为:" + result + "%");           */     
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("Q0", 7);
		map.put("Q1", 9);
		map.put("Q2", 17);
		map.put("Q3", 27);
		map.put("Q4", 5);
		map.put("Q5", 15);
		map.put("Q6", 12);
		
		NumberFormat numberFormat = NumberFormat.getInstance();     
		numberFormat.setMaximumFractionDigits(2); 
		String result = numberFormat.format(((float)(map.get("Q1")-(float)map.get("Q0"))/(float)map.get("Q0")*100));  
		System.out.println(result);		
	}
}
