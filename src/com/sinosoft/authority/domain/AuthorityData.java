package com.sinosoft.authority.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.sinosoftframework.core.domain.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
/**
 * 数据功能表实体
 * @author sunzhe
 * 
 * 2010-08-02
 */
@Entity
@Table(name = "Authority_Data")// 数据功能表
@JsonAutoDetect
@Cache(region = "com.sinosoft.authority.domain.AuthorityData", usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuthorityData extends IdEntity implements java.io.Serializable{
	
    private static final long serialVersionUID = 1L;

    //数据权限对应角色信息
    private AuthorityRole role;
    //数据库表信息
    private String tableInfo;
    //数据库字段对象
    private String fieldInfo;
    //是否可新增
    @Column(name="Add_Able")
    private String addAble;
    //新增时的限制值
    @Column(name="Add_Restrict_Value")
    private String addRestrictValue;
    //是否可修改
    @Column(name="Update_Able")
    private String updateAble;
    //修改时的限制值
    @Column(name="Update_Restrict_Value")
    private String updateRestrictValue;
    //是否可删除
    @Column(name="Delete_Able")
    private String deleteAble;
    //删除时的限制值
    @Column(name="Delete_Restrict_Value")
    private String deleteRestrictValue;
    //是否可查看
    @Column(name="Select_Able")
    private String selectAble;
    //查看时的限制值
    @Column(name="Select_Restrict_Value")
    private String selectRestrictValue;
    //限制类型
    @Column(name="Restrict_Type")
    private String restrictType;
    //操作人
    @Column(name="operator")
    private String operator;
    //操作时间
    @Column(name="Operate_Date")
    private Timestamp operateDate;
    //临时存放库表ＩＤ
    private String tableid;
    //临时存放字段ＩＤ
    private String fieldid;
    //临时存放码表信息
    private String codeType;
    //临时存放页面显示权限值内容
    private String addRestrict;
    
    @Transient
    public String getAddRestrict() {
		return addRestrict;
	}

	public void setAddRestrict(String addRestrict) {
		this.addRestrict = addRestrict;
	}

	@Transient
    public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	@Transient
    public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}
	
	@Transient
	public String getFieldid() {
		return fieldid;
	}

	public void setFieldid(String fieldid) {
		this.fieldid = fieldid;
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
	 * @return the DatabaseTableInfo
	 * 定义一对一的关联关系
	 */
	//@OneToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY )
    //@JoinColumn(name="TABLE_OID")
	@Column(name="Table_Oid")
	public String getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(String tableInfo) {
		this.tableInfo = tableInfo;
	}

	/**
	 * @return the DatabaseFieldInfo
	 * 定义一对一的关联关系
	 */
	//@OneToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY )
    //@JoinColumn(name="COLUMN_OID")
	@Column(name="Column_Oid")
	public String getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getAddAble() {
		return addAble;
	}

	public void setAddAble(String addAble) {
		this.addAble = addAble;
	}

	public String getAddRestrictValue() {
		return addRestrictValue;
	}

	public void setAddRestrictValue(String addRestrictValue) {
		this.addRestrictValue = addRestrictValue;
	}

	public String getUpdateAble() {
		return updateAble;
	}

	public void setUpdateAble(String updateAble) {
		this.updateAble = updateAble;
	}

	public String getUpdateRestrictValue() {
		return updateRestrictValue;
	}

	public void setUpdateRestrictValue(String updateRestrictValue) {
		this.updateRestrictValue = updateRestrictValue;
	}

	public String getDeleteAble() {
		return deleteAble;
	}

	public void setDeleteAble(String deleteAble) {
		this.deleteAble = deleteAble;
	}

	public String getDeleteRestrictValue() {
		return deleteRestrictValue;
	}

	public void setDeleteRestrictValue(String deleteRestrictValue) {
		this.deleteRestrictValue = deleteRestrictValue;
	}

	public String getSelectAble() {
		return selectAble;
	}

	public void setSelectAble(String selectAble) {
		this.selectAble = selectAble;
	}

	public String getSelectRestrictValue() {
		return selectRestrictValue;
	}

	public void setSelectRestrictValue(String selectRestrictValue) {
		this.selectRestrictValue = selectRestrictValue;
	}

	public String getRestrictType() {
		return restrictType;
	}

	public void setRestrictType(String restrictType) {
		this.restrictType = restrictType;
	}

	public Timestamp getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Timestamp operateDate) {
		this.operateDate = operateDate;
	}
	
}
