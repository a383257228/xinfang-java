package com.sinosoft.xf.document.docissue.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 存储模板信息项实体类
 * @author wangxx
 * @createDate 2011-04-19
 */
@Entity
@Table(name = "Temp_Item_Define_Info")//存储公文模板允许进行选择的信息项
public class TempItemDefineInfo extends IdEntity{
	
    private static final long serialVersionUID = 1L;

    private String regionCode;//当前纪检监察机构/行政区域
    private String regionName;//当前纪检监察机构/行政区域
    
	private String itemCode;//信息字段代码
	
	private String itemName;//信息字段名称
	
	private String itemTable;//信息字段所在表
	
	private String itemTableCname;//字段所在表的中文名
	
	private String validFlag;//是否有效 1、为有效，0为无效

	private Set<TempDefineInfoSub> tempDefineInfoSub = new HashSet<TempDefineInfoSub>();
	
	/**
	 * @return the tempDefineInfoSub
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tempItemDefineInfo")
	public Set<TempDefineInfoSub> getTempDefineInfoSub() {
		return tempDefineInfoSub;
	}

	/**
	 * @param tempDefineInfoSub the tempDefineInfoSub to set
	 */
	public void setTempDefineInfoSub(Set<TempDefineInfoSub> tempDefineInfoSub) {
		this.tempDefineInfoSub = tempDefineInfoSub;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the itemTable
	 */
	public String getItemTable() {
		return itemTable;
	}

	/**
	 * @param itemTable the itemTable to set
	 */
	public void setItemTable(String itemTable) {
		this.itemTable = itemTable;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	/**
	 * @return the itemTableCname
	 */
	public String getItemTableCname() {
		return itemTableCname;
	}

	/**
	 * @param itemTableCname the itemTableCname to set
	 */
	public void setItemTableCname(String itemTableCname) {
		this.itemTableCname = itemTableCname;
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
