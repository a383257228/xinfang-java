package com.sinosoft.systemcode.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.log.common.annotation.AttLog;
import com.sinosoft.log.common.annotation.ClassLog;
import com.sinosoft.log.common.annotation.MethodLog;
import com.sinosoft.log.domain.OperationDataLog;
import com.sinosoft.log.domain.OperationLog;
import com.sinosoft.log.manager.OperationLogManager;
import com.sinosoft.systemcode.domain.SystemCodeNodeInfo;
import com.sinosoft.systemcode.manager.SystemCodeInfoManager;
import com.sinosoft.systemcode.manager.SystemCodeNodeInfoManager;
import com.sinosoftframework.core.filter.PropertyFilter;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

@Namespace("/systemcode")
@InterceptorRefs({@org.apache.struts2.convention.annotation.InterceptorRef("paramsPrepareParamsStack")})
@Results({@org.apache.struts2.convention.annotation.Result(name="reload", location="system-code-node-info.action", type="redirect")})
public class SystemCodeNodeInfoAction extends CrudExtActionSupport<SystemCodeNodeInfo, String>
{
  private static final long serialVersionUID = 1L;

  @Autowired
  SystemCodeInfoManager sysciMan;

  @Autowired
  private SystemCodeNodeInfoManager syscniMan;

  @Autowired
  private OperationLogManager operationLogManager;
  private SystemCodeNodeInfo entity = new SystemCodeNodeInfo();
  private String node;
  private String parentId;
  private String systemCodeId;
  private String codeType;
  private String code;
  private String name;

  @AttLog(description="过滤的code")
  private String exceptCode;

  protected EntityManager<SystemCodeNodeInfo, String> getEntityManager()
  {
    return this.syscniMan;
  }

  protected void prepareModel()
    throws Exception
  {
    if ((this.id != null) && (!"".equals(this.id)))
      this.entity = ((SystemCodeNodeInfo)this.syscniMan.get(this.id));
    else
      this.entity = new SystemCodeNodeInfo();
  }

  public SystemCodeNodeInfo getModel()
  {
    return this.entity;
  }

  public String getExceptCode()
  {
    return this.exceptCode;
  }

