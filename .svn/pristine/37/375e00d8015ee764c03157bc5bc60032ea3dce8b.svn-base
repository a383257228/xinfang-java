package com.sinosoft.xf.petition.accept.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;

/**
 * 信访件部门权限信息表实体类信息
 * @date 2011-11-09
 * @author wangxx
 */
@Entity
@Table(name = "PETITION_HIS_DEPT_INFO")
public class PetitionHisDeptInfo extends IdEntity {
	
	private static final long serialVersionUID = 1L;

//	private PetitionBasicInfo petitionBasicInfo;
	private String petitionNo;
	private String deptCode;
	private String deptName;
	private String validFlag;//是否有效  0 有效  1 无效


//	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
//	@JoinColumn(name = "petitionBasicInfoOid")
//	public PetitionBasicInfo getPetitionBasicInfo() {
//		return petitionBasicInfo;
//	}
//	public void setPetitionBasicInfo(PetitionBasicInfo petitionBasicInfo) {
//		this.petitionBasicInfo = petitionBasicInfo;
//	}
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
	 * @return the validFlag
	 */
	public String getValidFlag() {
		return validFlag;
	}
	/**
	 * @param validFlag the validFlag to set
	 */
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
}