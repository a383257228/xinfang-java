package com.sinosoft.xf.petition.domainutil;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用于封装基本信息，第一反映人信息，第一被反映人信息，第一反映问题信息
 * @author Oba
 */
public class PetitionInfo implements  Serializable{
	private static final long serialVersionUID = 1L;
	private String basicId;
	private String accuserId;
	private String issueId;
	private String dealId;
	private String accusedId;
	private String petitionNo;
	private String regionCode;
	private String regionName;
	private String petitionSeqNo;
	private String petitionPrtNo;
	private String petitionAbbrNo;
	private Timestamp petitionDate;
	private String petitionTypeCode;//1、来信 2、来访 3、电话举报 4、网络举报 5、其他
	private String petitionTypeName;
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
	private String petitionTitle;
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
	private String archiveFlag;
	private String remark;
	private String returnContent;//添加回复内容字段
	private int issueNum;
	private String issueTypeCode;
	private String issueTypeName;
	private String keyWordContent;
	private String issueContent;
	private double involveAmount;
	private String issueContentNumFlag="0";//CHAR(1) 问题描述信息是否存放到大于2000(加密8000)存1，否则0
	private int involvePeopleNum;
	private String originDealRegion;
	private String originSolution;
	private String partyMeasureName;
	private String executiveMeasureName;
	private String accuserAnonymousFlag;//是否署名  1、是   2、否
	private int accuserNum;
	private String accuserTypeCode;
	private String accuserTypeName;
	private String accuserCardId;
	private String accuserName;
	private String accuserSexCode;
	private String accuserSexName;
	private Timestamp accuserBirthday;
	private String accuserNationCode;
	private String accuserNationName;
	private String accuserClassCode;
	private String accuserClassName;
	private String accuserPartyCode;
	private String accuserPartyName;
	private String accuserHomePlace;
	private String accuserWorkUnit;
	private String accuserPositionName;
	private String accuserOccupationCode;
	private String accuserOccupationName;
	private String accuserTelOne;
	private String accuserTelTwo;
	private String accuserTelThree;
	private String accuserAddr;
	private String accuserZip;
	private String accuserEmail;
	private String accuserRegionCode;
	private String accuserRegionName;
	private String accuserRegionCodeProv;
	private String accuserRegionCodeCity;
	private String accuserRegionCodeCounty;
	private String accuserRegionCodeTown;
	private String accuserRegionCodeStreet;
	private String accuserDevianceCode;
	private String accuserDevianceName;
	private String accuserCategoryCode;
	private String accuserCategoryName;
	private String everAccusePlace;
	private String everDealDept;
	private String accuserAppealFlag;//老访户 1是  2否
	private String accusedAppealFlag;//老访户 1是  2否
	private int accusedNum;
	private String accusedTypeCode;//1：单位  2：个人
	private String accusedTypeName;
	private String accusedDisciplineCode;
	private String accusedDisciplineName;
	private String accusedCardId;
	private String accusedName;
	private String accusedSexCode;//1、男 2、女
	private String accusedSexName;
	private Timestamp accusedBirthday;
	private String accusedNationCode;
	private String accusedNationName;
	private String accusedPartyCode;
	private String accusedPartyName;
	private String accusedNpcMemberCode;
	private String accusedNpcMemberName;
	private String accusedCppccMemberCode;
	private String accusedCppccMemberName;
	private String accusedPartyMemberCode;
	private String accusedPartyMemberName;
	private String accusedPositionName;
	private String accusedClassCode;
	private String accusedClassName;
	private String accusedHomePlace;
	private String accusedWorkUnit;
	private String accusedWorkTypeCode;
	private String accusedWorkTypeName;
	private String accusedEnterpriseTypeCode;
	private String accusedEnterpriseTypeName;
	private String accusedTelOne;
	private String accusedTelTwo;
	private String accusedTelThree;
	private String accusedAddr;
	private String accusedZip;
	private String accusedRegionCode;
	private String accusedRegionName;
	private String accusedRegionCodeProv;
	private String accusedRegionCodeCity;
	private String accusedRegionCodeCounty;
	private String accusedRegionCodeTown;
	private String accusedRegionCodeStreet;
	private String accusedDevianceFlag;//1、是  2、否  0、不祥
	private String accusedLocalCadreFlag;//1、是  2、否  0、不祥
	private String accusedLeaderDisciplineCode;
	private String accusedLeaderDisciplineName;
	private String accusedCategoryCode;
	private String accusedCategoryName;
	private String letterNo;
	private String letterContent;
	private String exceptionFlag;
	private String exceptionCond; //异常情况
	private String exceptionHandling;//异常情况处理
	private String currPetitionNo;				//用于给用户展示的信访编号	CHAR(26)
	private String realNameReport;				//是否署名					CHAR(1)
	private String repeatFlag;//1、是  2、否
	private String otherIssueTypeName;
	private String otherIssueTypeCode;
	private String defaultDealerName;
	private Timestamp signDate;
	private String instructor;					//用于机关领导交办--领导姓名			VARCHAR(50)
	private String instructText;				//用于机关领导交办--批示文号			VARCHAR(30)
	private Timestamp instructTime;				//用于机关领导交办--批示时间
	private String instruction;					//用于机关领导交办--批示内容			VARCHAR(1000)
	private Timestamp assignedDate;				//交办日期
	private Timestamp assignedRequireEndDate;	//交办截止日期
	private String dealSuggestion;//办理方案
	private String receiptor;//接访/接电人
	private String receptionCond;//接访/接电情况
	private String receptionNotion;//接访/接电意见
	private String postponeFlag = "0";//是否申请过延期  //0否，1同意申请延期，2正在申请中，3申请没同意
	private String creatorName;
	private String isCountFlag;		//by hjh. 是否属于统计范围 0、不统计，1、统计 。默认统计。
	private String isSecretaryFlag;		////是否属于书记专题会。0否1是
	private String currDeptName; // 承办部门
	private String currDeptCode;
	private String petitionSourceParentCode;
	private String toRegionName;
	private String isSwgat;
	//2016-05-25 byg 在全局修改中添加 “线索来源” 修改项
	private String handlingPretreatment;
	private String queryKeyNo;
	//2016-07-29 byg 在签收受理内添加关键字code
	private String keyWordCodes;
	private String NetContent;
	
