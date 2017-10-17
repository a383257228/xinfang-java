/**
 * Copyright (c) sinosoft May 17 2010
 * 中科软科技股份有限公司  行业推广部
 * Create May 14 2010
 * @author wangxx
 */
package com.sinosoft.organization.domain;

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
 * 用户机构关系表
 * @author wangxx
 * 2010-05-14
 */
@Entity
@Table(name = "Organ_Person_Relation_Info")
//@Cache(region = "com.sinosoft.organization.domain.OrganPersonRelationInfo", usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrganPersonRelationInfo extends IdEntity{
	
	private static final long serialVersionUID = 1L; 
	
    //关联用户信息表对象
	@Column(name="Person_Info_ID")
    private PersonInfo personInfo;
	
    //关联机构信息表对象
	@Column(name="Organization_Info_ID")
    private OrganizationInfo organizationInfo;
	
    //用户职务
	@Column(name="User_Position")
    private String userPosition;
	
	//是否仅限本人，用于权限
	@Column(name="limit_Flag")
	private String limitFlag="0";
	
	/**
	 * @return the personInfo
	 */
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch = FetchType.LAZY)
    @JoinColumn(name="Person_Info_ID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	/**
	 * @param personInfo the personInfo to set
	 */
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	/**
	 * @return the organizationInfo
	 */
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY )
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
	 * 获取用户职务
	 * @return String 用户职务
	 */
	public String getUserPosition() {
		return userPosition;
	}

	/**
	 * 注入用户职务
	 * @param userPosition 
	 * 			用户职务
	 */
	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	public String getLimitFlag() {
		return limitFlag;
	}

	public void setLimitFlag(String limitFlag) {
		this.limitFlag = limitFlag;
	}

	
}
