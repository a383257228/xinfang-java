package com.sinosoft.xf.petition.accept.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 网络原信息
 * @author zpj
 * @date 2013-05-21
 */
@Entity
@Table(name = "NET_CONTENT_INFO")
public class NetContentInfo extends IdEntity{
	private static final long serialVersionUID = 1L;
	private PetitionBasicInfo petitionBasicInfo;	
	private String petitionNo;		
	private String regionCode;
	private byte[] netContent;					
	
	/**
	 * @return the petitionIssueInfo
	 */
	@ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "PETITION_BASIC_INFO_OID")
	public PetitionBasicInfo getPetitionBasicInfo() {
		return petitionBasicInfo;
	}

	public String getPetitionNo() {
		return petitionNo;
	}

	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public byte[] getNetContent() {
		return netContent;
	}

	public void setNetContent(byte[] netContent) {
		this.netContent = netContent;
	}

	public void setPetitionBasicInfo(PetitionBasicInfo petitionBasicInfo) {
		this.petitionBasicInfo = petitionBasicInfo;
	}
	
	
}
