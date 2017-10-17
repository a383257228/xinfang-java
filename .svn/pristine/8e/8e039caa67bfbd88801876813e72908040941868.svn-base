package com.sinosoft.xf.petition.accept.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoftframework.core.domain.IdEntity;


/**
 * 网络举报接口信息bean
 * @date 2011-12-20
 * @author ljx
 */
@Entity
@Table(name = "PETITION_NET_INFO")
public class PetitionNetInfo extends IdEntity {

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
	
	
	private String accuserCardId;
	private String encryaccuserCardId;
	@Transient
	public String getAccuserCardId() {
		accuserCardId = Encrypt.decrypt(encryaccuserCardId);
		return accuserCardId;
	}
	public void setAccuserCardId(String accuserCardId) {
		this.accuserCardId = accuserCardId;
		this.encryaccuserCardId = Encrypt.encrypt(accuserCardId);
	}
	@Column(name = "ACCUSER_CARD_ID")
	public String getEncryaccuserCardId() {
		return encryaccuserCardId;
	}
	public void setEncryaccuserCardId(String accuserCardId) {
		this.encryaccuserCardId =  accuserCardId;
	}
	
	private String accuserName;
	private String encryaccuserName;
	@Transient
	public String getAccuserName() {
		accuserName = Encrypt.decrypt(encryaccuserName);
		return accuserName;
	}
	public void setAccuserName(String accuserName) {
		this.accuserName = accuserName;
		this.encryaccuserName = Encrypt.encrypt(accuserName);;
	}
	@Column(name = "ACCUSER_NAME")
	public String getEncryaccuserName() {
		return encryaccuserName;
	}
	public void setEncryaccuserName(String accuserName) {
		this.encryaccuserName = accuserName;
	}
	
	
	private String accuserAddr;
	private String encryaccuserAddr;
	@Transient
	public String getAccuserAddr() {
		accuserAddr = Encrypt.decrypt(encryaccuserAddr);
		return accuserAddr;
	}
	public void setAccuserAddr(String accuserAddr) {
		this.accuserAddr = accuserAddr;
		this.encryaccuserAddr =  Encrypt.encrypt(accuserAddr);
	}
	@Column(name = "ACCUSER_ADDR")
	public String getEncryaccuserAddr() {
		return encryaccuserAddr;
	}
	public void setEncryaccuserAddr(String accuserAddr) {
		this.encryaccuserAddr = accuserAddr;
	}
	
	private String accuserHomePlace;
	private String encryaccuserHomePlace;
	@Transient
	public String getAccuserHomePlace() {
		accuserHomePlace = Encrypt.decrypt(encryaccuserHomePlace);
		return accuserHomePlace;
	}
	public void setAccuserHomePlace(String accuserHomePlace) {
		this.accuserHomePlace = accuserHomePlace;
		this.encryaccuserHomePlace =  Encrypt.encrypt(accuserHomePlace);
	}
	@Column(name = "ACCUSER_HOME_PLACE")
	public String getEncryaccuserHomePlace() {
		return encryaccuserHomePlace;
	}
	public void setEncryaccuserHomePlace(String accuserHomePlace) {
		this.encryaccuserHomePlace = accuserHomePlace;
	}
	private String accuserWorkUnit;
	private String encryaccuserWorkUnit;
	@Transient
	public String getAccuserWorkUnit() {
		accuserWorkUnit = Encrypt.decrypt(encryaccuserWorkUnit);
		return accuserWorkUnit;
	}
	public void setAccuserWorkUnit(String accuserWorkUnit) {
		this.accuserWorkUnit = accuserWorkUnit;
		this.encryaccuserWorkUnit = Encrypt.encrypt(accuserWorkUnit);;
	}
	private String accuserEmail;
	private String encryaccuserEmail;
	@Transient
	public String getAccuserEmail() {
		accuserEmail = Encrypt.decrypt(encryaccuserEmail);
		return accuserEmail;
	}
	public void setAccuserEmail(String accuserEmail) {
		this.accuserEmail = accuserEmail;
		this.encryaccuserEmail = Encrypt.encrypt(accuserEmail);
	}
	@Column(name = "ACCUSER_EMAIL")
	public String getEncryaccuserEmail() {
		return encryaccuserEmail;
	}
	public void setEncryaccuserEmail(String accuserEmail) {
		this.encryaccuserEmail = accuserEmail;
	}
	
