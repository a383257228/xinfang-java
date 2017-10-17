package com.sinosoft.xf.pubfun.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 最大号信息
 * @date 2011-09-15
 * @author wangxx
 */
@Entity
@Table(name = "Petition_Max_No_Info")
public class PetitionMaxNoInfo extends IdEntity {
	private static final long serialVersionUID = 1L; 
	private String regionCode;//区域编码
	private String regionName;//区域名称
	private String noType;//号码类型
	private String noName;//号码名称
	private String noLimit;//号码限制条件
	private int maxNo;//当前最大值
	private String prefix;//前缀
	private String suffix;//后缀
	/**
	 * @return the noType
	 */
	public String getNoType() {
		return noType;
	}
	/**
	 * @param noType the noType to set
	 */
	public void setNoType(String noType) {
		this.noType = noType;
	}
	/**
	 * @return the noName
	 */
	public String getNoName() {
		return noName;
	}
	/**
	 * @param noName the noName to set
	 */
	public void setNoName(String noName) {
		this.noName = noName;
	}
	/**
	 * @return the noLimit
	 */
	public String getNoLimit() {
		return noLimit;
	}
	/**
	 * @param noLimit the noLimit to set
	 */
	public void setNoLimit(String noLimit) {
		this.noLimit = noLimit;
	}
	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}
	/**
	 * @param suffix the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	/**
	 * @return the maxNo
	 */
	public int getMaxNo() {
		return maxNo;
	}
	/**
	 * @param maxNo the maxNo to set
	 */
	public void setMaxNo(int maxNo) {
		this.maxNo = maxNo;
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
