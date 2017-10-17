/**
 * Copyright (c) sinosoft May 17 2010
 * 中科软科技股份有限公司  行业推广部
 * Create May 14 2010
 * @author wangxx
 */
package com.sinosoft.organization.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.sinosoftframework.core.domain.IdEntity;

/**
 * 机构关系实体类
 * @author wangxx
 * 2010-06-22
 */
@Entity
@Table(name = "Organization_Relation")
//@Cache(region = "com.sinosoft.organization.domain.OrganizationRelationInfo", usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrganizationRelationInfo extends IdEntity implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L; 
	
    //机构对象
    private OrganizationInfo organizationInfo;

    //父机构对象
    private OrganizationRelationInfo organizationRelationInfo;
    //下级机构关系信息
	private Set<OrganizationRelationInfo> childrens =  new LinkedHashSet<OrganizationRelationInfo>(0);
	
	@Column(name="relation")
	private String relation;//与上级机构的关系
	
	private int organOrder;//机构排序

	/**
	 * @return the organOrder
	 */
	public int getOrganOrder() {
		return organOrder;
	}

	/**
	 * @param organOrder the organOrder to set
	 */
	public void setOrganOrder(int organOrder) {
		this.organOrder = organOrder;
	}

	/**
	 * @return the relation
	 */
	public String getRelation() {
		return relation;
	}

	/**
	 * @param relation the relation to set
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}

	/**
	 * 获取父机构对象
	 * @return OrganRelationInfo 父机构对象
	 */
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "Parent_Org_ID")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@NotFound(action=NotFoundAction.IGNORE) // 找不到数据不抛异常
	public OrganizationRelationInfo getOrganizationRelationInfo() {
		return organizationRelationInfo;
	}

	/**
	 * @param organRelationInfo 
	 * 			父机构对象
	 */
	public void setOrganizationRelationInfo(OrganizationRelationInfo organizationRelationInfo) {
		//setParent(organizationRelationInfo);
		this.organizationRelationInfo = organizationRelationInfo;
	}

	/**
	 * @return 返回所有子机构
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organizationRelationInfo")
	public Set<OrganizationRelationInfo> getChildrens() {
		return childrens;
	}

	/**
	 * 
	 * @param childrens 
	 * 			机构子节点
	 */
	public void setChildrens(Set<OrganizationRelationInfo> childrens) {
		this.childrens = childrens;
	}

	/**
	 * @return the organizationInfo
	 * 定义多对一的关联关系
	 */
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="Organization_ID")
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
}
