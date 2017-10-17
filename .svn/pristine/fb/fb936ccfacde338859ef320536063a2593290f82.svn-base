package com.sinosoft.xf.petition.deal.domain;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoft.xf.petition.end.domain.PetitionEndInfo;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 办理信息表
 * @date 2011-11-30
 * @author ljx
 */
@Entity
@Table(name = "PETITION_DEAL_INFO")
public class PetitionDealInfo extends AudiEntity{
	
	private static final long serialVersionUID = 1L;

	private String petitionNo;
	private PetitionBasicInfo petitionBasicInfo;
	private String regionCode;
	private String regionName;
	private String dealNo;
	private int dealNum=1;
	private String procNum;
	private String validFlag="0";//1 已办理过 0、当前办理
	private String jointFlag;//联办1、联办 2、自办
	private String deptCode;
	private String deptName;
	private String userCode;
	private String userName;
	private String dealTypeCode;
	private String dealTypeName;
	private String assistDeptCode; //协办机构
	private String assistDeptName;	//协办机构
	private String dealSuggestion;
	private PetitionEndInfo petitionEndInfo;
	private PetitionTransDealInfo transDealInfo;
	private PetitionAssignInfo assignInfo;
	private PetitionSurveyDealInfo surveyDealInfo;
	//by hjh.承办单位，一般转办机构为“其它”时填写此项。暂时存放在trans表，只用作前台显示，不在Deal持久化。
	private String otherDealUnit; 

	/**
	 * @return the petitionEndInfo
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionDealInfo")
	public PetitionEndInfo getPetitionEndInfo() {
		return petitionEndInfo;
	}
	/**
	 * @param petitionEndInfo the petitionEndInfo to set
	 */
	public void setPetitionEndInfo(PetitionEndInfo petitionEndInfo) {
		this.petitionEndInfo = petitionEndInfo;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionDealInfo")
	public PetitionSurveyDealInfo getSurveyDealInfo() {
		return surveyDealInfo;
	}
	public void setSurveyDealInfo(PetitionSurveyDealInfo surveyDealInfo) {
		this.surveyDealInfo = surveyDealInfo;
	}
	/**
	 * @return the assignInfoInfo
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionDealInfo")
	public PetitionAssignInfo getAssignInfo() {
		return assignInfo;
	}
	/**
	 * @param assignInfoInfo the assignInfoInfo to set
	 */
	public void setAssignInfo(PetitionAssignInfo assignInfo) {
		this.assignInfo = assignInfo;
	}
	/**
	 * @return the PetitionTransDealInfo
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionDealInfo")
	public PetitionTransDealInfo getTransDealInfo() {
		return transDealInfo;
	}
	/**
	 * @param PetitionTransDealInfo the PetitionTransDealInfo to set
	 */
	public void setTransDealInfo(PetitionTransDealInfo transDealInfo) {
		this.transDealInfo = transDealInfo;
	}


	/**
	 * @return the petitionBasicInfo
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "PETITION_BASIC_INFO_OID")
	public PetitionBasicInfo getPetitionBasicInfo() {
		return petitionBasicInfo;
	}
	
	/**
	 * @param petitionBasicInfo the petitionBasicInfo to set
	 */
	public void setPetitionBasicInfo(PetitionBasicInfo petitionBasicInfo) {
		this.petitionBasicInfo = petitionBasicInfo;
	}
	
	@Column(name = "PETITION_NO", length = 26)
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

	@Column(name = "ASSIST_DEPT_CODE", length = 50)
	public String getAssistDeptCode() {
		return this.assistDeptCode;
	}

	public void setAssistDeptCode(String assistDeptCode) {
		this.assistDeptCode = assistDeptCode;
	}

	@Column(name = "ASSIST_DEPT_NAME", length = 100)
	public String getAssistDeptName() {
		return this.assistDeptName;
	}

	public void setAssistDeptName(String assistDeptName) {
		this.assistDeptName = assistDeptName;
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
	@Transient
	public String getOtherDealUnit() {
		return otherDealUnit;
	}
	public void setOtherDealUnit(String otherDealUnit) {
		this.otherDealUnit = otherDealUnit;
	}
}