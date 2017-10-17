package com.sinosoft.xf.petition.accept.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoftframework.core.domain.IdEntity;

/**
 * 问题描述信息TEMP
 * @author xuyi
 * @date 2013-04-07
 */
@Entity
@Table(name = "ISSUE_CONTENT_INFO_TEMP")
public class IssueContentInfoTemp extends IdEntity{
	private static final long serialVersionUID = 1L;
	private PetitionIssueInfoTemp petitionIssueInfoTemp;	//	反映问题信息		CHAR（20）
	private String petitionNo;								//信访编号			CHAR（26）
	private int issueNum;                       			//问题描述类型		int
	private String issueContent;							//问题描述
	private byte[] encryIssueContent;						//问题描述
	
	/**
	 * @return the petitionIssueInfo
	 */
	@ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "PETITION_ISSUE_INFO_TEMP_OID")
	public PetitionIssueInfoTemp getPetitionIssueInfoTemp() {
		return petitionIssueInfoTemp;
	}
	/**
	 * @param petitionIssueInfo the petitionIssueInfo to set
	 */
	public void setPetitionIssueInfoTemp(PetitionIssueInfoTemp petitionIssueInfoTemp) {
		this.petitionIssueInfoTemp = petitionIssueInfoTemp;
	}
	/**
	 * @return the issueContent
	 */
	@Transient
	public String getIssueContent() {
		this.issueContent = Encrypt.decrypt(new String(encryIssueContent));
		return issueContent;
	}
	/**
	 * @param issueContent the issueContent to set
	 */
	public void setIssueContent(String issueContent) {
		this.issueContent = issueContent;
		this.encryIssueContent = (Encrypt.encrypt(issueContent)).getBytes();
	}
	/**
	 * @return the encryIssueContent
	 */
	@Column(name = "ISSUE_CONTENT")
	public byte[] getEncryIssueContent() {
		return encryIssueContent;
	}
	/**
	 * @param encryIssueContent the encryIssueContent to set
	 */
	public void setEncryIssueContent(byte[] encryIssueContent) {
		this.encryIssueContent = encryIssueContent;
	}
	/**
	 * @return the issueNum
	 */
	public int getIssueNum() {
		return issueNum;
	}
	/**
	 * @param issueNum the issueNum to set
	 */
	public void setIssueNum(int issueNum) {
		this.issueNum = issueNum;
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
	
}
