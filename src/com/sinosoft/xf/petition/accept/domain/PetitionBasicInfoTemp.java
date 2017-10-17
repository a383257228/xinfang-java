package com.sinosoft.xf.petition.accept.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.xf.petition.deal.domain.PetitionDealInfoTemp;
import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;
/**
 * 信访基本信息表缓存类信息
 * @date 2011-11-24
 * @author sunzhe
 */
@Entity
@Table(name = "PETITION_BASIC_INFO_TEMP")
public class PetitionBasicInfoTemp extends AudiEntity{
	
		private static final long serialVersionUID = 1L;
		private String petitionTitle;//信访件标题
		
	    private String encrypetitionTitle;
	    @Transient
		public String getPetitionTitle() {
	    	this.petitionTitle = Encrypt.decrypt(encrypetitionTitle);
	    	return this.petitionTitle;
		}
		public void setPetitionTitle(String petitionTitle) {
			this.petitionTitle = petitionTitle;
			this.encrypetitionTitle = Encrypt.encrypt(petitionTitle);
		}
		@Column(name = "petition_Title")
		public String getEncrypetitionTitle() {
			return encrypetitionTitle;
		}
		public void setEncrypetitionTitle(String petitionTitle) {
			this.encrypetitionTitle = petitionTitle;
		}
		@Transient
		public String getFirstAccuser() {
			firstAccuser = Encrypt.decrypt(encryfirstAccuser);
			return firstAccuser;
		}
		public void setFirstAccuser(String firstAccuser) {
			this.firstAccuser = firstAccuser;
			this.encryfirstAccuser = Encrypt.encrypt(firstAccuser);
		}
		@Column(name = "FIRST_ACCUSER")
		public String getEncryfirstAccuser() {
			return encryfirstAccuser;
		}
		public void setEncryfirstAccuser(String firstAccuser) {
			this.encryfirstAccuser = firstAccuser;
		}
		@Transient
		public String getFirstAccused() {
			firstAccused = Encrypt.decrypt(encryfirstAccused);
			return firstAccused;
		}
		public void setFirstAccused(String firstAccused) {
			this.firstAccused = firstAccused;
			this.encryfirstAccused = Encrypt.encrypt(firstAccused);
		}
		@Column(name = "FIRST_ACCUSED")
		public String getEncryfirstAccused() {
			return encryfirstAccused;
		}
		public void setEncryfirstAccused(String firstAccused) {
			this.encryfirstAccused = firstAccused;
		}
		private String petitionNo;
		private String regionCode;
		private String regionName;
		private String petitionSeqNo;
		private String petitionPrtNo;
		private String petitionAbbrNo;
		private Timestamp petitionDate;
		private String petitionTypeCode;//1、来信 2、来访 3、电话举报 4、网络举报 5、其他
		private String petitionTypeName;
		private String petitionTypeCodeTemp;//1、来信 2、来访 3、电话举报 4、网络举报 5、其他
		private String petitionSourceCode;
		private String petitionSourceName;
		private String petitionClassCode;
		private String petitionClassName;
		private String petitionUrgeCode;
		private String petitionUrgeName;
		private String petitionSecrecyCode;
		private String petitionSecrecyName;
		private String overAccuseFlag;
		private String overAccuseTypeCode;
		private String overAccuseTypeName;
		private String groupFlag;
		private int    personNum;
		private String importantFlag;
		private Timestamp requireEndDate;
		private String issueRegionCode;
		private String issueRegionName;
		private String pretreatmentCode;
		private String pretreatmentName;
		private String pretreatment;
		private String dealTypeCode;
		private String dealTypeName;
		private String endTypeCode;
		private String endTypeName;
		private String endFlag;
		private String receiptor;					//用于来访、电话受理信件中的接访、接听人				VARCHAR(50)
		private String receptionCond;				//用于来访、电话受理信件中的接访、接听情况				VARCHAR(3000)
		private String receptionNotion;				//用于来访、电话受理信件中的接访、接听人意见			VARCHAR(3000)
		private String exceptionCond;
		private String exceptionHandling;
		private String archiveFlag;
		private String basicFlag="1";       //月基数统计用 0为无效 1为有效
		private String remark;
		private String currDeptCode;
		private String currDeptName;
		private String returnContent;
//		private Integer repeatNum = 0;
//		private String repeatFlag = "0";
		private String objectClassName;
		private String objectClassCode;
		private String subIssueRegionCode;
		private String subIssueRegionName;
		private String exceptionFlag;
		private String transFlag="0";
		private String currPetitionNo;				//用于给用户展示的信访编号	                CHAR(26)
		private String realNameReport;				//用于记录是否为实名举报信件 0： 否 	1：是	CHAR(1)
		private String otherAccusers;				//用于存放其他反映人姓名				VARCHAR(200)
		private String otherAccuseds;				//用于存放其他被反映人姓名			VARCHAR(200)
		private String firstAccuser;				//用于存放第一反映人					VARCHAR(120)
		private String encryfirstAccuser;			//用于存放第一反映人(加密)			VARCHAR(120)
		private String firstAccused;				//用于存放第一被反映人				VARCHAR(120)
		private String encryfirstAccused;			//用于存放第一被反映人(加密)			VARCHAR(120)
		private String returnFlag;
		private String tempFlag;					//0：未签收 1：已签收  2：回退
		private String isSwgat="0";        //2016-01-05liub是否涉外及港澳台
		
