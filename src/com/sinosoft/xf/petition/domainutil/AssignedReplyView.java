package com.sinosoft.xf.petition.domainutil;

/**
 * 交办回复view
 */
public class AssignedReplyView implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;//基本表ID
	private String assignId;//交办表ID
	private String regionCode;
	private String petitionDate;
	private String petitionNo;
	private String dealTypeCode;
	private String petitionTypeCode;
	private String petitionTitle;
	private String activityNo;
	
	private String toTypeName;
	private String toRegionName;
	private String inputDate;
	private String reportDate;
	private String petitionPrtNo;
	private String dealTypeName;
	private String petitionClassCode;
	private String petitionClassName;
	private String currPetitionNo;
	private String letterNo;
	private String importantFlag;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getPetitionDate() {
		return petitionDate;
	}
	public void setPetitionDate(String petitionDate) {
		this.petitionDate = petitionDate;
	}
	public String getPetitionNo() {
		return petitionNo;
	}
	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	public String getDealTypeCode() {
		return dealTypeCode;
	}
	public void setDealTypeCode(String dealTypeCode) {
		this.dealTypeCode = dealTypeCode;
	}
	public String getToTypeName() {
		return toTypeName;
	}
	public void setToTypeName(String toTypeName) {
		this.toTypeName = toTypeName;
	}
	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	public String getToRegionName() {
		return toRegionName;
	}
	public void setToRegionName(String toRegionName) {
		this.toRegionName = toRegionName;
	}
	public String getPetitionTypeCode() {
		return petitionTypeCode;
	}
	public void setPetitionTypeCode(String petitionTypeCode) {
		this.petitionTypeCode = petitionTypeCode;
	}
	public String getPetitionTitle() {
		return petitionTitle;
	}
	public void setPetitionTitle(String petitionTitle) {
		this.petitionTitle = petitionTitle;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getPetitionPrtNo() {
		return petitionPrtNo;
	}
	public void setPetitionPrtNo(String petitionPrtNo) {
		this.petitionPrtNo = petitionPrtNo;
	}
	public String getDealTypeName() {
		return dealTypeName;
	}
	public void setDealTypeName(String dealTypeName) {
		this.dealTypeName = dealTypeName;
	}
	public String getPetitionClassCode() {
		return petitionClassCode;
	}
	public void setPetitionClassCode(String petitionClassCode) {
		this.petitionClassCode = petitionClassCode;
	}
	public String getPetitionClassName() {
		return petitionClassName;
	}
	public void setPetitionClassName(String petitionClassName) {
		this.petitionClassName = petitionClassName;
	}
	public String getCurrPetitionNo() {
		return currPetitionNo;
	}
	public void setCurrPetitionNo(String currPetitionNo) {
		this.currPetitionNo = currPetitionNo;
	}
	public String getLetterNo() {
		return letterNo;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	public String getAssignId() {
		return assignId;
	}
	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}
	public String getImportantFlag() {
		return importantFlag;
	}
	public void setImportantFlag(String importantFlag) {
		this.importantFlag = importantFlag;
	}
	
	
}