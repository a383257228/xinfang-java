package com.sinosoft.xf.petition.accept.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;

/**
 *延期申请表
 */
@Entity
@Table(name = "PETITION_POSTPONE_INFO")
public class PetitionPostponeInfo extends IdEntity {

	private static final long serialVersionUID = 1L;
	private String postponeNo;
	private String regionCode; 
	private String regionName; 
	private String postponeReason;
	private String postponePerson;
	private Timestamp postponeDate;
	private String postponeInstructCode;
	private PetitionBasicInfo petitionBasicInfo;
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "petitionBasicInfoOid")
	public PetitionBasicInfo getPetitionBasicInfo() {
		return petitionBasicInfo;
	}
	public void setPetitionBasicInfo(PetitionBasicInfo petitionBasicInfo) {
		this.petitionBasicInfo = petitionBasicInfo;
	}
	public String getPostponeNo() {
		return postponeNo;
	}
	public void setPostponeNo(String postponeNo) {
		this.postponeNo = postponeNo;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getPostponeReason() {
		return postponeReason;
	}
	public void setPostponeReason(String postponeReason) {
		this.postponeReason = postponeReason;
	}
	public Timestamp getPostponeDate() {
		return postponeDate;
	}
	public void setPostponeDate(Timestamp postponeDate) {
		this.postponeDate = postponeDate;
	}
	public String getPostponeInstructCode() {
		return postponeInstructCode;
	}
	public void setPostponeInstructCode(String postponeInstructCode) {
		this.postponeInstructCode = postponeInstructCode;
	}
	public String getPostponePerson() {
		return postponePerson;
	}
	public void setPostponePerson(String postponePerson) {
		this.postponePerson = postponePerson;
	}
	
}
