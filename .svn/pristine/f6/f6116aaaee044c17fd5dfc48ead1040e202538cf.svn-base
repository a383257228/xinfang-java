package com.sinosoft.xf.document.docissue.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 存储模板和信息项关联信息实体类。
 * @author wangxx
 * @createDate 2011-04-19
 */
@Entity
@Table(name = "Temp_Define_Info_Sub")//存储模板定义中信息项以及对应书签实体类。
@Cache(region = "com.sinosoft.organization.domain.TempDefineInfoSub", usage = CacheConcurrencyStrategy.READ_WRITE)
public class TempDefineInfoSub extends IdEntity{
	
    private static final long serialVersionUID = 1L;

    private String regionCode;//当前纪检监察机构/行政区域
    private String regionName;//当前纪检监察机构/行政区域
    private TempDefineInfo tempDefineInfo;//关联公文模板定义信息表OID
    
    private TempItemDefineInfo tempItemDefineInfo;//信息项对应公文模板信息项定义表OID
    
    private String Mark;//书签编号
    
	/**
	 * @return the tempDefineInfo
	 */
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="Temp_Define_Info_OID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public TempDefineInfo getTempDefineInfo() {
		return tempDefineInfo;
	}
	/**
	 * @param tempDefineInfo the tempDefineInfo to set
	 */
	public void setTempDefineInfo(TempDefineInfo tempDefineInfo) {
		this.tempDefineInfo = tempDefineInfo;
	}
	/**
	 * @return the tempItemDefineInfo
	 */
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="Temp_Item_Define_Info_OID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public TempItemDefineInfo getTempItemDefineInfo() {
		return tempItemDefineInfo;
	}
	/**
	 * @param tempItemDefineInfo the tempItemDefineInfo to set
	 */
	public void setTempItemDefineInfo(TempItemDefineInfo tempItemDefineInfo) {
		this.tempItemDefineInfo = tempItemDefineInfo;
	}
	/**
	 * @return the mark
	 */
	public String getMark() {
		return Mark;
	}
	/**
	 * @param mark the mark to set
	 */
	public void setMark(String mark) {
		Mark = mark;
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
