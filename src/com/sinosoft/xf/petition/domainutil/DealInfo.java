package com.sinosoft.xf.petition.domainutil;

import java.sql.Timestamp;

/**
 * 用于封装办理信息
 * @author mengxy
 */
public class DealInfo {
	private static final long serialVersionUID = 1L;
	private String dealId;
	private String assignId;
	private String transId;
	private String petitionNo;
	private String regionCode;
	private String regionName;
	private String dealNo;
	private int dealNum;
	private String procNum;
	private String currFlag;//1 已办理过 0、当前办理
	private String jointFlag;//联办1、联办 2、自办
	private String deptCode;
	private String deptName;
	private String userCode;
	private String userName;
	private String dealTypeCode;
	private String dealTypeName;
	private String assistDeptCode;
	private String assistDeptName;
	private String isPetitionBriefing;
	private String dealSuggestion;
	private String fromRegionCode;
	private String fromRegionName;
	private String toRegionCode;
	private String toRegionName;
	private String twoUnits;
	private Timestamp inputDate;
	private Timestamp sendDate;
	private Timestamp arriveDate;
	private Timestamp receiveDate;
	private Timestamp backDate;
	private String backDealerCode;
	private Timestamp backDealDate;
	private Timestamp reportDate;
	private Timestamp reportSendDate;
	private String backReason;
	private String letterNo;//	交办函号		VARCHAR（50）		
	private String letterContent;//	交办函件内容		VARCHAR（6000）		
	private Timestamp assignEndDate;//	交办截止日期		DATE		
	private String secrecyCode;//	交办函密级代码	代码表—	VARCHAR（50）		
	private String secrecyName;//	交办函密级代码值		VARCHAR（100）		
	private Timestamp secrecyDate;//	交办函保密日期		DATE		
	private String responseContent;//	交办反馈内容		VARCHAR（2000）		
	private Timestamp endDate;//	完结日期		TIMESTAMP	
	/**
	 * @return the petitionNo
	 */
	public String getPetitionNo() {
		return petitionNo;
	}
	/**
	 * @param petitionNo the petitionNo to set
	 */
	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}
	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}
	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	/**
	 * @return the dealNo
	 */
	public String getDealNo() {
		return dealNo;
	}
	/**
	 * @param dealNo the dealNo to set
	 */
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	/**
	 * @return the dealNum
	 */
	public int getDealNum() {
		return dealNum;
	}
	/**
	 * @param dealNum the dealNum to set
	 */
	public void setDealNum(int dealNum) {
		this.dealNum = dealNum;
	}
	/**
	 * @return the procNum
	 */
	public String getProcNum() {
		return procNum;
	}
	/**
	 * @param procNum the procNum to set
	 */
	public void setProcNum(String procNum) {
		this.procNum = procNum;
	}
	/**
	 * @return the currFlag
	 */
	public String getCurrFlag() {
		return currFlag;
	}
	/**
	 * @param currFlag the currFlag to set
	 */
	public void setCurrFlag(String currFlag) {
		this.currFlag = currFlag;
	}
	/**
	 * @return the jointFlag
	 */
	public String getJointFlag() {
		return jointFlag;
	}
	/**
	 * @param jointFlag the jointFlag to set
	 */
	public void setJointFlag(String jointFlag) {
		this.jointFlag = jointFlag;
	}
	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}
	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}
	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the dealTypeCode
	 */
	public String getDealTypeCode() {
		return dealTypeCode;
	}
	/**
	 * @param dealTypeCode the dealTypeCode to set
	 */
	public void setDealTypeCode(String dealTypeCode) {
		this.dealTypeCode = dealTypeCode;
	}
	/**
	 * @return the dealTypeName
	 */
	public String getDealTypeName() {
		return dealTypeName;
	}
	/**
	 * @param dealTypeName the dealTypeName to set
	 */
	public void setDealTypeName(String dealTypeName) {
		this.dealTypeName = dealTypeName;
	}
	/**
	 * @return the assistDeptCode
	 */
	public String getAssistDeptCode() {
		return assistDeptCode;
	}
	/**
	 * @param assistDeptCode the assistDeptCode to set
	 */
	public void setAssistDeptCode(String assistDeptCode) {
		this.assistDeptCode = assistDeptCode;
	}
	/**
	 * @return the assistDeptName
	 */
	public String getAssistDeptName() {
		return assistDeptName;
	}
	/**
	 * @param assistDeptName the assistDeptName to set
	 */
	public void setAssistDeptName(String assistDeptName) {
		this.assistDeptName = assistDeptName;
	}
	/**
	 * @return the isPetitionBriefing
	 */
	public String getIsPetitionBriefing() {
		return isPetitionBriefing;
	}
	/**
	 * @param isPetitionBriefing the isPetitionBriefing to set
	 */
	public void setIsPetitionBriefing(String isPetitionBriefing) {
		this.isPetitionBriefing = isPetitionBriefing;
	}
	/**
	 * @return the dealSuggestion
	 */
	public String getDealSuggestion() {
		return dealSuggestion;
	}
	/**
	 * @param dealSuggestion the dealSuggestion to set
	 */
	public void setDealSuggestion(String dealSuggestion) {
		this.dealSuggestion = dealSuggestion;
	}
	/**
	 * @return the fromRegionCode
	 */
	public String getFromRegionCode() {
		return fromRegionCode;
	}
	/**
	 * @param fromRegionCode the fromRegionCode to set
	 */
	public void setFromRegionCode(String fromRegionCode) {
		this.fromRegionCode = fromRegionCode;
	}
	/**
	 * @return the fromRegionName
	 */
	public String getFromRegionName() {
		return fromRegionName;
	}
	/**
	 * @param fromRegionName the fromRegionName to set
	 */
	public void setFromRegionName(String fromRegionName) {
		this.fromRegionName = fromRegionName;
	}
	/**
	 * @return the toRegionCode
	 */
	public String getToRegionCode() {
		return toRegionCode;
	}
	/**
	 * @param toRegionCode the toRegionCode to set
	 */
	public void setToRegionCode(String toRegionCode) {
		this.toRegionCode = toRegionCode;
	}
	/**
	 * @return the toRegionName
	 */
	public String getToRegionName() {
		return toRegionName;
	}
	/**
	 * @param toRegionName the toRegionName to set
	 */
	public void setToRegionName(String toRegionName) {
		this.toRegionName = toRegionName;
	}
	/**
	 * @return the twoUnits
	 */
	public String getTwoUnits() {
		return twoUnits;
	}
	/**
	 * @param twoUnits the twoUnits to set
	 */
	public void setTwoUnits(String twoUnits) {
		this.twoUnits = twoUnits;
	}
	/**
	 * @return the inputDate
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}
	/**
	 * @param inputDate the inputDate to set
	 */
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	/**
	 * @return the sendDate
	 */
	public Timestamp getSendDate() {
		return sendDate;
	}
	/**
	 * @param sendDate the sendDate to set
	 */
	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}
	/**
	 * @return the arriveDate
	 */
	public Timestamp getArriveDate() {
		return arriveDate;
	}
	/**
	 * @param arriveDate the arriveDate to set
	 */
	public void setArriveDate(Timestamp arriveDate) {
		this.arriveDate = arriveDate;
	}
	/**
	 * @return the receiveDate
	 */
	public Timestamp getReceiveDate() {
		return receiveDate;
	}
	/**
	 * @param receiveDate the receiveDate to set
	 */
	public void setReceiveDate(Timestamp receiveDate) {
		this.receiveDate = receiveDate;
	}
	/**
	 * @return the backDate
	 */
	public Timestamp getBackDate() {
		return backDate;
	}
	/**
	 * @param backDate the backDate to set
	 */
	public void setBackDate(Timestamp backDate) {
		this.backDate = backDate;
	}
	/**
	 * @return the backDealerCode
	 */
	public String getBackDealerCode() {
		return backDealerCode;
	}
	/**
	 * @param backDealerCode the backDealerCode to set
	 */
	public void setBackDealerCode(String backDealerCode) {
		this.backDealerCode = backDealerCode;
	}
	/**
	 * @return the backDealDate
	 */
	public Timestamp getBackDealDate() {
		return backDealDate;
	}
	/**
	 * @param backDealDate the backDealDate to set
	 */
	public void setBackDealDate(Timestamp backDealDate) {
		this.backDealDate = backDealDate;
	}
	/**
	 * @return the reportDate
	 */
	public Timestamp getReportDate() {
		return reportDate;
	}
	/**
	 * @param reportDate the reportDate to set
	 */
	public void setReportDate(Timestamp reportDate) {
		this.reportDate = reportDate;
	}
	/**
	 * @return the reportSendDate
	 */
	public Timestamp getReportSendDate() {
		return reportSendDate;
	}
	/**
	 * @param reportSendDate the reportSendDate to set
	 */
	public void setReportSendDate(Timestamp reportSendDate) {
		this.reportSendDate = reportSendDate;
	}
	/**
	 * @return the backReason
	 */
	public String getBackReason() {
		return backReason;
	}
	/**
	 * @param backReason the backReason to set
	 */
	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}
	/**
	 * @return the letterNo
	 */
	public String getLetterNo() {
		return letterNo;
	}
	/**
	 * @param letterNo the letterNo to set
	 */
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	/**
	 * @return the letterContent
	 */
	public String getLetterContent() {
		return letterContent;
	}
	/**
	 * @param letterContent the letterContent to set
	 */
	public void setLetterContent(String letterContent) {
		this.letterContent = letterContent;
	}
	/**
	 * @return the assignEndDate
	 */
	public Timestamp getAssignEndDate() {
		return assignEndDate;
	}
	/**
	 * @param assignEndDate the assignEndDate to set
	 */
	public void setAssignEndDate(Timestamp assignEndDate) {
		this.assignEndDate = assignEndDate;
	}
	/**
	 * @return the secrecyCode
	 */
	public String getSecrecyCode() {
		return secrecyCode;
	}
	/**
	 * @param secrecyCode the secrecyCode to set
	 */
	public void setSecrecyCode(String secrecyCode) {
		this.secrecyCode = secrecyCode;
	}
	/**
	 * @return the secrecyName
	 */
	public String getSecrecyName() {
		return secrecyName;
	}
	/**
	 * @param secrecyName the secrecyName to set
	 */
	public void setSecrecyName(String secrecyName) {
		this.secrecyName = secrecyName;
	}
	/**
	 * @return the secrecyDate
	 */
	public Timestamp getSecrecyDate() {
		return secrecyDate;
	}
	/**
	 * @param secrecyDate the secrecyDate to set
	 */
	public void setSecrecyDate(Timestamp secrecyDate) {
		this.secrecyDate = secrecyDate;
	}
	/**
	 * @return the responseContent
	 */
	public String getResponseContent() {
		return responseContent;
	}
	/**
	 * @param responseContent the responseContent to set
	 */
	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}
	/**
	 * @return the endDate
	 */
	public Timestamp getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	/**
	 * @return the dealId
	 */
	public String getDealId() {
		return dealId;
	}
	/**
	 * @param dealId the dealId to set
	 */
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	/**
	 * @return the assignId
	 */
	public String getAssignId() {
		return assignId;
	}
	/**
	 * @param assignId the assignId to set
	 */
	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}
	/**
	 * @return the transId
	 */
	public String getTransId() {
		return transId;
	}
	/**
	 * @param transId the transId to set
	 */
	public void setTransId(String transId) {
		this.transId = transId;
	}
}
