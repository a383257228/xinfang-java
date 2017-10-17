package com.sinosoft.xf.petition.petitionaccept.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * PetitionRepeatInfo entity.
 * 
 * @author ljx
 */
@Entity
@Table(name = "PETITION_REPEAT_INFO")
public class PetitionRepeatInfo  extends AudiEntity{

	private static final long serialVersionUID = 1L;

	private String regionCode;
	private String regionName;
	private String petitionNo;
	private String directPetitionNo;
	private String mainPetitionNo;
	private Integer repeatNum = 0;
	private Timestamp repeatDate;
	private Timestamp cancelDate;
	private Timestamp directPetitionDate;
	private String repeatFlag = "0";
	private String moreFlag = "0";
	private String sameDealFlag = "0";
	private String petitionClassCode;
	private String endFlag = "0";
	private String remark;
	private String currPetitionNo;				//信访编号（用于客户端显示）		CHAR(26)
	private String directCurrPetitionNo;		//重复信访编号（用于客户端显示）	CHAR(26)
	private String mainCurrPetitionNo;			//主重复信访编号（用于客户端显示）CHAR(26)
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "PETITION_NO", length = 20)
	public String getPetitionNo() {
		return this.petitionNo;
	}

	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}

	@Column(name = "DIRECT_PETITION_NO", length = 20)
	public String getDirectPetitionNo() {
		return this.directPetitionNo;
	}

	public void setDirectPetitionNo(String directPetitionNo) {
		this.directPetitionNo = directPetitionNo;
	}

	@Column(name = "MAIN_PETITION_NO", length = 20)
	public String getMainPetitionNo() {
		return this.mainPetitionNo;
	}

	public void setMainPetitionNo(String mainPetitionNo) {
		this.mainPetitionNo = mainPetitionNo;
	}

	@Column(name = "REPEAT_NUM")
	public Integer getRepeatNum() {
		return this.repeatNum;
	}

	public void setRepeatNum(Integer repeatNum) {
		this.repeatNum = repeatNum;
	}

	
	public Timestamp getRepeatDate() {
		return this.repeatDate;
	}

	public void setRepeatDate(Timestamp repeatDate) {
		this.repeatDate = repeatDate;
	}

	public Timestamp getCancelDate() {
		return this.cancelDate;
	}

	public void setCancelDate(Timestamp cancelDate) {
		this.cancelDate = cancelDate;
	}

	@Column(name = "REPEAT_FLAG", length = 1)
	public String getRepeatFlag() {
		return this.repeatFlag;
	}

	public void setRepeatFlag(String repeatFlag) {
		this.repeatFlag = repeatFlag;
	}

	@Column(name = "MORE_FLAG", length = 1)
	public String getMoreFlag() {
		return this.moreFlag;
	}

	public void setMoreFlag(String moreFlag) {
		this.moreFlag = moreFlag;
	}

	@Column(name = "SAME_DEAL_FLAG", length = 1)
	public String getSameDealFlag() {
		return this.sameDealFlag;
	}

	public void setSameDealFlag(String sameDealFlag) {
		this.sameDealFlag = sameDealFlag;
	}

	@Column(name = "PETITION_CLASS_CODE", length = 50)
	public String getPetitionClassCode() {
		return this.petitionClassCode;
	}

	public void setPetitionClassCode(String petitionClassCode) {
		this.petitionClassCode = petitionClassCode;
	}

	@Column(name = "END_FLAG", length = 1)
	public String getEndFlag() {
		return this.endFlag;
	}

	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}

	/**
	 * @return the currPetitionNo
	 */
	@Column(name = "CURR_PETITION_NO", length = 26)
	public String getCurrPetitionNo() {
		return currPetitionNo;
	}

	/**
	 * @param currPetitionNo the currPetitionNo to set
	 */
	public void setCurrPetitionNo(String currPetitionNo) {
		this.currPetitionNo = currPetitionNo;
	}

	/**
	 * @return the directCurrPetitionNo
	 */
	@Column(name = "DIRECT_CURR_PETITION_NO", length = 26)
	public String getDirectCurrPetitionNo() {
		return directCurrPetitionNo;
	}

	/**
	 * @param directCurrPetitionNo the directCurrPetitionNo to set
	 */
	public void setDirectCurrPetitionNo(String directCurrPetitionNo) {
		this.directCurrPetitionNo = directCurrPetitionNo;
	}

	/**
	 * @return the mainCurrPetitionNo
	 */
	@Column(name = "MAIN_CURR_PETITION_NO", length = 26)
	public String getMainCurrPetitionNo() {
		return mainCurrPetitionNo;
	}

	/**
	 * @param mainCurrPetitionNo the mainCurrPetitionNo to set
	 */
	public void setMainCurrPetitionNo(String mainCurrPetitionNo) {
		this.mainCurrPetitionNo = mainCurrPetitionNo;
	}

	public Timestamp getDirectPetitionDate() {
		return directPetitionDate;
	}

	public void setDirectPetitionDate(Timestamp directPetitionDate) {
		this.directPetitionDate = directPetitionDate;
	}
	
}