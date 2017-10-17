package com.sinosoft.xf.system.set.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 系统变量信息
 * @date 2011-09-15
 * @author wangxx
 */
@Entity
@Table(name = "Petition_System_Variable_Info")
public class PetitionSystemVariableInfo  extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String regionCode;//当前机构编码
	private String sysVarCode;//系统变量代码
	private String sysVarName;//系统变量名称
	private String sysVarValue;//系统变量值
	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(final String regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * @return the sysVarCode
	 */
	public String getSysVarCode() {
		return sysVarCode;
	}
	/**
	 * @param sysVarCode the sysVarCode to set
	 */
	public void setSysVarCode(final String sysVarCode) {
		this.sysVarCode = sysVarCode;
	}
	/**
	 * @return the sysVarName
	 */
	public String getSysVarName() {
		return sysVarName;
	}
	/**
	 * @param sysVarName the sysVarName to set
	 */
	public void setSysVarName(final String sysVarName) {
		this.sysVarName = sysVarName;
	}
	/**
	 * @return the sysVarValue
	 */
	public String getSysVarValue() {
		return sysVarValue;
	}
	/**
	 * @param sysVarValue the sysVarValue to set
	 */
	public void setSysVarValue(final String sysVarValue) {
		this.sysVarValue = sysVarValue;
	}
}
