package com.sinosoft.xf.system.set.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.sinosoftframework.core.domain.IdEntity;

/**
 * 业务口径时间设置
 *
 */
@Entity	
@Table(name = "Business_Caliber")
public class BusinessCaliber extends IdEntity {
	private static final long serialVersionUID = 1L;
	
	private String monthFlag;//1当月表示为自然月
	private String monthStartDay;
	private String monthEndDay;
	private String yearFlag;//1今年表示为自然年
	private String yearStartMonth;
	private String yearEndMonth;
	private String holidayTime;
	private String paidleaveTime;
	public String getMonthFlag() {
		return monthFlag;
	}
	public void setMonthFlag(String monthFlag) {
		this.monthFlag = monthFlag;
	}
	public String getMonthStartDay() {
		return monthStartDay;
	}
	public void setMonthStartDay(String monthStartDay) {
		this.monthStartDay = monthStartDay;
	}
	public String getMonthEndDay() {
		return monthEndDay;
	}
	public void setMonthEndDay(String monthEndDay) {
		this.monthEndDay = monthEndDay;
	}
	public String getYearFlag() {
		return yearFlag;
	}
	public void setYearFlag(String yearFlag) {
		this.yearFlag = yearFlag;
	}
	public String getYearStartMonth() {
		return yearStartMonth;
	}
	public void setYearStartMonth(String yearStartMonth) {
		this.yearStartMonth = yearStartMonth;
	}
	public String getYearEndMonth() {
		return yearEndMonth;
	}
	public void setYearEndMonth(String yearEndMonth) {
		this.yearEndMonth = yearEndMonth;
	}
	public String getHolidayTime() {
		return holidayTime;
	}
	public void setHolidayTime(String holidayTime) {
		this.holidayTime = holidayTime;
	}
	public String getPaidleaveTime() {
		return paidleaveTime;
	}
	public void setPaidleaveTime(String paidleaveTime) {
		this.paidleaveTime = paidleaveTime;
	}
}
