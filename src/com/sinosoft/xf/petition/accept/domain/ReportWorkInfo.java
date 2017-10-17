package com.sinosoft.xf.petition.accept.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;

/**
 * 统计数据
 * @date 2012-10-18
 * @author ZPJ
 */
@Entity
@Table(name = "REPORT_WORK_INFO")
public class ReportWorkInfo extends IdEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String xfMode;  //标识信息来源自电话举报系统
	private Timestamp reportDate;  //统计时期
	private int ywn;               //统计业务内数据量（是否需要与导入的数据进行校验？）
	private int yww;              //统计业务外数据量
	private String reportDept;    //统计工作量的部门
	private String reportName;    //统计工作量的人员
	private String validFlag="0";
	
	public String getXfMode() {
		return xfMode;
	}
	public void setXfMode(String xfMode) {
		this.xfMode = xfMode;
	}
	public Timestamp getReportDate() {
		return reportDate;
	}
	public void setReportDate(Timestamp reportDate) {
		this.reportDate = reportDate;
	}
	public int getYwn() {
		return ywn;
	}
	public void setYwn(int ywn) {
		this.ywn = ywn;
	}
	public int getYww() {
		return yww;
	}
	public void setYww(int yww) {
		this.yww = yww;
	}
	public String getReportDept() {
		return reportDept;
	}
	public void setReportDept(String reportDept) {
		this.reportDept = reportDept;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}     
}
