package com.sinosoft.xf.petition.petitionaccept.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * RepeatRuleInfo entity.
 * 
 * @author ljx
 */
@Entity
@Table(name = "REPEAT_RULE_INFO")
public class RepeatRuleInfo extends AudiEntity{

	private static final long serialVersionUID = 1L;

	private String regionCode;
	private String regionName;
	private String petitionTypeFlag;
	private String petitionClassFlag;
	private String petitionSourceFlag;
	private String issueTypeFlag;
	private String validFlag;
	private Timestamp validStartDate;
	private Timestamp validEndDate;
	private String petitionEffectInterval;


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

	@Column(name = "PETITION_TYPE_FLAG", length = 1)
	public String getPetitionTypeFlag() {
		return this.petitionTypeFlag;
	}

	public void setPetitionTypeFlag(String petitionTypeFlag) {
		this.petitionTypeFlag = petitionTypeFlag;
	}

	@Column(name = "PETITION_CLASS_FLAG", length = 1)
	public String getPetitionClassFlag() {
		return this.petitionClassFlag;
	}

	public void setPetitionClassFlag(String petitionClassFlag) {
		this.petitionClassFlag = petitionClassFlag;
	}

	@Column(name = "PETITION_SOURCE_FLAG", length = 1)
	public String getPetitionSourceFlag() {
		return this.petitionSourceFlag;
	}

	public void setPetitionSourceFlag(String petitionSourceFlag) {
		this.petitionSourceFlag = petitionSourceFlag;
	}

	@Column(name = "ISSUE_TYPE_FLAG", length = 1)
	public String getIssueTypeFlag() {
		return this.issueTypeFlag;
	}

	public void setIssueTypeFlag(String issueTypeFlag) {
		this.issueTypeFlag = issueTypeFlag;
	}

	@Column(name = "VALID_FLAG", length = 1)
	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public Timestamp getValidStartDate() {
		return this.validStartDate;
	}

	public void setValidStartDate(Timestamp validStartDate) {
		this.validStartDate = validStartDate;
	}

	public Timestamp getValidEndDate() {
		return this.validEndDate;
	}

	public void setValidEndDate(Timestamp validEndDate) {
		this.validEndDate = validEndDate;
	}

	public String getPetitionEffectInterval() {
		return petitionEffectInterval;
	}

	public void setPetitionEffectInterval(String petitionEffectInterval) {
		this.petitionEffectInterval = petitionEffectInterval;
	}


	
	

}