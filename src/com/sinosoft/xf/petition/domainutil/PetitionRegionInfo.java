package com.sinosoft.xf.petition.domainutil;

import java.io.Serializable;


/**
 * 用于封装区县机构信息
 * @date 2013-8-7
 * @author jk
 */
public class PetitionRegionInfo implements  Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private int num;//机构序号
	private String yearOrMonth;
	private String regionCode;
	private String regionName;
	private int  yearCount=0;//年累计
	private int monthCount=0;//月累计
	private int  yearZB=0;//年自办
	private int monthZB=0;//月自办
	private int  yearTB=0;//年转交办
	private int monthTB=0;//月转交办
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public int getYearCount() {
		return yearCount;
	}
	public void setYearCount(int yearCount) {
		this.yearCount = yearCount;
	}
	public int getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(int monthCount) {
		this.monthCount = monthCount;
	}
	public int getYearZB() {
		return yearZB;
	}
	public void setYearZB(int yearZB) {
		this.yearZB = yearZB;
	}
	public int getMonthZB() {
		return monthZB;
	}
	public void setMonthZB(int monthZB) {
		this.monthZB = monthZB;
	}
	public int getYearTB() {
		return yearTB;
	}
	public void setYearTB(int yearTB) {
		this.yearTB = yearTB;
	}
	public int getMonthTB() {
		return monthTB;
	}
	public void setMonthTB(int monthTB) {
		this.monthTB = monthTB;
	}
	public String getYearOrMonth() {
		return yearOrMonth;
	}
	public void setYearOrMonth(String yearOrMonth) {
		this.yearOrMonth = yearOrMonth;
	}
}
