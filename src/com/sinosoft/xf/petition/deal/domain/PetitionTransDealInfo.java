package com.sinosoft.xf.petition.deal.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 办理转办信息表
 * @date 2011-11-30
 * @author ljx
 */
@Entity
@Table(name = "PETITION_TRANS_DEAL_INFO")
public class PetitionTransDealInfo extends AudiEntity{
	
	private static final long serialVersionUID = 1L;

	private String petitionNo;
	private String dealNo;
	private PetitionDealInfo petitionDealInfo;
	private String regionCode;
	private String regionName;
	private String dealTypeCode;
	private String dealTypeName;
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
	private String remarksInfo;//	(承办单位)转办备注信息		VARCHAR(100)
	private String validFlag;  //   标记转办信息是否有效  0有效，1无效 
	private String expressNo; //挂号条码
	private String letterNo;//	转办函号
	private String letterContent;//	转办函件内容
	private String batchFlag = "0"; //批量转发标志，0否1是
	private Timestamp accectReportDate;//	下级上传数据日期	
	
	/**
	 * @return the petitionDealInfo
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "PETITION_DEAL_INFO_OID")
	public PetitionDealInfo getPetitionDealInfo() {
		return petitionDealInfo;
	}
	
	/**
	 * @param petitionDealInfo the petitionDealInfo to set
	 */
	public void setPetitionDealInfo(PetitionDealInfo petitionDealInfo) {
		this.petitionDealInfo = petitionDealInfo;
	}

	@Column(name = "DEAL_NO", length = 20)
	public String getDealNo() {
		return this.dealNo;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	@Column(name = "REGION_CODE", length = 12)
	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	@Column(name = "REGION_NAME", length = 100)
	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Column(name = "DEAL_TYPE_CODE", length = 50)
	public String getDealTypeCode() {
		return this.dealTypeCode;
	}

	public void setDealTypeCode(String dealTypeCode) {
		this.dealTypeCode = dealTypeCode;
	}

	@Column(name = "DEAL_TYPE_NAME", length = 100)
	public String getDealTypeName() {
		return this.dealTypeName;
	}

	public void setDealTypeName(String dealTypeName) {
		this.dealTypeName = dealTypeName;
	}

	@Column(name = "FROM_REGION_CODE", length = 12)
	public String getFromRegionCode() {
		return this.fromRegionCode;
	}

	public void setFromRegionCode(String fromRegionCode) {
		this.fromRegionCode = fromRegionCode;
	}

	@Column(name = "FROM_REGION_NAME", length = 100)
	public String getFromRegionName() {
		return this.fromRegionName;
	}

	public void setFromRegionName(String fromRegionName) {
		this.fromRegionName = fromRegionName;
	}

	@Column(name = "TO_REGION_CODE", length = 12)
	public String getToRegionCode() {
		return this.toRegionCode;
	}

	public void setToRegionCode(String toRegionCode) {
		this.toRegionCode = toRegionCode;
	}

	@Column(name = "TO_REGION_NAME", length = 100)
	public String getToRegionName() {
		return this.toRegionName;
	}

	public void setToRegionName(String toRegionName) {
		this.toRegionName = toRegionName;
	}

	@Column(name = "TWO_UNITS", length = 100)
	public String getTwoUnits() {
		return this.twoUnits;
	}

	public void setTwoUnits(String twoUnits) {
		this.twoUnits = twoUnits;
	}

	
	@Column(name = "INPUT_DATE", length = 26)
	public Timestamp getInputDate() {
		return this.inputDate;
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
	 * @param inputDate the inputDate to set
	 */
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public String getRemarksInfo() {
		return remarksInfo;
	}

	public void setRemarksInfo(String remarksInfo) {
		this.remarksInfo = remarksInfo;
	}

	/**
	 * @return the validFlag
	 */
	public String getValidFlag() {
		return validFlag;
	}

	/**
	 * @param validFlag the validFlag to set
	 */
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	@Column(name = "PETITION_NO", length = 26)
	public String getPetitionNo() {
		return petitionNo;
	}

	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}

	@Column(name = "Express_No", length = 50)
	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getLetterNo() {
		return letterNo;
	}

	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}

	public String getLetterContent() {
		return letterContent;
	}

	public void setLetterContent(String letterContent) {
		this.letterContent = letterContent;
	}

	public String getBatchFlag() {
		return batchFlag;
	}

	public void setBatchFlag(String batchFlag) {
		this.batchFlag = batchFlag;
	}

	public Timestamp getAccectReportDate() {
		return accectReportDate;
	}

	public void setAccectReportDate(Timestamp accectReportDate) {
		this.accectReportDate = accectReportDate;
	}

}