/**
 * 中科软行业推广一部
 * @date 2011-09-10
 * @author Dusitn
 */
package com.sinosoft.xf.petition.accept.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 信访历年未结实体类信息
 * @date 2014-07-29
 * @author liub
 */
@Entity
@Table(name = "PETITION_NOT_KNOT")
public class PetitionNotKnot extends IdEntity{
	
	private static final long serialVersionUID = 1L;
	private String petitionNo;
	private String currPetitionNo;				//用于给用户展示的信访编号	                		CHAR(26)
	private String petitionPrtNo;
	private String regionCode;
	private String regionName;
	private String currDeptCode;
	private String currDeptName;
	

	@Column(name = "PETITION_NO")
	public String getPetitionNo() {
		return petitionNo;
	}

	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}
	@Column(name = "curr_petition_no")
	public String getCurrPetitionNo() {
		return currPetitionNo;
	}

	public void setCurrPetitionNo(String currPetitionNo) {
		this.currPetitionNo = currPetitionNo;
	}
	@Column(name = "PETITION_PRT_NO")
	public String getPetitionPrtNo() {
		return petitionPrtNo;
	}

	public void setPetitionPrtNo(String petitionPrtNo) {
		this.petitionPrtNo = petitionPrtNo;
	}
	@Column(name = "REGION_CODE")
	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	@Column(name = "REGION_NAME")
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	@Column(name = "Curr_Dept_Code")
	public String getCurrDeptCode() {
		return currDeptCode;
	}

	public void setCurrDeptCode(String currDeptCode) {
		this.currDeptCode = currDeptCode;
	}
	@Column(name = "Curr_Dept_Name")
	public String getCurrDeptName() {
		return currDeptName;
	}
	public void setCurrDeptName(String currDeptName) {
		this.currDeptName = currDeptName;
	}

}
