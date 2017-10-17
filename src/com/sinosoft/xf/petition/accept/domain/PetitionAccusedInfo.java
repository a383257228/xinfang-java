package com.sinosoft.xf.petition.accept.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 信访被反映人信息表实体类信息
 * @date 2011-11-09
 * @author wangxx
 */
@Entity
@Table(name = "PETITION_ACCUSED_INFO")
public class PetitionAccusedInfo extends AudiEntity{

	private static final long serialVersionUID = 1L;


	private String accusedCardId;
	private String encryaccusedCardId;
	@Transient
	public String getAccusedCardId() {
		accusedCardId = Encrypt.decrypt(encryaccusedCardId);
		return accusedCardId;
	}
	public void setAccusedCardId(String accusedCardId) {
		this.accusedCardId = accusedCardId;
		this.encryaccusedCardId = Encrypt.encrypt(accusedCardId);
	}
	@Column(name = "ACCUSED_CARD_ID")
	public String getEncryaccusedCardId() {
		return encryaccusedCardId;
	}
	public void setEncryaccusedCardId(String accusedCardId) {
		this.encryaccusedCardId =  accusedCardId;
	}
	
	private String accusedName;
	private String encryaccusedName;
	@Transient
	public String getAccusedName() {
		accusedName = Encrypt.decrypt(encryaccusedName);
		return accusedName;
	}
	public void setAccusedName(String accusedName) {
		this.accusedName = accusedName;
		this.encryaccusedName = Encrypt.encrypt(accusedName);;
	}
	@Column(name = "ACCUSED_NAME")
	public String getEncryaccusedName() {
		return encryaccusedName;
	}
	public void setEncryaccusedName(String accusedName) {
		this.encryaccusedName = accusedName;
	}
	
	private String accusedAddr;
	private String encryaccusedAddr;
	@Transient
	public String getAccusedAddr() {
		accusedAddr = Encrypt.decrypt(encryaccusedAddr);
		return accusedAddr;
	}
	public void setAccusedAddr(String accusedAddr) {
		this.accusedAddr = accusedAddr;
		this.encryaccusedAddr =  Encrypt.encrypt(accusedAddr);
	}
	@Column(name = "ACCUSED_ADDR")
	public String getEncryaccusedAddr() {
		return encryaccusedAddr;
	}
	public void setEncryaccusedAddr(String accusedAddr) {
		this.encryaccusedAddr = accusedAddr;
	}

	private String accusedHomePlace;
	private String encryaccusedHomePlace;
	@Transient
	public String getAccusedHomePlace() {
		this.accusedHomePlace = Encrypt.decrypt(encryaccusedHomePlace);
		return accusedHomePlace;
	}
	public void setAccusedHomePlace(String accusedHomePlace) {
		this.accusedHomePlace = accusedHomePlace;
		this.encryaccusedHomePlace = Encrypt.encrypt(accusedHomePlace);
	}
	@Column(name = "ACCUSED_HOME_PLACE")
	public String getEncryaccusedHomePlace() {
		return encryaccusedHomePlace;
	}
	public void setEncryaccusedHomePlace(String accusedHomePlace) {
		this.encryaccusedHomePlace =  accusedHomePlace;
	}	
	
