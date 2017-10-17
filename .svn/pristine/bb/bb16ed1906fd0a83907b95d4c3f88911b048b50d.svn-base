package com.sinosoft.xf.petition.accept.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 问题类别信息
 * @author Liuchang
 * @date 2013-07-17
 */
@Entity
@Table(name = "ISSUE_TYPE_INFO")
public class IssueTypeInfo extends IdEntity{
	private static final long serialVersionUID = 1L;
	private PetitionBasicInfo petitionBasicInfo;	//反映问题信息	
	private String petitionNo;		//信访编号
	private Timestamp petitionDate; //信访日期
	private String issueTypeCode;	//问题类别编码	
	private String issueTypeName;	//问题类别名
	private String firstFlag = "0"; //主问题类别标示,1为主问题
	private String  parentIssueCode;	//父类别编码	
	
	
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
	
	public String getPetitionNo() {
		return petitionNo;
	}
	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}
	public Timestamp getPetitionDate() {
		return petitionDate;
	}
	public void setPetitionDate(Timestamp petitionDate) {
		this.petitionDate = petitionDate;
	}
	public String getIssueTypeCode() {
		return issueTypeCode;
	}
	public void setIssueTypeCode(String issueTypeCode) {
		this.issueTypeCode = issueTypeCode;
	}
	public String getIssueTypeName() {
		return issueTypeName;
	}
	public void setIssueTypeName(String issueTypeName) {
		this.issueTypeName = issueTypeName;
	}
	public String getFirstFlag() {
		return firstFlag;
	}
	public void setFirstFlag(String firstFlag) {
		this.firstFlag = firstFlag;
	}
	public String getParentIssueCode() {
		return parentIssueCode;
	}
	public void setParentIssueCode(String parentIssueCode) {
		this.parentIssueCode = parentIssueCode;
	}
	
}