	public String getNetContent() {
		return NetContent;
	}
	public void setNetContent(String netContent) {
		NetContent = netContent;
	}
	public String getKeyWordCodes() {
		return keyWordCodes;
	}
	public void setKeyWordCodes(String keyWordCodes) {
		this.keyWordCodes = keyWordCodes;
	}
	public String getIsCountFlag() {
		return isCountFlag;
	}
	public void setIsCountFlag(String isCountFlag) {
		this.isCountFlag = isCountFlag;
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
	public String getExceptionFlag() {
		return exceptionFlag;
	}
	public void setExceptionFlag(String exceptionFlag) {
		this.exceptionFlag = exceptionFlag;
	}
	
	public String getLetterContent() {
		return letterContent;
	}
	public void setLetterContent(String letterContent) {
		this.letterContent = letterContent;
	}
	public String getLetterNo() {
		return letterNo;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	/**
	 * @return the basicId
	 */
	public String getBasicId() {
		return basicId;
	}
	/**
	 * @param basicId the basicId to set
	 */
	public void setBasicId(String basicId) {
		this.basicId = basicId;
	}
	/**
	 * @return the accuserId
	 */
	public String getAccuserId() {
		return accuserId;
	}
	/**
	 * @param accuserId the accuserId to set
	 */
	public void setAccuserId(String accuserId) {
		this.accuserId = accuserId;
	}
	/**
	 * @return the issueId
	 */
	public String getIssueId() {
		return issueId;
	}
	/**
	 * @param issueId the issueId to set
	 */
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	/**
	 * @return the accusedId
	 */
	public String getAccusedId() {
		return accusedId;
	}
	/**
	 * @param accusedId the accusedId to set
	 */
	public void setAccusedId(String accusedId) {
		this.accusedId = accusedId;
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
	 * @return the petitionSeqNo
	 */
	public String getPetitionSeqNo() {
		return petitionSeqNo;
	}
	/**
	 * @param petitionSeqNo the petitionSeqNo to set
	 */
	public void setPetitionSeqNo(String petitionSeqNo) {
		this.petitionSeqNo = petitionSeqNo;
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
	 * @return the petitionAbbrNo
	 */
	public String getPetitionAbbrNo() {
		return petitionAbbrNo;
	}
	/**
	 * @param petitionAbbrNo the petitionAbbrNo to set
	 */
	public void setPetitionAbbrNo(String petitionAbbrNo) {
		this.petitionAbbrNo = petitionAbbrNo;
	}
	/**
	 * @return the petitionDate
	 */
	public Timestamp getPetitionDate() {
		return petitionDate;
	}
	/**
	 * @param petitionDate the petitionDate to set
	 */
	public void setPetitionDate(Timestamp petitionDate) {
		this.petitionDate = petitionDate;
	}
	/**
	 * @return the petitionTypeCode
	 */
	public String getPetitionTypeCode() {
		return petitionTypeCode;
	}
	/**
	 * @param petitionTypeCode the petitionTypeCode to set
	 */
	public void setPetitionTypeCode(String petitionTypeCode) {
		this.petitionTypeCode = petitionTypeCode;
	}
	/**
	 * @return the petitionTypeName
	 */
	public String getPetitionTypeName() {
		return petitionTypeName;
	}
	/**
	 * @param petitionTypeName the petitionTypeName to set
	 */
	public void setPetitionTypeName(String petitionTypeName) {
		this.petitionTypeName = petitionTypeName;
	}
	/**
	 * @return the petitionSourceCode
	 */
	public String getPetitionSourceCode() {
		return petitionSourceCode;
	}
	/**
	 * @param petitionSourceCode the petitionSourceCode to set
	 */
	public void setPetitionSourceCode(String petitionSourceCode) {
		this.petitionSourceCode = petitionSourceCode;
	}
	/**
	 * @return the petitionSourceName
	 */
	public String getPetitionSourceName() {
		return petitionSourceName;
	}
	/**
	 * @param petitionSourceName the petitionSourceName to set
	 */
	public void setPetitionSourceName(String petitionSourceName) {
		this.petitionSourceName = petitionSourceName;
	}
	/**
	 * @return the petitionClassCode
	 */
	public String getPetitionClassCode() {
		return petitionClassCode;
	}
	/**
	 * @param petitionClassCode the petitionClassCode to set
	 */
	public void setPetitionClassCode(String petitionClassCode) {
		this.petitionClassCode = petitionClassCode;
	}
	/**
	 * @return the petitionClassName
	 */
	public String getPetitionClassName() {
		return petitionClassName;
	}
	/**
	 * @param petitionClassName the petitionClassName to set
	 */
	public void setPetitionClassName(String petitionClassName) {
		this.petitionClassName = petitionClassName;
	}
	/**
	 * @return the petitionUrgeCode
	 */
	public String getPetitionUrgeCode() {
		return petitionUrgeCode;
	}
	/**
	 * @param petitionUrgeCode the petitionUrgeCode to set
	 */
	public void setPetitionUrgeCode(String petitionUrgeCode) {
		this.petitionUrgeCode = petitionUrgeCode;
	}
	/**
	 * @return the petitionUrgeName
	 */
	public String getPetitionUrgeName() {
		return petitionUrgeName;
	}
	/**
	 * @param petitionUrgeName the petitionUrgeName to set
	 */
	public void setPetitionUrgeName(String petitionUrgeName) {
		this.petitionUrgeName = petitionUrgeName;
	}
	/**
	 * @return the petitionSecrecyCode
	 */
	public String getPetitionSecrecyCode() {
		return petitionSecrecyCode;
	}
	/**
	 * @param petitionSecrecyCode the petitionSecrecyCode to set
	 */
	public void setPetitionSecrecyCode(String petitionSecrecyCode) {
		this.petitionSecrecyCode = petitionSecrecyCode;
	}
	/**
	 * @return the petitionSecrecyName
	 */
	public String getPetitionSecrecyName() {
		return petitionSecrecyName;
	}
	/**
	 * @param petitionSecrecyName the petitionSecrecyName to set
	 */
	public void setPetitionSecrecyName(String petitionSecrecyName) {
		this.petitionSecrecyName = petitionSecrecyName;
	}
	/**
	 * @return the overAccuseFlag
	 */
	public String getOverAccuseFlag() {
		return overAccuseFlag;
	}
	/**
	 * @param overAccuseFlag the overAccuseFlag to set
	 */
	public void setOverAccuseFlag(String overAccuseFlag) {
		this.overAccuseFlag = overAccuseFlag;
	}
	/**
	 * @return the overAccuseTypeCode
	 */
	public String getOverAccuseTypeCode() {
		return overAccuseTypeCode;
	}
	/**
	 * @param overAccuseTypeCode the overAccuseTypeCode to set
	 */
	public void setOverAccuseTypeCode(String overAccuseTypeCode) {
		this.overAccuseTypeCode = overAccuseTypeCode;
	}
	/**
	 * @return the overAccuseTypeName
	 */
	public String getOverAccuseTypeName() {
		return overAccuseTypeName;
	}
	/**
	 * @param overAccuseTypeName the overAccuseTypeName to set
	 */
	public void setOverAccuseTypeName(String overAccuseTypeName) {
		this.overAccuseTypeName = overAccuseTypeName;
	}
	/**
	 * @return the groupFlag
	 */
	public String getGroupFlag() {
		return groupFlag;
	}
	/**
	 * @param groupFlag the groupFlag to set
	 */
	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}
	/**
	 * @return the personNum
	 */
	public int getPersonNum() {
		return personNum;
	}
	/**
	 * @param personNum the personNum to set
	 */
	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}
	/**
	 * @return the importantFlag
	 */
	public String getImportantFlag() {
		return importantFlag;
	}
	/**
	 * @param importantFlag the importantFlag to set
	 */
	public void setImportantFlag(String importantFlag) {
		this.importantFlag = importantFlag;
	}
	/**
	 * @return the petitionTitle
	 */
	public String getPetitionTitle() {
		return petitionTitle;
	}
	/**
	 * @param petitionTitle the petitionTitle to set
	 */
	public void setPetitionTitle(String petitionTitle) {
		this.petitionTitle = petitionTitle;
	}
	/**
	 * @return the requireEndDate
	 */
	public Timestamp getRequireEndDate() {
		return requireEndDate;
	}
	/**
	 * @param requireEndDate the requireEndDate to set
	 */
	public void setRequireEndDate(Timestamp requireEndDate) {
		this.requireEndDate = requireEndDate;
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
	/**
	 * @return the pretreatmentCode
	 */
	public String getPretreatmentCode() {
		return pretreatmentCode;
	}
	/**
	 * @param pretreatmentCode the pretreatmentCode to set
	 */
	public void setPretreatmentCode(String pretreatmentCode) {
		this.pretreatmentCode = pretreatmentCode;
	}
	/**
	 * @return the pretreatmentName
	 */
	public String getPretreatmentName() {
		return pretreatmentName;
	}
	/**
	 * @param pretreatmentName the pretreatmentName to set
	 */
	public void setPretreatmentName(String pretreatmentName) {
		this.pretreatmentName = pretreatmentName;
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
	 * @return the endTypeCode
	 */
	public String getEndTypeCode() {
		return endTypeCode;
	}
	/**
	 * @param endTypeCode the endTypeCode to set
	 */
	public void setEndTypeCode(String endTypeCode) {
		this.endTypeCode = endTypeCode;
	}
	/**
	 * @return the endTypeName
	 */
	public String getEndTypeName() {
		return endTypeName;
	}
	/**
	 * @param endTypeName the endTypeName to set
	 */
	public void setEndTypeName(String endTypeName) {
		this.endTypeName = endTypeName;
	}
	/**
	 * @return the endFlag
	 */
	public String getEndFlag() {
		return endFlag;
	}
	/**
	 * @param endFlag the endFlag to set
	 */
	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}
	/**
	 * @return the archiveFlag
	 */
	public String getArchiveFlag() {
		return archiveFlag;
	}
	/**
	 * @param archiveFlag the archiveFlag to set
	 */
	public void setArchiveFlag(String archiveFlag) {
		this.archiveFlag = archiveFlag;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * @return the issueTypeCode
	 */
	public String getIssueTypeCode() {
		return issueTypeCode;
	}
	/**
	 * @param issueTypeCode the issueTypeCode to set
	 */
	public void setIssueTypeCode(String issueTypeCode) {
		this.issueTypeCode = issueTypeCode;
	}
	/**
	 * @return the issueTypeName
	 */
	public String getIssueTypeName() {
		return issueTypeName;
	}
	/**
	 * @param issueTypeName the issueTypeName to set
	 */
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
	 * @return the issueContent
	 */
	public String getIssueContent() {
		return issueContent;
	}
	/**
	 * @param issueContent the issueContent to set
	 */
	public void setIssueContent(String issueContent) {
		this.issueContent = issueContent;
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
	/**
	 * @return the accuserAnonymousFlag
	 */
	public String getAccuserAnonymousFlag() {
		return accuserAnonymousFlag;
	}
	/**
	 * @param accuserAnonymousFlag the accuserAnonymousFlag to set
	 */
	public void setAccuserAnonymousFlag(String accuserAnonymousFlag) {
		this.accuserAnonymousFlag = accuserAnonymousFlag;
	}
	/**
	 * @return the accuserNum
	 */
	public int getAccuserNum() {
		return accuserNum;
	}
	/**
	 * @param accuserNum the accuserNum to set
	 */
	public void setAccuserNum(int accuserNum) {
		this.accuserNum = accuserNum;
	}
	/**
	 * @return the accuserTypeCode
	 */
	public String getAccuserTypeCode() {
		return accuserTypeCode;
	}
	/**
	 * @param accuserTypeCode the accuserTypeCode to set
	 */
	public void setAccuserTypeCode(String accuserTypeCode) {
		this.accuserTypeCode = accuserTypeCode;
	}
	/**
	 * @return the accuserTypeName
	 */
	public String getAccuserTypeName() {
		return accuserTypeName;
	}
	/**
	 * @param accuserTypeName the accuserTypeName to set
	 */
	public void setAccuserTypeName(String accuserTypeName) {
		this.accuserTypeName = accuserTypeName;
	}
	/**
	 * @return the accuserCardId
	 */
	public String getAccuserCardId() {
		return accuserCardId;
	}
	/**
	 * @param accuserCardId the accuserCardId to set
	 */
	public void setAccuserCardId(String accuserCardId) {
		this.accuserCardId = accuserCardId;
	}
	/**
	 * @return the accuserName
	 */
	public String getAccuserName() {
		return accuserName;
	}
	/**
	 * @param accuserName the accuserName to set
	 */
	public void setAccuserName(String accuserName) {
		this.accuserName = accuserName;
	}
	/**
	 * @return the accuserSexCode
	 */
	public String getAccuserSexCode() {
		return accuserSexCode;
	}
	/**
	 * @param accuserSexCode the accuserSexCode to set
	 */
	public void setAccuserSexCode(String accuserSexCode) {
		this.accuserSexCode = accuserSexCode;
	}
	/**
	 * @return the accuserSexName
	 */
	public String getAccuserSexName() {
		return accuserSexName;
	}
	/**
	 * @param accuserSexName the accuserSexName to set
	 */
	public void setAccuserSexName(String accuserSexName) {
		this.accuserSexName = accuserSexName;
	}
	/**
	 * @return the accuserBirthday
	 */
	public Timestamp getAccuserBirthday() {
		return accuserBirthday;
	}
	/**
	 * @param accuserBirthday the accuserBirthday to set
	 */
	public void setAccuserBirthday(Timestamp accuserBirthday) {
		this.accuserBirthday = accuserBirthday;
	}
	/**
	 * @return the accuserNationCode
	 */
	public String getAccuserNationCode() {
		return accuserNationCode;
	}
	/**
	 * @param accuserNationCode the accuserNationCode to set
	 */
	public void setAccuserNationCode(String accuserNationCode) {
		this.accuserNationCode = accuserNationCode;
	}
	/**
	 * @return the accuserNationName
	 */
	public String getAccuserNationName() {
		return accuserNationName;
	}
	/**
	 * @param accuserNationName the accuserNationName to set
	 */
	public void setAccuserNationName(String accuserNationName) {
		this.accuserNationName = accuserNationName;
	}
	/**
	 * @return the accuserClassCode
	 */
	public String getAccuserClassCode() {
		return accuserClassCode;
	}
	/**
	 * @param accuserClassCode the accuserClassCode to set
	 */
	public void setAccuserClassCode(String accuserClassCode) {
		this.accuserClassCode = accuserClassCode;
	}
	/**
	 * @return the accuserClassName
	 */
	public String getAccuserClassName() {
		return accuserClassName;
	}
	/**
	 * @param accuserClassName the accuserClassName to set
	 */
	public void setAccuserClassName(String accuserClassName) {
		this.accuserClassName = accuserClassName;
	}
	/**
	 * @return the accuserPartyCode
	 */
	public String getAccuserPartyCode() {
		return accuserPartyCode;
	}
	/**
	 * @param accuserPartyCode the accuserPartyCode to set
	 */
	public void setAccuserPartyCode(String accuserPartyCode) {
		this.accuserPartyCode = accuserPartyCode;
	}
	/**
	 * @return the accuserPartyName
	 */
	public String getAccuserPartyName() {
		return accuserPartyName;
	}
	/**
	 * @param accuserPartyName the accuserPartyName to set
	 */
	public void setAccuserPartyName(String accuserPartyName) {
		this.accuserPartyName = accuserPartyName;
	}
	/**
	 * @return the accuserPositionName
	 */
	public String getAccuserPositionName() {
		return accuserPositionName;
	}
	/**
	 * @param accuserPositionName the accuserPositionName to set
	 */
	public void setAccuserPositionName(String accuserPositionName) {
		this.accuserPositionName = accuserPositionName;
	}
	/**
	 * @return the accuserOccupationCode
	 */
	public String getAccuserOccupationCode() {
		return accuserOccupationCode;
	}
	/**
	 * @param accuserOccupationCode the accuserOccupationCode to set
	 */
	public void setAccuserOccupationCode(String accuserOccupationCode) {
		this.accuserOccupationCode = accuserOccupationCode;
	}
	/**
	 * @return the accuserOccupationName
	 */
	public String getAccuserOccupationName() {
		return accuserOccupationName;
	}
	/**
	 * @param accuserOccupationName the accuserOccupationName to set
	 */
	public void setAccuserOccupationName(String accuserOccupationName) {
		this.accuserOccupationName = accuserOccupationName;
	}
	/**
	 * @return the accuserTelOne
	 */
	public String getAccuserTelOne() {
		return accuserTelOne;
	}
	/**
	 * @param accuserTelOne the accuserTelOne to set
	 */
	public void setAccuserTelOne(String accuserTelOne) {
		this.accuserTelOne = accuserTelOne;
	}
	/**
	 * @return the accuserTelTwo
	 */
	public String getAccuserTelTwo() {
		return accuserTelTwo;
	}
	/**
	 * @param accuserTelTwo the accuserTelTwo to set
	 */
	public void setAccuserTelTwo(String accuserTelTwo) {
		this.accuserTelTwo = accuserTelTwo;
	}
	/**
	 * @return the accuserTelThree
	 */
	public String getAccuserTelThree() {
		return accuserTelThree;
	}
	/**
	 * @param accuserTelThree the accuserTelThree to set
	 */
	public void setAccuserTelThree(String accuserTelThree) {
		this.accuserTelThree = accuserTelThree;
	}
	/**
	 * @return the accuserAddr
	 */
	public String getAccuserAddr() {
		return accuserAddr;
	}
	/**
	 * @param accuserAddr the accuserAddr to set
	 */
	public void setAccuserAddr(String accuserAddr) {
		this.accuserAddr = accuserAddr;
	}
	/**
	 * @return the accuserZip
	 */
	public String getAccuserZip() {
		return accuserZip;
	}
	/**
	 * @param accuserZip the accuserZip to set
	 */
	public void setAccuserZip(String accuserZip) {
		this.accuserZip = accuserZip;
	}
	/**
	 * @return the accuserEmail
	 */
	public String getAccuserEmail() {
		return accuserEmail;
	}
	/**
	 * @param accuserEmail the accuserEmail to set
	 */
	public void setAccuserEmail(String accuserEmail) {
		this.accuserEmail = accuserEmail;
	}
	/**
	 * @return the accuserRegionCode
	 */
	public String getAccuserRegionCode() {
		return accuserRegionCode;
	}
	/**
	 * @param accuserRegionCode the accuserRegionCode to set
	 */
	public void setAccuserRegionCode(String accuserRegionCode) {
		this.accuserRegionCode = accuserRegionCode;
	}
	/**
	 * @return the accuserRegionName
	 */
	public String getAccuserRegionName() {
		return accuserRegionName;
	}
	/**
	 * @param accuserRegionName the accuserRegionName to set
	 */
	public void setAccuserRegionName(String accuserRegionName) {
		this.accuserRegionName = accuserRegionName;
	}
	/**
	 * @return the accuserRegionCodeProv
	 */
	public String getAccuserRegionCodeProv() {
		return accuserRegionCodeProv;
	}
	/**
	 * @param accuserRegionCodeProv the accuserRegionCodeProv to set
	 */
	public void setAccuserRegionCodeProv(String accuserRegionCodeProv) {
		this.accuserRegionCodeProv = accuserRegionCodeProv;
	}
	/**
	 * @return the accuserRegionCodeCity
	 */
	public String getAccuserRegionCodeCity() {
		return accuserRegionCodeCity;
	}
	/**
	 * @param accuserRegionCodeCity the accuserRegionCodeCity to set
	 */
	public void setAccuserRegionCodeCity(String accuserRegionCodeCity) {
		this.accuserRegionCodeCity = accuserRegionCodeCity;
	}
	/**
	 * @return the accuserRegionCodeCounty
	 */
	public String getAccuserRegionCodeCounty() {
		return accuserRegionCodeCounty;
	}
	/**
	 * @param accuserRegionCodeCounty the accuserRegionCodeCounty to set
	 */
	public void setAccuserRegionCodeCounty(String accuserRegionCodeCounty) {
		this.accuserRegionCodeCounty = accuserRegionCodeCounty;
	}
	/**
	 * @return the accuserRegionCodeTown
	 */
	public String getAccuserRegionCodeTown() {
		return accuserRegionCodeTown;
	}
	/**
	 * @param accuserRegionCodeTown the accuserRegionCodeTown to set
	 */
	public void setAccuserRegionCodeTown(String accuserRegionCodeTown) {
		this.accuserRegionCodeTown = accuserRegionCodeTown;
	}
	/**
	 * @return the accuserRegionCodeStreet
	 */
	public String getAccuserRegionCodeStreet() {
		return accuserRegionCodeStreet;
	}
	/**
	 * @param accuserRegionCodeStreet the accuserRegionCodeStreet to set
	 */
	public void setAccuserRegionCodeStreet(String accuserRegionCodeStreet) {
		this.accuserRegionCodeStreet = accuserRegionCodeStreet;
	}
	/**
	 * @return the accuserDevianceCode
	 */
	public String getAccuserDevianceCode() {
		return accuserDevianceCode;
	}
	/**
	 * @param accuserDevianceCode the accuserDevianceCode to set
	 */
	public void setAccuserDevianceCode(String accuserDevianceCode) {
		this.accuserDevianceCode = accuserDevianceCode;
	}
	/**
	 * @return the accuserDevianceName
	 */
	public String getAccuserDevianceName() {
		return accuserDevianceName;
	}
	/**
	 * @param accuserDevianceName the accuserDevianceName to set
	 */
	public void setAccuserDevianceName(String accuserDevianceName) {
		this.accuserDevianceName = accuserDevianceName;
	}
	/**
	 * @return the accuserCategoryCode
	 */
	public String getAccuserCategoryCode() {
		return accuserCategoryCode;
	}
	/**
	 * @param accuserCategoryCode the accuserCategoryCode to set
	 */
	public void setAccuserCategoryCode(String accuserCategoryCode) {
		this.accuserCategoryCode = accuserCategoryCode;
	}
	/**
	 * @return the accuserCategoryName
	 */
	public String getAccuserCategoryName() {
		return accuserCategoryName;
	}
	/**
	 * @param accuserCategoryName the accuserCategoryName to set
	 */
	public void setAccuserCategoryName(String accuserCategoryName) {
		this.accuserCategoryName = accuserCategoryName;
	}
	/**
	 * @return the everAccusePlace
	 */
	public String getEverAccusePlace() {
		return everAccusePlace;
	}
	/**
	 * @param everAccusePlace the everAccusePlace to set
	 */
	public void setEverAccusePlace(String everAccusePlace) {
		this.everAccusePlace = everAccusePlace;
	}
	/**
	 * @return the everDealDept
	 */
	public String getEverDealDept() {
		return everDealDept;
	}
	/**
	 * @param everDealDept the everDealDept to set
	 */
	public void setEverDealDept(String everDealDept) {
		this.everDealDept = everDealDept;
	}
	
	/**
	 * @return the accusedNum
	 */
	public int getAccusedNum() {
		return accusedNum;
	}
	public String getAccuserAppealFlag() {
		return accuserAppealFlag;
	}
	public void setAccuserAppealFlag(String accuserAppealFlag) {
		this.accuserAppealFlag = accuserAppealFlag;
	}
	public String getAccusedAppealFlag() {
		return accusedAppealFlag;
	}
	public void setAccusedAppealFlag(String accusedAppealFlag) {
		this.accusedAppealFlag = accusedAppealFlag;
	}
	/**
	 * @param accusedNum the accusedNum to set
	 */
	public void setAccusedNum(int accusedNum) {
		this.accusedNum = accusedNum;
	}
	/**
	 * @return the accusedTypeCode
	 */
	public String getAccusedTypeCode() {
		return accusedTypeCode;
	}
	/**
	 * @param accusedTypeCode the accusedTypeCode to set
	 */
	public void setAccusedTypeCode(String accusedTypeCode) {
		this.accusedTypeCode = accusedTypeCode;
	}
	/**
	 * @return the accusedTypeName
	 */
	public String getAccusedTypeName() {
		return accusedTypeName;
	}
	/**
	 * @param accusedTypeName the accusedTypeName to set
	 */
	public void setAccusedTypeName(String accusedTypeName) {
		this.accusedTypeName = accusedTypeName;
	}
	/**
	 * @return the accusedDisciplineCode
	 */
	public String getAccusedDisciplineCode() {
		return accusedDisciplineCode;
	}
	/**
	 * @param accusedDisciplineCode the accusedDisciplineCode to set
	 */
	public void setAccusedDisciplineCode(String accusedDisciplineCode) {
		this.accusedDisciplineCode = accusedDisciplineCode;
	}
	/**
	 * @return the accusedDisciplineName
	 */
	public String getAccusedDisciplineName() {
		return accusedDisciplineName;
	}
	/**
	 * @param accusedDisciplineName the accusedDisciplineName to set
	 */
	public void setAccusedDisciplineName(String accusedDisciplineName) {
		this.accusedDisciplineName = accusedDisciplineName;
	}
	/**
	 * @return the accusedCardId
	 */
	public String getAccusedCardId() {
		return accusedCardId;
	}
	/**
	 * @param accusedCardId the accusedCardId to set
	 */
	public void setAccusedCardId(String accusedCardId) {
		this.accusedCardId = accusedCardId;
	}
	/**
	 * @return the accusedName
	 */
	public String getAccusedName() {
		return accusedName;
	}
	/**
	 * @param accusedName the accusedName to set
	 */
	public void setAccusedName(String accusedName) {
		this.accusedName = accusedName;
	}
	/**
	 * @return the accusedSexCode
	 */
	public String getAccusedSexCode() {
		return accusedSexCode;
	}
	/**
	 * @param accusedSexCode the accusedSexCode to set
	 */
	public void setAccusedSexCode(String accusedSexCode) {
		this.accusedSexCode = accusedSexCode;
	}
	/**
	 * @return the accusedSexName
	 */
	public String getAccusedSexName() {
		return accusedSexName;
	}
	/**
	 * @param accusedSexName the accusedSexName to set
	 */
	public void setAccusedSexName(String accusedSexName) {
		this.accusedSexName = accusedSexName;
	}
	/**
	 * @return the accusedBirthday
	 */
	public Timestamp getAccusedBirthday() {
		return accusedBirthday;
	}
	/**
	 * @param accusedBirthday the accusedBirthday to set
	 */
	public void setAccusedBirthday(Timestamp accusedBirthday) {
		this.accusedBirthday = accusedBirthday;
	}
	/**
	 * @return the accusedNationCode
	 */
	public String getAccusedNationCode() {
		return accusedNationCode;
	}
	/**
	 * @param accusedNationCode the accusedNationCode to set
	 */
	public void setAccusedNationCode(String accusedNationCode) {
		this.accusedNationCode = accusedNationCode;
	}
	/**
	 * @return the accusedNationName
	 */
	public String getAccusedNationName() {
		return accusedNationName;
	}
	/**
	 * @param accusedNationName the accusedNationName to set
	 */
	public void setAccusedNationName(String accusedNationName) {
		this.accusedNationName = accusedNationName;
	}
	/**
	 * @return the accusedPartyCode
	 */
	public String getAccusedPartyCode() {
		return accusedPartyCode;
	}
	/**
	 * @param accusedPartyCode the accusedPartyCode to set
	 */
	public void setAccusedPartyCode(String accusedPartyCode) {
		this.accusedPartyCode = accusedPartyCode;
	}
	/**
	 * @return the accusedPartyName
	 */
	public String getAccusedPartyName() {
		return accusedPartyName;
	}
	/**
	 * @param accusedPartyName the accusedPartyName to set
	 */
	public void setAccusedPartyName(String accusedPartyName) {
		this.accusedPartyName = accusedPartyName;
	}
	/**
	 * @return the accusedNpcMemberCode
	 */
	public String getAccusedNpcMemberCode() {
		return accusedNpcMemberCode;
	}
	/**
	 * @param accusedNpcMemberCode the accusedNpcMemberCode to set
	 */
	public void setAccusedNpcMemberCode(String accusedNpcMemberCode) {
		this.accusedNpcMemberCode = accusedNpcMemberCode;
	}
	/**
	 * @return the accusedNpcMemberName
	 */
	public String getAccusedNpcMemberName() {
		return accusedNpcMemberName;
	}
	/**
	 * @param accusedNpcMemberName the accusedNpcMemberName to set
	 */
	public void setAccusedNpcMemberName(String accusedNpcMemberName) {
		this.accusedNpcMemberName = accusedNpcMemberName;
	}
	/**
	 * @return the accusedCppccMemberCode
	 */
	public String getAccusedCppccMemberCode() {
		return accusedCppccMemberCode;
	}
	/**
	 * @param accusedCppccMemberCode the accusedCppccMemberCode to set
	 */
	public void setAccusedCppccMemberCode(String accusedCppccMemberCode) {
		this.accusedCppccMemberCode = accusedCppccMemberCode;
	}
	/**
	 * @return the accusedCppccMemberName
	 */
	public String getAccusedCppccMemberName() {
		return accusedCppccMemberName;
	}
	/**
	 * @param accusedCppccMemberName the accusedCppccMemberName to set
	 */
	public void setAccusedCppccMemberName(String accusedCppccMemberName) {
		this.accusedCppccMemberName = accusedCppccMemberName;
	}
	/**
	 * @return the accusedPartyMemberCode
	 */
	public String getAccusedPartyMemberCode() {
		return accusedPartyMemberCode;
	}
	/**
	 * @param accusedPartyMemberCode the accusedPartyMemberCode to set
	 */
	public void setAccusedPartyMemberCode(String accusedPartyMemberCode) {
		this.accusedPartyMemberCode = accusedPartyMemberCode;
	}
	/**
	 * @return the accusedPartyMemberName
	 */
	public String getAccusedPartyMemberName() {
		return accusedPartyMemberName;
	}
	/**
	 * @param accusedPartyMemberName the accusedPartyMemberName to set
	 */
	public void setAccusedPartyMemberName(String accusedPartyMemberName) {
		this.accusedPartyMemberName = accusedPartyMemberName;
	}
	/**
	 * @return the accusedPositionName
	 */
	public String getAccusedPositionName() {
		return accusedPositionName;
	}
	/**
	 * @param accusedPositionName the accusedPositionName to set
	 */
	public void setAccusedPositionName(String accusedPositionName) {
		this.accusedPositionName = accusedPositionName;
	}
	/**
	 * @return the accusedClassCode
	 */
	public String getAccusedClassCode() {
		return accusedClassCode;
	}
	/**
	 * @param accusedClassCode the accusedClassCode to set
	 */
	public void setAccusedClassCode(String accusedClassCode) {
		this.accusedClassCode = accusedClassCode;
	}
	/**
	 * @return the accusedClassName
	 */
	public String getAccusedClassName() {
		return accusedClassName;
	}
	/**
	 * @param accusedClassName the accusedClassName to set
	 */
	public void setAccusedClassName(String accusedClassName) {
		this.accusedClassName = accusedClassName;
	}
	/**
	 * @return the accusedHomePlace
	 */
	public String getAccusedHomePlace() {
		return accusedHomePlace;
	}
	/**
	 * @param accusedHomePlace the accusedHomePlace to set
	 */
	
	public void setAccusedHomePlace(String accusedHomePlace) {
		this.accusedHomePlace = accusedHomePlace;
	}
	/**
	 * @return the accuserWorkUnit
	 */
	public String getAccuserWorkUnit() {
		return accuserWorkUnit;
	}
	/**
	 * @param accuserWorkUnit the accuserWorkUnit to set
	 */
	public void setAccuserWorkUnit(String accuserWorkUnit) {
		this.accuserWorkUnit = accuserWorkUnit;
	}
	public String getAccuserHomePlace() {
		return accuserHomePlace;
	}
	public void setAccuserHomePlace(String accuserHomePlace) {
		this.accuserHomePlace = accuserHomePlace;
	}
	public String getAccusedWorkUnit() {
		return accusedWorkUnit;
	}
	public void setAccusedWorkUnit(String accusedWorkUnit) {
		this.accusedWorkUnit = accusedWorkUnit;
	}
	/**
	 * @return the accusedWorkTypeCode
	 */
	public String getAccusedWorkTypeCode() {
		return accusedWorkTypeCode;
	}
	/**
	 * @param accusedWorkTypeCode the accusedWorkTypeCode to set
	 */
	public void setAccusedWorkTypeCode(String accusedWorkTypeCode) {
		this.accusedWorkTypeCode = accusedWorkTypeCode;
	}
	/**
	 * @return the accusedWorkTypeName
	 */
	public String getAccusedWorkTypeName() {
		return accusedWorkTypeName;
	}
	/**
	 * @param accusedWorkTypeName the accusedWorkTypeName to set
	 */
	public void setAccusedWorkTypeName(String accusedWorkTypeName) {
		this.accusedWorkTypeName = accusedWorkTypeName;
	}
	/**
	 * @return the accusedEnterpriseTypeCode
	 */
	public String getAccusedEnterpriseTypeCode() {
		return accusedEnterpriseTypeCode;
	}
	/**
	 * @param accusedEnterpriseTypeCode the accusedEnterpriseTypeCode to set
	 */
	public void setAccusedEnterpriseTypeCode(String accusedEnterpriseTypeCode) {
		this.accusedEnterpriseTypeCode = accusedEnterpriseTypeCode;
	}
	/**
	 * @return the accusedEnterpriseTypeName
	 */
	public String getAccusedEnterpriseTypeName() {
		return accusedEnterpriseTypeName;
	}
	/**
	 * @param accusedEnterpriseTypeName the accusedEnterpriseTypeName to set
	 */
	public void setAccusedEnterpriseTypeName(String accusedEnterpriseTypeName) {
		this.accusedEnterpriseTypeName = accusedEnterpriseTypeName;
	}
	/**
	 * @return the accusedTelOne
	 */
	public String getAccusedTelOne() {
		return accusedTelOne;
	}
	/**
	 * @param accusedTelOne the accusedTelOne to set
	 */
	public void setAccusedTelOne(String accusedTelOne) {
		this.accusedTelOne = accusedTelOne;
	}
	/**
	 * @return the accusedTelTwo
	 */
	public String getAccusedTelTwo() {
		return accusedTelTwo;
	}
	/**
	 * @param accusedTelTwo the accusedTelTwo to set
	 */
	public void setAccusedTelTwo(String accusedTelTwo) {
		this.accusedTelTwo = accusedTelTwo;
	}
	/**
	 * @return the accusedTelThree
	 */
	public String getAccusedTelThree() {
		return accusedTelThree;
	}
	/**
	 * @param accusedTelThree the accusedTelThree to set
	 */
	public void setAccusedTelThree(String accusedTelThree) {
		this.accusedTelThree = accusedTelThree;
	}
	/**
	 * @return the accusedAddr
	 */
	public String getAccusedAddr() {
		return accusedAddr;
	}
	/**
	 * @param accusedAddr the accusedAddr to set
	 */
	public void setAccusedAddr(String accusedAddr) {
		this.accusedAddr = accusedAddr;
	}
	/**
	 * @return the accusedZip
	 */
	public String getAccusedZip() {
		return accusedZip;
	}
	/**
	 * @param accusedZip the accusedZip to set
	 */
	public void setAccusedZip(String accusedZip) {
		this.accusedZip = accusedZip;
	}
	/**
	 * @return the accusedRegionCode
	 */
	public String getAccusedRegionCode() {
		return accusedRegionCode;
	}
	/**
	 * @param accusedRegionCode the accusedRegionCode to set
	 */
	public void setAccusedRegionCode(String accusedRegionCode) {
		this.accusedRegionCode = accusedRegionCode;
	}
	/**
	 * @return the accusedRegionName
	 */
	public String getAccusedRegionName() {
		return accusedRegionName;
	}
	/**
	 * @param accusedRegionName the accusedRegionName to set
	 */
	public void setAccusedRegionName(String accusedRegionName) {
		this.accusedRegionName = accusedRegionName;
	}
	/**
	 * @return the accusedRegionCodeProv
	 */
	public String getAccusedRegionCodeProv() {
		return accusedRegionCodeProv;
	}
	/**
	 * @param accusedRegionCodeProv the accusedRegionCodeProv to set
	 */
	public void setAccusedRegionCodeProv(String accusedRegionCodeProv) {
		this.accusedRegionCodeProv = accusedRegionCodeProv;
	}
	/**
	 * @return the accusedRegionCodeCity
	 */
	public String getAccusedRegionCodeCity() {
		return accusedRegionCodeCity;
	}
	/**
	 * @param accusedRegionCodeCity the accusedRegionCodeCity to set
	 */
	public void setAccusedRegionCodeCity(String accusedRegionCodeCity) {
		this.accusedRegionCodeCity = accusedRegionCodeCity;
	}
	/**
	 * @return the accusedRegionCodeCounty
	 */
	public String getAccusedRegionCodeCounty() {
		return accusedRegionCodeCounty;
	}
	/**
	 * @param accusedRegionCodeCounty the accusedRegionCodeCounty to set
	 */
	public void setAccusedRegionCodeCounty(String accusedRegionCodeCounty) {
		this.accusedRegionCodeCounty = accusedRegionCodeCounty;
	}
	/**
	 * @return the accusedRegionCodeTown
	 */
	public String getAccusedRegionCodeTown() {
		return accusedRegionCodeTown;
	}
	/**
	 * @param accusedRegionCodeTown the accusedRegionCodeTown to set
	 */
	public void setAccusedRegionCodeTown(String accusedRegionCodeTown) {
		this.accusedRegionCodeTown = accusedRegionCodeTown;
	}
	/**
	 * @return the accusedRegionCodeStreet
	 */
	public String getAccusedRegionCodeStreet() {
		return accusedRegionCodeStreet;
	}
	/**
	 * @param accusedRegionCodeStreet the accusedRegionCodeStreet to set
	 */
	public void setAccusedRegionCodeStreet(String accusedRegionCodeStreet) {
		this.accusedRegionCodeStreet = accusedRegionCodeStreet;
	}
	/**
	 * @return the accusedDevianceFlag
	 */
	public String getAccusedDevianceFlag() {
		return accusedDevianceFlag;
	}
	/**
	 * @param accusedDevianceFlag the accusedDevianceFlag to set
	 */
	public void setAccusedDevianceFlag(String accusedDevianceFlag) {
		this.accusedDevianceFlag = accusedDevianceFlag;
	}
	/**
	 * @return the accusedLocalCadreFlag
	 */
	public String getAccusedLocalCadreFlag() {
		return accusedLocalCadreFlag;
	}
	/**
	 * @param accusedLocalCadreFlag the accusedLocalCadreFlag to set
	 */
	public void setAccusedLocalCadreFlag(String accusedLocalCadreFlag) {
		this.accusedLocalCadreFlag = accusedLocalCadreFlag;
	}
	/**
	 * @return the accusedLeaderDisciplineCode
	 */
	public String getAccusedLeaderDisciplineCode() {
		return accusedLeaderDisciplineCode;
	}
	/**
	 * @param accusedLeaderDisciplineCode the accusedLeaderDisciplineCode to set
	 */
	public void setAccusedLeaderDisciplineCode(String accusedLeaderDisciplineCode) {
		this.accusedLeaderDisciplineCode = accusedLeaderDisciplineCode;
	}
	/**
	 * @return the accusedLeaderDisciplineName
	 */
	public String getAccusedLeaderDisciplineName() {
		return accusedLeaderDisciplineName;
	}
	/**
	 * @param accusedLeaderDisciplineName the accusedLeaderDisciplineName to set
	 */
	public void setAccusedLeaderDisciplineName(String accusedLeaderDisciplineName) {
		this.accusedLeaderDisciplineName = accusedLeaderDisciplineName;
	}
	/**
	 * @return the accusedCategoryCode
	 */
	public String getAccusedCategoryCode() {
		return accusedCategoryCode;
	}
	/**
	 * @param accusedCategoryCode the accusedCategoryCode to set
	 */
	public void setAccusedCategoryCode(String accusedCategoryCode) {
		this.accusedCategoryCode = accusedCategoryCode;
	}
	/**
	 * @return the accusedCategoryName
	 */
	public String getAccusedCategoryName() {
		return accusedCategoryName;
	}
	/**
	 * @param accusedCategoryName the accusedCategoryName to set
	 */
	public void setAccusedCategoryName(String accusedCategoryName) {
		this.accusedCategoryName = accusedCategoryName;
	}
	public String getReturnContent() {
		return returnContent;
	}
	public void setReturnContent(String returnContent) {
		this.returnContent = returnContent;
	}
	public String getPretreatment() {
		return pretreatment;
	}
	public void setPretreatment(String pretreatment) {
		this.pretreatment = pretreatment;
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
	public String getRepeatFlag() {
		return repeatFlag;
	}
	public void setRepeatFlag(String repeatFlag) {
		this.repeatFlag = repeatFlag;
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
	public String getOtherIssueTypeName() {
		return otherIssueTypeName;
	}
	public void setOtherIssueTypeName(String otherIssueTypeName) {
		this.otherIssueTypeName = otherIssueTypeName;
	}
	public String getOtherIssueTypeCode() {
		return otherIssueTypeCode;
	}
	public void setOtherIssueTypeCode(String otherIssueTypeCode) {
		this.otherIssueTypeCode = otherIssueTypeCode;
	}
	public String getDefaultDealerName() {
		return defaultDealerName;
	}
	public void setDefaultDealerName(String defaultDealerName) {
		this.defaultDealerName = defaultDealerName;
	}
	public Timestamp getSignDate() {
		return signDate;
	}
	public void setSignDate(Timestamp signDate) {
		this.signDate = signDate;
	}
	public String getPostponeFlag() {
		return postponeFlag;
	}
	public void setPostponeFlag(String postponeFlag) {
		this.postponeFlag = postponeFlag;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getInstructText() {
		return instructText;
	}
	public void setInstructText(String instructText) {
		this.instructText = instructText;
	}
	public Timestamp getInstructTime() {
		return instructTime;
	}
	public void setInstructTime(Timestamp instructTime) {
		this.instructTime = instructTime;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public Timestamp getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(Timestamp assignedDate) {
		this.assignedDate = assignedDate;
	}
	public Timestamp getAssignedRequireEndDate() {
		return assignedRequireEndDate;
	}
	public void setAssignedRequireEndDate(Timestamp assignedRequireEndDate) {
		this.assignedRequireEndDate = assignedRequireEndDate;
	}
	public String getDealSuggestion() {
		return dealSuggestion;
	}
	public void setDealSuggestion(String dealSuggestion) {
		this.dealSuggestion = dealSuggestion;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
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
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getIsSecretaryFlag() {
		return isSecretaryFlag;
	}
	public void setIsSecretaryFlag(String isSecretaryFlag) {
		this.isSecretaryFlag = isSecretaryFlag;
	}
	public String getCurrDeptName() {
		return currDeptName;
	}
	public void setCurrDeptName(String currDeptName) {
		this.currDeptName = currDeptName;
	}
	public String getCurrDeptCode() {
		return currDeptCode;
	}
	public void setCurrDeptCode(String currDeptCode) {
		this.currDeptCode = currDeptCode;
	}
	public String getPetitionSourceParentCode() {
		return petitionSourceParentCode;
	}
	public void setPetitionSourceParentCode(String petitionSourceParentCode) {
		this.petitionSourceParentCode = petitionSourceParentCode;
	}
	public String getToRegionName() {
		return toRegionName;
	}
	public void setToRegionName(String toRegionName) {
		this.toRegionName = toRegionName;
	}
	public String getIsSwgat() {
		return isSwgat;
	}
	public void setIsSwgat(String isSwgat) {
		this.isSwgat = isSwgat;
	}
	//2016-05-25 byg 在全局修改中添加 “线索来源” 修改项
	public String getHandlingPretreatment() {
		return handlingPretreatment;
	}
	public void setHandlingPretreatment(String handlingPretreatment) {
		this.handlingPretreatment = handlingPretreatment;
	}
	public String getQueryKeyNo() {
		return queryKeyNo;
	}
	public void setQueryKeyNo(String queryKeyNo) {
		this.queryKeyNo = queryKeyNo;
	}
	
}
