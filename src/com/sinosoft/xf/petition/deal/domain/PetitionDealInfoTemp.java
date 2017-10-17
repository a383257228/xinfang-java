package com.sinosoft.xf.petition.deal.domain;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfoTemp;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 办理信息缓存表
 * @date 2011-11-30
 * @author ljx
 */
@Entity
@Table(name = "PETITION_DEAL_INFO_TEMP")
public class PetitionDealInfoTemp  extends AudiEntity {
	
	private static final long serialVersionUID = 1L;

	private String petitionNo;
	private PetitionBasicInfoTemp petitionBasicInfoTemp;
	private String regionCode;
	private String regionName;
	private String dealNo;
	private int dealNum;
	private String procNum;
	private String jointFlag;
	private String deptCode;
	private String deptName;
	private String userCode;
	private String userName;
	private String dealTypeCode;
	private String dealTypeName;
	private String validFlag;//1 已办理过 0、当前办理
	private String assistDeptCode;
	private String assistDeptName;
	private String dealSuggestion;
	private String tempFlag;


	private PetitionTransDealInfoTemp transDealInfoTemp;
	private PetitionAssignInfoTemp assignInfoTemp;

	/**
	 * @return the PetitionTransDealInfo
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionDealInfoTemp")
	public PetitionAssignInfoTemp getAssignInfoTemp() {
		return assignInfoTemp;
	}
	/**
	 * @param PetitionTransDealInfo the PetitionTransDealInfo to set
	 */
	public void setAssignInfoTemp(PetitionAssignInfoTemp assignInfoTemp) {
		this.assignInfoTemp = assignInfoTemp;
	}

	/**
	 * @return the PetitionTransDealInfo
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionDealInfoTemp")
	public PetitionTransDealInfoTemp getTransDealInfoTemp() {
		return transDealInfoTemp;
	}
	/**
	 * @param PetitionTransDealInfo the PetitionTransDealInfo to set
	 */
	public void setTransDealInfoTemp(PetitionTransDealInfoTemp transDealInfoTemp) {
		this.transDealInfoTemp = transDealInfoTemp;
	}


	/**
	 * @return the petitionBasicInfo
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "PETITION_BASIC_INFO_TEMP_OID")
	public PetitionBasicInfoTemp getPetitionBasicInfoTemp() {
		return petitionBasicInfoTemp;
	}
	
	/**
	 * @param petitionBasicInfo the petitionBasicInfo to set
	 */
	public void setPetitionBasicInfoTemp(PetitionBasicInfoTemp petitionBasicInfoTemp) {
		this.petitionBasicInfoTemp = petitionBasicInfoTemp;
	}
	
	@Column(name = "PETITION_NO", length = 20)
	public String getPetitionNo() {
		return this.petitionNo;
	}

	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
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

	@Column(name = "DEAL_NO", length = 20)
	public String getDealNo() {
		return this.dealNo;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}


	public int getDealNum() {
		return dealNum;
	}
	public void setDealNum(int dealNum) {
		this.dealNum = dealNum;
	}
	@Column(name = "PROC_NUM", length = 5)
	public String getProcNum() {
		return this.procNum;
	}

	public void setProcNum(String procNum) {
		this.procNum = procNum;
	}

	

	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	public String getAssistDeptCode() {
		return assistDeptCode;
	}
	public void setAssistDeptCode(String assistDeptCode) {
		this.assistDeptCode = assistDeptCode;
	}
	public String getAssistDeptName() {
		return assistDeptName;
	}
	public void setAssistDeptName(String assistDeptName) {
		this.assistDeptName = assistDeptName;
	}
	public String getDealSuggestion() {
		return dealSuggestion;
	}
	public void setDealSuggestion(String dealSuggestion) {
		this.dealSuggestion = dealSuggestion;
	}
	@Column(name = "JOINT_FLAG", length = 1)
	public String getJointFlag() {
		return this.jointFlag;
	}

	public void setJointFlag(String jointFlag) {
		this.jointFlag = jointFlag;
	}

	@Column(name = "DEPT_CODE", length = 50)
	public String getDeptCode() {
		return this.deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name = "DEPT_NAME", length = 100)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "USER_CODE", length = 50)
	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "USER_NAME", length = 100)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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


	@Column(name = "TEMP_FLAG", length = 1)
	public String getTempFlag() {
		return this.tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

}