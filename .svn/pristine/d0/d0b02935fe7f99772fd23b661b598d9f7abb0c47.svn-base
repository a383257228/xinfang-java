package com.sinosoft.authority.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sinosoftframework.core.domain.IdEntity;
/**
 * 人员对应菜单权限实体
 * @author sunzhe
 * 
 * 2010-08-02
 */
@Entity
@Table(name = "Authority_User_Relation")// 人员菜单对应表
@JsonAutoDetect
@Cache(region = "com.sinosoft.authority.domain.AuthorityUserRelation", usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuthorityUserRelation extends IdEntity implements java.io.Serializable{
	
    private static final long serialVersionUID = 1L;
    //人员功能菜单信息
    private String person;
    //功能菜单组信息项
    @Column(name="Menu_Group_Oid")
    private String menuGroup;
    //功能菜单项
    private String menu;
    //父菜单项
    @Column(name="Parent_Menu")
    private String parentMenu;
    @Column(name="Relation_Type")
    private String relationType;
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
	//@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY )
    //@JoinColumn(name="USER_OID")
    @Column(name="User_Oid")
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}
	/**
	 * @return the MenuItemInfo
	 * 定义多对一的关联关系
	 */
	//@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY )
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
	@Column(name="Menu_Group_Oid")
	public String getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(String menuGroup) {
		this.menuGroup = menuGroup;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	
}
