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
 * 信访被反映人信息缓存类信息
 * @date 2011-11-24
 * @author sunzhe
 */
@Entity
@Table(name = "PETITION_ACCUSED_INFO_TEMP")
public class PetitionAccusedInfoTemp  extends AudiEntity{

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
		private PetitionBasicInfoTemp petitionBasicInfoTemp;
		private String regionCode;
		private String regionName;
		private Integer accusedNum;
		private String accusedTypeCode;//1：单位  2：个人
		private String accusedTypeName;
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
		private String accusedDevianceFlag;//1、是  2、否  0、不祥
		private String accusedLocalCadreFlag;//1、是  2、否  0、不祥
		private String accusedLeaderDisciplineCode;
		private String accusedLeaderDisciplineName;
		private String accusedCategoryCode;
		private String accusedCategoryName;
		private String tempFlag;			//0：未签收 1：已签收  2：回退
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

		@Column(name = "ACCUSED_NUM")
		public Integer getAccusedNum() {
			return this.accusedNum;
		}

		
		public String getAccusedDisciplineCode() {
			return accusedDisciplineCode;
		}


		public void setAccusedDisciplineCode(String accusedDisciplineCode) {
			this.accusedDisciplineCode = accusedDisciplineCode;
		}


		public String getAccusedDisciplineName() {
			return accusedDisciplineName;
		}


		public void setAccusedDisciplineName(String accusedDisciplineName) {
			this.accusedDisciplineName = accusedDisciplineName;
		}


		public void setAccusedNum(Integer accusedNum) {
			this.accusedNum = accusedNum;
		}

		@Column(name = "ACCUSED_TYPE_CODE", length = 50)
		public String getAccusedTypeCode() {
			return this.accusedTypeCode;
		}

		public void setAccusedTypeCode(String accusedTypeCode) {
			this.accusedTypeCode = accusedTypeCode;
		}

		@Column(name = "ACCUSED_TYPE_NAME", length = 100)
		public String getAccusedTypeName() {
			return this.accusedTypeName;
		}

		public void setAccusedTypeName(String accusedTypeName) {
			this.accusedTypeName = accusedTypeName;
		}

		@Column(name = "ACCUSED_SEX_CODE", length = 50)
		public String getAccusedSexCode() {
			return this.accusedSexCode;
		}

		public void setAccusedSexCode(String accusedSexCode) {
			this.accusedSexCode = accusedSexCode;
		}

		@Column(name = "ACCUSED_SEX_NAME", length = 100)
		public String getAccusedSexName() {
			return this.accusedSexName;
		}

		public void setAccusedSexName(String accusedSexName) {
			this.accusedSexName = accusedSexName;
		}

		public Timestamp getAccusedBirthday() {
			return this.accusedBirthday;
		}

		public void setAccusedBirthday(Timestamp accusedBirthday) {
			this.accusedBirthday = accusedBirthday;
		}

		@Column(name = "ACCUSED_NATION_CODE", length = 50)
		public String getAccusedNationCode() {
			return this.accusedNationCode;
		}

		public void setAccusedNationCode(String accusedNationCode) {
			this.accusedNationCode = accusedNationCode;
		}

		@Column(name = "ACCUSED_NATION_NAME", length = 100)
		public String getAccusedNationName() {
			return this.accusedNationName;
		}

		public void setAccusedNationName(String accusedNationName) {
			this.accusedNationName = accusedNationName;
		}

		@Column(name = "ACCUSED_PARTY_CODE", length = 50)
		public String getAccusedPartyCode() {
			return this.accusedPartyCode;
		}

		public void setAccusedPartyCode(String accusedPartyCode) {
			this.accusedPartyCode = accusedPartyCode;
		}

		@Column(name = "ACCUSED_PARTY_NAME", length = 100)
		public String getAccusedPartyName() {
			return this.accusedPartyName;
		}

