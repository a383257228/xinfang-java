package com.sinosoft.systemcode.domain;

import com.sinosoftframework.core.tree.LongTreeNode;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="System_Code")
@JsonAutoDetect
@Cache(region="com.sinosoft.systemcode.domain.SystemCodeInfo", usage=CacheConcurrencyStrategy.READ_WRITE)
public class SystemCodeInfo extends LongTreeNode<SystemCodeInfo>
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Set<SystemCodeNodeInfo> systemCodeNodeInfo = new HashSet();

  @Column(name="Org_Code")
  private String orgCode;

  @Column(name="Code_Type")
  private String codeType;

  @Column(name="Code_Type_Name")
  private String codeTypeName;

  @Column(name="Edit_Able")
  private String editAble;

  @Column(name="Code_Property")
  private String codeProperty;

  @Column(name="operator")
  private String operator;

  @Column(name="Operate_Date")
  private Date operateDate;
  private String isAllowShow;
  private String isAllowEdit;
  private String isAllowDelete;
  private String isAllowChildren;

  public String getOrgCode()
  {
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

  public String getCodeTypeName() {
    return this.codeTypeName;
  }

  public void setCodeTypeName(String codeTypeName) {
    setName(codeTypeName);
    this.codeTypeName = codeTypeName;
  }

  public String getEditAble() {
    return this.editAble;
  }

  public void setEditAble(String editAble) {
    this.editAble = editAble;
  }

  public String getCodeProperty() {
    return this.codeProperty;
  }

  public void setCodeProperty(String codeProperty) {
    this.codeProperty = codeProperty;
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
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="systemcode")
  @Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
  public Set<SystemCodeNodeInfo> getSystemCodeNodeInfo() {
    return this.systemCodeNodeInfo;
  }

  public void setSystemCodeNodeInfo(Set<SystemCodeNodeInfo> systemCodeNodeInfo) {
    this.systemCodeNodeInfo = systemCodeNodeInfo;
  }

  public void addSystemCodeNode(SystemCodeNodeInfo codeNode) {
    codeNode.setSystemcode(this);
    this.systemCodeNodeInfo.add(codeNode);
  }

  public String getIsAllowShow()
  {
    return this.isAllowShow;
  }

  public void setIsAllowShow(String isAllowShow)
  {
    this.isAllowShow = isAllowShow;
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
}