	private String accusedWorkUnit;
	private String encryaccusedWorkUnit;
	@Transient
	public String getAccusedWorkUnit() {
		accusedWorkUnit = Encrypt.decrypt(encryaccusedWorkUnit);
		return accusedWorkUnit;
	}
	public void setAccusedWorkUnit(String accusedWorkUnit) {
		this.accusedWorkUnit = accusedWorkUnit;
		this.encryaccusedWorkUnit = Encrypt.encrypt(accusedWorkUnit);;
	}
	@Column(name = "ACCUSED_WORK_UNIT")
	public String getEncryaccusedWorkUnit() {
		return encryaccusedWorkUnit;
	}
	public void setEncryaccusedWorkUnit(String accusedWorkUnit) {
		this.encryaccusedWorkUnit = accusedWorkUnit;
	}
	private String accusedTelOne;
	private String encryaccusedTelOne;
	@Transient
	public String getAccusedTelOne() {
		accusedTelOne = Encrypt.decrypt(encryaccusedTelOne);
		return accusedTelOne;
	}
	public void setAccusedTelOne(String accusedTelOne) {
		this.accusedTelOne = accusedTelOne;
		this.encryaccusedTelOne = Encrypt.encrypt(accusedTelOne);;
	}
	@Column(name = "ACCUSED_TEL_ONE")
	public String getEncryaccusedTelOne() {
		return encryaccusedTelOne;
	}
	public void setEncryaccusedTelOne(String accusedTelOne) {
		this.encryaccusedTelOne = accusedTelOne;
	}
	
	
	private String accusedTelTwo;
	private String encryaccusedTelTwo;
	@Transient
	public String getAccusedTelTwo() {
		accusedTelTwo = Encrypt.decrypt(encryaccusedTelTwo);
		return accusedTelTwo;
	}
	public void setAccusedTelTwo(String accusedTelTwo) {
		this.accusedTelTwo = accusedTelTwo;
		this.encryaccusedTelTwo = Encrypt.encrypt(accusedTelTwo);
	}
	@Column(name = "ACCUSED_TEL_TWO")
	public String getEncryaccusedTelTwo() {
		return encryaccusedTelTwo;
	}
	public void setEncryaccusedTelTwo(String accusedTelTwo) {
		this.encryaccusedTelTwo = accusedTelTwo;
	}
	
	
	private String accusedTelThree;
	private String encryaccusedTelThree;
	@Transient
	public String getAccusedTelThree() {
		accusedTelThree = Encrypt.decrypt(encryaccusedTelThree);
		return accusedTelThree;
	}
	public void setAccusedTelThree(String accusedTelThree) {
		this.accusedTelThree = accusedTelThree;
		this.encryaccusedTelThree = Encrypt.encrypt(accusedTelThree);
	}
	@Column(name = "ACCUSED_TEL_THREE")
	public String getEncryaccusedTelThree() {
		return encryaccusedTelThree;
	}
	public void setEncryaccusedTelThree(String accusedTelThree) {
		this.encryaccusedTelThree = accusedTelThree;
	}
	private String accusedEmail;
	private String encryaccusedEmail;
	@Transient
	public String getAccusedEmail() {
		accusedEmail = Encrypt.decrypt(encryaccusedEmail);
		return accusedEmail;
	}
	public void setAccusedEmail(String accusedEmail) {
		this.accusedEmail = accusedEmail;
		this.encryaccusedEmail = Encrypt.encrypt(accusedEmail);
	}
	@Column(name = "ACCUSED_EMAIL")
	public String getEncryaccusedEmail() {
		return encryaccusedEmail;
	}
	public void setEncryaccusedEmail(String accusedEmail) {
		this.encryaccusedEmail = accusedEmail;
	}
	private String petitionNo;
	private PetitionBasicInfo petitionBasicInfo;
	private String regionCode;
	private String regionName;
	private int accusedNum;
	private String accusedTypeCode = "2";//1：单位  2：个人
	private String accusedTypeName = "个人";
	private String accusedDisciplineCode;
	private String accusedDisciplineName;
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
	private String accusedPositionCode;
	private String accusedPositionName;
	private String accusedClassCode;
	private String accusedClassName;
	private String accusedWorkTypeCode;
	private String accusedWorkTypeName;
	private String accusedEnterpriseTypeCode;
	private String accusedEnterpriseTypeName;
	private String accusedZip;
	private String accusedRegionCode;
	private String accusedRegionName;
	private String accusedRegionCodeProv;
	private String accusedRegionCodeCity;
	private String accusedRegionCodeCounty;
	private String accusedRegionCodeTown;
	private String accusedRegionCodeStreet;
	private String accusedDevianceFlag="2";//负责人 1、是  2、否  0、不详 
	private String accusedLocalCadreFlag="2";//本级干部 1、是  2、否  0、不详 
	private String accusedLeaderDisciplineCode;
	private String accusedLeaderDisciplineName;
	private String accusedCategoryCode;
	private String accusedCategoryName;
	private String constantAppealFlag;//老访户 1是0否
	private String issueTypeCode;
	private String issueTypeName;
	private String punishTypeCode;
	private String punishTypeName;
	private String punishContent;
	private Double lostRetrieve;
	private String partyMeasureCode;
	private String partyMeasureName;
	private String executiveMeasureCode;
	private String executiveMeasureName;
	private String acussedOpinion;
	
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
	 * @return the accusedNum
	 */
	public int getAccusedNum() {
		return accusedNum;
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
	public void setAccusedDisciplineCode(
			String accusedDisciplineCode) {
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
	public void setAccusedDisciplineName(
			String accusedDisciplineName) {
		this.accusedDisciplineName = accusedDisciplineName;
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
	 * @return the accusedPositionCode
	 */
	public String getAccusedPositionCode() {
		return accusedPositionCode;
	}
	/**
	 * @param accusedPositionCode the accusedPositionCode to set
	 */
	public void setAccusedPositionCode(String accusedPositionCode) {
		this.accusedPositionCode = accusedPositionCode;
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
	public void setAccusedEnterpriseTypeCode(
			String accusedEnterpriseTypeCode) {
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
	public void setAccusedEnterpriseTypeName(
			String accusedEnterpriseTypeName) {
		this.accusedEnterpriseTypeName = accusedEnterpriseTypeName;
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
	public String getAccusedDevianceFlag() {
		return accusedDevianceFlag;
	}
	public void setAccusedDevianceFlag(String accusedDevianceFlag) {
		if(accusedDevianceFlag==null||"".equals(accusedDevianceFlag)){
			accusedDevianceFlag = "2";
		}
		this.accusedDevianceFlag = accusedDevianceFlag;
	}
	public String getAccusedLocalCadreFlag() {
		return accusedLocalCadreFlag;
	}
	/**
	 * @param accusedLocalCadreFlag the accusedLocalCadreFlag to set
	 */
	public void setAccusedLocalCadreFlag(String accusedLocalCadreFlag) {
		if(accusedLocalCadreFlag==null||"".equals(accusedLocalCadreFlag)){
			accusedLocalCadreFlag = "2";
		}
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
	public String getConstantAppealFlag() {
		return constantAppealFlag;
	}
	public void setConstantAppealFlag(String constantAppealFlag) {
		this.constantAppealFlag = constantAppealFlag;
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
	/**
	 * @return the punishTypeCode
	 */
	public String getPunishTypeCode() {
		return punishTypeCode;
	}
	/**
	 * @param punishTypeCode the punishTypeCode to set
	 */
	public void setPunishTypeCode(String punishTypeCode) {
		if(null!=punishTypeCode&&"1".equals(punishTypeCode.trim())){
			this.setPunishContent("");
		}else if(null!=punishTypeCode&&("3".equals(punishTypeCode.trim())||"2".equals(punishTypeCode.trim()))){
			this.setPartyMeasureCode("");
			this.setPartyMeasureName("");
			this.setExecutiveMeasureCode("");
			this.setExecutiveMeasureName("");
			this.setPunishContent("");
		}else{
			this.setPartyMeasureCode("");
			this.setPartyMeasureName("");
			this.setExecutiveMeasureCode("");
			this.setExecutiveMeasureName("");
		}
		this.punishTypeCode = punishTypeCode;
	}
	public String getPunishTypeName() {
		return punishTypeName;
	}
	public void setPunishTypeName(String punishTypeName) {
		this.punishTypeName = punishTypeName;
	}
	public String getPunishContent() {
		return punishContent;
	}
	public void setPunishContent(String punishContent) {
		if(null!=this.getPunishTypeCode()&&
				("1".equals(this.getPunishTypeCode().trim())||"3".equals(this.getPunishTypeCode().trim())
				||"2".equals(this.getPunishTypeCode().trim()))){
			this.punishContent = "";
		}else{
			this.punishContent = punishContent;
		}
	}
	public String getPartyMeasureCode() {
		return partyMeasureCode;
	}
	public void setPartyMeasureCode(String partyMeasureCode) {
		if(null!=this.getPunishTypeCode()&&
				("3".equals(this.getPunishTypeCode().trim())
				||"2".equals(this.getPunishTypeCode().trim())
				||"4".equals(this.getPunishTypeCode().trim())
				||"5".equals(this.getPunishTypeCode().trim())
				||"6".equals(this.getPunishTypeCode().trim()))){
			this.partyMeasureCode = "";
		}else{
			this.partyMeasureCode = partyMeasureCode;
		}
	}
	public String getPartyMeasureName() {
		return partyMeasureName;
	}
	public void setPartyMeasureName(String partyMeasureName) {
		if(null!=this.getPunishTypeCode()&&
				("3".equals(this.getPunishTypeCode().trim())
				||"2".equals(this.getPunishTypeCode().trim())
				||"4".equals(this.getPunishTypeCode().trim())
				||"5".equals(this.getPunishTypeCode().trim())
				||"6".equals(this.getPunishTypeCode().trim()))){
			this.partyMeasureName = "";
		}else{
			this.partyMeasureName = partyMeasureName;
		}
	}
	public String getExecutiveMeasureCode() {
		return executiveMeasureCode;
	}
	public void setExecutiveMeasureCode(String executiveMeasureCode) {
		if(null!=this.getPunishTypeCode()&&
				("2".equals(this.getPunishTypeCode().trim())
				||"3".equals(this.getPunishTypeCode().trim())
				||"4".equals(this.getPunishTypeCode().trim())
				||"5".equals(this.getPunishTypeCode().trim())
				||"6".equals(this.getPunishTypeCode().trim()))){
			this.executiveMeasureCode = "";
		}else{
			this.executiveMeasureCode = executiveMeasureCode;
		}
	}
	public String getExecutiveMeasureName() {
		return executiveMeasureName;
	}
	public void setExecutiveMeasureName(String executiveMeasureName) {
		if(null!=this.getPunishTypeCode()&&
				("2".equals(this.getPunishTypeCode().trim())
				||"3".equals(this.getPunishTypeCode().trim())
				||"4".equals(this.getPunishTypeCode().trim())
				||"5".equals(this.getPunishTypeCode().trim())
				||"6".equals(this.getPunishTypeCode().trim()))){
			this.executiveMeasureName = "";
		}else{
			this.executiveMeasureName = executiveMeasureName;
		}
	}
	public String getAcussedOpinion() {
		return acussedOpinion;
	}
	public void setAcussedOpinion(String acussedOpinion) {
		this.acussedOpinion = acussedOpinion;
	}
	public Double getLostRetrieve() {
		return lostRetrieve;
	}
	public void setLostRetrieve(Double lostRetrieve) {
		this.lostRetrieve = lostRetrieve;
	}
	
}