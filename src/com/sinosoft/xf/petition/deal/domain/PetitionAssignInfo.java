package com.sinosoft.xf.petition.deal.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;
/**
 * 办理交办信息表
 * @date 2011-11-30
 * @author Oba
 */
@Entity
@Table(name = "PETITION_ASSIGN_INFO")
public class PetitionAssignInfo extends AudiEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String dealNo;//	办理编号	参考“信访办理编码”规则	CHAR（20）
	private String petitionNo;	//信访编号						CHAR(26)
	private PetitionDealInfo petitionDealInfo;//	信访办理信息表oid		CHAR（20）		
	private String regionCode;//	当前纪检监察机构/行政区域	参考“行政区域编码”规则	CHAR（12）		
	private String regionName;//	当前纪检监察机构/行政区域值		Varchar（100）		
	private String dealTypeCode;//	交办类型代码	代码表—	VARCHAR（50）		
	private String dealTypeName;//	交办类型代码值		VARCHAR（100）		
	private String fromRegionCode;//	交办机构编码		CHAR（12）		
	private String fromRegionName;//	交办机构编码值		Varchar（100）		
	private String toRegionCode;//	接收机构编码		CHAR（12）		
	private String toRegionName;//	接收机构编码值		Varchar(100)		
	private String twoUnits;//	接收机构细化部门描述		VARCHAR（100）		
	private String letterNo;//	交办函号		VARCHAR（50）		
	private String letterContent;//	交办函件内容		VARCHAR（6000）		
	private Timestamp assignEndDate;//	交办截止日期		DATE		
	//private String secrecyCode;//	交办函密级代码	代码表—	VARCHAR（50）		
	//private String secrecyName;//	交办函密级代码值		VARCHAR（100）		
	//private Timestamp secrecyDate;//	交办函保密日期		DATE		
	private Timestamp inputDate;//	录入日期		TIMESTAMP		
	private Timestamp sendDate;//	传输日期		TIMESTAMP		
	private Timestamp arriveDate;//	数据交换日期		TIMESTAMP		
	private Timestamp receiveDate;//	下级首次接收日期		TIMESTAMP		
	private Timestamp reportDate;//	下级回复上报日期		TIMESTAMP	交办了结上报的时候添加。。转办的话接收的时候就添加	
	private Timestamp accectReportDate;//	下级上传数据日期	
	private Timestamp reportSendDate;//	下级回复数据交换日期		TIMESTAMP		
	private Timestamp backDate;//	回退日期		TIMESTAMP		
	private String backReason;//回退原因 varchar(6000)
	private String backDealerCode;//	回退处理人代码		CHAR（10）		
	private Timestamp backDealDate;//	回退处理日期		TIMESTAMP		
	private String responseContent;//	交办反馈内容		VARCHAR（2000）		
	private Timestamp endDate;//	完结日期		TIMESTAMP	
	private String remarksInfo;//	交办备注信息		VARCHAR(100)
	private String validFlag;  //   标记转办信息是否有效  0有效，1无效		CHAR(1)
	
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
	public Timestamp getAccectReportDate() {
		return accectReportDate;
	}
	public void setAccectReportDate(Timestamp accectReportDate) {
		this.accectReportDate = accectReportDate;
	}
	
}
