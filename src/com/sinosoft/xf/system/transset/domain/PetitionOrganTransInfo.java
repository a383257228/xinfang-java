package com.sinosoft.xf.system.transset.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.AudiEntity.AudiEntity;
/**
 * 转发机构设置信息
 * @date 2011-11-09
 * @author sunzhe
 */
@Entity
@Table(name = "Petition_Organ_Trans")
public class PetitionOrganTransInfo  extends AudiEntity{
	private static final long serialVersionUID = 1L;
	
	private String organId;//关联组织机构id
	
	private String regionCode;//机构区域编码
	
	private String orgCode;//机构编码
	
	private String orgName;//机构名称
	
	private String ipAddress;//IP地址
	
	private String port;//端口号
	
	private String orgType;//转发机构类型
	
	private String version;//接收系统版本　一期:old 二期:new
	
	private String validFlag="0";//有效标识 有效0，无效1
	private String isTrans="0";//转发标识 有效0，无效1

	
	public String getIsTrans() {
		return isTrans;
	}

	public void setIsTrans(String isTrans) {
		this.isTrans = isTrans;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	
}
