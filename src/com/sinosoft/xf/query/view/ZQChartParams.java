package com.sinosoft.xf.query.view;

import com.sinosoft.xf.query.common.iface.SqlHelper;

/**
 * 钻取参数，用于接受钻取时前台用户传来的参数。
 * @author hjh
 *
 * 2015-1-29下午08:05:43
 */
public class ZQChartParams {

	private String dataset;
	private String petitionClassCode;
	private String issueTypeCode;
	private String petitionTypeCode;
	private String objectClassCode;
	private String repeatFlag;
	private String firstAccuserFlag;
	private String isSwgat;//涉外及港澳台字段
	private String timeRange = SqlHelper.CUSTOM;
	private int start = 0;
	private int limit = 20;
	/**
	 * 问题大类代码
	 */
	private String parentIssueCode;
	private String petitionUrgeCode;
	public String getDataset() {
		return dataset;
	}
	public void setDataset(String dataset) {
		this.dataset = dataset.trim();
	}
	public String getPetitionClassCode() {
		return petitionClassCode;
	}
	public void setPetitionClassCode(String petitionClassCode) {
		this.petitionClassCode = petitionClassCode.trim();
	}
	public String getIssueTypeCode() {
		return issueTypeCode;
	}
	public void setIssueTypeCode(String issueTypeCode) {
		this.issueTypeCode = issueTypeCode.trim();
	}
	public String getPetitionTypeCode() {
		return petitionTypeCode;
	}
	public void setPetitionTypeCode(String petitionTypeCode) {
		this.petitionTypeCode = petitionTypeCode.trim();
	}
	public String getObjectClassCode() {
		return objectClassCode;
	}
	public void setObjectClassCode(String objectClassCode) {
		this.objectClassCode = objectClassCode.trim();
	}
	public String getRepeatFlag() {
		return repeatFlag;
	}
	public void setRepeatFlag(String repeatFlag) {
		this.repeatFlag = repeatFlag.trim();
	}
	public String getFirstAccuserFlag() {
		return firstAccuserFlag;
	}
	public void setFirstAccuserFlag(String firstAccuserFlag) {
		this.firstAccuserFlag = firstAccuserFlag.trim();
	}
	public String getParentIssueCode() {
		return parentIssueCode;
	}
	public void setParentIssueCode(String parentIssueCode) {
		this.parentIssueCode = parentIssueCode.trim();
	}
	
	public String getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange.trim();
	}
	public String getPetitionUrgeCode() {
		return petitionUrgeCode;
	}
	public void setPetitionUrgeCode(String petitionUrgeCode) {
		this.petitionUrgeCode = petitionUrgeCode.trim();
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getIsSwgat() {
		return isSwgat;
	}
	public void setIsSwgat(String isSwgat) {
		this.isSwgat = isSwgat;
	}
	
	
}
