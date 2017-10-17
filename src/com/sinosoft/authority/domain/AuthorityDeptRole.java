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
 * 角色部门对应表实体
 * @author sunzhe
 * 
 * 2010-08-02
 */
@Entity
@Table(name = "Authority_Dept_Role")// 角色部门对应表
@JsonAutoDetect
@Cache(region = "com.sinosoft.authority.domain.AuthorityDeptRole", usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuthorityDeptRole extends IdEntity implements java.io.Serializable{
	
    private static final long serialVersionUID = 1L;
    //部门对应的角色
    private AuthorityRole role;
    //部门信息
    private String organization;
    //操作人
    @Column(name="operator")
    private String operator;
    //操作时间
    @Column(name="Operate_Date")
    private Timestamp operateDate;
    
    /**
	 * @return the AuthorityRole
	 * 定义多对一的关联关系
	 */
	//@OneToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} ,fetch = FetchType.LAZY)
    //@JoinColumn(name="DEPT_OID")
    @Column(name="Dept_Oid")
    public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
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
	
}
