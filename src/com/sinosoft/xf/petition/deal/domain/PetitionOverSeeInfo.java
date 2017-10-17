package com.sinosoft.xf.petition.deal.domain;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;

/**
 * 督办信息表
 * @date 2014-05-26
 * @author liub
 */
@Entity
@Table(name = "PETITION_OVER_SEE_INFO")
public class PetitionOverSeeInfo extends AudiEntity{
	private static final long serialVersionUID = 1L;

	private String petitionNo;//信访编号
	private PetitionBasicInfo petitionBasicInfo;//基本表对象
	private String regionCode;//区域编码
	private String regionName;//区域名称
	private String superviseContent;//督办内容
	private String queryKeyNo;//问题线索编号
	
	public String getQueryKeyNo() {
		return queryKeyNo;
	}
	public void setQueryKeyNo(String queryKeyNo) {
		this.queryKeyNo = queryKeyNo;
	}
	public String getSuperviseContent() {
		return superviseContent;
	}
	public void setSuperviseContent(String superviseContent) {
		this.superviseContent = superviseContent;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "PETITION_BASIC_INFO_OID")
	public PetitionBasicInfo getPetitionBasicInfo() {
		return petitionBasicInfo;
	}
	public void setPetitionBasicInfo(PetitionBasicInfo petitionBasicInfo) {
		this.petitionBasicInfo = petitionBasicInfo;
	}
	public String getPetitionNo() {
		return this.petitionNo;
	}
	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}
	public String getRegionCode() {
		return this.regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionName() {
		return this.regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
}