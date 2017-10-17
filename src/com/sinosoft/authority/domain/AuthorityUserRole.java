package com.sinosoft.authority.domain;


import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sinosoftframework.core.domain.IdEntity;

/**
 * 角色人员对应表实体
 * @author sunzhe
 * 
 * 2010-08-02
 */
@Entity
@Table(name = "Authority_User_Role")//角色人员对应表
@JsonAutoDetect
@Cache(region = "com.sinosoft.authority.domain.AuthorityUserRole", usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuthorityUserRole extends IdEntity implements java.io.Serializable{
	
    private static final long serialVersionUID = 1L;
    //人员对应角色信息
    private AuthorityRole role;
    //人员信息
    private String person;
    //权限范围
    @Column(name="Role_Scope")
    private String roleScope;
    //失效日期
    @Column(name="Expire_Date")
    private Timestamp expireDate; 
    //操作人
    @Column(name="operator")
    private String operator;
    //操作时间
    @Column(name="Operate_Date")
    private Timestamp operateDate;
    //管理区域
    @Column(name="Region_Code")
    private String regionCode;
    
    public String getRoleScope() {
		return roleScope;
	}

	public void setRoleScope(String roleScope) {
		this.roleScope = roleScope;
	}

	/**
	 * @return the AuthorityRole
	 * 定义多对一的关联关系
	 */
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY )
    @JoinColumn(name="ROLE_OID")
	public AuthorityRole getRole() {
		return role;
	}

	public void setRole(AuthorityRole role) {
		this.role = role;
	}
	
	/**
	 * @return the PersonInfo
	 * 定义一对一的关联关系
	 */
	//@OneToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY )
    //@JoinColumn(name="USER_OID")
	@Column(name="User_Oid")
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public Timestamp getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
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

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	
}
