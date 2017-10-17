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
 * 反映问题信息缓存类信息
 * @date 2011-11-24
 * @author sunzhe
 */
@Entity
@Table(name = "PETITION_ISSUE_INFO_TEMP")
public class PetitionIssueInfoTemp extends AudiEntity {
	
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
		private PetitionBasicInfoTemp petitionBasicInfoTemp;
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
		private String partyMeasureName;
		private String executiveMeasureName;
		private String partyMeasureCode;
		private String executiveMeasureCode;
		private String tempFlag="0";
		private String remark;
		private String issueContentNumFlag;					//CHAR(1) 问题描述信息是否存放到大于2000(加密8000)存1，否则0								
		private Set<IssueContentInfoTemp> issueContentInfoTempSet 	//问题描述信息
			= new HashSet<IssueContentInfoTemp>();
		private Set<KeyWordInfoTemp> keyWordInfoSet = new HashSet<KeyWordInfoTemp>();
		
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionIssueInfoTemp")
		public Set<KeyWordInfoTemp> getKeyWordInfoSet() {
			return keyWordInfoSet;
		}
		public void setKeyWordInfoSet(Set<KeyWordInfoTemp> keyWordInfoSet) {
			this.keyWordInfoSet = keyWordInfoSet;
		}
		/**
		 * @return the issueContentInfoTempSet
		 */
		@OneToMany(cascade = CascadeType.ALL, fetch= FetchType.LAZY, mappedBy = "petitionIssueInfoTemp")
		public Set<IssueContentInfoTemp> getIssueContentInfoTempSet(){
			return issueContentInfoTempSet;
		}
		/**
		 * @param issueContentInfoTempSet the issueContentInfoTempSet to set
		 */
		public void setIssueContentInfoTempSet(Set<IssueContentInfoTemp> issueContentInfoTempSet){
			this.issueContentInfoTempSet = issueContentInfoTempSet;
		}
		/**
		 * @return the petitionBasicInfo
		 */
		@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
		@JoinColumn(name = "petitionBasicInfoTempOid")
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

		public String getKeyWordContent() {
			return keyWordContent;
		}
		public void setKeyWordContent(String keyWordContent) {
			this.keyWordContent = keyWordContent;
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


		@Column(name = "ISSUE_REGION_CODE", length = 12)
		public String getIssueRegionCode() {
			return this.issueRegionCode;
		}

		public void setIssueRegionCode(String issueRegionCode) {
			this.issueRegionCode = issueRegionCode;
		}

		@Column(name = "ISSUE_REGION_NAME", length = 100)
		public String getIssueRegionName() {
			return this.issueRegionName;
		}

		public void setIssueRegionName(String issueRegionName) {
			this.issueRegionName = issueRegionName;
		}

		public String getIssueTypeCode() {
			return this.issueTypeCode;
		}

		public void setIssueTypeCode(String issueTypeCode) {
			this.issueTypeCode = issueTypeCode;
		}

		@Column(name = "ISSUE_TYPE_NAME", length = 100)
		public String getIssueTypeName() {
			return this.issueTypeName;
		}

		public void setIssueTypeName(String issueTypeName) {
			this.issueTypeName = issueTypeName;
		}
		public int getIssueNum() {
			return issueNum;
		}
		public void setIssueNum(int issueNum) {
			this.issueNum = issueNum;
		}
		public double getInvolveAmount() {
			return involveAmount;
		}
		public void setInvolveAmount(double involveAmount) {
			this.involveAmount = involveAmount;
		}
		public int getInvolvePeopleNum() {
			return involvePeopleNum;
		}
		public void setInvolvePeopleNum(int involvePeopleNum) {
			this.involvePeopleNum = involvePeopleNum;
		}
		
		@Column(name = "ORIGIN_DEAL_REGION", length = 50)
		public String getOriginDealRegion() {
			return this.originDealRegion;
		}

		public void setOriginDealRegion(String originDealRegion) {
			this.originDealRegion = originDealRegion;
		}

		@Column(name = "ORIGIN_SOLUTION", length = 3000)
		public String getOriginSolution() {
			return this.originSolution;
		}

		public void setOriginSolution(String originSolution) {
			this.originSolution = originSolution;
		}

		@Column(name = "PARTY_MEASURE_NAME", length = 50)
		public String getPartyMeasureName() {
			return this.partyMeasureName;
		}

		public void setPartyMeasureName(String partyMeasureName) {
			this.partyMeasureName = partyMeasureName;
		}

		@Column(name = "EXECUTIVE_MEASURE_NAME", length = 50)
		public String getExecutiveMeasureName() {
			return this.executiveMeasureName;
		}

		public void setExecutiveMeasureName(String executiveMeasureName) {
			this.executiveMeasureName = executiveMeasureName;
		}

		@Column(name = "TEMP_FLAG", length = 1)
		public String getTempFlag() {
			return this.tempFlag;
		}

		public void setTempFlag(String tempFlag) {
			this.tempFlag = tempFlag;
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