package com.sinosoft.xf.petition.accept.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoftframework.core.domain.IdEntity;


/**
 * 网络举报接口信息bean
 * @date 2011-12-20
 * @author ljx
 */
@Entity
@Table(name = "Petition_Net_Content_Info")
public class PetitionNetContentInfo extends IdEntity {

	private static final long serialVersionUID = 1L;
	private byte[] netIssueContent;
	
	public byte[] getNetIssueContent() {
		return netIssueContent;
	}
	public void setNetIssueContent(byte[] netIssueContent) {
		this.netIssueContent = netIssueContent;
	}
	
		
//	private String regionCode;
//	private String regionName;
	private PetitionNetInfo petitionNetInfo;

//    private String petitionAbbrNo;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "PETITION_NET_INFO_OID")
	public PetitionNetInfo getPetitionNetInfo() {
		return petitionNetInfo;
	}

	public void setPetitionNetInfo(PetitionNetInfo petitionNetInfo) {
		this.petitionNetInfo = petitionNetInfo;
	}
	
//	public String getRegionCode() {
//		return regionCode;
//	}
//	public void setRegionCode(String regionCode) {
//		this.regionCode = regionCode;
//	}
//	public String getRegionName() {
//		return regionName;
//	}
//	public void setRegionName(String regionName) {
//		this.regionName = regionName;
//	}
//	public String getPetitionAbbrNo() {
//		return petitionAbbrNo;
//	}
//	public void setPetitionAbbrNo(String petitionAbbrNo) {
//		this.petitionAbbrNo = petitionAbbrNo;
//	}
}