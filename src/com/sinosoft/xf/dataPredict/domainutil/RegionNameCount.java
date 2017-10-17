package com.sinosoft.xf.dataPredict.domainutil;

import java.io.Serializable;

public class RegionNameCount implements  Serializable{
	private static final long serialVersionUID = 1L;
	private int zsCount;
	private int sjCount;
	private int xjCount;
	private String issueRegionName;
	private String issueRegionCode;
	public int getZsCount() {
		return zsCount;
	}
	public void setZsCount(int zsCount) {
		this.zsCount = zsCount;
	}
	public int getSjCount() {
		return sjCount;
	}
	public void setSjCount(int sjCount) {
		this.sjCount = sjCount;
	}
	public int getXjCount() {
		return xjCount;
	}
	public void setXjCount(int xjCount) {
		this.xjCount = xjCount;
	}
	public String getIssueRegionName() {
		return issueRegionName;
	}
	public void setIssueRegionName(String issueRegionName) {
		this.issueRegionName = issueRegionName;
	}
	public String getIssueRegionCode() {
		return issueRegionCode;
	}
	public void setIssueRegionCode(String issueRegionCode) {
		this.issueRegionCode = issueRegionCode;
	}
}
