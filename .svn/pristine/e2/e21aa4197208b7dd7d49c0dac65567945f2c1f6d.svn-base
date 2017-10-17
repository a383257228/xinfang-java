package com.sinosoft.xf.petition.accept.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 关键字存储表
 * @author oba
 */
@Entity
@Table(name = "Key_Word_Info")
public class KeyWordInfo extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String petitionNo;//	全国信访编码	CHAR（18）
	private PetitionIssueInfo petitionIssueInfo;//	反映问题信息		CHAR（20）
	private String keyWordCode;//	反映问题关键字代码		VARCHAR（50）
	private String keyWordName;//	反映问题关键字代码值		VARCHAR（100）
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "PETITION_ISSUE_INFO_OID")
	public PetitionIssueInfo getPetitionIssueInfo() {
		return petitionIssueInfo;
	}
	public void setPetitionIssueInfo(PetitionIssueInfo petitionIssueInfo) {
		this.petitionIssueInfo = petitionIssueInfo;
	}
	public String getPetitionNo() {
		return petitionNo;
	}
	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}
	public String getKeyWordCode() {
		return keyWordCode;
	}
	public void setKeyWordCode(String keyWordCode) {
		this.keyWordCode = keyWordCode;
	}
	public String getKeyWordName() {
		return keyWordName;
	}
	public void setKeyWordName(String keyWordName) {
		this.keyWordName = keyWordName;
	}

}
