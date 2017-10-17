package com.sinosoft.xf.document.docissue.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;
/**
 * 存储信模板基本信息实体类
 * @date 2010-06-22
 * @author wangxx
 */
@Entity
@Table(name = "Temp_Define_Info")//存储信访信息管理业务中信访公文处理过程中对于各种公文的定义信息实体类。
public class TempDefineInfo extends AudiEntity{
	
    private static final long serialVersionUID = 1L;

    private String regionCode;//当前纪检监察机构/行政区域
    private String regionName;//当前纪检监察机构/行政区域
    
    private String docTypeCode;//公文类型代码值
    
    private String docTypeName;//公文类型名称
    
    private String letterFlag;//函号/期序号标识
    
    private Timestamp startTime;//模板有效起始日期
    
    private Timestamp endTime;//模板有效终止日期
    
    private String reserve1;//预留字段1
    
    private String reserve2;//预留字段2
    
    private String reserve3;//预留字段3
    
    private String reserve4;//预留字段4
    
    private String remark;//备注信息
    
    private String tempStyle;//模板样式
    
    private TempDocInfo tempDocInfo = new TempDocInfo();

    private Set<TempDefineInfoSub> tempDefineInfoSub = new HashSet<TempDefineInfoSub>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tempDefineInfo")
	public TempDocInfo getTempDocInfo() {
		return tempDocInfo;
	}
	public void setTempDocInfo(TempDocInfo tempDocInfo) {
		this.tempDocInfo = tempDocInfo;
	}
	
	/**
	 * @return the tempDefineInfoSub
	 */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tempDefineInfo")
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
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
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
	 * @return the docTypeName
	 */
	public String getDocTypeName() {
		return docTypeName;
	}
	/**
	 * @param docTypeName the docTypeName to set
	 */
	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}
	/**
	 * @return the letterFlag
	 */
	public String getLetterFlag() {
		return letterFlag;
	}
	/**
	 * @param letterFlag the letterFlag to set
	 */
	public void setLetterFlag(String letterFlag) {
		this.letterFlag = letterFlag;
	}
	/**
	 * @return the startTime
	 */
	public Timestamp getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Timestamp getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the reserve1
	 */
	public String getReserve1() {
		return reserve1;
	}
	/**
	 * @param reserve1 the reserve1 to set
	 */
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	/**
	 * @return the reserve2
	 */
	public String getReserve2() {
		return reserve2;
	}
	/**
	 * @param reserve2 the reserve2 to set
	 */
	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}
	/**
	 * @return the reserve3
	 */
	public String getReserve3() {
		return reserve3;
	}
	/**
	 * @param reserve3 the reserve3 to set
	 */
	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}
	/**
	 * @return the reserve4
	 */
	public String getReserve4() {
		return reserve4;
	}
	/**
	 * @param reserve4 the reserve4 to set
	 */
	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the tempStyle
	 */
	public String getTempStyle() {
		return tempStyle;
	}
	/**
	 * @param tempStyle the tempStyle to set
	 */
	public void setTempStyle(String tempStyle) {
		this.tempStyle = tempStyle;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
}
