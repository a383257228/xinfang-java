package com.sinosoft.xf.petition.instruct.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;
/**
 * 信访批示信息表
 * @date 2011-11-25
 * @author Oba
 */
@Entity
@Table(name = "INSTRUCT_INFO")
public class InstructInfo extends AudiEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String petitionNo;
	private PetitionBasicInfo petitionBasicInfo;
	private String regionCode;
	private String regionName;
	private int instructNum;
	private String instructTypeCode;
	private String instructTypeName;
	private String instructClassCode;
	private String instructClassName;
	private Timestamp instructTime;
	private String leaderCode;
	private String leaderName;
	private String leaderClassCode;
	private String leaderClassName;
	private String instruction;
	private String validFlag;//0、有效，1、无效
	private String deptCode;
	private String deptName;

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
	 * @param regionCode
	 *            the regionCode to set
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
	 * @param regionName
	 *            the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * @return the instructNum
	 */
	public int getInstructNum() {
		return instructNum;
	}

	/**
	 * @param instructNum
	 *            the instructNum to set
	 */
	public void setInstructNum(int instructNum) {
		this.instructNum = instructNum;
	}

	/**
	 * @return the instructTypeCode
	 */
	public String getInstructTypeCode() {
		return instructTypeCode;
	}

	/**
	 * @param instructTypeCode
	 *            the instructTypeCode to set
	 */
	public void setInstructTypeCode(String instructTypeCode) {
		this.instructTypeCode = instructTypeCode;
	}

	/**
	 * @return the instructTypeName
	 */
	public String getInstructTypeName() {
		return instructTypeName;
	}

	/**
	 * @param instructTypeName
	 *            the instructTypeName to set
	 */
	public void setInstructTypeName(String instructTypeName) {
		this.instructTypeName = instructTypeName;
	}

	/**
	 * @return the instructClassCode
	 */
	public String getInstructClassCode() {
		return instructClassCode;
	}

	/**
	 * @param instructClassCode
	 *            the instructClassCode to set
	 */
	public void setInstructClassCode(String instructClassCode) {
		this.instructClassCode = instructClassCode;
	}

	/**
	 * @return the instructClassName
	 */
	public String getInstructClassName() {
		return instructClassName;
	}

	/**
	 * @param instructClassName
	 *            the instructClassName to set
	 */
	public void setInstructClassName(String instructClassName) {
		this.instructClassName = instructClassName;
	}

	/**
	 * @return the instructTime
	 */
	public Timestamp getInstructTime() {
		return instructTime;
	}

	/**
	 * @param instructTime
	 *            the instructTime to set
	 */
	public void setInstructTime(Timestamp instructTime) {
		this.instructTime = instructTime;
	}

	/**
	 * @return the leaderCode
	 */
	public String getLeaderCode() {
		return leaderCode;
	}

	/**
	 * @param leaderCode
	 *            the leaderCode to set
	 */
	public void setLeaderCode(String leaderCode) {
		this.leaderCode = leaderCode;
	}

	/**
	 * @return the leaderName
	 */
	public String getLeaderName() {
		return leaderName;
	}

	/**
	 * @param leaderName
	 *            the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	/**
	 * @return the leaderClassCode
	 */
	public String getLeaderClassCode() {
		return leaderClassCode;
	}

	/**
	 * @param leaderClassCode
	 *            the leaderClassCode to set
	 */
	public void setLeaderClassCode(String leaderClassCode) {
		this.leaderClassCode = leaderClassCode;
	}

	/**
	 * @return the leaderClassName
	 */
	public String getLeaderClassName() {
		return leaderClassName;
	}

	/**
	 * @param leaderClassName
	 *            the leaderClassName to set
	 */
	public void setLeaderClassName(String leaderClassName) {
		this.leaderClassName = leaderClassName;
	}

	/**
	 * @return the instruction
	 */
	public String getInstruction() {
		return instruction;
	}

	/**
	 * @param instruction
	 *            the instruction to set
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	/**
	 * @return the validFlag
	 */
	public String getValidFlag() {
		return validFlag;
	}

	/**
	 * @param validFlag
	 *            the validFlag to set
	 */
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	@Column(name = "DEPT_CODE")
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	@Column(name = "DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
