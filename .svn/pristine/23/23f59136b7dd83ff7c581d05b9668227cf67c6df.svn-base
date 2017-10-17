package com.sinosoft.systemcode.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.organization.dao.OrganizationInfoDao;
import com.sinosoft.systemcode.dao.SystemCodeInfoDao;
import com.sinosoft.systemcode.dao.SystemCodeNodeInfoDao;
import com.sinosoft.systemcode.domain.SystemCodeInfo;
import com.sinosoft.systemcode.domain.SystemCodeNodeInfo;
import com.sinosoftframework.core.domain.OnlineUser;
import com.sinosoftframework.core.filter.PropertyFilter;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.manager.exception.ServiceException;
import com.sinosoftframework.core.utils.app.AppUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.page.Page;
import com.sinosoftframework.core.utils.string.StringUtils;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

@Service
@Transactional
public class SystemCodeNodeInfoManager extends EntityManager<SystemCodeNodeInfo, String>
{

  @Autowired
  private SystemCodeInfoDao sysciDao;

  @Autowired
  private SystemCodeNodeInfoDao syscniDao;

  @Autowired
  private SystemCodeInfoManager sysciMan;

  @Autowired
  private OrganizationInfoDao organizationInfoDao;

  protected HibernateDao<SystemCodeNodeInfo, String> getEntityDao()
  {
    return this.syscniDao;
  }

  @Transactional(readOnly=true)
  public List<SystemCodeNodeInfo> getSystemCodeNode(String propertyName, Object value)
  {
    Criteria criteria = this.syscniDao.createCriteria(new Criterion[0]);
    criteria.add(Restrictions.eq(propertyName, value));

    criteria.add(Restrictions.eq("available", "Y"));

    OnlineUser onLinerUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
    if (onLinerUser != null) {
      criteria.add(Restrictions.or(Restrictions.eq("orgCode", onLinerUser.getRegionCode()), Restrictions.isNull("orgCode")));
    }

    criteria.addOrder(Order.asc("sequence"));
    criteria.addOrder(Order.asc("code"));

    return criteria.list();
  }

  @Transactional(readOnly=true)
  public List<SystemCodeNodeInfo> getSystemCodeNode(String propertyName, Object value, String[] except)
  {
    Criteria criteria = this.syscniDao.createCriteria(new Criterion[0]);

    OnlineUser onLinerUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
    if (onLinerUser != null) {
      criteria.add(Restrictions.or(Restrictions.eq("orgCode", onLinerUser.getRegionCode()), Restrictions.isNull("orgCode")));
    }
    criteria.add(Restrictions.eq(propertyName, value));
    for (String str : except) {
      criteria.add(Restrictions.ne("code", str));
    }

    criteria.add(Restrictions.eq("available", "Y"));
    return criteria.list();
  }

  @Transactional(readOnly=true)
  public List<SystemCodeNodeInfo> getSystemCodeNodeLowestLevel(String filterValue)
  {
    Criteria criteria = this.syscniDao.createCriteria(new Criterion[0]);
    criteria.add(Restrictions.eq("codeType", filterValue));

    OnlineUser onLinerUser = AppUtils.getOnlineUser(Struts2Utils.getSession().getId());
    if (onLinerUser != null) {
      criteria.add(Restrictions.or(Restrictions.eq("orgCode", onLinerUser.getRegionCode()), Restrictions.isNull("orgCode")));
    }

    List<SystemCodeNodeInfo> list = criteria.list();
    String parentIdTem = "";

    Map parentIdMap = new HashMap();
    for (SystemCodeNodeInfo info : list) {
      parentIdTem = info.getParentId();
      if (!parentIdMap.containsKey(parentIdTem)) {
        parentIdMap.put(parentIdTem, null);
      }
    }

    SystemCodeNodeInfo info = null;
    for (int i = 0; i < list.size(); i++) {
      info = (SystemCodeNodeInfo)list.get(i);
      if (parentIdMap.containsKey(info.getId())) {
        list.remove(i);
        i--;
      }
    }
    return list;
  }

  @Transactional(readOnly=true)
  public List<SystemCodeNodeInfo> getNodeByCodeId(String codeId)
  {
    return this.syscniDao.findBy("systemcode.id", codeId);
  }

