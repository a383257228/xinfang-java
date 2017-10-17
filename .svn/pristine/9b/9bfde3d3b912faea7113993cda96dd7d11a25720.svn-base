/**
 * Copyright (c) sinosoft May 17 2010
 * 中科软科技股份有限公司  行业推广部
 * Create May 14 2010
 * @author wangxx
 */
package com.sinosoft.organization.domain;



import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 机构合并拆分信息表
 * @author wangxx
 * 2010-05-14
 */
@Entity
@Table(name = "Organ_Merge_Split_Info")
//@Cache(region = "com.sinosoft.organization.domain.OrganMergeSplitInfo", usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrganMergeSplitInfo extends IdEntity{	
	private static final long serialVersionUID = 1L; 

    //合并前拆分后的机构ID
	@Column(name="Organ_ID")
    private String OrganId;
	
    //操作日期
	@Column(name="Operate_Date")
    private Timestamp operateDate;
	
    //操作标示   1-合并，2-拆分
	@Column(name="Operate_Flag")
    private String operateFlag;
	
	//合并后或者拆分前的机构
	private OrganizationInfo organizationInfo;
	
	//合并前的机构code拼接
	private String organCodes;
	/**
	 * @return the organizationInfo
	 */
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER )
    @JoinColumn(name="Organization_Info_ID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public OrganizationInfo getOrganizationInfo() {
		return organizationInfo;
	}

	/**
	 * @param organizationInfo the organizationInfo to set
	 */
	public void setOrganizationInfo(OrganizationInfo organizationInfo) {
		this.organizationInfo = organizationInfo;
	}
	/**
	 * @return the organId
	 */
	public String getOrganId() {
		return OrganId;
	}

	/**
	 * @param organId the organId to set
	 */
	public void setOrganId(String organId) {
		OrganId = organId;
	}

	/**
	 * @return the operateDate
	 */
	public Timestamp getOperateDate() {
		return operateDate;
	}

	/**
	 * @param operateDate the operateDate to set
	 */
	public void setOperateDate(Timestamp operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * @return the operateFlag
	 */
	public String getOperateFlag() {
		return operateFlag;
	}

	/**
	 * @param operateFlag the operateFlag to set
	 */
	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public String getOrganCodes() {
		return organCodes;
	}

	public void setOrganCodes(String organCodes) {
		this.organCodes = organCodes;
	}


   
	
	
}