		private Set<PetitionAccusedInfoTemp> accusedInfoTempSet = new HashSet<PetitionAccusedInfoTemp>();
		private Set<PetitionAccuserInfoTemp> accuserInfoTempSet = new HashSet<PetitionAccuserInfoTemp>();
		private Set<PetitionIssueInfoTemp> issueInfoTempSet = new HashSet<PetitionIssueInfoTemp>();
		private Set<IssueTypeInfoTemp> issueTypeInfoTempSet= new HashSet<IssueTypeInfoTemp>();
		private Set<PetitionMultiMediaInfoTemp> multiMediaInfoTempSet = new HashSet<PetitionMultiMediaInfoTemp>();
		private Set<PetitionDealInfoTemp> dealInfoTempSet = new HashSet<PetitionDealInfoTemp>();
		private Set<NetContentInfoTemp> netContentInfoTempSet = new HashSet<NetContentInfoTemp>();
		
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfoTemp")
		public Set<NetContentInfoTemp> getNetContentInfoTempSet() {
			return netContentInfoTempSet;
		}
		public void setNetContentInfoTempSet(Set<NetContentInfoTemp> netContentInfoTempSet) {
			this.netContentInfoTempSet = netContentInfoTempSet;
		}
		/**
		 * @return the PetitionDealInfo
		 */
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfoTemp")
		public Set<PetitionDealInfoTemp> getDealInfoTempSet() {
			return dealInfoTempSet;
		}
		/**
		 * @param PetitionDealInfo the PetitionDealInfo to set
		 */
		public void setDealInfoTempSet(Set<PetitionDealInfoTemp> dealInfoTempSet) {
			this.dealInfoTempSet = dealInfoTempSet;
		}
		
		/**
		 * @return the accusedInfoSet
		 */
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfoTemp")
		public Set<PetitionMultiMediaInfoTemp> getMultiMediaInfoTempSet() {
			return multiMediaInfoTempSet;
		}
		
		/**
		 * @param accusedInfoSet the accusedInfoSet to set
		 */
		public void setMultiMediaInfoTempSet(Set<PetitionMultiMediaInfoTemp> multiMediaInfoTempSet) {
			this.multiMediaInfoTempSet = multiMediaInfoTempSet;
		}
		
		/**
		 * @return the accusedInfoSet
		 */
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfoTemp")
		public Set<PetitionAccusedInfoTemp> getAccusedInfoTempSet() {
			return accusedInfoTempSet;
		}
		
		/**
		 * @param accusedInfoSet the accusedInfoSet to set
		 */
		public void setAccusedInfoTempSet(Set<PetitionAccusedInfoTemp> accusedInfoTempSet) {
			this.accusedInfoTempSet = accusedInfoTempSet;
		}
		
		/**
		 * @return the accuserInfoSet
		 */
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfoTemp")
		public Set<PetitionAccuserInfoTemp> getAccuserInfoTempSet() {
			return accuserInfoTempSet;
		}
		
		/**
		 * @param accuserInfoSet the accuserInfoSet to set
		 */
		public void setAccuserInfoTempSet(Set<PetitionAccuserInfoTemp> accuserInfoTempSet) {
			this.accuserInfoTempSet = accuserInfoTempSet;
		}
		
		/**
		 * @return the issueInfoSet
		 */
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfoTemp")
		public Set<PetitionIssueInfoTemp> getIssueInfoTempSet() {
			return issueInfoTempSet;
		}
		