  @Transactional(readOnly=true)
  public ExtjsPage<SystemCodeNodeInfo> queryList(Criteria criteria, int pageNo, int limit)
    throws NoSuchFieldException
  {
    return this.syscniDao.listQuery(criteria, pageNo, limit);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void save(SystemCodeNodeInfo model, String parentId, String systemCodeId, String codeType, String orgCode)
  {
    String sysCodeId = systemCodeId;

    if (((sysCodeId == null) || (sysCodeId.trim().length() <= 0)) && 
      (codeType != null) && (!"".equals(codeType))) {
      SystemCodeInfo info = (SystemCodeInfo)this.sysciMan.getEntity(
        SystemCodeInfo.class, "codeType", codeType);
      sysCodeId = info.getId();
      model.setSystemcode((SystemCodeInfo)this.sysciDao.get(sysCodeId));
    }
    if ((sysCodeId != null) && (!"".equals(sysCodeId))) {
      model.setSystemcode((SystemCodeInfo)this.sysciDao.get(sysCodeId));
    }
    if ((parentId != null) && (!"".equals(parentId))) {
      model.setParentId(parentId);
    }

    if ((model.getId() == null) || ("".equals(model.getId()))) {
      model.setOrgCode(orgCode);
      model.setIsAllowShow("Y");
      model.setIsAllowChildren("Y");
      model.setIsAllowDelete("Y");
      model.setIsAllowEdit("Y");
    }
    model.setOperateDate(new Timestamp(System.currentTimeMillis()));
    this.syscniDao.save(model);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void save(SystemCodeNodeInfo model, String parentId, String systemCodeId)
  {
    String sysCodeId = systemCodeId;
    if ((sysCodeId != null) && (!"".equals(sysCodeId))) {
      model.setSystemcode((SystemCodeInfo)this.sysciDao.get(sysCodeId));
    }
    if ((parentId != null) && (!"".equals(parentId))) {
      model.setParentId(parentId);
    }
    model.setOperateDate(new Timestamp(System.currentTimeMillis()));
    this.syscniDao.save(model);
  }

  public Integer queryCodeNodeSeq(String codeType, String code)
  {
    String sql = "SELECT SEQUENCE FROM System_Code_Node P WHERE P.CODE_TYPE = ? AND P.CODE = ?";
    Query query = this.syscniDao.getSession().createSQLQuery(sql);
    query.setString(0, codeType);
    query.setString(1, code);

    Object o = query.uniqueResult();
    if (o != null) {
      Integer oldSeq = Integer.valueOf(Integer.parseInt(o.toString()));
      return oldSeq;
    }
    return null;
  }

  public void updateCodeNodeSequence(String codeType, String code, Integer sequence, Integer oldSeq)
  {
    Criteria criteria = this.syscniDao.createCriteria(new Criterion[0]);
    criteria.add(Restrictions.eq("codeType", codeType));

    Boolean isPlus = Boolean.valueOf(true);

    if (oldSeq != null)
    {
      if (sequence != oldSeq)
      {
        if (sequence.intValue() < oldSeq.intValue())
        {
          criteria.add(Restrictions.ge("sequence", sequence));
          criteria.add(Restrictions.lt("sequence", oldSeq));
        }
        else {
          isPlus = Boolean.valueOf(false);
          criteria.add(Restrictions.gt("sequence", oldSeq));
          criteria.add(Restrictions.le("sequence", sequence));
        }
      }
      else {
        return;
      }
    }
    else {
      criteria.add(Restrictions.ge("sequence", sequence));
    }
    criteria.setProjection(Projections.projectionList()
      .add(Projections.property("code").as("code"))
      .add(Projections.property("sequence").as("seq")));

    criteria.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    Object syscni = criteria.list();
    List syscniList = null;

    if (syscni != null) {
      syscniList = (List)syscni;

      this.syscniDao
        .updateCodeNodeSequence(syscniList, codeType, code, isPlus);
    }
  }

  public String getOrgCode()
  {
    OnlineUser onlineUser = AppUtils.getOnlineUser(
      Struts2Utils.getSession().getId());
    if ((onlineUser != null) && (onlineUser.getRegionCode() != null)) {
      return onlineUser.getRegionCode();
    }
    return null;
  }

  @Transactional(readOnly=true)
  public List<SystemCodeNodeInfo> getCodeNode(List<PropertyFilter> filters)
  {
    return this.syscniDao.find(filters);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void dropNodes(String ids)
  {
    String[] nodeIds = ids.split(",");
    for (int i = 0; i < nodeIds.length; i++)
    {
      deleteNodes(nodeIds[i]);
    }
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void deleteNodes(String codeNodeId)
  {
    if (!((SystemCodeNodeInfo)get(codeNodeId)).getChildNodes().isEmpty()) {
      Set<SystemCodeNodeInfo> nodeSet = ((SystemCodeNodeInfo)get(codeNodeId))
        .getChildNodes();
      for (SystemCodeNodeInfo nodeInfo : nodeSet) {
        deleteNodes(nodeInfo.getId());
      }
    }
    this.syscniDao.deleteNodeById(codeNodeId);
  }

  public void saveChilds(SystemCodeNodeInfo scni) throws Exception
  {
    if (!((SystemCodeNodeInfo)get(scni.getId())).getChildNodes().isEmpty()) {
      Set<SystemCodeNodeInfo> nodeSet = ((SystemCodeNodeInfo)get(scni.getId()))
        .getChildNodes();
      for (SystemCodeNodeInfo nodeInfo : nodeSet) {
        nodeInfo.setSystemcode(scni.getSystemcode());
        nodeInfo.setCodeType(scni.getCodeType());
        saveChilds(nodeInfo);
      }
    }
    save(scni);
  }

  @Transactional(readOnly=true)
  public SystemCodeNodeInfo findSystemCodeNode(String codeTypeId, String codeValue)
  {
    Map map = new HashMap();
    map.put("codeTypeId", codeTypeId);
    map.put("code", codeValue);
    SystemCodeNodeInfo scni = (SystemCodeNodeInfo)this.syscniDao.findUnique(
      "From SystemCodeNodeInfo Where systemcode.id=:codeTypeId and code=:code", 
      map);
    return scni;
  }

  @Transactional(readOnly=true)
  public SystemCodeNodeInfo findSystemCodeNodeById(String codeTypeId, String codeId)
  {
    Map map = new HashMap();
    map.put("codeTypeId", codeTypeId);
    map.put("codeId", codeId);
    SystemCodeNodeInfo scni = (SystemCodeNodeInfo)this.syscniDao.findUnique(
      "From SystemCodeNodeInfo Where systemcode.id=:codeTypeId and id=:codeId", 
      map);
    return scni;
  }

  @Transactional(readOnly=true)
  public ExtjsPage<SystemCodeNodeInfo> pagedQueryNode(int start, int limit, String sort, String dir, String filterTxt, String filterValue, Date startDate, Date endDate, String dateFilter)
  {
    int pageNo = start / limit + 1;
    ExtjsPage page = new ExtjsPage();
    page.setPageNo(pageNo);
    page.setPageSize(limit);
    StringBuffer hql = new StringBuffer();
    hql.append("from SystemCodeNodeInfo");
    StringBuffer hqlsort = new StringBuffer();
    StringBuffer hqlM = new StringBuffer();

    if ((sort != null) && (!"".equals(sort)) && (sort.trim().length() >= 0)) {
      boolean isAsc = dir.equalsIgnoreCase("asc");
      String asc = "";
      if (isAsc)
        asc = "asc";
      else {
        asc = "desc";
      }
      hqlsort.append(" order by " + sort + " " + asc);
    }
    if ((filterTxt != null) && (filterValue != null) && (!filterTxt.equals("")) && 
      (!filterValue.equals(""))) {
      String[] filterT = filterTxt.split(";");
      String[] filterV = filterValue.split(";");
      hqlM.append(" where ");
      for (int i = 0; i < filterT.length; i++) {
        if (filterV[i].indexOf('%') >= 0) {
          if (i == filterT.length - 1)
            hqlM.append(filterT[i] + " like '" + filterV[i] + "'");
          else {
            hqlM.append(filterT[i] + " like '" + filterV[i] + 
              "' and ");
          }
        }
        else if (i == filterT.length - 1)
          hqlM.append(filterT[i] + "='" + filterV[i] + "'");
        else {
          hqlM.append(filterT[i] + "='" + filterV[i] + "' and ");
        }
      }

      hql.append(hqlM);
    }
    if ((dateFilter != null) && (startDate != null) && (!dateFilter.equals("")) && 
      (!startDate.toString().equals(""))) {
      if ((endDate == null) || ("".equals(endDate.toString())))
        hqlM.append(" where " + dateFilter + " = " + startDate);
      else {
        hqlM.append(" where " + dateFilter + " between " + startDate + 
          endDate);
      }
      hql.append(hqlM);
    }

    if ((filterTxt == null) || ((filterTxt.trim().length() <= 0) && 
      (this.sysciMan.codeType(filterValue) > 0))) {
      hql.append(" where codeType = '")
        .append(filterValue)
        .append("' and id not in (select id from SystemCodeNodeInfo where id in (select parentId from SystemCodeNodeInfo))");
    }

    if ((filterTxt != null) && (filterValue != null) && (filterTxt.equals("id"))) {
      StringBuffer hqlN = new StringBuffer();
      hqlN.append("from SystemCodeNodeInfo");

      if ("-1".equals(filterValue)) {
        OnlineUser onlineUser = AppUtils.getOnlineUser(
          Struts2Utils.getSession().getId());
        if ((onlineUser != null) && (onlineUser.getRegionCode() != null)) {
          hqlN.append("  where ( orgCode='" + 
            onlineUser.getRegionCode() + 
            "' or orgCode='' or orgCode is null) ");
        }
        hqlN.append(hqlsort);
        findPage(page, hqlN.toString());
      }
      else if (this.sysciMan.findCodeType(filterValue) > 0) {
        hqlN.append(" where systemcode.id = '" + filterValue + "'" + 
          hqlsort);
        findPage(page, hqlN.toString());
      }
      else if (!"-1".equals(filterValue)) {
        hqlN.append(" where id = '" + filterValue + "' " + hqlsort);
        findPage(page, hqlN.toString());

        List result = page.getResult();
        List nodeResult = new ArrayList();
        for (int i = 0; i < result.size(); i++) {
          SystemCodeNodeInfo scni = 
            (SystemCodeNodeInfo)result
            .get(i);
          nodeResult.addAll(this.syscniDao.getChilds(scni));
        }
        page.setResult(nodeResult);
        page.setTotalCount(nodeResult.size());
      }
    } else if ((filterTxt != null) && (filterValue != null)) {
      OnlineUser onlineUser = AppUtils.getOnlineUser(
        Struts2Utils.getSession().getId());
      if ((onlineUser != null) && (onlineUser.getRegionCode() != null)) {
        if (hql.indexOf("where") != -1)
          hql.append("  and ( orgCode='" + onlineUser.getRegionCode() + 
            "' or orgCode='' or orgCode is null) ");
        else {
          hql.append("  where ( orgCode='" + 
            onlineUser.getRegionCode() + 
            "' or orgCode='' or orgCode is null) ");
        }
      }

      hql.append(hqlsort);
      findPage(page, hql.toString());
    }
    else if ((StringUtils.isEmpty(filterTxt)) && 
      (StringUtils.isEmpty(filterValue)))
    {
      OnlineUser onlineUser = AppUtils.getOnlineUser(
        Struts2Utils.getSession().getId());
      if ((onlineUser != null) && (onlineUser.getRegionCode() != null)) {
        hql.append("  where ( orgCode='" + onlineUser.getRegionCode() + 
          "' or orgCode='' or orgCode is null) ");
      }
      findPage(page, hql.toString());
    }
    return page;
  }

  private Page findPage(Page page, String hql)
  {
    if (hql.indexOf("where") != -1)
      hql = hql.replace("where", " where is_allow_show='Y' and ");
    else {
      hql = hql.replace("SystemCodeNodeInfo", "SystemCodeNodeInfo where is_allow_show='Y' ");
    }
    Query query = this.syscniDao.createQuery(hql, new Object[0]);
    List result = query.list();
    Iterator it = result.iterator();
    while(it.hasNext()){
    	SystemCodeNodeInfo scn = (SystemCodeNodeInfo) it.next();
    	if ((scn.getParentId() == null) || (scn.getParentId().equals(""))) {
    		scn.setParentName(scn.getSystemcode().getName());
    	} else {
    		SystemCodeNodeInfo scni = findSystemCodeNodeById(scn
    				.getSystemcode().getId(), scn.getParentId());
    		if (scni != null) {
    			String name = scni.getName();
    			scn.setParentName(name);
    		}
    	}
    }
    page.setResult(result);
    page.setTotalCount(result.size());
    return page;
  }
}
