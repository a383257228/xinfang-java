package com.sinosoft.xf.petition.end.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sinosoft.xf.petition.accept.domain.PetitionAccusedInfo;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;
/**
 * 被反映人了结信息表
 * @date 2011-12-19
 * @author Oba
 */
@Entity
@Table(name = "PETITION_Accused_End_INFO")
public class PetitionAccusedEndInfo extends AudiEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String endNo;
	private String dealNo;
	private PetitionEndInfo petitionEndInfo;
	private String regionCode;
	private String regionName;
	private PetitionAccusedInfo petitionAccusedInfo;
	private String accusedTypeCode;
	private String accusedTypeName;
	private String accusedName;
	private String accusedWorkSpace;
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
	 * @return the petitionEndInfo
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "petitionEndInfoOid")
	public PetitionEndInfo getPetitionEndInfo() {
		return petitionEndInfo;
	}
	/**
	 * @param petitionEndInfo the petitionEndInfo to set
	 */
	public void setPetitionEndInfo(PetitionEndInfo petitionEndInfo) {
		this.petitionEndInfo = petitionEndInfo;
	}
	/**
	 * @return the petitionAccusedInfo
	 */
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "petitionAccusedInfoOid")
	public PetitionAccusedInfo getPetitionAccusedInfo() {
		return petitionAccusedInfo;
	}
	/**
	 * @param petitionAccusedInfo the petitionAccusedInfo to set
	 */
	public void setPetitionAccusedInfo(PetitionAccusedInfo petitionAccusedInfo) {
		this.petitionAccusedInfo = petitionAccusedInfo;
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
	 * @return the accusedWorkSpace
	 */
	public String getAccusedWorkSpace() {
		return accusedWorkSpace;
	}
	/**
	 * @param accusedWorkSpace the accusedWorkSpace to set
	 */
	public void setAccusedWorkSpace(String accusedWorkSpace) {
		this.accusedWorkSpace = accusedWorkSpace;
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
//			this.setIssueTypeCode("");
//			this.setIssueTypeName("");
			this.setPunishContent("");
		}else if(null!=punishTypeCode&&("3".equals(punishTypeCode.trim())||"2".equals(punishTypeCode.trim()))){
			this.setPartyMeasureCode("");
			this.setPartyMeasureName("");
			this.setExecutiveMeasureCode("");
			this.setExecutiveMeasureName("");
			this.setPunishContent("");
//			this.setIssueTypeCode("");
//			this.setIssueTypeName("");
		}else{
			this.setPartyMeasureCode("");
			this.setPartyMeasureName("");
			this.setExecutiveMeasureCode("");
			this.setExecutiveMeasureName("");
//			this.setIssueTypeCode("");
//			this.setIssueTypeName("");
		}
		this.punishTypeCode = punishTypeCode;
	}
	/**
	 * @return the punishTypeName
	 */
	public String getPunishTypeName() {
		return punishTypeName;
	}
	/**
	 * @param punishTypeName the punishTypeName to set
	 */
	public void setPunishTypeName(String punishTypeName) {
		this.punishTypeName = punishTypeName;
	}
	/**
	 * @return the punishContent
	 */
	public String getPunishContent() {
		return punishContent;
	}
	/**
	 * @param punishContent the punishContent to set
	 */
	public void setPunishContent(String punishContent) {
		if(null!=this.getPunishTypeCode()&&
				("1".equals(this.getPunishTypeCode().trim())||"3".equals(this.getPunishTypeCode().trim())
				||"2".equals(this.getPunishTypeCode().trim()))){
			this.punishContent = "";
		}else{
			this.punishContent = punishContent;
		}
	}
	/**
	 * @return the lostRetrieve
	 */
	public Double getLostRetrieve() {
		return lostRetrieve;
	}
	/**
	 * @param lostRetrieve the lostRetrieve to set
	 */
	public void setLostRetrieve(Double lostRetrieve) {
		this.lostRetrieve = lostRetrieve;
	}
	/**
	 * @return the partyMeasureCode
	 */
	public String getPartyMeasureCode() {
		return partyMeasureCode;
	}
	/**
	 * @param partyMeasureCode the partyMeasureCode to set
	 */
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
	/**
	 * @return the executiveMeasureCode
	 */
	public String getExecutiveMeasureCode() {
		return executiveMeasureCode;
	}
	/**
	 * @param executiveMeasureCode the executiveMeasureCode to set
	 */
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
	/**
	 * @return the acussedOpinion
	 */
	public String getAcussedOpinion() {
		return acussedOpinion;
	}
	/**
	 * @param acussedOpinion the acussedOpinion to set
	 */
	public void setAcussedOpinion(String acussedOpinion) {
		this.acussedOpinion = acussedOpinion;
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
}
