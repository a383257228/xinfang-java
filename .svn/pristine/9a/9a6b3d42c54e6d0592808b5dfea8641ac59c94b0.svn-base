package com.sinosoft.organization.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 机构信息实体类
 * @author wangxx
 * 
 * 2010-06-22
 */
@Entity
@Table(name = "Organization")//机构信息表
//@Cache(region = "com.sinosoft.organization.domain.OrganizationInfo", usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrganizationInfo extends IdEntity {
	
    private static final long serialVersionUID = 1L;

	private String orgEname;//机构英文名称
	
	private String orgCname;//机构中文名称
	
	private String orgShortCname;//长英文名
	
	private String orgShortEname;//长中文名
	
	@Column(name="Description")
	private String description;//机构描述
	
	@Column(name="Create_Date")
	private Timestamp createDate;//注册日期
	
	@Column(name="Modify_Date")
	private Timestamp modifyDate;//修改日期
	
	@Column(name="Invalid_Date")
	private Timestamp invalidDate;//失效日期
	
	@Column(name="Invalid_Flag")
	private String invalidFlag;//是否失效
	
	@Column(name="Org_Code")
	private String orgCode;//内部编码

	@Column(name="Current_Organ")
	private String currentOrgan;//是否本级机构
	
	@Column(name="Purpose")
	private String purpose;//用途 1组织  2部门
	
	@Column(name="Organ_Type")
	private String organType;//机构类别 扩展机构信息时用于标识具体关联子表对象
	/**
	 * 保存平台用户ID
	 * @author ZHZ
	 * Oct 25, 2016
	 */
	private String innerCode;
	//机构关系信息
	private Set<OrganizationRelationInfo> organizationRelationInfo = new HashSet<OrganizationRelationInfo>();
	//机构人员关系信息
	private Set<OrganPersonRelationInfo> organPersonRelationInfo = new HashSet<OrganPersonRelationInfo>();
	//机构合并拆分信息
	private Set<OrganMergeSplitInfo> organMergeSplitInfo = new HashSet<OrganMergeSplitInfo>();

	/**
	 * @return the organMergeSplitInfo
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organizationInfo")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<OrganMergeSplitInfo> getOrganMergeSplitInfo() {
		return organMergeSplitInfo;
	}

	/**
	 * @param organMergeSplitInfo the organMergeSplitInfo to set
	 */
	public void setOrganMergeSplitInfo(Set<OrganMergeSplitInfo> organMergeSplitInfo) {
		this.organMergeSplitInfo = organMergeSplitInfo;
	}
	/**
	 * @return the organPersonRelationInfo
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organizationInfo")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<OrganPersonRelationInfo> getOrganPersonRelationInfo() {
		return organPersonRelationInfo;
	}

	/**
	 * @param organPersonRelationInfo the organPersonRelationInfo to set
	 */
	public void setOrganPersonRelationInfo(
			Set<OrganPersonRelationInfo> organPersonRelationInfo) {
		this.organPersonRelationInfo = organPersonRelationInfo;
	}

	/**
	 * @return the organizationRelationInfo
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organizationInfo")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<OrganizationRelationInfo> getOrganizationRelationInfo() {
		return organizationRelationInfo;
	}

	/**
	 * @param organizationRelationInfo the organizationRelationInfo to set
	 */
	public void setOrganizationRelationInfo(
			Set<OrganizationRelationInfo> organizationRelationInfo) {
		this.organizationRelationInfo = organizationRelationInfo;
	}

	
	/**
	 * @return the orgEname
	 */
	public String getOrgEname() {
		return orgEname;
	}

	/**
	 * @param orgEname the orgEname to set
	 */
	public void setOrgEname(String orgEname) {
		this.orgEname = orgEname;
	}

	/**
	 * @return the orgCname
	 */
	public String getOrgCname() {
		return orgCname;
	}

	/**
	 * @param orgCname the orgCname to set
	 */
	public void setOrgCname(String orgCname) {
		this.orgCname = orgCname;
	}

	/**
	 * @return the orgShortCname
	 */
	public String getOrgShortCname() {
		return orgShortCname;
	}

	/**
	 * @param orgShortCname the orgShortCname to set
	 */
	public void setOrgShortCname(String orgShortCname) {
		this.orgShortCname = orgShortCname;
	}

	/**
	 * @return the orgShortEname
	 */
	public String getOrgShortEname() {
		return orgShortEname;
	}

	/**
	 * @param orgShortEname the orgShortEname to set
	 */
	public void setOrgShortEname(String orgShortEname) {
		this.orgShortEname = orgShortEname;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the createDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the modifyDate
	 */
	public Timestamp getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * @return the invalidDate
	 */
	public Timestamp getInvalidDate() {
		return invalidDate;
	}

	/**
	 * @param invalidDate the invalidDate to set
	 */
	public void setInvalidDate(Timestamp invalidDate) {
		this.invalidDate = invalidDate;
	}

	/**
	 * @return the invalidFlag
	 */
	public String getInvalidFlag() {
		return invalidFlag;
	}

	/**
	 * @param invalidFlag the invalidFlag to set
	 */
	public void setInvalidFlag(String invalidFlag) {
		this.invalidFlag = invalidFlag;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the currentOrgan
	 */
	public String getCurrentOrgan() {
		return currentOrgan;
	}

	/**
	 * @param currentOrgan the currentOrgan to set
	 */
	public void setCurrentOrgan(String currentOrgan) {
		this.currentOrgan = currentOrgan;
	}

	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * @param purpose the purpose to set
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getOrganType() {
		return organType;
	}

	public void setOrganType(String organType) {
		this.organType = organType;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

}
