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
 * 菜单功能表实体
 * @author sunzhe
 * 
 * 2010-08-02
 */
@Entity
@Table(name = "Authority_Menu")// 菜单功能表
@JsonAutoDetect
@Cache(region = "com.sinosoft.authority.domain.AuthorityMenu", usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuthorityMenu extends IdEntity implements java.io.Serializable{
	
    private static final long serialVersionUID = 1L;
    //功能菜单角色信息
    private AuthorityRole role;
    //功能菜单项
    private String menu;
    //父菜单项
    @Column(name="Parent_Menu")
    private String parentMenu;
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
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} ,fetch = FetchType.LAZY)
    @JoinColumn(name="ROLE_OID")
	public AuthorityRole getRole() {
		return role;
	}

	public void setRole(AuthorityRole role) {
		this.role = role;
	}
	/**
	 * @return the MenuItemInfo
	 * 定义多对一的关联关系
	 */
	//@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} ,fetch = FetchType.LAZY)
    //@JoinColumn(name="MENU_OID")
	@Column(name="Menu_Oid")
	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
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

	public String getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(String parentMenu) {
		this.parentMenu = parentMenu;
	}
	
}