  public void setExceptCode(String exceptCode) {
    this.exceptCode = exceptCode;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getParentId() {
    return this.parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getSystemCodeId() {
    return this.systemCodeId;
  }

  public void setSystemCodeId(String systemCodeId) {
    this.systemCodeId = systemCodeId;
  }

  public String getNode() {
    return this.node;
  }

  public void setNode(String node) {
    this.node = node;
  }

  public SystemCodeNodeInfo getEntity()
  {
    return this.entity;
  }

  public void setEntity(SystemCodeNodeInfo entity)
  {
    this.entity = entity;
  }

  public void getSystemCodeNodeList()
    throws Exception
  {
    List systemCodeList = this.syscniMan.getAll();
    JsonUtils.write(systemCodeList, Struts2Utils.getResponse().getWriter(), 
      getExcludesForAll(), getDatePattern());
  }

  public String[] getExcludesForAll()
  {
    return new String[] { "hibernateLazyInitializer", "class", 
      "root", "parent", "users", "childrens", "handler", 
      "systemCodeNodeInfo", "addSubAble", "allowChildren", 
      "allowDelete", "allowEdit", "childNodes", "children", 
      "codeProperty", "draggable", "editAble", "iconCls", 
      "leaf", "operateDate", "orgCode", "parentId", "parentNode", 
      "qtip", "renderAble", "sendAble", "sequence", "operator", 
      "codeType", "theSort", "text" };
  }

  public String[] getExcludes()
  {
    return new String[] { "hibernateLazyInitializer", "class", "root", 
      "parent", "users", "childrens", "handler", "systemCodeNodeInfo", 
      "childNodes", "children", "draggable", "parentNode", "addSubAble", 
      "allowChildren", "text", "leaf", "qtip", "iconCls", "editAble", 
      "allowEdit" };
  }

  public void save()
    throws IOException
  {
    Integer oldSeq = null;

    if (getModel().getSequence() != null) {
      oldSeq = this.syscniMan.queryCodeNodeSeq(
        getModel().getCodeType(), getModel().getCode());
    }
    String orgCode = this.syscniMan.getOrgCode();
    this.syscniMan.save(getModel(), this.parentId, this.systemCodeId, this.codeType, orgCode);

    if (getModel().getSequence() != null) {
      this.syscniMan.updateCodeNodeSequence(getModel().getCodeType(), 
        getModel().getCode(), getModel().getSequence(), oldSeq);
    }
    Struts2Utils.getResponse().getWriter().print("{success:true}");
  }

  public void saveNoOrg()
    throws IOException
  {
    Integer oldSeq = null;

    if (getModel().getSequence() != null) {
      oldSeq = this.syscniMan.queryCodeNodeSeq(
        getModel().getCodeType(), getModel().getCode());
    }
    this.syscniMan.save(getModel(), this.parentId, this.systemCodeId);

    if (getModel().getSequence() != null) {
      this.syscniMan.updateCodeNodeSequence(getModel().getCodeType(), 
        getModel().getCode(), getModel().getSequence(), oldSeq);
    }
    Struts2Utils.getResponse().getWriter().print("{success:true}");
  }

  public void saveChilds()
    throws Exception
  {
    this.syscniMan.save(getModel(), this.parentId, this.systemCodeId);
    this.syscniMan.saveChilds(getModel());
    Struts2Utils.getResponse().getWriter().print("{success:true}");
  }

  public void pagedQueryNode()
    throws Exception
  {
    try
    {
      ExtjsPage page = this.syscniMan
        .pagedQueryNode(this.start, this.limit, this.sort, this.dir, this.filterTxt, 
        this.filterValue, this.startDate, this.endDate, this.dateFilter);
      JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), 
        getExcludes(), getDatePattern());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getSystemCodeList()
    throws Exception
  {
    try
    {
      List systemCodeList = this.syscniMan
        .getSystemCodeNode("codeType", this.filterValue);
      JsonUtils.write(systemCodeList, Struts2Utils.getResponse()
        .getWriter(), getExcludesForAll(), getDatePattern());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getSystemCodeListExcept()
    throws IOException, Exception
  {
    String[] except = null;
    if (StringUtils.isNotBlank(this.exceptCode))
      except = this.exceptCode.split(",");
    else {
      except = new String[0];
    }
    List systemCodeList = this.syscniMan.getSystemCodeNode(
      "codeType", this.filterValue, except);
    JsonUtils.write(systemCodeList, Struts2Utils.getResponse().getWriter(), 
      getExcludesForAll(), getDatePattern());
  }

  public void getSystemCodeListLowestLevel()
    throws Exception
  {
    List systemCodeList = this.syscniMan.getSystemCodeNodeLowestLevel(this.filterValue);
    JsonUtils.write(systemCodeList, Struts2Utils.getResponse().getWriter(), getExcludesForAll(), getDatePattern());
  }

  public void getCodeNodeList()
    throws Exception
  {
    List systemCodeList = this.syscniMan
      .getNodeByCodeId(this.filterValue);
    JsonUtils.write(systemCodeList, Struts2Utils.getResponse().getWriter(), 
      getExcludesForAll(), getDatePattern());
  }

  public void isExistCode()
    throws Exception
  {
    List filters = new ArrayList();
    PropertyFilter filter = new PropertyFilter("EQS_codeType", this.codeType);
    filters.add(filter);
    filter = new PropertyFilter("EQS_code", this.code);
    filters.add(filter);
    List list = this.syscniMan.getCodeNode(filters);
    boolean boo = false;
    if (!list.isEmpty()) {
      boo = true;
    }
    Struts2Utils.getResponse().getWriter().print(boo);
  }

  public void isExistCodeName()
    throws Exception
  {
    List filters = new ArrayList();
    PropertyFilter filter = new PropertyFilter("EQS_codeType", this.codeType);
    filters.add(filter);
    filter = new PropertyFilter("EQS_name", this.name);
    filters.add(filter);
    List list = this.syscniMan.getCodeNode(filters);
    boolean boo = false;
    if (!list.isEmpty()) {
      boo = true;
    }
    Struts2Utils.getResponse().getWriter().print(boo);
  }

  public void judgeExistCode()
    throws Exception
  {
    int pageNo = this.start / this.limit + 1;
    ExtjsPage page = null;
    Criteria criteria = this.syscniMan.createCriteria();
    String[] filterTxts = this.filterTxt.split(";");
    String[] filterValues = this.filterValue.split(";");
    criteria = criteria.add(Restrictions.eq(filterTxts[0], filterValues[0]));
    criteria = criteria.add(Restrictions.eq(filterTxts[1], filterValues[1]));
    if ((this.id != null) && (!"".equals(this.id))) {
      criteria = criteria.add(Restrictions.ne("id", this.id));
    }
    page = this.syscniMan.pagedQuery(criteria, pageNo, this.limit);
    List loadingUnitList = page.getResult();
    if (loadingUnitList.isEmpty())
      Struts2Utils.getResponse().getWriter().print(false);
    else
      Struts2Utils.getResponse().getWriter().print(true);
  }

  public void dropNodes()
    throws IOException
  {
    this.syscniMan.dropNodes(this.ids);
    Struts2Utils.getResponse().getWriter().print("{success:true}");
  }

  public void isHasChild()
    throws IOException
  {
    int size = this.entity.getChildNodes().size();
    if (size == 0)
      Struts2Utils.getResponse().getWriter().print(false);
    else
      Struts2Utils.getResponse().getWriter().print(true);
  }

  public void getParentName()
    throws Exception
  {
    Criteria criteria = this.syscniMan.createCriteria();

    criteria.add(Restrictions.like("name", this.entity.getName(), MatchMode.ANYWHERE));
    criteria.setProjection(Projections.groupProperty("systemcode"));
    List listId = criteria.list();
    JsonUtils.write(listId, Struts2Utils.getResponse().getWriter(), 
      new String[] { "hibernateLazyInitializer", "orgCode", "code", "name", "children", 
      "codeProperty", "sendAble", "renderAble", "addSubAble", "editAble", "theSort", 
      "sequence", "operator", "operateDate", "parentId", "childNodes", "allowChildren", "allowDelete", 
      "allowEdit", "systemCodeNodeInfo", "root", "qtip", "parent", "leaf", "iconCls", "draggable2" }, 
      getDatePattern());
  }
}