		/**
		 * @param issueInfoSet the issueInfoSet to set
		 */
		public void setIssueInfoTempSet(Set<PetitionIssueInfoTemp> issueInfoTempSet) {
			this.issueInfoTempSet = issueInfoTempSet;
		}
		/**
		 * @return the issueTypeInfoTempSet
		 */
		@OneToMany(cascade = CascadeType.ALL, fetch= FetchType.LAZY, mappedBy = "petitionBasicInfoTemp")
		public Set<IssueTypeInfoTemp> getIssueTypeInfoTempSet() {
			return issueTypeInfoTempSet;
		}
		/**
		 * @param issueTypeInfoTempSet the issueTypeInfoTempSet to set
		 */
		public void setIssueTypeInfoTempSet(Set<IssueTypeInfoTemp> issueTypeInfoTempSet) {
			this.issueTypeInfoTempSet = issueTypeInfoTempSet;
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

		@Column(name = "PETITION_SEQ_NO", length = 26)
		public String getPetitionSeqNo() {
			return this.petitionSeqNo;
		}

		public void setPetitionSeqNo(String petitionSeqNo) {
			this.petitionSeqNo = petitionSeqNo;
		}

		@Column(name = "PETITION_PRT_NO", length = 26)
		public String getPetitionPrtNo() {
			return this.petitionPrtNo;
		}

		public void setPetitionPrtNo(String petitionPrtNo) {
			this.petitionPrtNo = petitionPrtNo;
		}

		@Column(name = "PETITION_ABBR_NO", length = 20)
		public String getPetitionAbbrNo() {
			return this.petitionAbbrNo;
		}

		public void setPetitionAbbrNo(String petitionAbbrNo) {
			this.petitionAbbrNo = petitionAbbrNo;
		}

		public Timestamp getPetitionDate() {
			return this.petitionDate;
		}

		public void setPetitionDate(Timestamp petitionDate) {
			this.petitionDate = petitionDate;
		}

		@Column(name = "PETITION_TYPE_CODE", length = 50)
		public String getPetitionTypeCode() {
			return this.petitionTypeCode;
		}

		public void setPetitionTypeCode(String petitionTypeCode) {
			this.petitionTypeCode = petitionTypeCode;
		}

		@Column(name = "PETITION_TYPE_NAME", length = 100)
		public String getPetitionTypeName() {
			return this.petitionTypeName;
		}

		public void setPetitionTypeName(String petitionTypeName) {
			this.petitionTypeName = petitionTypeName;
		}

		@Column(name = "PETITION_SOURCE_CODE", length = 50)
		public String getPetitionSourceCode() {
			return this.petitionSourceCode;
		}

		public void setPetitionSourceCode(String petitionSourceCode) {
			this.petitionSourceCode = petitionSourceCode;
		}

		@Column(name = "PETITION_SOURCE_NAME", length = 100)
		public String getPetitionSourceName() {
			return this.petitionSourceName;
		}

		public void setPetitionSourceName(String petitionSourceName) {
			this.petitionSourceName = petitionSourceName;
		}

		@Column(name = "PETITION_CLASS_CODE", length = 50)
		public String getPetitionClassCode() {
			return this.petitionClassCode;
		}

		public void setPetitionClassCode(String petitionClassCode) {
			this.petitionClassCode = petitionClassCode;
		}

		@Column(name = "PETITION_CLASS_NAME", length = 100)
		public String getPetitionClassName() {
			return this.petitionClassName;
		}

		public void setPetitionClassName(String petitionClassName) {
			this.petitionClassName = petitionClassName;
		}

		@Column(name = "PETITION_URGE_CODE", length = 50)
		public String getPetitionUrgeCode() {
			return this.petitionUrgeCode;
		}

		public void setPetitionUrgeCode(String petitionUrgeCode) {
			this.petitionUrgeCode = petitionUrgeCode;
		}

		@Column(name = "PETITION_URGE_NAME", length = 100)
		public String getPetitionUrgeName() {
			return this.petitionUrgeName;
		}

		public void setPetitionUrgeName(String petitionUrgeName) {
			this.petitionUrgeName = petitionUrgeName;
		}

		@Column(name = "PETITION_SECRECY_CODE", length = 50)
		public String getPetitionSecrecyCode() {
			return this.petitionSecrecyCode;
		}

		public void setPetitionSecrecyCode(String petitionSecrecyCode) {
			this.petitionSecrecyCode = petitionSecrecyCode;
		}

		@Column(name = "PETITION_SECRECY_NAME", length = 100)
		public String getPetitionSecrecyName() {
			return this.petitionSecrecyName;
		}

		public void setPetitionSecrecyName(String petitionSecrecyName) {
			this.petitionSecrecyName = petitionSecrecyName;
		}

		@Column(name = "OVER_ACCUSE_FLAG", length = 1)
		public String getOverAccuseFlag() {
			return this.overAccuseFlag;
		}

		public void setOverAccuseFlag(String overAccuseFlag) {
			this.overAccuseFlag = overAccuseFlag;
		}

		@Column(name = "OVER_ACCUSE_TYPE_CODE", length = 50)
		public String getOverAccuseTypeCode() {
			return this.overAccuseTypeCode;
		}

		public void setOverAccuseTypeCode(String overAccuseTypeCode) {
			this.overAccuseTypeCode = overAccuseTypeCode;
		}

		@Column(name = "OVER_ACCUSE_TYPE_NAME", length = 100)
		public String getOverAccuseTypeName() {
			return this.overAccuseTypeName;
		}

		public void setOverAccuseTypeName(String overAccuseTypeName) {
			this.overAccuseTypeName = overAccuseTypeName;
		}

		@Column(name = "GROUP_FLAG", length = 1)
		public String getGroupFlag() {
			return this.groupFlag;
		}

		public void setGroupFlag(String groupFlag) {
			this.groupFlag = groupFlag;
		}

		@Column(name = "PERSON_NUM")
		public Integer getPersonNum() {
			return this.personNum;
		}

		public void setPersonNum(Integer personNum) {
			this.personNum = personNum;
		}

		@Column(name = "IMPORTANT_FLAG", length = 1)
		public String getImportantFlag() {
			return this.importantFlag;
		}

		public void setImportantFlag(String importantFlag) {
			this.importantFlag = importantFlag;
		}

		@Column(name = "ISSUE_REGION_CODE", length = 12)
		public String getIssueRegionCode() {
			return this.issueRegionCode;
		}

		public void setIssueRegionCode(String issueRegionCode) {
			this.issueRegionCode = issueRegionCode;
		}

		public Timestamp getRequireEndDate() {
			return this.requireEndDate;
		}

		public void setRequireEndDate(Timestamp requireEndDate) {
			this.requireEndDate = requireEndDate;
		}

		@Column(name = "ISSUE_REGION_NAME", length = 100)
		public String getIssueRegionName() {
			return this.issueRegionName;
		}

		public void setIssueRegionName(String issueRegionName) {
			this.issueRegionName = issueRegionName;
		}

		@Column(name = "PRETREATMENT_CODE", length = 50)
		public String getPretreatmentCode() {
			return this.pretreatmentCode;
		}

		public void setPretreatmentCode(String pretreatmentCode) {
			this.pretreatmentCode = pretreatmentCode;
		}

		@Column(name = "PRETREATMENT_NAME", length = 100)
		public String getPretreatmentName() {
			return this.pretreatmentName;
		}

		public void setPretreatmentName(String pretreatmentName) {
			this.pretreatmentName = pretreatmentName;
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

		@Column(name = "END_TYPE_CODE", length = 50)
		public String getEndTypeCode() {
			return this.endTypeCode;
		}

		public void setEndTypeCode(String endTypeCode) {
			this.endTypeCode = endTypeCode;
		}

		@Column(name = "END_TYPE_NAME", length = 100)
		public String getEndTypeName() {
			return this.endTypeName;
		}

		public void setEndTypeName(String endTypeName) {
			this.endTypeName = endTypeName;
		}

		@Column(name = "END_FLAG", length = 1)
		public String getEndFlag() {
			return this.endFlag;
		}

		public void setEndFlag(String endFlag) {
			this.endFlag = endFlag;
		}

		@Column(name = "ARCHIVE_FLAG", length = 1)
		public String getArchiveFlag() {
			return this.archiveFlag;
		}

		public void setArchiveFlag(String archiveFlag) {
			this.archiveFlag = archiveFlag;
		}

//		@Column(name = "REPEAT_FLAG", length = 1)
//		public String getRepeatFlag() {
//			return this.repeatFlag;
//		}
//
//		public void setRepeatFlag(String repeatFlag) {
//			this.repeatFlag = repeatFlag;
//		}
//
//		@Column(name = "REPEAT_NUM")
//		public Integer getRepeatNum() {
//			return this.repeatNum;
//		}
//
//		public void setRepeatNum(Integer repeatNum) {
//			this.repeatNum = repeatNum;
//		}

		@Column(name = "TEMP_FLAG", length = 1)
		public String getTempFlag() {
			return this.tempFlag;
		}

		public void setTempFlag(String tempFlag) {
			this.tempFlag = tempFlag;
		}

		@Column(name = "REMARK", length = 2000)
		public String getRemark() {
			return this.remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getTransFlag() {
			return transFlag;
		}
		public void setTransFlag(String transFlag) {
			this.transFlag = transFlag;
		}
		/**
		 * @return the currPetitionNo
		 */
		public String getCurrPetitionNo() {
			return currPetitionNo;
		}
		/**
		 * @param currPetitionNo the currPetitionNo to set
		 */
		public void setCurrPetitionNo(String currPetitionNo) {
			this.currPetitionNo = currPetitionNo;
		}
		/**
		 * @return the realNameReport
		 */
		public String getRealNameReport() {
			return realNameReport;
		}
		/**
		 * @param realNameReport the realNameReport to set
		 */
		public void setRealNameReport(String realNameReport) {
			this.realNameReport = realNameReport;
		}
		/**
		 * @return the exceptionFlag
		 */
		public String getExceptionFlag() {
			return exceptionFlag;
		}
		/**
		 * @param exceptionFlag the exceptionFlag to set 
		 */
		public void setExceptionFlag(String exceptionFlag) {
			this.exceptionFlag = exceptionFlag;
		}
		public String getPetitionTypeCodeTemp() {
			return petitionTypeCodeTemp;
		}
		public void setPetitionTypeCodeTemp(String petitionTypeCodeTemp) {
			this.petitionTypeCodeTemp = petitionTypeCodeTemp;
		}
		public String getPretreatment() {
			return pretreatment;
		}
		public void setPretreatment(String pretreatment) {
			this.pretreatment = pretreatment;
		}
		public String getExceptionCond() {
			return exceptionCond;
		}
		public void setExceptionCond(String exceptionCond) {
			this.exceptionCond = exceptionCond;
		}
		public String getExceptionHandling() {
			return exceptionHandling;
		}
		public void setExceptionHandling(String exceptionHandling) {
			this.exceptionHandling = exceptionHandling;
		}
		public String getCurrDeptCode() {
			return currDeptCode;
		}
		public void setCurrDeptCode(String currDeptCode) {
			this.currDeptCode = currDeptCode;
		}
		public String getCurrDeptName() {
			return currDeptName;
		}
		public void setCurrDeptName(String currDeptName) {
			this.currDeptName = currDeptName;
		}
		public String getReturnContent() {
			return returnContent;
		}
		public void setReturnContent(String returnContent) {
			this.returnContent = returnContent;
		}
		public String getObjectClassName() {
			return objectClassName;
		}
		public void setObjectClassName(String objectClassName) {
			this.objectClassName = objectClassName;
		}
		public String getObjectClassCode() {
			return objectClassCode;
		}
		public void setObjectClassCode(String objectClassCode) {
			this.objectClassCode = objectClassCode;
		}
		public String getSubIssueRegionCode() {
			return subIssueRegionCode;
		}
		public void setSubIssueRegionCode(String subIssueRegionCode) {
			this.subIssueRegionCode = subIssueRegionCode;
		}
		public String getSubIssueRegionName() {
			return subIssueRegionName;
		}
		public void setSubIssueRegionName(String subIssueRegionName) {
			this.subIssueRegionName = subIssueRegionName;
		}
		public String getReturnFlag() {
			return returnFlag;
		}
		public void setReturnFlag(String returnFlag) {
			this.returnFlag = returnFlag;
		}
		public void setPersonNum(int personNum) {
			this.personNum = personNum;
		}
		/**
		 * @return the otherAccusers
		 */
		public String getOtherAccusers() {
			return otherAccusers;
		}
		/**
		 * @param otherAccusers the otherAccusers to set
		 */
		public void setOtherAccusers(String otherAccusers) {
			this.otherAccusers = otherAccusers;
		}
		public String getBasicFlag() {
			return basicFlag;
		}
		public void setBasicFlag(String basicFlag) {
			this.basicFlag = basicFlag;
		}
		/**
		 * @return the otherAccuseds
		 */
		public String getOtherAccuseds() {
			return otherAccuseds;
		}
		/**
		 * @param otherAccused the otherAccuseds to set
		 */
		public void setOtherAccuseds(String otherAccuseds) {
			this.otherAccuseds = otherAccuseds;
		}
		
		public String getReceiptor() {
			return receiptor;
		}
		public void setReceiptor(String receiptor) {
			this.receiptor = receiptor;
		}
		public String getReceptionCond() {
			return receptionCond;
		}
		public void setReceptionCond(String receptionCond) {
			this.receptionCond = receptionCond;
		}
		public String getReceptionNotion() {
			return receptionNotion;
		}
		public void setReceptionNotion(String receptionNotion) {
			this.receptionNotion = receptionNotion;
		}
		@Column(name = "IS_SWGAT", length = 1)
		public String getIsSwgat() {
			return isSwgat;
		}
		public void setIsSwgat(String isSwgat) {
			this.isSwgat = isSwgat;
		}
		
		
}