	private String accuserTelOne;
	private String encryaccuserTelOne;
	@Transient
	public String getAccuserTelOne() {
		accuserTelOne = Encrypt.decrypt(encryaccuserTelOne);
		return accuserTelOne;
	}
	public void setAccuserTelOne(String accuserTelOne) {
		this.accuserTelOne = accuserTelOne;
		this.encryaccuserTelOne = Encrypt.encrypt(accuserTelOne);;
	}
	@Column(name = "ACCUSER_TEL_ONE")
	public String getEncryaccuserTelOne() {
		return encryaccuserTelOne;
	}
	public void setEncryaccuserTelOne(String accuserTelOne) {
		this.encryaccuserTelOne = accuserTelOne;
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
	
	private String petitionAcceptNo;
	private String petitionNetNo;
	private String regionCode;
	private String regionName;
	private Timestamp petitionDate;
	private String petitionTypeCode;
	private String petitionTypeName;
	private String petitionClassCode;
	private String petitionClassName;
	private String petitionSourceCode;
	private String petitionSourceName;
	private String netSourceCode;
	private String netSourceName;
	private String anonymousFlag;
	private String groupFlag;
	private Integer personNum;
	private String accusedRegionCode;
	private String accusedRegionName;
	private String accusedSexCode;
	private String accusedSexName;
	private String accusedWorkTypeCode;
	private String accusedWorkTypeName;
	private String accusedPositonCode;
	private String accusedPositonName;
	private String accusedClassCode;
	private String accusedClassName;
	private String accusedDisciplineCode;
	private String accusedDisciplineName;
	private String accusedDevianceFlag;
	private String accusedLocalCadreFlag;
	private String accusedZip;
	private String accuserSexCode;
	private String accuserSexName;
	private String accuserNationCode;
	private String accuserNationName;
	private String accuserPartyCode;
	private String accuserPartyName;
	private String accuserRegionCode;
	private String accuserRegionName;
	private String accuserOccupationCode;
	private String accuserOccupationName;
	private String accuserZip;
	private String accuserDevianceCode;
	private String accuserDevianceName;
	private String everAccusePlace;
	private String everDealDept;
	private String issueTypeCode;
	private String issueTypeName;
	private String originDealRegion;
	private String originSolution;
	private String validFlag;
	private String replyFlag;
	private String remark;
	private PetitionNetContentInfo petitionNetContentInfo;

	@Column(name = "PETITION_NET_NO", length = 20)
	public String getPetitionNetNo() {
		return this.petitionNetNo;
	}

	public void setPetitionNetNo(String petitionNetNo) {
		this.petitionNetNo = petitionNetNo;
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

	@Column(name = "ACCUSED_REGION_CODE", length = 12)
	public String getAccusedRegionCode() {
		return this.accusedRegionCode;
	}

	public void setAccusedRegionCode(String accusedRegionCode) {
		this.accusedRegionCode = accusedRegionCode;
	}

	@Column(name = "ACCUSED_REGION_NAME", length = 100)
	public String getAccusedRegionName() {
		return this.accusedRegionName;
	}

	public void setAccusedRegionName(String accusedRegionName) {
		this.accusedRegionName = accusedRegionName;
	}
	@Column(name = "ACCUSED_SEX_CODE", length = 50)
	public String getAccusedSexCode() {
		return this.accusedSexCode;
	}

	public void setAccusedSexCode(String accusedSexCode) {
		this.accusedSexCode = accusedSexCode;
	}

	@Column(name = "ACCUSED_SEX_NAME", length = 10)
	public String getAccusedSexName() {
		return this.accusedSexName;
	}

	public void setAccusedSexName(String accusedSexName) {
		this.accusedSexName = accusedSexName;
	}

	@Column(name = "ACCUSED_WORK_TYPE_CODE", length = 50)
	public String getAccusedWorkTypeCode() {
		return this.accusedWorkTypeCode;
	}

	public void setAccusedWorkTypeCode(String accusedWorkTypeCode) {
		this.accusedWorkTypeCode = accusedWorkTypeCode;
	}

	@Column(name = "ACCUSED_WORK_TYPE_NAME", length = 100)
	public String getAccusedWorkTypeName() {
		return this.accusedWorkTypeName;
	}

	public void setAccusedWorkTypeName(String accusedWorkTypeName) {
		this.accusedWorkTypeName = accusedWorkTypeName;
	}

	@Column(name = "ACCUSED_POSITON_CODE", length = 50)
	public String getAccusedPositonCode() {
		return this.accusedPositonCode;
	}

	public void setAccusedPositonCode(String accusedPositonCode) {
		this.accusedPositonCode = accusedPositonCode;
	}

	@Column(name = "ACCUSED_POSITON_NAME", length = 100)
	public String getAccusedPositonName() {
		return this.accusedPositonName;
	}

	public void setAccusedPositonName(String accusedPositonName) {
		this.accusedPositonName = accusedPositonName;
	}

	@Column(name = "ACCUSED_CLASS_CODE", length = 50)
	public String getAccusedClassCode() {
		return this.accusedClassCode;
	}

	public void setAccusedClassCode(String accusedClassCode) {
		this.accusedClassCode = accusedClassCode;
	}

	@Column(name = "ACCUSED_CLASS_NAME", length = 100)
	public String getAccusedClassName() {
		return this.accusedClassName;
	}
	
	public void setAccusedClassName(String accusedClassName) {
		this.accusedClassName = accusedClassName;
	}

	@Column(name = "ACCUSED_DISCIPLINE_CODE", length = 50)
	public String getAccusedDisciplineCode() {
		return this.accusedDisciplineCode;
	}

	public void setAccusedDisciplineCode(String accusedDisciplineCode) {
		this.accusedDisciplineCode = accusedDisciplineCode;
	}

	@Column(name = "ACCUSED_DISCIPLINE_NAME", length = 100)
	public String getAccusedDisciplineName() {
		return this.accusedDisciplineName;
	}

	public void setAccusedDisciplineName(String accusedDisciplineName) {
		this.accusedDisciplineName = accusedDisciplineName;
	}


	@Column(name = "ACCUSED_LOCAL_CADRE_FLAG", length = 1)
	public String getAccusedLocalCadreFlag() {
		return this.accusedLocalCadreFlag;
	}

	public void setAccusedLocalCadreFlag(String accusedLocalCadreFlag) {
		this.accusedLocalCadreFlag = accusedLocalCadreFlag;
	}

	@Column(name = "ACCUSED_ZIP", length = 6)
	public String getAccusedZip() {
		return this.accusedZip;
	}

	public void setAccusedZip(String accusedZip) {
		this.accusedZip = accusedZip;
	}

	@Column(name = "ACCUSER_SEX_CODE", length = 50)
	public String getAccuserSexCode() {
		return this.accuserSexCode;
	}

	public void setAccuserSexCode(String accuserSexCode) {
		this.accuserSexCode = accuserSexCode;
	}

	@Column(name = "ACCUSER_SEX_NAME", length = 100)
	public String getAccuserSexName() {
		return this.accuserSexName;
	}

	public void setAccuserSexName(String accuserSexName) {
		this.accuserSexName = accuserSexName;
	}

	@Column(name = "ACCUSER_NATION_CODE", length = 50)
	public String getAccuserNationCode() {
		return this.accuserNationCode;
	}

	public void setAccuserNationCode(String accuserNationCode) {
		this.accuserNationCode = accuserNationCode;
	}

	@Column(name = "ACCUSER_NATION_NAME", length = 100)
	public String getAccuserNationName() {
		return this.accuserNationName;
	}

	public void setAccuserNationName(String accuserNationName) {
		this.accuserNationName = accuserNationName;
	}

	@Column(name = "ACCUSER_PARTY_CODE", length = 50)
	public String getAccuserPartyCode() {
		return this.accuserPartyCode;
	}

	public void setAccuserPartyCode(String accuserPartyCode) {
		this.accuserPartyCode = accuserPartyCode;
	}

	@Column(name = "ACCUSER_PARTY_NAME", length = 100)
	public String getAccuserPartyName() {
		return this.accuserPartyName;
	}

	public void setAccuserPartyName(String accuserPartyName) {
		this.accuserPartyName = accuserPartyName;
	}

	@Column(name = "ACCUSER_REGION_CODE", length = 50)
	public String getAccuserRegionCode() {
		return this.accuserRegionCode;
	}

	public void setAccuserRegionCode(String accuserRegionCode) {
		this.accuserRegionCode = accuserRegionCode;
	}

	@Column(name = "ACCUSER_REGION_NAME", length = 100)
	public String getAccuserRegionName() {
		return this.accuserRegionName;
	}

	public void setAccuserRegionName(String accuserRegionName) {
		this.accuserRegionName = accuserRegionName;
	}

	@Column(name = "ACCUSER_OCCUPATION_CODE", length = 50)
	public String getAccuserOccupationCode() {
		return this.accuserOccupationCode;
	}

	public void setAccuserOccupationCode(String accuserOccupationCode) {
		this.accuserOccupationCode = accuserOccupationCode;
	}

	@Column(name = "ACCUSER_OCCUPATION_NAME", length = 100)
	public String getAccuserOccupationName() {
		return this.accuserOccupationName;
	}

	public void setAccuserOccupationName(String accuserOccupationName) {
		this.accuserOccupationName = accuserOccupationName;
	}

	@Column(name = "ACCUSER_ZIP", length = 6)
	public String getAccuserZip() {
		return this.accuserZip;
	}

	public void setAccuserZip(String accuserZip) {
		this.accuserZip = accuserZip;
	}

	@Column(name = "ACCUSER_DEVIANCE_CODE", length = 50)
	public String getAccuserDevianceCode() {
		return this.accuserDevianceCode;
	}

	public void setAccuserDevianceCode(String accuserDevianceCode) {
		this.accuserDevianceCode = accuserDevianceCode;
	}

	@Column(name = "ACCUSER_DEVIANCE_NAME", length = 100)
	public String getAccuserDevianceName() {
		return this.accuserDevianceName;
	}

	public void setAccuserDevianceName(String accuserDevianceName) {
		this.accuserDevianceName = accuserDevianceName;
	}

	@Column(name = "EVER_ACCUSE_PLACE", length = 100)
	public String getEverAccusePlace() {
		return this.everAccusePlace;
	}

	public void setEverAccusePlace(String everAccusePlace) {
		this.everAccusePlace = everAccusePlace;
	}

	@Column(name = "EVER_DEAL_DEPT", length = 100)
	public String getEverDealDept() {
		return this.everDealDept;
	}

	public void setEverDealDept(String everDealDept) {
		this.everDealDept = everDealDept;
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

	@Column(name = "ORIGIN_DEAL_REGION", length = 50)
	public String getOriginDealRegion() {
		return this.originDealRegion;
	}

	public void setOriginDealRegion(String originDealRegion) {
		this.originDealRegion = originDealRegion;
	}

	@Column(name = "ORIGIN_SOLUTION", length = 30)
	public String getOriginSolution() {
		return this.originSolution;
	}

	public void setOriginSolution(String originSolution) {
		this.originSolution = originSolution;
	}

	@Column(name = "REMARK", length = 3000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNetSourceCode() {
		return netSourceCode;
	}

	public void setNetSourceCode(String netSourceCode) {
		this.netSourceCode = netSourceCode;
	}

	public String getNetSourceName() {
		return netSourceName;
	}

	public void setNetSourceName(String netSourceName) {
		this.netSourceName = netSourceName;
	}


	public String getAnonymousFlag() {
		return anonymousFlag;
	}

	public void setAnonymousFlag(String anonymousFlag) {
		this.anonymousFlag = anonymousFlag;
	}

	public String getAccusedDevianceFlag() {
		return accusedDevianceFlag;
	}

	public void setAccusedDevianceFlag(String accusedDevianceFlag) {
		this.accusedDevianceFlag = accusedDevianceFlag;
	}
	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getReplyFlag() {
		return replyFlag;
	}

	public void setReplyFlag(String replyFlag) {
		this.replyFlag = replyFlag;
	}
	
	public String getPetitionAcceptNo() {
		return petitionAcceptNo;
	}

	public void setPetitionAcceptNo(String petitionAcceptNo) {
		this.petitionAcceptNo = petitionAcceptNo;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionNetInfo")
	public PetitionNetContentInfo getPetitionNetContentInfo() {
		return petitionNetContentInfo;
	}

	public void setPetitionNetContentInfo(
			PetitionNetContentInfo petitionNetContentInfo) {
		this.petitionNetContentInfo = petitionNetContentInfo;
	}
}