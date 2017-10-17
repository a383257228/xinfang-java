package com.sinosoft.xf.document.docissue.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 公文和信访件关联信息实体类
 * @date 2011-06-16
 * @author wangxx
 */
@Entity
@Table(name = "Doc_Relation_Info")
public class DocRelationInfo extends IdEntity {
	private static final long serialVersionUID = 1L;
    private String regionCode;//当前纪检监察机构/行政区域
    private String regionName;//当前纪检监察机构/行政区域
	private DocInfo docInfo;//公文对象
	private String petitionNo;//信访编号
	private String currPetitionNo;//展示的信访编号
	private int docNum;//次数
	private String dealNo;//办理编号
	private String validFlag;//有效标识，1为无效，0为有效

	/**
	 * @return the docInfo
	 */
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "docInfoOid")
	public DocInfo getDocInfo() {
		return docInfo;
	}
	/**
	 * @param docInfo the docInfo to set
	 */
	public void setDocInfo(DocInfo docInfo) {
		this.docInfo = docInfo;
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
	
	public String getPetitionNo() {
		return petitionNo;
	}
	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}
	/**
	 * @return the docNum
	 */
	public int getDocNum() {
		return docNum;
	}
	/**
	 * @param docNum the docNum to set
	 */
	public void setDocNum(int docNum) {
		this.docNum = docNum;
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
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getCurrPetitionNo() {
		return currPetitionNo;
	}
	public void setCurrPetitionNo(String currPetitionNo) {
		this.currPetitionNo = currPetitionNo;
	}
}
