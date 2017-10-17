package com.sinosoft.systemcode.domain;

import com.sinosoftframework.core.domain.IdEntity;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="System_Code_Node")
@JsonAutoDetect
@Cache(region="com.sinosoft.systemcode.domain.SystemCodeNodeInfo", usage=CacheConcurrencyStrategy.READ_WRITE)
public class SystemCodeNodeInfo extends IdEntity
{
  private static final long serialVersionUID = 1L;

  @Column(name="Org_Code")
  private String orgCode;

  @Column(name="Code_Type")
  private String codeType;

  @Column(name="Code")
  private String code;

  @Column(name="Name")
  private String name;

  @Column(name="Code_Property")
  private String codeProperty;

  @Column(name="Send_Able")
  private String sendAble;

  @Column(name="Render_Able")
  private String renderAble;

  @Column(name="Add_Sub_Able")
  private String addSubAble;

  @Column(name="Edit_Able")
  private String editAble;

  @Column(name="Sequence")
  private Integer sequence;

  @Column(name="operator")
  private String operator;

  @Column(name="Operate_Date")
  private Date operateDate;

  @Column(name="available")
  private String available;

  @Column(name="Parent_Id")
  private String parentId;

  @Transient
  private String parentName;
  private SystemCodeInfo systemcode;
  private String isAllowEdit;
  private String isAllowDelete;
  private String isAllowChildren;
  private String isAllowShow;
  private Set<SystemCodeNodeInfo> childNodes = new HashSet();

  public String getOrgCode() {
    return this.orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public String getCodeType() {
    return this.codeType;
  }

  public void setCodeType(String codeType) {
    this.codeType = codeType;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCodeProperty() {
    return this.codeProperty;
  }

  public void setCodeProperty(String codeProperty) {
    this.codeProperty = codeProperty;
  }

  public String getSendAble() {
    return this.sendAble;
  }

  public void setSendAble(String sendAble) {
    this.sendAble = sendAble;
  }

  public String getRenderAble() {
    return this.renderAble;
  }

  public void setRenderAble(String renderAble) {
    this.renderAble = renderAble;
  }

  public String getAddSubAble() {
    return this.addSubAble;
  }

  public void setAddSubAble(String addSubAble) {
    this.addSubAble = addSubAble;
  }

  public String getEditAble() {
    return this.editAble;
  }

  public void setEditAble(String editAble) {
    this.editAble = editAble;
  }

  public Integer getSequence() {
    return this.sequence;
  }

  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  public String getOperator() {
    return this.operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public Date getOperateDate() {
    return this.operateDate;
  }

  public void setOperateDate(Date operateDate) {
    this.operateDate = operateDate;
  }

  public String getAvailable() {
    return this.available;
  }

  public void setAvailable(String available) {
    this.available = available;
  }

  public String getParentId() {
    return this.parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE})
  @JoinColumn(name="SYSTEM_CODE_OID")
  public SystemCodeInfo getSystemcode()
  {
    return this.systemcode;
  }

  public void setSystemcode(SystemCodeInfo systemcode) {
    this.systemcode = systemcode;
  }
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="parentId")
  @Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
  public Set<SystemCodeNodeInfo> getChildNodes() {
    return this.childNodes;
  }

  public void setChildNodes(Set<SystemCodeNodeInfo> childNodes) {
    this.childNodes = childNodes;
  }
  @Transient
  public String getParentName() {
    return this.parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }

  public String getIsAllowEdit()
  {
    return this.isAllowEdit;
  }

  public void setIsAllowEdit(String isAllowEdit)
  {
    this.isAllowEdit = isAllowEdit;
  }

  public String getIsAllowDelete()
  {
    return this.isAllowDelete;
  }

  public void setIsAllowDelete(String isAllowDelete)
  {
    this.isAllowDelete = isAllowDelete;
  }

  public String getIsAllowChildren()
  {
    return this.isAllowChildren;
  }

  public void setIsAllowChildren(String isAllowChildren)
  {
    this.isAllowChildren = isAllowChildren;
  }

  public String getIsAllowShow()
  {
    return this.isAllowShow;
  }

  public void setIsAllowShow(String isAllowShow)
  {
    this.isAllowShow = isAllowShow;
  }
}
