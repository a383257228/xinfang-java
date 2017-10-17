package com.sinosoft.fullTextSearch.vo;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.xf.util.encrypt.Encrypt;



public class DataVO {
	private String oid;
	private String petitionTypeName;
	private String petitionClassName;
	private String issueContent;
	private String encryIssueContent;
	private String petitionTitle;
	private String encryPetitionTitle;
	private String petitionNo;
	private String suggest;
	private String regionCode;
	private Timestamp petitionDate;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getPetitionTypeName() {
		return petitionTypeName;
	}

	public void setPetitionTypeName(String petitionTypeName) {
		this.petitionTypeName = petitionTypeName;
	}

	public String getPetitionClassName() {
		return petitionClassName;
	}

	public void setPetitionClassName(String petitionClassName) {
		this.petitionClassName = petitionClassName;
	}

	public String getIssueContent() {
		String value = com.sinosoft.xf.util.encrypt.Encrypt.decrypt(this.encryIssueContent);
		return value==null?"无":value;
	}

	public void setIssueContent(String issueContent) {
		this.issueContent = issueContent;
	}

	public void setEncryIssueContent(String encryIssueContent) {
		this.encryIssueContent = encryIssueContent;
	}

	public String getPetitionNo() {
		return petitionNo;
	}

	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}

	public void setPetitionDate(Timestamp petitionDate) {
		this.petitionDate = petitionDate;
	}
	
	public Timestamp getPetitionDate() {
		return petitionDate;
	}

	public void setEncryPetitionTitle(String encryPetitionTitle) {
		this.encryPetitionTitle = encryPetitionTitle;
		this.petitionTitle = Encrypt.decrypt(this.encryPetitionTitle);
		this.suggest = StringUtils.isNotBlank(petitionTitle)?petitionTitle.replaceAll("\\\\s|\\，|\\【|\\】|\\[|\\]|\\<|\\>|\\|", ""):"";
	}

	public String getPetitionTitle() {
		return this.petitionTitle;
	}

	public String getSuggest() {
		return this.suggest;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
}
