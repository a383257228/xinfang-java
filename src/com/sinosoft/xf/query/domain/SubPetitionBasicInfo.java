package com.sinosoft.xf.query.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sinosoft.xf.petition.accept.domain.IssueTypeInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionAccusedInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionAccuserInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionAssignedInfo;
import com.sinosoft.xf.petition.accept.domain.PetitionIssueInfo;
import com.sinosoft.xf.petition.deal.domain.PetitionDealInfo;
import com.sinosoft.xf.petition.instruct.domain.InstructInfo;
import com.sinosoft.xf.workflow.domain.PetitionCirculationStateInfo;
import com.sinosoft.xf.workflow.domain.PetitionCirculationStateTraceInfo;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 
 * @author hjh 2015-4-3上午11:03:16
 */
@Entity
@Table(name = "PETITION_BASIC_INFO")
public class SubPetitionBasicInfo extends AudiEntity {

	private static final long serialVersionUID = 1L;
	private Timestamp petitionDate;
	private String currPetitionNo;
	private String petitionNo;
	private String petitionPrtNo;
	private String regionCode;
	private String importantFlag = "0";
	private String petitionTypeName;
	private String petitionSourceName;

	//	
	// private String petitionSourceName;
	// private String regionName;
	// private String petitionTypeCode;//1、来信 2、来访 3、电话举报 4、网络举报 5、其他
	// private String petitionSourceCode;
	// private String petitionClassCode;
	// private String petitionClassName;
	// 延期未结件:申请过延期&requireEndDate.before(new Date())
	// private String issueRegionCode;
	// private String issueRegionName;
	// private String repeatFlag = "0";
	// private String objectClassName;
	// private String objectClassCode;
	// private String subIssueRegionCode;
	// private String subIssueRegionName;
	// 用于给用户展示的信访编号 CHAR(26)
	private Set<PetitionAccusedInfo> accusedInfoSet = new HashSet<PetitionAccusedInfo>();
	private Set<PetitionAccuserInfo> accuserInfoSet = new HashSet<PetitionAccuserInfo>();
	private Set<PetitionIssueInfo> issueInfoSet = new HashSet<PetitionIssueInfo>();
	private Set<IssueTypeInfo> issueTypeInfoSet = new HashSet<IssueTypeInfo>();
	private PetitionCirculationStateInfo circulationStateInfo;
	private Set<PetitionCirculationStateTraceInfo> circulationStateTraceInfoSet = new HashSet<PetitionCirculationStateTraceInfo>();
	private Set<InstructInfo> instructInfoSet = new HashSet<InstructInfo>();
	private Set<PetitionDealInfo> dealInfoSet = new HashSet<PetitionDealInfo>();
	// private Set<PetitionMultiMediaInfo> multiMediaInfoSet = new
	// HashSet<PetitionMultiMediaInfo>();
	// private Set<PetitionDealLogInfo> dealLogInfo = new
	// HashSet<PetitionDealLogInfo>();
	// private Set<NetContentInfo> netContentInfoSet = new
	// HashSet<NetContentInfo>();
	private Set<PetitionAssignedInfo> assignedInfoSet = new HashSet<PetitionAssignedInfo>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionAssignedInfo> getAssignedInfoSet() {
		return assignedInfoSet;
	}

	public void setAssignedInfoSet(Set<PetitionAssignedInfo> assignedInfoSet) {
		this.assignedInfoSet = assignedInfoSet;
	}

	/**
	 * @return the PetitionDealInfo
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionDealInfo> getDealInfoSet() {
		return dealInfoSet;
	}

	/**
	 * @param PetitionDealInfo
	 *            the PetitionDealInfo to set
	 */
	public void setDealInfoSet(Set<PetitionDealInfo> dealInfoSet) {
		this.dealInfoSet = dealInfoSet;
	}

	/**
	 * @return the instructInfoSet
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<InstructInfo> getInstructInfoSet() {
		return instructInfoSet;
	}

	/**
	 * @param instructInfoSet
	 *            the instructInfoSet to set
	 */
	public void setInstructInfoSet(Set<InstructInfo> instructInfoSet) {
		this.instructInfoSet = instructInfoSet;
	}

