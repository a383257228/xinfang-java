package com.sinosoft.authority.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 权限角色表实体
 * alter by lixl 2014-06-19
 * 添加regionCode 、regionName字段
 * @author sunzhe
 * 
 * 2010-08-02
 */
@Entity
@Table(name = "Authority_Role")// 权限角色表
@JsonAutoDetect
@Cache(region = "com.sinosoft.authority.domain.AuthorityRole", usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuthorityRole extends IdEntity implements java.io.Serializable{
	
    private static final long serialVersionUID = 1L;
    //角色编码
    @Column(name="Role_Code")
    private String roleCode;
    //角色名称
    @Column(name="Role_Name")
    private String roleName;
    //失效日期
    @Column(name="Expire_Date")
    private Timestamp expireDate;    
    //角色描述
    @Column(name="Description")
    private String description;
    //是否有效
    @Column(name="Invalid_Flag")
    private String invalidFlag;
    //操作人
    @Column(name="Operator")
    private String operator;
    //操作时间
    @Column(name="Operate_Date")
    private Timestamp operateDate;
    //角色访问首页
    @Column(name="Visit_Url")
    private String visitUrl;
    //角色来源
    private String roleResource;
    //所对应系统
    private String system;
    //行政区划代码 add by lixl
   
    private String regionCode;
    //行政区划名称 add by lixl
   // @Column(name="REGION_NAME")
    private String regionName;
    
    //角色下人员
    private Set<AuthorityUserRole> rolePerson = new HashSet<AuthorityUserRole>();
    //角色下部门
    private Set<AuthorityDeptRole> roleDept = new HashSet<AuthorityDeptRole>();
    
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "role")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public Set<AuthorityDeptRole> getRoleDept() {
		return roleDept;
	}

	public void setRoleDept(Set<AuthorityDeptRole> roleDept) {
		this.roleDept = roleDept;
	}

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "role")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<AuthorityUserRole> getRolePerson() {
		return rolePerson;
	}

	public void setRolePerson(Set<AuthorityUserRole> rolePerson) {
		this.rolePerson = rolePerson;
	}

	private Set<AuthorityMenu> roleMenu = new HashSet<AuthorityMenu>();
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "role")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<AuthorityMenu> getRoleMenu() {
		return roleMenu;
	}

	public void setRoleMenu(Set<AuthorityMenu> roleMenu) {
		this.roleMenu = roleMenu;
	}

	/**
	 * @return the SystemInfo
	 * 定义多对一的关联关系
	 */
	//@OneToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY )
    //@JoinColumn(name="App_System")
	@Column(name="App_System")
	public String getSystem() {
		return system;
	}
	@Transient
	public String getRoleResource() {
		return roleResource;
	}

	public void setRoleResource(String roleResource) {
		this.roleResource = roleResource;
	}

	public String getInvalidFlag() {
		return invalidFlag;
	}

	public void setInvalidFlag(String invalidFlag) {
		this.invalidFlag = invalidFlag;
	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Timestamp getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Timestamp operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * @return the regionCode
	 */
	 @Column(name="REGION_CODE")
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
	 * @return the regionName
	 */
	 @Column(name="REGION_NAME")
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
    
}
