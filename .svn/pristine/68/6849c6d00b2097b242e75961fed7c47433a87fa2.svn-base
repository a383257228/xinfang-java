package com.sinosoft.xf.petition.accept.domain;
// default package

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;


/**
 * 信访交办信息表实体类信息
 * @date 2011-11-09
 * @author wangxx
 */
@Entity
@Table(name = "PETITION_ASSIGNED_INFO")
public class PetitionAssignedInfo extends IdEntity{
	
	private static final long serialVersionUID = 1L;

	private PetitionBasicInfo petitionBasicInfo;
	private String regionCode;
	private String regionName;
	private Timestamp assignedDate;
	private String assignedLetterText;
	private String assignedContent;
	private Timestamp assignedRequireEndDate;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "petitionBasicInfoOid")
	public PetitionBasicInfo getPetitionBasicInfo() {
		return petitionBasicInfo;
	}
	/**
	 * @param petitionBasicInfo the petitionBasicInfo to set
	 */
	public void setPetitionBasicInfo(PetitionBasicInfo petitionBasicInfo) {
		this.petitionBasicInfo = petitionBasicInfo;
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
	 * @return the assignedDate
	 */
	public Timestamp getAssignedDate() {
		return assignedDate;
	}
	/**
	 * @param assignedDate the assignedDate to set
	 */
	public void setAssignedDate(Timestamp assignedDate) {
		this.assignedDate = assignedDate;
	}
	/**
	 * @return the assignedLetterText
	 */
	public String getAssignedLetterText() {
		return assignedLetterText;
	}
	/**
	 * @param assignedLetterText the assignedLetterText to set
	 */
	public void setAssignedLetterText(String assignedLetterText) {
		this.assignedLetterText = assignedLetterText;
	}
	/**
	 * @return the assignedContent
	 */
	public String getAssignedContent() {
		return assignedContent;
	}
	/**
	 * @param assignedContent the assignedContent to set
	 */
	public void setAssignedContent(String assignedContent) {
		this.assignedContent = assignedContent;
	}
	/**
	 * @return the assignedRequireEndDate
	 */
	public Timestamp getAssignedRequireEndDate() {
		return assignedRequireEndDate;
	}
	/**
	 * @param assignedRequireEndDate the assignedRequireEndDate to set
	 */
	public void setAssignedRequireEndDate(Timestamp assignedRequireEndDate) {
		this.assignedRequireEndDate = assignedRequireEndDate;
	}
}