	/**
	 * @return the circulationStateTraceInfoSet
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionCirculationStateTraceInfo> getCirculationStateTraceInfoSet() {
		return circulationStateTraceInfoSet;
	}

	/**
	 * @param circulationStateTraceInfoSet
	 *            the circulationStateTraceInfoSet to set
	 */
	public void setCirculationStateTraceInfoSet(
			Set<PetitionCirculationStateTraceInfo> circulationStateTraceInfoSet) {
		this.circulationStateTraceInfoSet = circulationStateTraceInfoSet;
	}

	/**
	 * @return the circulationStateInfoSet
	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "petitionBasicInfo")
	public PetitionCirculationStateInfo getCirculationStateInfo() {
		return circulationStateInfo;
	}

	/**
	 * @param circulationStateInfoSet
	 *            the circulationStateInfoSet to set
	 */
	public void setCirculationStateInfo(
			PetitionCirculationStateInfo circulationStateInfo) {
		this.circulationStateInfo = circulationStateInfo;
	}

	/**
	 * @return the accusedInfoSet
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionAccusedInfo> getAccusedInfoSet() {
		return accusedInfoSet;
	}

	/**
	 * @param accusedInfoSet
	 *            the accusedInfoSet to set
	 */
	public void setAccusedInfoSet(Set<PetitionAccusedInfo> accusedInfoSet) {
		this.accusedInfoSet = accusedInfoSet;
	}

	/**
	 * @return the accuserInfoSet
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionAccuserInfo> getAccuserInfoSet() {
		return accuserInfoSet;
	}

	/**
	 * @param accuserInfoSet
	 *            the accuserInfoSet to set
	 */
	public void setAccuserInfoSet(Set<PetitionAccuserInfo> accuserInfoSet) {
		this.accuserInfoSet = accuserInfoSet;
	}

	/**
	 * @return the issueInfoSet
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<PetitionIssueInfo> getIssueInfoSet() {
		return issueInfoSet;
	}

	/**
	 * @param issueInfoSet
	 *            the issueInfoSet to set
	 */
	public void setIssueInfoSet(Set<PetitionIssueInfo> issueInfoSet) {
		this.issueInfoSet = issueInfoSet;
	}

	/**
	 * @return the issueTypeInfoSet
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "petitionBasicInfo")
	public Set<IssueTypeInfo> getIssueTypeInfoSet() {
		return issueTypeInfoSet;
	}

	/**
	 * @param issueTypeInfoSet
	 *            the issueTypeInfoSet to set
	 */
	public void setIssueTypeInfoSet(Set<IssueTypeInfo> issueTypeInfoSet) {
		this.issueTypeInfoSet = issueTypeInfoSet;
	}

	/**
	 * @return the petitionNo
	 */
	public String getPetitionNo() {
		return petitionNo;
	}

	/**
	 * @param petitionNo
	 *            the petitionNo to set
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
	 * @param regionCode
	 *            the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * @return the regionName
	 */
	public String getCurrPetitionNo() {
		return currPetitionNo;
	}

	public void setCurrPetitionNo(String currPetitionNo) {
		this.currPetitionNo = currPetitionNo;
	}

	public Timestamp getPetitionDate() {
		return petitionDate;
	}

	public void setPetitionDate(Timestamp petitionDate) {
		this.petitionDate = petitionDate;
	}

	public String getPetitionPrtNo() {
		return petitionPrtNo;
	}

	public void setPetitionPrtNo(String petitionPrtNo) {
		this.petitionPrtNo = petitionPrtNo;
	}

	public String getImportantFlag() {
		return importantFlag;
	}

	public void setImportantFlag(String importantFlag) {
		this.importantFlag = importantFlag;
	}

	public String getPetitionTypeName() {
		return petitionTypeName;
	}

	public void setPetitionTypeName(String petitionTypeName) {
		this.petitionTypeName = petitionTypeName;
	}

	public String getPetitionSourceName() {
		return petitionSourceName;
	}

	public void setPetitionSourceName(String petitionSourceName) {
		this.petitionSourceName = petitionSourceName;
	}

}
