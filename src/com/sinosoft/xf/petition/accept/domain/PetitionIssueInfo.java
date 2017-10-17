package com.sinosoft.xf.petition.accept.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 反映问题信息表实体类信息
 * @date 2011-11-09
 * @author wangxx
 */
@Entity
@Table(name = "PETITION_ISSUE_INFO")
public class PetitionIssueInfo extends AudiEntity {
	
	private static final long serialVersionUID = 1L;
	private String issueContent;
	private String encryissueContent;
	@Transient
	public String getIssueContent() {
		this.issueContent = Encrypt.decrypt(encryissueContent);
		return issueContent;
	}
	public void setIssueContent(String issueContent) {
		this.issueContent = issueContent;
		this.encryissueContent = Encrypt.encrypt(issueContent);
	}
	@Column(name = "ISSUE_CONTENT")
	public String getEncryissueContent() {
		return encryissueContent;
	}
	public void setEncryissueContent(String issueContent) {
		this.encryissueContent = issueContent;
	}
	
	private String petitionNo;
	private PetitionBasicInfo petitionBasicInfo;
	private String regionCode;
	private String regionName;
	private int issueNum;
	private String issueRegionCode;
	private String issueRegionName;
	private String issueTypeCode;
	private String issueTypeName;
	private String keyWordContent;//关键字内容
	private double involveAmount;
	private int involvePeopleNum;
	private String originDealRegion;
	private String originSolution;
	private String partyMeasureCode;
	private String executiveMeasureCode;
	private String partyMeasureName;
	private String executiveMeasureName;
	private String remark;
	private String issueContentNumFlag = "0";					//CHAR(1) 问题描述信息是否存放到大于2000(加密8000)存1，否则0	
	private Set<IssueContentInfo> issueContentInfoSet 	//问题描述信息
		= new HashSet<IssueContentInfo>();
	private Set<KeyWordInfo> keyWordInfoSet = new HashSet<KeyWordInfo>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionIssueInfo")
	public Set<KeyWordInfo> getKeyWordInfoSet() {
		return keyWordInfoSet;
	}
	public void setKeyWordInfoSet(Set<KeyWordInfo> keyWordInfoSet) {
		this.keyWordInfoSet = keyWordInfoSet;
	}
	/**
	 * @return the issueContentInfoSet
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch= FetchType.LAZY, mappedBy = "petitionIssueInfo")
	public Set<IssueContentInfo> getIssueContentInfoSet(){
		return issueContentInfoSet;
	}
	/**
	 * @param issueContentInfoSet the issueContentInfoSet to set
	 */
	public void setIssueContentInfoSet(Set<IssueContentInfo> issueContentInfoSet){
		this.issueContentInfoSet = issueContentInfoSet;
	}
	/**
	 * @return the petitionBasicInfo
	 */
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
	 * @return the issueNum
	 */
	public int getIssueNum() {
		return issueNum;
	}
	/**
	 * @param issueNum the issueNum to set
	 */
	public void setIssueNum(int issueNum) {
		this.issueNum = issueNum;
	}
	/**
	 * @return the issueRegionCode
	 */
	public String getIssueRegionCode() {
		return issueRegionCode;
	}
	/**
	 * @param issueRegionCode the issueRegionCode to set
	 */
	public void setIssueRegionCode(String issueRegionCode) {
		this.issueRegionCode = issueRegionCode;
	}
	/**
	 * @return the issueRegionName
	 */
	public String getIssueRegionName() {
		return issueRegionName;
	}
	/**
	 * @param issueRegionName the issueRegionName to set
	 */
	public void setIssueRegionName(String issueRegionName) {
		this.issueRegionName = issueRegionName;
	}
	public String getIssueTypeCode() {
		return issueTypeCode;
	}
	public void setIssueTypeCode(String issueTypeCode) {
		this.issueTypeCode = issueTypeCode;
	}
	public String getIssueTypeName() {
		return issueTypeName;
	}
	public void setIssueTypeName(String issueTypeName) {
		this.issueTypeName = issueTypeName;
	}
	
	public String getKeyWordContent() {
		return keyWordContent;
	}
	public void setKeyWordContent(String keyWordContent) {
		this.keyWordContent = keyWordContent;
	}
	/**
	 * @return the involveAmount
	 */
	public double getInvolveAmount() {
		return involveAmount;
	}
	/**
	 * @param involveAmount the involveAmount to set
	 */
	public void setInvolveAmount(double involveAmount) {
		this.involveAmount = involveAmount;
	}
	/**
	 * @return the involvePeopleNum
	 */
	public int getInvolvePeopleNum() {
		return involvePeopleNum;
	}
	/**
	 * @param involvePeopleNum the involvePeopleNum to set
	 */
	public void setInvolvePeopleNum(int involvePeopleNum) {
		this.involvePeopleNum = involvePeopleNum;
	}
	/**
	 * @return the originDealRegion
	 */
	public String getOriginDealRegion() {
		return originDealRegion;
	}
	/**
	 * @param originDealRegion the originDealRegion to set
	 */
	public void setOriginDealRegion(String originDealRegion) {
		this.originDealRegion = originDealRegion;
	}
	/**
	 * @return the originSolution
	 */
	public String getOriginSolution() {
		return originSolution;
	}
	/**
	 * @param originSolution the originSolution to set
	 */
	public void setOriginSolution(String originSolution) {
		this.originSolution = originSolution;
	}
	/**
	 * @return the partyMeasureName
	 */
	public String getPartyMeasureName() {
		return partyMeasureName;
	}
	/**
	 * @param partyMeasureName the partyMeasureName to set
	 */
	public void setPartyMeasureName(String partyMeasureName) {
		this.partyMeasureName = partyMeasureName;
	}
	/**
	 * @return the executiveMeasureName
	 */
	public String getExecutiveMeasureName() {
		return executiveMeasureName;
	}
	/**
	 * @param executiveMeasureName the executiveMeasureName to set
	 */
	public void setExecutiveMeasureName(String executiveMeasureName) {
		this.executiveMeasureName = executiveMeasureName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the issueContentNumFlag
	 */
	public String getIssueContentNumFlag() {
		return issueContentNumFlag;
	}
	/**
	 * @param issueContentNumFlag the issueContentNumFlag to set
	 */
	public void setIssueContentNumFlag(String issueContentNumFlag) {
		this.issueContentNumFlag = issueContentNumFlag;
	}
	public String getPartyMeasureCode() {
		return partyMeasureCode;
	}
	public void setPartyMeasureCode(String partyMeasureCode) {
		this.partyMeasureCode = partyMeasureCode;
	}
	public String getExecutiveMeasureCode() {
		return executiveMeasureCode;
	}
	public void setExecutiveMeasureCode(String executiveMeasureCode) {
		this.executiveMeasureCode = executiveMeasureCode;
	}
}
