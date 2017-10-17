package com.sinosoft.xf.petition.deal.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 调查办理记录表
 * @date 2011-12-12
 * @author Oba
 */
@Entity
@Table(name = "Petition_Deal_Log_Info")
public class PetitionDealLogInfo extends AudiEntity{

	private static final long serialVersionUID = 1L;

	private String dealNo;
	private PetitionBasicInfo petitionBasicInfo;
	private String regionCode;
	private String regionName;
	private int noteNum;
	private Timestamp noteDate;
	private String noteContent;
	private String remark;

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the petitionBasicInfo
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "PETITION_Basic_INFO_OID")
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
	 * @return the noteNum
	 */
	public int getNoteNum() {
		return noteNum;
	}
	/**
	 * @param noteNum the noteNum to set
	 */
	public void setNoteNum(int noteNum) {
		this.noteNum = noteNum;
	}
	/**
	 * @return the noteDate
	 */
	public Timestamp getNoteDate() {
		return noteDate;
	}
	/**
	 * @param noteDate the noteDate to set
	 */
	public void setNoteDate(Timestamp noteDate) {
		this.noteDate = noteDate;
	}
	/**
	 * @return the noteContent
	 */
	public String getNoteContent() {
		return noteContent;
	}
	/**
	 * @param noteContent the noteContent to set
	 */
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
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
}
