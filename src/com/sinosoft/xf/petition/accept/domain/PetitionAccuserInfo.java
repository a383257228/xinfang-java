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
 * 信访反映人信息表实体类信息
 * @date 2011-11-09
 * @author wangxx
 */
@Entity
@Table(name = "PETITION_ACCUSER_INFO")
public class PetitionAccuserInfo extends AudiEntity{
	
	private static final long serialVersionUID = 1L;

	
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
	@Column(name = "ACCUSER_WORK_UNIT")
	public String getEncryaccuserWorkUnit() {
		return encryaccuserWorkUnit;
	}
	public void setEncryaccuserWorkUnit(String accuserWorkUnit) {
		this.encryaccuserWorkUnit = accuserWorkUnit;
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
	
	
	private String accuserTelTwo;
	private String encryaccuserTelTwo;
	@Transient
	public String getAccuserTelTwo() {
		accuserTelTwo = Encrypt.decrypt(encryaccuserTelTwo);
		return accuserTelTwo;
	}
	public void setAccuserTelTwo(String accuserTelTwo) {
		this.accuserTelTwo = accuserTelTwo;
		this.encryaccuserTelTwo = Encrypt.encrypt(accuserTelTwo);
	}
	@Column(name = "ACCUSER_TEL_TWO")
	public String getEncryaccuserTelTwo() {
		return encryaccuserTelTwo;
	}
	public void setEncryaccuserTelTwo(String accuserTelTwo) {
		this.encryaccuserTelTwo = accuserTelTwo;
	}
	
	
	private String accuserTelThree;
	private String encryaccuserTelThree;
	@Transient
	public String getAccuserTelThree() {
		accuserTelThree = Encrypt.decrypt(encryaccuserTelThree);
		return accuserTelThree;
	}
	public void setAccuserTelThree(String accuserTelThree) {
		this.accuserTelThree = accuserTelThree;
		this.encryaccuserTelThree = Encrypt.encrypt(accuserTelThree);
	}
	@Column(name = "ACCUSER_TEL_THREE")
	public String getEncryaccuserTelThree() {
		return encryaccuserTelThree;
	}
	public void setEncryaccuserTelThree(String accuserTelThree) {
		this.encryaccuserTelThree = accuserTelThree;
	}
	
	
	
	private String petitionNo;
	private PetitionBasicInfo petitionBasicInfo;
	private String accuserAnonymousFlag="2";//是否署名  1、是   2、否
	private String regionCode;
	private String regionName;
	private int accuserNum;
	private String accuserTypeCode;
	private String accuserTypeName;
	private String accuserSexCode;
	private String accuserSexName;
	private Timestamp accuserBirthday;
	private String accuserNationCode;
	private String accuserNationName;
	private String accuserClassCode;
	private String accuserClassName;
	private String accuserPartyCode;
	private String accuserPartyName;
	private String accuserPositionCode;
	private String accuserPositionName;
	private String accuserOccupationCode;
	private String accuserOccupationName;
	private String accuserZip;
	private String accuserRegionCode;
	private String accuserRegionName;
	private String accuserRegionCodeProv;
	private String accuserRegionCodeCity;
	private String accuserRegionCodeCounty;
	private String accuserRegionCodeTown;
	private String accuserRegionCodeStreet;
	private String exceptionFlag;//异常行为标识
	private String exceptionCond;//异常情况
	private String exceptionHandling;//异常处理情况
	private String accuserCategoryCode;
	private String accuserCategoryName;
	private String everAccusePlace;
	private String everDealDept;
	private String constantAppealFlag;//老访户 1是0否
	private String accuserNotion;
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
	
	public void setAccuserPartyName(String accuserPartyName) {
		this.accuserPartyName = accuserPartyName;
	}
	/**
	 * @return the accuserPositionCode
	 */
	public String getAccuserPositionCode() {
		return accuserPositionCode;
	}
	/**
	 * @param accuserPositionCode the accuserPositionCode to set
	 */
	public void setAccuserPositionCode(String accuserPositionCode) {
		this.accuserPositionCode = accuserPositionCode;
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
	
	public String getExceptionFlag() {
		return exceptionFlag;
	}
	public void setExceptionFlag(String exceptionFlag) {
		this.exceptionFlag = exceptionFlag;
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
	 * @return the constantAppealFlag
	 */
	public String getConstantAppealFlag() {
		return constantAppealFlag;
	}
	/**
	 * @param constantAppealFlag the constantAppealFlag to set
	 */
	public void setConstantAppealFlag(String constantAppealFlag) {
		this.constantAppealFlag = constantAppealFlag;
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
	public String getAccuserNotion() {
		return accuserNotion;
	}
	public void setAccuserNotion(String accuserNotion) {
		this.accuserNotion = accuserNotion;
	}
	
}