package com.sinosoft.xf.petition.deal.domain;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 办理转办信息缓存表
 * @date 2011-11-30
 * @author ljx
 */
@Entity
@Table(name = "PETITION_TRANS_DEAL_INFO_TEMP")
public class PetitionTransDealInfoTemp  extends AudiEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String petitionNo;
	private String dealNo;
	private PetitionDealInfoTemp petitionDealInfoTemp;
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
	private String tempFlag;
	private String backReason;
	private String remarksInfo;//	转办备注信息		VARCHAR(100)
	private String validFlag;  //   标记转办信息是否有效  0有效，1无效 
	private String expressNo; //挂号条码 
	/**
	 * @return the petitionDealInfo
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "PETITION_DEAL_INFO_TEMP_OID")
	public PetitionDealInfoTemp getPetitionDealInfoTemp() {
		return petitionDealInfoTemp;
	}
	
	/**
	 * @param petitionDealInfo the petitionDealInfo to set
	 */
	public void setPetitionDealInfoTemp(PetitionDealInfoTemp petitionDealInfoTemp) {
		this.petitionDealInfoTemp = petitionDealInfoTemp;
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
	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	
	@Column(name = "SEND_DATE", length = 26)
	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}

	
	@Column(name = "ARRIVE_DATE", length = 26)
	public Date getArriveDate() {
		return this.arriveDate;
	}

	public void setArriveDate(Timestamp arriveDate) {
		this.arriveDate = arriveDate;
	}

	
	@Column(name = "RECEIVE_DATE", length = 26)
	public Date getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(Timestamp receiveDate) {
		this.receiveDate = receiveDate;
	}

	
	@Column(name = "BACK_DATE", length = 26)
	public Timestamp getBackDate() {
		return this.backDate;
	}

	public void setBackDate(Timestamp backDate) {
		this.backDate = backDate;
	}

	
	public String getBackDealerCode() {
		return this.backDealerCode;
	}

	public void setBackDealerCode(String backDealerCode) {
		this.backDealerCode = backDealerCode;
	}

	
	@Column(name = "BACK_DEAL_DATE", length = 26)
	public Date getBackDealDate() {
		return this.backDealDate;
	}

	public void setBackDealDate(Timestamp backDealDate) {
		this.backDealDate = backDealDate;
	}

	public Timestamp getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Timestamp reportDate) {
		this.reportDate = reportDate;
	}

	
	@Column(name = "REPORT_SEND_DATE", length = 26)
	public Date getReportSendDate() {
		return this.reportSendDate;
	}

	public void setReportSendDate(Timestamp reportSendDate) {
		this.reportSendDate = reportSendDate;
	}
	
	@Column(name = "TEMP_FLAG", length = 1)
	public String getTempFlag() {
		return this.tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
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
}