package com.sinosoft.xf.petition.end.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sinosoft.xf.petition.accept.domain.PetitionAccuserInfo;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;
/**
 * 被反映人了结信息表
 * @date 2011-12-19
 * @author Oba
 */
@Entity
@Table(name = "PETITION_Accuser_End_INFO")
public class PetitionAccuserEndInfo extends AudiEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String endNo;
	private String dealNo;
	private PetitionEndInfo petitionEndInfo;
	private String regionCode;
	private String regionName;
	private PetitionAccuserInfo petitionAccuserInfo;
	private String accuserTypeCode;
	private String accuserTypeName;
	private String accuserName;
	private String accuserNotion;
	/**
	 * @return the petitionDealInfo
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "PETITION_End_INFO_OID")
	public PetitionEndInfo getPetitionEndInfo() {
		return petitionEndInfo;
	}
	public void setPetitionEndInfo(PetitionEndInfo petitionEndInfo) {
		this.petitionEndInfo = petitionEndInfo;
	}
	/**
	 * @return the petitionAccusedInfo
	 */
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "petitionAccuserInfoOid")
	public PetitionAccuserInfo getPetitionAccuserInfo() {
		return petitionAccuserInfo;
	}
	/**
	 * @param petitionAccusedInfo the petitionAccusedInfo to set
	 */
	public void setPetitionAccuserInfo(PetitionAccuserInfo petitionAccuserInfo) {
		this.petitionAccuserInfo = petitionAccuserInfo;
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
	 * @return the dealNo
	 */
	public String getDealNo() {
		return dealNo;
	}
	/**
	 * 
	 * @return the accuserTypeCode
	 */
	public String getAccuserTypeCode() {
		return accuserTypeCode;
	}
	/**
	 * 
	 * @param accuserTypeCode
	 */
	public void setAccuserTypeCode(String accuserTypeCode) {
		this.accuserTypeCode = accuserTypeCode;
	}
	/**
	 * 
	 * @return accuserTypeName
	 */
	public String getAccuserTypeName() {
		return accuserTypeName;
	}
	/**
	 * 
	 * @param accuserTypeName
	 */
	public void setAccuserTypeName(String accuserTypeName) {
		this.accuserTypeName = accuserTypeName;
	}
	/**
	 * 
	 * @return accuserName
	 */
	public String getAccuserName() {
		return accuserName;
	}
	/**
	 * 
	 * @param accuserName
	 */
	public void setAccuserName(String accuserName) {
		this.accuserName = accuserName;
	}
	/**
	 * 
	 * @return accuserNotion
	 */
	public String getAccuserNotion() {
		return accuserNotion;
	}
	/**
	 * 
	 * @param accuserNotion
	 */
	public void setAccuserNotion(String accuserNotion) {
		this.accuserNotion = accuserNotion;
	}
	/**
	 * @param dealNo the dealNo to set
	 */
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
}
