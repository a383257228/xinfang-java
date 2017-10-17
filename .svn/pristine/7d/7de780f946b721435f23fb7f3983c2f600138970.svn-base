package com.sinosoft.xf.query.common;

import java.sql.Timestamp;

/**
 * 根据指定日期，返回业务时间（Timestamp）
 * @author hjh
 *
 * 2015-1-31下午02:58:21
 */
public class BusinessTime extends DateSetters {
	

	public BusinessTime(String dateStr) {
		super(dateStr);
	}
	public BusinessTime() {
		super();
	}

	public Timestamp getMonthStartTime(){
		return new Timestamp(monthStart.toDate().getTime()) ;
	}
	public Timestamp getMonthEndTime(){
		return new Timestamp(monthEnd.toDate().getTime()) ;
	}
	public Timestamp getLastMonthStartTime(){
		return new Timestamp(lastMonthStart.toDate().getTime()) ;
	}
	public Timestamp getLastMonthEndTime(){
		return new Timestamp(lastMonthEnd.toDate().getTime()) ;
	}
	public Timestamp getYearEndTime(){
		return new Timestamp(yearEnd.toDate().getTime()) ;
	}
	public Timestamp getYearStartTime(){
		return new Timestamp(yearStart.toDate().getTime()) ;
	}
	public Timestamp getLastYearEndTime(){
		return new Timestamp(lastYearEnd.toDate().getTime()) ;
	}
	public Timestamp getLastYearStartTime(){
		return new Timestamp(lastYearStart.toDate().getTime()) ;
	}
	public Timestamp getLastYearMonthEndTime(){
		return new Timestamp(lastYearMonthEnd.toDate().getTime()) ;
	}
	public Timestamp getLastYearMonthStartTime(){
		return new Timestamp(lastYearMonthStart.toDate().getTime()) ;
	}

}
