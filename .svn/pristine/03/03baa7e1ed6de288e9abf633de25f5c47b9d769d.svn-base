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
 * 信访反映人信息表缓存类信息
 * @date 2011-11-24
 * @author sunzhe
 */
@Entity
@Table(name = "PETITION_ACCUSER_INFO_TEMP")
public class PetitionAccuserInfoTemp extends AudiEntity{
	
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
		private PetitionBasicInfoTemp petitionBasicInfoTemp;
		private String accuserAnonymousFlag;//是否署名  1、是   2、否
		private String regionCode;
		private String regionName;
		private Integer accuserNum;
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
		private String accuserCategoryCode;
		private String accuserCategoryName;
		private String everAccusePlace;
		private String everDealDept;
		private String exceptionFlag;//异常行为标识
		private String exceptionCond;//异常情况
		private String exceptionHandling;//异常处理情况
		private String tempFlag;
		private String constantAppealFlag;//老访户 1是0否
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

		@Column(name = "ACCUSER_NUM")
		public Integer getAccuserNum() {
			return this.accuserNum;
		}

		public void setAccuserNum(Integer accuserNum) {
			this.accuserNum = accuserNum;
		}

		@Column(name = "ACCUSER_TYPE_CODE", length = 50)
		public String getAccuserTypeCode() {
			return this.accuserTypeCode;
		}

		public void setAccuserTypeCode(String accuserTypeCode) {
			this.accuserTypeCode = accuserTypeCode;
		}

		@Column(name = "ACCUSER_TYPE_NAME", length = 100)
		public String getAccuserTypeName() {
			return this.accuserTypeName;
		}

		public void setAccuserTypeName(String accuserTypeName) {
			this.accuserTypeName = accuserTypeName;
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

		public Timestamp getAccuserBirthday() {
			return this.accuserBirthday;
		}

		public void setAccuserBirthday(Timestamp accuserBirthday) {
			this.accuserBirthday = accuserBirthday;
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

		@Column(name = "ACCUSER_CLASS_CODE", length = 50)
		public String getAccuserClassCode() {
			return this.accuserClassCode;
		}

		public void setAccuserClassCode(String accuserClassCode) {
			this.accuserClassCode = accuserClassCode;
		}

		@Column(name = "ACCUSER_CLASS_NAME", length = 100)
		public String getAccuserClassName() {
			return this.accuserClassName;
		}

		public void setAccuserClassName(String accuserClassName) {
			this.accuserClassName = accuserClassName;
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

		@Column(name = "ACCUSER_POSITION_CODE", length = 50)
		public String getAccuserPositionCode() {
			return this.accuserPositionCode;
		}

		public void setAccuserPositionCode(String accuserPositionCode) {
			this.accuserPositionCode = accuserPositionCode;
		}

		@Column(name = "ACCUSER_POSITION_NAME", length = 100)
		public String getAccuserPositionName() {
			return this.accuserPositionName;
		}

		public void setAccuserPositionName(String accuserPositionName) {
			this.accuserPositionName = accuserPositionName;
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
		@Column(name = "ACCUSER_REGION_CODE", length = 12)
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

		@Column(name = "ACCUSER_REGION_CODE_PROV", length = 2)
		public String getAccuserRegionCodeProv() {
			return this.accuserRegionCodeProv;
		}

		public void setAccuserRegionCodeProv(String accuserRegionCodeProv) {
			this.accuserRegionCodeProv = accuserRegionCodeProv;
		}

		@Column(name = "ACCUSER_REGION_CODE_CITY", length = 2)
		public String getAccuserRegionCodeCity() {
			return this.accuserRegionCodeCity;
		}

		public void setAccuserRegionCodeCity(String accuserRegionCodeCity) {
			this.accuserRegionCodeCity = accuserRegionCodeCity;
		}

		@Column(name = "ACCUSER_REGION_CODE_COUNTY", length = 2)
		public String getAccuserRegionCodeCounty() {
			return this.accuserRegionCodeCounty;
		}

		public void setAccuserRegionCodeCounty(String accuserRegionCodeCounty) {
			this.accuserRegionCodeCounty = accuserRegionCodeCounty;
		}

		@Column(name = "ACCUSER_REGION_CODE_TOWN", length = 2)
		public String getAccuserRegionCodeTown() {
			return this.accuserRegionCodeTown;
		}

		public void setAccuserRegionCodeTown(String accuserRegionCodeTown) {
			this.accuserRegionCodeTown = accuserRegionCodeTown;
		}

		@Column(name = "ACCUSER_REGION_CODE_STREET", length = 2)
		public String getAccuserRegionCodeStreet() {
			return this.accuserRegionCodeStreet;
		}

		public void setAccuserRegionCodeStreet(String accuserRegionCodeStreet) {
			this.accuserRegionCodeStreet = accuserRegionCodeStreet;
		}
		public String getAccuserAnonymousFlag() {
			return accuserAnonymousFlag;
		}
		public void setAccuserAnonymousFlag(String accuserAnonymousFlag) {
			this.accuserAnonymousFlag = accuserAnonymousFlag;
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
		@Column(name = "ACCUSER_CATEGORY_CODE", length = 50)
		public String getAccuserCategoryCode() {
			return this.accuserCategoryCode;
		}

		public void setAccuserCategoryCode(String accuserCategoryCode) {
			this.accuserCategoryCode = accuserCategoryCode;
		}

		@Column(name = "ACCUSER_CATEGORY_NAME", length = 100)
		public String getAccuserCategoryName() {
			return this.accuserCategoryName;
		}

		public void setAccuserCategoryName(String accuserCategoryName) {
			this.accuserCategoryName = accuserCategoryName;
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

		@Column(name = "CONSTANT_APPEAL_FLAG", length = 1)
		public String getConstantAppealFlag() {
			return this.constantAppealFlag;
		}

		public void setConstantAppealFlag(String constantAppealFlag) {
			this.constantAppealFlag = constantAppealFlag;
		}

		@Column(name = "TEMP_FLAG", length = 1)
		public String getTempFlag() {
			return this.tempFlag;
		}

		public void setTempFlag(String tempFlag) {
			this.tempFlag = tempFlag;
		}

}