		public void setAccusedPartyName(String accusedPartyName) {
			this.accusedPartyName = accusedPartyName;
		}

		@Column(name = "ACCUSED_NPC_MEMBER_CODE", length = 50)
		public String getAccusedNpcMemberCode() {
			return this.accusedNpcMemberCode;
		}

		public void setAccusedNpcMemberCode(String accusedNpcMemberCode) {
			this.accusedNpcMemberCode = accusedNpcMemberCode;
		}

		@Column(name = "ACCUSED_NPC_MEMBER_NAME", length = 100)
		public String getAccusedNpcMemberName() {
			return this.accusedNpcMemberName;
		}

		public void setAccusedNpcMemberName(String accusedNpcMemberName) {
			this.accusedNpcMemberName = accusedNpcMemberName;
		}

		@Column(name = "ACCUSED_CPPCC_MEMBER_CODE", length = 50)
		public String getAccusedCppccMemberCode() {
			return this.accusedCppccMemberCode;
		}

		public void setAccusedCppccMemberCode(String accusedCppccMemberCode) {
			this.accusedCppccMemberCode = accusedCppccMemberCode;
		}

		@Column(name = "ACCUSED_CPPCC_MEMBER_NAME", length = 100)
		public String getAccusedCppccMemberName() {
			return this.accusedCppccMemberName;
		}

		public void setAccusedCppccMemberName(String accusedCppccMemberName) {
			this.accusedCppccMemberName = accusedCppccMemberName;
		}

		@Column(name = "ACCUSED_PARTY_MEMBER_CODE", length = 50)
		public String getAccusedPartyMemberCode() {
			return this.accusedPartyMemberCode;
		}

		public void setAccusedPartyMemberCode(String accusedPartyMemberCode) {
			this.accusedPartyMemberCode = accusedPartyMemberCode;
		}

		@Column(name = "ACCUSED_PARTY_MEMBER_NAME", length = 100)
		public String getAccusedPartyMemberName() {
			return this.accusedPartyMemberName;
		}

		public void setAccusedPartyMemberName(String accusedPartyMemberName) {
			this.accusedPartyMemberName = accusedPartyMemberName;
		}

		@Column(name = "ACCUSED_POSITION_CODE", length = 50)
		public String getAccusedPositionCode() {
			return this.accusedPositionCode;
		}

		public void setAccusedPositionCode(String accusedPositionCode) {
			this.accusedPositionCode = accusedPositionCode;
		}

		@Column(name = "ACCUSED_POSITION_NAME", length = 100)
		public String getAccusedPositionName() {
			return this.accusedPositionName;
		}

