package com.sinosoft.xf.petition.distribute.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 来信预受理信息
 * @date 2013-01-07
 * @author xuyi
 */
@Entity
@Table(name="QUI_LETTER_INFO")
public class QuiLetterInfo extends AudiEntity{
	private static final long serialVersionUID = 1L;
	private String quickLetterNo;		//预受理编号					CHAR(20)
	private String regionCode;			//当前纪监察机构/行政区域		CHAR(20)
	private String regionName;			//当前纪检监察机构/行政区域值  VARCHAR(100)
	private Timestamp quickLetterDate;	//预受理日期
	private String recipientName;		//收信人姓名					VARCHAR(120)
	private String mailAddress;			//寄信地址					VARCHAR(100)
	private String acceptExpressNo;		//举报件单号					VARCHAR(50)
	private String petitionPrtNo;		//信访条形码编码				CHAR(26)
	private String userCode;			//指定承办人编码				VARCHAR(50)
	private String userCname;			//指定承办人名称				VARCHAR(100)
	private String signFlag;			//签收标识					CHAR(1)
	private String signCode;			//签收人编码				VARCHAR(50)
	private String signCname;			//签收人名称					VARCHARA(100)
	private Timestamp signDate;			//签收时间
	private String letterFlag;			//信访件标识					CHAR(1)   1为信访件   0为私人信件
//	private String modifyId;			//修改人标识					CAHRA(20)
//	private String modifyName;			//修改人名称					VARCHARA(50)
//	private Timestamp modifyDate;		//修改时间
//	private String remark;				//备注						VARCHARA(2000)
	
	/**
	 * @return the quickLetterNo
	 */
	public String getQuickLetterNo() {
		return quickLetterNo;
	}
	/**
	 * @param quickLetterNon the quickLetterNo to set
	 */
	public void setQuickLetterNo(String quickLetterNo) {
		this.quickLetterNo = quickLetterNo;
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
	 * @return the quickLetterDate
	 */
	public Timestamp getQuickLetterDate() {
		return quickLetterDate;
	}
	/**
	 * @param quickLetterDate the quickLetterDate to set
	 */
	public void setQuickLetterDate(Timestamp quickLetterDate) {
		this.quickLetterDate = quickLetterDate;
	}
	/**
	 * @return the recipientName
	 */
	public String getRecipientName() {
		return recipientName;
	}
	/**
	 * @param recipientName the recipientName to set
	 */
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	/**
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}
	/**
	 * @param mailAddress the mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	/**
	 * @return the acceptExpressNo
	 */
	public String getAcceptExpressNo() {
		return acceptExpressNo;
	}
	/**
	 * @param acceptExpressNo the acceptExpressNo to set
	 */
	public void setAcceptExpressNo(String acceptExpressNo) {
		this.acceptExpressNo = acceptExpressNo;
	}
	
	/**
	 * @return the petitionPrtNo
	 */
	public String getPetitionPrtNo() {
		return petitionPrtNo;
	}
	/**
	 * @param petitionPrtNo the petitionPrtNo to set
	 */
	public void setPetitionPrtNo(String petitionPrtNo) {
		this.petitionPrtNo = petitionPrtNo;
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
	 * @return the userCname
	 */
	public String getUserCname() {
		return userCname;
	}
	/**
	 * @param userCname the userCname to set
	 */
	public void setUserCname(String userCname) {
		this.userCname = userCname;
	}
	/**
	 * @return the signFlag
	 */
	public String getSignFlag() {
		return signFlag;
	}
	/**
	 * @param signFlag the signFlag to set
	 */
	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}
	/**
	 * @return the remark
	 */
//	public String getRemark() {
//		return remark;
//	}
//	/**
//	 * @param remark the remark to set
//	 */
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
	public String getSignCode() {
		return signCode;
	}
	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}
	public String getSignCname() {
		return signCname;
	}
	public void setSignCname(String signCname) {
		this.signCname = signCname;
	}
	public Timestamp getSignDate() {
		return signDate;
	}
	public void setSignDate(Timestamp signDate) {
		this.signDate = signDate;
	}
	public String getLetterFlag() {
		return letterFlag;
	}
	public void setLetterFlag(String letterFlag) {
		this.letterFlag = letterFlag;
	}
	
}
