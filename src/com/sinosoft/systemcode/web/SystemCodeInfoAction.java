package com.sinosoft.systemcode.web;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.log.common.annotation.AttLog;
import com.sinosoft.log.common.annotation.ClassLog;
import com.sinosoft.log.common.annotation.MethodLog;
import com.sinosoft.systemcode.domain.SystemCodeInfo;
import com.sinosoft.systemcode.manager.SystemCodeInfoManager;
import com.sinosoft.systemcode.manager.SystemCodeNodeInfoManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.manager.exception.ServiceException;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

@Namespace("/systemcode")
@InterceptorRefs({@org.apache.struts2.convention.annotation.InterceptorRef("paramsPrepareParamsStack")})
@Results({@org.apache.struts2.convention.annotation.Result(name="reload", location="system-code-info.action", type="redirect")})
public class SystemCodeInfoAction extends CrudExtActionSupport<SystemCodeInfo, String>
{
  private static final long serialVersionUID = 1L;

  @Autowired
  private SystemCodeInfoManager sysciMan;

  @Autowired
  private SystemCodeNodeInfoManager syscniMan;
  private SystemCodeInfo entity = new SystemCodeInfo();
  private String node;
  private String sendUrl;
  private String codeType;
  private String singleSelect;

  @AttLog(description="代码类别名称")
  private String codeTypeName;
  private String isString;
  private String available;

  protected EntityManager<SystemCodeInfo, String> getEntityManager()
  {
    return this.sysciMan;
  }

  protected void prepareModel()
  {
    if ((this.id != null) && (!"".equals(this.id)))
      this.entity = ((SystemCodeInfo)this.sysciMan.get(this.id));
    else
      this.entity = new SystemCodeInfo();
  }

  public SystemCodeInfo getModel()
  {
    return this.entity;
  }

  public SystemCodeInfo getEntity()
  {
    return this.entity;
  }

  public void setEntity(SystemCodeInfo entity)
  {
    this.entity = entity;
  }

  public String getAvailable()
  {
    return this.available;
  }

  public void setAvailable(String available)
  {
    this.available = available;
  }

  public String getIsString() {
    return this.isString;
  }

  public void setIsString(String isString) {
    this.isString = isString;
  }

  public String getCodeTypeName() {
    return this.codeTypeName;
  }

  public void setCodeTypeName(String codeTypeName) {
    this.codeTypeName = codeTypeName;
  }

  public String getSingleSelect() {
    return this.singleSelect;
  }

  public void setSingleSelect(String singleSelect) {
    this.singleSelect = singleSelect;
  }

  public String getCodeType() {
    return this.codeType;
  }

  public void setCodeType(String codeType) {
    this.codeType = codeType;
  }

  public String getSendUrl() {
    return this.sendUrl;
  }

  public void setSendUrl(String sendUrl) {
    this.sendUrl = sendUrl;
  }

  public String getNode() {
    return this.node;
  }

  public void setNode(String node) {
    this.node = node;
  }

  public void getSystemCodeList()
    throws IOException
  {
    try
    {
      String treejson = this.sysciMan.buildCodeTree(this.node);
      Struts2Utils.getResponse().getWriter().print(treejson);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getSystemCodeListByType()
    throws IOException
  {
    String treejson = this.sysciMan.buildCodeTreeByType(
      this.node, this.codeType, Boolean.valueOf(this.singleSelect).booleanValue(), Boolean.valueOf(this.available).booleanValue());
    Struts2Utils.getResponse().getWriter().print(treejson);
  }

  public void getSystemCodeListByCodeType()
    throws IOException
  {
    String treejson = this.sysciMan.buildCodeTreeByCodeType(this.node, this.codeType, Boolean.valueOf(this.singleSelect).booleanValue());
    Struts2Utils.getResponse().getWriter().print(treejson);
  }

  public void getSystemCodeListByTypeName()
    throws IOException
  {
    String[] typeName = null;
    if (StringUtils.isNotBlank(this.codeTypeName))
      typeName = this.codeTypeName.split(",");
    else {
      typeName = new String[0];
    }
    String treejson = this.sysciMan.buildCodeTreeByTypeName(
      this.node, typeName, Boolean.valueOf(this.singleSelect).booleanValue(), 
      Boolean.valueOf(this.isString).booleanValue());
    Struts2Utils.getResponse().getWriter().print(treejson);
  }

  public void isCodeType()
    throws IOException
  {
    int info = this.sysciMan.findCodeType(this.node);
    boolean isCodeType = false;
    if (info > 0) {
      isCodeType = true;
    }
    Struts2Utils.getResponse().getWriter().print(isCodeType);
  }

  public void existCodeType()
    throws IOException
  {
    SystemCodeInfo sci = this.sysciMan.findUnique("codeType", this.codeType);
    boolean ect = false;
    if (sci != null) {
      ect = true;
    }
    Struts2Utils.getResponse().getWriter().print(ect);
  }

  public void existCodeTypeName()
    throws IOException
  {
    SystemCodeInfo sci = this.sysciMan.findUnique("codeTypeName", this.codeTypeName);
    boolean ectn = false;
    if (sci != null) {
      ectn = true;
    }
    Struts2Utils.getResponse().getWriter().print(ectn);
  }

  public String[] getExcludesForAll()
  {
    return new String[] { "hibernateLazyInitializer", "class", "root", "parent", 
      "users", "childrens", "handler", "systemCodeNodeInfo" };
  }

  public String[] getExcludes()
  {
    return new String[] { "hibernateLazyInitializer", "class", "root", "parent", 
      "users", "childrens", "handler", "systemCodeNodeInfo", 
      "childNodes", "children", "draggable", "parentNode" };
  }

  public void send()
    throws IOException
  {
    boolean result = this.sysciMan.sendCode(this.sendUrl, this.node);
    Struts2Utils.getResponse().getWriter().print("{success:" + result + "}");
  }

  public void save()
    throws IOException
  {
    String orgCode = this.syscniMan.getOrgCode();
    this.sysciMan.save(getModel(), orgCode);
    Struts2Utils.getResponse().getWriter().print("{success:true}");
  }

  public void saveNoOrg()
    throws ServiceException, Exception
  {
    this.sysciMan.save(getModel());
    Struts2Utils.getResponse().getWriter().print("{success:true}");
  }

  public void judgeExistCodeType()
    throws IOException
  {
    int pageNo = this.start / this.limit + 1;
    ExtjsPage page = null;
    Criteria criteria = this.sysciMan.createCriteria();
    criteria = criteria.add(Restrictions.eq(this.filterTxt, this.filterValue));
    if ((this.id != null) && (!"".equals(this.id))) {
      criteria = criteria.add(Restrictions.ne("id", this.id));
    }
    page = this.sysciMan.pagedQuery(criteria, pageNo, this.limit);
    List loadingUnitList = page.getResult();
    if (loadingUnitList.isEmpty())
      Struts2Utils.getResponse().getWriter().print(false);
    else
      Struts2Utils.getResponse().getWriter().print(true);
  }

  public void isHasChild()
    throws IOException
  {
    int size = this.entity.getSystemCodeNodeInfo().size();
    if (size == 0)
      Struts2Utils.getResponse().getWriter().print(false);
    else
      Struts2Utils.getResponse().getWriter().print(true);
  }
}