		public void setAccusedPositionName(String accusedPositionName) {
			this.accusedPositionName = accusedPositionName;
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
		
		public String getAccusedEnterpriseTypeCode() {
			return accusedEnterpriseTypeCode;
		}


		public void setAccusedEnterpriseTypeCode(String accusedEnterpriseTypeCode) {
			this.accusedEnterpriseTypeCode = accusedEnterpriseTypeCode;
		}


		public String getAccusedEnterpriseTypeName() {
			return accusedEnterpriseTypeName;
		}


		public void setAccusedEnterpriseTypeName(String accusedEnterpriseTypeName) {
			this.accusedEnterpriseTypeName = accusedEnterpriseTypeName;
		}


		@Column(name = "ACCUSED_ZIP", length = 6)
		public String getAccusedZip() {
			return this.accusedZip;
		}

		public void setAccusedZip(String accusedZip) {
			this.accusedZip = accusedZip;
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

		@Column(name = "ACCUSED_REGION_CODE_PROV", length = 2)
		public String getAccusedRegionCodeProv() {
			return this.accusedRegionCodeProv;
		}

		public void setAccusedRegionCodeProv(String accusedRegionCodeProv) {
			this.accusedRegionCodeProv = accusedRegionCodeProv;
		}

		@Column(name = "ACCUSED_REGION_CODE_CITY", length = 2)
		public String getAccusedRegionCodeCity() {
			return this.accusedRegionCodeCity;
		}

		public void setAccusedRegionCodeCity(String accusedRegionCodeCity) {
			this.accusedRegionCodeCity = accusedRegionCodeCity;
		}

		@Column(name = "ACCUSED_REGION_CODE_COUNTY", length = 2)
		public String getAccusedRegionCodeCounty() {
			return this.accusedRegionCodeCounty;
		}

		public void setAccusedRegionCodeCounty(String accusedRegionCodeCounty) {
			this.accusedRegionCodeCounty = accusedRegionCodeCounty;
		}

		@Column(name = "ACCUSED_REGION_CODE_TOWN", length = 2)
		public String getAccusedRegionCodeTown() {
			return this.accusedRegionCodeTown;
		}

		public void setAccusedRegionCodeTown(String accusedRegionCodeTown) {
			this.accusedRegionCodeTown = accusedRegionCodeTown;
		}

		@Column(name = "ACCUSED_REGION_CODE_STREET", length = 2)
		public String getAccusedRegionCodeStreet() {
			return this.accusedRegionCodeStreet;
		}

		public void setAccusedRegionCodeStreet(String accusedRegionCodeStreet) {
			this.accusedRegionCodeStreet = accusedRegionCodeStreet;
		}

		@Column(name = "ACCUSED_DEVIANCE_FLAG", length = 1)
		public String getAccusedDevianceFlag() {
			return this.accusedDevianceFlag;
		}

		public void setAccusedDevianceFlag(String accusedDevianceFlag) {
			this.accusedDevianceFlag = accusedDevianceFlag;
		}

		@Column(name = "ACCUSED_LOCAL_CADRE_FLAG", length = 1)
		public String getAccusedLocalCadreFlag() {
			return this.accusedLocalCadreFlag;
		}

		public void setAccusedLocalCadreFlag(String accusedLocalCadreFlag) {
			this.accusedLocalCadreFlag = accusedLocalCadreFlag;
		}

		@Column(name = "ACCUSED_LEADER_DISCIPLINE_CODE", length = 50)
		public String getAccusedLeaderDisciplineCode() {
			return this.accusedLeaderDisciplineCode;
		}

		public void setAccusedLeaderDisciplineCode(
				String accusedLeaderDisciplineCode) {
			this.accusedLeaderDisciplineCode = accusedLeaderDisciplineCode;
		}

		@Column(name = "ACCUSED_LEADER_DISCIPLINE_NAME", length = 100)
		public String getAccusedLeaderDisciplineName() {
			return this.accusedLeaderDisciplineName;
		}

		public void setAccusedLeaderDisciplineName(
				String accusedLeaderDisciplineName) {
			this.accusedLeaderDisciplineName = accusedLeaderDisciplineName;
		}

		@Column(name = "ACCUSED_CATEGORY_CODE", length = 50)
		public String getAccusedCategoryCode() {
			return this.accusedCategoryCode;
		}

		public void setAccusedCategoryCode(String accusedCategoryCode) {
			this.accusedCategoryCode = accusedCategoryCode;
		}

		@Column(name = "ACCUSED_CATEGORY_NAME", length = 100)
		public String getAccusedCategoryName() {
			return this.accusedCategoryName;
		}

		public void setAccusedCategoryName(String accusedCategoryName) {
			this.accusedCategoryName = accusedCategoryName;
		}

		@Column(name = "TEMP_FLAG", length = 1)
		public String getTempFlag() {
			return this.tempFlag;
		}

		public void setTempFlag(String tempFlag) {
			this.tempFlag = tempFlag;
		}
		public String getConstantAppealFlag() {
			return constantAppealFlag;
		}
		public void setConstantAppealFlag(String constantAppealFlag) {
			this.constantAppealFlag = constantAppealFlag;
		}

}