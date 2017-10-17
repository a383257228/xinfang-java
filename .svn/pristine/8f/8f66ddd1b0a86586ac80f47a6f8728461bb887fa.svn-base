package com.sinosoft.xf.petition.end.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.xf.petition.deal.domain.PetitionDealInfo;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;
/**
 * 了结信息表
 * @date 2011-12-19
 * @author Oba
 */
@Entity
@Table(name = "PETITION_End_INFO")
public class PetitionEndInfo extends AudiEntity{

	private static final long serialVersionUID = 1L;

	private String dealNo;
	private String endNo ;
	private PetitionDealInfo petitionDealInfo;
	private String regionCode;
	private String regionName;
	private int endNum=1;
	private int procNum;
	private String validFlag;						//0:有效；1：无效
	private Timestamp endDate;
	private String endTypeCode;
	private String endTypeName;
	private String verifyCode;//初步核实代码
	private String beforeEndNotion;
	private String realityCode;
	private String realityName;
	private int dealPeopleNum;
	private Double lostRetrieve;
	private String deptCode;
	private String deptName;
	private String isHost;
	private String endClassCode;					//办结类型代码
	private String endClassName;					//办结类型代码值
	private EndReportInfo endReportInfo;
	private String endReport;
//	private Set<PetitionAccuserEndInfo> accuserEndInfoSet = new HashSet<PetitionAccuserEndInfo>();
//	private Set<PetitionAccusedEndInfo> accusedEndInfoSet = new HashSet<PetitionAccusedEndInfo>();

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionEndInfo")
//	public Set<PetitionAccuserEndInfo> getAccuserEndInfoSet() {
//		return accuserEndInfoSet;
//	}
//	public void setAccuserEndInfoSet(Set<PetitionAccuserEndInfo> accuserEndInfoSet) {
//		this.accuserEndInfoSet = accuserEndInfoSet;
//	}

//	/**
//	 * @return the accusedEndInfo
//	 */
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionEndInfo")
//	public Set<PetitionAccusedEndInfo> getAccusedEndInfoSet() {
//		return accusedEndInfoSet;
//	}
//	/**
//	 * @param accusedEndInfo the accusedEndInfo to set
//	 */
//	public void setAccusedEndInfoSet(Set<PetitionAccusedEndInfo> accusedEndInfoSet) {
//		this.accusedEndInfoSet = accusedEndInfoSet;
//	}
	/**
	 * @return the petitionEndInfo
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "endInfo")
	public EndReportInfo getEndReportInfo() {
		return endReportInfo;
	}
	public void setEndReportInfo(EndReportInfo endReportInfo) {
		this.endReportInfo = endReportInfo;
	}
	/**
	 * @return the petitionDealInfo
	 */
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "petitionDealInfoOid")
	public PetitionDealInfo getPetitionDealInfo() {
		return petitionDealInfo;
	}
	/**
	 * @param petitionDealInfo the petitionDealInfo to set
	 */
	public void setPetitionDealInfo(PetitionDealInfo petitionDealInfo) {
		this.petitionDealInfo = petitionDealInfo;
	}
	/**
	 * @return the dealNo
	 */
	public String getDealNo() {
		return dealNo;
	}
	/**
	 * @param dealNo the dealNo to set
	 */
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	/**
	 * @return the endNo
	 */
	public String getEndNo() {
		return endNo;
	}
	/**
	 * @param endNo the endNo to set
	 */
	public void setEndNo(String endNo) {
		this.endNo = endNo;
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
	 * @return the endNum
	 */
	public int getEndNum() {
		return endNum;
	}
	/**
	 * @param endNum the endNum to set
	 */
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	/**
	 * @return the procNum
	 */
	public int getProcNum() {
		return procNum;
	}
	/**
	 * @param procNum the procNum to set
	 */
	public void setProcNum(int procNum) {
		this.procNum = procNum;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	/**
	 * @return the endDate
	 */
	public Timestamp getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
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
	 * @return the beforeEndNotion
	 */
	public String getBeforeEndNotion() {
		return beforeEndNotion;
	}
	/**
	 * @param beforeEndNotion the beforeEndNotion to set
	 */
	public void setBeforeEndNotion(String beforeEndNotion) {
		this.beforeEndNotion = beforeEndNotion;
	}
	/**
	 * @return the realityCode
	 */
	public String getRealityCode() {
		return realityCode;
	}
	/**
	 * @param realityCode the realityCode to set
	 */
	public void setRealityCode(String realityCode) {
		this.realityCode = realityCode;
	}
	/**
	 * @return the realityName
	 */
	public String getRealityName() {
		return realityName;
	}
	/**
	 * @param realityName the realityName to set
	 */
	public void setRealityName(String realityName) {
		this.realityName = realityName;
	}
	/**
	 * @return the dealPeopleNum
	 */
	public int getDealPeopleNum() {
		return dealPeopleNum;
	}
	/**
	 * @param dealPeopleNum the dealPeopleNum to set
	 */
	public void setDealPeopleNum(int dealPeopleNum) {
		this.dealPeopleNum = dealPeopleNum;
	}
	public Double getLostRetrieve() {
		return lostRetrieve;
	}
	public void setLostRetrieve(Double lostRetrieve) {
		this.lostRetrieve = lostRetrieve;
	}
	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}
	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * @return the isHost
	 */
	public String getIsHost() {
		return isHost;
	}
	/**
	 * @param isHost the isHost to set
	 */
	public void setIsHost(String isHost) {
		this.isHost = isHost;
	}
	public String getEndClassCode() {
		return endClassCode;
	}
	public void setEndClassCode(String endClassCode) {
		this.endClassCode = endClassCode;
	}
	public String getEndClassName() {
		return endClassName;
	}
	public void setEndClassName(String endClassName) {
		this.endClassName = endClassName;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	@Transient
	public String getEndReport() {
		return endReport;
	}
	public void setEndReport(String endReport) {
		this.endReport = endReport;
	}
	
}
