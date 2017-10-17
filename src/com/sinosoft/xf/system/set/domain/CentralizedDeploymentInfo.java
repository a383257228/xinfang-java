package com.sinosoft.xf.system.set.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 集中式部署信息
 * @date 2012-03-12
 * @author oba
 */
@Entity
@Table(name = "Centralized_deployment_info")
public class CentralizedDeploymentInfo extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String regionCode;//部署机构编码
	private String regionName;//部署机构名称
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
