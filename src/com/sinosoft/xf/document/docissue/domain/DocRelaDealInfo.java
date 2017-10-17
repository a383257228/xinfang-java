package com.sinosoft.xf.document.docissue.domain;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;

/**
 * 存储办理方式和公文模板对应信息
 * @date 2011-06-29
 * @author wangxx
 */
@Entity
@Table(name = "Doc_Rela_Deal_Info")
public class DocRelaDealInfo extends IdEntity {

	private static final long serialVersionUID = 1L;

    private String regionCode;//当前纪检监察机构/行政区域
    private String regionName;//当前纪检监察机构/行政区域
    
	private String dealTypeCode;//办理方式编码
	
	private String dealTypeName;//办理方式名称
	
	private String docTypeCode;//公文类型编码
	
	/**
	 * @return the docTypeCode
	 */
	public String getDocTypeCode() {
		return docTypeCode;
	}
	/**
	 * @param docTypeCode the docTypeCode to set
	 */
	public void setDocTypeCode(String docTypeCode) {
		this.docTypeCode = docTypeCode;
	}
	/**
	 * @return the dealTypeCode
	 */
	public String getDealTypeCode() {
		return dealTypeCode;
	}
	/**
	 * @param dealTypeCode the dealTypeCode to set
	 */
	public void setDealTypeCode(String dealTypeCode) {
		this.dealTypeCode = dealTypeCode;
	}
	/**
	 * @return the dealTypeName
	 */
	public String getDealTypeName() {
		return dealTypeName;
	}
	/**
	 * @param dealTypeName the dealTypeName to set
	 */
	public void setDealTypeName(String dealTypeName) {
		this.dealTypeName = dealTypeName;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
}
